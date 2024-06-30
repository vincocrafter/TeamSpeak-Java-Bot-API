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

import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
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
                query.connect("localhost", 10011, "serveradmin", password, 1, "Test", -1);
                Ts3SyncAPI sync = query.getSyncAPI();
                assertTrue(sync.isConnected());
                QueryClientInfo info = sync.getQueryInfo();
                assertEquals(info.getName(), "Test");
                assertEquals(info.getUUID(), "serveradmin");
                assertEquals(info.getDataBaseID(), 1);
                assertEquals(info.getVirtualServerID(), 1);
                assertEquals(25, sync.getDataBaseClients().size());
                assertEquals(51, sync.getChannels().size());
                assertTrue(sync.getServerGroups().size() >= 28);
                assertTrue(sync.getChannelGroups().size() >= 24);
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
}
