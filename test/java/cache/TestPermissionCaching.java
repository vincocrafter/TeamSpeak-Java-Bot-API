package cache;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPermissionCaching {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        query.getConfig().addCacheItem(CacheType.PERMISSION);

        assertTimeout(Duration.ofMillis(350), () -> {
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
    public void testGetPermissionList() {
        assertEquals(496, basic.getPermissionList().size());
        assertEquals(496, basic.getPermissionIDs(basic.getPermissionList()).size());
        assertEquals(496, basic.getPermissionIDs().size());

        assertTimeout(Duration.ofMillis(40), () -> {
            basic.getPermissionList().forEach(perm -> assertNotNull(perm, "Permission should not be null"));
        });

        assertTimeout(Duration.ofMillis(40), () -> {
            basic.getPermissionIDs().forEach(perms -> assertNotNull(perms, "Permission should not be null"));
        });

    }
}
