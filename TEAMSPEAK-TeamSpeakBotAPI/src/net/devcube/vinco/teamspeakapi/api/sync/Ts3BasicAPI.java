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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManager;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
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
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture.Transformator;
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
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

/**
 * Reduced Version of the Ts3SyncAPI. Just every Method with its basic Data
 * Types. Not much Method-overriding, just for a better overview.
 */

public class Ts3BasicAPI {

	protected Ts3ServerQuery query;
	protected QueryConfig config;
	protected QueryWriter writer;
	protected CacheManager cache;
	private boolean connected = false;
	protected static final String TS_INFO_SEPARATOR = "\\|";

	/**
	 * Initiation of the Sync API
	 * 
	 * @param query
	 *                  Server query class
	 */
	public Ts3BasicAPI(Ts3ServerQuery query) {
		this.query = query;
		this.writer = query.getWriter();
		this.config = query.getConfig();
		this.cache = query.getCache();
	}

	public String getHelp() {
		return getHelp("help");
	}

	public String getHelp(String command) {
		return writer.executeReadCommand(CommandBuilder.buildHelpCommand(command))[0];
	}

	/**
	 * Logs the client into the server using the provided login credentials.
	 * 
	 * @param username The username for the login.
	 * @param password The password for the login.
	 * @throws QueryLoginException If the login fails due to invalid credentials or a banned query client.
	 */
	public void login(String username, String password) throws QueryLoginException {
		String[] res = writer.executeReadCommand(CommandBuilder.buildLoginCommand(username, password));
		if (TSError.isError(res[1], TSError.OK)) {
			query.debug(DebugOutputType.QUERY, "Logged in successfully");
			query.debug(DebugOutputType.QUERY, "Using Debugs: " + query.getConfig().getDebuglist().toString());
			query.debug(DebugOutputType.QUERY, "Using Caches: " + query.getConfig().getCachingList().toString());
		} else if (TSError.isError(res[1], TSError.QUERY_INVALID_LOGIN)) {
			query.debug(DebugOutputType.ERROR, "Login failed! Invalid loginname or password!");
			throw new QueryLoginException("Invalid loginname or password!");
		} else if (TSError.isError(res[1], TSError.CONNECTION_FAILED_BANNED)) {
			query.debug(DebugOutputType.ERROR, "Login failed! Queryclient is banned!");
			throw new QueryLoginException("Queryclient is banned!");
		}
	}

	/**
	 * Connects the query to the virtual server and changes the Query bot's name.
	 * 
	 * @param serverID
	 *                     ID of the virtual server
	 * @param nickName
	 *                     nickname of the QueryBot
	 */
	public void connectTeamSpeakQuery(int serverID, String nickName) {
		if (isConnected()) {
			query.debug(DebugOutputType.QUERY, "Query is already connected");
			return;
		}
		if (selectVirtualServer(serverID, -1, nickName)) { // select the virtualServer, the query client should connect to
			query.debug(DebugOutputType.QUERY, "Query connected successfully");
			setConnected(true);
		}
	}

	/**
	 * Connects the query to the virtual server. Starts caching for the first time.
	 * At least the server ID or the server port have to be used.
	 * 
	 * @param serverID
	 *                       ID of the virtual Server (set to -1 to ignore)
	 * @param serverPort
	 *                       Port of the virtual Server (set to -1 to ingore)
	 * @param nickName
	 *                       is optional (if null, not used)
	 * 
	 * @return Returns true if the connection to the virtual server was sucessful.
	 */

	public boolean selectVirtualServer(int serverID, int serverPort, String nickName) {
		String cmd = CommandBuilder.buildSelectVirtualServerCommand(serverID, serverPort, nickName);
		String res = writer.executeReadErrorCommand(cmd);
		if (checkError(res, cmd))
			return false;
		/*
		 * Starts cache updater for the first time. After selecting the virtual server.
		 */
		if (!query.getConfig().getCachingList().isEmpty()) {
			this.cache = query.getCache();
			query.getCache().prepareCache();
		}
		return true;
	}
	
	/**
	 * Sends a poke message to a client with the specified ID.
	 * 
	 * @param clientID The ID of the client to poke.
	 * @param message  The message to send in the poke.
	 */
	
	public void pokeClient(int clientID, String message) {
		writer.executeReadErrorCommand(CommandBuilder.buildPokeClientCommand(clientID, message));
	}
	
	/**
	 * Moves a single client to the specified channel.
	 * 
	 * @param clientID  The ID of the client to move.
	 * @param channelID The ID of the channel to move the client to.
	 */
	
	public void moveClient(int clientID, int channelID) {
		moveClientIDs(Collections.singletonList(clientID), channelID);
	}
	
	/**
	 * Moves multiple clients to the specified channel.
	 * 
	 * @param clientIDs The IDs of the clients to move.
	 * @param channelID The ID of the channel to move the clients to.
	 */

	public void moveClientIDs(List<Integer> clientIDs, int channelID) {
		writer.executeReadErrorCommand(CommandBuilder.buildMoveClientsCommand(clientIDs, channelID));
	}

	/**
	 * Sends a text message to all clients on all virtual servers in the Server
	 * instance.
	 * 
	 * @param message
	 *                    Message formatted to teamspeak format
	 */

	public void sendGlobalMessage(String message) {
		writer.executeReadErrorCommand(CommandBuilder.buildSendGlobalMessageCommand(message));
	}
	
	/**
	 * Logs out the current query session.
	 * 
	 * This method sends a logout command to the server using the writer. If the logout command
	 * is executed successfully and the query is currently connected, it sets the connected status
	 * to false and logs a successful logout message.
	 */
	
	public void logout() {
		writer.executeReadErrorCommand(CommandBuilder.buildLogoutCommand());

		if (isConnected()) {
			query.debug(DebugOutputType.QUERY, "Query logged out successful");
			setConnected(false);
		}
	}

