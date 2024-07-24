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

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.connection.QueryConnection;
import net.devcube.vinco.teamspeakapi.query.connection.SocketConnection;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;


public class TestQueryConfig {

    private static Ts3ServerQuery query;
    private static QueryConfig config;

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
        config = query.getConfig();
    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testConfig() {
        assertSame(config.getFloodRate(), FloodRate.DEFAULT_TSAPI);
        assertSame(config.getDebugType(), DebugType.CONSOLE);
        assertSame(config.getEventCallType(), EventCallType.NEW);
        assertTrue(config.getConnection() instanceof SocketConnection);
        assertFalse(config.isShowTimeMilliseconds());
        assertFalse(config.isShowDate());
        assertEquals(2000, config.getConnectionTimeout());

        config.setFloodRate(FloodRate.custom(100));
        config.setDebugType(DebugType.BOTH);
        config.setEventCallType(EventCallType.BOTH);

        config.setShowTimeMilliseconds(true);
        config.setShowDate(true);
        config.setConnectionTimeout(3000);

        assertEquals(100, config.getFloodRate().getValue());
        assertSame(config.getDebugType(), DebugType.BOTH);
        assertSame(config.getEventCallType(), EventCallType.BOTH);
        assertTrue(config.isShowTimeMilliseconds());
        assertTrue(config.isShowDate());
        assertEquals(3000, config.getConnectionTimeout());


    }

    @Test
    public void testCacheEnabled() {
        assertTrue(config.getCachingList().isEmpty(), "No cache items should be added");

        config.addCacheItem(CacheType.CLIENTS);
        config.addCacheItem(CacheType.DATABASE);
        config.addCacheItem(CacheType.GROUPS);
        config.addCacheItem(CacheType.PERMISSION);
        config.addCacheItem(CacheType.QUERY);
        config.addCacheItem(CacheType.VIRTUALSERVER);

        assertEquals(6, config.getCachingList().size(), "All cache items should be added");

        assertTrue(config.isClientsCached(), "Client cache should be enabled");
        assertTrue(config.isDataBaseCached(), "Database cache should be enabled");
        assertTrue(config.isGroupsCached(), "Groups cache should be enabled");
        assertTrue(config.isPermissionCached(), "Permission cache should be enabled");
        assertTrue(config.isQueryCached(), "Query cache should be enabled");
        assertTrue(config.isVirtualServerCached(), "VirtualServer cache should be enabled");

        config.removeCacheItem(CacheType.CLIENTS);
        config.removeCacheItem(CacheType.DATABASE);
        config.removeCacheItem(CacheType.GROUPS);
        config.removeCacheItem(CacheType.PERMISSION);
        config.removeCacheItem(CacheType.QUERY);
        config.removeCacheItem(CacheType.VIRTUALSERVER);

        assertTrue(config.getCachingList().isEmpty(), "No cache items should be added");
    }
}
