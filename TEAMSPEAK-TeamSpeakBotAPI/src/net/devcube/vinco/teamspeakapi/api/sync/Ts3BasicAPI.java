/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 24.07.2023
 * 
 * Uhrzeit : 20:55:08
 */
package net.devcube.vinco.teamspeakapi.api.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManager;
import net.devcube.vinco.teamspeakapi.api.api.property.APIScope;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.ClientProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.EventType;
import net.devcube.vinco.teamspeakapi.api.api.property.InstanceProperty;
import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupLevel;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerProperty;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.util.TSError;
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
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

/**
 * Reduced Version of the Ts3SyncAPI. Just every Method with its basic Data
 * Types. Not much Methodoverriding, just for a better overview.
 * 
 */

public class Ts3BasicAPI {

	protected Ts3ServerQuery query;
	protected QueryConfig config;
	protected CacheManager cache;
	private boolean connected = false;
	protected static String TS_INFO_SEPERATOR = "\\|";

	/**
	 * Initiation of the Sync API
	 * 
	 * @param query
	 *                  Serverquery class
	 */
	public Ts3BasicAPI(Ts3ServerQuery query) {
		this.query = query;
		this.config = query.getConfig();
		this.cache = query.getCache();
	}

	

	public String getHelp() {
		return query.getWriter().executeReadCommand("help")[0];
	}

	public String getHelp(String command) {
		return query.getWriter().executeReadCommand("help " + command)[0];
	}

	/**
	 * Connects the query to the virtual server and changes the Query bot's name.
	 * 
	 * @param serverID
	 *                     ID of the virtual server
	 * @param QueryBot
	 *                     nickname
	 */
	public void connectTeamSpeakQuery(int serverID, String nickName) {
		if (!isConnected()) {
			selectVirtualServer(serverID, -1, nickName); // select the virtualServer, the query client should connect to
			query.debug(DebugOutputType.QUERY, "Query connected sucessfully ");
			setConnected(true);
		} else {
			query.debug(DebugOutputType.QUERY, "Query is already conncted");
		}
	}

	/**
	 * Connects the query to the virtual server. Starts caching for the first time.
	 * 
	 * @param serverID
	 *                     ID of the virtual Server
	 * @param nickName
	 *                     is optional (if null, not used)
	 */

	public void selectVirtualServer(int serverID, int serverPort, String nickName) {
		StringBuilder commandBuilder = new StringBuilder("use sid=" + serverID);
		if (serverPort != -1)
			commandBuilder.append(" port=" + serverPort);
		if (nickName != null)
			commandBuilder.append(" client_nickname=" + Formatter.toTsFormat(nickName));

		String res = query.getWriter().executeReadErrorCommand(commandBuilder.toString());
		if (checkError(res, commandBuilder.toString()))
			return;

		/**
		 * Starts chache updater for the first time. After selecting the virtual server.
		 */

		if (!query.getConfig().getCachingList().isEmpty()) {
			this.cache = query.getCache();
			query.getCache().prepareCache();
		}
	}

	public void pokeClient(int clientID, String message) {
		query.getWriter().executeReadErrorCommand("clientpoke clid=" + clientID + " msg=" + Formatter.toTsFormat(message));
	}

