/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 19:13:51
 */
package net.devcube.vinco.teamspeakapi.api.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownChannelInfoException;
import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownClientInfoException;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.ClientProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.InstanceProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.LogLevel;
import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupLevel;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerProperty;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.BanInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.CreatedQueryLogin;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.FileInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.PrivilegeKeyInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ServerGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.TempPasswordInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.VirtualServerInfo;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Main Class to Interact with the TeamSpeakServer in any way.
 * Extends its basic Methods from the Ts3BasicAPI an calls them.
 * @see Ts3BasicAPI
 * 
 */

public class Ts3SyncAPI extends Ts3BasicAPI {
	
	/**
	 * Initiation of the Sync API
	 * 
	 * @param Serverquery
	 *                        class
	 */
	public Ts3SyncAPI(Ts3ServerQuery query) {
		super(query);
	}

	
	public String getFullHelp() {
		StringBuilder resultBuilder = new StringBuilder();
		String help = getHelp();
		resultBuilder.append(help);

		String[] splitHelp = help.split("\n");
		for (int i = 11; i <= 149; i++) {
			String command = splitHelp[i].replace(" ", "").split(TS_INFO_SEPERATOR)[0];
			if (command.isEmpty() || command.equalsIgnoreCase("help"))
				continue;
			resultBuilder.append("help " + command + System.lineSeparator());
			resultBuilder.append(getHelp(command));
		}

		return resultBuilder.toString();
	}

	/**
	 * Selects the virtual server to connect to
	 * 
	 * @param serverid
	 *                     of the virtual server
	 */
	public void selectVirtualServer(int serverID) {
		selectVirtualServer(serverID, null);
	}

	public void selectVirtualServer(VirtualServerInfo virtualServer) {
		selectVirtualServer(virtualServer.getServerID());
	}

	public void selectVirtualServer(VirtualServerInfo virtualServer, String nickName) {
		selectVirtualServer(virtualServer.getServerID(), nickName);
	}

	
	public void pokeClient(ClientInfo client, String message) {
		pokeClient(client.getID(), message);
	}

	public void moveClient(ClientInfo client, int channelID) {
		moveClient(client.getID(), channelID);
	}

	public void moveClient(int clientID, ChannelInfo channelInfo) {
		moveClient(clientID, channelInfo.getChannelID());
	}

	public void moveClient(ClientInfo client, ChannelInfo channelInfo) {
		moveClient(client.getID(), channelInfo.getChannelID());
	}
	
	public void moveClients(List<ClientInfo> clients, int channelID) {
		StringBuilder commandBuilder = new StringBuilder("clientmove");
		for (ClientInfo moveClient : clients) {
			commandBuilder.append(" clid=" + moveClient.getClientID());
			commandBuilder.append("|");
		}
		int lenght = commandBuilder.toString().length();
		
		commandBuilder.delete(lenght - 1, lenght);
		commandBuilder.append(" cid=" + channelID);
		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}
	
	public void moveClients(List<ClientInfo> clients, ChannelInfo channelInfo) {
		moveClients(clients, channelInfo.getChannelID());
	}
	
	public void moveClientIDs(List<Integer> clientIDs, int channelID) {
		StringBuilder commandBuilder = new StringBuilder("clientmove");
		for (int moveClientID : clientIDs) {
			commandBuilder.append(" clid=" + moveClientID);
			commandBuilder.append("|");
		}
		int lenght = commandBuilder.toString().length();
		
		commandBuilder.delete(lenght - 1, lenght);
		commandBuilder.append(" cid=" + channelID);
		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}
	
	public void moveClientIDs(List<Integer> clientIDs, ChannelInfo channelInfo) {
		moveClientIDs(clientIDs, channelInfo.getChannelID());
	}
	
	
	/**
	 * Switches the channel of the query bot
	 * 
	 * @param channelID
	 */
	public void goToChannel(int channelID) {
		moveClient(getQueryInfo().getID(), channelID);
	}

	public void goToChannel(ChannelInfo channel) {
		moveClient(getQueryInfo().getID(), channel.getChannelID());
	}

	public QueryClientInfo whoAmI() {
		return getQueryInfo();
	}

	/**
	 * Method to get an overview for the Permissions of
	 * the Query Client.
	 * Does not include Channel specific Permissions, because
	 * the Client could be (not) in that Channel or could leave it.
	 * @return List of PermissionIDs containing every ID only once.
	 */
	
	public Set<Integer> getQueryPermissions() {
		Set<Integer> perms = new HashSet<>();
		int dbID = getQueryInfo().getDataBaseID();

		for (Integer servergroups : getServerGroupIDsByClient(dbID)) {
			for (Permission permissions : getServerGroupPermissions(servergroups)) {
				perms.add(permissions.getPermID());
			}
		}

		for (Permission clientPerms : getClientPermissions(dbID)) {
			perms.add(clientPerms.getPermID());
		}
		return perms;
	}

	public List<Permission> getChannelPermissions(ChannelInfo channel) {
		return getChannelPermissions(channel.getChannelID());
	}

	public List<Permission> getClientPermissions(DataBaseClientInfo dataBaseClient) {
		return getClientPermissions(dataBaseClient.getClientDataBaseID());
	}

	public List<Permission> getClientPermissions(ClientInfo client) {
		return getClientPermissions(client.getClientDataBaseID());
	}

	
	public List<Permission> getServerGroupPermissions(ServerGroupInfo serverGroup) {
		return getServerGroupPermissions(serverGroup.getID());
	}

	public List<Permission> getChannelGroupPermissions(ChannelGroupInfo channelGroup) {
		return getChannelGroupPermissions(channelGroup.getID());
	}

	public List<Permission> getChannelClientPermissions(int channelID, DataBaseClientInfo dataBaseClient) {
		return getChannelClientPermissions(channelID, dataBaseClient.getClientDataBaseID());
	}

	public List<Permission> getChannelClientPermissions(int channelID, ClientInfo client) {
		return getChannelClientPermissions(channelID, client.getClientDataBaseID());
	}

	public List<Permission> getChannelClientPermissions(ChannelInfo channel, DataBaseClientInfo dataBaseClient) {
		return getChannelClientPermissions(channel.getChannelID(), dataBaseClient.getClientDataBaseID());
	}

	public List<Permission> getChannelClientPermissions(ChannelInfo channel, ClientInfo client) {
		return getChannelClientPermissions(channel.getChannelID(), client.getClientDataBaseID());
	}

	public ServerGroupInfo getServerGroupByID(int groupID) {
		for (ServerGroupInfo groups : getServerGroups()) {
			if (groups.getID() == groupID) {
				return groups;
			}
		}
		return null;
	}
	
	/**
	 * Method to get an ServerGroupInfo by providing an List of ServerGroups.
	 * Use getServerGroups() only once an save it as an variable.
	 * So if you repeat the Method for other ServerGroups the
	 * List is not every time requested from the server.
	 * 
	 * @see Ts3SyncAPI#getServerGroups()
	 * @param groupList
	 * @param groupID
	 * @return
	 */
	
	public ServerGroupInfo getServerGroupByID(List<ServerGroupInfo> groupList, int groupID) {
		for (ServerGroupInfo groups : groupList) {
			if (groups.getID() == groupID) {
				return groups;
			}
		}
		return null;
	}

	public List<Integer> getServerGroupIDsByClient(DataBaseClientInfo dataBaseClient) {
		return getServerGroupIDsByClient(dataBaseClient.getClientDataBaseID());
	}

	public List<Integer> getServerGroupIDsByClient(ClientInfo client) {
		return getServerGroupIDsByClient(client.getClientDataBaseID());
	}

	public List<ServerGroupInfo> getServerGroupsByClient(int clientDBID) {
		List<ServerGroupInfo> resultList = new ArrayList<ServerGroupInfo>();
		List<ServerGroupInfo> serverGroups = getServerGroups();

		for (int groupID : getServerGroupIDsByClient(clientDBID)) {
			resultList.add(getServerGroupByID(serverGroups, groupID));
		}

		return resultList;
	}

	public List<ServerGroupInfo> getServerGroupsByClient(DataBaseClientInfo dataBaseClient) {
		return getServerGroupsByClient(dataBaseClient.getClientDataBaseID());
	}

	public List<ServerGroupInfo> getServerGroupsByClient(ClientInfo client) {
		return getServerGroupsByClient(client.getClientDataBaseID());
	}