	public void quit() {
		writer.executeReadErrorCommand(CommandBuilder.buildQuitCommand());
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
			String[] res = writer.executeReadCommand(CommandBuilder.buildgetQueryInfoCommand());
			if (checkError(res, CommandBuilder.buildgetQueryInfoCommand()))
				return null;
			info = res[0];
		}
		if (info == null)
			return null;
		return new QueryClientInfo(info);
	}

	public int getVirtualServerIDByPort(int port) {
		return executeCommandGetIntResult(CommandBuilder.buildGetVirtualServerIDByPort(port), "server_id=");
	}

	public int getPermissionID(String permissionName) {
		return getPermissionIDsByNames(Collections.singletonList(permissionName))
				.stream().findFirst().orElse(-1);
	}

	public List<Integer> getPermissionIDsByNames(List<String> permissionNames) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetPermissionIDsByNamesCommand(permissionNames), "permid=");
	}

	public String getPermissionName(int permissionID) {
		return executeCommandGetStringPropResult(CommandBuilder.buildGetPermissionNameByIDCommand(permissionID), "permsid=");
	}

	public List<Permission> getPermissionList() {
		List<Permission> resultList = new ArrayList<>();
		String permissions;
		if (config.isPermissionCached()) {
			permissions = cache.getPermissionsList();
		} else {	
			String cmd = CommandBuilder.buildGetPermissionListCommand();
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return resultList;
			permissions = result[0];
		}
		if (permissions == null)
			return resultList;
		for (String permission : permissions.split(TS_INFO_SEPARATOR)) {
			resultList.add(new Permission(permission));
		}
		return resultList;
	}

	public List<Integer> getPermissionIDs(List<Permission> permissions) {
	    return permissions.stream()
	                     .map(Permission::getID)
	                     .collect(Collectors.toList());
	}

	private List<Permission> getPermissionListByCommand(String command) {
		List<Permission> resultList = new ArrayList<>();
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return resultList;

		for (String permission : splitResult(result)) {
			resultList.add(new Permission(permission));
		}
		return resultList;
	}

	public List<Permission> getChannelPermissions(int channelID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelPermissionsCommand(channelID));
	}

	public List<Permission> getClientPermissions(int clientDataBaseID) {
		return getPermissionListByCommand(CommandBuilder.buildGetClientPermissionsCommand(clientDataBaseID));
	}

	public List<Permission> getServerGroupPermissions(int serverGroupID) {
		return getPermissionListByCommand(CommandBuilder.buildGetServerGroupPermissionsCommand(serverGroupID));
	}

	public List<Permission> getChannelGroupPermissions(int channelGroupID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelGroupPermissionsCommand(channelGroupID));
	}

	public List<Permission> getChannelClientPermissions(int channelID, int clientDataBaseID) {
		return getPermissionListByCommand(CommandBuilder.buildGetChannelClientPermissionsCommand(channelID, clientDataBaseID));
	}

	public List<ServerGroupInfo> getServerGroups() {
		List<ServerGroupInfo> resultList = new ArrayList<>();
		String serverGroups;
		if (config.isGroupsCached()) {
			serverGroups = cache.getServerGroupsList();
		} else {
			String cmd = CommandBuilder.buildGetServerGroupsCommand();
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return resultList;
			serverGroups = result[0];
		}
		
		if (serverGroups == null)
			return resultList;
		for (String groups : serverGroups.split(TS_INFO_SEPARATOR)) {
			resultList.add(new ServerGroupInfo(groups));
		}
		return resultList;
	}

	public List<Integer> getServerGroupIDsByClient(int clientDBID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "sgid=");
	}

	public List<String> getServerGroupNamesByClient(int clientDBID) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "name=");
	}

	public List<ChannelGroupInfo> getChannelGroups() {
		List<ChannelGroupInfo> resultList = new ArrayList<>();
		String channelGroups;
		if (config.isGroupsCached()) {
			channelGroups = cache.getChannelGroupsList();
		} else {
			String cmd = CommandBuilder.buildGetChannelGroupsCommand();
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return resultList;
			channelGroups = result[0];
		}
		
		if (channelGroups == null)
			return resultList;
		for (String client : channelGroups.split(TS_INFO_SEPARATOR)) {
			resultList.add(new ChannelGroupInfo(client));
		}
		return resultList;
	}

	public VirtualServerInfo getVirtualServerInfo() {
		String information;
		if (config.isVirtualServerCached()) {
			information = cache.getVirtualServerProperties();
		} else {
			String cmd = CommandBuilder.buildGetServerInfoCommand();
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return null;
			information = result[0];
		}
		if (information == null)
			return null;
		return new VirtualServerInfo(information);
	}

	public ConnectionInfo getConnectionInfo() {
		return executeCommandGetObject(CommandBuilder.buildGetConnectionInfoCommand(), ConnectionInfo::new);
	}
	public String getVersion() {
		return writer.executeReadCommand(CommandBuilder.buildGetVersionCommand())[0];
	}


	public OfflineMessageInfo getOfflineMessage(int messageID) {
		return executeCommandGetObject(CommandBuilder.buildGetOfflineMessageCommand(messageID), OfflineMessageInfo::new);
	}

	/**
	 * Uses only the given information of clientdblist. Less detailed than
	 * databaseclientinfo for each client, because some Information are not provided
	 * by clientdblist.
	 * 
	 * @see Ts3SyncAPI#getClientDBListDetailed() This Method gets more information about each
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
		List<DataBaseClientInfo> resultList = new ArrayList<>();
		String clients;
		if (config.isDataBaseCached()) {
			clients = cache.getDBClientsList();
		} else {
			String cmd = CommandBuilder.buildGetDataBaseClientsCommand(startOffset, limit);
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return resultList;
			clients = result[0];
		}
		if (clients == null)
			return resultList;
		for (String client : clients.split(TS_INFO_SEPARATOR)) {
			resultList.add(new DataBaseClientInfo(client.replace("cldbid", "client_database_id")));
		}
		return resultList;
	}

	public int getDataBaseClientsCount() {
		return executeCommandGetIntResult(CommandBuilder.buildGetDataBaseClientsCountCommand(), "count=");
	}

	public List<Integer> getDataBaseClientIDs() {
		return getDataBaseClientIDs(getDataBaseClients(-1, -1));
	}

	public List<Integer> getDataBaseClientIDs(List<DataBaseClientInfo> clients) {
	    return clients.stream()
	                 .map(DataBaseClientInfo::getClientDataBaseID)
	                 .collect(Collectors.toList());
	}

	public List<Integer> getDataBaseClientIDsByClients(List<ClientInfo> clients) {
	    return clients.stream()
	                 .map(ClientInfo::getClientDataBaseID)
	                 .collect(Collectors.toList());
	}

	public List<OfflineMessageInfo> getOfflineMessages() {
		return executeCommandGetListResult(CommandBuilder.buildGetOfflineMessagesCommand(), OfflineMessageInfo::new);
	}

	public HostInfo getHostInfo() {
		return executeCommandGetObject(CommandBuilder.buildGetHostInfoCommand(), HostInfo::new);
	}

	public int getClientIDByUUID(String clientUUID) {
		return getClientIDsByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(-1);
	}

	public List<Integer> getClientIDsByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientIDsByUUIDsCommand(clientUUIDs), "clid=");
	}

	public String getClientUUIDByID(int clientID) {
		return getClientUUIDsByIDs(Collections.singletonList(clientID)).stream().findFirst().orElse(null);
	}

	public List<String> getClientUUIDsByIDs(List<Integer> clientIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "cluid=");
	}

	public String getClientNameByID(int clientID) {
	    return getClientNamesByIDs(Collections.singletonList(clientID)).stream().findFirst().orElse(null);
	}


	public List<String> getClientNamesByIDs(List<Integer> clientIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "nickname=");
	}

	public String getClientNameByUUID(String clientUUID) {
	    return getClientNamesByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(null);
	}

	public List<String> getClientNamesByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesByUUIDsCommand(clientUUIDs), "name=");
	}

	public String getClientNameByDBID(int clientDataBaseID) {
	    return getClientNamesByDBIDs(Collections.singletonList(clientDataBaseID)).stream().findFirst().orElse(null);
	}

	public List<String> getClientNamesByDBIDs(List<Integer> clientDataBaseIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "name=");
	}

	public String getClientUUIDByDBID(int clientDataBaseID) {
		return getClientUUIDsByDBIDs(Collections.singletonList(clientDataBaseID)).stream().findFirst().orElse(null);
	}

	public List<String> getClientUUIDsByDBIDs(List<Integer> clientDataBaseIDs) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "cluid=");
	}


	public int getClientDataBaseIDByUUID(String clientUUID) {
		return getClientDataBaseIDsByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(-1);
}

	public List<Integer> getClientDataBaseIDsByUUIDs(List<String> clientUUIDs) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientDataBaseIDsByUUIDsCommand(clientUUIDs), "cldbid=");
	}
	
	/**
	 * Retrieves information about a client with the specified ID.
	 * 
	 * If client caching is enabled in the configuration, this method attempts to retrieve
	 * the client information from the cache. If the information is found, it is returned as
	 * a {@code ClientInfo} object. If the client information is not found in the cache, 
	 * {@code null} is returned.
	 * 
	 * If client caching is not enabled, this method retrieves the client information from
	 * the server by calling {@code getClientsByIDs} method with a list containing the 
	 * specified client ID. If client information is retrieved successfully, it is returned
	 * as a {@code ClientInfo} object. If no client is found with the specified ID, 
	 * {@code null} is returned.
	 * 
	 * @param clientID The ID of the client to retrieve information for.
	 * @return A {@code ClientInfo} object containing information about the client, or 
	 *         {@code null} if the client is not found or if an error occurs.
	 */
	
	public ClientInfo getClient(int clientID) {
		if (config.isClientsCached()) {
			String info = cache.getClientInfo(clientID);
			if (info == null)
				return null;
			return new ClientInfo(info.concat(" clid=" + clientID));
		} else {
			return getClientsByIDs(Collections.singletonList(clientID)).stream()
		            .findFirst().orElse(null);
		}
	}

	public List<ClientInfo> getClientsByIDs(List<Integer> clientIDs) {
		List<ClientInfo> resultList = new ArrayList<>();
		String cmd = CommandBuilder.buildGetClientsByIDsCommand(clientIDs);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultList;
		int counter = 0;
		for (String clientInfo : splitResult(result)) {
			resultList.add(new ClientInfo(clientInfo.concat(" clid=" + clientIDs.get(counter))));
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
		List<ClientInfo> resultList = new ArrayList<>();
		String clients;
		if (config.isClientsCached()) {
			clients = cache.getClientsList();
		} else {
			String cmd = CommandBuilder.buildGetClientsCommand();
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return resultList;
			clients = result[0];
		}
		if (clients == null)
			return resultList;
		for (String client : clients.split(TS_INFO_SEPARATOR)) {
			resultList.add(new ClientInfo(client));
		}
		return resultList;
	}
	
	/**
	 * Used getClientIDs on all Clients which are online by
	 * getting them with getClients().
	 * 
	 * @see Ts3BasicAPI#getClients()
	 * @see Ts3BasicAPI#getClientIDs(List)
	 * @return
	 */
	
	public List<Integer> getClientIDs() {
		return getClientIDs(getClients());
	}

	public List<Integer> getClientIDs(List<ClientInfo> clients) {
		return clients.stream()
        .map(ClientInfo::getID)
        .collect(Collectors.toList());
	}

	public DataBaseClientInfo getDataBaseClient(int clientDataBaseID) {
		if (config.isDataBaseCached()) {
			String info = cache.getDBClientInfo(clientDataBaseID);
			if (info == null)
				return null;
			return new DataBaseClientInfo(info);
		} else {
			List<Integer> clientDBIDs = new ArrayList<>();
			clientDBIDs.add(clientDataBaseID);
			return getDataBaseClientsByDBIDs(clientDBIDs).stream().findFirst().orElse(null);
		}
	}

	public List<DataBaseClientInfo> getDataBaseClientsByDBIDs(List<Integer> clientDBIDs) {
		return executeCommandGetListResult(CommandBuilder.buildGetDataBaseClientsByDBIDsCommand(clientDBIDs), DataBaseClientInfo::new);
	}
	
	/**
	 * Retrieves information about a channel with the specified ID.
	 * 
	 * If channel caching is enabled in the configuration, this method attempts to retrieve
	 * the channel information from the cache. If the information is found, it is returned as
	 * a {@code ChannelInfo} object. If the channel information is not found in the cache, 
	 * it queries the server to get the channel information using the channel ID. If the 
	 * information is retrieved successfully, it is returned as a {@code ChannelInfo} object.
	 * If no channel is found with the specified ID or an error occurs during retrieval,
	 * {@code null} is returned.
	 * 
	 * @param channelID The ID of the channel to retrieve information for.
	 * @return A {@code ChannelInfo} object containing information about the channel, or 
	 *         {@code null} if the channel is not found or if an error occurs.
	 */
	
	public ChannelInfo getChannel(int channelID) {
		String info;
		if (config.isChannelsCached()) {
			info = cache.getChannelInfo(channelID);
		} else {
			String cmd = CommandBuilder.buildGetChannelCommand(channelID);
			String[] result = writer.executeReadCommand(cmd);
			if (checkError(result, cmd))
				return null;
			info = result[0];
		}
		if (info == null || info.isEmpty())
			return null;
		return new ChannelInfo(info.concat(" cid=" + channelID));
	}

	public List<ChannelInfo> getChannelsByName(String channelName) {
		return getChannelsByCommand(CommandBuilder.buildGetChannelsByNameCommand(channelName));
	}

	protected List<ChannelInfo> getChannelsByCommand(String command) {
		List<ChannelInfo> resultList = new ArrayList<>();
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return resultList;
		for (String channel : splitResult(result)) {
			resultList.add(new ChannelInfo(channel));
		}
		return resultList;
	}

	/**
	 * Gets information about all Channels. Less detailed, because some Information
	 * are not provided by channellist. For more detailed information use
	 * {@link Ts3SyncAPI#getChannelsDetailed()}
	 * 
	 * @see Ts3SyncAPI#getChannelsDetailed()
	 * 
	 * @return an Empty List if QueryClient has no Permissions.
	 */

	public List<ChannelInfo> getChannels() {
		if (config.isChannelsCached()) {
			List<ChannelInfo> resultList = new ArrayList<>();
			String channels = cache.getChannelsList();
			if (channels == null)
				return resultList;
			
			for (String channel : channels.split(TS_INFO_SEPARATOR)) {
				resultList.add(new ChannelInfo(channel));
			}
			return resultList;
		}
		return getChannelsByCommand(CommandBuilder.buildGetChannelsCommand());
	}

	public List<Integer> getDatabaseIDsByServerGroup(int servergroupID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDatabaseIDsByServerGroupCommand(servergroupID), "cldbid=");
	}

	/**
	 * Provides a Map which contains every Clientdatabaseid as a key and the value
	 * is a list of channelids in which the Clientdatabaseid has the channelgroup.
	 * 
	 * @param channelgroupID
	 * @return [DataBaseID, List[ChannelID]]
	 */

	public Map<Integer, List<Integer>> getDatabaseIDsByChannelGroup(int channelgroupID) {
		Map<Integer, List<Integer>> resultMap = new HashMap<>();
		String cmd = CommandBuilder.buildGetDatabaseIDsByChannelGroupCommand(channelgroupID);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int clientDBID = Integer.parseInt(Formatter.get(user, "cldbid="));
			int channelID = Integer.parseInt(Formatter.get(user, "cid="));
			resultMap.computeIfAbsent(clientDBID, k -> new ArrayList<>()).add(channelID);
		}
		return resultMap;
	}

	/**
	 * Provides a List of DatabaseClient(ID)s which have a specific ChannelGroup in
	 * a specific Channel.
	 * 
	 * @param channelID
	 * @param channelgroupID
	 * @return List[ClientDataBaseID]
	 */

	public List<Integer> getDatabaseIDsByChannelAndGroup(int channelID, int channelgroupID) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDatabaseIDsByChannelAndGroupCommand(channelID, channelgroupID), "cldbid=");
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
		String cmd = CommandBuilder.buildGetChannelGroupsByDatabaseIDCommand(clientDataBaseID);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int channelGroupID = Integer.parseInt(Formatter.get(user, "cgid="));
			int channelID = Integer.parseInt(Formatter.get(user, "cid="));
			resultMap.computeIfAbsent(channelGroupID, k -> new ArrayList<>()).add(channelID);
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
		String cmd = CommandBuilder.buildGetChannelGroupsByChannelIDCommand(channelID);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultMap;
		for (String user : splitResult(result)) {
			if (user.isEmpty())
				continue;
			int clDBID = Integer.parseInt(Formatter.get(user, "cldbid="));
			int cgID = Integer.parseInt(Formatter.get(user, "cgid="));
			resultMap.computeIfAbsent(cgID, k -> new ArrayList<>()).add(clDBID);
		}
		return resultMap;
	}

	public List<ComplainInfo> getComplainsByClient(int clientDataBaseID) {
		return executeCommandGetListResult(CommandBuilder.buildGetComplainsByClientCommand(clientDataBaseID), ComplainInfo::new);
	}

	public List<PrivilegeKeyInfo> getPrivilegeKeys() {
		return executeCommandGetListResult(CommandBuilder.buildGetPrivilegeKeysCommand(), PrivilegeKeyInfo::new);
	}

	public List<ComplainInfo> getComplains() {
		return executeCommandGetListResult(CommandBuilder.buildGetComplainsCommand(), ComplainInfo::new);
	}

	public List<BanInfo> getBans() {
		return executeCommandGetListResult(CommandBuilder.buildGetBansCommand(), BanInfo::new);
	}

	protected List<VirtualServerInfo> getVirtualServersByCommand(String command) {
		return executeCommandGetListResult(command, VirtualServerInfo::new);
	}

	public List<VirtualServerInfo> getVirtualServers() {
		if (config.isVirtualServerCached()) {
			List<VirtualServerInfo> resultList = new ArrayList<>();
			String vservers = cache.getVirtualServerList();
			if (vservers == null)
				return resultList;
			for (String server : vservers.split(TS_INFO_SEPARATOR)) {
				resultList.add(new VirtualServerInfo(server));
			}
			return resultList;
		}
		
		return getVirtualServersByCommand(CommandBuilder.buildGetVirtualServersCommand());
	}

	/**
	 * Adds a bandrule to the server, every argument is optional, but you should use
	 * at least one. Be aware that you could ban more clients than indeeded.
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
		return executeCommandGetIntResult(CommandBuilder.buildAddBanCommand(ip, name, clientUUID, myTSID, banTime, banReason), "banid=");
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
		List<Integer> result = banClientIDs(Collections.singletonList(clientID), banTime, banReason);
		return new int[] { result.get(0), result.get(1) };
	}

	public List<Integer> banClientIDs(List<Integer> clientIDs, long banTime, String banReason) {
		List<Integer> resultList = new ArrayList<>();
		String cmd = CommandBuilder.buildBanClientIDsCommand(clientIDs, banTime, banReason);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultList;
		for (String id : result[0].replace("banid=", "").replace("\n", ",").split(",")) {
			resultList.add(Integer.parseInt(id));
		}
		return resultList;
	}

	public void removeBan(int banID) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveBanCommand(banID));
	}

	public void removeAllBans() {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveAllBansCommand());
	}

	public void startVirtualServer(int virtualServerID) {
		writer.executeReadErrorCommand(CommandBuilder.buildStartVirtualServerCommand(virtualServerID));
	}

	/**
	 * Stops a specified virtual server.
	 * 
	 * @param virtualServerID
	 * @param reasonmsg
	 *                            is optional (if null not used)
	 */

	public void stopVirtualServer(int virtualServerID, String reasonmsg) {
		writer.executeReadErrorCommand(CommandBuilder.buildStopVirtualServerCommand(virtualServerID, reasonmsg));
	}

	public void stopServerProcess(String reasonmsg) {
		writer.executeReadCommand(CommandBuilder.buildStopServerProcessCommand(reasonmsg));
	}

	public String resetPermissions() {
		return executeCommandGetStringPropResult(CommandBuilder.buildResetPermissionsCommand(), "token=");
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
		addChannelPermissions(channelID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public void addChannelPermissions(int channelID, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddChannelPermissionsCommand(channelID, permissions));
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
		addChannelClientPermissions(channelID, clientdataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public void addChannelClientPermissions(int channelID, int clientdataBaseID, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddChannelClientPermissionsCommand(channelID, clientdataBaseID, permissions));
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
		removeChannelClientPermissions(channelID, clientdataBaseID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeChannelClientPermissions(int channelID, int clientdataBaseID, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelClientPermissionsCommand(channelID, clientdataBaseID, permissions, permissionNames));
	}

	public int createChannel(String channelName, Map<ChannelProperty, String> channelProperties) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateChannelCommand(channelName, channelProperties), "cid=");
	}

	public void deleteChannel(int channelID, boolean force) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteChannelCommand(channelID, force));
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
		removeChannelPermissions(channelID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeChannelPermissions(int channelID, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelPermissionsCommand(channelID, permissions, permissionNames));
	}

	public void editChannel(int channelID, Map<ChannelProperty, String> channelProperties) {
		writer.executeReadCommand(CommandBuilder.buildEditChannelCommand(channelID, channelProperties));
	}

	public int createChannelGroup(String channelGroupName, ChannelGroupType channelGroupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateChannelGroupCommand(channelGroupName, channelGroupType), "cgid=");
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
		addChannelGroupPermissions(channelGroupID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
	}

	public void addChannelGroupPermissions(int channelGroupID, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddChannelGroupPermissionsCommand(channelGroupID, permissions));
	}

	public int copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCopyChannelGroupCommand(sourceChannelGroupID, targetChannelGroupID, channelGroupName, channelGroupType), "cgid=");
	}

	public void deleteChannelGroup(int channelGroupID, boolean force) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteChannelGroupCommand(channelGroupID, force));
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
		removeChannelGroupPermissions(channelGroupID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeChannelGroupPermissions(int channelGroupID, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelGroupPermissionsCommand(channelGroupID, permissions, permissionNames));
	}

	public void renameChannelGroup(int channelGroupID, String channelName) {
		writer.executeReadErrorCommand(CommandBuilder.buildRenameChannelGroupCommand(channelGroupID, channelName));
	}

	public void moveChannel(int channelID, int channelParentID, int order) {
		writer.executeReadErrorCommand(CommandBuilder.buildMoveChannelCommand(channelID, channelParentID, order));
	}

	public void addClientPermission(int clientDataBaseID, int permissionID, String permissionName, int permissionValue, boolean permSkip) {
		addClientPermissions(clientDataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permSkip, false)));
	}

	public void addClientPermissions(int clientDataBaseID, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddClientPermissionsCommand(clientDataBaseID, permissions));
	}

	public void deleteDataBaseClient(int clientDataBaseID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteDataBaseClientCommand(clientDataBaseID));
	}

	public void editDataBaseClient(int clientDataBaseID, String description) {
		writer.executeReadErrorCommand(CommandBuilder.buildEditDataBaseClientCommand(clientDataBaseID, description));
	}

	public int getDataBaseClientIDByUUID(String clientUUID) {
		return executeCommandGetIntResult(CommandBuilder.buildGetDataBaseClientIDByUUIDCommand(clientUUID), "cldbid=");
	}

	public List<Integer> getDataBaseClientIDsByName(String clientLastName) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetDataBaseClientIDsByNameCommand(clientLastName), "cldbid=");
	}

	public void removeClientPermission(int clientDataBaseID, int permissionID, String permissionName) {
		removeClientPermissions(clientDataBaseID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeClientPermissions(int clientDataBaseID, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveClientPermissionsCommand(clientDataBaseID, permissions, permissionNames));
	}

	public void editClient(int clientID, Map<ClientProperty, String> clientProperties) {
		writer.executeReadErrorCommand(CommandBuilder.buildEditClientCommand(clientID, clientProperties));
	}

	public List<Integer> getClientIDsByName(String clientName) {
		return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientIDsByNameCommand(clientName), "clid=");
	}

	public void kickClientFromServer(int clientID, String reason) {
		kickClientIDs(Collections.singletonList(clientID), 5, reason);
	}

	public void kickClientIDs(List<Integer> clientIDs, int reasonID, String reason) {
		writer.executeReadErrorCommand(CommandBuilder.buildKickClientIDsCommand(clientIDs, reasonID, reason));
	}

	public void kickClientFromChannel(int clientID, String reason) {
		kickClientIDs(Collections.singletonList(clientID), 4, reason);
	}

	public String updateServerQueryLogin(String username) {
		return executeCommandGetStringPropResult(CommandBuilder.buildUpdateServerQueryLoginCommand(username), "client_login_password=");
	}

	public void updateQueryName(String queryName) {
		if (config.isQueryCached()) {
			String info = cache.getQueryProperties();
			cache.updateQueryPropsCache(cache.updateAttribute(info, "client_nickname=", Formatter.toTsFormat(queryName)));
		}

		writer.executeReadErrorCommand(CommandBuilder.buildUpdateQueryNameCommand(queryName));
	}

	public void addComplain(int clientDBID, String message) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddComplainCommand(clientDBID, message));
	}

	public void deleteComplain(int clientDBID, int fromClientDBID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteComplainCommand(clientDBID, fromClientDBID));
	}

	public void deleteAllComplains(int clientDBID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteAllComplainsCommand(clientDBID));
	}

	public Map<String, String> getCustomInfo(int clientDBID) {
		Map<String, String> resultMap = new HashMap<>();
		String cmd = CommandBuilder.buildGetCustomInfoCommand(clientDBID);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultMap;

		for (String info : splitResult(result)) {
			String key = Formatter.toNormalFormat(Formatter.get(info, "ident="));
			String value = Formatter.toNormalFormat(Formatter.get(info, "value="));
			resultMap.put(key, value);
		}
		return resultMap;
	}

	public Map<Integer, List<String>> searchDBIDsCustomInfo(String ident, String pattern) {
		Map<Integer, List<String>> resultMap = new HashMap<>();
		String cmd = CommandBuilder.buildSearchDBIDsCustomInfoCommand(ident, pattern);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultMap;

		for (String info : splitResult(result)) {
			int clDBID = Integer.parseInt(Formatter.get(info, "cldbid="));
			String value = Formatter.toNormalFormat(Formatter.get(info, "value="));
			resultMap.computeIfAbsent(clDBID, k -> new ArrayList<>()).add(value);
		}
		return resultMap;
	}

	public void setCustomInfo(int clientDBID, String ident, String value) {
		writer.executeReadErrorCommand(CommandBuilder.buildSetCustomInfoCommand(clientDBID, ident, value));
	}

	public void deleteCustomInfo(int clientDBID, String ident) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteCustomInfoCommand(clientDBID, ident));
	}

	public void createFileDirectory(int channelID, String channelPassword, String dirName) {
		writer.executeReadErrorCommand(CommandBuilder.buildCreateFileDirectoryCommand(channelID, channelPassword, dirName));
	}

	public void deleteFile(int channelID, String channelPassword, String fileName) {
		deleteFiles(channelID, channelPassword, Collections.singletonList(fileName));
	}

	public void deleteFiles(int channelID, String channelPassword, List<String> fileNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteFilesCommand(channelID, channelPassword, fileNames));
	}

	public FileInfo getFileInfo(int channelID, String channelPassword, String fileName) {
		return getFileInfos(channelID, channelPassword, Collections.singletonList(fileName)).stream()
	            .findFirst().orElse(null);
	}

	public List<FileInfo> getFileInfos(int channelID, String channelPassword, List<String> fileNames) {
		List<FileInfo> resultList = new ArrayList<>();
		String cmd = CommandBuilder.buildGetFileInfosCommand(channelID, channelPassword, fileNames);
		String[] result = writer.executeReadCommand(cmd);
		if (checkError(result, cmd))
			return resultList;
		for (String files : splitResult(result)) {
			resultList.add(new FileInfo(files.concat(" cid=" + channelID)));
		}
		return resultList;
	}

	public List<FileInfo> getChannelFilesByPath(int channelID, String channelPassword, String filePath) {
		List<FileInfo> resultList = new ArrayList<>();
		String cmd = CommandBuilder.buildGetChannelFilesByPathCommand(channelID, channelPassword, filePath);
		String[] result = writer.executeReadCommand(cmd);
		String[] information = splitResult(result);
		if (checkError(result, cmd))
			return resultList;
		String path = Formatter.get(information[0], "path=");
		for (String info : information) {
			resultList.add(new FileInfo(info.concat(" cid=" + channelID).concat(" path=" + path)));
		}
		return resultList;
	}

	public List<FileTransferInfo> getFileTransfers() {
		return executeCommandGetListResult(CommandBuilder.buildGetFileTransfersCommand(), FileTransferInfo::new);
	}

	public void renameFile(int channelID, String channelPassword, String oldFilePath, String newFilePath) {
		writer.executeReadErrorCommand(CommandBuilder.buildRenameFileCommand(channelID, channelPassword, oldFilePath, newFilePath));
	}

	public void moveFile(int channelID, String channelPassword, String oldFilePath, int newChannelID, String newChannelPassword, String newFilePath) {
		writer.executeReadErrorCommand(CommandBuilder.buildMoveFileCommand(channelID, channelPassword, oldFilePath, newChannelID, newChannelPassword, newFilePath));
	}

	public void stopFileTransfer(int serverFileTransferID, boolean delete) {
		writer.executeReadErrorCommand(CommandBuilder.buildStopFileTransferCommand(serverFileTransferID, delete));
	}

	public void editInstance(Map<InstanceProperty, String> instanceProperties) {
		writer.executeReadCommand(CommandBuilder.buildEditInstanceCommand(instanceProperties));
	}

	public InstanceInfo getInstanceInfo() {
		return executeCommandGetObject(CommandBuilder.buildGetInstanceInfoCommand(), InstanceInfo::new);
	}

	public void addToLog(LogLevel logLevel, String logMessage) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddToLogCommand(logLevel, logMessage));
	}
	
	/**
	 * Gets a specified number of entries from the servers log
	 * 
	 * @param lines Amount of entries should be returned. Between [1,100] (100 Default)
	 * @param reverse if enabled the order is reversed
	 * @param instance if enabled log is returned from the instance, otherwise from the virtualserver.
	 * @param beginPos amout of lines that should be skipped from the start.
	 * @return List of Log entries from the servers log.
	 */
	
	public List<String> getLog(int lines, boolean reverse, boolean instance, int beginPos) {
		return executeCommandGetListStringPropResult(CommandBuilder.buildGetLogCommand(lines, reverse, instance, beginPos), "l=");
	}

	public void sendOfflineMessage(String clientUUID, String subject, String message) {
		writer.executeReadErrorCommand(CommandBuilder.buildSendOfflineMessageCommand(clientUUID, subject, message));
	}

	public void deleteOfflineMessage(int offlineMessageID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteOfflineMessageCommand(offlineMessageID));
	}

	public void updateOfflineMessageFlag(int offlineMessageID, boolean flag) {
		writer.executeReadErrorCommand(CommandBuilder.buildUpdateOfflineMessageFlagCommand(offlineMessageID, flag));
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermission(int permissionID) {
		return getAssignmentsOfPermissions(Collections.singletonList(permissionID), new ArrayList<>());
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermission(String permissionName) {
		return getAssignmentsOfPermissions(new ArrayList<>(), Collections.singletonList(permissionName));
	}

	public List<PermissionAssignmentInfo> getAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {
		return executeCommandGetListResult(CommandBuilder.buildGetAssignmentsOfPermissionsCommand(permissions, permissionNames), PermissionAssignmentInfo::new);
	}

	public Permission getQueryAssignmentOfPermission(int permissionID) {
		return getQueryAssignmentsOfPermissions(Collections.singletonList(permissionID), new ArrayList<>())
				.stream().findFirst().orElse(null);
	}

	public Permission getQueryAssignmentOfPermission(String permissionName) {
		return getQueryAssignmentsOfPermissions(new ArrayList<>(), Collections.singletonList(permissionName))
				.stream().findFirst().orElse(null);
	}

	public List<Permission> getQueryAssignmentsOfPermissions(List<Integer> permissions, List<String> permissionNames) {
		return getPermissionListByCommand(CommandBuilder.buildGetQueryAssignmentsOfPermissionsCommand(permissions, permissionNames));
	}

	public List<PermissionAssignmentInfo> getPermOverview(int clientDBID, int channelID, int permID) {
		return executeCommandGetListResult(CommandBuilder.buildGetPermOverviewCommand(clientDBID, channelID, permID), PermissionAssignmentInfo::new);
	}

	public PermissionAssignmentInfo getPermOverviewByPermID(int clientDBID, int channelID, int permID) {
		return getPermOverview(clientDBID, channelID, permID).get(0);
	}

	public String createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		return executeCommandGetStringPropResult(CommandBuilder.buildCreatePrivilegeKeyCommand(keyType, groupID, channelID, description), "token=");
	}

	public void deletePrivilegeKey(String privilegeKey) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeletePrivilegeKeyCommand(privilegeKey));
	}

	public void usePrivilegeKey(String privilegeKey) {
		writer.executeReadErrorCommand(CommandBuilder.buildUsePrivilegeKeyCommand(privilegeKey));
	}

	public CreatedQueryLogin createQueryLogin(String loginName, int clientDBID) {
		return executeCommandGetObject(CommandBuilder.buildCreateQueryLoginCommand(loginName, clientDBID), CreatedQueryLogin::new);
	}

	public void deleteQueryLogin(int clientDBID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteQueryLoginCommand(clientDBID));
	}

	/**
	 * List existing query client logins.
	 * 
	 * @param pattern
	 *                        Filter for client login name (set to null to ignore)
	 * @param startOffset
	 *                        Integer to skip the first `n` entries (set to -1 to
	 *                        ignore)
	 * @param duration
	 *                        Integer to only return the first `n` entries (set to
	 *                        -1 to ignore)
	 * @return List of Query logins
	 */

	public List<CreatedQueryLogin> getQueryLogins(String pattern, int startOffset, int duration) {
		return executeCommandGetListResult(CommandBuilder.buildGetQueryLoginsCommand(pattern, startOffset, duration), CreatedQueryLogin::new);
	}

	public void sendTextMessage(TextMessageType messageType, int clientID, String message) {
		writer.executeReadErrorCommand(CommandBuilder.buildSendTextMessageCommand(messageType, clientID, message));
	}

	public CreatedVirtualServer createVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		return executeCommandGetObject(CommandBuilder.buildCreateVirtualServerCommand(virtualServerProperties), CreatedVirtualServer::new);
	}

	public void deleteVirtualServer(int virtualServerID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteVirtualServerCommand(virtualServerID));
	}

	public void editVirtualServer(Map<VirtualServerProperty, String> virtualServerProperties) {
		writer.executeReadCommand(CommandBuilder.buildEditVirtualServerCommand(virtualServerProperties));
	}

	public int createServerGroup(String serverGroupName, ServerGroupType groupType) {
		return executeCommandGetIntResult(CommandBuilder.buildCreateServerGroupCommand(serverGroupName, groupType), "sgid=");
	}

	public void addClientToServerGroup(int groupID, int clientDBID) {
		addClientDBIDsToServerGroup(groupID, Collections.singletonList(clientDBID));
	}

	public void addClientDBIDsToServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddClientDBIDsToServerGroupCommand(serverGroupID, dataBaseClientIDs));
	}

	public void addServerGroupPermission(int serverGroupID, int permissionID, String permissionName, int permissionValue, boolean permNegated, boolean permSkip) {
		addServerGroupPermissions(serverGroupID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null)));
	}

	public void addServerGroupPermissions(int serverGroupID, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddServerGroupPermissionsCommand(serverGroupID, permissions));
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
		addServerGroupAutoPermissions(groupLevel, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, permNegated, permSkip, null)));
	}

	public void addServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Permission> permissions) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddServerGroupAutoPermissionsCommand(groupLevel, permissions));
	}

	public void removeServerGroupAutoPermission(ServerGroupLevel groupLevel, int permissionID, String permissionName) {
		removeServerGroupAutoPermissions(groupLevel, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeServerGroupAutoPermissions(ServerGroupLevel groupLevel, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveServerGroupAutoPermissionsCommand(groupLevel, permissions, permissionNames));
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
		return executeCommandGetIntResult(CommandBuilder.buildCopyServerGroupCommand(sourceServerGroupID, targetServerGroupID, serverGroupName, serverGroupType), "sgid=");
	}

	public void deleteServerGroup(int serverGroupID, boolean force) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteServerGroupCommand(serverGroupID, force));
	}

	public void removeClientFromServerGroup(int serverGroupID, int clientDBID) {
		removeClientDBIDsFromServerGroup(serverGroupID, Collections.singletonList(clientDBID));
	}

	public void removeClientDBIDsFromServerGroup(int serverGroupID, List<Integer> dataBaseClientIDs) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveClientDBIDsFromServerGroupCommand(serverGroupID, dataBaseClientIDs));
	}

	public void renameServerGroup(int serverGroupID, String name) {
		writer.executeReadErrorCommand(CommandBuilder.buildRenameServerGroupCommand(serverGroupID, name));
	}

	public void removeServerGroupPermission(int serverGroupID, int permissionID, String permissionName) {
		removeServerGroupPermissions(serverGroupID, Collections.singletonList(permissionID), Collections.singletonList(permissionName));
	}

	public void removeServerGroupPermissions(int serverGroupID, List<Integer> permissions, List<String> permissionNames) {
		writer.executeReadErrorCommand(CommandBuilder.buildRemoveServerGroupPermissionsCommand(serverGroupID, permissions, permissionNames));
	}

	public void addVirtualServerTempPassword(String password, String description, long duration, int targetChannelID, String targetChannelPassword) {
		writer.executeReadErrorCommand(CommandBuilder.buildAddVirtualServerTempPasswordCommand(password, description, duration, targetChannelID, targetChannelPassword));
	}

	public void deleteVirtualServerTempPassword(String password) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteVirtualServerTempPasswordCommand(password));
	}

	public List<TempPasswordInfo> getVirtualServerTempPasswords() {
		return executeCommandGetListResult(CommandBuilder.buildGetVirtualServerTempPasswordsCommand(), TempPasswordInfo::new);
	}

	public CreatedSnapshot createSnapshot(String password) {
		return executeCommandGetObject(CommandBuilder.buildCreateSnapshotCommand(password), CreatedSnapshot::new);
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
		return executeCommandGetObject(CommandBuilder.buildAddAPIKeyCommand(scope, lifetime, clientDBID), CreatedAPIKey::new);
	}

	public void deleteAPIKey(int keyID) {
		writer.executeReadErrorCommand(CommandBuilder.buildDeleteAPIKeyCommand(keyID));
	}

	/**
	 * Lists all apikeys owned by the user.
	 * 
	 * @param clientDBID
	 *                        ClientDataBaseID (use -1 to ingore, use 0 to list all
	 *                        clients)
	 * @param startOffset
	 *                        To skip the first 'n' entries.
	 * @param limit
	 *                        Return only the first 'n' entries.
	 * @return List of APIKeys
	 */

	public List<CreatedAPIKey> getAPIKeys(int clientDBID, int startOffset, int limit) {
		return executeCommandGetListResult(CommandBuilder.buildGetAPIKeysCommand(clientDBID, startOffset, limit), CreatedAPIKey::new);
	}

	public void setClientChannelGroup(int channelGroupID, int channelID, int clientDBID) {
		writer.executeReadErrorCommand(CommandBuilder.buildSetClientChannelGroupCommand(channelGroupID, channelID, clientDBID));
	}

	public void registerEvent(EventType eventType, int channelID) {
		writer.executeReadErrorCommand(CommandBuilder.buildRegisterEventCommand(eventType, channelID));
	}

	public void unRegisterAllEvents() {
		writer.executeReadErrorCommand(CommandBuilder.buildUnRegisterAllEventsCommand());
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
	
	public <T> T executeCommandGetObject(String command, Transformator<T> transformator) {
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return null;
		return transformator.transformResult(result[0]);
	}
	
	private int executeCommandGetIntResult(String command, String property) {
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return -1;
		return Integer.parseInt(Formatter.get(result[0], property));
	}
	
	private String executeCommandGetStringPropResult(String command, String property) {
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return null;
		return Formatter.get(result[0], property);
	}
	
	private <T> List<T> executeCommandGetListResult(String command, Transformator<T> transformator) {
		List<T> resultList = new ArrayList<>();
		String[] result = writer.executeReadCommand(command);
		if (checkError(result, command))
			return resultList;
		
		for (String info : splitResult(result)) {
			if (!info.isBlank())
				resultList.add(transformator.transformResult(info));
		}
		return resultList;
	}
	
	private <T> List<Integer> executeCommandGetListIntPropResult(String command, String property) {
		return executeCommandGetListResult(command, new Transformator<Integer>() {

			@Override
			public Integer transformResult(String result) {
				return Integer.parseInt(Formatter.get(result, property));
			}
		});
	}

	private List<String> executeCommandGetListStringPropResult(String command, String property) {
		return executeCommandGetListResult(command, new Transformator<String>() {

			@Override
			public String transformResult(String result) {
				return Formatter.toNormalFormat(Formatter.get(result, property));
			}
		});
	}
	
	private boolean checkError(String result, String cmd) {
		boolean error = false;

		if (TSError.isError(result, TSError.DATABASE_EMPTY_RESULT)) {
			query.debug(DebugOutputType.WARNING, "Database was empty for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.INSUFFICIENT_CLIENT_PERMISSIONS)) {
			query.debug(DebugOutputType.ERROR, "Insufficient client permissions for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.PARAMETER_NOT_FOUND)) {
			query.debug(DebugOutputType.WARNING, "Parameter not found for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.INVALID_CLIENT_ID.getValue())) {
			query.debug(DebugOutputType.WARNING, "ClientUUID was invalid for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.CHANNEL_NAME_IS_ALEARY_IN_USE)) {
			query.debug(DebugOutputType.WARNING, "Channel could not be created! Channelname is already in use!");
			error = true;
		} else if (TSError.isError(result, TSError.FILE_IO_ERROR)) {
			query.debug(DebugOutputType.WARNING, "File input/output error for command : '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.VIRTUALSERVER_LIMIT_REACHED)) {
			query.debug(DebugOutputType.ERROR, "Limit of virtual servers reached by command: '" + cmd + "'");
			error = true;
		} else if (TSError.isError(result, TSError.INVALID_PERMISSION_ID)) {
			query.debug(DebugOutputType.WARNING, "Permission could not be found by command: '" + cmd + "'");
			error = true;
		}

		return error;
	}

	private boolean checkError(String[] result, String cmd) {
		return checkError(result[1], cmd);
	}

	private String[] splitResult(String[] result) {
		return result[0].split(TS_INFO_SEPARATOR);
	}

}
