package cache;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueryCaching {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.QUERY);


        assertTimeout(Duration.ofMillis(300), () -> {
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
    public void testGetQueryInfo() {
        assertTimeout(Duration.ofMillis(30), () -> {
            QueryClientInfo info = basic.getQueryInfo();
            assertEquals("CachingTest", info.getName());
            assertEquals("serveradmin", info.getUUID());
            assertEquals(1, info.getDataBaseID());
            assertEquals(1, info.getVirtualServerID());
        });
    }

    @Test
    public void testUpdateQueryInfo() {
        basic.updateQueryName("CachingTestTest");
        assertEquals("CachingTestTest", basic.getQueryInfo().getName());

        basic.moveClient(basic.getQueryInfo().getClientID(), 103);
        assertEquals(103, basic.getQueryInfo().getChannelID());
    }
}
