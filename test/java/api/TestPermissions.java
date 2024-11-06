package api;

import net.devcube.vinco.teamspeakapi.api.api.property.TSPermission;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.PermissionAssignmentInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPermissions {
    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;

    @BeforeAll
    public static void connectQuery() {
        query = new Ts3ServerQuery();
        String password = System.getenv("TS3_SERVER_PASSWORD");
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                query.connect("localhost", 10011, "serveradmin", password, 1, "PermissionsTest", -1);
            }
        });
        basic = query.getBasicAPI();
    }

    @AfterAll
    public static void stopQuery() {
        query.stopQuery();
    }

    @Test
    public void testGetPermissions() {
        assertEquals(1, basic.getPermissionID("b_serverinstance_help_view"));
        assertEquals("b_serverinstance_help_view", basic.getPermissionName(1));

        List<Permission> permissionList = basic.getPermissionList();
        assertEquals(496, permissionList.size());
        assertEquals(496, basic.getPermissionIDs(permissionList).size());
        assertEquals(496, basic.getPermissionIDs().size());


        List<Permission> channelPerms = basic.getChannelPermissions(1);
        assertEquals(1, channelPerms.size());
        assertEquals(86, channelPerms.get(0).getID());

        List<Permission> clientPerms = basic.getClientPermissions(2);
        assertEquals(1, clientPerms.size());
        assertEquals(204, clientPerms.get(0).getID());
        assertEquals(42, clientPerms.get(0).getValue());

        List<Permission> serverGroupPermissions = basic.getServerGroupPermissions(17);
        assertEquals(276, serverGroupPermissions.size());
        assertEquals(26, serverGroupPermissions.get(0).getID());

        List<Permission> channelGroupPermissions = basic.getChannelGroupPermissions(9);
        assertEquals(45, channelGroupPermissions.size());
        assertEquals(83, channelGroupPermissions.get(0).getID());

        List<Permission> channelClientPermissions = basic.getChannelClientPermissions(55, 26);
        assertEquals(1, channelClientPermissions.size());
        assertEquals(223, channelClientPermissions.get(0).getID());
    }

    @Test
    public void testCreatePermissions() {
        Permission permConnectionView = new Permission("permid=26 permvalue=1 permnegated=0 permskip=0");
        assertEquals(26, permConnectionView.getID());
        assertEquals(1, permConnectionView.getValue());
        assertFalse(permConnectionView.isNegated());
        assertFalse(permConnectionView.isSkip());
        assertNotNull(permConnectionView.toString());

        Permission permSelect = new Permission("b_virtualserver_select", "Select a virtual server");
        assertEquals("b_virtualserver_select", permSelect.getName());
        assertEquals("Select a virtual server", permSelect.getDescription());
        assertNotNull(permSelect.toString());

        Permission permHelpView = new Permission("b_serverinstance_help_view", 1,
                1, "Retrieve information about ServerQuery commands");
        assertEquals("b_serverinstance_help_view", permHelpView.getName());
        assertEquals(1, permHelpView.getID());
        assertEquals(1, permHelpView.getValue());
        assertEquals("Retrieve information about ServerQuery commands", permHelpView.getDescription());
        assertNotNull(permHelpView.toString());

        Permission permInstanceView = new Permission("b_serverinstance_info_view", 2,
                "View virtual Retrieve global server information");
        assertEquals("b_serverinstance_info_view", permInstanceView.getName());
        assertEquals(2, permInstanceView.getID());
        assertEquals("View virtual Retrieve global server information", permInstanceView.getDescription());
        assertNotNull(permInstanceView.toString());

        Permission permList = new Permission(TSPermission.B_SERVERINSTANCE_PERMISSION_LIST);
        assertEquals(5, permList.getID());
        assertEquals("b_serverinstance_permission_list", permList.getName());
        assertNotNull(permList.toString());

        Permission permVirtualServerList = new Permission(3, "b_serverinstance_virtualserver_list");
        assertEquals(3, permVirtualServerList.getID());
        assertEquals("b_serverinstance_virtualserver_list", permVirtualServerList.getName());
        assertNotNull(permVirtualServerList.toString());

        Permission permPermissionFind = new Permission("b_serverinstance_permission_find", 75);
        assertEquals("b_serverinstance_permission_find", permPermissionFind.getName());
        assertEquals(75, permPermissionFind.getValue());
        assertNotNull(permPermissionFind.toString());

        Permission permVirtualServerCreate = new Permission(TSPermission.B_VIRTUALSERVER_CREATE, 75);
        assertEquals(7, permVirtualServerCreate.getID());
        assertEquals("b_virtualserver_create", permVirtualServerCreate.getName());
        assertEquals(75, permVirtualServerCreate.getValue());
        assertNotNull(permVirtualServerCreate.toString());

        Permission permVirtualServerDelete = new Permission(8, 75);
        assertEquals(8, permVirtualServerDelete.getID());
        assertEquals(75, permVirtualServerDelete.getValue());
        assertNotNull(permVirtualServerDelete.toString());

        Permission permVirtualServerStart = new Permission("b_virtualserver_start_any", 9, 75);
        assertEquals("b_virtualserver_start_any", permVirtualServerStart.getName());
        assertEquals(9, permVirtualServerStart.getID());
        assertEquals(75, permVirtualServerStart.getValue());
        assertNotNull(permVirtualServerStart.toString());

        Permission permVirtualServerStop = new Permission("b_virtualserver_stop_any",
                75, false, true);
        assertEquals("b_virtualserver_stop_any", permVirtualServerStop.getName());
        assertEquals(75, permVirtualServerStop.getValue());
        assertFalse(permVirtualServerStop.isNegated());
        assertTrue(permVirtualServerStop.isSkip());
        assertNotNull(permVirtualServerStop.toString());

        Permission permChangeMachineID = new Permission(TSPermission.B_VIRTUALSERVER_CHANGE_MACHINE_ID,
                75, false, true);
        assertEquals("b_virtualserver_change_machine_id", permChangeMachineID.getName());
        assertEquals(11, permChangeMachineID.getID());
        assertEquals(75, permChangeMachineID.getValue());
        assertFalse(permChangeMachineID.isNegated());
        assertTrue(permChangeMachineID.isSkip());
        assertNotNull(permChangeMachineID.toString());

        Permission permChangeTemplate = new Permission(12, 75, false, true);
        assertEquals(12, permChangeTemplate.getID());
        assertEquals(75, permChangeTemplate.getValue());
        assertFalse(permChangeTemplate.isNegated());
        assertTrue(permChangeTemplate.isSkip());
        assertNotNull(permChangeTemplate.toString());

        Permission permLogin = new Permission("b_virtualserver_login", 13,
                75, false, true);
        assertEquals("b_virtualserver_login", permLogin.getName());
        assertEquals(13, permLogin.getID());
        assertEquals(75, permLogin.getValue());
        assertFalse(permLogin.isNegated());
        assertTrue(permLogin.isSkip());
        assertNotNull(permLogin.toString());

        Permission permCreateLogin = new Permission("b_serverquery_login_create", 14, 75,
                true, false, "Create a new server query login");
        assertEquals("b_serverquery_login_create", permCreateLogin.getName());
        assertEquals(14, permCreateLogin.getID());
        assertEquals(75, permCreateLogin.getValue());
        assertTrue(permCreateLogin.isNegated());
        assertFalse(permCreateLogin.isSkip());
        assertEquals("Create a new server query login", permCreateLogin.getDescription());
        assertNotNull(permCreateLogin.toString());
    }

    @Test
    public void testModifyPermissions() {
        assertTrue(basic.getClientPermissions(5).isEmpty());
        basic.addClientPermission(5, 14, null, 75, true);
        assertFalse(basic.getClientPermissions(5).isEmpty());

        assertEquals(2, basic.getChannelPermissions(103).size());
        basic.addChannelPermission(103, 140, null, 75);
        assertEquals(3, basic.getChannelPermissions(103).size());

        assertEquals(4, basic.getServerGroupPermissions(10).size());
        basic.addServerGroupPermission(10, 139, null, 75, false, true);
        assertEquals(5, basic.getServerGroupPermissions(10).size());

        assertEquals(2, basic.getChannelGroupPermissions(24).size());
        basic.addChannelGroupPermission(24, 140, null, 75);
        assertEquals(3, basic.getChannelGroupPermissions(24).size());

        assertEquals(0, basic.getChannelClientPermissions(1, 2).size());
        basic.addChannelClientPermission(1, 2, 140, null, 75);
        assertEquals(1, basic.getChannelClientPermissions(1, 2).size());


        basic.removeClientPermission(5, 14, null);
        assertTrue(basic.getClientPermissions(5).isEmpty());

        basic.removeChannelPermission(103, 140, null);
        assertEquals(2, basic.getChannelPermissions(103).size());

        basic.removeServerGroupPermission(10, 139, null);
        assertEquals(4, basic.getServerGroupPermissions(10).size());

        basic.removeChannelGroupPermission(24, 140, null);
        assertEquals(2, basic.getChannelGroupPermissions(24).size());

        basic.removeChannelClientPermission(1, 2, 140, null);
        assertEquals(0, basic.getChannelClientPermissions(1, 2).size());
    }
    @Test
    public void testGetPermissionAssignments() {
        List<PermissionAssignmentInfo> permassign = basic.getAssignmentsOfPermission(TSPermission.I_CHANNEL_JOIN_POWER.getValue());
        permassign.forEach(perms -> {
            assertNotNull(perms);
            perms.getPermID();
            perms.getFirstID();
            perms.getSecondID();
            perms.getTier();
            assertEquals(-2, perms.getPermValue());
            assertFalse(perms.isNegated());
            assertFalse(perms.isSkip());
            assertNotNull(perms.toString());
        });

        assertEquals(10, permassign.size());
        assertEquals(10, basic.getAssignmentsOfPermission(TSPermission.I_CHANNEL_JOIN_POWER.getName()).size());

        assertEquals(1, basic.getQueryAssignmentOfPermission(TSPermission.I_GROUP_MODIFY_POWER.getValue()).size());
        assertEquals(1, basic.getQueryAssignmentOfPermission(TSPermission.I_GROUP_MODIFY_POWER.getName()).size());
    }
}
