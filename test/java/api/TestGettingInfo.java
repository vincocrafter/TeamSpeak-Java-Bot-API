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

import net.devcube.vinco.teamspeakapi.api.api.property.*;
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
                query.connect("localhost", 10011, "serveradmin" +
                        "", password, 1, "GettingInfoTest", -1);
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
            client.getId();
            client.getClientId();
            assertNotNull(client.getName());
            assertNotNull(client.getUUID());
            assertNotNull(client.getUniqueIdentifier());
            client.getChannelID();
            client.getIdleTime();
            assertNotNull(client.getVersion());
            assertNotNull(client.getPlatform());
            client.isInputMuted();
            client.isOutputMuted();
            client.isInputHardwareEnabled();
            client.isOutputHardwareEnabled();
            client.isRecording();
            client.getClientDataBaseID();
            assertTrue(client.getChannelGroupID() > 0);
            assertFalse(client.getServerGroups().isEmpty());
            client.getServerGroups().forEach(group -> assertTrue(client.isInServerGroup(group)));
            client.getCreatingTime();
            client.getLastConnection();
            client.isAway();
            assertNotNull(client.getAwayMessage());
            assertNotNull(client.getClientType());
            assertTrue(client.isServerQueryClient());
            assertFalse(client.isUser());
            client.getTalkPower();
            client.isTalker();
            client.canTalk();
            client.isPrioritySpeaker();
            client.getIconID();
            client.isChannelCommander();
            assertNotNull(client.getCountry());
            client.getChannelGroupInheritedChannelID();
            assertNotNull(client.getBadges());
            assertFalse(client.hasMyTeamspeakID());
            assertNotNull(client.getMyTeamspeakID());
            assertNotNull(client.getIntegrations());
            assertNotNull(client.getMyTeamspeakAvatar());
            client.getIconID();
            assertNotNull(client.toString());
        });


        sync.getClientsDetailed().forEach(client -> {
            var sameClient = basic.getClient(client.getID());
            assertEquals(client.getID(), sameClient.getID());

            assertEquals(client.getID(), basic.getClientIDByUUID(client.getUUID()));
            assertEquals(client.getUUID(), basic.getClientUUIDByID(client.getID()));
            assertEquals(client.getUUID(), basic.getClientUUIDByDBID(client.getClientDataBaseID()));
            assertEquals(client.getName(), basic.getClientNameByID(client.getID()));
            assertEquals(client.getName(), basic.getClientNameByUUID(client.getUUID()));
            assertEquals(client.getName(), basic.getClientNameByDBID(client.getClientDataBaseID()));
            assertEquals(client.getClientDataBaseID(), basic.getClientDataBaseIDByUUID(client.getUUID()));

            assertEquals(1, basic.getClientIDsByName(client.getName()).size());

            client.isOutputOnlyMuted();
            client.getDefaultChannelID();

            assertNotNull(client.getLoginName());
            client.getTotalConnections();
            assertNotNull(client.getAvatar());
            client.isTalkPowerRequesting();
            assertNotNull(client.getTalkPowerRequestMsg());
            assertNotNull(client.getClientDescription());
            assertFalse(client.hasClientDescription());
            client.getMonthBytesUploaded();
            client.getMonthBytesDownloaded();
            client.getTotalBytesUploaded();
            client.getTotalBytesDownloaded();

            assertFalse(client.hasPhoneticName());
            assertNotNull(client.getPhoneticName());
            client.getNeededServerQueryViewPower();
            assertNotNull(client.getDefaultToken());

            assertNotNull(client.getConnection());
            assertNotNull(client.getIP());
            assertFalse(client.hasMyTeamspeakID());
            assertNotNull(client.getMyTeamspeakID());
            assertNotNull(client.getIntegrations());
            assertNotNull(client.getMyTeamspeakAvatar());
            assertNotNull(client.getBase64HashUID());
            assertNotNull(client.toString());
        });
    }

    @Test
    public void testDBClientsInfos() {
        basic.getDataBaseClients(-1, -1).forEach(client -> {
            client.getClientDataBaseID();
            client.getID();
            client.getDatabaseId();
            assertNotNull(client.getClientName());
            assertNotNull(client.getClientUUID());
            assertNotNull(client.getUUID());
            assertNotNull(client.getUniqueIdentifier());
            client.getCreatingTime();
            assertNotNull(client.getDescription());
            client.getLastConnection();
            assertNotNull(client.getLastIP());
            assertNotNull(client.getIP());
            client.getTotalConnections();
        });

        assertTrue(basic.getDataBaseClientsCount() >= 25);
        assertEquals(1, basic.getDataBaseClientIDsByClients(basic.getClients()).size());

        sync.getDataBaseClientsDetailed().forEach(dbClient -> {
            var sameClient = basic.getDataBaseClient(dbClient.getID());
            assertEquals(dbClient.getID(), sameClient.getID());
            sameClient.getAvatar();
            sameClient.getMonthBytesUploaded();
            sameClient.getMonthBytesDownloaded();
            sameClient.getTotalBytesUploaded();
            sameClient.getTotalBytesDownloaded();
            sameClient.getBase64HashUID();
            assertNotNull(sameClient.toString());
        });
    }

    @Test
    public void testChannelsInfos() {
        basic.getChannels().forEach(channel -> {
            channel.getChannelID();
            channel.getID();
            channel.getId();
            channel.getChannelId();
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
            assertNotNull(channel.toString());
        });

        assertEquals(51, basic.getChannelsByName("Channel").size());

        ChannelInfo channel = basic.getChannel(1);
        assertEquals(1, channel.getID());
        assertEquals("Default Channel", channel.getName());
        assertEquals("Default\\sChannel", channel.getOriginalName());
        assertEquals("Default Channel has no topic", channel.getTopic());
        assertEquals("4f282078-339a-4932-9d63-f567fe2f82b4", channel.getUUID());
        assertEquals("This is the default channel", channel.getDescription());
        assertEquals("files\\/virtualserver_1\\/channel_1", channel.getFilePath());
        assertEquals("files/virtualserver_1/channel_1", channel.getFilePathStr());
        assertEquals(0, channel.getDeleteDelay());
        assertSame(ChannelCodec.OPUS_VOICE, channel.getCodec());
        assertTrue(channel.isUnEncrypted());
        assertTrue(channel.hasMaxClientsUnlimited());
        assertTrue(channel.hasMaxFamilyClientsUnlimited());
        assertFalse(channel.hasMaxFamilyClientsInherited());
        assertFalse(channel.isForcedSilence());
        assertTrue(channel.getPhoneticName().isEmpty());
        assertTrue(channel.getBannerGfxURL().isEmpty());
        assertNotNull(channel.getCodecLatencyFactor());
        assertEquals(0, channel.getBannerModeInt());
        assertSame(ChannelBannerMode.NOT_ADJUST, channel.getBannerMode());
    }

    @Test
    public void testBans() {
        basic.removeAllBans();
        int banID = basic.addBan(null, "Client22", "y+ldsNsDSWF8HoNTSQcLWp\\/5Gv8=",
                null, 60, "TestBan");
        assertTrue(banID > 0);

        basic.getBans().forEach(ban -> {
            assertEquals(banID, ban.getID());
            assertEquals(60, ban.getBanDuration());
            assertEquals("TestBan", ban.getBanReason());
            ban.getCreatingTime();

            assertEquals(1, ban.getInvokerClientDataBaseID());
            assertEquals("serveradmin", ban.getInvokerClientUUID());
            assertEquals("GettingInfoTest", ban.getInvokerName());

            assertEquals("Client22", ban.getLastNickName());
            assertEquals("y+ldsNsDSWF8HoNTSQcLWp/5Gv8=", ban.getClientUUID());
            assertNotNull(ban.getIP());
            assertNotNull(ban.getMyTeamSpeakID());
            ban.getBanEnforcements();
            assertNotNull(ban.toString());
        });
        assertNotNull(sync.getBanInfo(banID));

        basic.removeBan(banID);
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
            groups.getIconID();
            groups.isSaved();
            groups.getNeededModifyPower();
            groups.getNeededMemberAddPower();
            groups.getNeededMemberRemovePower();
            assertNotNull(groups.getType());
            assertNotNull(groups.toString());
        });
    }

    @Test
    public void testChannelGroups() {
        basic.getChannelGroups().forEach(groups -> {
            groups.getChannelGroupID();
            groups.getID();
            groups.getId();
            assertNotNull(groups.getChannelGroupName());
            assertNotNull(groups.getName());
            assertNotNull(groups.getNameMode());
            groups.getSortID();
            groups.isSaved();
            groups.getNeededModifyPower();
            groups.getNeededMemberAddPower();
            groups.getNeededMemberRemovePower();
            groups.getIconID();
            assertNotNull(groups.getType());
            assertNotNull(groups.toString());
        });
    }

    @Test
    public void testComplaints() {
        QueryClientInfo queryInfo = basic.getQueryInfo();
        int queryDBID = queryInfo.getDataBaseID();
        String name = queryInfo.getName();

        basic.addComplaint(queryDBID, "TestComplaint");
        basic.getComplaints().forEach(complaints -> {
            assertNotNull(complaints.getMessage());
            assertNotNull(complaints.getTargetName());
            complaints.getTargetClientDataBaseID();
            assertNotNull(complaints.getSenderName());
            complaints.getSenderClientDataBaseID();
            complaints.getTime();
            assertNotNull(complaints.toString());
        });

        basic.getComplaintsByClient(queryDBID).forEach(complaint -> {
            assertEquals("TestComplaint", complaint.getMessage());
            assertEquals(name, complaint.getTargetName());
            assertEquals(queryDBID, complaint.getTargetClientDataBaseID());
            complaint.getSenderName();
            assertEquals(queryDBID, complaint.getSenderClientDataBaseID());
            complaint.getTime();
        });
        basic.deleteComplain(queryDBID, queryDBID);
        assertEquals(0, basic.getComplaintsByClient(queryDBID).size());
        basic.addComplaint(queryDBID, "TestComplaint");
        assertEquals(1, basic.getComplaintsByClient(queryDBID).size());
        basic.deleteAllComplains(queryDBID);
        assertEquals(0, basic.getComplaintsByClient(queryDBID).size());
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

        assertDoesNotThrow(() -> {
            CreatedQueryLogin login = basic.createQueryLogin("Test", -1);

            login.getClientDataBaseID();
            assertEquals("Test", login.getClientLoginName());
            assertNotNull(login.getClientLoginPassword());
            assertEquals(0, login.getServerID());
            assertNotNull(login.toString());

            assertEquals(1, basic.getQueryLogins("Test", -1, -1).size());
            basic.deleteQueryLogin(login.getClientDataBaseID());
        });

        sync.selectVirtualServer(1);

        assertDoesNotThrow(() -> {
            CreatedQueryLogin login = basic.createQueryLogin("Test", 3);
            login.getClientDataBaseID();
            assertEquals("Test", login.getClientLoginName());
            assertNotNull(login.getClientLoginPassword());
            assertEquals(1, login.getServerID());
            assertEquals(3, login.getClientDataBaseID());

            assertEquals(1, basic.getQueryLogins("Test", -1, -1).size());
            basic.deleteQueryLogin(login.getClientDataBaseID());
        });
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
        assertThrows(NullPointerException.class, () -> {

            createdServer.getID();
            createdServer.getKey();
            createdServer.getPort();
            createdServer.getServerID();
            basic.deleteVirtualServer(createdServer.getServerID());
        });
        assertNull(createdServer);

        assertEquals(1, basic.getVirtualServers().size());
    }

    @Test
    public void testCreatedAPIKey() {
        basic.getAPIKeys(2, -1, -1).forEach(keys -> basic.deleteAPIKey(keys.getID()));
        CreatedAPIKey createdAPIKey = basic.addAPIKey(APIScope.READ, 1, 2);
        assertNotNull(createdAPIKey);

        assertEquals(2, createdAPIKey.getClientDataBaseID());
        assertEquals(1, createdAPIKey.getServerID());
        assertNotNull(createdAPIKey.getAPIKey());
        assertTrue(createdAPIKey.getTimeCreated() > 0);
        assertTrue(createdAPIKey.getTimeExpire() > 0);
        assertTrue(createdAPIKey.getTimeLeft() > 86.000);
        assertSame(createdAPIKey.getScope(), APIScope.READ);
        assertNotNull(createdAPIKey.toString());

        List<CreatedAPIKey> createdAPIKeys = basic.getAPIKeys(2, -1, -1);
        assertEquals(1, createdAPIKeys.size());
        createdAPIKeys.forEach(keys -> {
            assertEquals(createdAPIKey.getID(), keys.getID());
            assertEquals(createdAPIKey.getClientDataBaseID(), keys.getClientDataBaseID());
            assertEquals(createdAPIKey.getServerID(), keys.getServerID());
            assertEquals(createdAPIKey.getTimeCreated(), keys.getTimeCreated());
            assertEquals(createdAPIKey.getTimeExpire(), keys.getTimeExpire());
            assertEquals(createdAPIKey.getTimeLeft(), keys.getTimeLeft());
            assertEquals(createdAPIKey.getScope(), keys.getScope());
            basic.deleteAPIKey(keys.getID());
        });

        assertEquals(0, basic.getAPIKeys(2, -1, -1).size());
    }

    @Test
    public void testCreateSnapshot() {
        CreatedSnapshot snapshot = basic.createSnapshot(null);
        assertNotNull(snapshot);
        assertNotNull(snapshot.getData());
        assertNotNull(snapshot.getSalt());
        snapshot.getVersion();
        assertNotNull(snapshot.toString());

        CreatedSnapshot passwordSnapshot = basic.createSnapshot("Password");
        assertNotNull(passwordSnapshot);
        assertNotNull(passwordSnapshot.getSalt());
    }

    @Test
    public void testFileInfo() {
        ChannelInfo channel = basic.getChannel(1);
        List<FileInfo> filesByPath = basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/");
        assertEquals(4, filesByPath.size());
        filesByPath.forEach(files -> {
            files.getChannelID();
            files.getDateTime();
            assertNotNull(files.getFileType());
            files.getFileTypeInt();
            assertNotNull(files.getName());
            assertNotNull(files.getPath());
            files.getSize();
            assertSame(FileType.DIRECTORY, files.getFileType());
            assertTrue(files.isDirectory());
            assertNotNull(files.toString());
        });

        List<FileInfo> filesFromTest = basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/Test/");
        assertEquals(3, filesFromTest.size());
        filesFromTest.forEach(files -> {
            assertNotNull(files.getFileType());
            assertSame(FileType.FILE, files.getFileType());
            assertTrue(files.isFile());
        });

        List<FileInfo> filesFromTest1 = basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/Test1/");
        assertEquals(1, filesFromTest1.size());
        assertEquals("Test1.txt", filesFromTest1.get(0).getName());
        assertSame(FileType.FILE, filesFromTest1.get(0).getFileType());
        assertTrue(filesFromTest1.get(0).isFile());
        assertNotNull(basic.getFileInfo(channel.getID(), channel.getPassword(), "/Test1/Test1.txt"));

        List<FileInfo> filesFromTest2 = basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/Test2/");
        assertEquals(1, filesFromTest2.size());
        assertEquals("Test2.txt", filesFromTest2.get(0).getName());
        assertSame(FileType.FILE, filesFromTest2.get(0).getFileType());
        assertTrue(filesFromTest2.get(0).isFile());
        assertNotNull(basic.getFileInfo(channel.getID(), channel.getPassword(), "/Test2/Test2.txt"));

        List<FileInfo> filesFromTest3 = basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/Test3/");
        assertEquals(1, filesFromTest3.size());
        assertEquals("Test3.txt", filesFromTest3.get(0).getName());
        assertSame(FileType.FILE, filesFromTest3.get(0).getFileType());
        assertTrue(filesFromTest3.get(0).isFile());
        assertNotNull(basic.getFileInfo(channel.getID(), channel.getPassword(), "/Test3/Test3.txt"));


        List<String> fileNames = List.of("/Test1/Test1.txt", "/Test2/Test2.txt", "/Test3/Test3.txt");
        List<FileInfo> files = basic.getFileInfos(channel.getID(), channel.getPassword(), fileNames);
        assertEquals(3, files.size());

        basic.renameFile(channel.getID(), channel.getPassword(), "/Test3", "/Test5");
        assertEquals("Test3.txt", basic.getFileInfo(channel.getID(), channel.getPassword(), "/Test5/Test3.txt").getName());

        basic.createFileDirectory(channel.getID(), channel.getPassword(), "/Test4");
        assertEquals(5, basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/").size());
        basic.deleteFile(channel.getID(), channel.getPassword(), "/Test4");
        basic.deleteFile(channel.getID(), channel.getPassword(), "/Test5");
        assertEquals(3, basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/").size());

        ChannelInfo channel2 = basic.getChannel(54);
        basic.moveFile(channel.getID(), channel.getPassword(), "/Test2/Test2.txt",
                channel2.getID(), channel.getPassword(), "/MovedTest2.txt");
        assertEquals(0, basic.getChannelFilesByPath(channel.getID(), channel.getPassword(), "/Test2/").size());
        assertEquals(1, basic.getChannelFilesByPath(channel2.getID(), channel2.getPassword(), "/").size());
        assertEquals("MovedTest2.txt", basic.getFileInfo(channel2.getID(), channel2.getPassword(), "/MovedTest2.txt").getName());
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
        info.getMaxClients();
        info.getVirtualServersRunning();
        assertNotNull(info.getTimestampUTC());
        info.getFileTransfersBytesSend();
        info.getFileTransfersBytesReceived();
        assertNotNull(info.getConnection());
        assertNotNull(info.toString());
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
    public void testGroupPermissions() {
        basic.getServerGroups().forEach(groups -> {
            basic.getServerGroupPermissions(groups.getID()).forEach(perm -> {
                assertNotNull(perm);
                assertNotNull(perm.getName());
            });
        });

        basic.getChannelGroups().forEach(groups -> {
            basic.getChannelGroupPermissions(groups.getID()).forEach(perm -> {
                assertNotNull(perm);
                assertNotNull(perm.getName());
            });
        });
    }

    @Test
    public void testPrivilegeKeyInfo() {
        String key = basic.createPrivilegeKey(PrivilegeKeyType.SERVER_GROUP,
                10, 0, "TestDescription", null);
        basic.getPrivilegeKeys().forEach(keys -> {
            keys.getChannelID();
            keys.getCreatedTime();
            assertNotNull(keys.getDescription());
            assertNotNull(keys.getKey());
            keys.getKeyType();
            keys.getServerGroup();
            assertNotNull(keys.getType());
            assertNotNull(keys.toString());
        });

        basic.deletePrivilegeKey(key);
    }

    @Test
    public void testQueryClientInfo() {
        QueryClientInfo query = basic.getQueryInfo();
        assertNotNull(query.getNickName());
        assertNotNull(query.getName());
        assertNotNull(query.getUUID());
        assertNotNull(query.getUniqueIdentifier());
        query.getID();
        query.getId();
        query.getClientID();
        query.getChannelID();
        query.getDataBaseID();
        query.getVirtualServerID();
        query.getVirtualServerPort();
        assertNotNull(query.getVirtualServerUUID());
        assertNotNull(query.toString());
    }

    @Test
    public void testTempPasswordInfo() {
        basic.addVirtualServerTempPassword("Password", "TestDesc", 200, 1, "ChPassword");

        basic.getVirtualServerTempPasswords().forEach(pw -> {
            assertNotNull(pw.getPassword());
            assertNotNull(pw.getDescription());
            pw.getEndTime();
            assertNotNull(pw.getInvokerName());
            assertNotNull(pw.getInvokerUUID());
            pw.getStartTime();
            pw.getTargetChannelID();
            assertNotNull(pw.toString());
        });
        basic.deleteVirtualServerTempPassword("Password");
        assertEquals(0, basic.getVirtualServerTempPasswords().size());
    }

    @Test
    public void testVirtualServerInfo() {
        VirtualServerInfo server = basic.getVirtualServerInfo();

        assertEquals(1, basic.getVirtualServerIDByPort(server.getServerPort()));

        server.getServerID();
        server.getID();
        assertNotNull(server.getServerUUID());
        assertNotNull(server.getUUID());
        assertNotNull(server.getServerName());
        assertNotNull(server.getName());
        assertNotNull(server.getServerNickName());
        assertNotNull(server.getWelcomeMessage());
        assertNotNull(server.getStatus());
        assertSame(EncryptionMode.INDIVIDUAL, server.getEncryptionMode());
        assertNotNull(server.getPlatform());
        assertNotNull(server.getVersion());
        assertNotNull(server.getPassword());
        server.getMaxclients();
        server.getChannels();
        server.getCreatingTime();
        server.getOnlineClientsSize();
        assertNotNull(server.getHostMessage());
        server.getHostMessageModeInt();
        assertSame(HostMessageMode.NONE, server.getHostMessageMode());
        assertNotNull(server.getDefaultFilePath());
        server.getDefaultServerGroup();
        server.getDefaultChannelGroup();
        server.getDefaultChannelAdminGroup();
        assertNotNull(server.getMaxDownloadTotalBandwidth());
        assertNotNull(server.getMaxUploadTotalBandwidth());
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
        server.getQuerysOnlineSize();
        assertNotNull(server.getHostbannerGFXURL());
        server.getHostBannerGFXInterval();
        server.getAntifloodPointsTickReduce();
        server.getAntifloodPointsNeededCommandBlock();
        server.getAntifloodPointsNeededIPBlock();
        assertNotNull(server.getHostbuttonTooltip());
        assertNotNull(server.getHostbuttonURL());
        assertNotNull(server.getHostbuttonGFXURL());
        assertNotNull(server.getDownloadQuota());
        assertNotNull(server.getUploadQuota());
        server.getMonthBytesDownloaded();
        server.getMonthBytesUploaded();
        server.getTotalBytesDownloaded();
        server.getTotalBytesUploaded();
        server.getMachineID();
        server.getMinClientVersion();
        assertNotNull(server.getPhoneticName());
        assertNotNull(server.getIP());
        assertTrue(server.isWeblistEnabled());
        assertFalse(server.isAskForPrivilegeKey());
        assertEquals(0, server.getHostbannerMode());
        assertEquals(0, server.getChannelTempDeleteDefaultDelay());
        server.getMinAndroidVersion();
        server.getMinIOSVersion();
        server.getAntifloodPointsNeededPluginBlock();
        assertNotNull(server.getCapabilityExtensions());
        assertNotNull(server.getFileStorageClass());
        server.getPacketsSendSpeech();
        server.getBytesPacketsSendSpeech();
        server.getPacketsRecivedSpeech();
        server.getBytesPacketsRecivedSpeech();
        server.getPacketsSendKeepalive();
        server.getBytesPacketsSendKeepalive();
        server.getPacketsRecivedKeepalive();
        server.getBytesPacketsRecivedKeepalive();
        server.getPacketsSendControl();
        server.getBytesPacketsSendControl();
        server.getPacketsRecivedControl();
        server.getBytesPacketsRecivedControl();
        assertNotNull(server.getConnectionInfo());
        assertNotNull(server.toString());
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

        assertEquals(1, basic.searchDBIDsCustomInfo("Test1", "Test").size());

        customInfo.keySet().forEach(info -> basic.deleteCustomInfo(dbClientID, info));
        assertNotNull(basic.getCustomInfo(dbClientID));
    }

    @Test
    public void testGetHelp() {
        String help = basic.getHelp();
        assertEquals(150, help.split(System.lineSeparator()).length);
    }

    @Test
    public void testGetLog() {
        assertTrue(sync.getInstanceLog().size() >= 23);
        assertEquals(20, sync.getInstanceLog(20).size());

        assertTrue(sync.getVirtualServerLog().size() >= 1);
        assertEquals(1, sync.getVirtualServerLog(1).size());
    }
}
