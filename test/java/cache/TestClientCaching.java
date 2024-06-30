/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 05.11.2023
 * <p>
 * Uhrzeit : 14:18:37
 */
package cache;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientCaching {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.CLIENTS);
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
    public void testGetClientList() {
        assertTimeout(Duration.ofMillis(25), () -> {
            List<ClientInfo> clients = basic.getClients();
            assertFalse(clients.isEmpty(), "Client list should not be empty");
            clients.forEach(client -> assertNotNull(client, "ClientInfo should not be null"));
        });
    }

    @Test
    public void testGetClients() {
        assertTimeout(Duration.ofMillis(25), () -> {
            for (ClientInfo client : basic.getClients()) {
                ClientInfo clientInfo = basic.getClient(client.getID());
                assertNotNull(clientInfo, "ClientInfo should not be null for client ID: " + client.getID());
                assertEquals(client.getID(), clientInfo.getID(), "ClientInfo ID should match the client ID");
            }
        });
    }
}