	public List<String> getServerGroupNamessByClient(DataBaseClientInfo dataBaseClient) {
		return getServerGroupNamesByClient(dataBaseClient.getClientDataBaseID());
	}

	public List<String> getServerGroupNamesByClient(ClientInfo client) {
		return getServerGroupNamesByClient(client.getClientDataBaseID());
	}


	public ChannelGroupInfo getChannelGroupByID(int groupID) {
		for (ChannelGroupInfo groups : getChannelGroups()) {
			if (groups.getID() == groupID) {
				return groups;
			}
		}
		return null;
	}

	public ChannelGroupInfo getChannelGroupByID(List<ChannelGroupInfo> groupList, int groupID) {
		for (ChannelGroupInfo groups : groupList) {
			if (groups.getID() == groupID) {
				return groups;
			}
		}
		return null;
	}

	public VirtualServerInfo getVirtualServerInfo() {
		return getServerInfo();
	}

	
	public List<DataBaseClientInfo> getClientDBList() {
		return getDatabaseClients();
	}

	public List<DataBaseClientInfo> getDataBaseClientInfos() {
		return getDatabaseClients();
	}
	
	/**
	 * Uses the ClientDataBaseIDs of clientdblist and gets the information for each databaseclient
	 * seperate with getDataBaseClientInfo().
	 * 
	 * @see Ts3SyncAPI#getDataBaseClientInfo(int)
	 * 
	 * @return
	 */
	
	public List<DataBaseClientInfo> getClientDBListDetailed() {
		List<DataBaseClientInfo> resultList = new ArrayList<DataBaseClientInfo>();
		String[] clients = query.getWriter().executeReadCommand("clientdblist")[0].split(TS_INFO_SEPERATOR);
		for (String client : clients) {
			int cldbid = Integer.parseInt(get(client, "cldbid="));
			resultList.add(getDataBaseClientInfo(cldbid));
		}
		return resultList;
	}
	
	public List<DataBaseClientInfo> getDataBaseClientsDetailed() {
		return getClientDBListDetailed();
	}

	public List<DataBaseClientInfo> getDataBaseClientInfosDetailed() {
		return getClientDBListDetailed();
	}
	
	public boolean isClientOnline(int clientID) {
		try {
			return getClientInfo(clientID) != null;
		} catch (UnknownClientInfoException exception) {
			return false;
		}
	}

	public boolean isClientOnline(String clientUUID) {
		try {
			return getClientInfoByUUID(clientUUID) != null;
		} catch (UnknownClientInfoException e) {
			return false;
		}
	}

	public ClientInfo getClientInfo(int clientID) throws UnknownClientInfoException {
		ClientInfo clientinfo = getClient(clientID);
		if (clientinfo != null) {
			return clientinfo;
		} else {
			throw new UnknownClientInfoException("The Client is null! Please use the DataBaseClientInfo to get the Information of the Player");
		}
	}

	public ClientInfo getClientByUUID(String clientUUID) {
		return getClient(getClientIDByUUID(clientUUID));
	}

	public ClientInfo getClientInfoByUUID(String clientUUID) throws UnknownClientInfoException {
		ClientInfo info = getClient(getClientIDByUUID(clientUUID));
		if (info == null) {
			throw new UnknownClientInfoException("Client with UUID: " + clientUUID + " was not found");
		}

		return info;
	}
	
	public List<ClientInfo> getOnlineClients() {
		return getClients();
	}
	
	/**
	 * Uses the ClientIDs of clientlist and gets the information for each client
	 * seperate with getClient().
	 * 
	 * @see Ts3SyncAPI#getClient()
	 * 
	 * @return
	 */

	public List<ClientInfo> getClientsDetailed() {
		List<ClientInfo> resultList = new ArrayList<ClientInfo>();

		String[] clients = query.getWriter().executeReadCommand("clientlist -voice")[0].split(TS_INFO_SEPERATOR);
		for (String client : clients) {
			int clientID = Integer.parseInt(get(client, "clid="));
			ClientInfo cInfo = getClient(clientID);
			cInfo.addInfo("client_flag_talking", client.split("client_flag_talking")[1]);
			resultList.add(cInfo);
		}
		return resultList;
	}

	public List<ClientInfo> getOnlineClientsDetailed() {
		return getClientsDetailed();
	}

	public int getOnlineClientSize() {
		return this.getVirtualServerInfo().getOnlineClientsSize();
	}

	public int getOnlineClientsRealSize() {
		return (int) getClients().stream().filter(clients -> !clients.isServerQueryClient()).count();
	}

	
	public DataBaseClientInfo getDataBaseClientInfo(ClientInfo client) {
		return getDataBaseClientInfo(client.getClientDataBaseID());
	}

	public ChannelInfo getChannelInfo(int channelID) throws UnknownChannelInfoException {
		ChannelInfo clientinfo = getChannel(channelID);
		if (clientinfo != null) {
			return clientinfo;
		} else {
			throw new UnknownChannelInfoException("The Channel is null! Please use channellist for more Information about the Channels of the Server.");
		}
	}

	
	public List<ChannelInfo> getChannelsDetailed() {
		List<ChannelInfo> resultList = new ArrayList<ChannelInfo>();
		for (ChannelInfo channel : getChannelsByCommand("channellist")) {
			ChannelInfo chInfo = getChannel(channel.getID());
			chInfo.addInfo("total_clients", channel.get("total_clients"));
			chInfo.addInfo("channel_needed_subscribe_power", channel.get("channel_needed_subscribe_power"));
			resultList.add(chInfo);
		}
		return resultList;
	}

	public List<Integer> getDatabaseIDsByServerGroup(ServerGroupInfo serverGroup) {
		return getDatabaseIDsByServerGroup(serverGroup.getID());
	}

	/**
	 * 
	 * @see Ts3SyncAPI#getDatabaseIDsByServerGroup
	 * @param servergroupID
	 * @return
	 */

	public List<DataBaseClientInfo> getDatabaseClientInfosByServerGroup(int servergroupID) {
		List<DataBaseClientInfo> resultList = new ArrayList<DataBaseClientInfo>();

		for (int dbIDs : getDatabaseIDsByServerGroup(servergroupID)) {
			resultList.add(getDataBaseClientInfo(dbIDs));
		}
		return resultList;
	}

	public List<DataBaseClientInfo> getDatabaseClientInfosByServerGroup(ServerGroupInfo serverGroup) {
		return getDatabaseClientInfosByServerGroup(serverGroup.getID());
	}


	public Map<Integer, List<Integer>> getDatabaseIDsByChannelGroup(ChannelGroupInfo channelGroup) {
		return getDatabaseIDsByChannelGroup(channelGroup.getID());
	}

	/**
	 * @see Ts3BasicAPI#getDatabaseIDsByChannelGroup(int)
	 * @param channelgroupID
	 * @return [DataBaseClientInfo, List<ChannelIDs>]
	 */

	public Map<DataBaseClientInfo, List<Integer>> getDatabaseClientInfosByChannelGroup(int channelgroupID) {
		Map<DataBaseClientInfo, List<Integer>> resultMap = new HashMap<>();
		Map<Integer, List<Integer>> getMap = getDatabaseIDsByChannelGroup(channelgroupID);

		for (int dbIDs : getMap.keySet()) {
			DataBaseClientInfo dbClInfo = getDataBaseClientInfo(dbIDs);
			resultMap.put(dbClInfo, getMap.get(dbIDs));
		}
		return resultMap;
	}

	public Map<DataBaseClientInfo, List<Integer>> getDatabaseClientInfosByChannelGroup(ChannelGroupInfo channelGroup) {
		return getDatabaseClientInfosByChannelGroup(channelGroup.getID());
	}

	public List<Integer> getDatabaseIDsByChannelGroup(int channelgroupID, ChannelInfo channel) {
		return getDatabaseIDsByChannelAndGroup(channelgroupID, channel.getChannelID());
	}

	public List<Integer> getDatabaseIDsByChannelAndGroup(ChannelGroupInfo channelGroup, int channelID) {
		return getDatabaseIDsByChannelAndGroup(channelGroup.getID(), channelID);
	}

	public List<Integer> getDatabaseIDsByChannelAndGroup(ChannelGroupInfo channelGroup, ChannelInfo channel) {
		return getDatabaseIDsByChannelAndGroup(channelGroup.getID(), channel.getChannelID());
	}


