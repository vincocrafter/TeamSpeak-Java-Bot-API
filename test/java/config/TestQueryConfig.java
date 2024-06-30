/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 03.07.2023
 * <p>
 * Uhrzeit : 20:00:02
 */
package config;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.connection.SocketConnection;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;


public class TestQueryConfig {

    private static Ts3ServerQuery query;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "ConfigTest", -1);
            }
        });
    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testConfig() {
        QueryConfig config = query.getConfig();

        assertSame(config.getFloodRate(), FloodRate.DEFAULT_TSAPI);
        assertSame(config.getDebugType(), DebugType.CONSOLE);
        assertSame(config.getEventCallType(), EventCallType.NEW);
        assertTrue(config.getConnection() instanceof SocketConnection);
        assertFalse(config.isShowTimeMilliseconds());
        assertFalse(config.isShowDate());

        config.setFloodRate(FloodRate.custom(100));
        config.setDebugType(DebugType.BOTH);
        config.setEventCallType(EventCallType.BOTH);
        config.setShowTimeMilliseconds(true);
        config.setShowDate(true);

        assertEquals(100, config.getFloodRate().getValue());
        assertSame(config.getDebugType(), DebugType.BOTH);
        assertSame(config.getEventCallType(), EventCallType.BOTH);
        assertTrue(config.isShowTimeMilliseconds());
        assertTrue(config.isShowDate());
    }

}
