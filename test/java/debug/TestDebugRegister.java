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
package debug;


import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class TestDebugRegister {

    private static Ts3ServerQuery query;
    private static QueryConfig config;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "DebugTest", -1);
            }
        });
        config = query.getConfig();

    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testDebugEnabled() {
        assertTrue(config.getDebugList().isEmpty(), "No debug items should be added");

        config.addDebugItem(DebugOutputType.EVENTMANAGER);
        config.addDebugItem(DebugOutputType.KEEPALIVETHREAD);
        config.addDebugItem(DebugOutputType.QUERY);
        config.addDebugItem(DebugOutputType.ERROR);
        config.addDebugItem(DebugOutputType.WARNING);
        config.addDebugItem(DebugOutputType.QUERYREADER);
        config.addDebugItem(DebugOutputType.QUERYREADERQUEUE);
        config.addDebugItem(DebugOutputType.QUERYWRITER);
        config.addDebugItem(DebugOutputType.CACHEMANAGER);
        config.addDebugItem(DebugOutputType.GENERAL);

        config.addDebugItem(DebugOutputType.E_CHANNEL_CREATED);
        config.addDebugItem(DebugOutputType.E_CHANNEL_DELETED);
        config.addDebugItem(DebugOutputType.E_CHANNEL_DESCRIPTION_EDITED);
        config.addDebugItem(DebugOutputType.E_CHANNEL_EDITED);
        config.addDebugItem(DebugOutputType.E_CHANNEL_MOVED);
        config.addDebugItem(DebugOutputType.E_CHANNEL_PASSWORD_CHANGED);
        config.addDebugItem(DebugOutputType.E_CLIENT_JOIN);
        config.addDebugItem(DebugOutputType.E_CLIENT_LEAVE);
        config.addDebugItem(DebugOutputType.E_CLIENT_MOVE);
        config.addDebugItem(DebugOutputType.E_PRIVILEGEKEY_USED);
        config.addDebugItem(DebugOutputType.E_SERVER_EDITED);
        config.addDebugItem(DebugOutputType.E_TEXT_MESSAGE);

        assertEquals(22, config.getDebugList().size(), "All debug items should be added");

        assertTrue(config.isInDebug(DebugOutputType.EVENTMANAGER), "EventManager debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.KEEPALIVETHREAD), "KeepAliveThread debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.QUERY), "Query debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.ERROR), "Error debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.WARNING), "Warning debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.QUERYREADER), "QueryReader debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.QUERYREADERQUEUE), "QueryReaderQueue debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.QUERYWRITER), "QueryWriter debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.CACHEMANAGER), "CacheManager debug should be enabled");
        assertTrue(config.isInDebug(DebugOutputType.GENERAL), "General debug should be enabled");

        config.removeDebugItem(DebugOutputType.EVENTMANAGER);
        config.removeDebugItem(DebugOutputType.KEEPALIVETHREAD);
        config.removeDebugItem(DebugOutputType.QUERY);
        config.removeDebugItem(DebugOutputType.ERROR);
        config.removeDebugItem(DebugOutputType.WARNING);
        config.removeDebugItem(DebugOutputType.QUERYREADER);
        config.removeDebugItem(DebugOutputType.QUERYREADERQUEUE);
        config.removeDebugItem(DebugOutputType.QUERYWRITER);
        config.removeDebugItem(DebugOutputType.CACHEMANAGER);
        config.removeDebugItem(DebugOutputType.GENERAL);

        assertEquals(12, config.getDebugList().size(), "All normal debug items should be removed");
    }
}
