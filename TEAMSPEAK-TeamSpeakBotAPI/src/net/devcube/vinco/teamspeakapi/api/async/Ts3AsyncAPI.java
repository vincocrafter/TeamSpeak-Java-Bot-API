/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author: vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 19:14:06
 */
package net.devcube.vinco.teamspeakapi.api.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.devcube.vinco.teamspeakapi.api.api.property.APIScope;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.ClientProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.EventType;
import net.devcube.vinco.teamspeakapi.api.api.property.InstanceProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.LogLevel;
import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupLevel;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerProperty;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandBuilder;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture.Transformator;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.BanInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ComplainInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ConnectionInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedAPIKey;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedQueryLogin;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedSnapshot;
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
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

public class Ts3AsyncAPI {

	private Ts3ServerQuery query;
	private QueryWriter writer;
	protected static final String TS_INFO_SEPARATOR = "\\|";

	public Ts3AsyncAPI(Ts3ServerQuery query) {
		this.query = query;
		this.writer = query.getWriter();
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	public CommandFuture<String> getHelp() {
		return getHelp("help");
	}

	public CommandFuture<String> getHelp(String command) {
		return executeCommandGetStringResult(CommandBuilder.buildHelpCommand(command));
	}

	public CommandFuture<Void> login(String username, String password) {
		return executeCommandGetNoResult(CommandBuilder.buildLoginCommand(username, password));
	}

	public CommandFuture<Void> selectVirtualServer(int serverID, int serverPort, String nickName) {
		return executeCommandGetNoResult(CommandBuilder.buildSelectVirtualServerCommand(serverID, serverPort, nickName));
	}

	public CommandFuture<Void> pokeClient(int clientID, String message) {
		return executeCommandGetNoResult(CommandBuilder.buildPokeClientCommand(clientID, message));
	}

	public CommandFuture<Void> moveClient(int clientID, int channelID) {
		return moveClientIDs(Collections.singletonList(clientID), channelID);
	}

	public CommandFuture<Void> moveClientIDs(List<Integer> clientIDs, int channelID) {
		return executeCommandGetNoResult(CommandBuilder.buildMoveClientsCommand(clientIDs, channelID));
	}

	public CommandFuture<Void> sendGlobalMessage(String message) {
		return executeCommandGetNoResult(CommandBuilder.buildSendGlobalMessageCommand(message));
	}

	public CommandFuture<Void> logout() {
		return executeCommandGetNoResult(CommandBuilder.buildLogoutCommand());
	}

	public CommandFuture<Void> quit() {
		return executeCommandGetNoResult(CommandBuilder.buildQuitCommand());
	}

	public CommandFuture<QueryClientInfo> getQueryInfo() {
		return writer.executeAsyncCommand(CommandBuilder.buildgetQueryInfoCommand(), QueryClientInfo::new);
	}

	public CommandFuture<Integer> getVirtualServerIDByPort(int port) {
		return executeCommandGetIntResult(CommandBuilder.buildGetVirtualServerIDByPort(port), "server_id=");
	}

	public CommandFuture<Integer> getPermissionID(String permissionName) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetPermissionIDsByNamesCommand(Collections.singletonList(permissionName)), null);
	}

	public CommandFuture<List<Integer>> getPermissionIDsByNames(List<String> permissionNames) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetPermissionIDsByNamesCommand(permissionNames), "permid=");
	}

	public CommandFuture<String> getPermissionName(int permissionID) {
		return executeCommandGetStringPropResult(CommandBuilder.buildGetPermissionNameByIDCommand(permissionID), "permsid=");
	}

	public CommandFuture<List<Permission>> getPermissionList() {
		return getPermissionListByCommand(CommandBuilder.buildGetPermissionListCommand());
	}

	public CommandFuture<List<Permission>> getPermissionListByCommand(String command) {
		return executeCommandGetListResult(command, Permission::new);
	}

	public CommandFuture<List<Permission>> getChannelPermissions(int channelID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelPermissionsCommand(channelID));
	}

	public CommandFuture<List<Permission>> getClientPermissions(int clientDataBaseID) {
		return getPermissionListByCommand(CommandBuilder.buildGetClientPermissionsCommand(clientDataBaseID));
	}

	public CommandFuture<List<Permission>> getServerGroupPermissions(int serverGroupID) {
		return getPermissionListByCommand(CommandBuilder.buildGetServerGroupPermissionsCommand(serverGroupID));
	}

	public CommandFuture<List<Permission>> getChannelGroupPermissions(int channelGroupID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelGroupPermissionsCommand(channelGroupID));
	}

	public CommandFuture<List<Permission>> getChannelClientPermissions(int channelID, int clientDataBaseID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelClientPermissionsCommand(channelID, clientDataBaseID));
	}

	public CommandFuture<List<ServerGroupInfo>> getServerGroups() {
		return executeCommandGetListResult(CommandBuilder.buildGetServerGroupsCommand(), ServerGroupInfo::new);
	}

	public CommandFuture<List<Integer>> getServerGroupIDsByClient(int clientDBID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "sgid=");
	}

	public CommandFuture<List<String>> getServerGroupNamesByClient(int clientDBID) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "name=");
	}

	public CommandFuture<List<ChannelGroupInfo>> getChannelGroups() {
		return executeCommandGetListResult(CommandBuilder.buildGetChannelGroupsCommand(), ChannelGroupInfo::new);
	}

	public CommandFuture<VirtualServerInfo> getServerInfo() {
		return writer.executeAsyncCommand(CommandBuilder.buildGetServerInfoCommand(), VirtualServerInfo::new);
	}

	public CommandFuture<ConnectionInfo> getConnectionInfo() {
		return writer.executeAsyncCommand(CommandBuilder.buildGetConnectionInfoCommand(), ConnectionInfo::new);
	}

	public CommandFuture<OfflineMessageInfo> getOfflineMessage(int messageID) {
		return writer.executeAsyncCommand(CommandBuilder.buildGetOfflineMessageCommand(messageID), OfflineMessageInfo::new);
	}

	public CommandFuture<List<DataBaseClientInfo>> getDataBaseClients(int startOffset, int limit) {
		return writer.executeAsyncCommand(CommandBuilder.buildGetDataBaseClientsCommand(startOffset, limit), new Transformator<List<DataBaseClientInfo>>() {

			@Override
			public List<DataBaseClientInfo> transformResult(String result) {
				List<DataBaseClientInfo> resultList = new ArrayList<>();
				for (String info : splitResult(result)) {
					resultList.add(new DataBaseClientInfo(info.replace("cldbid", "client_database_id")));
				}
				return resultList;
			}
		});
	}

	public CommandFuture<Integer> getDataBaseClientsCount() {
		return executeCommandGetListIntFirstPropResult(CommandBuilder.buildGetDataBaseClientsCountCommand(), "count=");
	}

	public CommandFuture<List<OfflineMessageInfo>> getOfflineMessages() {
		return executeCommandGetListResult(CommandBuilder.buildGetOfflineMessagesCommand(), OfflineMessageInfo::new);
	}

	public CommandFuture<HostInfo> getHostInfo() {
		return writer.executeAsyncCommand(CommandBuilder.buildGetHostInfoCommand(), HostInfo::new);
	}

	public CommandFuture<Integer> getClientIDByUUID(String clientUUID) {
		return executeCommandGetListIntFirstPropResult(CommandBuilder.buildGetClientIDsByUUIDsCommand(Collections.singletonList(clientUUID)), "clid=");
	}

	public CommandFuture<List<Integer>> getClientIDsByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientIDsByUUIDsCommand(clientUUIDs), "clid=");
	}

	public CommandFuture<String> getClientUUIDByID(int clientID) {
		return executeCommandGetListStringFirstPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(Collections.singletonList(clientID)), "cluid=");
	}

	public CommandFuture<List<String>> getClientUUIDsByIDs(List<Integer> clientIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "cluid=");
	}

	public CommandFuture<String> getClientNameByID(int clientID) {
		return executeCommandGetListStringFirstPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(Collections.singletonList(clientID)), "nickname=");
	}

	public CommandFuture<List<String>> getClientNamesByIDs(List<Integer> clientIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "nickname=");
	}

	public CommandFuture<String> getClientNameByUUID(String clientUUID) {
		return executeCommandGetListStringFirstPropResult(CommandBuilder.buildGetClientNamesByUUIDsCommand(Collections.singletonList(clientUUID)), "name=");
	}

	public CommandFuture<List<String>> getClientNamesByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesByUUIDsCommand(clientUUIDs), "name=");
	}

	public CommandFuture<String> getClientNameByDBID(int clientDataBaseID) {
		return executeCommandGetListStringFirstPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(Collections.singletonList(clientDataBaseID)), "name=");
	}

	public CommandFuture<List<String>> getClientNamesByDBIDs(List<Integer> clientDataBaseIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "name=");
	}

	public CommandFuture<String> getClientUUIDByDBID(int clientDataBaseID) {
		return executeCommandGetListStringFirstPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(Collections.singletonList(clientDataBaseID)), "cluid=");
	}

	public CommandFuture<List<String>> getClientUUIDsByDBIDs(List<Integer> clientDataBaseIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "cluid=");
	}

	public CommandFuture<Integer> getClientDataBaseIDByUUID(String clientUUID) {
		return executeCommandGetListIntFirstPropResult(CommandBuilder.buildGetClientDataBaseIDsByUUIDsCommand(Collections.singletonList(clientUUID)), "cldbid=");
	}

	public CommandFuture<List<Integer>> getClientDataBaseIDsByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientDataBaseIDsByUUIDsCommand(clientUUIDs), "cldbid=");
	}

	public CommandFuture<ClientInfo> getClient(int clientID) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetClientsByIDsCommand(Collections.singletonList(clientID)), new Transformator<ClientInfo>() {

			@Override
			public ClientInfo transformResult(String result) {
				return new ClientInfo(result.concat(" clid=" + clientID));
			}
		});
	}

	public CommandFuture<List<ClientInfo>> getClientsByIDs(List<Integer> clientIDs) {
		return writer.executeAsyncCommand(CommandBuilder.buildGetClientsByIDsCommand(clientIDs), new Transformator<List<ClientInfo>>() {

			@Override
			public List<ClientInfo> transformResult(String result) {
				List<ClientInfo> resultList = new ArrayList<>();
				int counter = 0;
				for (String clientInfo : splitResult(result)) {
					resultList.add(new ClientInfo(clientInfo.concat(" clid=" + clientIDs.get(counter))));
					counter++;
				}
				return resultList;
			}
		});
	}

	public CommandFuture<List<ClientInfo>> getClients() {
		return executeCommandGetListResult(CommandBuilder.buildGetClientsCommand(), ClientInfo::new);
	}

	public CommandFuture<DataBaseClientInfo> getDataBaseClient(int clientDataBaseID) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetDataBaseClientsByDBIDsCommand(Collections.singletonList(clientDataBaseID)), DataBaseClientInfo::new);
	}

	public CommandFuture<List<DataBaseClientInfo>> getDataBaseClientsByDBIDs(List<Integer> clientDBIDs) {
		return executeCommandGetListResult(CommandBuilder.buildGetDataBaseClientsByDBIDsCommand(clientDBIDs), DataBaseClientInfo::new);
	}

	public CommandFuture<ChannelInfo> getChannel(int channelID) {
		return writer.executeAsyncCommand(CommandBuilder.buildGetChannelCommand(channelID), new Transformator<ChannelInfo>() {

			@Override
			public ChannelInfo transformResult(String result) {
				return new ChannelInfo(result.concat(" cid=" + channelID));
			}
		});
	}

	public CommandFuture<List<ChannelInfo>> getChannelsByName(String channelName) {
		return getChannelsByCommand(CommandBuilder.buildGetChannelsByNameCommand(channelName));
	}

	private CommandFuture<List<ChannelInfo>> getChannelsByCommand(String command) {
		return executeCommandGetListResult(command, ChannelInfo::new);
	}

	public CommandFuture<List<ChannelInfo>> getChannels() {
		return getChannelsByCommand(CommandBuilder.buildGetChannelsCommand());
	}

	public CommandFuture<List<Integer>> getDatabaseIDsByServerGroup(int servergroupID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDatabaseIDsByServerGroupCommand(servergroupID), "cldbid=");
	}

	public CommandFuture<Map<Integer, List<Integer>>> getDatabaseIDsByChannelGroup(int channelgroupID) {
		return executeCommandGetHashMapIntListResult(CommandBuilder.buildGetDatabaseIDsByChannelGroupCommand(channelgroupID), "cldbid=", "cid=");
	}

	public CommandFuture<List<Integer>> getDatabaseIDsByChannelAndGroup(int channelID, int channelgroupID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDatabaseIDsByChannelAndGroupCommand(channelID, channelgroupID), "cldbid=");
	}

	public CommandFuture<Map<Integer, List<Integer>>> getChannelGroupsByDatabaseID(int clientDataBaseID) {
		return executeCommandGetHashMapIntListResult(CommandBuilder.buildGetChannelGroupsByDatabaseIDCommand(clientDataBaseID), "cgid=", "cid=");
	}

	public CommandFuture<Map<Integer, List<Integer>>> getChannelGroupsByChannelID(int channelID) {
		return executeCommandGetHashMapIntListResult(CommandBuilder.buildGetChannelGroupsByChannelIDCommand(channelID), "cldbid=", "cgid=");
	}

	public CommandFuture<List<ComplainInfo>> getComplainsByClient(int clientDataBaseID) {
		return executeCommandGetListResult(CommandBuilder.buildGetComplainsByClientCommand(clientDataBaseID), ComplainInfo::new);
	}

	public CommandFuture<List<PrivilegeKeyInfo>> getPrivilegeKeys() {
		return executeCommandGetListResult(CommandBuilder.buildGetPrivilegeKeysCommand(), PrivilegeKeyInfo::new);
	}

	public CommandFuture<List<ComplainInfo>> getComplains() {
		return executeCommandGetListResult(CommandBuilder.buildGetComplainsCommand(), ComplainInfo::new);
	}

	public CommandFuture<List<BanInfo>> getBans() {
		return executeCommandGetListResult(CommandBuilder.buildGetBansCommand(), BanInfo::new);
	}

	protected CommandFuture<List<VirtualServerInfo>> getVirtualServersByCommand(String command) {
		return executeCommandGetListResult(command, VirtualServerInfo::new);
	}

	public CommandFuture<List<VirtualServerInfo>> getVirtualServers() {
		return getVirtualServersByCommand(CommandBuilder.buildGetVirtualServersCommand());
	}

	public CommandFuture<String> getVersion() {
		return executeCommandGetStringResult(CommandBuilder.buildGetVersionCommand());
	}

	public CommandFuture<Integer> addBan(String ip, String name, String clientUUID, String myTSID, long banTime, String banReason) {
		return executeCommandGetIntResult(CommandBuilder.buildAddBanCommand(ip, name, clientUUID, myTSID, banTime, banReason), "banid=");
	}

	public CommandFuture<int[]> banClient(int clientID, long banTime, String banReason) {
		return writer.executeAsyncCommand(CommandBuilder.buildBanClientIDsCommand(Collections.singletonList(clientID), banTime, banReason), new Transformator<int[]>() {

			@Override
			public int[] transformResult(String result) {
				List<Integer> resultList = new ArrayList<>();
				for (String id : result.replace("banid=", "").replace("\n", ",").split(",")) {
					resultList.add(Integer.parseInt(id));
				}
				return new int[] { resultList.get(0), resultList.get(1) };
			}
		});
	}

	public CommandFuture<List<Integer>> banClientIDs(List<Integer> clientIDs, long banTime, String banReason) {
		return writer.executeAsyncCommand(CommandBuilder.buildBanClientIDsCommand(clientIDs, banTime, banReason), new Transformator<List<Integer>>() {

			@Override
			public List<Integer> transformResult(String result) {
				List<Integer> resultList = new ArrayList<>();
				for (String id : result.replace("banid=", "").replace("\n", ",").split(",")) {
					resultList.add(Integer.parseInt(id));
				}
				return resultList;
			}
		});
	}

	public CommandFuture<Void> removeBan(int banID) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveBanCommand(banID));
	}

	public CommandFuture<Void> removeAllBans() {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveAllBansCommand());
	}

	public CommandFuture<Void> startVirtualServer(int virtualServerID) {
		return executeCommandGetNoResult(CommandBuilder.buildStartVirtualServerCommand(virtualServerID));
	}

	public CommandFuture<Void> stopVirtualServer(int virtualServerID, String reasonmsg) {
		return executeCommandGetNoResult(CommandBuilder.buildStopVirtualServerCommand(virtualServerID, reasonmsg));
	}

	public CommandFuture<Void> stopServerProcess(String reasonmsg) {
		return executeCommandGetNoResult(CommandBuilder.buildStopServerProcessCommand(reasonmsg));
	}

	public CommandFuture<String> resetPermissions() {
		return executeCommandGetStringPropResult(CommandBuilder.buildResetPermissionsCommand(), "token=");
	}

	public CommandFuture<Void> addChannelPermission(int channelID, int permissionID, String permissionName, int permissionValue) {
		return addChannelPermissions(channelID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public CommandFuture<Void> addChannelPermissions(int channelID, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddChannelPermissionsCommand(channelID, permissions));
	}

	public CommandFuture<Void> addChannelClientPermission(int channelID, int clientdataBaseID, int permissionID, String permissionName, int permissionValue) {
		return addChannelClientPermissions(channelID, clientdataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public CommandFuture<Void> addChannelClientPermissions(int channelID, int clientdataBaseID, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddChannelClientPermissionsCommand(channelID, clientdataBaseID, permissions));
	}

	public CommandFuture<Void> removeChannelClientPermission(int channelID, int clientdataBaseID, int permissionID, String permissionName) {
		return removeChannelClientPermissions(channelID, clientdataBaseID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeChannelClientPermissions(int channelID, int clientdataBaseID, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveChannelClientPermissionsCommand(channelID, clientdataBaseID, permissions, permissionNames));
	}

	public CommandFuture<Integer> createChannel(String channelName, Map<ChannelProperty, String> channelProperties) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateChannelCommand(channelName, channelProperties), "cid=");
	}

	public CommandFuture<Void> deleteChannel(int channelID, boolean force) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteChannelCommand(channelID, force));
	}

	public CommandFuture<Void> removeChannelPermission(int channelID, int permissionID, String permissionName) {
		return removeChannelPermissions(channelID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeChannelPermissions(int channelID, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveChannelPermissionsCommand(channelID, permissions, permissionNames));
	}

	public CommandFuture<Void> editChannel(int channelID, Map<ChannelProperty, String> channelProperties) {
		return executeCommandGetNoResult(CommandBuilder.buildEditChannelCommand(channelID, channelProperties));
	}

	public CommandFuture<Integer> createChannelGroup(String channelGroupName, ChannelGroupType channelGroupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateChannelGroupCommand(channelGroupName, channelGroupType), "cgid=");
	}

	public CommandFuture<Void> addChannelGroupPermission(int channelGroupID, int permissionID, String permissionName, int permissionValue) {
		return addChannelGroupPermissions(channelGroupID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public CommandFuture<Void> addChannelGroupPermissions(int channelGroupID, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddChannelGroupPermissionsCommand(channelGroupID, permissions));
	}

	public CommandFuture<Integer> copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCopyChannelGroupCommand(sourceChannelGroupID, targetChannelGroupID, channelGroupName, channelGroupType), "cgid=");
	}

	public CommandFuture<Void> deleteChannelGroup(int channelGroupID, boolean force) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteChannelGroupCommand(channelGroupID, force));
	}

	public CommandFuture<Void> removeChannelGroupPermission(int channelGroupID, int permissionID, String permissionName) {
		return removeChannelGroupPermissions(channelGroupID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeChannelGroupPermissions(int channelGroupID, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveChannelGroupPermissionsCommand(channelGroupID, permissions, permissionNames));
	}

	public CommandFuture<Void> renameChannelGroup(int channelGroupID, String channelName) {
		return executeCommandGetNoResult(CommandBuilder.buildRenameChannelGroupCommand(channelGroupID, channelName));
	}

	public CommandFuture<Void> moveChannel(int channelID, int channelParentID, int order) {
		return executeCommandGetNoResult(CommandBuilder.buildMoveChannelCommand(channelID, channelParentID, order));
	}

	public CommandFuture<Void> addClientPermission(int clientDataBaseID, int permissionID, String permissionName, int permissionValue, boolean permSkip) {
		return addClientPermissions(clientDataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permSkip, false)));
	}

	public CommandFuture<Void> addClientPermissions(int clientDataBaseID, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddClientPermissionsCommand(clientDataBaseID, permissions));
	}

	public CommandFuture<Void> deleteDataBaseClient(int clientDataBaseID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteDataBaseClientCommand(clientDataBaseID));
	}

	public CommandFuture<Void> editDataBaseClient(int clientDataBaseID, String description) {
		return executeCommandGetNoResult(CommandBuilder.buildEditDataBaseClientCommand(clientDataBaseID, description));
	}

	public CommandFuture<Integer> getDataBaseClientIDByUUID(String clientUUID) {
		return executeCommandGetIntResult(CommandBuilder.buildGetDataBaseClientIDByUUIDCommand(clientUUID), "cldbid=");
	}

	public CommandFuture<List<Integer>> getDataBaseClientIDsByName(String clientLastName) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDataBaseClientIDsByNameCommand(clientLastName), "cldbid=");
	}

	public CommandFuture<Void> removeClientPermission(int clientDataBaseID, int permissionID, String permissionName) {
		return removeClientPermissions(clientDataBaseID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeClientPermissions(int clientDataBaseID, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveClientPermissionsCommand(clientDataBaseID, permissions, permissionNames));
	}

	public CommandFuture<Void> editClient(int clientID, Map<ClientProperty, String> clientProperties) {
		return executeCommandGetNoResult(CommandBuilder.buildEditClientCommand(clientID, clientProperties));
	}

	public CommandFuture<List<Integer>> getClientIDsByName(String clientName) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientIDsByNameCommand(clientName), "clid=");
	}

	public CommandFuture<Void> kickClientFromServer(int clientID, String reason) {
		return kickClientIDs(Collections.singletonList(clientID), 5, reason);
	}

	public CommandFuture<Void> kickClientIDs(List<Integer> clientIDs, int reasonID, String reason) {
		return executeCommandGetNoResult(CommandBuilder.buildKickClientIDsCommand(clientIDs, reasonID, reason));
	}

	public CommandFuture<Void> kickClientFromChannel(int clientID, String reason) {
		return kickClientIDs(Collections.singletonList(clientID), 4, reason);
	}

	public CommandFuture<String> updateServerQueryLogin(String username) {
		return executeCommandGetStringPropResult(CommandBuilder.buildUpdateServerQueryLoginCommand(username), "client_login_password=");
	}

	public CommandFuture<Void> updateQueryName(String queryName) {
		return executeCommandGetNoResult(CommandBuilder.buildUpdateQueryNameCommand(queryName));
	}

	public CommandFuture<Void> addComplain(int clientDBID, String message) {
		return executeCommandGetNoResult(CommandBuilder.buildAddComplainCommand(clientDBID, message));
	}

	public CommandFuture<Void> deleteComplain(int clientDBID, int fromClientDBID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteComplainCommand(clientDBID, fromClientDBID));
	}

	public CommandFuture<Void> deleteAllComplains(int clientDBID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteAllComplainsCommand(clientDBID));
	}

	public CommandFuture<Map<String, String>> getCustomInfo(int clientDBID) {
		return executeCommandGetHashMapStringResult(CommandBuilder.buildGetCustomInfoCommand(clientDBID), "ident=", "value=");
	}

	public CommandFuture<Map<Integer, List<String>>> searchDBIDsCustomInfo(String ident, String pattern) {
		return executeCommandGetHashMapListStringResult(CommandBuilder.buildSearchDBIDsCustomInfoCommand(ident, pattern), "cldbid=", "value=");
	}

	public CommandFuture<Void> setCustomInfo(int clientDBID, String ident, String value) {
		return executeCommandGetNoResult(CommandBuilder.buildSetCustomInfoCommand(clientDBID, ident, value));
	}

	public CommandFuture<Void> deleteCustomInfo(int clientDBID, String ident) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteCustomInfoCommand(clientDBID, ident));
	}

	public CommandFuture<Void> createFileDirectory(int channelID, String channelPassword, String dirName) {
		return executeCommandGetNoResult(CommandBuilder.buildCreateFileDirectoryCommand(channelID, channelPassword, dirName));
	}

	public CommandFuture<Void> deleteFile(int channelID, String channelPassword, String fileName) {
		return deleteFiles(channelID, channelPassword, Collections.singletonList(fileName));
	}

	public CommandFuture<Void> deleteFiles(int channelID, String channelPassword, List<String> fileNames) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteFilesCommand(channelID, channelPassword, fileNames));
	}

	public CommandFuture<FileInfo> getFileInfo(int channelID, String channelPassword, String fileName) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetFileInfosCommand(channelID, channelPassword, Collections.singletonList(fileName)), new Transformator<FileInfo>() {

			@Override
			public FileInfo transformResult(String result) {
				return new FileInfo(result.concat(" cid=" + channelID));
			}
		});
	}

	public CommandFuture<List<FileInfo>> getFileInfos(int channelID, String channelPassword, List<String> fileNames) {
		return executeCommandGetListResult(CommandBuilder.buildGetFileInfosCommand(channelID, channelPassword, fileNames), new Transformator<FileInfo>() {

			@Override
			public FileInfo transformResult(String result) {
				return new FileInfo(result.concat(" cid=" + channelID));
			}
		});
	}

	public CommandFuture<List<FileInfo>> getChannelFilesByPath(int channelID, String channelPassword, String filePath) {
		return writer.executeAsyncCommand(CommandBuilder.buildGetChannelFilesByPathCommand(channelID, channelPassword, filePath), new Transformator<List<FileInfo>>() {

			@Override
			public List<FileInfo> transformResult(String result) {
				List<FileInfo> resultList = new ArrayList<>();
				String[] information = splitResult(result);
				for (String info : information) {
					resultList.add(new FileInfo(info.concat(" cid=" + channelID).concat(" path=" + Formatter.get(information[0], "path="))));
				}
				return resultList;
			}
		});

	}

	public CommandFuture<List<FileTransferInfo>> getFileTransfers() {
		return executeCommandGetListResult(CommandBuilder.buildGetFileTransfersCommand(), FileTransferInfo::new);
	}

	public CommandFuture<Void> renameFile(int channelID, String channelPassword, String oldFilePath, String newFilePath) {
		return executeCommandGetNoResult(CommandBuilder.buildRenameFileCommand(channelID, channelPassword, oldFilePath, newFilePath));
	}

	public CommandFuture<Void> moveFile(int channelID, String channelPassword, String oldFilePath, int newChannelID, String newChannelPassword, String newFilePath) {
		return executeCommandGetNoResult(CommandBuilder.buildMoveFileCommand(channelID, channelPassword, oldFilePath, newChannelID, newChannelPassword, newFilePath));
	}

	public CommandFuture<Void> stopFileTransfer(int serverFileTransferID, boolean delete) {
		return executeCommandGetNoResult(CommandBuilder.buildStopFileTransferCommand(serverFileTransferID, delete));
	}

	public CommandFuture<Void> editInstance(Map<InstanceProperty, String> instanceProperties) {
		return executeCommandGetNoResult(CommandBuilder.buildEditInstanceCommand(instanceProperties));
	}

	public CommandFuture<InstanceInfo> getInstanceInfo() {
		return writer.executeAsyncCommand(CommandBuilder.buildGetInstanceInfoCommand(), InstanceInfo::new);
	}

	public CommandFuture<Void> addToLog(LogLevel logLevel, String logMessage) {
		return executeCommandGetNoResult(CommandBuilder.buildAddToLogCommand(logLevel, logMessage));
	}

	public CommandFuture<List<String>> getLog(int lines, boolean reverse, boolean instance, int beginPos) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetLogCommand(lines, reverse, instance, beginPos), "l=");
	}

	public CommandFuture<Void> sendOfflineMessage(String clientUUID, String subject, String message) {
		return executeCommandGetNoResult(CommandBuilder.buildSendOfflineMessageCommand(clientUUID, subject, message));
	}

	public CommandFuture<Void> deleteOfflineMessage(int offlineMessageID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteOfflineMessageCommand(offlineMessageID));
	}

	public CommandFuture<Void> updateOfflineMessageFlag(int offlineMessageID, boolean flag) {
		return executeCommandGetNoResult(CommandBuilder.buildUpdateOfflineMessageFlagCommand(offlineMessageID, flag));
	}

	public CommandFuture<List<PermissionAssignmentInfo>> getAssignmentsOfPermission(int permissionID) {
		return getAssignmentsOfPermissions(Collections.singletonList(permissionID), new ArrayList<>());
	}

	public CommandFuture<List<PermissionAssignmentInfo>> getAssignmentsOfPermission(String permissionName) {
		return getAssignmentsOfPermissions(new ArrayList<>(), Collections.singletonList(permissionName));
	}

	public CommandFuture<List<PermissionAssignmentInfo>> getAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {

		return executeCommandGetListResult(CommandBuilder.buildGetAssignmentsOfPermissionsCommand(permissions, permissionNames), PermissionAssignmentInfo::new);
	}

	public CommandFuture<Permission> getQueryAssignmentOfPermission(int permissionID) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetQueryAssignmentsOfPermissionsCommand(Collections.singletonList(permissionID), new ArrayList<>()), Permission::new);
	}

	public CommandFuture<Permission> getQueryAssignmentOfPermission(String permissionName) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetQueryAssignmentsOfPermissionsCommand(new ArrayList<>(), Collections.singletonList(permissionName)), Permission::new);
	}

	public CommandFuture<List<Permission>> getQueryAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {
		return getPermissionListByCommand(CommandBuilder.buildGetQueryAssignmentsOfPermissionsCommand(permissions, permissionNames));
	}

	public CommandFuture<List<PermissionAssignmentInfo>> getPermOverview(int clientDBID, int channelID, int permID) {
		return executeCommandGetListResult(CommandBuilder.buildGetPermOverviewCommand(clientDBID, channelID, permID), PermissionAssignmentInfo::new);
	}

	public CommandFuture<PermissionAssignmentInfo> getPermOverviewByPermID(int clientDBID, int channelID, int permID) {
		return executeCommandGetListFirstResult(CommandBuilder.buildGetPermOverviewCommand(clientDBID, channelID, permID), PermissionAssignmentInfo::new);
	}

	public CommandFuture<String> createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		return executeCommandGetStringPropResult(CommandBuilder.buildCreatePrivilegeKeyCommand(keyType, groupID, channelID, description), "token=");
	}

	public CommandFuture<Void> deletePrivilegeKey(String privilegeKey) {
		return executeCommandGetNoResult(CommandBuilder.buildDeletePrivilegeKeyCommand(privilegeKey));
	}

	public CommandFuture<Void> usePrivilegeKey(String privilegeKey) {
		return executeCommandGetNoResult(CommandBuilder.buildUsePrivilegeKeyCommand(privilegeKey));
	}

	public CommandFuture<CreatedQueryLogin> createQueryLogin(String loginName, int clientDBID) {
		return writer.executeAsyncCommand(CommandBuilder.buildCreateQueryLoginCommand(loginName, clientDBID), CreatedQueryLogin::new);
	}

	public CommandFuture<Void> deleteQueryLogin(int clientDBID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteQueryLoginCommand(clientDBID));
	}

	public CommandFuture<List<CreatedQueryLogin>> getQueryLogins(String pattern, int startOffset, int duration) {
		return executeCommandGetListResult(CommandBuilder.buildGetQueryLoginsCommand(pattern, startOffset, duration), CreatedQueryLogin::new);
	}

	public CommandFuture<Void> sendTextMessage(TextMessageType messageType, int clientID, String message) {
		return executeCommandGetNoResult(CommandBuilder.buildSendTextMessageCommand(messageType, clientID, message));
	}

	public CommandFuture<CreatedVirtualServer> createVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		return writer.executeAsyncCommand(CommandBuilder.buildCreateVirtualServerCommand(virtualServerProperties), CreatedVirtualServer::new);
	}

	public CommandFuture<Void> deleteVirtualServer(int virtualServerID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteVirtualServerCommand(virtualServerID));
	}

	public CommandFuture<Void> editVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		return executeCommandGetNoResult(CommandBuilder.buildEditVirtualServerCommand(virtualServerProperties));
	}

	public CommandFuture<Integer> createServerGroup(String serverGroupName, ServerGroupType groupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateServerGroupCommand(serverGroupName, groupType), "sgid=");
	}

	public CommandFuture<Void> addClientToServerGroup(int groupID, int clientDBID) {
		return addClientDBIDsToServerGroup(groupID, Collections.singletonList(clientDBID));
	}

	public CommandFuture<Void> addClientDBIDsToServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		return executeCommandGetNoResult(CommandBuilder.buildAddClientDBIDsToServerGroupCommand(serverGroupID, dataBaseClientIDs));
	}

	public CommandFuture<Void> addServerGroupPermission(int serverGroupID, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		return addServerGroupPermissions(serverGroupID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null)));
	}

	public CommandFuture<Void> addServerGroupPermissions(int serverGroupID, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddServerGroupPermissionsCommand(serverGroupID, permissions));
	}

	public CommandFuture<Void> addServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		return addServerGroupAutoPermissions(groupLevel, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null)));
	}

	public CommandFuture<Void> addServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Permission> permissions) {
		return executeCommandGetNoResult(CommandBuilder.buildAddServerGroupAutoPermissionsCommand(groupLevel, permissions));
	}

	public CommandFuture<Void> removeServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName) {
		return removeServerGroupAutoPermissions(groupLevel, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveServerGroupAutoPermissionsCommand(groupLevel, permissions, permissionNames));
	}

	public CommandFuture<Integer> copyServerGroup(int sourceServerGroupID, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCopyServerGroupCommand(sourceServerGroupID, targetServerGroupID, serverGroupName, serverGroupType), "sgid=");
	}

	public CommandFuture<Void> deleteServerGroup(int serverGroupID, boolean force) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteServerGroupCommand(serverGroupID, force));
	}

	public CommandFuture<Void> removeClientFromServerGroup(int serverGroupID, int clientDBID) {
		return removeClientDBIDsFromServerGroup(serverGroupID, Collections.singletonList(clientDBID));
	}

	public CommandFuture<Void> removeClientDBIDsFromServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveClientDBIDsFromServerGroupCommand(serverGroupID, dataBaseClientIDs));
	}

	public CommandFuture<Void> renameServerGroup(int serverGroupID, String name) {
		return executeCommandGetNoResult(CommandBuilder.buildRenameServerGroupCommand(serverGroupID, name));
	}

	public CommandFuture<Void> removeServerGroupPermission(int serverGroupID, int permissionID, String permissionName) {
		return removeServerGroupPermissions(serverGroupID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public CommandFuture<Void> removeServerGroupPermissions(int serverGroupID, List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetNoResult(CommandBuilder.buildRemoveServerGroupPermissionsCommand(serverGroupID, permissions, permissionNames));
	}

	public CommandFuture<Void> addVirtualServerTempPassword(String password, String description, long duration, int targetChannelID, String targetChannelPassword) {

		return executeCommandGetNoResult(CommandBuilder.buildAddVirtualServerTempPasswordCommand(password, description, duration, targetChannelID, targetChannelPassword));
	}

	public CommandFuture<Void> deleteVirtualServerTempPassword(String password) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteVirtualServerTempPasswordCommand(password));
	}

	public CommandFuture<List<TempPasswordInfo>> getVirtualServerTempPasswords() {
		return executeCommandGetListResult(CommandBuilder.buildGetVirtualServerTempPasswordsCommand(), TempPasswordInfo::new);
	}

	public CommandFuture<CreatedSnapshot> createSnapshot(String password) {
		return writer.executeAsyncCommand(CommandBuilder.buildCreateSnapshotCommand(password), CreatedSnapshot::new);
	}

	public CommandFuture<CreatedAPIKey> addAPIKey(APIScope scope, int lifetime, int clientDBID) {
		return writer.executeAsyncCommand(CommandBuilder.buildAddAPIKeyCommand(scope, lifetime, clientDBID), CreatedAPIKey::new);
	}

	public CommandFuture<Void> deleteAPIKey(int keyID) {
		return executeCommandGetNoResult(CommandBuilder.buildDeleteAPIKeyCommand(keyID));
	}

	public CommandFuture<List<CreatedAPIKey>> getAPIKeys(int clientDBID, int startOffset, int limit) {
		return executeCommandGetListResult(CommandBuilder.buildGetAPIKeysCommand(clientDBID, startOffset, limit), CreatedAPIKey::new);
	}

	public CommandFuture<Void> setClientChannelGroup(int channelGroupID, int channelID, int clientDBID) {
		return executeCommandGetNoResult(CommandBuilder.buildSetClientChannelGroupCommand(channelGroupID, channelID, clientDBID));
	}

	public CommandFuture<Void> registerEvent(EventType eventType, int channelID) {
		return executeCommandGetNoResult(CommandBuilder.buildRegisterEventCommand(eventType, channelID));
	}

	public CommandFuture<Void> unRegisterAllEvents() {
		return executeCommandGetNoResult(CommandBuilder.buildUnRegisterAllEventsCommand());
	}

	private CommandFuture<Map<Integer, List<Integer>>> executeCommandGetHashMapIntListResult(String command, String keyProp, String valueProp) {
		return writer.executeAsyncCommand(command, new Transformator<Map<Integer, List<Integer>>>() {

			@Override
			public Map<Integer, List<Integer>> transformResult(String result) {
				Map<Integer, List<Integer>> resultMap = new HashMap<>();
				for (String user : splitResult(result)) {
					if (user.isEmpty())
						continue;
					int keyID = Integer.parseInt(Formatter.get(user, keyProp));
					int valID = Integer.parseInt(Formatter.get(user, valueProp));

					resultMap.computeIfAbsent(keyID, k -> new ArrayList<>()).add(valID);
				}
				return resultMap;
			}

		});
	}

	private CommandFuture<Map<String, String>> executeCommandGetHashMapStringResult(String command, String keyProp, String valueProp) {
		return writer.executeAsyncCommand(command, new Transformator<Map<String, String>>() {

			@Override
			public Map<String, String> transformResult(String result) {
				Map<String, String> resultMap = new HashMap<>();
				for (String info : splitResult(result)) {
					String key = Formatter.toNormalFormat(Formatter.get(info, keyProp));
					String value = Formatter.toNormalFormat(Formatter.get(info, valueProp));
					resultMap.put(key, value);
				}
				return resultMap;
			}

		});
	}

	private CommandFuture<Map<Integer, List<String>>> executeCommandGetHashMapListStringResult(String command, String keyProp, String valueProp) {
		return writer.executeAsyncCommand(command, new Transformator<Map<Integer, List<String>>>() {

			@Override
			public Map<Integer, List<String>> transformResult(String result) {
				Map<Integer, List<String>> resultMap = new HashMap<>();
				for (String info : splitResult(result)) {
					int keyID = Integer.parseInt(Formatter.get(info, keyProp));
					String valueID = Formatter.toNormalFormat(Formatter.get(info, valueProp));
					resultMap.computeIfAbsent(keyID, k -> new ArrayList<>()).add(valueID);
				}

				return resultMap;
			}

		});
	}

	private CommandFuture<Integer> executeCommandGetIntResult(String command, String property) {
		return writer.executeAsyncCommand(command, new Transformator<Integer>() {

			@Override
			public Integer transformResult(String result) {
				try {
					return Integer.parseInt(Formatter.get(result, property));
				} catch (NumberFormatException e) {
					return -2;
				}
			}

		});
	}

	private CommandFuture<String> executeCommandGetStringResult(String command) {
		return writer.executeAsyncCommand(command, new Transformator<String>() {

			@Override
			public String transformResult(String result) {
				return result;
			}

		});
	}

	private CommandFuture<String> executeCommandGetStringPropResult(String command, String property) {
		return writer.executeAsyncCommand(command, new Transformator<String>() {

			@Override
			public String transformResult(String result) {
				return Formatter.get(result, property);
			}

		});
	}

	private CommandFuture<Void> executeCommandGetNoResult(String command) {
		return writer.executeAsyncCommand(command, new Transformator<Void>() {

			@Override
			public Void transformResult(String result) {
				return null;
			}

		});
	}

	private <T> CommandFuture<List<Integer>> executeCommandGetListIntPropResult(String command, String property) {
		return executeCommandGetListResult(command, new Transformator<Integer>() {

			@Override
			public Integer transformResult(String result) {
				return Integer.parseInt(Formatter.get(result, property));
			}
		});
	}

	private CommandFuture<List<String>> executeCommandGetListStringPropResult(String command, String property) {
		return executeCommandGetListResult(command, new Transformator<String>() {

			@Override
			public String transformResult(String result) {
				return Formatter.toNormalFormat(Formatter.get(result, property));
			}
		});
	}

	private CommandFuture<Integer> executeCommandGetListIntFirstPropResult(String command, String property) {
		return executeCommandGetListFirstResult(command, new Transformator<Integer>() {

			@Override
			public Integer transformResult(String result) {
				return Integer.parseInt(Formatter.get(result, property));
			}
		});
	}

	private CommandFuture<String> executeCommandGetListStringFirstPropResult(String command, String property) {
		return executeCommandGetListFirstResult(command, new Transformator<String>() {

			@Override
			public String transformResult(String result) {
				return Formatter.toNormalFormat(Formatter.get(result, property));
			}
		});
	}

	private <T> CommandFuture<List<T>> executeCommandGetListResult(String command, Transformator<T> transformEl) {
		return writer.executeAsyncCommand(command, new Transformator<List<T>>() {

			@Override
			public List<T> transformResult(String result) {
				List<T> resultList = new ArrayList<>();

				for (String info : splitResult(result)) {
					if (!info.isBlank())
						resultList.add(transformEl.transformResult(info));
				}
				return resultList;
			}

		});
	}

	private <T> CommandFuture<T> executeCommandGetListFirstResult(String command, Transformator<T> transformEl) {
		return writer.executeAsyncCommand(command, new Transformator<T>() {

			@Override
			public T transformResult(String result) {
				List<T> resultList = new ArrayList<>();

				for (String info : splitResult(result)) {
					resultList.add(transformEl.transformResult(info));
				}
				return resultList.get(0);
			}

		});
	}

	private String[] splitResult(String result) {
		return result.split(TS_INFO_SEPARATOR);
	}
}
