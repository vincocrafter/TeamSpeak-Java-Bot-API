/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 24.07.2023
 * <p>
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
import net.devcube.vinco.teamspeakapi.api.api.util.*;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture.Transformator;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.BanInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroupInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ComplaintInfo;
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
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

/**
 * Reduced Version of the Ts3SyncAPI. Just every Method with its basic Data
 * Types. Not much Method-overriding, just for a better overview.
 */

public class Ts3BasicAPI {

    protected static final String TS_INFO_SEPARATOR = "\\|";
    protected QueryConfig config;
    protected Logger logger;
    protected QueryWriter writer;
    protected CacheManager cache;
    private boolean connected = false;

    public Ts3BasicAPI(QueryConfig config, Logger logger, QueryWriter writer, CacheManager cache) {
        this.config = config;
        this.logger = logger;
        this.writer = writer;
        this.cache = cache;
    }

    /**
     * Returns the help message for the default command.
     * <p>
     * This method is a convenience method that calls the overloaded getHelp(String command) method
     * with the default command "help". The returned help message is the response from the server
     * to the "help help" command.
     *
     * @return A string containing the help message for the default command.
     */
    public String getHelp() {
        return getHelp("help");
    }

    /**
     * Returns the help message for the specified command.
     * <p>
     * This method sends a help command for the specified command to the server and returns the
     * response. The help command is built using the CommandBuilder.buildHelpCommand(command) method.
     *
     * @param command The command to get help for.
     * @return A string containing the help message for the specified command.
     */
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
            logger.debug(DebugOutputType.QUERY, "Logged in successfully");
            logger.debug(DebugOutputType.QUERY, "Using Debugs: " + config.getDebugList().toString());
            logger.debug(DebugOutputType.QUERY, "Using Caches: " + config.getCachingList().toString());
        } else if (TSError.isError(res[1], TSError.QUERY_INVALID_LOGIN)) {
            logger.debug(DebugOutputType.ERROR, "Login failed! Invalid loginname or password!");
            throw new QueryLoginException("Invalid loginname or password!");
        } else if (TSError.isError(res[1], TSError.CONNECTION_FAILED_BANNED)) {
            logger.debug(DebugOutputType.ERROR, "Login failed! Queryclient is banned!");
            throw new QueryLoginException("Queryclient is banned!");
        }
    }

    /**
     * Connects the query to the virtual server and changes the Query bot's name.
     *
     * @param serverID ID of the virtual server
     * @param nickName nickname of the QueryBot
     */
    public void connectTeamSpeakQuery(int serverID, String nickName) {
        if (isConnected()) {
            logger.debug(DebugOutputType.QUERY, "Query is already connected");
            return;
        }
        if (selectVirtualServer(serverID, -1, nickName)) { // select the virtualServer, the query client should connect to
            logger.debug(DebugOutputType.QUERY, "Query connected successfully");
            setConnected(true);
        }
    }

    /**
     * Connects the query to the virtual server and optionally starts caching.
     * At least the server ID or the server port have to be used.
     *
     * @param serverID   ID of the virtual Server. If set to -1, this parameter is ignored.
     * @param serverPort Port of the virtual Server. If set to -1, this parameter is ignored.
     * @param nickName   Nickname for the Query bot. This parameter is optional and can be null.
     * @return Returns true if the connection to the virtual server was successful.
     */
    public boolean selectVirtualServer(int serverID, int serverPort, String nickName) {
        String cmd = CommandBuilder.buildSelectVirtualServerCommand(serverID, serverPort, nickName);
        String res = writer.executeReadErrorCommand(cmd);
        if (checkError(res, cmd))
            return false;

        /*
         * If caching is enabled in the configuration, prepare the cache.
         * This is done after successfully selecting the virtual server.
         */
        if (!config.getCachingList().isEmpty()) {
            cache.prepareCache();
        }

        // If there was no error and caching (if enabled) was successful, return true
        return true;
    }

    /**
     * Sends a poke message to a client with the specified ID.
     * A poke message is a direct message that appears as a popup on the client's TeamSpeak interface.
     *
     * @param clientID The ID of the client to poke. This is an integer value representing the unique identifier of the client.
     * @param message  The message to send in the poke. This is a string value representing the content of the poke message.
     */
    public void pokeClient(int clientID, String message) {
        writer.executeReadErrorCommand(CommandBuilder.buildPokeClientCommand(clientID, message));
    }

    /**
     * Moves a single client to the specified channel.
     * This method is a convenience method that calls the overloaded moveClientIDs(List<Integer> clientIDs, int channelID) method
     * with a list containing the single client ID.
     *
     * @param clientID  The ID of the client to move. This is an integer value representing the unique identifier of the client.
     * @param channelID The ID of the channel to move the client to. This is an integer value representing the unique identifier of the channel.
     */
    public void moveClient(int clientID, int channelID) {
        moveClientIDs(Collections.singletonList(clientID), channelID);
    }

    /**
     * Moves multiple clients to the specified channel.
     * This method sends a command to the server to move the specified clients to the specified channel.
     *
     * @param clientIDs The IDs of the clients to move. This is a list of integer values representing the unique identifiers of the clients.
     * @param channelID The ID of the channel to move the clients to. This is an integer value representing the unique identifier of the channel.
     */
    public void moveClientIDs(List<Integer> clientIDs, int channelID) {
        String cmd = CommandBuilder.buildMoveClientsCommand(clientIDs, channelID);
        String res = writer.executeReadErrorCommand(cmd);

        if (checkError(res, cmd))
            return;

        if (config.isQueryCached() && clientIDs.contains(getQueryInfo().getClientID())) {
            cache.updateQueryPropsCache(cache.updateAttribute(cache.getQueryProperties(),
                    "client_channel_id", String.valueOf(channelID)));
        }
    }

    /**
     * Sends a text message to all clients on all virtual servers in the Server
     * instance.
     *
     * @param message Message formatted to teamspeak format
     */

    public void sendGlobalMessage(String message) {
        writer.executeReadErrorCommand(CommandBuilder.buildSendGlobalMessageCommand(message));
    }

    /**
     * Logs out the current query session and deselects a virtual server.
     * <p>
     * This method sends a logout command to the server using the writer. If the logout command
     * is executed successfully and the query is currently connected, it sets the connected status
     * to false and logs a successful logout message.
     */

    public void logout() {
        writer.executeReadErrorCommand(CommandBuilder.buildLogoutCommand());

        if (isConnected()) {
            logger.debug(DebugOutputType.QUERY, "Query logged out successful");
            setConnected(false);
        }
    }

    public void quit() {
        writer.executeReadErrorCommand(CommandBuilder.buildQuitCommand());

        if (isConnected()) {
            logger.debug(DebugOutputType.QUERY, "Query logged out successful");
            setConnected(false);
        }
    }

    /**
     * Retrieves information about the Query Client.
     * <p>
     * This method first checks if the query information is cached. If it is, it retrieves the information from the cache.
     * If the query information is not cached, it sends a command to the server to get the query information.
     *
     * @return A QueryClientInfo object containing information about the query client, or null if an error occurs or if 'info' is null.
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

    /**
     * Retrieves the ID of the virtual server by its port.
     * <p>
     * This method sends a command to the server to get the ID of the virtual server that is running on the specified port.
     *
     * @param port The port number of the virtual server.
     * @return The ID of the virtual server running on the specified port, or -1 if an error occurs.
     */
    public int getVirtualServerIDByPort(int port) {
        return executeCommandGetIntResult(CommandBuilder.buildGetVirtualServerIDByPort(port), "server_id=");
    }

    /**
     * Retrieves the ID of a permission by its name.
     * <p>
     * This method sends a command to the server to get the ID of the permission with the specified name.
     *
     * @param permissionName The name of the permission.
     * @return The ID of the permission with the specified name, or -1 if the permission is not found or an error occurs.
     */
    public int getPermissionID(String permissionName) {
        return getPermissionIDsByNames(Collections.singletonList(permissionName))
                .stream().findFirst().orElse(-1);
    }

    /**
     * Retrieves the IDs of multiple permissions by their names.
     * <p>
     * This method sends a command to the server to get the IDs of the permissions with the specified names.
     *
     * @param permissionNames The names of the permissions.
     * @return A list of IDs of the permissions with the specified names. If a permission is not found or an error occurs, its ID will not be included in the list.
     */
    public List<Integer> getPermissionIDsByNames(List<String> permissionNames) {
        return executeCommandGetListIntPropResult(CommandBuilder.buildGetPermissionIDsByNamesCommand(permissionNames), "permid=");
    }

    /**
     * Retrieves the name of a permission by its ID.
     * <p>
     * This method sends a command to the server to get the name of the permission with the specified ID.
     *
     * @param permissionID The ID of the permission.
     * @return The name of the permission with the specified ID, or null if the permission is not found or an error occurs.
     */
    public String getPermissionName(int permissionID) {
        return executeCommandGetStringPropResult(CommandBuilder.buildGetPermissionNameByIDCommand(permissionID), "permsid=");
    }

    /**
     * Retrieves a list of permissions.
     * <p>
     * This method first checks if the permissions are cached. If they are, it retrieves the permissions from the cache.
     * If the permissions are not cached, it sends a command to the server to get the permissions.
     *
     * @return A list of Permission objects representing the permissions. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getPermissionList() {
        List<Permission> resultList = new ArrayList<>(496);
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

    /**
     * Retrieves the IDs of the specified permissions.
     * <p>
     * This method iterates over the list of specified permissions and retrieves their IDs.
     *
     * @param permissions The list of permissions to get the IDs for.
     * @return A list of integers representing the IDs of the specified permissions.
     */
    public List<Integer> getPermissionIDs(List<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getID)
                .collect(Collectors.toList());
    }

    public List<Integer> getPermissionIDs() {
        if (config.isPermissionCached()) {
            String permIDs = cache.getPermissionsIDsList();
            List<Integer> idList = new ArrayList<>();
            for (String idStr : permIDs.toString().split(" ")) {
                idList.add(Integer.parseInt(idStr));
            }
            return idList;
        } else {
            return getPermissionIDs(getPermissionList());
        }
    }

    /**
     * Retrieves a list of permissions by sending a command to the server.
     * <p>
     * This method sends a command to the server to get the permissions.
     *
     * @param command The command to send to the server.
     * @return A list of Permission objects representing the permissions. If an error occurs or no permissions are found, an empty list is returned.
     */
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

    /**
     * Retrieves a list of permissions for a specific channel.
     * <p>
     * This method sends a command to the server to get the permissions for the specified channel.
     *
     * @param channelID The ID of the channel to get the permissions for.
     * @return A list of Permission objects representing the permissions for the specified channel. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getChannelPermissions(int channelID) {
        return getPermissionListByCommand(CommandBuilder.buildGetChannelPermissionsCommand(channelID));
    }

    /**
     * Retrieves a list of permissions for a specific client.
     * <p>
     * This method sends a command to the server to get the permissions for the specified client.
     *
     * @param clientDataBaseID The database ID of the client to get the permissions for.
     * @return A list of Permission objects representing the permissions for the specified client. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getClientPermissions(int clientDataBaseID) {
        return getPermissionListByCommand(CommandBuilder.buildGetClientPermissionsCommand(clientDataBaseID));
    }

    /**
     * Retrieves a list of permissions for a specific server group.
     * <p>
     * This method sends a command to the server to get the permissions for the specified server group.
     *
     * @param serverGroupID The ID of the server group to get the permissions for.
     * @return A list of Permission objects representing the permissions for the specified server group. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getServerGroupPermissions(int serverGroupID) {
        return getPermissionListByCommand(CommandBuilder.buildGetServerGroupPermissionsCommand(serverGroupID));
    }

    /**
     * Retrieves a list of permissions for a specific channel group.
     * <p>
     * This method sends a command to the server to get the permissions for the specified channel group.
     *
     * @param channelGroupID The ID of the channel group to get the permissions for.
     * @return A list of Permission objects representing the permissions for the specified channel group. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getChannelGroupPermissions(int channelGroupID) {
        return getPermissionListByCommand(CommandBuilder.buildGetChannelGroupPermissionsCommand(channelGroupID));
    }

    /**
     * Retrieves a list of permissions for a specific client in a specific channel.
     * <p>
     * This method sends a command to the server to get the permissions for the specified client in the specified channel.
     *
     * @param channelID        The ID of the channel to get the permissions for.
     * @param clientDataBaseID The database ID of the client to get the permissions for.
     * @return A list of Permission objects representing the permissions for the specified client in the specified channel. If an error occurs or no permissions are found, an empty list is returned.
     */
    public List<Permission> getChannelClientPermissions(int channelID, int clientDataBaseID) {
        return getPermissionListByCommand(CommandBuilder.buildGetChannelClientPermissionsCommand(channelID, clientDataBaseID));
    }

    /**
     * Retrieves a list of server groups.
     * <p>
     * This method first checks if the server groups are cached. If they are, it retrieves the server groups from the cache.
     * If the server groups are not cached, it sends a command to the server to get the server groups.
     *
     * @return A list of ServerGroupInfo objects representing the server groups. If an error occurs or no server groups are found, an empty list is returned.
     */
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

    /**
     * Retrieves a list of server group IDs for a specific client.
     * <p>
     * This method sends a command to the server to get the server group IDs for the specified client.
     *
     * @param clientDBID The database ID of the client to get the server group IDs for.
     * @return A list of integers representing the server group IDs. If an error occurs or no server group IDs are found, an empty list is returned.
     */
    public List<Integer> getServerGroupIDsByClient(int clientDBID) {
        return executeCommandGetListIntPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "sgid=");
    }

    /**
     * Retrieves a list of server group names for a specific client.
     * <p>
     * This method sends a command to the server to get the server group names for the specified client.
     *
     * @param clientDBID The database ID of the client to get the server group names for.
     * @return A list of strings representing the server group names. If an error occurs or no server group names are found, an empty list is returned.
     */
    public List<String> getServerGroupNamesByClient(int clientDBID) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetServerGroupIDsByClientCommand(clientDBID), "name=");
    }

    /**
     * Retrieves a list of channel groups.
     * <p>
     * This method first checks if the channel groups are cached. If they are, it retrieves the channel groups from the cache.
     * If the channel groups are not cached, it sends a command to the server to get the channel groups.
     *
     * @return A list of ChannelGroupInfo objects representing the channel groups. If an error occurs or no channel groups are found, an empty list is returned.
     */
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

    /**
     * Retrieves information about the virtual server.
     * <p>
     * This method first checks if the virtual server information is cached. If it is, it retrieves the information from the cache.
     * If the virtual server information is not cached, it sends a command to the server to get the virtual server information.
     *
     * @return A VirtualServerInfo object containing information about the virtual server, or null if an error occurs or if 'information' is null.
     */
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

    /**
     * Retrieves connection information.
     * <p>
     * This method sends a command to the server to get the connection information.
     *
     * @return A ConnectionInfo object containing connection information.
     */
    public ConnectionInfo getConnectionInfo() {
        return executeCommandGetObject(CommandBuilder.buildGetConnectionInfoCommand(), ConnectionInfo::new);
    }

    /**
     * Retrieves the version of the server.
     * <p>
     * This method sends a command to the server to get the version.
     *
     * @return A string containing the version of the server.
     */
    public String getVersion() {
        return writer.executeReadCommand(CommandBuilder.buildGetVersionCommand())[0];
    }

    /**
     * Retrieves an offline message with the specified ID.
     * <p>
     * This method sends a command to the server to get the offline message with the specified ID.
     *
     * @param messageID The ID of the offline message to retrieve.
     * @return An OfflineMessageInfo object containing the offline message, or null if an error occurs.
     */
    public OfflineMessageInfo getOfflineMessage(int messageID) {
        return executeCommandGetObject(CommandBuilder.buildGetOfflineMessageCommand(messageID), OfflineMessageInfo::new);
    }

    /**
     * Retrieves a list of database clients.
     * <p>
     * This method retrieves a list of database clients from the cache if caching is enabled.
     * If caching is not enabled, it sends a command to the server to get the database clients.
     *
     * @param startOffset The number of entries to skip at the start. This parameter is ignored if caching is enabled.
     * @param limit       The maximum number of entries to return. This parameter is ignored if caching is enabled.
     * @return A list of DataBaseClientInfo objects representing the database clients. If an error occurs or no database clients are found, an empty list is returned.
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

    /**
     * Retrieves the count of database clients.
     * <p>
     * This method sends a command to the server to get the count of database clients.
     *
     * @return The count of database clients, or -1 if an error occurs.
     */
    public int getDataBaseClientsCount() {
        return executeCommandGetIntResult(CommandBuilder.buildGetDataBaseClientsCountCommand(), "count=");
    }

    /**
     * Retrieves the IDs of all database clients.
     * <p>
     * This method is a convenience method that calls the overloaded getDataBaseClientIDs(List<DataBaseClientInfo> clients) method
     * with the list of all database clients.
     *
     * @return A list of integers representing the IDs of all database clients.
     */
    public List<Integer> getDataBaseClientIDs() {
        return getDataBaseClientIDs(getDataBaseClients(-1, -1));
    }

    /**
     * Retrieves the IDs of the specified database clients.
     * <p>
     * This method iterates over the list of specified database clients and retrieves their IDs.
     *
     * @param clients The list of database clients to get the IDs for.
     * @return A list of integers representing the IDs of the specified database clients.
     */
    public List<Integer> getDataBaseClientIDs(List<DataBaseClientInfo> clients) {
        return clients.stream()
                .map(DataBaseClientInfo::getClientDataBaseID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the database IDs of the specified clients.
     * <p>
     * This method iterates over the list of specified clients and retrieves their database IDs.
     *
     * @param clients The list of clients to get the database IDs for.
     * @return A list of integers representing the database IDs of the specified clients.
     */
    public List<Integer> getDataBaseClientIDsByClients(List<ClientInfo> clients) {
        return clients.stream()
                .map(ClientInfo::getClientDataBaseID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of offline messages.
     * <p>
     * This method sends a command to the server to get the offline messages.
     *
     * @return A list of OfflineMessageInfo objects representing the offline messages. If an error occurs or no offline messages are found, an empty list is returned.
     */
    public List<OfflineMessageInfo> getOfflineMessages() {
        return executeCommandGetListResult(CommandBuilder.buildGetOfflineMessagesCommand(), OfflineMessageInfo::new);
    }

    /**
     * Retrieves information about the host.
     * <p>
     * This method sends a command to the server to get the host information.
     *
     * @return A HostInfo object containing information about the host, or null if an error occurs.
     */
    public HostInfo getHostInfo() {
        return executeCommandGetObject(CommandBuilder.buildGetHostInfoCommand(), HostInfo::new);
    }

    /**
     * Retrieves the ID of a client by its UUID.
     * <p>
     * This method is a convenience method that calls the getClientIDsByUUIDs(List<String> clientUUIDs) method
     * with a list containing the single client UUID.
     *
     * @param clientUUID The UUID of the client to get the ID for.
     * @return The ID of the client with the specified UUID, or -1 if the client is not found or an error occurs.
     */
    public int getClientIDByUUID(String clientUUID) {
        return getClientIDsByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(-1);
    }

    /**
     * Retrieves a list of client IDs by their UUIDs.
     *
     * @param clientUUIDs The UUIDs of the clients to get the IDs for.
     * @return A list of integers representing the client IDs.
     */
    public List<Integer> getClientIDsByUUIDs(List<String> clientUUIDs) {
        return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientIDsByUUIDsCommand(clientUUIDs), "clid=");
    }

    /**
     * Retrieves the UUID of a client by its ID.
     *
     * @param clientID The ID of the client to get the UUID for.
     * @return The UUID of the client with the specified ID, or null if the client is not found.
     */
    public String getClientUUIDByID(int clientID) {
        return getClientUUIDsByIDs(Collections.singletonList(clientID)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of client UUIDs by their IDs.
     *
     * @param clientIDs The IDs of the clients to get the UUIDs for.
     * @return A list of strings representing the client UUIDs.
     */
    public List<String> getClientUUIDsByIDs(List<Integer> clientIDs) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "cluid=");
    }

    /**
     * Retrieves the name of a client by its ID.
     *
     * @param clientID The ID of the client to get the name for.
     * @return The name of the client with the specified ID, or null if the client is not found.
     */
    public String getClientNameByID(int clientID) {
        return getClientNamesByIDs(Collections.singletonList(clientID)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of client names by their IDs.
     *
     * @param clientIDs The IDs of the clients to get the names for.
     * @return A list of strings representing the client names.
     */
    public List<String> getClientNamesByIDs(List<Integer> clientIDs) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsByIDsCommand(clientIDs), "nickname=");
    }

    /**
     * Retrieves the name of a client by its UUID.
     *
     * @param clientUUID The UUID of the client to get the name for.
     * @return The name of the client with the specified UUID, or null if the client is not found.
     */
    public String getClientNameByUUID(String clientUUID) {
        return getClientNamesByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of client names by their UUIDs.
     *
     * @param clientUUIDs The UUIDs of the clients to get the names for.
     * @return A list of strings representing the client names.
     */
    public List<String> getClientNamesByUUIDs(List<String> clientUUIDs) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesByUUIDsCommand(clientUUIDs), "name=");
    }

    /**
     * Retrieves the name of a client by its database ID.
     *
     * @param clientDataBaseID The database ID of the client to get the name for.
     * @return The name of the client with the specified database ID, or null if the client is not found.
     */
    public String getClientNameByDBID(int clientDataBaseID) {
        return getClientNamesByDBIDs(Collections.singletonList(clientDataBaseID)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of client names by their database IDs.
     *
     * @param clientDataBaseIDs The database IDs of the clients to get the names for.
     * @return A list of strings representing the client names.
     */
    public List<String> getClientNamesByDBIDs(List<Integer> clientDataBaseIDs) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "name=");
    }

    /**
     * Retrieves the UUID of a client by its database ID.
     *
     * @param clientDataBaseID The database ID of the client to get the UUID for.
     * @return The UUID of the client with the specified database ID, or null if the client is not found.
     */
    public String getClientUUIDByDBID(int clientDataBaseID) {
        return getClientUUIDsByDBIDs(Collections.singletonList(clientDataBaseID)).stream().findFirst().orElse(null);
    }

    /**
     * Retrieves a list of client UUIDs by their database IDs.
     *
     * @param clientDataBaseIDs The database IDs of the clients to get the UUIDs for.
     * @return A list of strings representing the client UUIDs.
     */
    public List<String> getClientUUIDsByDBIDs(List<Integer> clientDataBaseIDs) {
        return executeCommandGetListStringPropResult(CommandBuilder.buildGetClientNamesUUIDsCommand(clientDataBaseIDs), "cluid=");
    }

    /**
     * Retrieves the database ID of a client by its UUID.
     *
     * @param clientUUID The UUID of the client to get the database ID for.
     * @return The database ID of the client with the specified UUID, or -1 if the client is not found.
     */
    public int getClientDataBaseIDByUUID(String clientUUID) {
        return getClientDataBaseIDsByUUIDs(Collections.singletonList(clientUUID)).stream().findFirst().orElse(-1);
    }

    /**
     * Retrieves a list of client database IDs by their UUIDs.
     *
     * @param clientUUIDs The UUIDs of the clients to get the database IDs for.
     * @return A list of integers representing the client database IDs.
     */
    public List<Integer> getClientDataBaseIDsByUUIDs(List<String> clientUUIDs) {
        return executeCommandGetListIntPropResult(CommandBuilder.buildGetClientDataBaseIDsByUUIDsCommand(clientUUIDs), "cldbid=");
    }

    /**
     * Retrieves information about a client with the specified ID.
     * <p>
     * If client caching is enabled in the configuration, this method attempts to retrieve
     * the client information from the cache.
     * <p>
     * If client caching is not enabled, this method retrieves the client information from
     * the server by calling {@link Ts3BasicAPI#getClientsByIDs(List)} method with a list containing the
     * specified client ID.
     * <p>
     * If client information is retrieved successfully, it is returned
     * as a {@code ClientInfo} object. If no client is found with the specified ID,
     * {@code null} is returned.
     *
     * @param clientID The ID of the client to retrieve information for.
     * @return A {@code ClientInfo} object containing information about the client, or
     * {@code null} if the client is not found or if an error occurs.
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

    /**
     * Retrieves a list of clients by their IDs.
     * <p>
     * This method sends a command to the server to get the clients with the specified IDs.
     *
     * @param clientIDs The IDs of the clients to retrieve. This is a list of integer values representing the unique identifiers of the clients.
     * @return A list of ClientInfo objects representing the clients with the specified IDs. If an error occurs or no clients are found, an empty list is returned.
     */
    public List<ClientInfo> getClientsByIDs(List<Integer> clientIDs) {
        List<ClientInfo> resultList = new ArrayList<>(clientIDs.size());
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
     * @return List with Information about each Client connected to the Server.
     * @see Ts3SyncAPI#getOnlineClientsDetailed() gets more information about each
     * client.
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
     * @return
     * @see Ts3BasicAPI#getClients()
     * @see Ts3BasicAPI#getClientIDs(List)
     */

    public List<Integer> getClientIDs() {
        return getClientIDs(getClients());
    }

    /**
     * This method retrieves the IDs of the provided list of clients.
     * It uses Java 8's Stream API to map each ClientInfo object to its ID and collect the IDs into a list.
     *
     * @param clients A list of ClientInfo objects for which to retrieve the IDs.
     *                This is a list of ClientInfo objects representing the clients.
     * @return A list of integers representing the IDs of the provided clients.
     */
    public List<Integer> getClientIDs(List<ClientInfo> clients) {
        return clients.stream()
                .map(ClientInfo::getID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a DataBaseClientInfo object for a client with the specified database ID.
     * <p>
     * If database caching is enabled in the configuration, this method attempts to retrieve
     * the client database information from the cache.
     * <p>
     * If database caching is not enabled, this method retrieves the client database information from
     * the server by calling {@link Ts3BasicAPI#getDataBaseClientsByDBIDs(List)} method with a list containing the
     * specified client database ID.
     * <p>
     * If client database information is retrieved successfully, it is returned
     * as a {@code DataBaseClientInfo} object. If no database client is found with the specified ID,
     * {@code null} is returned.
     *
     * @param clientDataBaseID The database ID of the client to retrieve information for.
     *                         This is an integer value representing the unique identifier of the client in the database.
     * @return A DataBaseClientInfo object containing information about the client with the specified database ID.
     * If the client is not found or an error occurs, null is returned.
     */
    public DataBaseClientInfo getDataBaseClient(int clientDataBaseID) {
        if (config.isDataBaseCached()) {
            String info = cache.getDBClientInfo(clientDataBaseID);
            if (info == null)
                return null;
            return new DataBaseClientInfo(info);
        } else {
            return getDataBaseClientsByDBIDs(Collections.singletonList(clientDataBaseID))
                    .stream().findFirst().orElse(null);
        }
    }

    /**
     * Retrieves a list of DataBaseClientInfo objects for clients with the specified database IDs.
     * This method sends a command to the server to get the DataBaseClientInfo objects for the specified clients.
     *
     * @param clientDBIDs A list of database IDs of the clients to retrieve.
     *                    This is a list of integer values representing the unique identifiers of the clients in the database.
     * @return A list of DataBaseClientInfo objects representing the clients with the specified database IDs.
     * If an error occurs or no clients are found, an empty list is returned.
     */
    public List<DataBaseClientInfo> getDataBaseClientsByDBIDs(List<Integer> clientDBIDs) {
        return executeCommandGetListResult(CommandBuilder.buildGetDataBaseClientsByDBIDsCommand(clientDBIDs), DataBaseClientInfo::new);
    }

    /**
     * Retrieves information about a channel with the specified ID.
     * <p>
     * If channel caching is enabled in the configuration, this method attempts to retrieve
     * the channel information from the cache.
     * <p>
     * If channel caching is not enabled in the configuration,
     * it queries the server to get the channel information using the channel ID.
     * <p>
     * If the
     * information is retrieved successfully, it is returned as a {@code ChannelInfo} object.
     * If no channel is found with the specified ID or an error occurs during retrieval,
     * {@code null} is returned.
     *
     * @param channelID The ID of the channel to retrieve information for.
     * @return A {@code ChannelInfo} object containing information about the channel, or
     * {@code null} if the channel is not found or if an error occurs.
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

    public Map<Integer, String> getChannelsByName(String channelName) {
        String cmd = CommandBuilder.buildGetChannelsByNameCommand(channelName);
        String[] result = writer.executeReadCommand(cmd);
        if (checkError(result, cmd))
            return null;

        Map<Integer, String> resultMap = new HashMap<>();
        for (String info : splitResult(result)) {
            int key = Integer.parseInt(Formatter.toNormalFormat(Formatter.get(info, "cid=")));
            String value = Formatter.toNormalFormat(Formatter.get(info, "channel_name="));
            resultMap.put(key, value);
        }
        return resultMap;
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
     * Retrieves a list of channels.
     * <p>
     * If channel caching is enabled in the configuration, this method retrieves the channels from the cache.
     * <p>
     * If channel caching is not enabled, it sends a command to the server to get the channels.
     * <p>
     * Note that the information retrieved is less detailed because some Information
     * are not provided by {@link CommandBuilder#buildGetChannelsCommand() channellist} command.
     * For more detailed information, use {@link Ts3SyncAPI#getChannelsDetailed()}.
     *
     * @return A list of ChannelInfo objects representing the channels. If an error occurs or the QueryClient has no permission, an empty list is returned.
     * @see Ts3SyncAPI#getChannelsDetailed()
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
     * Retrieves a map of database IDs and channel IDs for a specific channel group.
     * This method sends a command to the server to get the database IDs and channel IDs for the specified channel group.
     *
     * @param channelGroupID The ID of the channel group to retrieve.
     * @return A map [ClientDataBaseID, List[ChannelIDs]] where the keys are the database IDs of the clients and the values are lists of channel IDs.
     * Each list contains the IDs of the channels in which the corresponding client has the specified channel group.
     * If an error occurs or no clients are found, an empty map is returned.
     */
    public Map<Integer, List<Integer>> getDatabaseIDsByChannelGroup(int channelGroupID) {
        Map<Integer, List<Integer>> resultMap = new HashMap<>();
        String cmd = CommandBuilder.buildGetDatabaseIDsByChannelGroupCommand(channelGroupID);
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
     * Retrieves a list of database client IDs that have a specific channel group in a specific channel.
     * This method sends a command to the server to get the database client IDs.
     *
     * @param channelID      The ID of the channel to retrieve the database client IDs for.
     * @param channelgroupID The ID of the channel group to retrieve the database client IDs for.
     * @return A list of integers representing the database client IDs that have the specified channel group in the specified channel.
     * If an error occurs or no database client IDs are found, an empty list is returned.
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

    /**
     * Retrieves a list of complaints about a specific client.
     *
     * @param clientDataBaseID The database ID of the client to get the complaints for.
     * @return A list of ComplainInfo objects representing the complaints about the specified client. If an error occurs or no complaints are found, an empty list is returned.
     */
    public List<ComplaintInfo> getComplaintsByClient(int clientDataBaseID) {
        return executeCommandGetListResult(CommandBuilder.buildGetComplainsByClientCommand(clientDataBaseID), ComplaintInfo::new);
    }

    /**
     * Retrieves a list of privilege keys.
     *
     * @return A list of PrivilegeKeyInfo objects representing the privilege keys. If an error occurs or no privilege keys are found, an empty list is returned.
     */
    public List<PrivilegeKeyInfo> getPrivilegeKeys() {
        return executeCommandGetListResult(CommandBuilder.buildGetPrivilegeKeysCommand(), PrivilegeKeyInfo::new);
    }

    /**
     * Retrieves a list of complaints.
     *
     * @return A list of ComplainInfo objects representing the complaints. If an error occurs or no complaints are found, an empty list is returned.
     */
    public List<ComplaintInfo> getComplaints() {
        return executeCommandGetListResult(CommandBuilder.buildGetComplainsCommand(), ComplaintInfo::new);
    }

    /**
     * Retrieves a list of bans.
     *
     * @return A list of BanInfo objects representing the bans. If an error occurs or no bans are found, an empty list is returned.
     */
    public List<BanInfo> getBans() {
        return executeCommandGetListResult(CommandBuilder.buildGetBansCommand(), BanInfo::new);
    }

    /**
     * Retrieves a list of virtual servers by sending a command to the server.
     *
     * @param command The command to send to the server.
     * @return A list of VirtualServerInfo objects representing the virtual servers. If an error occurs or no virtual servers are found, an empty list is returned.
     */
    protected List<VirtualServerInfo> getVirtualServersByCommand(String command) {
        return executeCommandGetListResult(command, VirtualServerInfo::new);
    }

    /**
     * Retrieves a list of virtual servers.
     * <p>
     * If virtual server caching is enabled in the configuration, this method retrieves the virtual servers from the cache.
     * <p>
     * If virtual server caching is not enabled, it sends a command to the server to get the virtual servers.
     *
     * @return A list of VirtualServerInfo objects representing the virtual servers. If an error occurs or no virtual servers are found, an empty list is returned.
     */
    public List<VirtualServerInfo> getVirtualServers() {
        if (config.isVirtualServerCached()) {
            List<VirtualServerInfo> resultList = new ArrayList<>();
            String vservers = cache.getVirtualServerList();
            if (vservers == null || vservers.isEmpty())
                return resultList;
            for (String server : vservers.split(TS_INFO_SEPARATOR)) {
                resultList.add(new VirtualServerInfo(server));
            }
            return resultList;
        }

        return getVirtualServersByCommand(CommandBuilder.buildGetVirtualServersCommand());
    }

    /**
     * Adds a ban rule to the server. Every argument is optional, but at least one should be used.
     * Be aware that you could ban more clients than intended.
     *
     * @param ip         is optional (if null not used)
     * @param name       is optional (if null not used)
     * @param clientUUID is optional (if null not used)
     * @param myTSID     is optional (if null not used)
     * @param banTime    is optional (if -2 not used)
     * @param banReason  is optional (if null not used)
     * @return ID of the ban.
     */

    public int addBan(String ip, String name, String clientUUID, String myTSID, long banTime, String banReason) {
        return executeCommandGetIntResult(CommandBuilder.buildAddBanCommand(ip, name, clientUUID, myTSID, banTime, banReason), "banid=");
    }

    /**
     * Bans a client from the server.
     *
     * @param clientID  ID of the client.
     * @param banTime   Time the client should be banned.
     * @param banReason is optional (if null not used)
     * @return Array of banIDs {clientBanID, ipBanID}
     */

    public int[] banClient(int clientID, long banTime, String banReason) {
        List<Integer> result = banClientIDs(Collections.singletonList(clientID), banTime, banReason);
        return new int[]{result.get(0), result.get(1)};
    }

    public List<Integer> banClientIDs(List<Integer> clientIDs, long banTime, String banReason) {
        List<Integer> resultList = new ArrayList<>(clientIDs.size() * 2);
        String cmd = CommandBuilder.buildBanClientIDsCommand(clientIDs, banTime, banReason);
        String[] result = writer.executeReadCommand(cmd);
        if (checkError(result, cmd))
            return resultList;
        for (String id : result[0].replace("banid=", "").replace("\n", ",").split(",")) {
            resultList.add(Integer.parseInt(id));
        }
        return resultList;
    }

    /**
     * Removes a ban rule from the server.
     * <p>
     * This method sends a command to the server to remove the ban rule with the specified ID.
     *
     * @param banID The ID of the ban rule to remove. This is an integer value representing the unique identifier of the ban rule.
     */
    public void removeBan(int banID) {
        writer.executeReadErrorCommand(CommandBuilder.buildRemoveBanCommand(banID));
    }

    /**
     * Removes all ban rules from the server.
     * <p>
     * This method sends a command to the server to remove all ban rules.
     */
    public void removeAllBans() {
        writer.executeReadErrorCommand(CommandBuilder.buildRemoveAllBansCommand());
    }

    /**
     * Starts a virtual server.
     * <p>
     * This method sends a command to the server to start the virtual server with the specified ID.
     *
     * @param virtualServerID The ID of the virtual server to start. This is an integer value representing the identifier of the virtual server.
     */
    public void startVirtualServer(int virtualServerID) {
        writer.executeReadErrorCommand(CommandBuilder.buildStartVirtualServerCommand(virtualServerID));
    }

    /**
     * Stops a specified virtual server.
     * <p>
     * This method sends a command to the server to stop the virtual server with the specified ID.
     *
     * @param virtualServerID The ID of the virtual server to stop. This is an integer value representing the identifier of the virtual server.
     * @param reasonmsg       The reason for stopping the server. This is a string value representing the reason for the action. If null, this parameter is not used.
     */
    public void stopVirtualServer(int virtualServerID, String reasonmsg) {
        writer.executeReadErrorCommand(CommandBuilder.buildStopVirtualServerCommand(virtualServerID, reasonmsg));
    }

    /**
     * Stops the server process.
     * <p>
     * This method sends a command to the server to stop the server process.
     *
     * @param reasonmsg The reason for stopping the server process. This is a string value representing the reason for the action.
     */
    public void stopServerProcess(String reasonmsg) {
        writer.executeReadCommand(CommandBuilder.buildStopServerProcessCommand(reasonmsg));
    }

    /**
     * Resets all permissions on the server.
     * <p>
     * This method sends a command to the server to reset all permissions. The server responds with a token that can be used to gain administrator access.
     *
     * @return A string containing the token for administrator access.
     */
    public String resetPermissions() {
        return executeCommandGetStringPropResult(CommandBuilder.buildResetPermissionsCommand(), "token=");
    }

    /**
     * Adds a permission to a specified channel. The permission can be identified by either its ID or its name.
     *
     * @param channelID       The ID of the channel to which the permission should be added.
     * @param permissionID    ID to identify the permission, could be optional
     *                        (if -1 not used)
     * @param permissionName  Name to identify the permission, could be optional
     *                        (if null not used)
     * @param permissionValue Value which the permission should have
     */

    public void addChannelPermission(int channelID, int permissionID, String permissionName, int permissionValue) {
        addChannelPermissions(channelID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
    }

    /**
     * Adds a list of permissions to a specified channel.
     *
     * @param channelID   The ID of the channel to which the permissions should be added.
     * @param permissions The list of permissions to add. Each permission is represented by a Permission object.
     */
    public void addChannelPermissions(int channelID, List<Permission> permissions) {
        writer.executeReadErrorCommand(CommandBuilder.buildAddChannelPermissionsCommand(channelID, permissions));
    }

    /**
     * Adds a specified client in a specified channel a permission. Use the
     * permissionid or the permissionname to identify the permission.
     *
     * @param channelID        Channel in which the client should get the
     *                         permission
     * @param clientDataBaseID Client which should get the permission
     * @param permissionID     ID to identify the permission, could be optional
     *                         (if -1 not used)
     * @param permissionName   Name to identify the permission, could be
     *                         optional (if null not used)
     * @param permissionValue  Value which the permission should have
     */

    public void addChannelClientPermission(int channelID, int clientDataBaseID, int permissionID, String permissionName, int permissionValue) {
        addChannelClientPermissions(channelID, clientDataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
    }

    public void addChannelClientPermissions(int channelID, int clientDataBaseID, List<Permission> permissions) {
        writer.executeReadErrorCommand(CommandBuilder.buildAddChannelClientPermissionsCommand(channelID, clientDataBaseID, permissions));
    }

    /**
     * Removes a specified client in a specified channel a permission. Use the
     * permissionid or the permissionname to identify the permission.
     *
     * @param channelID        Channel in which the permission should be removed
     *                         from the client
     * @param clientdataBaseID Client which should be removed the permission
     * @param permissionID     ID to identify the permission, could be optional
     *                         (if -1 not used)
     * @param permissionName   Name to identify the permission, could be
     *                         optional (if null not used)
     */

    public void removeChannelClientPermission(int channelID, int clientdataBaseID, int permissionID, String permissionName) {
        List<Integer> permIDs = permissionID != -1 ? Collections.singletonList(permissionID) : Collections.emptyList();
        List<String> permNames = permissionName != null ? Collections.singletonList(permissionName) : Collections.emptyList();
        removeChannelClientPermissions(channelID, clientdataBaseID, permIDs, permNames);
    }

    public void removeChannelClientPermissions(int channelID, int clientdataBaseID, List<Integer> permissions, List<String> permissionNames) {
        writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelClientPermissionsCommand(channelID, clientdataBaseID, permissions, permissionNames));
    }

    /**
     * Creates a new channel with the specified name and properties.
     * <p>
     * This method sends a command to the server to create a new channel with the specified name and properties.
     * The properties of the channel are specified in a map, where the keys are ChannelProperty objects and the values are strings.
     *
     * @param channelName       The name of the new channel. This is a string value representing the name of the channel.
     * @param channelProperties A map containing the properties of the new channel. The keys are ChannelProperty objects and the values are strings representing the property values.
     * @return The ID of the newly created channel. This is an integer value representing the identifier of the channel.
     */
    public int createChannel(String channelName, Map<ChannelProperty, String> channelProperties) {
        return executeCommandGetIntResult(CommandBuilder.buildCreateChannelCommand(channelName, channelProperties), "cid=");
    }

    /**
     * Deletes a channel from the server.
     * <p>
     * This method sends a command to the server to delete the channel with the specified ID.
     * If the 'force' parameter is true, the channel is deleted even if there are clients in it.
     *
     * @param channelID The ID of the channel to delete. This is an integer value representing the unique identifier of the channel.
     * @param force     A boolean value indicating whether to force the deletion of the channel. If true, the channel is deleted even if there are clients in it.
     */
    public void deleteChannel(int channelID, boolean force) {
        writer.executeReadErrorCommand(CommandBuilder.buildDeleteChannelCommand(channelID, force));
    }

    /**
     * Removes a specified channel a permission. Use the permissionid or the
     * permissionname to identify the permission.
     *
     * @param channelID      Channel which the permission should be removed from
     * @param permissionID   ID to identify the permission, could be optional
     *                       (if -1 not used)
     * @param permissionName Name to identify the permission, could be optional
     *                       (if null not used)
     */

    public void removeChannelPermission(int channelID, int permissionID, String permissionName) {
        List<Integer> permIDs = permissionID != -1 ? Collections.singletonList(permissionID) : Collections.emptyList();
        List<String> permNames = permissionName != null ? Collections.singletonList(permissionName) : Collections.emptyList();
        removeChannelPermissions(channelID, permIDs, permNames);
    }

    public void removeChannelPermissions(int channelID, List<Integer> permissions, List<String> permissionNames) {
        writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelPermissionsCommand(channelID, permissions, permissionNames));
    }

    /**
     * Edits the properties of a channel with the specified ID.
     * <p>
     * This method sends a command to the server to edit the properties of the channel with the specified ID.
     * The properties to be edited and their new values are specified in a map, where the keys are ChannelProperty objects and the values are strings.
     *
     * @param channelID         The ID of the channel to edit. This is an integer value representing the identifier of the channel.
     * @param channelProperties A map containing the properties to edit and their new values. The keys are ChannelProperty objects and the values are strings representing the new property values.
     */
    public void editChannel(int channelID, Map<ChannelProperty, String> channelProperties) {
        writer.executeReadErrorCommand(CommandBuilder.buildEditChannelCommand(channelID, channelProperties));
    }

    /**
     * Creates a new channel group with the specified name and type.
     * <p>
     * This method sends a command to the server to create a new channel group with the specified name and type.
     * The type of the channel group is specified as a ChannelGroupType object.
     *
     * @param channelGroupName The name of the new channel group. This is a string value representing the name of the channel group.
     * @param channelGroupType The type of the new channel group. This is a ChannelGroupType object representing the type of the channel group.
     * @return The ID of the newly created channel group. This is an integer value representing the unique identifier of the channel group.
     */
    public int createChannelGroup(String channelGroupName, ChannelGroupType channelGroupType) {
        return executeCommandGetIntResult(CommandBuilder.buildCreateChannelGroupCommand(channelGroupName, channelGroupType), "cgid=");
    }

    /**
     * Adds a specified channelgroup a permission. Use the permissionid or the
     * permissionname to identify the permission.
     *
     * @param channelGroupID  ChannelGroup which should get the permission
     * @param permissionID    ID to identify the permission, could be optional
     *                        (if -1 not used)
     * @param permissionName  Name to identify the permission, could be optional
     *                        (if null not used)
     * @param permissionValue Value which the permission should have
     */

    public void addChannelGroupPermission(int channelGroupID, int permissionID, String permissionName, int permissionValue) {
        addChannelGroupPermissions(channelGroupID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue)));
    }

    public void addChannelGroupPermissions(int channelGroupID, List<Permission> permissions) {
        writer.executeReadErrorCommand(CommandBuilder.buildAddChannelGroupPermissionsCommand(channelGroupID, permissions));
    }

    /**
     * Creates a copy of the channel group specified with sourceChannelGroupID.
     *
     * @param sourceChannelGroupID ID of a channel group the server should copy.
     * @param targetChannelGroupID ID of a designated target  channel group (if is set to
     *                             0 the server will create a new channel group)
     * @param channelGroupName     Name of the new group (needed if targetChannelGroupID is set to 0, otherwise ignored
     * @param channelGroupType     Type of channel group to choose between
     *                             template or normal group.
     * @return -1 if the targetChannelGroupID is NOT 0. And the new channel  group id otherwise.
     */

    public int copyChannelGroup(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
        String cmd = CommandBuilder.buildCopyChannelGroupCommand(sourceChannelGroupID, targetChannelGroupID, channelGroupName, channelGroupType);
        if (targetChannelGroupID != 0) {
            writer.executeReadErrorCommand(cmd);
            return -1;
        }

        return executeCommandGetIntResult(cmd, "cgid=");
    }

    public void deleteChannelGroup(int channelGroupID, boolean force) {
        writer.executeReadErrorCommand(CommandBuilder.buildDeleteChannelGroupCommand(channelGroupID, force));
    }

    /**
     * Removes a specified channelgroup a permission. Use the permissionid or the
     * permissionname to identify the permission.
     *
     * @param channelGroupID ChannelGroup which the permission should be removed
     *                       from
     * @param permissionID   ID to identify the permission, could be optional
     *                       (if -1 not used)
     * @param permissionName Name to identify the permission, could be optional
     *                       (if null not used)
     */

    public void removeChannelGroupPermission(int channelGroupID, int permissionID, String permissionName) {
        List<Integer> permIDs = permissionID != -1 ? Collections.singletonList(permissionID) : Collections.emptyList();
        List<String> permNames = permissionName != null ? Collections.singletonList(permissionName) : Collections.emptyList();
        removeChannelGroupPermissions(channelGroupID, permIDs, permNames);
    }

    /**
     * Removes a list of permissions from a specified channel group.
     *
     * @param channelGroupID  The ID of the channel group to remove the permissions from.
     * @param permissions     The list of permissions ids to remove. If the list is empty, they will be ignored.
     * @param permissionNames The list of permission names to remove. If the list is empty, they will be ignored.
     */

    public void removeChannelGroupPermissions(int channelGroupID, List<Integer> permissions, List<String> permissionNames) {
        writer.executeReadErrorCommand(CommandBuilder.buildRemoveChannelGroupPermissionsCommand(channelGroupID, permissions, permissionNames));
    }

    /**
     * Changes the name of a channel group.
     *
     * @param channelGroupID The ID of the channel group to rename.
     * @param channelName    The new name of the channel group.
     */

    public void renameChannelGroup(int channelGroupID, String channelName) {
        writer.executeReadErrorCommand(CommandBuilder.buildRenameChannelGroupCommand(channelGroupID, channelName));
    }

    public void moveChannel(int channelID, int channelParentID, int order) {
        writer.executeReadErrorCommand(CommandBuilder.buildMoveChannelCommand(channelID, channelParentID, order));
    }

    public void addClientPermission(int clientDataBaseID, int permissionID, String permissionName, int permissionValue, boolean permSkip) {
        addClientPermissions(clientDataBaseID, Collections.singletonList(new Permission(permissionName, permissionID, permissionValue, false, permSkip)));
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
        List<Integer> permIDs = permissionID != -1 ? Collections.singletonList(permissionID) : Collections.emptyList();
        List<String> permNames = permissionName != null ? Collections.singletonList(permissionName) : Collections.emptyList();

        removeClientPermissions(clientDataBaseID, permIDs, permNames);
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
            cache.updateQueryPropsCache(cache.updateAttribute(info, "client_nickname", Formatter.toTsFormat(queryName)));
        }

        writer.executeReadErrorCommand(CommandBuilder.buildUpdateQueryNameCommand(queryName));
    }

    public void addComplaint(int clientDBID, String message) {
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
        for (String files : result[0].split(System.lineSeparator())) {
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
     * @param lines    Amount of entries should be returned. Between [1,100] (100 Default)
     * @param reverse  if enabled the order is reversed
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

    public List<Permission> getQueryAssignmentOfPermission(int permissionID) {
        return getQueryAssignmentsOfPermissions(Collections.singletonList(permissionID), new ArrayList<>());
    }

    public List<Permission> getQueryAssignmentOfPermission(String permissionName) {
        return getQueryAssignmentsOfPermissions(new ArrayList<>(), Collections.singletonList(permissionName));
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

    public String createPrivilegeKey(PrivilegeKeyType keyType, int groupID, int channelID, String description, String customSet) {
        return executeCommandGetStringPropResult(CommandBuilder.buildCreatePrivilegeKeyCommand(keyType,
                groupID, channelID, description, customSet), "token=");
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
     * @param pattern     Filter for client login name (set to null to ignore)
     * @param startOffset Integer to skip the first `n` entries (set to -1 to
     *                    ignore)
     * @param duration    Integer to only return the first `n` entries (set to
     *                    -1 to ignore)
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
     * @param groupLevel      Selected level of groups.
     * @param permissionID    ID to identify the permission (could be -1 to be
     *                        ignored)
     * @param permissionName  Name to identify the permission (could be null to
     *                        be ignored)
     * @param permissionValue Value the permission should have.
     * @param permNegated     Permission should be negated or not.
     * @param permSkip        Permission should skip or not.
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
     * @param sourceServerGroupID ID of a group the server should copy.
     * @param targetServerGroupID ID of a designated target group (if is set to
     *                            0 the server will create a new group)
     * @param serverGroupName     Name of the new group.
     * @param serverGroupType     Type of servergroup to choose between
     *                            ServerQuery group, template or normal group.
     * @return -1 if the targetServerGroupID is NOT 0. And the new servergroup id otherwise.
     */

    public int copyServerGroup(int sourceServerGroupID, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
        String cmd = CommandBuilder.buildCopyServerGroupCommand(sourceServerGroupID, targetServerGroupID, serverGroupName, serverGroupType);
        if (targetServerGroupID != 0) {
            writer.executeReadErrorCommand(cmd);
            return -1;
        }

        return executeCommandGetIntResult(cmd, "sgid=");
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
        List<Integer> permIDs = permissionID != -1 ? Collections.singletonList(permissionID) : Collections.emptyList();
        List<String> permNames = permissionName != null ? Collections.singletonList(permissionName) : Collections.emptyList();

        removeServerGroupPermissions(serverGroupID, permIDs, permNames);
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
     * @param scope      Permissions of the key
     * @param lifetime   Lifetime of the key in Days (default is 14, 0 is
     *                   infinte, -1 to set to ignore)
     * @param clientDBID ID of the keyowner (default it the invoker, -1 to set
     *                   to ignore)
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
     * @param clientDBID  ClientDataBaseID (use -1 to ingore, use 0 to list all
     *                    clients)
     * @param startOffset To skip the first 'n' entries.
     * @param limit       Return only the first 'n' entries.
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
     * @param connected the connected to set
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
            logger.debug(DebugOutputType.WARNING, "Database was empty for command : '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.INSUFFICIENT_CLIENT_PERMISSIONS)) {
            logger.debug(DebugOutputType.ERROR, "Insufficient client permissions for command : '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.PARAMETER_NOT_FOUND)) {
            logger.debug(DebugOutputType.WARNING, "Parameter not found for command : '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.INVALID_CLIENT_ID.getValue())) {
            logger.debug(DebugOutputType.WARNING, "ClientUUID was invalid for command : '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.CHANNEL_NAME_IS_ALEARY_IN_USE)) {
            logger.debug(DebugOutputType.WARNING, "Channel could not be created! Channelname is already in use!");
            error = true;
        } else if (TSError.isError(result, TSError.FILE_IO_ERROR)) {
            logger.debug(DebugOutputType.WARNING, "File input/output error for command : '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.VIRTUALSERVER_LIMIT_REACHED)) {
            logger.debug(DebugOutputType.ERROR, "Limit of virtual servers reached by command: '" + cmd + "'");
            error = true;
        } else if (TSError.isError(result, TSError.INVALID_PERMISSION_ID)) {
            logger.debug(DebugOutputType.WARNING, "Permission could not be found by command: '" + cmd + "'");
            error = true;
        }

        if (!TSError.isError(result, TSError.OK)) {
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