	public List<DataBaseClientInfo> getDatabaseClientInfosByChannelAndGroup(int channelGroupID, ChannelInfo channel) {
		return getDatabaseClientInfosByChannelAndGroup(channelGroupID, channel.getChannelID());
	}

	public List<DataBaseClientInfo> getDatabaseClientInfosByChannelGroup(ChannelGroupInfo channelGroup, int channelID) {
		return getDatabaseClientInfosByChannelAndGroup(channelGroup.getID(), channelID);
	}

	public List<DataBaseClientInfo> getDatabaseClientInfosByChannelAndGroup(ChannelGroupInfo channelGroup, ChannelInfo channel) {
		return getDatabaseClientInfosByChannelAndGroup(channelGroup.getID(), channel.getChannelID());
	}


	public Map<Integer, List<Integer>> getChannelGroupsByDatabaseClient(DataBaseClientInfo dataBaseClient) {
		return getChannelGroupsByDatabaseID(dataBaseClient.getClientDataBaseID());
	}

	public Map<Integer, List<Integer>> getChannelGroupsByClient(ClientInfo client) {
		return getChannelGroupsByDatabaseID(client.getClientDataBaseID());
	}

	/**
	 * @see Ts3SyncAPI#getChannelGroupsByDatabaseID(int)
	 * @param clientDataBaseID
	 * @return [ChannelGroupInfo, List<ChannelID>]
	 */

	public Map<ChannelGroupInfo, List<Integer>> getChannelGroupInfosByDatabaseID(int clientDataBaseID) {
		Map<ChannelGroupInfo, List<Integer>> resultMap = new HashMap<>();
		List<ChannelGroupInfo> channelGroups = getChannelGroups();
		Map<Integer, List<Integer>> getMap = getChannelGroupsByDatabaseID(clientDataBaseID);
		for (int cgID : getMap.keySet()) {
			resultMap.put(getChannelGroupByID(channelGroups, cgID), getMap.get(cgID));
		}
		return resultMap;
	}

	public Map<ChannelGroupInfo, List<Integer>> getChannelGroupInfosByDatabaseClient(DataBaseClientInfo dataBaseClient) {
		return getChannelGroupInfosByDatabaseID(dataBaseClient.getClientDataBaseID());
	}

	public Map<ChannelGroupInfo, List<Integer>> getChannelGroupInfosByClient(ClientInfo client) {
		return getChannelGroupInfosByDatabaseID(client.getClientDataBaseID());
	}


	public Map<Integer, List<Integer>> getChannelGroupsByChannel(ChannelInfo channel) {
		return getChannelGroupsByChannelID(channel.getChannelID());
	}

	/**
	 * @see Ts3SyncAPI#getChannelGroupsByChannelID(int)
	 * @param channelID
	 * @return [ChannelGroupInfo, ClientDataBaseID]
	 */

	public Map<ChannelGroupInfo, List<Integer>> getChannelGroupInfosByChannelID(int channelID) {
		Map<ChannelGroupInfo, List<Integer>> resultMap = new HashMap<>();
		Map<Integer, List<Integer>> getMap = getChannelGroupsByChannelID(channelID);
		List<ChannelGroupInfo> channelGroups = getChannelGroups();

		for (int dbIDs : getMap.keySet()) {
			resultMap.put(getChannelGroupByID(channelGroups, dbIDs), getMap.get(dbIDs));
		}
		return resultMap;
	}

	
	public List<PrivilegeKeyInfo> getTokenList() {
		return getPrivilegeKeys();
	}

	
	public List<VirtualServerInfo> getVirtualServersOffline() {
		return getVirtualServersByCommand("serverlist -uid -onlyoffline");
	}

	public List<VirtualServerInfo> getOfflineVirtualServers() {
		return getVirtualServersOffline();
	}

	public List<VirtualServerInfo> getVirtualServersAll() {
		return getVirtualServersByCommand("serverlist -uid -all");
	}

	public List<VirtualServerInfo> getAllVirtualServers() {
		return getVirtualServersAll();
	}

	public void addBan(String ip, String name, String clientUUID, String myTSID, long banTime) {
		addBan(ip, name, clientUUID, myTSID, banTime, null);
	}

	public void addBan(String ip, String clientUUID, long banTime, String banReason) {
		addBan(ip, null, clientUUID, null, banTime, banReason);
	}

	public void addBan(String ip, String clientUUID, long banTime) {
		addBan(ip, null, clientUUID, null, banTime, null);
	}

	public void addBan(String clientUUID, long banTime, String banReason) {
		addBan(null, null, clientUUID, null, banTime, banReason);
	}

	public void addBan(String clientUUID, long banTime) {
		addBan(null, null, clientUUID, null, banTime, null);
	}

	public void addBanIP(String ip, long banTime, String banReason) {
		addBan(ip, null, null, null, banTime, banReason);
	}

	public void addBanIP(String ip, long banTime) {
		addBan(ip, null, null, null, banTime, null);
	}

	public void banClient(ClientInfo client, long banTime, String banReason) {
		banClient(client.getID(), banTime, banReason);
	}

	public void banClient(int clientID, long banTime) {
		banClient(clientID, banTime, null);
	}

	public void banClient(ClientInfo client, long banTime) {
		banClient(client.getID(), banTime);
	}

	public void banClient(int clientID) {
		banClient(clientID, -1);
	}

	public void banClient(ClientInfo client) {
		banClient(client.getID());
	}
	
