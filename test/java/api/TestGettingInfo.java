/**
 * Projekt: TEAMSPEAK - TestBot
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.11.2023
 * 
 * Uhrzeit : 20:31:10
 */
package java.api;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerProperty;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.BanInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ComplainInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ConnectionInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedQueryLogin;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedVirtualServer;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.FileInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.FileTransferInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.HostInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.InstanceInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.OfflineMessageInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.PermissionAssignmentInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.PrivilegeKeyInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ServerGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.TempPasswordInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.VirtualServerInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class TestGettingInfo {

	private static Ts3ServerQuery query;
	private static Ts3BasicAPI basic;
	private static Ts3SyncAPI sync;
	

	@BeforeAll
	public static void connectQuery() {
		String password = System.getenv("TS3_SERVER_PASSWORD");


		query = new Ts3ServerQuery();

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "Test", -1);
		} catch (IOException | QueryLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		assertTrue(query.getSyncAPI().isConnected());

		basic = query.getBasicAPI();
		sync = query.getSyncAPI();
	}
	
	@AfterAll
	public static void quitQuery() {
		query.stopQuery();
	}
	
	@Test
	public void testClientsInfos() {
		for (ClientInfo clients : basic.getClients()) {
			clients.getClientID();
			clients.getID();
			clients.getName();
			clients.getClientUUID();
			clients.getChannelGroupID();
			clients.getChannelID();
			clients.getClientType();
			clients.isChannelCommander();
			clients.isAway();
			clients.getServerGroups();
		}
		
		for (ClientInfo clients : basic.getClientsByIDs(basic.getClientIDs())) {
			ClientInfo sameClient = basic.getClient(clients.getID());
			assertEquals(clients.getID(), sameClient.getID());
		}
	}

	@Test
	public void testDBClientsInfos() {
		for (DataBaseClientInfo clients : basic.getDataBaseClients(-1, -1)) {
			clients.getClientDataBaseID();
			clients.getID();
			clients.getClientName();
			clients.getClientUUID();
			clients.getCreatingTime();
			clients.getDescription();
			clients.getLastConnection();
			clients.getLastIP();
			clients.getTotalConnections();
		}

		for (DataBaseClientInfo clients : sync.getDataBaseClientsDetailed()) {
			clients.getClientDataBaseID();
			clients.getID();
			clients.getClientName();
			clients.getClientUUID();
			clients.getCreatingTime();
			clients.getDescription();
			clients.getLastConnection();
			clients.getLastIP();
			clients.getTotalConnections();
		}
	}

	@Test
	public void testChannelsInfos() {
		for (ChannelInfo channels : basic.getChannels()) {
			channels.getChannelID();
			channels.getID();
			channels.getName();
			channels.getDescription();
			channels.getTotalClients();
			channels.getMaxClients();
		}
	}

	@Test
	public void testBans() {
		for (BanInfo bans : basic.getBans()) {
			bans.getBanID();
			bans.getClientUUID();
			bans.getInvokerName();
			bans.getBanDuration();
		}
	}

	@Test
	public void testServerGroups() {
		for (ServerGroupInfo groups : basic.getServerGroups()) {
			groups.getServerGroupID();
			groups.getID();
			groups.getServerGroupName();
			groups.getName();
			groups.getNameMode();
			groups.getSortID();
			groups.getType();

		}
	}

	@Test
	public void testChannelGroups() {
		for (ChannelGroupInfo groups : basic.getChannelGroups()) {
			groups.getChannelGroupID();
			groups.getID();
			groups.getChannelGroupName();
			groups.getName();
			groups.getNameMode();
			groups.getSortID();
			groups.getType();
		}
	}

	@Test
	public void testComplains() {
		for (ComplainInfo complains : basic.getComplains()) {
			complains.getMessage();
			complains.getTargetName();
			complains.getTargetClientDataBaseID();
			complains.getSenderName();
			complains.getSenderClientDataBaseID();
			complains.getTime();
		}
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
		assertThrows(NullPointerException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				CreatedQueryLogin login = basic.createQueryLogin("Test", 3);
				login.getClientDataBaseID();
				login.getClientLoginName();
				login.getClientLoginPassword();
				basic.deleteQueryLogin(3);
				
			}
		});
		sync.selectVirtualServer(1);
	}
	
	@Test
	public void testCreatedVirtualServer() {
		HashMap<VirtualServerProperty, String> props = new HashMap<>();
		props.put(VirtualServerProperty.VIRTUALSERVER_NAME, "TestServer");
		CreatedVirtualServer createdServer = basic.createVirtualServer(props);
		
		/* Because in most of the cases an other virtual server could
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
	
		basic.getVirtualServers();
	}
	
	@Test
	public void testFileInfo() {
		for (ChannelInfo channels : basic.getChannels()) {
			ChannelInfo channel = basic.getChannel(channels.getID());
			List<FileInfo> info = basic.getChannelFilesByPath(channel.getChannelID(), channel.getPassword(), "/");
			for (FileInfo files : info) {
				files.getChannelID();
				files.getDateTime();
				files.getFileType();
				files.getFileTypeInt();
				files.getName();
				files.getPath();
				files.getSize();
			}
		}
	}

	@Test
	public void testFileTransferInfo() {
		for (FileTransferInfo files : basic.getFileTransfers()) {
			files.getChannelID();
			files.getClientFileTransferID();
			files.getClientID();
			files.getCurrentSpeed();
			files.getFilePath();
			files.getName();
			files.getServerFileTransferID();
			files.getSize();
			files.getSizeDone();
			files.getStatus();
			files.getVirtualServerID();
		}
	}

	@Test
	public void testHostInfo() {
		HostInfo info = basic.getHostInfo();
		info.getChannels();
		info.getClientsOnline();
		info.getInstanceUptime();
		info.getMaxClients();
		info.getVirtualServersRunning();
	}

	@Test
	public void testInstanceInfo() {
		InstanceInfo info = basic.getInstanceInfo();
		info.getDataBaseVersion();
		info.getFileTransferPort();
		info.getMaxDownloadTotalBandwidth();
		info.getMaxUploadTotalBandwidth();
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
		List<OfflineMessageInfo> messages = basic.getOfflineMessages();
		for (OfflineMessageInfo msg : messages) {
			msg.getClientUUID();
			msg.getID();
			msg.getMessage();
			msg.getMessageID();
			msg.getSubject();
			msg.getTime();
		}
	}

	@Test
	public void testPermissions() {
		for (ServerGroupInfo groups : basic.getServerGroups()) {
			for (Permission groupPerm : basic.getServerGroupPermissions(groups.getID())) {
				groupPerm.getDescription();
				groupPerm.getPermID();
				groupPerm.getName();
				groupPerm.getValue();
				groupPerm.isNegated();
				groupPerm.isSkip();
			}
		}
	}

	@Test
	public void testPermissionAssignmentInfo() {
		List<Integer> permsList = basic.getPermissionIDs(basic.getPermissionList());
		List<PermissionAssignmentInfo> permassig = basic.getAssignmentsOfPermissions(permsList, new ArrayList<>());
		for (PermissionAssignmentInfo perm : permassig) {
			perm.getPermID();
			perm.getFirstID();
			perm.getPermValue();
			perm.getSecondID();
			perm.getTier();
			perm.isNegated();
			perm.isSkip();
		}
	}

	@Test
	public void testPrivilegeKeyInfo() {
		for (PrivilegeKeyInfo keys : basic.getPrivilegeKeys()) {
			keys.getChannelID();
			keys.getCreatedTime();
			keys.getDescription();
			keys.getKey();
			keys.getKeyType();
			keys.getServerGroup();
			keys.getType();
		}
	}

	@Test
	public void testQueryClientInfo() {
		QueryClientInfo query = basic.getQueryInfo();
		query.getNickName();
		query.getName();
		query.getNickName();
		query.getUUID();
		query.getID();
		query.getClientID();
		query.getChannelID();
		query.getDataBaseID();
		query.getVirtualServerID();
		query.getVirtualServerPort();
		query.getVirtualServerUUID();
	}

	@Test
	public void testTempPasswordInfo() {
		List<TempPasswordInfo> passwords = basic.getVirtualServerTempPasswords();
		for (TempPasswordInfo pw : passwords) {
			pw.getDescription();
			pw.getEndTime();
			pw.getInvokerName();
			pw.getInvokerUUID();
			pw.getPassword();
			pw.getStartTime();
			pw.getTargetChannelID();
		}
	}

	@Test
	public void testVirtualServerInfo() {
		VirtualServerInfo server = basic.getVirtualServerInfo();
		server.getServerUUID();
		server.getUUID();
		server.getServerName();
		server.getName();
		server.getWelcomeMessage();
		server.getStatus();
		server.getServerID();
		server.getID();
		server.getPlatform();
		server.getVersion();
		server.getPassword();
		server.getMaxclients();
		server.getChannels();
		server.getCreatingTime();
		server.getOnlineClientsSize();
		server.getHostMessage();
		server.getHostMessageMode();
		server.getDefaultFilePath();
		server.getDefaultServerGroup();
		server.getDefaultChannelGroup();
		server.getDefaultChannelAdminGroup();
		server.hasPassword();
		server.getHostBannerURL();
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
		server.getTotalPing();
	}
	
	@Test
	public void testCustomInfo() {
		List<Integer> fclients = basic.getDataBaseClientIDsByClients(basic.getClients().stream().filter(clients -> clients.isUser()).toList());
		
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test1", "Test"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test2", "Test"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test3", "-1"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test4", "1"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test5", "true"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test6", "false"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test7", "5,10,11,-1,5"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test8", "Coder=true"));
		fclients.forEach(clients -> basic.setCustomInfo(clients, "Test9", "a b c d e f g h"));
		
		fclients.forEach(clients -> System.out.println(basic.getCustomInfo(clients)));
		fclients.forEach(clients -> basic.getCustomInfo(clients).keySet().forEach(info -> basic.deleteCustomInfo(clients, info)));
		fclients.forEach(clients -> basic.getCustomInfo(clients));
	}
 	
}
