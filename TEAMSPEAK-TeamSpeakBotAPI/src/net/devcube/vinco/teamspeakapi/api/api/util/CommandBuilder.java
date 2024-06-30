/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 09.02.2024
 * 
 * Uhrzeit : 12:27:32
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;

public class CommandBuilder {

	public static String buildHelpCommand(String command) {
		return new StringBuilder("help").append(" ").append(command).toString();
	}

	public static String buildLoginCommand(String username, String password) {
		StringBuilder cmd = new StringBuilder("login");
		cmd.append(" ").append(username);
		cmd.append(" ").append(password);
		return cmd.toString();
	}

	public static String buildChannelListCommand() {
		StringBuilder cmd = new StringBuilder("channellist");
		cmd.append(" -topic");
		cmd.append(" -flags");
		cmd.append(" -voice");
		cmd.append(" -limits");
		cmd.append(" -icon");
		cmd.append(" -secondsempty");
		cmd.append(" -banners");
		return cmd.toString();
	}

	public static String buildSelectVirtualServerCommand(int serverID, int serverPort, String nickName) {
		StringBuilder cmd = new StringBuilder("use");
		if (serverID != -1)
			cmd.append(" sid=").append(serverID);
		if (serverPort != -1)
			cmd.append(" port=").append(serverPort);
		if (nickName != null)
			cmd.append(" client_nickname=").append(Formatter.toTsFormat(nickName));
		return cmd.toString();
	}

	public static String buildPokeClientCommand(int clientID, String message) {
		StringBuilder cmd = new StringBuilder("clientpoke");
		cmd.append(" clid=").append(clientID);
		cmd.append(" msg=").append(Formatter.toTsFormat(message));
		return cmd.toString();
	}