	public void banClients(List<ClientInfo> clients, long banTime, String banReason) {
		StringBuilder commandBuilder = new StringBuilder("banclient");
		commandBuilder.append(" time=" + banTime);
		if (banReason != null)
			commandBuilder.append(" banreason=" + Formatter.toTsFormat(banReason));
		for (ClientInfo banClient : clients) {
			commandBuilder.append(" clid=" + banClient.getID());
			commandBuilder.append("|");			
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void banClients(List<ClientInfo> clients, long banTime) {
		banClients(clients, banTime, null);
	}
	
	public void banClientIDs(List<Integer> clientIDs, long banTime, String banReason) {
		StringBuilder commandBuilder = new StringBuilder("banclient");
		commandBuilder.append(" time=" + banTime);
		if (banReason != null)
			commandBuilder.append(" banreason=" + Formatter.toTsFormat(banReason));
		for (int banClientID : clientIDs) {
			commandBuilder.append(" clid=" + banClientID);
			commandBuilder.append("|");			
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void banClientIDs(List<Integer> clientIDs, long banTime) {
		banClientIDs(clientIDs, banTime, null);
	}
	
	public void unbanClient(String clientUUID) {
		for (BanInfo bans : getBans()) {
			if (bans.getClientUUID().equals(clientUUID)) {
				removeBan(bans.getBanID());
			}
		}
	}

	public void unbanClientIP(String ip) {
		for (BanInfo bans : getBans()) {
			if (bans.getIP().equals(ip)) {
				removeBan(bans.getBanID());
			}
		}
	}

	public void unbanAllClients() {
		removeAllBans();
	}

	
	public void startVirtualServer(VirtualServerInfo virtualServer) {
		startVirtualServer(virtualServer.getServerID());
	}


	public void stopVirtualServer(VirtualServerInfo virtualServer, String reasonmsg) {
		stopVirtualServer(virtualServer.getServerID(), reasonmsg);
	}

	public void stopVirtualServer(VirtualServerInfo virtualServer) {
		stopVirtualServer(virtualServer.getServerID());
	}

	
	public void addChannelPermission(int channelID, int permissionID, int permissionValue) {
		addChannelPermission(channelID, permissionID, null, permissionValue);
	}

	public void addChannelPermission(int channelID, Permission permission) {
		addChannelPermission(channelID, permission.getPermID(), permission.getPermValue());
	}

	public void addChannelPermission(ChannelInfo channel, int permissionID, String permissionName, int permissionValue) {
		addChannelPermission(channel.getChannelID(), permissionID, permissionName, permissionValue);
	}

	public void addChannelPermission(ChannelInfo channel, int permissionID, int permissionValue) {
		addChannelPermission(channel.getChannelID(), permissionID, permissionValue);
	}

	public void addChannelPermission(ChannelInfo channel, Permission permission) {
		addChannelPermission(channel.getChannelID(), permission.getPermID(), permission.getPermValue());
	}
	
	public void addChannelPermissions(int channelID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channeladdperm");
		commandBuilder.append(" cid=" + channelID);
		for (Permission addPerm : permissions) {
			if (addPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + addPerm.getPermID());
			if (addPerm.getPermValue() != -1)
				commandBuilder.append(" permvalue=" + addPerm.getPermValue());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void addChannelPermissions(ChannelInfo channel, List<Permission> permissions) {
		addChannelPermissions(channel.getChannelID(), permissions);
	}
	
	public void addChannelClientPermission(int channelID, int clientdataBaseID, Permission permission) {
		addChannelClientPermission(channelID, clientdataBaseID, permission.getPermID(), permission.getPermValue());
	}

	public void addChannelClientPermission(int channelID, DataBaseClientInfo dbClientInfo, Permission permission) {
		addChannelClientPermission(channelID, dbClientInfo.getClientDataBaseID(), permission.getPermID(), permission.getPermValue());
	}

	public void addChannelClientPermission(ChannelInfo channel, int clientdataBaseID, int permissionID, int permissionValue) {
		addChannelClientPermission(channel.getChannelID(), clientdataBaseID, permissionID, permissionValue);
	}

	public void addChannelClientPermission(ChannelInfo channel, DataBaseClientInfo dbClientInfo, int permissionID, int permissionValue) {
		addChannelClientPermission(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permissionID, permissionValue);
	}

	public void addChannelClientPermission(ChannelInfo channel, int clientdataBaseID, Permission permission) {
		addChannelClientPermission(channel.getChannelID(), clientdataBaseID, permission.getPermID(), permission.getPermValue());
	}

	public void addChannelClientPermission(ChannelInfo channel, DataBaseClientInfo dbClientInfo, Permission permission) {
		addChannelClientPermission(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permission.getPermID(), permission.getPermValue());
	}
	
	public void addChannelClientPermissions(int channelID, int clientdataBaseID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channelclientaddperm");
		commandBuilder.append(" cid=" + channelID);
		commandBuilder.append(" cldbid=" + clientdataBaseID);
		
		for (Permission addPerm : permissions) {
			if (addPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + addPerm.getPermID());
			if (addPerm.getPermValue() != -1)
				commandBuilder.append(" permvalue=" + addPerm.getPermValue());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void addChannelClientPermissions(int channelID, DataBaseClientInfo dbClientInfo, List<Permission> permissions) {
		addChannelClientPermissions(channelID, dbClientInfo.getClientDataBaseID(), permissions);
	}
	
	public void addChannelClientPermissions(ChannelInfo channel, int clientdataBaseID, List<Permission> permissions) {
		addChannelClientPermissions(channel.getChannelID(), clientdataBaseID, permissions);
	}
	
	public void addChannelClientPermissions(ChannelInfo channel, DataBaseClientInfo dbClientInfo, List<Permission> permissions) {
		addChannelClientPermissions(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permissions);
	}
	

	public void removeChannelClientPermission(int channelID, int clientdataBaseID, int permissionID) {
		removeChannelClientPermission(channelID, clientdataBaseID, permissionID, null);
	}

	public void removeChannelClientPermission(int channelID, int clientdataBaseID, Permission permission) {
		removeChannelClientPermission(channelID, clientdataBaseID, permission.getPermID());
	}

	public void removeChannelClientPermission(int channelID, DataBaseClientInfo dbClientInfo, Permission permission) {
		removeChannelClientPermission(channelID, dbClientInfo.getClientDataBaseID(), permission.getPermID());
	}

	public void removeChannelClientPermission(ChannelInfo channel, int clientdataBaseID, int permissionID) {
		removeChannelClientPermission(channel.getChannelID(), clientdataBaseID, permissionID);
	}

	public void removeChannelClientPermission(ChannelInfo channel, int clientdataBaseID, Permission permission) {
		removeChannelClientPermission(channel.getChannelID(), clientdataBaseID, permission.getPermID());
	}

	public void removeChannelClientPermission(ChannelInfo channel, DataBaseClientInfo dbClientInfo, int permissionID) {
		removeChannelClientPermission(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permissionID);
	}

	public void removeChannelClientPermission(ChannelInfo channel, DataBaseClientInfo dbClientInfo, Permission permission) {
		removeChannelClientPermission(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permission.getPermID());
	}
	
	public void removeChannelClientPermissions(int channelID, int clientdataBaseID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channelclientdelperm");
		commandBuilder.append(" cid=" + channelID);
		commandBuilder.append(" cldbid=" + clientdataBaseID);
		
		for (Permission remPerm : permissions) {
			if (remPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + remPerm.getPermID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeChannelClientPermissions(int channelID, DataBaseClientInfo dbClientInfo, List<Permission> permissions) {
		removeChannelClientPermissions(channelID, dbClientInfo.getClientDataBaseID(), permissions);
	}
	
	public void removeChannelClientPermissions(ChannelInfo channel, int clientdataBaseID, List<Permission> permissions) {
		removeChannelClientPermissions(channel.getChannelID(), clientdataBaseID, permissions);
	}
	
	public void removeChannelClientPermissions(ChannelInfo channel, DataBaseClientInfo dbClientInfo, List<Permission> permissions) {
		removeChannelClientPermissions(channel.getChannelID(), dbClientInfo.getClientDataBaseID(), permissions);
	}
	

	public int createChannel(String channelName) {
		return createChannel(channelName, new HashMap<>());
	}

	public void deleteChannel(ChannelInfo channel, boolean force) {
		deleteChannel(channel.getChannelID(), force);
	}

	public void deleteChannel(int channelID) {
		deleteChannel(channelID, true);
	}

	public void deleteChannel(ChannelInfo channel) {
		deleteChannel(channel.getChannelID(), true);
	}

	public void removeChannelPermission(int channelID, int permissionID) {
		removeChannelPermission(channelID, permissionID, null);
	}

	public void removeChannelPermission(int channelID, String permissionName) {
		removeChannelPermission(channelID, -1, permissionName);
	}

	public void removeChannelPermission(ChannelInfo channel, int permissionID, String permissionName) {
		removeChannelPermission(channel.getChannelID(), permissionID, permissionName);
	}

	public void removeChannelPermission(ChannelInfo channel, int permissionID) {
		removeChannelPermission(channel.getChannelID(), permissionID);
	}

	public void removeChannelPermission(ChannelInfo channel, Permission permission) {
		removeChannelPermission(channel.getChannelID(), permission.getPermID());
	}

	public void removeChannelAllPermissions(int channelID) {
		removeChannelPermission(channelID, -1, null);
	}

	public void removeChannelAllPermissions(ChannelInfo channel) {
		removeChannelAllPermissions(channel.getChannelID());
	}
	
	public void removeChannelPermissions(int channelID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channeldelperm");
		commandBuilder.append(" cid=" + channelID);

		for (Permission remPerm : permissions) {
			if (remPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + remPerm.getPermID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeChannelPermissions(ChannelInfo channel, List<Permission> permissions) {
		removeChannelPermissions(channel.getChannelID(), permissions);
	}
	
	public void removeChannelPermissionIDs(int channelID, List<Integer> permissionIDs) {
		StringBuilder commandBuilder = new StringBuilder("channeldelperm");
		commandBuilder.append(" cid=" + channelID);

		for (int remPermID : permissionIDs) {
			if (remPermID != -1)
				commandBuilder.append(" permid=" + remPermID);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeChannelPermissionIDs(ChannelInfo channel, List<Integer> permissions) {
		removeChannelPermissionIDs(channel.getChannelID(), permissions);
	}

	public void editChannel(int channelID, ChannelProperty channelProperty, String setValue) {
		Map<ChannelProperty, String> prop = new HashMap<>();
		prop.put(channelProperty, setValue);
		editChannel(channelID, prop);
	}

	public void editChannel(ChannelInfo channel, Map<ChannelProperty, String> channelProperties) {
		editChannel(channel.getChannelID(), channelProperties);
	}

	public void editChannel(ChannelInfo channel, ChannelProperty channelProperty, String setValue) {
		editChannel(channel.getChannelID(), channelProperty, setValue);
	}

	public int createChannelGroup(String channelGroupName) {
		return createChannelGroup(channelGroupName, ChannelGroupType.NORMAL);
	}

	public void addChannelGroupPermission(int channelGroupID, Permission permission) {
		addChannelGroupPermission(channelGroupID, permission.getPermID(), permission.getPermValue());
	}

	public void addChannelGroupPermission(ChannelGroupInfo channelGroup, int permissionID, String permissionName, int permissionValue) {
		addChannelGroupPermission(channelGroup.getChannelGroupID(), permissionID, permissionName, permissionValue);
	}

	public void addChannelGroupPermission(ChannelGroupInfo channelGroup, int permissionID, int permissionValue) {
		addChannelGroupPermission(channelGroup.getChannelGroupID(), permissionID, permissionValue);
	}

	public void addChannelGroupPermission(ChannelGroupInfo channelGroup, Permission permission) {
		addChannelGroupPermission(channelGroup.getChannelGroupID(), permission.getPermID(), permission.getPermValue());
	}
	
	public void addChannelGroupPermissions(int channelGroupID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channelgroupaddperm");
		commandBuilder.append(" cgid=" + channelGroupID);

		for (Permission addPerm : permissions) {
			if (addPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + addPerm.getPermID());
			if (addPerm.getPermValue() != -1)
				commandBuilder.append(" permvalue=" + addPerm.getPermValue());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void addChannelGroupPermissions(ChannelGroupInfo channelGroup, List<Permission> permissions) {
		addChannelGroupPermissions(channelGroup.getChannelGroupID(), permissions);
	}

	public int copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName) {
		return copyChannelGroup(sourceChannelGroupID, targetChannelGroupID, channelGroupName, ChannelGroupType.NORMAL);
	}

	public int copyChannelGroup(ChannelGroupInfo channelGroup, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		return copyChannelGroup(channelGroup.getID(), targetChannelGroupID, channelGroupName, channelGroupType);
	}

	public int copyChannelGroup(ChannelGroupInfo channelGroup, int targetChannelGroupID, String channelGroupName) {
		return copyChannelGroup(channelGroup.getID(), targetChannelGroupID, channelGroupName, ChannelGroupType.NORMAL);
	}

	public void deleteChannelGroup(ChannelGroupInfo channelGroup, boolean force) {
		deleteChannelGroup(channelGroup.getID(), force);
	}

	public void deleteChannelGroup(int channelGroupID) {
		deleteChannelGroup(channelGroupID, true);
	}

	public void deleteChannelGroup(ChannelGroupInfo channelGroup) {
		deleteChannelGroup(channelGroup.getID(), true);
	}

	public void removeChannelGroupPermission(int channelGroupID, Permission permission) {
		removeChannelGroupPermission(channelGroupID, permission.getPermID());
	}

	public void removeChannelGroupPermission(ChannelGroupInfo channelGroup, int permissionID) {
		removeChannelGroupPermission(channelGroup.getID(), permissionID);
	}

	public void removeChannelGroupPermission(ChannelGroupInfo channelGroup, Permission permission) {
		removeChannelGroupPermission(channelGroup.getID(), permission.getPermID());
	}
	
	public void removeChannelGroupPermissions(int channelID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("channelgroupdelperm");
		commandBuilder.append(" cid=" + channelID);

		for (Permission remPerm : permissions) {
			if (remPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + remPerm.getPermID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeChannelGroupPermissions(ChannelGroupInfo channelGroup, List<Permission> permissions) {
		removeChannelGroupPermissions(channelGroup.getChannelGroupID(), permissions);
	}
	
	public void removeChannelGroupPermissionIDs(int channelID, List<Integer> permissionIDs) {
		StringBuilder commandBuilder = new StringBuilder("channelgroupdelperm");
		commandBuilder.append(" cid=" + channelID);

		for (int remPermID : permissionIDs) {
			if (remPermID != -1)
				commandBuilder.append(" permid=" + remPermID);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeChannelGroupPermissionIDs(ChannelGroupInfo channelGroup, List<Integer> permissions) {
		removeChannelGroupPermissionIDs(channelGroup.getChannelGroupID(), permissions);
	}

	public void renameChannelGroup(ChannelInfo channel, String channelName) {
		renameChannelGroup(channel.getChannelID(), channelName);
	}

	public void moveChannel(int channelID, int channelParentID) {
		moveChannel(channelID, channelParentID, 0);
	}

	public void moveChannel(ChannelInfo channel, int channelParentID, int order) {
		moveChannel(channel.getChannelID(), channelParentID, order);
	}

	public void moveChannel(ChannelInfo channel, int channelParentID) {
		moveChannel(channel.getChannelID(), channelParentID, 0);
	}

	public void moveChannel(ChannelInfo channel, ChannelInfo channelParent, int order) {
		moveChannel(channel.getChannelID(), channelParent.getChannelID(), order);
	}

	public void moveChannel(ChannelInfo channel, ChannelInfo channelParent) {
		moveChannel(channel.getChannelID(), channelParent.getChannelID(), 0);
	}

	public void addClientPermission(int clientDataBaseID, int permissionID, int permissionValue) {
		addClientPermission(clientDataBaseID, permissionID, permissionValue, false);
	}

	public void addClientPermission(int clientDataBaseID, Permission permission) {
		addClientPermission(clientDataBaseID, permission.getPermID(), permission.getPermValue(), permission.isSkip());
	}

	public void addClientPermission(DataBaseClientInfo dataBaseClient, int permissionID, int permissionValue, boolean permSkip) {
		addClientPermission(dataBaseClient.getClientDataBaseID(), permissionID, permissionValue, permSkip);
	}

	public void addClientPermission(DataBaseClientInfo dataBaseClient, int permissionID, int permissionValue) {
		addClientPermission(dataBaseClient.getClientDataBaseID(), permissionID, permissionValue, false);
	}

	public void addClientPermission(DataBaseClientInfo dataBaseClient, Permission permission) {
		addClientPermission(dataBaseClient.getClientDataBaseID(), permission.getPermID(), permission.getPermValue(), permission.isSkip());
	}
	
	public void addClientPermissions(int clientDataBaseID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("clientaddperm");
		commandBuilder.append(" cldbid=" + clientDataBaseID);

		for (Permission addPerm : permissions) {
			if (addPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + addPerm.getPermID());
			if (addPerm.getPermValue() != -1)
				commandBuilder.append(" permvalue=" + addPerm.getPermValue());
			if (addPerm.isSkip())
				commandBuilder.append(" permskip=" + Formatter.toInt(addPerm.isSkip()));
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void addClientPermissions(DataBaseClientInfo dataBaseClient, List<Permission> permissions) {
		addChannelGroupPermissions(dataBaseClient.getClientDataBaseID(), permissions);
	}
	

	public void deleteDataBaseClient(DataBaseClientInfo dataBaseClient) {
		deleteDataBaseClient(dataBaseClient.getClientDataBaseID());
	}

	public void deleteDataBaseClient(ClientInfo client) {
		deleteDataBaseClient(client.getClientDataBaseID());
	}

	
	public void editDataBaseClient(DataBaseClientInfo dataBaseClient, String description) {
		editDataBaseClient(dataBaseClient.getClientDataBaseID(), description);
	}


	public DataBaseClientInfo getDataBaseClientInfoByUUID(String clientUUID) {
		return getDataBaseClientInfo(getDataBaseClientIDByUUID(clientUUID));
	}

	public List<DataBaseClientInfo> getDataBaseClientInfosByName(String clientLastName) {
		List<DataBaseClientInfo> resultList = new ArrayList<>();
		for (Integer ids : getDataBaseClientIDsByName(clientLastName)) {
			resultList.add(getDataBaseClientInfo(ids));
		}

		return resultList;
	}

	public void removeClientPermission(int clientDataBaseID, String permissionName) {
		removeClientPermission(clientDataBaseID, -1, permissionName);
	}

	public void removeClientPermission(DataBaseClientInfo dataBaseClient, int permissionID) {
		removeClientPermission(dataBaseClient.getClientDataBaseID(), permissionID, null);
	}

	public void removeClientPermission(DataBaseClientInfo dataBaseClient, Permission permission) {
		removeClientPermission(dataBaseClient.getClientDataBaseID(), permission.getPermID(), null);
	}

	public void removeClientPermission(DataBaseClientInfo dataBaseClient, String permissionName) {
		removeClientPermission(dataBaseClient.getClientDataBaseID(), -1, permissionName);
	}

	public void removeClientPermission(ClientInfo client, int permissionID) {
		removeClientPermission(client.getClientDataBaseID(), permissionID, null);
	}

	public void removeClientPermission(ClientInfo client, Permission permission) {
		removeClientPermission(client.getClientDataBaseID(), permission.getPermID(), null);
	}

	public void removeClientPermission(ClientInfo client, String permissionName) {
		removeClientPermission(client.getClientDataBaseID(), -1, permissionName);
	}
	
	public void removeClientPermissions(int clientDataBaseID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("clientdelperm");
		commandBuilder.append(" cldbid=" + clientDataBaseID);

		for (Permission remPerm : permissions) {
			if (remPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + remPerm.getPermID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeClientPermissions(DataBaseClientInfo dataBaseClient, List<Permission> permissions) {
		removeClientPermissions(dataBaseClient.getClientDataBaseID(), permissions);
	}
	
	public void removeClientPermissionIDs(int clientDataBaseID, List<Integer> permissionIDs) {
		StringBuilder commandBuilder = new StringBuilder("clientdelperm");
		commandBuilder.append(" cldbid=" + clientDataBaseID);

		for (int remPermID : permissionIDs) {
			if (remPermID != -1)
				commandBuilder.append(" permid=" + remPermID);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	
	public void removeClientPermissionIDs(DataBaseClientInfo dataBaseClient, List<Integer> permissions) {
		removeClientPermissionIDs(dataBaseClient.getClientDataBaseID(), permissions);
	}
	
	public void editClient(ClientInfo client, Map<ClientProperty, String> clientProperties) {
		editClient(client.getID(), clientProperties);
	}

	public void editClient(int clientID, ClientProperty clientProperty, String setValue) {
		Map<ClientProperty, String> prop = new HashMap<>();
		prop.put(clientProperty, setValue);
		editClient(clientID, prop);
	}

	public void editClient(ClientInfo client, ClientProperty clientProperty, String setValue) {
		editClient(client.getID(), clientProperty, setValue);
	}

	public List<ClientInfo> getClientsByName(String clientName) {
		List<ClientInfo> resultList = new ArrayList<>();
		for (int clientIDs : getClientIDsByName(clientName)) {
			resultList.add(getClient(clientIDs));
		}
		return resultList;
	}

	public List<ClientInfo> getClientInfosByName(String clientName) throws UnknownClientInfoException {
		List<ClientInfo> resultList = new ArrayList<>();
		for (int clientIDs : getClientIDsByName(clientName)) {
			resultList.add(getClientInfo(clientIDs));
		}
		return resultList;
	}

	public void kickClientFromServer(ClientInfo client, String reason) {
		kickClientFromServer(client.getID(), reason);
	}

	public void kickClientFromServer(ClientInfo client) {
		kickClientFromServer(client.getID());
	}
	
	public void kickClientsFromServer(List<ClientInfo> clients, String reason) {
		StringBuilder commandBuilder = new StringBuilder("clientkick");
		commandBuilder.append(" reasonid=5");
		if (reason != null)
			commandBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		
		for (ClientInfo kickClients : clients) {
			commandBuilder.append(" clid=" + kickClients.getClientID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void kickClientsFromServer(List<ClientInfo> clients) {
		kickClientsFromServer(clients, null);
	}
	
	public void kickClientIDsFromServer(List<Integer> clientIDs, String reason) {
		StringBuilder commandBuilder = new StringBuilder("clientkick");
		commandBuilder.append(" reasonid=5");
		if (reason != null)
			commandBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		
		for (int kickClientIDs : clientIDs) {
			commandBuilder.append(" clid=" + kickClientIDs);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void kickClientIDsFromServer(List<Integer> clientIDs) {
		kickClientIDsFromServer(clientIDs, null);
	}
	
	public void kickClientFromChannel(ClientInfo client, String reason) {
		kickClientFromChannel(client.getID(), reason);
	}

	public void kickClientFromChannel(ClientInfo client) {
		kickClientFromChannel(client.getID());
	}
	
	public void kickClientsFromChannel(List<ClientInfo> clients, String reason) {
		StringBuilder commandBuilder = new StringBuilder("clientkick");
		commandBuilder.append(" reasonid=4");
		if (reason != null)
			commandBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		
		for (ClientInfo kickClients : clients) {
			commandBuilder.append(" clid=" + kickClients.getClientID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void kickClientsFromChannel(List<ClientInfo> clients) {
		kickClientsFromChannel(clients, null);
	}
	
	public void kickClientIDsFromChannel(List<Integer> clientIDs, String reason) {
		StringBuilder commandBuilder = new StringBuilder("clientkick");
		commandBuilder.append(" reasonid=4");
		if (reason != null)
			commandBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		
		for (int kickClientIDs : clientIDs) {
			commandBuilder.append(" clid=" + kickClientIDs);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void kickClientIDsFromChannel(List<Integer> clientIDs) {
		kickClientIDsFromChannel(clientIDs, null);
	}

	public void addComplain(DataBaseClientInfo dataBaseClient, String message) {
		addComplain(dataBaseClient.getClientDataBaseID(), message);
	}

	public void deleteComplain(int clientDBID, DataBaseClientInfo fromDataBaseClient) {
		deleteComplain(clientDBID, fromDataBaseClient.getClientDataBaseID());
	}

	public void deleteComplain(DataBaseClientInfo dataBaseClient, int fromClientDBID) {
		deleteComplain(dataBaseClient.getClientDataBaseID(), fromClientDBID);
	}

	public void deleteComplain(DataBaseClientInfo dataBaseClient, DataBaseClientInfo fromDataBaseClient) {
		deleteComplain(dataBaseClient.getClientDataBaseID(), fromDataBaseClient.getClientDataBaseID());
	}

	public void deleteAllComplains(DataBaseClientInfo dataBaseClient) {
		deleteAllComplains(dataBaseClient.getClientDataBaseID());
	}
	
	public Map<String, String> getCustomInfo(DataBaseClientInfo dataBaseClient) {
		return getCustomInfo(dataBaseClient.getClientDataBaseID());
	}

	public String getCustomInfo(int clientDBID, String ident) {
		return getCustomInfo(clientDBID).get(ident);
	}

	public String getCustomInfo(DataBaseClientInfo dataBaseClient, String ident) {
		return getCustomInfo(dataBaseClient).get(ident);
	}

	public Map<DataBaseClientInfo, List<String>> searchDBClientCustomInfo(String ident, String pattern) {
		Map<DataBaseClientInfo, List<String>> resultMap = new HashMap<>();
		List<Integer> addedList = new ArrayList<>();

		Map<Integer, List<String>> searchList = searchDBIDsCustomInfo(ident, pattern);
		for (int clDBID : searchList.keySet()) {
			DataBaseClientInfo dbClInfo;
			if (addedList.contains(clDBID)) {
				dbClInfo = resultMap.keySet().stream().filter(info -> info.getClientDataBaseID() == clDBID).findFirst().get();
			} else {
				dbClInfo = getDataBaseClientInfo(clDBID);
				addedList.add(clDBID);
			}
			resultMap.put(dbClInfo, searchList.get(clDBID));
		}
		return resultMap;
	}

	
	public void setCustomInfo(DataBaseClientInfo dataBaseClient, String ident, String value) {
		setCustomInfo(dataBaseClient.getClientDataBaseID(), ident, value);
	}

	public void setCustomInfo(ClientInfo client, String ident, String value) {
		setCustomInfo(client.getClientDataBaseID(), ident, value);
	}

	
	public void deleteCustomInfo(DataBaseClientInfo dataBaseClient, String ident) {
		deleteCustomInfo(dataBaseClient.getClientDataBaseID(), ident);
	}

	

	public void createFileDirectory(ChannelInfo channel, String dirName) {
		createFileDirectory(channel.getChannelID(), channel.getPassword(), dirName);
	}

	public void deleteFile(ChannelInfo channel, String fileName) {
		deleteFile(channel.getChannelID(), channel.getPassword(), fileName);
	}

	public FileInfo getFileInfo(ChannelInfo channel, String fileName) {
		return getFileInfo(channel.getChannelID(), channel.getPassword(), fileName);
	}

	public List<FileInfo> getChannelFilesByPath(ChannelInfo channel, String filePath) {
		return getChannelFilesByPath(channel.getChannelID(), channel.getPassword(), filePath);
	}

	public void renameFile(ChannelInfo channel, String oldFilePath, String newFilePath) {
		renameFile(channel.getChannelID(), channel.getPassword(), oldFilePath, newFilePath);
	}

	
	public void moveFile(ChannelInfo channel, String oldFilePath, ChannelInfo newchannel, String newFilePath) {
		moveFile(channel.getChannelID(), channel.getPassword(), oldFilePath, newchannel.getChannelID(), newchannel.getPassword(), newFilePath);
	}

	public void stopFileTransfer(int serverFileTransferID) {
		stopFileTransfer(serverFileTransferID, false);
	}

	public void editInstance(InstanceProperty instanceProperty, String setValue) {
		Map<InstanceProperty, String> prop = new HashMap<>();
		prop.put(instanceProperty, setValue);
		editInstance(prop);
	}

	
	public void addToLog(LogLevel logLevel, String logMessage) {
		addToLog(logLevel.getValue(), logMessage);
	}

	public List<String> getInstanceLog() {
		return getLog(100, false, true, 0);
	}

	public List<String> getInstanceLog(int lines) {
		return getLog(lines, false, true, 0);
	}

	public List<String> getInstanceLog(int lines, int beginPos) {
		return getLog(lines, false, true, beginPos);
	}

	public List<String> getVirtualServerLog() {
		return getLog(100, false, false, 0);
	}

	public List<String> getVirtualServerLog(int lines) {
		return getLog(lines, false, false, 0);
	}

	public List<String> getVirtualServerLog(int lines, int beginPos) {
		return getLog(lines, false, false, beginPos);
	}

	
	public void readOfflineMessage(int offlineMessageID) {
		updateOfflineMessageFlag(offlineMessageID, true);
	}

	
	public String createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID) {
		return createPrivilegeKey(keyType, groupID, channelID, null);
	}

	public void deletePrivilegeKey(PrivilegeKeyInfo privilegeKey) {
		deletePrivilegeKey(privilegeKey.getKey());
	}

	public void usePrivilegeKey(PrivilegeKeyInfo privilegeKey) {
		usePrivilegeKey(privilegeKey.getKey());
	}

	
	public CreatedQueryLogin createQueryLogin(String loginName) {
		return createQueryLogin(loginName, -1);
	}

	public void deleteQueryLogin(CreatedQueryLogin queryLogin) {
		deleteQueryLogin(queryLogin.getClientDataBaseID());
	}

	public List<CreatedQueryLogin> getQueryLogins() {
		return getQueryLogins(null, -1, -1);
	}

	public List<CreatedQueryLogin> getQueryLogins(String pattern) {
		return getQueryLogins(pattern, -1, -1);
	}

	public List<CreatedQueryLogin> getQueryLogins(String pattern, int start) {
		return getQueryLogins(pattern, start, -1);
	}

	public void sendClientMessage(TextMessageType messageType, int clientID, String message) {
		sendTextMessage(messageType, clientID, message);
	}
	
	public void sendClientTextMessage(TextMessageType messageType, int clientID, String message) {
		sendTextMessage(messageType, clientID, message);
	}
	
	
	public void deleteVirtualServer(VirtualServerInfo virtualServer) {
		deleteVirtualServer(virtualServer.getID());
	}

	public void editVirtualServer(VirtualServerProperty virtualServerProperty, String setValue) {
		Map<VirtualServerProperty, String> prop = new HashMap<>();
		prop.put(virtualServerProperty, setValue);
		editVirtualServer(prop);
	}

	public int createServerGroup(String serverGroupNamee) {
		return createServerGroup(serverGroupNamee, ServerGroupType.NORMAL);
	}

	public void addClientToServerGroup(int groupID, DataBaseClientInfo dataBaseClient) {
		addClientToServerGroup(groupID, dataBaseClient.getClientDataBaseID());
	}

	public void addClientToServerGroup(int groupID, ClientInfo client) {
		addClientToServerGroup(groupID, client.getClientDataBaseID());
	}

	public void addClientToServerGroup(ServerGroupInfo serverGroup, int clientDBID) {
		addClientToServerGroup(serverGroup.getID(), clientDBID);
	}

	public void addClientToServerGroup(ServerGroupInfo serverGroup, DataBaseClientInfo dataBaseClient) {
		addClientToServerGroup(serverGroup.getID(), dataBaseClient.getClientDataBaseID());
	}

	public void addClientToServerGroup(ServerGroupInfo serverGroup, ClientInfo client) {
		addClientToServerGroup(serverGroup.getID(), client.getClientDataBaseID());
	}
	
	public void addClientsToServerGroup(int serverGroupID, List<ClientInfo> clients) {
		StringBuilder commandBuilder = new StringBuilder("servergroupaddclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (ClientInfo addClients : clients) {
			commandBuilder.append(" cldbid=" + addClients.getClientDataBaseID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void addClientsToServerGroup(ServerGroupInfo serverGroup, List<ClientInfo> clients) {
		addClientsToServerGroup(serverGroup.getID(), clients);
	}
	
	public void addDBClientsToServerGroup(int serverGroupID, List<DataBaseClientInfo> dataBaseClients) {
		StringBuilder commandBuilder = new StringBuilder("servergroupaddclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (DataBaseClientInfo addClients : dataBaseClients) {
			commandBuilder.append(" cldbid=" + addClients.getClientDataBaseID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void addDBClientsToServerGroup(ServerGroupInfo serverGroup, List<DataBaseClientInfo> dataBaseClients) {
		addDBClientsToServerGroup(serverGroup.getID(), dataBaseClients);
	}
	
	public void addClientDBIDsToServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder commandBuilder = new StringBuilder("servergroupaddclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (int clDBIDs : dataBaseClientIDs) {
			commandBuilder.append(" cldbid=" + clDBIDs);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void addClientDBIDsToServerGroup(ServerGroupInfo serverGroup, List<Integer> dataBaseClientIDs) {
		addClientDBIDsToServerGroup(serverGroup.getID(), dataBaseClientIDs);
	}
	
	public void addServerGroupPermission(int serverGroupID, int permissionID, int permissionValue) {
		addServerGroupPermission(serverGroupID, permissionID, null, permissionValue, false, false);
	}

	public void addServerGroupPermission(int serverGroupID, Permission permission) {
		addServerGroupPermission(serverGroupID, permission.getPermID(), permission.getPermValue());
	}

	public void addServerGroupPermission(ServerGroupInfo group, int permissionID, String permissionName, int permissionValue) {
		addServerGroupPermission(group.getID(), permissionID, permissionName, permissionValue, false, false);
	}

	public void addServerGroupPermission(ServerGroupInfo group, int permissionID, int permissionValue) {
		addServerGroupPermission(group.getID(), permissionID, permissionValue);
	}

	public void addServerGroupPermission(ServerGroupInfo group, Permission permission) {
		addServerGroupPermission(group.getID(), permission.getPermID(), permission.getPermValue());
	}
	
	public void addServerGroupPermissions(int serverGroupID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("servergroupaddperm");
		commandBuilder.append(" sgid=" + serverGroupID);

		for (Permission addPerm : permissions) {
			if (addPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + addPerm.getPermID());
			if (addPerm.getPermValue() != -1)
				commandBuilder.append(" permvalue=" + addPerm.getPermValue());
			commandBuilder.append(" permnegated=" + Formatter.toInt(addPerm.isNegated()));
			commandBuilder.append(" permskip=" + Formatter.toInt(addPerm.isSkip()));
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}

	public void addServerGroupPermissions(ServerGroupInfo serverGroup, List<Permission> permissions) {
		addServerGroupPermissions(serverGroup.getID(), permissions);
	}
	
	public void addServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, int permissionValue) {
		addServerGroupAutoPermission(groupLevel, permissionID, null, permissionValue, false, false);
	}

	public void addServerGroupAutoPermission(ServerGroupLevel groupLevel, Permission permission) {
		addServerGroupAutoPermission(groupLevel, permission.getPermID(), permission.getPermValue());
	}


	public void removeServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID) {
		removeServerGroupAutoPermission(groupLevel, permissionID, null);
	}

	public void removeServerGroupAutoPermission(ServerGroupLevel groupLevel, String permissionName) {
		removeServerGroupAutoPermission(groupLevel, -1, permissionName);
	}

	public int copyServerGroup(int sourceServerGroupID, int targetServerGroupID, String serverGroupName) {
		return copyServerGroup(sourceServerGroupID, targetServerGroupID, serverGroupName, ServerGroupType.NORMAL);
	}

	public int copyServerGroup(ServerGroupInfo serverGroup, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
		return copyServerGroup(serverGroup.getID(), targetServerGroupID, serverGroupName, serverGroupType);
	}

	public int copyServerGroup(ServerGroupInfo serverGroup, int targetServerGroupID, String serverGroupName) {
		return copyServerGroup(serverGroup.getID(), targetServerGroupID, serverGroupName, ServerGroupType.NORMAL);
	}

	public void deleteServerGroup(int serverGroupID) {
		deleteServerGroup(serverGroupID, true);
	}

	public void deleteServerGroup(ServerGroupInfo serverGroup, boolean force) {
		deleteServerGroup(serverGroup.getID(), force);
	}

	public void deleteServerGroup(ServerGroupInfo serverGroup) {
		deleteServerGroup(serverGroup.getID(), true);
	}

	public void removeClientFromServerGroup(int serverGroupID, DataBaseClientInfo dataBaseClient) {
		removeClientFromServerGroup(serverGroupID, dataBaseClient.getClientDataBaseID());
	}

	public void removeClientFromServerGroup(int serverGroupID, ClientInfo client) {
		removeClientFromServerGroup(serverGroupID, client.getClientDataBaseID());
	}

	public void removeClientFromServerGroup(ServerGroupInfo serverGroup, int clientDBID) {
		removeClientFromServerGroup(serverGroup.getID(), clientDBID);
	}

	public void removeClientFromServerGroup(ServerGroupInfo serverGroup, DataBaseClientInfo dataBaseClient) {
		removeClientFromServerGroup(serverGroup.getID(), dataBaseClient.getClientDataBaseID());
	}

	public void removeClientFromServerGroup(ServerGroupInfo serverGroup, ClientInfo client) {
		removeClientFromServerGroup(serverGroup.getID(), client.getClientDataBaseID());
	}
	
	public void removeClientsFromServerGroup(int serverGroupID, List<ClientInfo> clients) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (ClientInfo addClients : clients) {
			commandBuilder.append(" cldbid=" + addClients.getClientDataBaseID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeClientsFromServerGroup(ServerGroupInfo serverGroup, List<ClientInfo> clients) {
		addClientsToServerGroup(serverGroup.getID(), clients);
	}
	
	public void removeDBClientsFromServerGroup(int serverGroupID, List<DataBaseClientInfo> dataBaseClients) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (DataBaseClientInfo addClients : dataBaseClients) {
			commandBuilder.append(" cldbid=" + addClients.getClientDataBaseID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeDBClientsFromServerGroup(ServerGroupInfo serverGroup, List<DataBaseClientInfo> dataBaseClients) {
		removeDBClientsFromServerGroup(serverGroup.getID(), dataBaseClients);
	}
	
	public void removeClientDBIDsFromServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelclient");
		commandBuilder.append(" sgid=" + serverGroupID);
		for (int clDBIDs : dataBaseClientIDs) {
			commandBuilder.append(" cldbid=" + clDBIDs);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeClientDBIDsFromServerGroup(ServerGroupInfo serverGroup, List<Integer> dataBaseClientIDs) {
		removeClientDBIDsFromServerGroup(serverGroup.getID(), dataBaseClientIDs);
	}


	public void renameServerGroup(ServerGroupInfo serverGroup, String name) {
		renameServerGroup(serverGroup.getID(), name);
	}

	public void removeServerGroupPermission(int serverGroupID, int permissionID) {
		removeServerGroupPermission(serverGroupID, permissionID, null);
	}

	public void removeServerGroupPermission(int serverGroupID, String permissionName) {
		removeServerGroupPermission(serverGroupID, -1, permissionName);
	}

	public void removeServerGroupPermission(ServerGroupInfo serverGroup, int permissionID, String permissionName) {
		removeServerGroupPermission(serverGroup.getID(), permissionID, null);
	}

	public void removeServerGroupPermission(ServerGroupInfo serverGroup, int permissionID) {
		removeServerGroupPermission(serverGroup.getID(), permissionID, null);
	}

	public void removeServerGroupPermission(ServerGroupInfo serverGroup, String permissionName) {
		removeServerGroupPermission(serverGroup.getID(), -1, permissionName);
	}
	
	public void removeServerGroupPermissions(int serverGroupID, List<Permission> permissions) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelperm");
		commandBuilder.append(" sgid=" + serverGroupID);

		for (Permission remPerm : permissions) {
			if (remPerm.getPermID() != -1)
				commandBuilder.append(" permid=" + remPerm.getPermID());
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeServerGroupPermissions(ServerGroupInfo serverGroup, List<Permission> permissions) {
		removeServerGroupPermissions(serverGroup.getID(), permissions);
	}
	
	public void removeServerGroupPermissionIDs(int serverGroupID, List<Integer> permissionIDs) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelperm");
		commandBuilder.append(" sgid=" + serverGroupID);

		for (int remPermID : permissionIDs) {
			if (remPermID != -1)
				commandBuilder.append(" permid=" + remPermID);
			commandBuilder.append("|");
		}
		query.getWriter().executeReadErrorCommand(commandBuilder.toString().substring(0, commandBuilder.toString().length() - 1));
	}
	
	public void removeServerGroupPermissionIDs(ServerGroupInfo serverGroup, List<Integer> permissionIDs) {
		removeServerGroupPermissionIDs(serverGroup.getID(), permissionIDs);
	}

	public void addVirtualServerTempPassword(String password, String description, long duration, int targetChannelID) {
		addVirtualServerTempPassword(password, description, duration, targetChannelID, "");
	}

	public void addVirtualServerTempPassword(String password, String description, long duration) {
		addVirtualServerTempPassword(password, description, duration, 0, "");
	}

	public void deleteVirtualServerTempPassword(TempPasswordInfo tempPassword) {
		deleteVirtualServerTempPassword(tempPassword.getPassword());
	}

	
	public void setClientChannelGroup(int channelGroupID, ChannelInfo channel, int clientDBID) {
		setClientChannelGroup(channelGroupID, channel.getChannelID(), clientDBID);
	}

	public void setClientChannelGroup(int channelGroupID, ChannelInfo channel, DataBaseClientInfo dataBaseClient) {
		setClientChannelGroup(channelGroupID, channel.getChannelID(), dataBaseClient.getClientDataBaseID());
	}

	public void setClientChannelGroup(int channelGroupID, ChannelInfo channel, ClientInfo client) {
		setClientChannelGroup(channelGroupID, channel.getChannelID(), client.getClientDataBaseID());
	}

	public void setClientChannelGroup(ChannelGroupInfo channelGroup, int channelID, int clientDBID) {
		setClientChannelGroup(channelGroup.getID(), channelID, clientDBID);
	}

	public void setClientChannelGroup(ChannelGroupInfo channelGroup, ChannelInfo channel, int clientDBID) {
		setClientChannelGroup(channelGroup.getID(), channel.getChannelID(), clientDBID);
	}

	public void setClientChannelGroup(ChannelGroupInfo channelGroup, ChannelInfo channel, DataBaseClientInfo dataBaseClient) {
		setClientChannelGroup(channelGroup.getID(), channel.getChannelID(), dataBaseClient.getClientDataBaseID());
	}

	public void setClientChannelGroup(ChannelGroupInfo channelGroup, ChannelInfo channel, ClientInfo client) {
		setClientChannelGroup(channelGroup.getID(), channel.getChannelID(), client.getClientDataBaseID());
	}

	public void createToken(PrivilegeKeyType keyType, int groupID, int channelID) {
		createPrivilegeKey(keyType, groupID, channelID);
	}

	public void createToken(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		createPrivilegeKey(keyType, groupID, channelID, description);
	}

	public void deleteToken(String token) {
		deletePrivilegeKey(token);
	}

	public void deleteToken(PrivilegeKeyInfo token) {
		deletePrivilegeKey(token.getKey());
	}

	public void useToken(String token) {
		usePrivilegeKey(token);
	}

	public void useToken(PrivilegeKeyInfo token) {
		usePrivilegeKey(token);
	}

	

}