	public void moveClient(int clientID, int channelID) {
		List<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);
		moveClientIDs(clientIDs, channelID);
	}

	public void moveClientIDs(List<Integer> clientIDs, int channelID) {
		StringBuilder cmd = new StringBuilder("clientmove ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		cmd.append(" cid=" + channelID);
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	/**
	 * Sends a text message to all clients on all virtual servers in the Server
	 * instance.
	 * 
	 * @param message
	 */

	public void sendGlobalMessage(String message) {
		query.getWriter().executeReadErrorCommand("gm msg=" + Formatter.toTsFormat(message));
	}

	public void logout() {
		query.getWriter().executeReadErrorCommand("logout");
	}

	public void quit() {
		query.getWriter().executeReadErrorCommand("quit");
	}

	/**
	 * Returns information about the Query Client
	 * 
	 * @return QueryClientInfo
	 */

	public QueryClientInfo getQueryInfo() {
		String info;
		if (config.isQueryCached()) {
			info = cache.getQueryProperties();
		} else {
			info = query.getWriter().executeReadCommand("whoami")[0];
		}
		return new QueryClientInfo(info.split(" "));
	}

	public int getVirtualServerIDByPort(int port) {
		return Integer.parseInt(query.getWriter().executeReadCommand("serveridgetbyport virtualserver_port=" + port)[0]);
	}

	public int getPermissionID(String permissionName) {
		List<String> names = new ArrayList<>();
		names.add(permissionName);
		List<Integer> result = getPermissionIDsByNames(names);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Integer> getPermissionIDsByNames(List<String> permissionNames) {
		List<Integer> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("permidgetbyname ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		for (String info : splitResult(result)) {
			resultList.add(Integer.parseInt(get(info, "permid=")));
		}
		return resultList;
	}

	public String getPermissionName(int permissionID) {
		return get(query.getWriter().executeReadCommand("permget permid=" + permissionID)[0], "permsid=");
	}

	public List<Permission> getPermissionList() {
		List<Permission> resultList = new ArrayList<Permission>();
		String permissions;
		if (config.isPermissionCached()) {
			permissions = cache.getPermissionsList();
		} else {
			permissions = query.getWriter().executeReadCommand("permissionlist")[0];
		}

		for (String permission : permissions.split(TS_INFO_SEPERATOR)) {
			int permID = Integer.parseInt(get(permission, "permid="));
			String permName = get(permission, "permname=");
			String permDes = "";
			if (permission.contains("permdesc=")) {
				permDes = Formatter.toNormalFormat(get(permission, "permdesc="));
			}
			resultList.add(new Permission(permName, permID, permDes));
		}

		return resultList;
	}

	public List<Integer> getPermissionIDs(List<Permission> permissions) {
		List<Integer> resultList = new ArrayList<>();
		permissions.forEach(perms -> resultList.add(perms.getID()));
		return resultList;
	}

	private List<Permission> getPermissionListByCommand(String command) {
		List<Permission> resultList = new ArrayList<Permission>();
		String[] result = query.getWriter().executeReadCommand(command);
		if (checkError(result[1], command))
			return resultList;
		
		for (String permission : splitResult(result)) {
			int permID = Integer.parseInt(get(permission, "permid="));
			int permValue = Integer.parseInt(get(permission, "permvalue="));
			boolean negated = Integer.parseInt(get(permission, "permnegated=")) == 1;
			boolean permskip = Integer.parseInt(get(permission, "permskip=")) == 1;
			resultList.add(new Permission(permID, permValue, negated, permskip));
		}
		return resultList;
	}

	public List<Permission> getChannelPermissions(int channelID) {
		return getPermissionListByCommand("channelpermlist cid=" + channelID);
	}

	public List<Permission> getClientPermissions(int clientDataBaseID) {
		return getPermissionListByCommand("clientpermlist cldbid=" + clientDataBaseID);
	}

	public List<Permission> getServerGroupPermissions(int serverGroupID) {
		return getPermissionListByCommand("servergrouppermlist sgid=" + serverGroupID);
	}

	public List<Permission> getChannelGroupPermissions(int channelGroupID) {
		return getPermissionListByCommand("channelgrouppermlist cgid=" + channelGroupID);
	}

	public List<Permission> getChannelClientPermissions(int channelID, int clientDataBaseID) {
		return getPermissionListByCommand("channelclientpermlist cid=" + channelID + " cldbid=" + clientDataBaseID);
	}

	public List<ServerGroupInfo> getServerGroups() {
		List<ServerGroupInfo> resultList = new ArrayList<ServerGroupInfo>();

		String serverGroups;
		if (config.isGroupsCached()) {
			serverGroups = cache.getServerGroupsList();
		} else {
			String[] result = query.getWriter().executeReadCommand("servergrouplist");
			if (checkError(result[1], "servergrouplist"))
				return resultList;
			serverGroups = result[0];
		}

		for (String groups : serverGroups.split(TS_INFO_SEPERATOR)) {
			resultList.add(new ServerGroupInfo(groups.split(" ")));
		}

		return resultList;
	}

	public List<Integer> getServerGroupIDsByClient(int clientDBID) {
		List<Integer> resultList = new ArrayList<Integer>();
		String[] result = query.getWriter().executeReadCommand("servergroupsbyclientid cldbid=" + clientDBID);
		if (checkError(result[1], "servergroupsbyclientid cldbid=" + clientDBID))
			return resultList;
		
		for (String groups : splitResult(result)) {
			int groupID = Integer.parseInt(get(groups, "sgid="));
			resultList.add(groupID);
		}
		return resultList;
	}

	public List<String> getServerGroupNamesByClient(int clientDBID) {
		List<String> resultList = new ArrayList<String>();
		String[] result = query.getWriter().executeReadCommand("servergroupsbyclientid cldbid=" + clientDBID);
		if (checkError(result[1], "servergroupsbyclientid cldbid=" + clientDBID))
			return resultList;

		for (String groups : splitResult(result)) {
			String groupname = get(groups, "name=");
			resultList.add(Formatter.toNormalFormat(groupname));
		}
		return resultList;
	}

	public List<ChannelGroupInfo> getChannelGroups() {
		List<ChannelGroupInfo> resultList = new ArrayList<ChannelGroupInfo>();
		String channelGroups;
		if (config.isGroupsCached()) {
			channelGroups = cache.getChannelGroupsList();
		} else {
			channelGroups = query.getWriter().executeReadCommand("channelgrouplist")[0];
		}

		for (String client : channelGroups.split(TS_INFO_SEPERATOR)) {
			resultList.add(new ChannelGroupInfo(client.split(" ")));
		}
		return resultList;
	}

	public VirtualServerInfo getServerInfo() {
		String information;
		if (config.isVirtualServerCached()) {
			information = cache.getVirtualServerProperties();
		} else {
			information = query.getWriter().executeReadCommand("serverinfo")[0];
		}

		return new VirtualServerInfo(information.split(" "));
	}

	public ConnectionInfo getConnectionInfo() {
		return new ConnectionInfo(query.getWriter().executeReadCommand("serverrequestconnectioninfo")[0].split(" "));
	}

	public OfflineMessageInfo getOfflineMessage(int id) {
		String[] result = query.getWriter().executeReadCommand("messageget msgid=" + id);
		String information = result[0];
		if (checkError(result[1], "messageget msgid=" + id))
			return null;
		return new OfflineMessageInfo(information.split(" "));
	}

	/**
	 * Uses only the given information of clientdblist. Less detailed than
	 * databaseclientinfo for each client, because some Information are not provided
	 * by clientdblist.
	 * 
	 * @see Ts3SyncAPI#getClientDBListDetailed() gets more information about each
	 *      client.
	 * @param startOffset
	 *                        To skip the first 'n' entries. Ignored if caching is
	 *                        enabled.
	 * @param limit
	 *                        Return only the first 'n' entries. Ignored if caching
	 *                        is enabled.
	 * 
	 * @return List with Information about each Client in the Database.
	 */

	public List<DataBaseClientInfo> getDataBaseClients(int startOffset, int limit) {
		List<DataBaseClientInfo> resultList = new ArrayList<DataBaseClientInfo>();
		String clients;
		if (config.isDataBaseCached()) {
			clients = cache.getDBClientsList();
		} else {
			StringBuilder cmd = new StringBuilder("clientdblist");
			if (startOffset != -1)
				cmd.append(" start=" + startOffset);
			if (limit != -1)
				cmd.append(" duration=" + limit);
			clients = query.getWriter().executeReadCommand(cmd.toString())[0];
		}

		for (String client : clients.split(TS_INFO_SEPERATOR)) {
			resultList.add(new DataBaseClientInfo(client.replace("cldbid", "client_database_id").split(" ")));
		}
		return resultList;
	}

	public List<Integer> getDataBaseClientIDs() {
		return getDataBaseClientIDs(getDataBaseClients(-1, -1));
	}

	public List<Integer> getDataBaseClientIDs(List<DataBaseClientInfo> clients) {
		List<Integer> resultList = new ArrayList<Integer>();
		clients.forEach(client -> resultList.add(client.getClientDataBaseID()));
		return resultList;
	}

	public List<Integer> getDataBaseClientIDsByClients(List<ClientInfo> clients) {
		List<Integer> resultList = new ArrayList<Integer>();
		clients.forEach(client -> resultList.add(client.getClientDataBaseID()));
		return resultList;
	}

	public List<OfflineMessageInfo> getOfflineMessages() {
		List<OfflineMessageInfo> resultList = new ArrayList<OfflineMessageInfo>();
		String[] result = query.getWriter().executeReadCommand("messagelist");
		if (checkError(result[1], "messagelist"))
			return resultList;
		for (String client : splitResult(result)) {
			resultList.add(new OfflineMessageInfo(client.split(" ")));
		}
		return resultList;
	}

	public HostInfo getHostInfo() {
		String information = query.getWriter().executeReadCommand("hostinfo")[0];
		if (information.isEmpty())
			return null;

		return new HostInfo(information.split(" "));
	}

	public int getClientIDByUUID(String clientUUID) {
		List<String> uuids = new ArrayList<>();
		uuids.add(clientUUID);
		List<Integer> result = getClientIDsByUUIDs(uuids);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Integer> getClientIDsByUUIDs(List<String> clientUUIDs) {
		List<Integer> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientgetids ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		
		for (String info : splitResult(result)) {
			resultList.add(Integer.parseInt(get(info, "clid=")));
		}
		return resultList;
	}

	public String getClientUUIDByID(int clientID) {
		List<Integer> uuids = new ArrayList<>();
		uuids.add(clientID);
		List<String> result = getClientNamesUUIDsByIDs(uuids, false);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<String> getClientUUIDsByIDs(List<Integer> clientIDs) {
		return getClientNamesUUIDsByIDs(clientIDs, false);
	}

	public String getClientNameByID(int clientID) {
		List<Integer> uuids = new ArrayList<>();
		uuids.add(clientID);
		List<String> result = getClientNamesUUIDsByIDs(uuids, true);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<String> getClientNamesByIDs(List<Integer> clientIDs) {
		return getClientNamesUUIDsByIDs(clientIDs, true);
	}

	protected List<String> getClientNamesUUIDsByIDs(List<Integer> clientIDs, boolean names) {
		List<String> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientgetuidfromclid ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;

		for (String info : splitResult(result)) {
			if (names) {
				resultList.add(Formatter.toNormalFormat(get(info, "nickname=")));
			} else {
				resultList.add(get(info, "cluid="));
			}
		}
		return resultList;
	}

	public String getClientNameByUUID(String clientUUID) {
		List<String> uuids = new ArrayList<>();
		uuids.add(clientUUID);
		List<String> result = getClientNamesByUUIDs(uuids);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<String> getClientNamesByUUIDs(List<String> clientUUIDs) {
		List<String> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientgetnamefromuid ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		for (String info : splitResult(result)) {
			resultList.add(Formatter.toNormalFormat(get(info, "name=")));
		}
		return resultList;
	}

	public String getClientNameByDBID(int clientDataBaseID) {
		List<Integer> dbIDs = new ArrayList<>();
		dbIDs.add(clientDataBaseID);
		List<String> result = getClientNamesUUIDs(dbIDs, true);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<String> getClientNamesByDBIDs(List<Integer> clientDataBaseIDs) {
		return getClientNamesUUIDs(clientDataBaseIDs, true);
	}

	public String getClientUUIDByDBID(int clientDataBaseID) {
		List<Integer> dbIDs = new ArrayList<>();
		dbIDs.add(clientDataBaseID);
		List<String> result = getClientNamesUUIDs(dbIDs, false);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<String> getClientUUIDsByDBIDs(List<Integer> clientDataBaseIDs) {
		return getClientNamesUUIDs(clientDataBaseIDs, false);
	}

	protected List<String> getClientNamesUUIDs(List<Integer> clientDataBaseIDs, boolean names) {
		List<String> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientgetnamefromdbid ");
		cmd.append(buildObjectArray(clientDataBaseIDs, "cldbid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		for (String info : splitResult(result)) {
			if (names) {
				resultList.add(Formatter.toNormalFormat(get(info, "name=")));
			} else {
				resultList.add(get(info, "cluid="));
			}
		}
		return resultList;
	}

	public int getClientDataBaseID(String clientUUID) {
		List<String> uuids = new ArrayList<>();
		uuids.add(clientUUID);
		List<Integer> result = getClientDataBaseIDsByUUIDs(uuids);
		return result.isEmpty() ? -1 : result.get(0);
	}

	public List<Integer> getClientDataBaseIDsByUUIDs(List<String> clientUUIDs) {
		List<Integer> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientgetdbidfromuid ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid="));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		for (String info : splitResult(result)) {
			resultList.add(Integer.parseInt(get(info, "cldbid=")));
		}
		return resultList;
	}

	public ClientInfo getClient(int clientID) {
		if (config.isClientsCached()) {
			return new ClientInfo(cache.getClientInfo(clientID).concat(" clid=" + clientID).split(" "));
		} else {
			List<Integer> clientIDs = new ArrayList<>();
			clientIDs.add(clientID);
			List<ClientInfo> result = getClientsByIDs(clientIDs);
			return result.isEmpty() ? null : result.get(0);
		}
	}

	public List<ClientInfo> getClientsByIDs(List<Integer> clientIDs) {
		List<ClientInfo> resultList = new ArrayList<>();
		StringBuilder commandBuilder = new StringBuilder("clientinfo ");
		commandBuilder.append(buildObjectArray(clientIDs, "clid"));
		String cmd = commandBuilder.toString();
		String[] result = query.getWriter().executeReadCommand(cmd);
		if (checkError(result[1], cmd))
			return resultList;

		int counter = 0;
		for (String clientInfo : splitResult(result)) {
			resultList.add(new ClientInfo(clientInfo.concat(" clid=" + clientIDs.get(counter)).split(" ")));
			counter++;
		}

		return resultList;
	}

	/**
	 * Uses only the given information of clientlist. Less detailed than clientinfo
	 * for each client, because some Information are not provided by clientlist.
	 * 
	 * @see Ts3SyncAPI#getOnlineClientsDetailed() gets more information about each
	 *      client.
	 * 
	 * @return List with Information about each Client connected to the Server.
	 */

	public List<ClientInfo> getClients() {
		List<ClientInfo> resultList = new ArrayList<ClientInfo>();
		String[] clients;
		if (config.isClientsCached()) {
			clients = cache.getClientsList().split(TS_INFO_SEPERATOR);
		} else {
			StringBuilder commandBuilder = new StringBuilder("clientlist");
			commandBuilder.append(" -uid");
			commandBuilder.append(" -away");
			commandBuilder.append(" -voice");
			commandBuilder.append(" -times");
			commandBuilder.append(" -groups");
			commandBuilder.append(" -info");
			commandBuilder.append(" -country");
			commandBuilder.append(" -ip");
			commandBuilder.append(" -badges");
			clients = query.getWriter().executeReadCommand(commandBuilder.toString())[0].split(TS_INFO_SEPERATOR);
		}

		for (String client : clients) {
			resultList.add(new ClientInfo(client.concat(" clid=" + get(client, "clid=")).split(" ")));
		}
		return resultList;
	}

	public List<Integer> getClientIDs() {
		return getClientIDs(getClients());
	}

	public List<Integer> getClientIDs(List<ClientInfo> clients) {
		List<Integer> resultList = new ArrayList<Integer>();
		clients.forEach(client -> resultList.add(client.getID()));
		return resultList;
	}

	public DataBaseClientInfo getDataBaseClient(int clientDataBaseID) {
		if (config.isDataBaseCached()) {
			return new DataBaseClientInfo(cache.getDBClientInfo(clientDataBaseID).split(" "));
		} else {
			List<Integer> clientDBIDs = new ArrayList<>();
			clientDBIDs.add(clientDataBaseID);
			List<DataBaseClientInfo> result = getDataBaseClientsByDBIDs(clientDBIDs);
			return result.isEmpty() ? null : result.get(0);
		}
	}

	public List<DataBaseClientInfo> getDataBaseClientsByDBIDs(List<Integer> clientDBIDs) {
		List<DataBaseClientInfo> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("clientdbinfo ");
		cmd.append(buildObjectArray(clientDBIDs, "cldbid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		for (String clientInfo : splitResult(result)) {
			resultList.add(new DataBaseClientInfo(clientInfo.split(" ")));
		}
		return resultList;
	}

	public ChannelInfo getChannel(int channelID) {
		String info;
		if (config.isChannelsCached()) {
			info = cache.getChannelInfo(channelID);
		} else {
			info = query.getWriter().executeReadCommand("channelinfo cid=" + channelID)[0];
		}
		if (info.isEmpty())
			return null;
		return new ChannelInfo(info.concat(" cid=" + channelID).split(" "));
	}

	public List<ChannelInfo> getChannelsByName(String channelName) {
		List<ChannelInfo> resultList = new ArrayList<ChannelInfo>();
		String[] channels = query.getWriter().executeReadCommand("channelfind pattern=" + Formatter.toTsFormat(channelName))[0].split(TS_INFO_SEPERATOR);
		for (String channel : channels) {
			resultList.add(new ChannelInfo(channel.split(" ")));
		}
		return resultList;
	}

	protected List<ChannelInfo> getChannelsByCommand(String command) {
		List<ChannelInfo> resultList = new ArrayList<ChannelInfo>();
		String[] result = query.getWriter().executeReadCommand(command);
		if (checkError(result[1], command))
			return resultList;
		for (String channel : splitResult(result)) {
			resultList.add(new ChannelInfo(channel.split(" ")));
		}
		return resultList;
	}

	/**
	 * Gets information about all Channels. Less detailed, because some Information
	 * are not provided by channellist. For more detailed Information use
	 * {@link Ts3SyncAPI#getChannelsDetailed()}
	 * 
	 * @see Ts3SyncAPI#getChannelsDetailed()
	 * 
	 * @return an Empty List if QueryClient has no Permissions.
	 */

	public List<ChannelInfo> getChannels() {
		if (config.isChannelsCached()) {
			List<ChannelInfo> resultList = new ArrayList<ChannelInfo>();
			for (String channel : cache.getChannelsList().split(TS_INFO_SEPERATOR)) {
				resultList.add(new ChannelInfo(channel.split(" ")));
			}
			return resultList;
		}

		StringBuilder commandBuilder = new StringBuilder("channellist");
		commandBuilder.append(" -topic");
		commandBuilder.append(" -flags");
		commandBuilder.append(" -voice");
		commandBuilder.append(" -limits");
		commandBuilder.append(" -icon");
		commandBuilder.append(" -secondsempty");
		commandBuilder.append(" -banners");

		return getChannelsByCommand(commandBuilder.toString());
	}

	public List<Integer> getDatabaseIDsByServerGroup(int servergroupID) {
		List<Integer> resultList = new ArrayList<Integer>();
		String[] users = query.getWriter().executeReadCommand("servergroupclientlist sgid=" + servergroupID)[0].split(TS_INFO_SEPERATOR);
		for (String user : users) {
			if (user.isEmpty())
				continue;
			resultList.add(Integer.parseInt(get(user, "cldbid=")));
		}
		return resultList;
	}

	/**
	 * Provides a Map which contains every Clientdatabaseid as a ley and the value
	 * is a list of channelids in which the Clientdatabaseid has the channelgroup.
	 * 
	 * @param channelgroupID
	 * @return [DataBaseID, List[ChannelID]]
	 */

	public Map<Integer, List<Integer>> getDatabaseIDsByChannelGroup(int channelgroupID) {
		Map<Integer, List<Integer>> resultMap = new HashMap<>();
		String cmd = "channelgroupclientlist cgid=" + channelgroupID;
		String[] result = query.getWriter().executeReadCommand(cmd);
		if (checkError(result[1], cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int clientDBID = Integer.parseInt(get(user, "cldbid="));
			int channelID = Integer.parseInt(get(user, "cid="));
			List<Integer> list = new ArrayList<>();
			if (resultMap.containsKey(clientDBID)) {
				list = resultMap.get(clientDBID);
			}
			list.add(channelID);
			resultMap.put(clientDBID, list);
		}
		return resultMap;
	}

	/**
	 * Provides a List of DatabaseClient(ID)s which have a specific ChannelGroup in
	 * a specific Channel.
	 * 
	 * @param channelgroupID
	 * @param channelID
	 * @return List[ClientDataBaseID]
	 */

	public List<Integer> getDatabaseIDsByChannelAndGroup(int channelgroupID, int channelID) {
		List<Integer> resultList = new ArrayList<Integer>();
		String cmd = "channelgroupclientlist cid=" + channelID + " cgid=" + channelgroupID;
		String[] result = query.getWriter().executeReadCommand(cmd);
		if (checkError(result[1], cmd))
			return resultList;

		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			resultList.add(Integer.parseInt(get(user, "cldbid=")));
		}
		return resultList;
	}

	/**
	 * Provides a Map which contains every ChannelGroupID as a Key and the Value is
	 * a List of ChannelIDs in which the Client has the ChannelGroupID. Note that
	 * {@link Ts3SyncAPI#getChannelGroupInfosByDatabaseID(int)} caches the List of
	 * ChannelGroups to improve the performance. To get only the ChannelGroup(ID)s
	 * just use keySet().
	 * 
	 * @param clientDataBaseID
	 * @return [ChannelGroupID, ChannelID]
	 */

	public Map<Integer, List<Integer>> getChannelGroupsByDatabaseID(int clientDataBaseID) {
		Map<Integer, List<Integer>> resultMap = new HashMap<>();
		String cmd = "channelgroupclientlist cldbid=" + clientDataBaseID;
		String[] result = query.getWriter().executeReadCommand(cmd);
		if (checkError(result[1], cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int channelGroupID = Integer.parseInt(get(user, "cgid="));
			int channelID = Integer.parseInt(get(user, "cid="));
			List<Integer> list = new ArrayList<>();
			if (resultMap.containsKey(channelGroupID)) {
				list = resultMap.get(channelGroupID);
			}
			list.add(channelID);
			resultMap.put(channelGroupID, list);
		}
		return resultMap;
	}

	/**
	 * Provides a Map which contains every ChannelGroup(ID) as a Key and the Value
	 * is a List of DatabaseClientIDs which are in the ChannelGroup. Note that
	 * getChannelGroupInfosByChannelID caches the List of ChannelGroups to improve
	 * the performance and only returns ChannelGroups which are set to any Client.
	 * 
	 * @param channelID
	 * @return [ChannelGroupID, List[ClientDataBaseID]]
	 */

	public Map<Integer, List<Integer>> getChannelGroupsByChannelID(int channelID) {
		Map<Integer, List<Integer>> resultMap = new HashMap<>();
		String cmd = "channelgroupclientlist cid=" + channelID;
		String[] result = query.getWriter().executeReadCommand(cmd);
		if (checkError(result[1], cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int clDBID = Integer.parseInt(get(user, "cldbid="));
			int cgID = Integer.parseInt(get(user, "cgid="));
			List<Integer> list = new ArrayList<>();
			if (resultMap.containsKey(cgID)) {
				list = resultMap.get(cgID);
			}
			list.add(clDBID);
			resultMap.put(cgID, list);
		}
		return resultMap;
	}

	public List<ComplainInfo> getComplainsByClient(int clientDataBaseID) {
		List<ComplainInfo> resultList = new ArrayList<ComplainInfo>();
		String[] result = query.getWriter().executeReadCommand("complainlist tcldbid=" + clientDataBaseID);
		if (checkError(result[1], "complainlist tcldbid=" + clientDataBaseID))
			return resultList;

		for (String complain : splitResult(result)) {
			resultList.add(new ComplainInfo(complain.split(" ")));
		}
		return resultList;
	}

	public List<PrivilegeKeyInfo> getPrivilegeKeys() {
		List<PrivilegeKeyInfo> resultList = new ArrayList<PrivilegeKeyInfo>();
		String[] result = query.getWriter().executeReadCommand("privilegekeylist");
		if (checkError(result[1], "privilegekeylist"))
			return resultList;

		for (String token : splitResult(result)) {
			resultList.add(new PrivilegeKeyInfo(token.split(" ")));
		}
		return resultList;
	}

	public List<ComplainInfo> getComplains() {
		List<ComplainInfo> resultList = new ArrayList<ComplainInfo>();
		String[] result = query.getWriter().executeReadCommand("complainlist");
		if (checkError(result[1], "complainlist"))
			return resultList;

		for (String complain : splitResult(result)) {
			resultList.add(new ComplainInfo(complain.split(" ")));
		}
		return resultList;
	}

	public List<BanInfo> getBans() {
		List<BanInfo> resultList = new ArrayList<BanInfo>();
		String[] result = query.getWriter().executeReadCommand("banlist");
		if (checkError(result[1], "banlist"))
			return resultList;

		for (String ban : splitResult(result)) {
			resultList.add(new BanInfo(ban.split(" ")));
		}
		return resultList;
	}

	protected List<VirtualServerInfo> getVirtualServersByCommand(String command) {
		List<VirtualServerInfo> resultList = new ArrayList<VirtualServerInfo>();
		String[] result = query.getWriter().executeReadCommand(command);
		if (checkError(result[1], command))
			return resultList;
		for (String server : splitResult(result)) {
			resultList.add(new VirtualServerInfo(server.split(" ")));
		}
		return resultList;
	}

	public List<VirtualServerInfo> getVirtualServers() {
		return getVirtualServersByCommand("serverlist -uid");
	}

	public String getVersion() {
		return query.getWriter().executeReadCommand("version")[0];
	}

	/**
	 * Adds a bandrule to the server, every argument is optional, but you should use
	 * at least one. Be aware that you could ban more clients than indeeded.
	 * 
	 * 
	 * @param ip
	 *                       is optional (if null not used)
	 * @param name
	 *                       is optional (if null not used)
	 * @param clientUUID
	 *                       is optional (if null not used)
	 * @param myTSID
	 *                       is optional (if null not used)
	 * @param banTime
	 *                       is optional (if -2 not used)
	 * @param banReason
	 *                       is optional (if null not used)
	 * 
	 * @return ID of the ban.
	 */

	public int addBan(String ip, String name, String clientUUID, String myTSID, long banTime, String banReason) {
		StringBuilder cmd = new StringBuilder("banadd");
		if (ip != null)
			cmd.append(" ip=" + ip);
		if (name != null)
			cmd.append(" name=" + Formatter.toTsFormat(name));
		if (clientUUID != null)
			cmd.append(" uid=" + clientUUID);
		if (myTSID != null)
			cmd.append(" mytsid=" + myTSID);
		if (banTime != -2)
			cmd.append(" time=" + banTime);
		if (banReason != null)
			cmd.append(" banreason=" + Formatter.toTsFormat(banReason));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return -1;
		return Integer.parseInt(get(result[0], "="));
	}

	/**
	 * Bans a client from the server.
	 * 
	 * @param clientID
	 *                      ID of the client.
	 * @param banTime
	 *                      Time the client should be banned.
	 * @param banReason
	 *                      is optional (if null not used)
	 * 
	 * @return Array of banIDs {clientBanID, ipBanID}
	 */

	public int[] banClient(int clientID, long banTime, String banReason) {
		List<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);
		List<Integer> result = banClientIDs(clientIDs, banTime, banReason);
		return new int[] { result.get(0), result.get(1) };
	}

	public List<Integer> banClientIDs(List<Integer> clientIDs, long banTime, String banReason) {
		List<Integer> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("banclient");
		cmd.append(" time=" + banTime + " ");
		if (banReason != null)
			cmd.append(" banreason=" + Formatter.toTsFormat(banReason) + " ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		String res = result[0].replace("banid=", "").replace("\n", ",");
		for (String id : res.split(",")) {
			resultList.add(Integer.parseInt(id));
		}
		return resultList;
	}

	public void removeBan(int banID) {
		query.getWriter().executeReadErrorCommand("bandel banid=" + banID);
	}

	public void removeAllBans() {
		query.getWriter().executeReadErrorCommand("bandelall");
	}

	public void startVirtualServer(int virtualServerID) {
		query.getWriter().executeReadErrorCommand("serverstart sid=" + virtualServerID);
	}

	/**
	 * Stops a specified virtual server.
	 * 
	 * @param virtualServerID
	 * @param reasonmsg
	 *                            is optional (if null not used)
	 */

	public void stopVirtualServer(int virtualServerID, String reasonmsg) {
		StringBuilder cmd = new StringBuilder("serverstop");
		cmd.append(" sid=" + virtualServerID);
		if (reasonmsg != null)
			cmd.append(" reasonmsg=" + Formatter.toTsFormat(reasonmsg));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void stopServerProcess(String reasonmsg) {
		StringBuilder cmd = new StringBuilder("serverprocessstop");
		if (reasonmsg != null) {
			cmd.append(" reasonmsg=" + Formatter.toTsFormat(reasonmsg));
		}
		query.getWriter().executeReadCommand(cmd.toString());
	}

	public void resetPermissions() {
		query.getWriter().executeReadCommand("permreset");
	}

	/**
	 * Adds a specified channel a permission. Use the permissionid or the
	 * permissionname to identify the permission.
	 * 
	 * @param channelID
	 *                            Channel which should get the permission
	 * @param permissionID
	 *                            ID to identify the permission, could be optional
	 *                            (if -1 not used)
	 * @param permissionName
	 *                            Name to identify the permission, could be optional
	 *                            (if null not used)
	 * @param permissionValue
	 *                            Value which the permission should have
	 */

	public void addChannelPermission(int channelID, int permissionID, String permissionName, int permissionValue) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue));
		addChannelPermissions(channelID, perms);
	}

	public void addChannelPermissions(int channelID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channeladdperm");
		cmd.append(" cid=" + channelID + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	/**
	 * Adds a specified client in a specified channel a permission. Use the
	 * permissionid or the permissionname to identify the permission.
	 * 
	 * @param channelID
	 *                             Channel in which the client should get the
	 *                             permission
	 * @param clientdataBaseID
	 *                             Client which should get the permission
	 * @param permissionID
	 *                             ID to identify the permission, could be optional
	 *                             (if -1 not used)
	 * @param permissionName
	 *                             Name to identify the permission, could be
	 *                             optional (if null not used)
	 * @param permissionValue
	 *                             Value which the permission should have
	 */

	public void addChannelClientPermission(int channelID, int clientdataBaseID, int permissionID, String permissionName, int permissionValue) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue));
		addChannelClientPermissions(channelID, clientdataBaseID, perms);
	}

	public void addChannelClientPermissions(int channelID, int clientdataBaseID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channelclientaddperm");
		cmd.append(" cid=" + channelID);
		cmd.append(" cldbid=" + clientdataBaseID + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	/**
	 * Removes a specified client in a specified channel a permission. Use the
	 * permissionid or the permissionname to identify the permission.
	 * 
	 * @param channelID
	 *                             Channel in which the permission should be removed
	 *                             from the client
	 * @param clientdataBaseID
	 *                             Client which should be removed the permission
	 * @param permissionID
	 *                             ID to identify the permission, could be optional
	 *                             (if -1 not used)
	 * @param permissionName
	 *                             Name to identify the permission, could be
	 *                             optional (if null not used)
	 */

	public void removeChannelClientPermission(int channelID, int clientdataBaseID, int permissionID, String permissionName) {
		List<Integer> permissionIDs = new ArrayList<>();
		List<String> permissionNames = new ArrayList<>();
		permissionIDs.add(permissionID);
		permissionNames.add(permissionName);
		removeChannelClientPermissions(channelID, clientdataBaseID, permissionIDs, permissionNames);
	}

	public void removeChannelClientPermissions(int channelID, int clientdataBaseID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channelclientdelperm");
		cmd.append(" cid=" + channelID);
		cmd.append(" cldbid=" + clientdataBaseID + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public int createChannel(String channelName, Map<ChannelProperty, String> channelProperties) {
		StringBuilder commandBuilder = new StringBuilder("channelcreate");
		commandBuilder.append(" channel_name=" + Formatter.toTsFormat(channelName));
		channelProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});

		String[] result = query.getWriter().executeReadCommand(commandBuilder.toString());
		if (checkError(result[1], commandBuilder.toString()))
			return -1;
		return Integer.parseInt(get(result[0], "cid="));
	}

	public void deleteChannel(int channelID, boolean force) {
		query.getWriter().executeReadErrorCommand("channeldelete cid=" + channelID + " force=" + Formatter.toInt(force));
	}

	/**
	 * Removes a specified channel a permission. Use the permissionid or the
	 * permissionname to identify the permission.
	 * 
	 * @param channelID
	 *                           Channel which the permission should be removed from
	 * @param permissionID
	 *                           ID to identify the permission, could be optional
	 *                           (if -1 not used)
	 * @param permissionName
	 *                           Name to identify the permission, could be optional
	 *                           (if null not used)
	 */

	public void removeChannelPermission(int channelID, int permissionID, String permissionName) {
		List<Integer> permissionIDs = new ArrayList<>();
		List<String> permissionNames = new ArrayList<>();
		permissionIDs.add(permissionID);
		permissionNames.add(permissionName);
		removeChannelPermissions(channelID, permissionIDs, permissionNames);
	}

	public void removeChannelPermissions(int channelID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channeldelperm");
		cmd.append(" cid=" + channelID + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void editChannel(int channelID, Map<ChannelProperty, String> channelProperties) {
		StringBuilder commandBuilder = new StringBuilder("channeledit");
		commandBuilder.append(" cid=" + channelID);
		channelProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});
		query.getWriter().executeReadCommand(commandBuilder.toString());
	}

	public int createChannelGroup(String channelGroupName, ChannelGroupType channelGroupType) {
		String command = "channelgroupadd name=" + Formatter.toTsFormat(channelGroupName) + " type=" + channelGroupType.getValue();
		return Integer.parseInt(get(query.getWriter().executeReadCommand(command)[0], "cgid="));
	}

	/**
	 * Adds a specified channelgroup a permission. Use the permissionid or the
	 * permissionname to identify the permission.
	 * 
	 * @param channelGroupID
	 *                            ChannelGroup which should get the permission
	 * @param permissionID
	 *                            ID to identify the permission, could be optional
	 *                            (if -1 not used)
	 * @param permissionName
	 *                            Name to identify the permission, could be optional
	 *                            (if null not used)
	 * @param permissionValue
	 *                            Value which the permission should have
	 */

	public void addChannelGroupPermission(int channelGroupID, int permissionID, String permissionName, int permissionValue) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue));
		addChannelGroupPermissions(channelGroupID, perms);
	}

	public void addChannelGroupPermissions(int channelGroupID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channelgroupaddperm");
		cmd.append(" cgid=" + channelGroupID + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public int copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		StringBuilder commandBuilder = new StringBuilder("channelgroupcopy");
		commandBuilder.append(" scgid=" + sourceChannelGroupID);
		commandBuilder.append(" tcgid=" + targetChannelGroupID);
		commandBuilder.append(" name=" + Formatter.toTsFormat(channelGroupName));
		commandBuilder.append(" type=" + channelGroupType.getValue());
		return Integer.parseInt(get(query.getWriter().executeReadCommand(commandBuilder.toString())[0], "cgid="));
	}

	public void deleteChannelGroup(int channelGroupID, boolean force) {
		query.getWriter().executeReadErrorCommand("channelgroupdel cgid=" + channelGroupID + " force=" + Formatter.toInt(force));
	}

	/**
	 * Removes a specified channelgroup a permission. Use the permissionid or the
	 * permissionname to identify the permission.
	 * 
	 * @param channelGroupID
	 *                           ChannelGroup which the permission should be removed
	 *                           from
	 * @param permissionID
	 *                           ID to identify the permission, could be optional
	 *                           (if -1 not used)
	 * @param permissionName
	 *                           Name to identify the permission, could be optional
	 *                           (if null not used)
	 */

	public void removeChannelGroupPermission(int channelGroupID, int permissionID, String permissionName) {
		List<Integer> permissionIDs = new ArrayList<>();
		List<String> permissionNames = new ArrayList<>();
		permissionIDs.add(permissionID);
		permissionNames.add(permissionName);
		removeChannelGroupPermissions(channelGroupID, permissionIDs, permissionNames);
	}

	public void removeChannelGroupPermissions(int channelGroupID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channelgroupdelperm");
		cmd.append(" cgid=" + channelGroupID + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void renameChannelGroup(int channelGroupID, String channelName) {
		query.getWriter().executeReadErrorCommand("channelgrouprename cgid=" + channelGroupID + " name=" + Formatter.toTsFormat(channelName));
	}

	public void moveChannel(int channelID, int channelParentID, int order) {
		query.getWriter().executeReadErrorCommand("channelmove cid=" + channelID + " cpid=" + channelParentID + " order=" + order);
	}

	public void addClientPermission(int clientDataBaseID, int permissionID, String permissionName, int permissionValue, boolean permSkip) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue, permSkip, false));
		addClientPermissions(clientDataBaseID, perms);
	}

	public void addClientPermissions(int clientDataBaseID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("clientaddperm");
		cmd.append(" cldbid=" + clientDataBaseID + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void deleteDataBaseClient(int clientDataBaseID) {
		query.getWriter().executeReadErrorCommand("clientdbdelete cldbid=" + clientDataBaseID);
	}

	public void editDataBaseClient(int clientDataBaseID, String description) {
		query.getWriter().executeReadErrorCommand("clientdbedit cldbid=" + clientDataBaseID + " client_description=" + Formatter.toTsFormat(description));
	}

	public int getDataBaseClientIDByUUID(String clientUUID) {
		return Integer.parseInt(get(query.getWriter().executeReadCommand("clientdbfind -uid pattern=" + clientUUID)[0], "cldbid="));
	}

	public List<Integer> getDataBaseClientIDsByName(String clientLastName) {
		List<Integer> resultList = new ArrayList<>();
		String information[] = query.getWriter().executeReadCommand("clientdbfind pattern=" + Formatter.toTsFormat(clientLastName))[0].split(TS_INFO_SEPERATOR);
		for (String infos : information) {
			resultList.add(Integer.parseInt(get(infos, "cldbid=")));
		}
		return resultList;
	}

	public void removeClientPermission(int clientDataBaseID, int permissionID, String permissionName) {
		List<Integer> permissionIDs = new ArrayList<>();
		List<String> permissionNames = new ArrayList<>();
		permissionIDs.add(permissionID);
		permissionNames.add(permissionName);
		removeClientPermissions(clientDataBaseID, permissionIDs, permissionNames);
	}

	public void removeClientPermissions(int clientDataBaseID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("clientdelperm");
		cmd.append(" cldbid=" + clientDataBaseID + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void editClient(int clientID, Map<ClientProperty, String> clientProperties) {
		StringBuilder commandBuilder = new StringBuilder("clientedit");
		commandBuilder.append(" clid=" + clientID);
		clientProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(clientProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});
		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public List<Integer> getClientIDsByName(String clientName) {
		List<Integer> resultList = new ArrayList<>();
		String information[] = query.getWriter().executeReadCommand("clientfind pattern=" + Formatter.toTsFormat(clientName))[0].split(TS_INFO_SEPERATOR);
		for (String infos : information) {
			resultList.add(Integer.parseInt(get(infos, "clid=")));
		}
		return resultList;
	}

	public void kickClientFromServer(int clientID, String reason) {
		List<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);
		kickClientIDs(clientIDs, 5, reason);
	}

	public void kickClientIDs(List<Integer> clientIDs, int reasonID, String reason) {
		StringBuilder cmd = new StringBuilder("clientkick");
		cmd.append(" reasonid=" + reasonID + " ");
		if (reason != null)
			cmd.append(" reasonmsg=" + Formatter.toTsFormat(reason) + " ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void kickClientFromChannel(int clientID, String reason) {
		List<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);
		kickClientIDs(clientIDs, 4, reason);
	}

	public String updateServerQueryLogin(String username) {
		String information = query.getWriter().executeReadCommand("clientsetserverquerylogin client_login_name=" + Formatter.toTsFormat(username))[0];
		return get(information, "client_login_password=");
	}

	public void updateQueryName(String queryName) {
		query.getWriter().executeReadErrorCommand("clientupdate client_nickname=" + Formatter.toTsFormat(queryName));
	}

	public void addComplain(int clientDBID, String message) {
		query.getWriter().executeReadErrorCommand("complainadd tcldbid=" + clientDBID + " message=" + Formatter.toTsFormat(message));
	}

	public void deleteComplain(int clientDBID, int fromClientDBID) {
		query.getWriter().executeReadErrorCommand("complaindel tcldbid=" + clientDBID + " fcldbid=" + fromClientDBID);
	}

	public void deleteAllComplains(int clientDBID) {
		query.getWriter().executeReadErrorCommand("dataBaseClient tcldbid=" + clientDBID);
	}

	public Map<String, String> getCustomInfo(int clientDBID) {
		Map<String, String> resultMap = new HashMap<>();
		String[] information = query.getWriter().executeReadCommand("custominfo cldbid=" + clientDBID)[0].split(TS_INFO_SEPERATOR);
		for (String info : information) {
			String key = Formatter.toNormalFormat(get(info, "ident="));
			String value = Formatter.toNormalFormat(get(info, "value="));
			resultMap.put(key, value);
		}
		return resultMap;
	}

	public Map<Integer, List<String>> searchDBIDsCustomInfo(String ident, String pattern) {
		Map<Integer, List<String>> resultMap = new HashMap<>();
		String[] information = query.getWriter().executeReadCommand("customsearch ident=" + ident + "pattern=" + pattern)[0].split(TS_INFO_SEPERATOR);
		for (String info : information) {
			int clDBID = Integer.parseInt(get(info, "cldbid="));
			String value = Formatter.toNormalFormat(get(info, "value="));
			List<String> list = new ArrayList<>();
			if (resultMap.containsKey(clDBID)) {
				list = resultMap.get(clDBID);
			}
			list.add(value);
			resultMap.put(clDBID, list);

		}
		return resultMap;
	}

	public void setCustomInfo(int clientDBID, String ident, String value) {
		query.getWriter().executeReadErrorCommand("customset cldbid=" + clientDBID + " ident=" + Formatter.toTsFormat(ident) + " value=" + Formatter.toTsFormat(value));
	}

	public void deleteCustomInfo(int clientDBID, String ident) {
		query.getWriter().executeReadErrorCommand("customdelete cldbid=" + clientDBID + " ident=" + Formatter.toTsFormat(ident));
	}

	public void createFileDirectory(int channelID, String channelPassword, String dirName) {
		query.getWriter().executeReadErrorCommand("ftcreatedir cid=" + channelID + " cpw=" + channelPassword + " dirname=" + dirName);
	}

	public void deleteFile(int channelID, String channelPassword, String fileName) {
		ArrayList<String> fileNames = new ArrayList<>();
		fileNames.add(fileName);
		deleteFiles(channelID, channelPassword, fileNames);
	}

	public void deleteFiles(int channelID, String channelPassword, List<String> fileNames) {
		StringBuilder cmd = new StringBuilder("ftdeletefile cid=" + channelID + " cpw=" + channelPassword + " ");
		cmd.append(buildObjectArray(fileNames, "name"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public FileInfo getFileInfo(int channelID, String channelPassword, String fileName) {
		ArrayList<String> fileNames = new ArrayList<>();
		fileNames.add(fileName);
		List<FileInfo> result = getFileInfos(channelID, channelPassword, fileNames);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<FileInfo> getFileInfos(int channelID, String channelPassword, List<String> fileNames) {
		List<FileInfo> resultList = new ArrayList<>();
		StringBuilder commandBuilder = new StringBuilder("ftgetfileinfo ");
		fileNames.forEach(fileName -> {
			if (!fileName.contentEquals(fileNames.get(0)))
				commandBuilder.append("|");
			commandBuilder.append("cid=" + channelID);
			commandBuilder.append(" cpw=" + channelPassword);
			commandBuilder.append(" name=" + Formatter.toTsFormat(fileName));
		});
		String command = commandBuilder.toString();
		String[] result = query.getWriter().executeReadCommand(command);
		if (checkError(result[1], command))
			return resultList;
		for (String files : splitResult(result)) {
			resultList.add(new FileInfo(files.concat(" cid=" + channelID).split(" ")));
		}
		return resultList;
	}

	public List<FileInfo> getChannelFilesByPath(int channelID, String channelPassword, String filePath) {
		List<FileInfo> resultList = new ArrayList<>();
		String command = "ftgetfilelist cid=" + channelID + " cpw=" + channelPassword + " path=" + Formatter.toTsFormat(filePath);
		String[] result = query.getWriter().executeReadCommand(command);
		String[] information = splitResult(result);
		if (checkError(result[1], command))
			return resultList;

		for (String info : information) {
			String path = get(information[0], "path=");
			resultList.add(new FileInfo(info.concat(" cid=" + channelID).concat(" path=" + path).split(" ")));
		}
		return resultList;
	}

	public List<FileTransferInfo> getFileTransfers() {
		List<FileTransferInfo> resultList = new ArrayList<>();
		String[] result = query.getWriter().executeReadCommand("ftlist");
		String[] information = splitResult(result);
		if (checkError(result[1], "ftlist"))
			return resultList;
		for (String info : information) {
			resultList.add(new FileTransferInfo(info.split(" ")));
		}
		return resultList;
	}

	public void renameFile(int channelID, String channelPassword, String oldFilePath, String newFilePath) {
		query.getWriter().executeReadErrorCommand(
				"ftrenamefile cid=" + channelID + " cpw=" + channelPassword + " oldname=" + Formatter.toTsFormat(oldFilePath) + " newname=" + Formatter.toTsFormat(newFilePath));
	}

	public void moveFile(int channelID, String channelPassword, String oldFilePath, int newChannelID, String newChannelPassword, String newFilePath) {
		query.getWriter().executeReadErrorCommand("ftrenamefile cid=" + channelID + " cpw=" + channelPassword + "tcid=" + newChannelID + " tcpw=" + newChannelPassword + " oldname="
				+ Formatter.toTsFormat(oldFilePath) + " newname=" + Formatter.toTsFormat(newFilePath));
	}

	public void stopFileTransfer(int serverFileTransferID, boolean delete) {
		query.getWriter().executeReadErrorCommand("ftstop serverftfid=" + serverFileTransferID + " delete=" + Formatter.toInt(delete));
	}

	public void editInstance(Map<InstanceProperty, String> instanceProperties) {
		StringBuilder commandBuilder = new StringBuilder("instanceedit");
		instanceProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(instanceProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});
		query.getWriter().executeReadCommand(commandBuilder.toString());
	}

	public InstanceInfo getInstanceInfo() {
		String information = query.getWriter().executeReadCommand("instanceinfo")[0];
		if (information.isEmpty())
			return null;
		return new InstanceInfo(information.split(" "));
	}

	public void addToLog(int logLevel, String logMessage) {
		query.getWriter().executeReadErrorCommand("logadd loglevel=" + logLevel + " logmsg=" + Formatter.toTsFormat(logMessage));
	}

	public List<String> getLog(int lines, boolean reverse, boolean instance, int beginPos) {
		List<String> resultLines = new ArrayList<>();
		String command = "logview lines=" + lines + " reverse=" + Formatter.toInt(reverse) + " instance=" + Formatter.toInt(instance) + " begin_pos=" + beginPos;

		String[] log = query.getWriter().executeReadCommand(command)[0].split(TS_INFO_SEPERATOR);
		for (String logLines : log) {
			resultLines.add(Formatter.toNormalFormat(get(logLines, "l=")));
		}
		return resultLines;
	}

	public void sendOfflineMessage(String clientUUID, String subject, String message) {
		query.getWriter().executeReadErrorCommand("messageadd cluid=" + clientUUID + " subject=" + Formatter.toTsFormat(subject) + " message=" + Formatter.toTsFormat(message));
	}

	public void deleteOfflineMessage(int offlineMessageID) {
		query.getWriter().executeReadErrorCommand("messagedel msgid=" + offlineMessageID);
	}

	public void updateOfflineMessageFlag(int offlineMessageID, boolean flag) {
		query.getWriter().executeReadErrorCommand("messageupdateflag msgid=" + offlineMessageID + " flag=" + Formatter.toInt(flag));
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermission(int permissionID) {
		List<Integer> ids = new ArrayList<>();
		ids.add(permissionID);
		return getAssignmentsOfPermissions(ids, new ArrayList<>());
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermission(String permissionName) {
		List<String> names = new ArrayList<>();
		names.add(permissionName);
		return getAssignmentsOfPermissions(new ArrayList<>(), names);
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("permfind ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()) || result[0].isEmpty())
			return resultList;
		for (String perms : splitResult(result)) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}
		return resultList;
	}

	public Permission getQueryAssignmentOfPermission(int permissionID) {
		List<Integer> ids = new ArrayList<>();
		ids.add(permissionID);
		List<Permission> result = getQueryAssignmentsOfPermissions(ids, new ArrayList<>());
		return result.isEmpty() ? null : result.get(0);
	}

	public Permission getQueryAssignmentOfPermission(String permissionName) {
		List<String> names = new ArrayList<>();
		names.add(permissionName);
		List<Permission> result = getQueryAssignmentsOfPermissions(new ArrayList<>(), names);
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Permission> getQueryAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("permget ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return getPermissionListByCommand(cmd.toString());
	}

	public List<PermissionAssignmentInfo> getPermOverview(int clientDBID, int channelID, int permID) {
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();
		String[] info = query.getWriter().executeReadCommand("permoverview cid=" + channelID + " cldbid=" + clientDBID + " permid=" + permID)[0].split(TS_INFO_SEPERATOR);
		for (String perms : info) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}
		return resultList;
	}

	public PermissionAssignmentInfo getPermOverviewByPermID(int clientDBID, int channelID, int permID) {
		return getPermOverview(clientDBID, channelID, permID).get(0);
	}

	public String createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		StringBuilder cmd = new StringBuilder("privilegekeyadd");
		cmd.append(" tokentype=" + keyType.getValue());
		cmd.append(" tokenid1=" + groupID);
		cmd.append(" tokenid2=" + channelID);
		if (description != null)
			cmd.append(" tokendescription=" + Formatter.toTsFormat(description));
		return get(query.getWriter().executeReadCommand(cmd.toString())[0], "token=");
	}

	public void deletePrivilegeKey(String privilegeKey) {
		query.getWriter().executeReadErrorCommand("privilegekeydelete token=" + privilegeKey);
	}

	public void usePrivilegeKey(String privilegeKey) {
		query.getWriter().executeReadErrorCommand("privilegekeyuse token=" + privilegeKey);
	}

	public CreatedQueryLogin createQueryLogin(String loginName, int clientDBID) {
		StringBuilder command = new StringBuilder("queryloginadd");
		command.append(" client_login_name=" + loginName);
		if (clientDBID != -1)
			command.append(" cldbid=" + clientDBID);
		String information = query.getWriter().executeReadCommand(command.toString())[0];
		if (information.isEmpty())
			return null;
		return new CreatedQueryLogin(information.split(" "));
	}

	public void deleteQueryLogin(int clientDBID) {
		query.getWriter().executeReadErrorCommand("querylogindel cldbid=" + clientDBID);
	}

	/**
	 * List existing query client logins.
	 * 
	 * @param pattern
	 *                     Filter for client login name (set to null to ignore)
	 * @param startOffset
	 *                     Integer to skip the first `n` entries (set to -1 to
	 *                     ignore)
	 * @param duration
	 *                     Integer to only return the first `n` entries (set to -1
	 *                     to ignore)
	 * @return List of Query logins
	 */

	public List<CreatedQueryLogin> getQueryLogins(String pattern, int startOffset, int duration) {
		List<CreatedQueryLogin> resultList = new ArrayList<>();
		StringBuilder commandBuilder = new StringBuilder("queryloginlist");
		if (pattern != null)
			commandBuilder.append(" pattern=" + pattern);
		if (startOffset != -1)
			commandBuilder.append(" start=" + startOffset);
		if (duration != -1)
			commandBuilder.append(" duration=" + duration);

		String[] info = query.getWriter().executeReadCommand(commandBuilder.toString())[0].split(TS_INFO_SEPERATOR);
		for (String queryLogin : info) {
			resultList.add(new CreatedQueryLogin(queryLogin.split(" ")));
		}

		return resultList;
	}

	public void sendTextMessage(TextMessageType messageType, int clientID, String message) {
		query.getWriter().executeReadErrorCommand("sendtextmessage targetmode=" + messageType.getValue() + " target=" + clientID + " msg=" + Formatter.toTsFormat(message));
	}

	public CreatedVirtualServer createVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		StringBuilder commandBuilder = new StringBuilder("servercreate");
		virtualServerProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});

		String[] result = query.getWriter().executeReadCommand(commandBuilder.toString());
		if (checkError(result[1], "servercreate") || result[0].isEmpty())
			return null;
		return new CreatedVirtualServer(result[0].split(" "));
	}

	public void deleteVirtualServer(int virtualServerID) {
		query.getWriter().executeReadErrorCommand("serverdelete sid=" + virtualServerID);
	}

	public void editVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		StringBuilder commandBuilder = new StringBuilder("serveredit");

		virtualServerProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		});
		query.getWriter().executeReadCommand(commandBuilder.toString());
	}

	public int createServerGroup(String serverGroupName, ServerGroupType groupType) {
		String command = "servergroupadd name=" + Formatter.toTsFormat(serverGroupName) + " type=" + groupType.getValue();
		return Integer.parseInt(get(query.getWriter().executeReadCommand(command)[0], "sgid="));
	}

	public void addClientToServerGroup(int groupID, int clientDBID) {
		ArrayList<Integer> dataBaseClientIDs = new ArrayList<>();
		dataBaseClientIDs.add(clientDBID);
		addClientDBIDsToServerGroup(groupID, dataBaseClientIDs);
	}

	public void addClientDBIDsToServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder cmd = new StringBuilder("servergroupaddclient");
		cmd.append(" sgid=" + serverGroupID + " ");
		cmd.append(buildObjectArray(dataBaseClientIDs, "cldbid="));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void addServerGroupPermission(int serverGroupID, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null));
		addServerGroupPermissions(serverGroupID, perms);
	}

	public void addServerGroupPermissions(int serverGroupID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("servergroupaddperm");
		cmd.append(" sgid=" + serverGroupID + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	/**
	 * Adds Permission to all Groups of a specified level.
	 * 
	 * @param groupLevel
	 *                            Selected level of groups.
	 * @param permissionID
	 *                            ID to identify the permission (could be -1 to be
	 *                            ignored)
	 * @param permissionName
	 *                            Name to identify the permission (could be null to
	 *                            be ignored)
	 * @param permissionValue
	 *                            Value the permission should have.
	 * @param permNegated
	 *                            Permission should be negated or not.
	 * @param permSkip
	 *                            Permission should skip or not.
	 */

	public void addServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		List<Permission> perms = new ArrayList<>();
		perms.add(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null));
		addServerGroupAutoPermissions(groupLevel, perms);
	}

	public void addServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("servergroupautoaddperm");
		cmd.append(" sgtype=" + groupLevel.getValue() + " ");
		cmd.append(buildAddPermsArray(permissions));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void removeServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName) {
		List<Integer> permIDs = new ArrayList<>();
		permIDs.add(permissionID);
		List<String> permNames = new ArrayList<>();
		permNames.add(permissionName);
		removeServerGroupAutoPermissions(groupLevel, permIDs, permNames);
	}

	public void removeServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("servergroupautodelperm");
		cmd.append(" sgtype=" + groupLevel.getValue() + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	/**
	 * Creates a copy of the server group specified with sourceServerGroupID.
	 * 
	 * @param sourceServerGroupID
	 *                                ID of a group the server should copy.
	 * @param targetServerGroupID
	 *                                ID of a designated target group (if is set to
	 *                                0 the server will create a new group)
	 * @param serverGroupName
	 *                                Name of the new group.
	 * @param serverGroupType
	 *                                Type of servergroup to choose between
	 *                                ServerQuery group, template or normal group.
	 * 
	 * @return
	 */

	public int copyServerGroup(int sourceServerGroupID, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
		String command = "servergroupcopy ssgid=" + sourceServerGroupID + " tsgid=" + targetServerGroupID + " name=" + Formatter.toTsFormat(serverGroupName) + " type="
				+ serverGroupType.getValue();
		return Integer.parseInt(get(query.getWriter().executeReadCommand(command)[0], "sgid="));
	}

	public void deleteServerGroup(int serverGroupID, boolean force) {
		query.getWriter().executeReadErrorCommand("servergroupdel sgid=" + serverGroupID + " force=" + Formatter.toInt(force));
	}

	public void removeClientFromServerGroup(int serverGroupID, int clientDBID) {
		ArrayList<Integer> dataBaseClients = new ArrayList<>();
		dataBaseClients.add(clientDBID);
		removeClientDBIDsFromServerGroup(serverGroupID, dataBaseClients);
	}

	public void removeClientDBIDsFromServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder cmd = new StringBuilder("servergroupdelclient");
		cmd.append(" sgid=" + serverGroupID + " ");
		cmd.append(buildObjectArray(dataBaseClientIDs, "cldbid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void renameServerGroup(int serverGroupID, String name) {
		query.getWriter().executeReadErrorCommand("servergrouprename sgid=" + serverGroupID + " name=" + Formatter.toTsFormat(name));
	}

	public void removeServerGroupPermission(int serverGroupID, int permissionID, String permissionName) {
		List<Integer> permissionIDs = new ArrayList<>();
		List<String> permissionNames = new ArrayList<>();
		permissionIDs.add(permissionID);
		permissionNames.add(permissionName);
		removeClientPermissions(serverGroupID, permissionIDs, permissionNames);
	}

	public void removeServerGroupPermissions(int serverGroupID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("servergroupdelperm");
		cmd.append(" sgid=" + serverGroupID + " ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void addVirtualServerTempPassword(String password, String description, long duration, int targetChannelID, String targetChannelPassword) {
		StringBuilder cmd = new StringBuilder("servertemppasswordadd");
		cmd.append(" pw=" + Formatter.toTsFormat(password));
		cmd.append(" desc=" + Formatter.toTsFormat(description));
		cmd.append(" duration=" + duration);
		cmd.append(" tcid=" + targetChannelID);
		cmd.append(" tcpw=" + targetChannelPassword);

		query.getWriter().executeReadErrorCommand(cmd.toString());
	}

	public void deleteVirtualServerTempPassword(String password) {
		query.getWriter().executeReadErrorCommand("servertemppassworddel pw=" + Formatter.toTsFormat(password));
	}

	public List<TempPasswordInfo> getVirtualServerTempPasswords() {
		List<TempPasswordInfo> resultList = new ArrayList<>();
		String[] result = query.getWriter().executeReadCommand("servertemppasswordlist");

		if (checkError(result[1], "servertemppasswordlist"))
			return resultList;
		String[] information = splitResult(result);
		for (String tempPassword : information) {
			resultList.add(new TempPasswordInfo(tempPassword.split(" ")));
		}
		return resultList;
	}

	public CreatedSnapshot createSnapshot(String password) {
		StringBuilder cmd = new StringBuilder("serversnapshotcreate");
		if (password != null)
			cmd.append(" password=" + password);
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return null;

		return new CreatedSnapshot(result[0].split(" "));
	}

	/**
	 * Creates a new apikey using the specified scope, for the invoking user.
	 * 
	 * @param scope
	 *                       Permissions of the key
	 * @param lifetime
	 *                       Lifetime of the key in Days (default is 14, 0 is
	 *                       infinte, -1 to set to ignore)
	 * @param clientDBID
	 *                       ID of the keyowner (default it the invoker, null to set
	 *                       to ignore)
	 * @return CreatedAPIKey
	 */

	public CreatedAPIKey addAPIKey(APIScope scope, int lifetime, int clientDBID) {
		StringBuilder cmd = new StringBuilder("apikeyadd");
		cmd.append(" scope=" + scope.getValue());
		if (lifetime != -1)
			cmd.append(" lifetime=" + lifetime);
		if (clientDBID != -1)
			cmd.append(" cldbid=" + clientDBID);

		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return null;
		return new CreatedAPIKey(result[0].split(" "));
	}

	public void deleteAPIKey(int keyID) {
		query.getWriter().executeReadErrorCommand("apikeydel id=" + keyID);
	}
	
	/**
	 * Lists all apikeys owned by the a user.
	 * @param clientDBID ClientDataBaseID (use -1 to ingore, use 0 to list all clients)
	 * @param startOffset
	 *                        To skip the first 'n' entries.
	 * @param limit
	 *                        Return only the first 'n' entries.
	 * @return List of APIKeys
	 */

	public List<CreatedAPIKey> getAPIKeys(int clientDBID, int startOffset, int limit) {
		List<CreatedAPIKey> resultList = new ArrayList<>();
		StringBuilder cmd = new StringBuilder("apikeylist");
		if (clientDBID != -1) {
			cmd.append(" cldbid=");
			if (clientDBID == 0) {
				cmd.append("*");
			} else {
				cmd.append(clientDBID);
			}
		}
		if (startOffset != -1)
			cmd.append(" start=" + startOffset);
		if (limit != -1)
			cmd.append(" duration=" + limit);
		String[] result = query.getWriter().executeReadCommand(cmd.toString());
		if (checkError(result[1], cmd.toString()))
			return resultList;
		for (String info : splitResult(result)) {
			resultList.add(new CreatedAPIKey(info.split(" ")));
		}
		return resultList;
	}

	public void setClientChannelGroup(int channelGroupID, int channelID, int clientDBID) {
		query.getWriter().executeReadErrorCommand("setclientchannelgroup cgid=" + channelGroupID + " cid=" + channelID + " cldbid=" + clientDBID);
	}

	public void registerEvent(EventType eventType, int channelID) {
		StringBuilder cmdBuilder = new StringBuilder("servernotifyregister");
		cmdBuilder.append(" event=" + eventType.getValue());
		if (channelID != -1)
			cmdBuilder.append(" id=" + channelID);

		query.getWriter().executeReadErrorCommand(cmdBuilder.toString());
	}

	public void unRegisterAllEvents() {
		query.getWriter().executeReadErrorCommand("servernotifyunregister");
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * @param connected
	 *                      the connected to set
	 */
	private void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public String get(String stringFrom, String splitter) {
		return stringFrom.split(splitter)[1].split(" ")[0];
	}
	
	private String[] splitResult(String[] result) {
		return result[0].split(TS_INFO_SEPERATOR);
	}
	
	private boolean checkError(String info, String cmd) {
		boolean error = false;
		if (TSError.isError(info, TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(info, TSError.INSUFFICIENT_CLIENT_PERMISSIONS)) {
			query.debug(DebugOutputType.ERROR, "Insufficient client permissions for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(info, TSError.PARAMETER_NOT_FOUND)) {
			query.debug(DebugOutputType.WARNING, "Parameter not found for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(info, TSError.INVALID_CLIENT_ID.getValue())) {
			query.debug(DebugOutputType.WARNING, "Some ClientUUID was invalid for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(info, TSError.CHANNEL_NAME_IS_ALEARY_IN_USE)) {
			query.debug(DebugOutputType.WARNING, "Channel could not be created! Channelname is already in use!");
			error = true;
		} else if (TSError.isError(info, TSError.FILE_IO_ERROR)) {
			query.debug(DebugOutputType.WARNING, "File input/output error for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(info, TSError.VIRTUALSERVER_LIMIT_REACHED)) {
			query.debug(DebugOutputType.ERROR, "Limit of virtual servers reached by command: '" + cmd + "'");
			error = true;
		}

		return error;
	}

	private String buildObjectArray(List<? extends Object> objects, String key) {
		StringBuilder list = new StringBuilder();
		objects.forEach(object -> {
			if (!object.equals(objects.get(0)))
				list.append("|");
			list.append(key + "=" + Formatter.toTsFormat(object.toString()));
		});
		return list.toString();
	}

	private String buildAddPermsArray(List<Permission> permissions) {
		StringBuilder list = new StringBuilder();
		permissions.forEach(perms -> {
			if (perms.getID() != permissions.get(0).getID() && !perms.getPermName().equals(permissions.get(0).getPermName()))
				list.append("|");
			if (perms.getPermID() != -1)
				list.append(" permid=" + perms.getPermID());
			if (perms.getPermName() != null)
				list.append(" permsid=" + perms.getPermName());
			if (perms.getPermValue() != -1)
				list.append(" permvalue=" + perms.getPermValue());
			if (perms.isNegated())
				list.append(" permnegated=" + Formatter.toInt(perms.isNegated()));
			if (perms.isSkip())
				list.append(" permskip=" + Formatter.toInt(perms.isSkip()));
		});
		return list.toString();
	}
}
