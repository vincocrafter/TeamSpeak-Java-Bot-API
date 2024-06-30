/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 05.11.2023
 * <p>
 * Uhrzeit : 14:28:50
 */
package cache;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ServerGroupInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGroupCaching {

    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.GROUPS);
        assertTimeout(Duration.ofMillis(200), () -> {
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
    public void testGetServerGroups() {
        assertTimeout(Duration.ofMillis(25), () -> {
            List<ServerGroupInfo> serverGroups = basic.getServerGroups();
            assertFalse(serverGroups.isEmpty(), "Server group list should not be empty");
            assertEquals(28, serverGroups.size(), "Server group list size should match the expected size");
            serverGroups.forEach(groups -> assertNotNull(groups, "Server group should not be null"));
        });
        basic.createServerGroup("TestOtherServerGroup", ServerGroupType.NORMAL);
        assertEquals(28, basic.getServerGroups().size(), "Server group list size should match the old size");
        query.getCache().updateServerGroupsCache();
        assertEquals(29, basic.getServerGroups().size(), "Server group list size should match the actual size");

    }

    @Test
    public void testGetChannelGroups() {
        assertTimeout(Duration.ofMillis(25), () -> {
            List<ChannelGroupInfo> channelGroups = basic.getChannelGroups();
            assertFalse(channelGroups.isEmpty(), "Channel group list should not be empty");
            assertEquals(24, channelGroups.size(), "Channel group list size should match the expected size");
            channelGroups.forEach(groups -> assertNotNull(groups, "Channel group should not be null"));
        });

        basic.createChannelGroup("TestOtherChannelGroup", ChannelGroupType.NORMAL);
        assertEquals(24, basic.getChannelGroups().size(), "Channel group list size should match the old size");
        query.getCache().updateChannelGroupsCache();
        assertEquals(25, basic.getChannelGroups().size(), "Channel group list size should match the actual size");
    }
}
