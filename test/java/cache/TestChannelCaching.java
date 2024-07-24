/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 05.11.2023
 * <p>
 * Uhrzeit : 14:25:56
 */
package cache;

import net.devcube.vinco.teamspeakapi.api.api.event.EventHandler;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelCreateEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelProperty;
import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestChannelCaching {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.CHANNELS);
        query.getConfig().setShowTimeMilliseconds(true);

        assertTimeout(Duration.ofMillis(400), () -> {
            assertDoesNotThrow(new Executable() {
                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011, "serveradmin", password, 1, "CachingTest", -1);
                }
            });
        });
        basic = query.getBasicAPI();
    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testGetChannelList() {
        assertTimeout(Duration.ofMillis(25), () -> {
            List<ChannelInfo> channels = basic.getChannels();
            assertFalse(channels.isEmpty(), "Channel list should not be empty");
            assertEquals(51, channels.size());
            channels.forEach(channel -> assertNotNull(channel, "ChannelInfo should not be null"));
        });
    }

    @Test
    public void testGetChannel() {
        assertTimeout(Duration.ofMillis(25), () -> {
            for (ChannelInfo channel : basic.getChannels()) {
                ChannelInfo channelInfo = basic.getChannel(channel.getID());
                assertNotNull(channelInfo, "ChannelInfo should not be null for channel ID: " + channel.getID());
                assertEquals(channel.getID(), channelInfo.getID(), "Channel ID should match the expected ID");
            }
        });
        ChannelInfo channel = basic.getChannel(103);
        assertEquals("Channel50", channel.getName(), "Channel name should match the expected name");
        assertEquals("5A13LwVe5iVWKiTszrty", channel.getDescription(),
                "Channel description should match the expected description");
        assertEquals("1520920e-c923-43f1-af08-1f14a3f4b6fa", channel.getUUID(),
                "Channel UUID should match the expected UUID");
    }

    @Test
    public void testChannelCacheUpdate()  {
        basic.editChannel(103, Collections.singletonMap(ChannelProperty.CHANNEL_TOPIC, "Test Topic"));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }

        assertEquals("Test Topic", basic.getChannel(103).getTopic());
        assertEquals("Test Topic", basic.getChannels().stream().filter(c -> c.getID() == 103).findFirst().get().getTopic());

        basic.editChannel(103, Collections.singletonMap(ChannelProperty.CHANNEL_TOPIC, ""));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }

        assertEquals("", basic.getChannel(103).getTopic());
        assertEquals("", basic.getChannels().stream().filter(c -> c.getID() == 103).findFirst().get().getTopic());

        basic.moveChannel(103, 101, -1);
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
        }
        assertEquals(101, basic.getChannels().stream().filter(c -> c.getID() == 103).findFirst().get().getChannelParentID(), "Channel parent ID should match the expected parent ID");
        assertEquals(101, basic.getChannel(103).getChannelParentID());
        basic.moveChannel(103, 0, 102);
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
        }

        assertEquals(0, basic.getChannel(103).getChannelParentID());

        int createdChannel = query.getSyncAPI().createPermanentChannel("Channel51");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }

        assertNotNull(basic.getChannel(createdChannel), "ChannelInfo should not be null for the created channel");
        assertEquals(52, basic.getChannels().size(), "Channel list size should match the expected size");
        basic.deleteChannel(createdChannel, true);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }

        assertEquals(51, basic.getChannels().size(), "Channel list size should match the expected size");
    }
}
