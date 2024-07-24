package cache;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.VirtualServerInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestVirtualServerCaching {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.VIRTUALSERVER);

        assertTimeout(Duration.ofMillis(250), () -> {
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
    public void testGetVirtualServerList() {
        assertTimeout(Duration.ofMillis(30), () -> {
            List<VirtualServerInfo> info = basic.getVirtualServers();
            assertEquals(1, info.size());
            info.forEach(vs -> assertNotNull(vs, "VirtualServerInfo should not be null"));
        });
    }

    @Test
    public void testGetVirtualServerInfo() {
        assertTimeout(Duration.ofMillis(30), () -> {
            VirtualServerInfo info = basic.getVirtualServerInfo();
        });
    }
}
