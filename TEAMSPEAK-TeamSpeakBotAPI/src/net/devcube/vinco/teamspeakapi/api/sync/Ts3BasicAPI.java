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

	public String get(String stringFrom, String splitter) {
		return stringFrom.split(splitter)[1].split(" ")[0];
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
		if (TSError.isError(res, TSError.INSUFFICIENT_CLIENT_PERMISSIONS)) {
			query.debug(DebugOutputType.ERROR, "Use Command failed! Insufficient client permissions!");
			return;
		}

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
		query.getWriter().executeReadErrorCommand("clientmove clid=" + clientID + " cid=" + channelID);
	}

	public void sendGlobalMessage(String message) {
		query.getWriter().executeReadErrorCommand("gm msg=" + Formatter.toTsFormat(message));
	}

	public void logout() {
		query.getWriter().executeReadErrorCommand("logout");
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
		return Integer.parseInt(query.getWriter().executeReadCommand("permidgetbyname permsid=" + permissionName)[0].split("permid=")[1]);
	}

	public String getPermissionName(int permissionID) {
		return query.getWriter().executeReadCommand("permget permid=" + permissionID)[0].split("permsid=")[1].split(" ")[0];
	}

	public List<Permission> getPermissionList() {
		List<Permission> resultList = new ArrayList<Permission>();
		String[] permissions;
		if (config.isPermissionCached()) {
			permissions = cache.getPermissionsList().split(TS_INFO_SEPERATOR);
		} else {
			permissions = query.getWriter().executeReadCommand("permissionlist")[0].split(TS_INFO_SEPERATOR);
		}

		for (String permission : permissions) {
			int permID = Integer.parseInt(get(permission, "permid="));
			String permName = get(permission, "permname=");
			String permDes = "";
			if (permission.contains("permdesc=")) {
				permDes = Formatter.toNormalFormat(permission.split("permdesc=")[1]);
			}

			resultList.add(new Permission(permName, permID, permDes));
		}

		return resultList;
	}

	private List<Permission> getPermissionListByCommand(String command) {
		List<Permission> resultList = new ArrayList<Permission>();
		String[] result = query.getWriter().executeReadCommand(command);
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : '" + command + "'");
			return resultList;
		}

		String[] permissions = result[0].split(TS_INFO_SEPERATOR);
		for (String permission : permissions) {
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
			if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
				query.debug(DebugOutputType.WARNING, "Database was empty for command : 'servergrouplist'");
				return resultList;
			}
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
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'servergroupsbyclientid cldbid=" + clientDBID + "'");
			return resultList;
		}

		String[] servergroups = result[0].split(TS_INFO_SEPERATOR);
		for (String groups : servergroups) {
			int groupID = Integer.parseInt(get(groups, "sgid="));
			resultList.add(groupID);
		}

		return resultList;
	}

	public List<String> getServerGroupNamesByClient(int clientDBID) {
		List<String> resultList = new ArrayList<String>();
		String[] result = query.getWriter().executeReadCommand("servergroupsbyclientid cldbid=" + clientDBID);
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'servergroupsbyclientid cldbid=" + clientDBID + "'");
			return resultList;
		}

		String[] servergroups = result[0].split(TS_INFO_SEPERATOR);
		for (String groups : servergroups) {
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
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'messageget msgid=" + id + "'");
			return null;
		}

		return new OfflineMessageInfo(information.split(" "));
	}

	/**
	 * Uses only the given information of clientdblist. Less detailed than
	 * databaseclientinfo for each client, because some Information are not provided
	 * by clientdblist.
	 * 
	 * @see Ts3SyncAPI#getClientDBListDetailed() gets more information about each
	 *      client.
	 * 
	 * @return List with Information about each Client in the Database.
	 */

	public List<DataBaseClientInfo> getDataBaseClients() {
		List<DataBaseClientInfo> resultList = new ArrayList<DataBaseClientInfo>();
		String[] clients;
		if (config.isDataBaseCached()) {
			clients = cache.getDBClientsList().split(TS_INFO_SEPERATOR);
		} else {
			clients = query.getWriter().executeReadCommand("clientdblist")[0].split(TS_INFO_SEPERATOR);
		}

		for (String client : clients) {
			resultList.add(new DataBaseClientInfo(client.replace("cldbid", "client_database_id").split(" ")));
		}
		return resultList;
	}

	public List<OfflineMessageInfo> getOfflineMessages() {
		List<OfflineMessageInfo> resultList = new ArrayList<OfflineMessageInfo>();
		String[] result = query.getWriter().executeReadCommand("messagelist");
		String[] clients = result[0].split(TS_INFO_SEPERATOR);
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'messagelist'");
			return resultList;
		}

		for (String client : clients) {
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

	public String getClientName(String clientUUID) {
		String name = query.getWriter().executeReadCommand("clientgetnamefromuid cluid=" + clientUUID)[0].split("name=")[1];
		return Formatter.toNormalFormat(name);
	}

	public String getClientName(int clientDataBaseID) {
		String name = query.getWriter().executeReadCommand("clientgetnamefromdbid cldbid=" + clientDataBaseID)[0].split("name=")[1];
		return Formatter.toNormalFormat(name);
	}

	public String getClientUUID(int clientDataBaseID) {
		return query.getWriter().executeReadCommand("clientgetnamefromdbid cldbid=" + clientDataBaseID)[0].split("cluid=")[1].split(" ")[0];
	}

	public int getClientDataBaseID(String clientUUID) {
		return Integer.parseInt(query.getWriter().executeReadCommand("clientgetdbidfromuid cluid=" + clientUUID)[0].split("cldbid=")[1]);
	}

	public ClientInfo getClient(int clientID) {
		String information;
		if (config.isClientsCached()) {
			information = cache.getClientInfo(clientID);
		} else {
			information = query.getWriter().executeReadCommand("clientinfo clid=" + clientID)[0];
		}

		if (information.isEmpty())
			return null;

		return new ClientInfo(information.concat(" clid=" + clientID).split(" "));
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
			int clientID = Integer.parseInt(get(client, "clid="));
			resultList.add(new ClientInfo(client.concat(" clid=" + clientID).split(" ")));
		}
		return resultList;
	}

	public DataBaseClientInfo getDataBaseClient(int clientDataBaseID) {
		String information;
		if (config.isDataBaseCached()) {
			information = cache.getDBClientInfo(clientDataBaseID);
		} else {
			information = query.getWriter().executeReadCommand("clientdbinfo cldbid=" + clientDataBaseID)[0];
		}

		if (information.isEmpty())
			return null;

		return new DataBaseClientInfo(information.split(" "));
	}

	public ChannelInfo getChannel(int channelID) {
		String information;
		if (config.isChannelsCached()) {
			information = cache.getChannelInfo(channelID);
		} else {
			information = query.getWriter().executeReadCommand("channelinfo cid=" + channelID)[0];
		}

		if (information.isEmpty())
			return null;
		return new ChannelInfo(information.concat(" cid=" + channelID).split(" "));
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
		String[] channels = result[0].split(TS_INFO_SEPERATOR);
		if (TSError.isError(result[1], TSError.INSUFFICIENT_CLIENT_PERMISSIONS)) {
			query.debug(DebugOutputType.WARNING, "Client has insufficient client permissions for command : 'channellist'");
			return resultList;
		}

		for (String channel : channels) {
			resultList.add(new ChannelInfo(channel.split(" ")));
		}
		return resultList;
	}

	/**
	 * Gets information about all Channels. Less detailed, because some Information
	 * are not provided by channellist. For more detailed Information
	 * 
	 * @see Ts3SyncAPI#getChannelsDetailed()
	 * 
	 * @return an Empty List if QueryClient has no Permissions.
	 */

	public List<ChannelInfo> getChannels() {
		if (config.isChannelsCached()) {
			List<ChannelInfo> resultList = new ArrayList<ChannelInfo>();
			String[] channels = cache.getChannelsList().split(TS_INFO_SEPERATOR);
			for (String channel : channels) {
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
			resultList.add(Integer.parseInt(user.split("cldbid=")[1]));
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
		String[] users = query.getWriter().executeReadCommand("channelgroupclientlist cgid=" + channelgroupID)[0].split(TS_INFO_SEPERATOR);
		for (String user : users) {
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
		String[] users = query.getWriter().executeReadCommand("channelgroupclientlist cid=" + channelID + " cgid=" + channelgroupID)[0].split(TS_INFO_SEPERATOR);
		for (String user : users) {
			if (user.isEmpty())
				continue;
			resultList.add(Integer.parseInt(get(user, "cldbid=")));
		}
		return resultList;
	}

	/**
	 * Provides a Map which contains every ChannelGroup(ID) as a Key and the Value
	 * is a List of ChannelIDs in which the Client has the ChannelGroup(ID). Note
	 * that getChannelGroupInfosByDatabaseID caches the List of ChannelGroups to
	 * improve the performance. To get only the ChannelGroup(ID)s just use keySet().
	 * 
	 * @param clientDataBaseID
	 * @return [ChannelGroupID, ChannelID]
	 */

	public Map<Integer, List<Integer>> getChannelGroupsByDatabaseID(int clientDataBaseID) {
		Map<Integer, List<Integer>> resultMap = new HashMap<>();
		String[] users = query.getWriter().executeReadCommand("channelgroupclientlist cldbid=" + clientDataBaseID)[0].split(TS_INFO_SEPERATOR);
		for (String user : users) {
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
		String[] users = query.getWriter().executeReadCommand("channelgroupclientlist cid=" + channelID)[0].split(TS_INFO_SEPERATOR);
		for (String user : users) {
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
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'complainlist tcldbid=" + clientDataBaseID + "'");
			return resultList;
		}

		for (String complain : result[0].split(TS_INFO_SEPERATOR)) {
			resultList.add(new ComplainInfo(complain.split(" ")));
		}
		return resultList;
	}

	public List<PrivilegeKeyInfo> getPrivilegeKeys() {
		List<PrivilegeKeyInfo> resultList = new ArrayList<PrivilegeKeyInfo>();
		String[] result = query.getWriter().executeReadCommand("privilegekeylist");
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'privilegekeylist'");
			return resultList;
		}

		for (String token : result[0].split(TS_INFO_SEPERATOR)) {
			resultList.add(new PrivilegeKeyInfo(token.split(" ")));
		}
		return resultList;
	}

	public List<ComplainInfo> getComplains() {
		List<ComplainInfo> resultList = new ArrayList<ComplainInfo>();
		String[] result = query.getWriter().executeReadCommand("complainlist");
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'complainlist'");
			return resultList;
		}

		for (String complain : result[0].split(TS_INFO_SEPERATOR)) {
			resultList.add(new ComplainInfo(complain.split(" ")));
		}
		return resultList;
	}

	public List<BanInfo> getBans() {
		List<BanInfo> resultList = new ArrayList<BanInfo>();
		String[] result = query.getWriter().executeReadCommand("banlist");
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'banlist'");
			return resultList;
		}

		for (String ban : result[0].split(TS_INFO_SEPERATOR)) {
			resultList.add(new BanInfo(ban.split(" ")));
		}
		return resultList;
	}

	protected List<VirtualServerInfo> getVirtualServersByCommand(String command) {
		List<VirtualServerInfo> resultList = new ArrayList<VirtualServerInfo>();
		String[] servers = query.getWriter().executeReadCommand(command)[0].split(TS_INFO_SEPERATOR);
		for (String server : servers) {
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
		StringBuilder commandBuilder = new StringBuilder("banadd");
		if (ip != null)
			commandBuilder.append(" ip=" + ip);
		if (name != null)
			commandBuilder.append(" name=" + Formatter.toTsFormat(name));
		if (clientUUID != null)
			commandBuilder.append(" uid=" + clientUUID);
		if (myTSID != null)
			commandBuilder.append(" mytsid=" + myTSID);
		if (banTime != -2)
			commandBuilder.append(" time=" + banTime);
		if (banReason != null)
			commandBuilder.append(" banreason=" + Formatter.toTsFormat(banReason));

		return Integer.parseInt(query.getWriter().executeReadCommand(commandBuilder.toString())[0].split("=")[1]);
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
		StringBuilder commandBuilder = new StringBuilder();
		commandBuilder.append("banclient time=" + banTime);
		if (banReason != null)
			commandBuilder.append(" banreason=" + Formatter.toTsFormat(banReason));
		commandBuilder.append(" clid=" + clientID);

		String res = query.getWriter().executeReadCommand(commandBuilder.toString())[0]
				.replace("banid=", "").replace("\n", ",");
		int banIDclient = Integer.parseInt(res.split(",")[0]);
		int banIDip = Integer.parseInt(res.split(",")[1]);
		
		return new int[] {banIDclient, banIDip};
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
		StringBuilder commandBuilder = new StringBuilder("serverstop");
		commandBuilder.append(" sid=" + virtualServerID);
		if (reasonmsg != null)
			commandBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reasonmsg));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void stopServerProcess(String reasonmsg) {
		StringBuilder cmdBuilder = new StringBuilder("serverprocessstop");
		if (reasonmsg != null) {
			cmdBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reasonmsg));
		}

		query.getWriter().executeReadCommand(cmdBuilder.toString());
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
		StringBuilder commandBuilder = new StringBuilder("channeladdperm");
		commandBuilder.append(" cid=" + channelID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
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
		StringBuilder commandBuilder = new StringBuilder("channelclientaddperm");
		commandBuilder.append(" cid=" + channelID);
		commandBuilder.append(" cldbid=" + clientdataBaseID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
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
		StringBuilder commandBuilder = new StringBuilder("channelclientdelperm");
		commandBuilder.append(" cid=" + channelID);
		commandBuilder.append(" cldbid=" + clientdataBaseID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public int createChannel(String channelName, Map<ChannelProperty, String> channelProperties) {
		StringBuilder commandBuilder = new StringBuilder("channelcreate");
		commandBuilder.append(" channel_name=" + Formatter.toTsFormat(channelName));
		for (ChannelProperty prop : channelProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}
		String[] result = query.getWriter().executeReadCommand(commandBuilder.toString());
		if (TSError.isError(result[1], TSError.CHANNEL_NAME_IS_ALEARY_IN_USE)) {
			query.debug(DebugOutputType.WARNING, "Channel could not be created! Channelname is already in use!");
			return -1;
		}
		return Integer.parseInt(result[0].split("cid=")[1]);
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
		StringBuilder commandBuilder = new StringBuilder("channeldelperm");
		if (channelID != -1)
			commandBuilder.append("cid=" + channelID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void editChannel(int channelID, Map<ChannelProperty, String> channelProperties) {
		StringBuilder commandBuilder = new StringBuilder("channeledit");
		commandBuilder.append(" cid=" + channelID);
		for (ChannelProperty prop : channelProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}

		query.getWriter().executeReadCommand(commandBuilder.toString());
	}

	public int createChannelGroup(String channelGroupName, ChannelGroupType channelGroupType) {
		String command = "channelgroupadd name=" + Formatter.toTsFormat(channelGroupName) + " type=" + channelGroupType.getValue();
		return Integer.parseInt(query.getWriter().executeReadCommand(command)[0].split("cgid=")[1]);
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
		StringBuilder commandBuilder = new StringBuilder("channelgroupaddperm");
		commandBuilder.append(" cgid=" + channelGroupID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public int copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		StringBuilder commandBuilder = new StringBuilder("channelgroupcopy");
		commandBuilder.append(" scgid=" + sourceChannelGroupID);
		commandBuilder.append(" tcgid=" + targetChannelGroupID);
		commandBuilder.append(" name=" + Formatter.toTsFormat(channelGroupName));
		commandBuilder.append(" type=" + channelGroupType.getValue());

		return Integer.parseInt(query.getWriter().executeReadCommand(commandBuilder.toString())[0].split("cgid=")[1]);
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
		StringBuilder commandBuilder = new StringBuilder("channelgroupdelperm");
		commandBuilder.append(" cgid=" + channelGroupID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void renameChannelGroup(int channelGroupID, String channelName) {
		query.getWriter().executeReadErrorCommand("channelgrouprename cgid=" + channelGroupID + " name=" + Formatter.toTsFormat(channelName));
	}

	public void moveChannel(int channelID, int channelParentID, int order) {
		query.getWriter().executeReadErrorCommand("channelmove cid=" + channelID + " cpid=" + channelParentID + " order=" + order);
	}

	public void addClientPermission(int clientDataBaseID, int permissionID, String permissionName, int permissionValue, boolean permSkip) {
		StringBuilder commandBuilder = new StringBuilder("clientaddperm");
		commandBuilder.append(" cldbid=" + clientDataBaseID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);
		commandBuilder.append(" permskip=" + Formatter.toInt(permSkip));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void deleteDataBaseClient(int clientDataBaseID) {
		query.getWriter().executeReadErrorCommand("clientdbdelete cldbid=" + clientDataBaseID);
	}

	public void editDataBaseClient(int clientDataBaseID, String description) {
		query.getWriter().executeReadErrorCommand("clientdbedit cldbid=" + clientDataBaseID + " client_description=" + Formatter.toTsFormat(description));
	}

	public int getDataBaseClientIDByUUID(String clientUUID) {
		return Integer.parseInt(query.getWriter().executeReadCommand("clientdbfind -uid pattern=" + clientUUID)[0].split("cldbid=")[1]);
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
		StringBuilder commandBuilder = new StringBuilder("clientdelperm");
		commandBuilder.append(" cldbid=" + clientDataBaseID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void editClient(int clientID, Map<ClientProperty, String> clientProperties) {
		StringBuilder commandBuilder = new StringBuilder("clientedit");
		commandBuilder.append(" clid=" + clientID);
		for (ClientProperty prop : clientProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(clientProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}

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

	public int getClientIDByUUID(String clientUUID) {
		String information = query.getWriter().executeReadCommand("clientgetids cluid=" + clientUUID)[0];
		return Integer.parseInt(get(information, "clid="));
	}

	public String getClientUUIDByID(int clientID) {
		return query.getWriter().executeReadCommand("clientgetuidfromclid clid=" + clientID)[0].split("cluid=")[1].split(" ")[0];
	}

	public String getClientNameByID(int clientID) {
		String name = query.getWriter().executeReadCommand("clientgetuidfromclid clid=" + clientID)[0].split("nickname=")[1].split(" ")[0];
		return Formatter.toNormalFormat(name);
	}

	public void kickClientFromServer(int clientID, String reason) {
		StringBuilder cmdBuilder = new StringBuilder("clientkick");
		cmdBuilder.append(" reasonid=5");
		if (reason != null)
			cmdBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		cmdBuilder.append(" clid=" + clientID);
		query.getWriter().executeReadErrorCommand(cmdBuilder.toString());
	}

	public void kickClientFromChannel(int clientID, String reason) {
		StringBuilder cmdBuilder = new StringBuilder("clientkick");
		cmdBuilder.append(" reasonid=4");
		if (reason != null)
			cmdBuilder.append(" reasonmsg=" + Formatter.toTsFormat(reason));
		cmdBuilder.append(" clid=" + clientID);
		query.getWriter().executeReadErrorCommand(cmdBuilder.toString());
	}

	public String updateServerQueryLogin(String username) {
		String information = query.getWriter().executeReadCommand("clientsetserverquerylogin client_login_name=" + Formatter.toTsFormat(username))[0];
		return information.split("client_login_password=")[1];
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
		query.getWriter().executeReadErrorCommand("ftdeletefile cid=" + channelID + " cpw=" + channelPassword + " name=" + Formatter.toTsFormat(fileName));
	}

	public FileInfo getFileInfo(int channelID, String channelPassword, String fileName) {
		String information = query.getWriter().executeReadCommand("ftgetfileinfo cid=" + channelID + " cpw=" + channelPassword + " name=" + Formatter.toTsFormat(fileName))[0];
		if (information.isEmpty())
			return null;

		return new FileInfo(information.split(" "));
	}

	public List<FileInfo> getChannelFilesByPath(int channelID, String channelPassword, String filePath) {
		List<FileInfo> resultList = new ArrayList<>();
		String command = "ftgetfilelist cid=" + channelID + " cpw=" + channelPassword + " path=" + Formatter.toTsFormat(filePath);

		String[] result = query.getWriter().executeReadCommand(command);
		String[] information = result[0].split(TS_INFO_SEPERATOR);
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : '" + command + "'");
			return resultList;
		}

		for (String info : information) {
			resultList.add(new FileInfo(info.split(" ")));
		}

		return resultList;
	}

	public List<FileTransferInfo> getFileTransfers() {
		List<FileTransferInfo> resultList = new ArrayList<>();
		String[] result = query.getWriter().executeReadCommand("ftlist");
		String[] information = result[0].split(TS_INFO_SEPERATOR);
		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'ftlist'");
			return resultList;
		}
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
		for (InstanceProperty prop : instanceProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(instanceProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}

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
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();

		String info = query.getWriter().executeReadCommand("permfind permid=" + permissionID)[0];
		if (info.isEmpty())
			return resultList;

		for (String perms : info.split(TS_INFO_SEPERATOR)) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}

		return resultList;
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermission(String permissionName) {
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();

		String info = query.getWriter().executeReadCommand("permfind permsid=" + Formatter.toTsFormat(permissionName))[0];
		if (info.isEmpty())
			return resultList;

		for (String perms : info.split(TS_INFO_SEPERATOR)) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}

		return resultList;
	}

	public Permission getQueryAssignmentsOfPermission(int permissionID) {
		String info = query.getWriter().executeReadCommand("permget permid=" + permissionID)[0];
		if (info.isEmpty())
			return null;
		String permName = get(info, "permsid=");
		int permValue = Integer.parseInt(get(info, "permvalue="));

		return new Permission(permName, permissionID, permValue);
	}

	public Permission getQueryAssignmentsOfPermission(String permissionName) {
		String info = query.getWriter().executeReadCommand("permget permsid=" + Formatter.toTsFormat(permissionName))[0];
		if (info.isEmpty())
			return null;
		int permID = Integer.parseInt(get(info, "permid="));
		int permValue = Integer.parseInt(get(info, "permvalue="));

		return new Permission(permissionName, permID, permValue);
	}

	public List<PermissionAssignmentInfo> getPermOverview(int clientDBID, int channelID) {
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();
		String[] info = query.getWriter().executeReadCommand("permoverview cid=" + channelID + " cldbid=" + clientDBID + " permid=0")[0].split(TS_INFO_SEPERATOR);
		for (String perms : info) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}
		return resultList;
	}

	public PermissionAssignmentInfo getPermOverviewByPermID(int clientDBID, int channelID, int permID) {
		List<PermissionAssignmentInfo> resultList = new ArrayList<>();
		String[] info = query.getWriter().executeReadCommand("permoverview cid=" + channelID + " cldbid=" + clientDBID + " permid=" + permID)[0].split(TS_INFO_SEPERATOR);
		for (String perms : info) {
			resultList.add(new PermissionAssignmentInfo(perms.split(" ")));
		}

		return resultList.get(0);
	}

	public String createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		StringBuilder commandBuilder = new StringBuilder("privilegekeyadd");
		commandBuilder.append(" tokentype=" + keyType.getValue());
		commandBuilder.append(" tokenid1=" + groupID);
		commandBuilder.append(" tokenid2=" + channelID);
		if (description != null)
			commandBuilder.append(" tokendescription=" + Formatter.toTsFormat(description));

		String info = query.getWriter().executeReadCommand(commandBuilder.toString())[0];
		return info.split("token=")[1];
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

	public List<CreatedQueryLogin> getQueryLogins(String pattern, int start, int duration) {
		List<CreatedQueryLogin> resultList = new ArrayList<>();
		StringBuilder commandBuilder = new StringBuilder("queryloginlist");
		if (pattern != null)
			commandBuilder.append(" pattern=" + pattern);

		if (start != -1)
			commandBuilder.append(" start=" + start);

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

		for (VirtualServerProperty prop : virtualServerProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}
		String[] result = query.getWriter().executeReadCommand(commandBuilder.toString());
		if (TSError.isError(result[1], TSError.VIRTUALSERVER_LIMIT_REACHED)) {
			query.debug(DebugOutputType.ERROR, "Limit of virtual servers reached by command: 'servercreate'");
			return null;
		}
		
		String information = result[0];
		if (information.isEmpty())
			return null;

		return new CreatedVirtualServer(information.split(" "));
	}

	public void deleteVirtualServer(int virtualServerID) {
		query.getWriter().executeReadErrorCommand("serverdelete sid=" + virtualServerID);
	}

	public void editVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		StringBuilder commandBuilder = new StringBuilder("serveredit");

		for (VirtualServerProperty prop : virtualServerProperties.keySet()) {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			commandBuilder.append(" " + propName + "=" + setValue);
		}
		query.getWriter().executeReadCommand(commandBuilder.toString());
	}

	public int createServerGroup(String serverGroupName, ServerGroupType groupType) {
		String command = "servergroupadd name=" + Formatter.toTsFormat(serverGroupName) + " type=" + groupType.getValue();
		return Integer.parseInt(query.getWriter().executeReadCommand(command)[0].split("sgid=")[1]);
	}

	public void addClientToServerGroup(int groupID, int clientDBID) {
		query.getWriter().executeReadErrorCommand("servergroupaddclient sgid=" + groupID + " cldbid=" + clientDBID);
	}

	public void addServerGroupPermission(int serverGroupID, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		StringBuilder commandBuilder = new StringBuilder("servergroupaddperm");
		commandBuilder.append(" sgid=" + serverGroupID);
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);
		commandBuilder.append(" permnegated=" + Formatter.toInt(permNegated));
		commandBuilder.append(" permskip=" + Formatter.toInt(permSkip));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void addServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		StringBuilder commandBuilder = new StringBuilder("servergroupautoaddperm");
		commandBuilder.append(" sgtype=" + groupLevel.getValue());
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		commandBuilder.append(" permvalue=" + permissionValue);
		commandBuilder.append(" permnegated=" + Formatter.toInt(permNegated));
		commandBuilder.append(" permskip=" + Formatter.toInt(permSkip));

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void removeServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName) {
		StringBuilder commandBuilder = new StringBuilder("servergroupautodelperm");
		commandBuilder.append(" sgtype=" + groupLevel.getValue());
		if (permissionID != -1)
			commandBuilder.append(" permid=" + permissionID);
		if (permissionName != null)
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public int copyServerGroup(int sourceServerGroupID, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
		String command = "servergroupcopy ssgid=" + sourceServerGroupID + " tsgid=" + targetServerGroupID + " name=" + Formatter.toTsFormat(serverGroupName) + " type="
				+ serverGroupType.getValue();
		return Integer.parseInt(query.getWriter().executeReadCommand(command)[0].split("sgid=")[1]);
	}

	public void deleteServerGroup(int serverGroupID, boolean force) {
		query.getWriter().executeReadErrorCommand("servergroupdel sgid=" + serverGroupID + " force=" + Formatter.toInt(force));
	}

	public void removeClientFromServerGroup(int serverGroupID, int clientDBID) {
		query.getWriter().executeReadErrorCommand("servergroupdelclient sgid=" + serverGroupID + " cldbid=" + clientDBID);
	}

	public void renameServerGroup(int serverGroupID, String name) {
		query.getWriter().executeReadErrorCommand("servergrouprename sgid=" + serverGroupID + " name=" + Formatter.toTsFormat(name));
	}

	public void removeServerGroupPermission(int serverGroupID, int permissionID, String permissionName) {
		StringBuilder commandBuilder = new StringBuilder("servergroupdelperm");
		commandBuilder.append(" sgid=" + serverGroupID);
		if (permissionID != -1) {
			commandBuilder.append(" permid=" + permissionID);
		}

		if (permissionName != null) {
			commandBuilder.append(" permsid=" + Formatter.toTsFormat(permissionName));
		}

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void addVirtualServerTempPassword(String password, String description, long duration, int targetChannelID, String targetChannelPassword) {
		StringBuilder commandBuilder = new StringBuilder("servertemppasswordadd");
		commandBuilder.append(" pw=" + Formatter.toTsFormat(password));
		commandBuilder.append(" desc=" + Formatter.toTsFormat(description));
		commandBuilder.append(" duration=" + duration);
		commandBuilder.append(" tcid=" + targetChannelID);
		commandBuilder.append(" tcpw=" + targetChannelPassword);

		query.getWriter().executeReadErrorCommand(commandBuilder.toString());
	}

	public void deleteVirtualServerTempPassword(String password) {
		query.getWriter().executeReadErrorCommand("servertemppassworddel pw=" + Formatter.toTsFormat(password));
	}

	public List<TempPasswordInfo> getVirtualServerTempPasswords() {
		List<TempPasswordInfo> resultList = new ArrayList<>();
		String[] result = query.getWriter().executeReadCommand("servertemppasswordlist");

		if (TSError.isError(result[1], TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : 'servertemppasswordlist'");
			return resultList;
		}
		String[] information = result[0].split(TS_INFO_SEPERATOR);
		for (String tempPassword : information) {
			resultList.add(new TempPasswordInfo(tempPassword.split(" ")));
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
	protected void setConnected(boolean connected) {
		this.connected = connected;
	}
}