	public static String buildMoveClientsCommand(List<Integer> clientIDs, int channelID) {
		StringBuilder cmd = new StringBuilder("clientmove ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		cmd.append(" cid=").append(channelID);
		return cmd.toString();
	}

	public static String buildSendGlobalMessageCommand(String message) {
		StringBuilder cmd = new StringBuilder("gm");
		cmd.append(" msg=").append(Formatter.toTsFormat(message));
		return cmd.toString();
	}

	public static String buildLogoutCommand() {
		return "logout";
	}

	public static String buildQuitCommand() {
		return "quit";
	}

	public static String buildgetQueryInfoCommand() {
		return "whoami";
	}

	public static String buildGetVirtualServerIDByPort(int port) {
		StringBuilder cmd = new StringBuilder("serveridgetbyport");
		cmd.append(" virtualserver_port=").append(port);
		return cmd.toString();
	}

	public static String buildGetPermissionIDsByNamesCommand(List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("permidgetbyname ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildGetPermissionListCommand() {
		return "permissionlist";
	}

	public static String buildGetPermissionNameByIDCommand(int permissionID) {
		StringBuilder cmd = new StringBuilder("permget");
		cmd.append(" permid=").append(permissionID);
		return cmd.toString();
	}

	public static String buildGetChannelPermissionsCommand(int channelID) {
		StringBuilder cmd = new StringBuilder("channelpermlist");
		cmd.append(" cid=").append(channelID);
		return cmd.toString();
	}

	public static String buildGetClientPermissionsCommand(int clientDataBaseID) {
		StringBuilder cmd = new StringBuilder("clientpermlist");
		cmd.append(" cldbid=").append(clientDataBaseID);
		return cmd.toString();
	}

	public static String buildGetServerGroupPermissionsCommand(int serverGroupID) {
		StringBuilder cmd = new StringBuilder("servergrouppermlist");
		cmd.append(" sgid=").append(serverGroupID);
		return cmd.toString();
	}

	public static String buildGetChannelGroupPermissionsCommand(int channelGroupID) {
		StringBuilder cmd = new StringBuilder("channelgrouppermlist");
		cmd.append(" cgid=").append(channelGroupID);
		return cmd.toString();
	}

	public static String buildGetChannelClientPermissionsCommand(int channelID, int clientDataBaseID) {
		StringBuilder cmd = new StringBuilder("channelclientpermlist");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cldbid=").append(clientDataBaseID);
		return cmd.toString();
	}

	public static String buildGetServerGroupsCommand() {
		return new StringBuilder("servergrouplist").toString();
	}

	public static String buildGetServerGroupIDsByClientCommand(int clientDBID) {
		StringBuilder cmd = new StringBuilder("servergroupsbyclientid");
		cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildGetChannelGroupsCommand() {
		return "channelgrouplist";
	}

	public static String buildGetServerInfoCommand() {
		return "serverinfo";
	}

	public static String buildGetConnectionInfoCommand() {
		return "serverrequestconnectioninfo";
	}

	public static String buildGetOfflineMessageCommand(int messageID) {
		StringBuilder cmd = new StringBuilder("messageget");
		cmd.append(" msgid=").append(messageID);
		return cmd.toString();
	}

	public static String buildGetDataBaseClientsCommand(int startOffset, int limit) {
		StringBuilder cmd = new StringBuilder("clientdblist");
		if (startOffset != -1)
			cmd.append(" start=").append(startOffset);
		if (limit != -1)
			cmd.append(" duration=").append(limit);
		return cmd.toString();
	}

	public static String buildGetDataBaseClientsCountCommand() {
		StringBuilder cmd = new StringBuilder("clientdblist");
		cmd.append(" duration=").append(1);
		cmd.append(" -count");
		return cmd.toString();
	}

	public static String buildGetOfflineMessagesCommand() {
		return "messagelist";
	}

	public static String buildGetHostInfoCommand() {
		return "hostinfo";
	}

	public static String buildGetClientIDsByUUIDsCommand(List<String> clientUUIDs) {
		StringBuilder cmd = new StringBuilder("clientgetids ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid"));
		return cmd.toString();
	}

	public static String buildGetClientNamesUUIDsByIDsCommand(List<Integer> clientIDs) {
		StringBuilder cmd = new StringBuilder("clientgetuidfromclid ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		return cmd.toString();
	}

	public static String buildGetClientNamesByUUIDsCommand(List<String> clientUUIDs) {
		StringBuilder cmd = new StringBuilder("clientgetnamefromuid ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid"));
		return cmd.toString();
	}

	public static String buildGetClientNamesUUIDsCommand(List<Integer> clientDataBaseIDs) {
		StringBuilder cmd = new StringBuilder("clientgetnamefromdbid ");
		cmd.append(buildObjectArray(clientDataBaseIDs, "cldbid"));
		return cmd.toString();
	}

	public static String buildGetClientDataBaseIDsByUUIDsCommand(List<String> clientUUIDs) {
		StringBuilder cmd = new StringBuilder("clientgetdbidfromuid ");
		cmd.append(buildObjectArray(clientUUIDs, "cluid"));
		return cmd.toString();
	}

	public static String buildGetClientsByIDsCommand(List<Integer> clientIDs) {
		StringBuilder cmd = new StringBuilder("clientinfo ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		return cmd.toString();
	}

	public static String buildGetClientsCommand() {
		StringBuilder cmd = new StringBuilder("clientlist");
		cmd.append(" -uid");
		cmd.append(" -away");
		cmd.append(" -voice");
		cmd.append(" -times");
		cmd.append(" -groups");
		cmd.append(" -info");
		cmd.append(" -country");
		cmd.append(" -ip");
		cmd.append(" -badges");
		return cmd.toString();
	}

	public static String buildGetDataBaseClientsByDBIDsCommand(List<Integer> clientDBIDs) {
		StringBuilder cmd = new StringBuilder("clientdbinfo ");
		cmd.append(buildObjectArray(clientDBIDs, "cldbid"));
		return cmd.toString();
	}

	public static String buildGetChannelCommand(int channelID) {
		StringBuilder cmd = new StringBuilder("channelinfo");
		cmd.append(" cid=").append(channelID);
		return cmd.toString();
	}

	public static String buildGetChannelsByNameCommand(String channelName) {
		StringBuilder cmd = new StringBuilder("channelfind");
		cmd.append(" pattern=").append(Formatter.toTsFormat(channelName));
		return cmd.toString();
	}

	public static String buildGetChannelsCommand() {
		StringBuilder cmd = new StringBuilder("channellist");
		cmd.append(" -topic");
		cmd.append(" -flags");
		cmd.append(" -voice");
		cmd.append(" -limits");
		cmd.append(" -icon");
		cmd.append(" -secondsempty");
		cmd.append(" -banners");
		return cmd.toString();
	}

	public static String buildGetDatabaseIDsByServerGroupCommand(int servergroupID) {
		StringBuilder cmd = new StringBuilder("servergroupclientlist");
		cmd.append(" sgid=").append(servergroupID);
		return cmd.toString();
	}

	public static String buildGetDatabaseIDsByChannelGroupCommand(int channelgroupID) {
		StringBuilder cmd = new StringBuilder("channelgroupclientlist");
		cmd.append(" cgid=").append(channelgroupID);
		return cmd.toString();
	}

	public static String buildGetDatabaseIDsByChannelAndGroupCommand(int channelID, int channelgroupID) {
		StringBuilder cmd = new StringBuilder("channelgroupclientlist");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cgid=").append(channelgroupID);
		return cmd.toString();
	}

	public static String buildGetChannelGroupsByDatabaseIDCommand(int clientDataBaseID) {
		StringBuilder cmd = new StringBuilder("channelgroupclientlist");
		cmd.append(" cldbid=").append(clientDataBaseID);
		return cmd.toString();
	}

	public static String buildGetChannelGroupsByChannelIDCommand(int channelID) {
		StringBuilder cmd = new StringBuilder("channelgroupclientlist");
		cmd.append(" cid=").append(channelID);
		return cmd.toString();
	}

	public static String buildGetComplainsByClientCommand(int clientDataBaseID) {
		StringBuilder cmd = new StringBuilder("complainlist");
		cmd.append(" tcldbid=").append(clientDataBaseID);
		return cmd.toString();
	}

	public static String buildGetPrivilegeKeysCommand() {
		return "privilegekeylist";
	}

	public static String buildGetComplainsCommand() {
		return "complainlist";
	}

	public static String buildGetBansCommand() {
		return "banlist";
	}

	public static String buildGetVirtualServersCommand() {
		StringBuilder cmd = new StringBuilder("serverlist");
		cmd.append(" -uid");
		cmd.append(" -all");
		return cmd.toString();
	}
	
	public static String buildGetVersionCommand() {
		return "version";
	}

	public static String buildAddBanCommand(String ip, String name, String clientUUID, String myTSID, long banTime, String banReason) {
		StringBuilder cmd = new StringBuilder("banadd");
		if (ip != null)
			cmd.append(" ip=").append(ip);
		if (name != null)
			cmd.append(" name=").append(Formatter.toTsFormat(name));
		if (clientUUID != null)
			cmd.append(" uid=").append(clientUUID);
		if (myTSID != null)
			cmd.append(" mytsid=").append(myTSID);
		if (banTime != -2)
			cmd.append(" time=").append(banTime);
		if (banReason != null)
			cmd.append(" banreason=").append(Formatter.toTsFormat(banReason));
		return cmd.toString();
	}

	public static String buildBanClientIDsCommand(List<Integer> clientIDs, long banTime, String banReason) {
		StringBuilder cmd = new StringBuilder("banclient");
		cmd.append(" time=").append(banTime).append(" ");
		if (banReason != null)
			cmd.append(" banreason=").append(Formatter.toTsFormat(banReason)).append(" ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		return cmd.toString();
	}

	public static String buildRemoveBanCommand(int banID) {
		StringBuilder cmd = new StringBuilder("bandel");
		cmd.append(" banid=").append(banID);
		return cmd.toString();
	}
	
	public static String buildRemoveAllBansCommand() {
		return "bandelall";
	}

	public static String buildStartVirtualServerCommand(int virtualServerID) {
		StringBuilder cmd = new StringBuilder("serverstart");
		cmd.append(" sid=").append(virtualServerID);
		return cmd.toString();
	}

	public static String buildStopVirtualServerCommand(int virtualServerID, String reasonmsg) {
		StringBuilder cmd = new StringBuilder("serverstop");
		cmd.append(" sid=").append(virtualServerID);
		if (reasonmsg != null)
			cmd.append(" reasonmsg=").append(Formatter.toTsFormat(reasonmsg));
		return cmd.toString();
	}

	public static String buildStopServerProcessCommand(String reasonmsg) {
		StringBuilder cmd = new StringBuilder("serverprocessstop");
		if (reasonmsg != null)
			cmd.append(" reasonmsg=").append(Formatter.toTsFormat(reasonmsg));
		return cmd.toString();
	}
	
	public static String buildResetPermissionsCommand() {
		return "permreset";
	}

	public static String buildAddChannelPermissionsCommand(int channelID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channeladdperm");
		cmd.append(" cid=").append(channelID).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildAddChannelClientPermissionsCommand(int channelID, int clientdataBaseID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channelclientaddperm");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cldbid=").append(clientdataBaseID).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildRemoveChannelClientPermissionsCommand(int channelID, int clientdataBaseID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channelclientdelperm");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cldbid=").append(clientdataBaseID).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildCreateChannelCommand(String channelName, Map<ChannelProperty, String> channelProperties) {
		StringBuilder cmd = new StringBuilder("channelcreate");
		cmd.append(" channel_name=").append(Formatter.toTsFormat(channelName));
		channelProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}

	public static String buildDeleteChannelCommand(int channelID, boolean force) {
		StringBuilder cmd = new StringBuilder("channeldelete");
		cmd.append(" cid=").append(channelID);
		cmd.append(" force=").append(Formatter.toInt(force));
		return cmd.toString();
	}

	public static String buildRemoveChannelPermissionsCommand(int channelID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channeldelperm");
		cmd.append(" cid=").append(channelID).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildEditChannelCommand(int channelID, Map<ChannelProperty, String> channelProperties) {
		StringBuilder cmd = new StringBuilder("channeledit");
		cmd.append(" cid=").append(channelID);
		channelProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(channelProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}

	public static String buildCreateChannelGroupCommand(String channelGroupName, ChannelGroupType channelGroupType) {
		StringBuilder cmd = new StringBuilder("channelgroupadd");
		cmd.append(" name=").append(Formatter.toTsFormat(channelGroupName));
		cmd.append(" type=").append(channelGroupType.getValue());
		return cmd.toString();
	}

	public static String buildAddChannelGroupPermissionsCommand(int channelGroupID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("channelgroupaddperm");
		cmd.append(" cgid=").append(channelGroupID).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildCopyChannelGroupCommand(int sourceChannelGroupID, int targetChannelGroupID, String channelGroupName, ChannelGroupType channelGroupType) {
		StringBuilder cmd = new StringBuilder("channelgroupcopy");
		cmd.append(" scgid=").append(sourceChannelGroupID);
		cmd.append(" tcgid=").append(targetChannelGroupID);
		cmd.append(" name=").append(Formatter.toTsFormat(channelGroupName));
		cmd.append(" type=").append(channelGroupType.getValue());
		return cmd.toString();
	}

	public static String buildDeleteChannelGroupCommand(int channelGroupID, boolean force) {
		StringBuilder cmd = new StringBuilder("channelgroupdel");
		cmd.append(" cgid=").append(channelGroupID);
		cmd.append(" force=").append(Formatter.toInt(force));
		return cmd.toString();
	}

	public static String buildRemoveChannelGroupPermissionsCommand(int channelGroupID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("channelgroupdelperm");
		cmd.append(" cgid=").append(channelGroupID).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildRenameChannelGroupCommand(int channelGroupID, String channelName) {
		StringBuilder cmd = new StringBuilder("channelgrouprename");
		cmd.append(" cgid=").append(channelGroupID);
		cmd.append(" name=").append(Formatter.toTsFormat(channelName));
		return cmd.toString();
	}

	public static String buildMoveChannelCommand(int channelID, int channelParentID, int order) {
		StringBuilder cmd = new StringBuilder("channelmove");
		cmd.append("cid=").append(channelID);
		cmd.append(" cpid=").append(channelParentID);
		cmd.append(" order=").append(order);
		return cmd.toString();
	}

	public static String buildAddClientPermissionsCommand(int clientDataBaseID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("clientaddperm");
		cmd.append(" cldbid=").append(clientDataBaseID).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildDeleteDataBaseClientCommand(int clientDataBaseID) {
		StringBuilder cmd = new StringBuilder("clientdbdelete");
		cmd.append(" cldbid=").append(clientDataBaseID);
		return cmd.toString();
	}

	public static String buildEditDataBaseClientCommand(int clientDataBaseID, String description) {
		StringBuilder cmd = new StringBuilder("clientdbedit");
		cmd.append("cldbid=").append(clientDataBaseID);
		cmd.append(" client_description=").append(Formatter.toTsFormat(description));
		return cmd.toString();
	}

	public static String buildGetDataBaseClientIDByUUIDCommand(String clientUUID) {
		StringBuilder cmd = new StringBuilder("clientdbfind");
		cmd.append(" -uid=");
		cmd.append(" pattern=").append(clientUUID);
		return cmd.toString();
	}

	public static String buildGetDataBaseClientIDsByNameCommand(String clientLastName) {
		StringBuilder cmd = new StringBuilder("clientdbfind");
		cmd.append(" pattern=").append(Formatter.toTsFormat(clientLastName));
		return cmd.toString();
	}

	public static String buildRemoveClientPermissionsCommand(int clientDataBaseID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("clientdelperm");
		cmd.append(" cldbid=").append(clientDataBaseID).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildEditClientCommand(int clientID, Map<ClientProperty, String> clientProperties) {
		StringBuilder cmd = new StringBuilder("clientedit");
		cmd.append(" clid=").append(clientID);
		clientProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(clientProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}

	public static String buildGetClientIDsByNameCommand(String clientName) {
		StringBuilder cmd = new StringBuilder("clientfind");
		cmd.append(" pattern=").append(Formatter.toTsFormat(clientName));
		return cmd.toString();
	}

	public static String buildKickClientIDsCommand(List<Integer> clientIDs, int reasonID, String reason) {
		StringBuilder cmd = new StringBuilder("clientkick");
		cmd.append(" reasonid=").append(reasonID).append(" ");
		if (reason != null)
			cmd.append(" reasonmsg=").append(Formatter.toTsFormat(reason)).append(" ");
		cmd.append(buildObjectArray(clientIDs, "clid"));
		return cmd.toString();
	}

	public static String buildUpdateServerQueryLoginCommand(String username) {
		StringBuilder cmd = new StringBuilder("clientsetserverquerylogin");
		cmd.append("client_login_name=").append(Formatter.toTsFormat(username));
		return cmd.toString();
	}

	public static String buildUpdateQueryNameCommand(String queryName) {
		StringBuilder cmd = new StringBuilder("clientupdate");
		cmd.append(" client_nickname=").append(Formatter.toTsFormat(queryName));
		return cmd.toString();
	}

	public static String buildAddComplainCommand(int clientDBID, String message) {
		StringBuilder cmd = new StringBuilder("complainadd");
		cmd.append(" tcldbid=").append(clientDBID);
		cmd.append(" message=").append(Formatter.toTsFormat(message));
		return cmd.toString();
	}

	public static String buildDeleteComplainCommand(int clientDBID, int fromClientDBID) {
		StringBuilder cmd = new StringBuilder("complaindel");
		cmd.append(" tcldbid=").append(clientDBID);
		cmd.append(" fcldbid=").append(fromClientDBID);
		return cmd.toString();
	}

	public static String buildDeleteAllComplainsCommand(int clientDBID) {
		StringBuilder cmd = new StringBuilder("complaindelall");
		cmd.append(" tcldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildGetCustomInfoCommand(int clientDBID) {
		StringBuilder cmd = new StringBuilder("custominfo");
		cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildSearchDBIDsCustomInfoCommand(String ident, String pattern) {
		StringBuilder cmd = new StringBuilder("customsearch");
		cmd.append(" ident=").append(Formatter.toTsFormat(ident));
		cmd.append(" pattern=").append(Formatter.toTsFormat(pattern));
		return cmd.toString();
	}

	public static String buildSetCustomInfoCommand(int clientDBID, String ident, String value) {
		StringBuilder cmd = new StringBuilder("customset");
		cmd.append(" cldbid=").append(clientDBID);
		cmd.append(" ident=").append(Formatter.toTsFormat(ident));
		cmd.append(" value=").append(Formatter.toTsFormat(value));
		return cmd.toString();
	}

	public static String buildDeleteCustomInfoCommand(int clientDBID, String ident) {
		StringBuilder cmd = new StringBuilder("customdelete");
		cmd.append(" cldbid=").append(clientDBID);
		cmd.append(" ident=").append(Formatter.toTsFormat(ident));
		return cmd.toString();
	}

	public static String buildCreateFileDirectoryCommand(int channelID, String channelPassword, String dirName) {
		StringBuilder cmd = new StringBuilder("ftcreatedir");
		cmd.append("cid=").append(channelID);
		cmd.append(" cpw=").append(channelPassword);
		cmd.append(" dirname=").append(Formatter.toTsFormat(dirName));
		return cmd.toString();
	}

	public static String buildDeleteFilesCommand(int channelID, String channelPassword, List<String> fileNames) {
		StringBuilder cmd = new StringBuilder("ftdeletefile");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cpw=").append(channelPassword);
		cmd.append(" ");
		cmd.append(buildObjectArray(fileNames, "name"));
		return cmd.toString();
	}

	public static String buildGetFileInfosCommand(int channelID, String channelPassword, List<String> fileNames) {

		StringBuilder cmd = new StringBuilder("ftgetfileinfo ");
		fileNames.forEach(fileName -> {
			if (!fileName.contentEquals(fileNames.get(0)))
				cmd.append("|");
			cmd.append("cid=").append(channelID);
			cmd.append(" cpw=").append(channelPassword);
			cmd.append(" name=").append(Formatter.toTsFormat(fileName));
		});
		return cmd.toString();
	}

	public static String buildGetChannelFilesByPathCommand(int channelID, String channelPassword, String filePath) {
		StringBuilder cmd = new StringBuilder("ftgetfilelist");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cpw=").append(channelPassword);
		cmd.append(" path=").append(Formatter.toTsFormat(filePath));
		return cmd.toString();
	}

	public static String buildGetFileTransfersCommand() {
		return new StringBuilder("ftlist").toString();
	}

	public static String buildRenameFileCommand(int channelID, String channelPassword, String oldFilePath, String newFilePath) {
		StringBuilder cmd = new StringBuilder("ftrenamefile");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cpw=").append(channelPassword);
		cmd.append(" oldname=").append(Formatter.toTsFormat(oldFilePath));
		cmd.append(" newname=").append(Formatter.toTsFormat(newFilePath));
		return cmd.toString();
	}

	public static String buildMoveFileCommand(int channelID, String channelPassword, String oldFilePath, int newChannelID, String newChannelPassword, String newFilePath) {
		StringBuilder cmd = new StringBuilder("ftrenamefile");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cpw=").append(channelPassword);
		cmd.append(" tcid=").append(newChannelID);
		cmd.append(" tcpw=").append(newChannelPassword);
		cmd.append(" oldname=").append(Formatter.toTsFormat(oldFilePath));
		cmd.append(" newname=").append(Formatter.toTsFormat(newFilePath));
		return cmd.toString();
	}

	public static String buildStopFileTransferCommand(int serverFileTransferID, boolean delete) {
		StringBuilder cmd = new StringBuilder("ftstop");
		cmd.append(" serverftfid=").append(serverFileTransferID);
		cmd.append(" delete=").append(Formatter.toInt(delete));
		return cmd.toString();
	}

	public static String buildEditInstanceCommand(Map<InstanceProperty, String> instanceProperties) {
		StringBuilder cmd = new StringBuilder("instanceedit");
		instanceProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(instanceProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}
	
	public static String buildGetInstanceInfoCommand() {
		return new StringBuilder("instanceinfo").toString();
	}

	public static String buildAddToLogCommand(LogLevel logLevel, String logMessage) {
		StringBuilder cmd = new StringBuilder("logadd");
		cmd.append(" loglevel=").append(logLevel.getValue());
		cmd.append(" logmsg=").append(Formatter.toTsFormat(logMessage));
		return cmd.toString();
	}

	public static String buildGetLogCommand(int lines, boolean reverse, boolean instance, int beginPos) {
		StringBuilder cmd = new StringBuilder("logview");
		cmd.append(" lines=").append(lines);
		cmd.append(" reverse=").append(Formatter.toInt(reverse));
		cmd.append(" instance=").append(Formatter.toInt(instance));
		cmd.append(" begin_pos=").append(beginPos);
		return cmd.toString();
	}

	public static String buildSendOfflineMessageCommand(String clientUUID, String subject, String message) {
		StringBuilder cmd = new StringBuilder("messageadd ");
		cmd.append(" cluid=").append(clientUUID);
		cmd.append(" subject=").append(Formatter.toTsFormat(subject));
		cmd.append(" message=").append(Formatter.toTsFormat(message));
		return cmd.toString();
	}

	public static String buildDeleteOfflineMessageCommand(int offlineMessageID) {
		StringBuilder cmd = new StringBuilder("messagedel");
		cmd.append(" msgid=").append(offlineMessageID);
		return cmd.toString();
	}

	public static String buildUpdateOfflineMessageFlagCommand(int offlineMessageID, boolean flag) {
		StringBuilder cmd = new StringBuilder("messageupdateflag");
		cmd.append(" msgid=").append(offlineMessageID);
		cmd.append(" flag=").append(Formatter.toInt(flag));
		return cmd.toString();
	}

	public static String buildGetAssignmentsOfPermissionsCommand(List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("permfind ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty() && !permissionNames.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildGetQueryAssignmentsOfPermissionsCommand(List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("permget ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildGetPermOverviewCommand(int clientDBID, int channelID, int permID) {
		StringBuilder cmd = new StringBuilder("permoverview");
		cmd.append(" cid=").append(channelID);
		cmd.append(" cldbid=").append(clientDBID);
		cmd.append(" permid=").append(permID);
		return cmd.toString();
	}

	public static String buildCreatePrivilegeKeyCommand(PrivilegeKeyType keyType, int groupID, int channelID, String description) {
		StringBuilder cmd = new StringBuilder("privilegekeyadd");
		cmd.append(" tokentype=").append(keyType.getValue());
		cmd.append(" tokenid1=").append(groupID);
		cmd.append(" tokenid2=").append(channelID);
		if (description != null)
			cmd.append(" tokendescription=").append(Formatter.toTsFormat(description));
		return cmd.toString();
	}

	public static String buildDeletePrivilegeKeyCommand(String privilegeKey) {
		StringBuilder cmd = new StringBuilder("privilegekeydelete");
		cmd.append(" token=").append(privilegeKey);
		return cmd.toString();
	}

	public static String buildUsePrivilegeKeyCommand(String privilegeKey) {
		StringBuilder cmd = new StringBuilder("privilegekeyuse");
		cmd.append(" token=").append(privilegeKey);
		return cmd.toString();
	}

	public static String buildCreateQueryLoginCommand(String loginName, int clientDBID) {
		StringBuilder cmd = new StringBuilder("queryloginadd");
		cmd.append(" client_login_name=").append(loginName);
		if (clientDBID != -1)
			cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildDeleteQueryLoginCommand(int clientDBID) {
		StringBuilder cmd = new StringBuilder("querylogindel");
		cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildGetQueryLoginsCommand(String pattern, int startOffset, int duration) {
		StringBuilder cmd = new StringBuilder("queryloginlist");
		if (pattern != null)
			cmd.append(" pattern=").append(pattern);
		if (startOffset != -1)
			cmd.append(" start=").append(startOffset);
		if (duration != -1)
			cmd.append(" duration=").append(duration);
		return cmd.toString();
	}

	public static String buildSendTextMessageCommand(TextMessageType messageType, int clientID, String message) {
		StringBuilder cmd = new StringBuilder("sendtextmessage");
		cmd.append(" targetmode=").append(messageType.getValue());
		cmd.append(" target=").append(clientID);
		cmd.append(" msg=").append(Formatter.toTsFormat(message));
		return cmd.toString();
	}

	public static String buildCreateVirtualServerCommand(Map<VirtualServerProperty, String> virtualServerProperties) {
		StringBuilder cmd = new StringBuilder("servercreate");
		virtualServerProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}

	public static String buildDeleteVirtualServerCommand(int virtualServerID) {
		StringBuilder cmd = new StringBuilder("serverdelete");
		cmd.append(" sid=").append(virtualServerID);
		return cmd.toString();
	}

	public static String buildEditVirtualServerCommand(Map<VirtualServerProperty, String> virtualServerProperties) {
		StringBuilder cmd = new StringBuilder("serveredit");
		virtualServerProperties.keySet().forEach(prop -> {
			String propName = prop.getValue();
			String setValue = Formatter.toTsFormat(virtualServerProperties.get(prop));
			cmd.append(" ").append(propName).append("=").append(setValue);
		});
		return cmd.toString();
	}

	public static String buildCreateServerGroupCommand(String serverGroupName, ServerGroupType groupType) {
		StringBuilder cmd = new StringBuilder("servergroupadd");
		cmd.append(" name=").append(Formatter.toTsFormat(serverGroupName));
		cmd.append(" type=").append(groupType.getValue());
		return cmd.toString();
	}

	public static String buildAddClientDBIDsToServerGroupCommand(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder cmd = new StringBuilder("servergroupaddclient");
		cmd.append(" sgid=").append(serverGroupID).append(" ");
		cmd.append(buildObjectArray(dataBaseClientIDs, "cldbid"));
		return cmd.toString();
	}

	public static String buildAddServerGroupPermissionsCommand(int serverGroupID, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("servergroupaddperm");
		cmd.append(" sgid=").append(serverGroupID).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildAddServerGroupAutoPermissionsCommand(ServerGroupLevel groupLevel, List<Permission> permissions) {
		StringBuilder cmd = new StringBuilder("servergroupautoaddperm");
		cmd.append(" sgtype=").append(groupLevel.getValue()).append(" ");
		cmd.append(buildAddPermsArray(permissions));
		return cmd.toString();
	}

	public static String buildRemoveServerGroupAutoPermissionsCommand(ServerGroupLevel groupLevel, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("servergroupautodelperm");
		cmd.append(" sgtype=").append(groupLevel.getValue()).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildCopyServerGroupCommand(int sourceServerGroupID, int targetServerGroupID, String serverGroupName, ServerGroupType serverGroupType) {
		StringBuilder cmd = new StringBuilder("servergroupcopy");
		cmd.append(" ssgid=").append(sourceServerGroupID);
		cmd.append(" tsgid=").append(targetServerGroupID);
		cmd.append(" name=").append(Formatter.toTsFormat(serverGroupName));
		cmd.append(" type=").append(serverGroupType.getValue());
		return cmd.toString();
	}

	public static String buildDeleteServerGroupCommand(int serverGroupID, boolean force) {
		StringBuilder cmd = new StringBuilder("servergroupdel");
		cmd.append(" sgid=").append(serverGroupID);
		cmd.append(" force=").append(Formatter.toInt(force));
		return cmd.toString();
	}

	public static String buildRemoveClientDBIDsFromServerGroupCommand(int serverGroupID, List<Integer> dataBaseClientIDs) {
		StringBuilder cmd = new StringBuilder("servergroupdelclient");
		cmd.append(" sgid=").append(serverGroupID).append(" ");
		cmd.append(buildObjectArray(dataBaseClientIDs, "cldbid"));
		return cmd.toString();
	}

	public static String buildRenameServerGroupCommand(int serverGroupID, String name) {
		StringBuilder cmd = new StringBuilder("servergrouprename");
		cmd.append(" sgid=").append(serverGroupID);
		cmd.append(" name=").append(Formatter.toTsFormat(name));
		return cmd.toString();
	}

	public static String buildRemoveServerGroupPermissionsCommand(int serverGroupID, List<Integer> permissions, List<String> permissionNames) {
		StringBuilder cmd = new StringBuilder("servergroupdelperm");
		cmd.append(" sgid=").append(serverGroupID).append(" ");
		cmd.append(buildObjectArray(permissions, "permid"));
		if (!permissions.isEmpty() && !permissionNames.isEmpty())
			cmd.append(" ");
		cmd.append(buildObjectArray(permissionNames, "permsid"));
		return cmd.toString();
	}

	public static String buildAddVirtualServerTempPasswordCommand(String password, String description, long duration, int targetChannelID, String targetChannelPassword) {
		StringBuilder cmd = new StringBuilder("servertemppasswordadd");
		cmd.append(" pw=").append(Formatter.toTsFormat(password));
		cmd.append(" desc=").append(Formatter.toTsFormat(description));
		cmd.append(" duration=").append(duration);
		cmd.append(" tcid=").append(targetChannelID);
		cmd.append(" tcpw=").append(targetChannelPassword);
		return cmd.toString();
	}

	public static String buildDeleteVirtualServerTempPasswordCommand(String password) {
		StringBuilder cmd = new StringBuilder("servertemppassworddel");
		cmd.append(" pw=").append(Formatter.toTsFormat(password));
		return cmd.toString();
	}
	
	public static String buildGetVirtualServerTempPasswordsCommand() {
		return new StringBuilder("servertemppasswordlist").toString();
	}

	public static String buildCreateSnapshotCommand(String password) {
		StringBuilder cmd = new StringBuilder("serversnapshotcreate");
		if (password != null)
			cmd.append(" password=").append(password);
		return cmd.toString();
	}

	public static String buildAddAPIKeyCommand(APIScope scope, int lifetime, int clientDBID) {
		StringBuilder cmd = new StringBuilder("apikeyadd");
		cmd.append(" scope=").append(scope.getValue());
		if (lifetime != -1)
			cmd.append(" lifetime=").append(lifetime);
		if (clientDBID != -1)
			cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildDeleteAPIKeyCommand(int keyID) {
		StringBuilder cmd = new StringBuilder("apikeydel");
		cmd.append(" id=").append(keyID);
		return cmd.toString();
	}

	public static String buildGetAPIKeysCommand(int clientDBID, int startOffset, int limit) {
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
			cmd.append(" start=").append(startOffset);
		if (limit != -1)
			cmd.append(" duration=").append(limit);
		return cmd.toString();
	}

	public static String buildSetClientChannelGroupCommand(int channelGroupID, int channelID, int clientDBID) {
		StringBuilder cmd = new StringBuilder("setclientchannelgroup");
		cmd.append(" cgid=").append(channelGroupID);
		cmd.append(" cid=").append(channelID);
		cmd.append(" cldbid=").append(clientDBID);
		return cmd.toString();
	}

	public static String buildRegisterEventCommand(EventType eventType, int channelID) {
		StringBuilder cmd = new StringBuilder("servernotifyregister");
		cmd.append(" event=").append(eventType.getValue());
		if (channelID != -1)
			cmd.append(" id=").append(channelID);
		return cmd.toString();
	}
	
	public static String buildUnRegisterAllEventsCommand() {
		return "servernotifyunregister";
	}

	public static String buildObjectArray(List<? extends Object> objects, String key) {
		StringJoiner list = new StringJoiner("|");
	    objects.forEach(object -> {
	    	list.add(new StringBuilder(key).append("=").append(Formatter.toTsFormat(object.toString())));
	    });
		
		return list.toString();
	}

	public static String buildAddPermsArray(List<Permission> permissions) {
		StringJoiner result = new StringJoiner("|");
		permissions.forEach(perms -> {
			StringJoiner permBuilder = new StringJoiner(" ");
			
			if (perms.getPermID() != -1) {
				permBuilder.add(new StringBuilder("permid=").append(perms.getPermID()));
			}
			
			if (perms.getName() != null && !perms.getName().isEmpty())
				permBuilder.add(new StringBuilder("permsid=").append(perms.getName()));
			
			if (perms.getValue() != -1)
				permBuilder.add(new StringBuilder("permvalue=").append(perms.getValue()));

			permBuilder.add(new StringBuilder("permnegated=").append(Formatter.toInt(perms.isNegated())));
			permBuilder.add(new StringBuilder("permskip=").append(Formatter.toInt(perms.isSkip())));
			result.add(permBuilder.toString());
		});
		return result.toString();
	}

}
