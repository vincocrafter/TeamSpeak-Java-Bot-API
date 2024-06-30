/**
 * Projekt: TEAMSPEAK - TestBot
 * <p>
 * Autor: Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 05.11.2023
 * <p>
 * Uhrzeit : 20:31:10
 */
package api;

import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerProperty;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.*;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestGettingInfo {

    private static Ts3ServerQuery query;
    private static Ts3BasicAPI basic;
    private static Ts3SyncAPI sync;

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
        sync = query.getSyncAPI();
    }

    @AfterAll
    public static void quitQuery() {
        query.stopQuery();
    }

    @Test
    public void testClientsInfos() {
        basic.getClients().forEach(client -> {
            client.getID();
            assertNotNull(client.getName());
            assertNotNull(client.getClientUUID());
            client.getChannelID();
            assertNotNull(client.getClientType());
            client.isChannelCommander();
            client.isAway();
            assertNotNull(client.getServerGroups());
        });

        basic.getClientsByIDs(basic.getClientIDs()).forEach(client -> {
            var sameClient = basic.getClient(client.getID());
            assertEquals(client.getID(), sameClient.getID());
        });
    }

    @Test
    public void testDBClientsInfos() {
        basic.getDataBaseClients(-1, -1).forEach(client -> {
            client.getClientDataBaseID();
            client.getID();
            assertNotNull(client.getClientName());
            assertNotNull(client.getClientUUID());
            client.getCreatingTime();
            assertNotNull(client.getDescription());
            client.getLastConnection();
            assertNotNull(client.getLastIP());
            client.getTotalConnections();
        });

        sync.getDataBaseClients().forEach(client -> {
            client.getClientDataBaseID();
            client.getID();
            assertNotNull(client.getClientName());
            assertNotNull(client.getClientUUID());
            client.getCreatingTime();
            assertNotNull(client.getDescription());
            client.getLastConnection();
            assertNotNull(client.getLastIP());
            client.getTotalConnections();
        });
    }

    @Test
    public void testChannelsInfos() {
        basic.getChannels().forEach(channel -> {
            channel.getChannelID();
            channel.getID();
            channel.getChannelParentID();
            assertNotNull(channel.getName());
            assertNotNull(channel.getCodec());
            channel.getQuality();
            channel.getMaxClients();
            channel.getMaxFamilyClients();
            channel.getOrder();
            channel.isPermanent();
            channel.isSemiPermanent();
            channel.isDefault();
            channel.hasPassword();
            channel.getNeededTalkPower();
            channel.getIconID();
            channel.getSecondsEmpty();
        });
    }

    @Test
    public void testBans() {
        basic.getBans().forEach(ban -> {
            ban.getBanID();
            assertNotNull(ban.getClientUUID());
            assertNotNull(ban.getInvokerName());
            ban.getBanDuration();
        });
    }

    @Test
    public void testServerGroups() {
        basic.getServerGroups().forEach(groups -> {
            groups.getServerGroupID();
            groups.getID();
            assertNotNull(groups.getServerGroupName());
            assertNotNull(groups.getName());
            assertNotNull(groups.getNameMode());
            groups.getSortID();
            assertNotNull(groups.getType());
        });
    }

    @Test
    public void testChannelGroups() {
        basic.getChannelGroups().forEach(groups -> {
            groups.getChannelGroupID();
            groups.getID();
            assertNotNull(groups.getChannelGroupName());
            assertNotNull(groups.getName());
            assertNotNull(groups.getNameMode());
            groups.getSortID();
            assertNotNull(groups.getType());
        });
    }

    @Test
    public void testComplains() {
        basic.getComplains().forEach(complain -> {
            assertNotNull(complain.getMessage());
            assertNotNull(complain.getTargetName());
            complain.getTargetClientDataBaseID();
            assertNotNull(complain.getSenderName());
            complain.getSenderClientDataBaseID();
            complain.getTime();
        });
    }

    @Test
    public void testConnectionInfo() {
        ConnectionInfo conInfo = basic.getConnectionInfo();
        conInfo.getFileTransfersBandwidthSend();
        conInfo.getFileTransfersBandwidthReceived();
        conInfo.getFileTransfersBytesSend();
        conInfo.getFileTransfersBytesReceived();
        conInfo.getPacketsSendTotal();
        conInfo.getBytesSendTotal();
        conInfo.getPacketsReceivedTotal();
        conInfo.getBytesReceivedTotal();
        conInfo.getBranwidthSendLastSecondTotal();
        conInfo.getBranwidthSendLastMinuteTotal();
        conInfo.getBranwidthReceivedLastSecondTotal();
        conInfo.getBranwidthReceivedLastMinuteTotal();
        conInfo.getConnectedTime();
        conInfo.getPacketlossTotal();
        conInfo.getPing();
    }

    @Test
    public void testCreatedQueryLogin() {
        sync.selectVirtualServer(0);
        assertThrows(NullPointerException.class, () -> {
            CreatedQueryLogin login = basic.createQueryLogin("Test", 3);
            login.getClientLoginName();
            basic.deleteQueryLogin(3);

        });
        sync.selectVirtualServer(1);
    }

    @Test
    public void testCreatedVirtualServer() {
        HashMap<VirtualServerProperty, String> props = new HashMap<>();
        props.put(VirtualServerProperty.VIRTUALSERVER_NAME, "TestServer");
        CreatedVirtualServer createdServer = basic.createVirtualServer(props);

        /* Because in most of the cases another virtual server could
         * not be created due to licencing reasons.
         * But if you have the licence this test
         * could work and the createdServer above would
         * not be null.
         */
        assertThrows(NullPointerException.class, new Executable() {

            @Override
            public void execute() throws Throwable {

                createdServer.getID();
                createdServer.getKey();
                createdServer.getPort();
                createdServer.getServerID();
                basic.deleteVirtualServer(createdServer.getServerID());
            }
        });
        assertNull(createdServer);

        assertEquals(1, basic.getVirtualServers().size());
    }

    @Test
    public void testFileInfo() {
        for (ChannelInfo channels : basic.getChannels()) {
            ChannelInfo channel = basic.getChannel(channels.getID());
            List<FileInfo> filesByPath = basic.getChannelFilesByPath(channel.getChannelID(), channel.getPassword(), "/");
            filesByPath.forEach(files -> {
                files.getChannelID();
                files.getDateTime();
                assertNotNull(files.getFileType());
                files.getFileTypeInt();
                assertNotNull(files.getName());
                assertNotNull(files.getPath());
                files.getSize();
            });
        }
    }

    @Test
    public void testFileTransferInfo() {
        basic.getFileTransfers().forEach(file -> {
            file.getChannelID();
            file.getClientFileTransferID();
            file.getClientID();
            file.getCurrentSpeed();
            assertNotNull(file.getFilePath());
            assertNotNull(file.getName());
            file.getServerFileTransferID();
            file.getSize();
            file.getSizeDone();
            file.getStatus();
            file.getVirtualServerID();
        });
    }

    @Test
    public void testHostInfo() {
        HostInfo info = basic.getHostInfo();
        info.getChannels();
        info.getClientsOnline();
        info.getInstanceUptime();
        info.getMaxClients();
        info.getVirtualServersRunning();
        assertNotNull(info.getTimestampUTC());
        info.getFileTransfersBytesSend();
        info.getFileTransfersBytesReceived();
        assertNotNull(info.getConnection());
    }

    @Test
    public void testInstanceInfo() {
        InstanceInfo info = basic.getInstanceInfo();
        info.getDataBaseVersion();
        info.getFileTransferPort();
        assertNotNull(info.getMaxDownloadTotalBandwidth());
        assertNotNull(info.getMaxUploadTotalBandwidth());
        info.getGuestServerQueryGroup();
        info.getServerQueryFloodCommands();
        info.getServerQueryFloodTime();
        info.getServerQueryBanTime();
        info.getTemplateServerAdminGroup();
        info.getTemplateServerDefaultGroup();
        info.getTemplateChannelAdminGroup();
        info.getTemplateChannelDefaultGroup();
        info.getPermissionsVersion();
        info.getPendingConnectionsPerIP();
        info.getServerQueryMaxConnectionsPerIP();
    }

    @Test
    public void testOfflineMessages() {
        basic.getOfflineMessages().forEach(msg -> {
            assertNotNull(msg.getClientUUID());
            msg.getID();
            assertNotNull(msg.getMessage());
            msg.getMessageID();
            assertNotNull(msg.getSubject());
            msg.getTime();
        });
    }

    @Test
    public void testPermissions() {
        basic.getServerGroups().forEach(groups -> {
            basic.getServerGroupPermissions(groups.getID()).forEach(perm -> {
                assertNotNull(perm.getName());
            });
        });
    }

    @Test
    public void testPrivilegeKeyInfo() {
        basic.getPrivilegeKeys().forEach(keys -> {
            keys.getChannelID();
            keys.getCreatedTime();
            assertNotNull(keys.getDescription());
            assertNotNull(keys.getKey());
            keys.getKeyType();
            keys.getServerGroup();
            assertNotNull(keys.getType());
        });
    }

    @Test
    public void testQueryClientInfo() {
        QueryClientInfo query = basic.getQueryInfo();
        assertNotNull(query.getNickName());
        assertNotNull(query.getName());
        assertNotNull(query.getUUID());
        query.getID();
        query.getClientID();
        query.getChannelID();
        query.getDataBaseID();
        query.getVirtualServerID();
        query.getVirtualServerPort();
        assertNotNull(query.getVirtualServerUUID());
    }

    @Test
    public void testTempPasswordInfo() {
        basic.getVirtualServerTempPasswords().forEach(pw -> {
            assertNotNull(pw.getDescription());
            pw.getEndTime();
            assertNotNull(pw.getInvokerName());
            assertNotNull(pw.getInvokerUUID());
            assertNotNull(pw.getPassword());
            pw.getStartTime();
            pw.getTargetChannelID();
        });
    }

    @Test
    public void testVirtualServerInfo() {
        VirtualServerInfo server = basic.getVirtualServerInfo();
        assertNotNull(server.getServerUUID());
        assertNotNull(server.getUUID());
        assertNotNull(server.getServerName());
        assertNotNull(server.getName());
        assertNotNull(server.getWelcomeMessage());
        assertNotNull(server.getStatus());
        server.getServerID();
        server.getID();
        assertNotNull(server.getPlatform());
        assertNotNull(server.getVersion());
        assertNotNull(server.getPassword());
        server.getMaxclients();
        server.getChannels();
        server.getCreatingTime();
        server.getOnlineClientsSize();
        assertNotNull(server.getHostMessage());
        server.getHostMessageMode();
        assertNotNull(server.getDefaultFilePath());
        server.getDefaultServerGroup();
        server.getDefaultChannelGroup();
        server.getDefaultChannelAdminGroup();
        server.hasPassword();
        assertNotNull(server.getHostBannerURL());
        server.getComplainAutoBanCount();
        server.getAutoBanTime();
        server.getComplainRemoveTime();
        server.getMinClientsBeforeForcedSilence();
        server.getPrioritySpeakerDimmModificator();
        server.getClientConnections();
        server.getQueryConnections();
        server.getServerPort();
        server.isAutoStart();
        server.getSecurityLevel();
        server.isLogClient();
        server.isLogQuery();
        server.isLogChannel();
        server.isLogPermissions();
        server.isLogServer();
        server.isLogFiletransfer();
        server.getReservedSlots();
        server.getServerIconID();
        server.getTotalPacketlossSpeech();
        server.getTotalPacketlossKeepAlive();
        server.getTotalPacketlossControl();
        assertNotNull(server.getConnectionInfo());
    }

    @Test
    public void testCustomInfo() {
        int dbClientID = 20;
        basic.setCustomInfo(dbClientID, "Test1", "Test");
        basic.setCustomInfo(dbClientID, "Test2", "Test");
        basic.setCustomInfo(dbClientID, "Test3", "-1");
        basic.setCustomInfo(dbClientID, "Test4", "1");
        basic.setCustomInfo(dbClientID, "Test5", "true");
        basic.setCustomInfo(dbClientID, "Test6", "false");
        basic.setCustomInfo(dbClientID, "Test7", "5,10,11,-1,5");
        basic.setCustomInfo(dbClientID, "Test8", "Coder=true");
        basic.setCustomInfo(dbClientID, "Test9", "a b c d e f g h");

        Map<String, String> customInfo = basic.getCustomInfo(dbClientID);
        assertEquals(9, customInfo.size());
        assertEquals("Test", customInfo.get("Test1"));
        assertEquals("Test", customInfo.get("Test2"));
        assertEquals("-1", customInfo.get("Test3"));
        assertEquals("1", customInfo.get("Test4"));
        assertEquals("true", customInfo.get("Test5"));
        assertEquals("false", customInfo.get("Test6"));
        assertEquals("5,10,11,-1,5", customInfo.get("Test7"));
        assertEquals("Coder=true", customInfo.get("Test8"));
        assertEquals("a b c d e f g h", customInfo.get("Test9"));

        customInfo.keySet().forEach(info -> basic.deleteCustomInfo(dbClientID, info));


        assertNotNull(basic.getCustomInfo(dbClientID));
    }

}
