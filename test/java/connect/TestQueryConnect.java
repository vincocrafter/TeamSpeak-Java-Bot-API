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

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueryConnect {


    @Test
    public void testConnectionNormal() throws InterruptedException {
        Ts3ServerQuery query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "Test", -1);
                Ts3SyncAPI sync = query.getSyncAPI();
                assertEquals(25, sync.getDataBaseClients().size());
                assertEquals(51, sync.getChannels().size());
                assertEquals(28, sync.getServerGroups().size());
                assertEquals(24, sync.getChannelGroups().size());
                assertTrue(query.getSyncAPI().isConnected());
                query.stopQuery();
            }
        });
        assertFalse(query.getSyncAPI().isConnected());
    }

    @Test
    public void testConnectionOtherOne() {
        Ts3ServerQuery query = new Ts3ServerQuery();

        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011);
				query.getSyncAPI().login("serveradmin", password);
                query.getSyncAPI().connectTeamSpeakQuery(1, "Test");
				query.getSyncAPI().registerAllEvents();
                query.stopQuery();
            }
        });
    }

    @Test
    public void testConnectionOtherTwo() {
        Ts3ServerQuery query = new Ts3ServerQuery();

        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");

        assertDoesNotThrow(new Executable() {

            @Override
            public void execute() throws Throwable {
				query.connect("localhost", 10011);
				query.getSyncAPI().login("serveradmin", password);
				query.getSyncAPI().selectVirtualServer(1);
				query.getSyncAPI().updateQueryName("Test");
				query.getSyncAPI().registerAllEvents();
				query.stopQuery();
            }
        });
    }

    @Test
    public void testConnectionWithoutLogin() {
        Ts3ServerQuery query = new Ts3ServerQuery();
        assertDoesNotThrow(new Executable() {

            @Override
            public void execute() throws Throwable {
				query.connect("localhost", 10011);
				assertEquals(query.getSyncAPI().getQueryInfo().getDataBaseID(), 0);
				query.getSyncAPI().selectVirtualServer(1);
				query.getSyncAPI().updateQueryName("Test");
				query.stopQuery();
            }
        });
    }

}
