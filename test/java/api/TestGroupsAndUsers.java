package api;

import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;
public class TestGroupsAndUsers {

    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "GroupsAndUsersTest", -1);
            }
        });

        basic = query.getBasicAPI();
    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testUsersByServerGroup() {
        assertEquals(1, basic.getDatabaseIDsByServerGroup(2).size(), "Server group 2 should have 1 user");

        assertEquals(1, basic.getDatabaseIDsByServerGroup(6).size(), "Server group 6 should have 1 user");
        assertTrue(basic.getDatabaseIDsByServerGroup(14).isEmpty(), "Server group 14 should not have any users");
        assertEquals(14, basic.getDatabaseIDsByServerGroup(15).size(), "Server group 15 should have 14 users");
        assertEquals(2, basic.getDatabaseIDsByServerGroup(29).size(), "Server group 29 should have 2 users");
        assertTrue(basic.getDatabaseIDsByServerGroup(29).contains(4), "Server group 29 should have user with id 4");
    }

    @Test
    public void testUsersByChannelGroup() {
        assertTrue(basic.getDatabaseIDsByChannelGroup(1).isEmpty(), "Channel group 1 should not have any users");

        assertEquals(1, basic.getDatabaseIDsByChannelGroup(5).size(), "Channel group 5 should have 1 user");
        assertEquals(1, basic.getDatabaseIDsByChannelGroup(5).get(2).size(), "Channel group 5 should have 1 user with id 2 with only 1 channel");
        assertTrue(basic.getDatabaseIDsByChannelGroup(5).get(2).contains(74), "Channel group 5 should have 1 user with id 2 in channel 74");

        assertEquals(7, basic.getDatabaseIDsByChannelGroup(14).size(), "Channel group 14 should have 7 users");
        assertEquals(2, basic.getDatabaseIDsByChannelGroup(14).get(5).size(), "Channel group 14 should have 1 user with id 5 with 2 channel ids");
        assertTrue(basic.getDatabaseIDsByChannelGroup(14).get(5).contains(101), "Channel group 14 should have user with id 5 with channel 101");
    }

    @Test
    public void testGroupsByUser() {
        assertEquals(15, basic.getServerGroupIDsByClient(2).size(), "User with id 2 should be in 15 server groups");
        assertEquals(15, basic.getServerGroupNamesByClient(2).size(), "User with id 2 should be in 15 server groups");
        assertEquals(9, basic.getServerGroupIDsByClient(4).size(), "User with id 4 should be in 9 server groups");

        assertTrue(basic.getServerGroupIDsByClient(4).contains(6), "User with id 4 should be in server group 6");
        assertTrue(basic.getServerGroupIDsByClient(4).contains(29), "User with id 4 should be in server group 29");
        assertEquals(13, basic.getServerGroupIDsByClient(10).size(), "User with id 10 should be in 13 server groups");

        assertEquals(5, basic.getChannelGroupsByDatabaseID(2).size(), "User with id 2 should be in 5 channel groups");
        assertEquals(1, basic.getChannelGroupsByDatabaseID(5).size(), "User with id 5 should be in one channel group");
        assertEquals(2, basic.getChannelGroupsByDatabaseID(5).get(14).size(), "User with id 5 should have channel group 14 in two different channels");
        assertTrue(basic.getChannelGroupsByDatabaseID(5).get(14).contains(101), "User with id 5 should be in channel group 14 in channel 101");

        assertEquals(2, basic.getChannelGroupsByDatabaseID(10).size(), "User with id 10 should be in 2 channel groups");
    }

    @Test
    public void testChannelGroupUsersByChannel() {
        assertTrue(basic.getChannelGroupsByChannelID(55).isEmpty(), "Channel with id 55 should not have any channel groups");
        assertEquals(1, basic.getChannelGroupsByChannelID(57).size(), "Channel with id 57 should have 1 channel group");
        assertTrue(basic.getChannelGroupsByChannelID(57).get(20).contains(18), "Channel with id 57 should have channel group with id 20 and client with id 18");

        assertEquals(2, basic.getChannelGroupsByChannelID(74).size(), "Channel with id 74 should have 2 channel group");
        assertTrue(basic.getChannelGroupsByChannelID(74).get(5).contains(2), "Channel with id 74 should have channel group with id 5 and client with id 2");

        assertEquals(3, basic.getChannelGroupsByChannelID(101).size(), "Channel with id 101 should have 3 channel groups");
        assertEquals(2, basic.getChannelGroupsByChannelID(101).get(14).size(), "Channel with id 101 should have channel group with id 14 and 2 clients");
        assertTrue(basic.getChannelGroupsByChannelID(101).get(14).contains(5), "Channel with id 101 should have channel group with id 14 and clients with id 5");
    }

    @Test
    public void testUsersByChannelAndGroup() {
        assertEquals(2, basic.getDatabaseIDsByChannelAndGroup(101, 14).size(), "Channel with id 101 and group with id 14 should have 2 users");
        assertEquals(1, basic.getDatabaseIDsByChannelAndGroup(1, 16).size(), "Channel with id 1 and group with id 16 should have 1 user");
    }

}
