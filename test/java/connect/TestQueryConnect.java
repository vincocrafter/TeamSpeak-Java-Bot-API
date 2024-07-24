/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 09.08.2023
 * <p>
 * Uhrzeit : 21:20:44
 */
package connect;

import net.devcube.vinco.teamspeakapi.api.api.property.EventType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueryConnect {

    private static String password;

    @BeforeAll
    public static void testPassword() {
        password = System.getenv("TS3_SERVER_PASSWORD");
        assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
    }

    @Test
    public void testConnectionNormal() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertTimeout(Duration.ofMillis(250), () -> {
            assertDoesNotThrow(() -> {
                query.connect("localhost", 10011, "serveradmin", password, 1, "Test", 1);
                Ts3SyncAPI sync = query.getSyncAPI();
                assertTrue(sync.isConnected());
                QueryClientInfo info = sync.getQueryInfo();
                assertEquals("Test", info.getName());
                assertEquals("serveradmin", info.getUUID());
                assertEquals(1, info.getDataBaseID());
                assertEquals(1, info.getVirtualServerID());
                assertEquals(25, sync.getDataBaseClients().size());
                assertEquals(51, sync.getChannels().size());
                assertEquals(28, sync.getServerGroups().size());
                assertEquals(24, sync.getChannelGroups().size());
                query.stopQuery();
            });
        });

        assertFalse(query.getSyncAPI().isConnected());
    }

    @Test
    public void testConnectionOtherOne() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertTimeout(Duration.ofMillis(250), () -> {
            assertDoesNotThrow(new Executable() {
                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011);
                    Ts3SyncAPI sync = query.getSyncAPI();
                    sync.login("serveradmin", password);
                    sync.connectTeamSpeakQuery(1, "Test");
                    assertTrue(sync.isConnected());
                    sync.registerAllEvents();
                    query.stopQuery();
                }
            });
            assertFalse(query.getSyncAPI().isConnected());
        });
    }

    @Test
    public void testConnectionOtherTwo() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertTimeout(Duration.ofMillis(200), () -> {
            assertDoesNotThrow(new Executable() {
                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011);
                    Ts3SyncAPI sync = query.getSyncAPI();
                    sync.login("serveradmin", password);
                    sync.selectVirtualServer(1);
                    sync.updateQueryName("Test");
                    sync.registerAllEvents();
                    query.stopQuery();
                }
            });
        });
    }

    @Test
    public void testConnectionWithoutLogin() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertTimeout(Duration.ofMillis(200), () -> {
            assertDoesNotThrow(new Executable() {

                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011);
                    Ts3SyncAPI sync = query.getSyncAPI();
                    sync.selectVirtualServer(1);
                    sync.updateQueryName("Test");
                    QueryClientInfo info = sync.getQueryInfo();
                    assertNotNull(info); // QueryInfo is not null
                    assertEquals(info.getDataBaseID(), 0);
                    query.stopQuery();
                }
            });
        });
    }

    @Test
    public void testConnectionWithoutSocketConnection() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        query.getConfig().setConnection(null);
        assertTimeout(Duration.ofMillis(200), () -> {
            assertThrows(NullPointerException.class,new Executable() {

                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011);
                    query.stopQuery();
                }
            });
        });
    }

    @Test
    public void testConnectionAsync() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertTimeout(Duration.ofMillis(250), () -> {
            assertDoesNotThrow(new Executable() {
                @Override
                public void execute() throws Throwable {
                    query.connect("localhost", 10011);
                    Ts3AsyncAPI async = query.getAsyncAPI();
                    async.login("serveradmin", password);
                    async.selectVirtualServer(1, -1, "Test").onFinish(res -> {
                        async.registerEvent(EventType.CHANNEL, 0);
                        async.registerEvent(EventType.SERVER, -1);
                    });
                    query.getSyncAPI().getVersion();
                    query.stopQuery();
                }
            });
            assertFalse(query.getSyncAPI().isConnected());
        });
    }
}
