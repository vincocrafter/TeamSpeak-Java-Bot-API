/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 05.11.2023
 * <p>
 * Uhrzeit : 20:05:07
 */
package cache;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
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


public class TestDatabaseCaching {

    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.DATABASE);
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
    public void testGetDatabaseClientList() {
        assertTimeout(Duration.ofMillis(25), () -> {
            List<DataBaseClientInfo> dbClients = basic.getDataBaseClients(-1, -1);
            assertEquals(25, dbClients.size());
            assertFalse(dbClients.isEmpty(), "Database client list should not be empty");
            dbClients.forEach(dbClient -> assertNotNull(dbClient, "DataBaseClientInfo should not be null"));
        });
    }

    @Test
    public void testGetDatabaseClients() {
        assertTimeout(Duration.ofMillis(25), () -> {
            for (DataBaseClientInfo dbClient : basic.getDataBaseClients(-1, -1)) {
                DataBaseClientInfo dbClientInfo = basic.getDataBaseClient(dbClient.getID());
                assertNotNull(dbClientInfo, "DataBaseClientInfo should not be null for client ID: " + dbClient.getID());
                assertEquals(dbClient.getID(), dbClientInfo.getID(), "DataBase Client ID should match the expected ID");
            }
        });

        DataBaseClientInfo dbClient = basic.getDataBaseClient(21);
        assertEquals("Client17", dbClient.getClientName(), "Client name should match the expected name");
        assertEquals("uaw\\/BPP2sVGuP7KjSbvyPWV9nNA=", dbClient.getUUID(), "UUID should match the expected UUID");
        assertEquals(1717667384, dbClient.getCreatingTime(), "Created timestamp should match the expected timestamp");
        assertEquals(1717667389, dbClient.getLastConnection(), "Last connected timestamp should match the expected timestamp");
        assertEquals("Description of Client17", dbClient.getDescription(), "Description should match the expected description");
    }
}
