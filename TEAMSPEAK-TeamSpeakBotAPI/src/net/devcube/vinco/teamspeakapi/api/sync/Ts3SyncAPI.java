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

import net.devcube.vinco.teamspeakapi.api.api.event.TsEvent;
import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownClientInfoException;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Ban;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelGroup;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ChannelInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Complain;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ConnectionInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.HostInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.OfflineMessage;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.Permission;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.PrivilegeKey;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.QueryClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ServerGroup;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.VirtualServerInfo;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Ts3SyncAPI {

	private Ts3ServerQuery query;
	private boolean connected = false;

	/**
	 * Initiation of the Sync API
	 * 
	 * @param query class
	 */
	public Ts3SyncAPI(Ts3ServerQuery query) {
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * Connects the Query bot to the virtual server And changes the Query bot's name
	 * to the param nickname
	 * 
	 * @param serverID from the virtual server
	 * @param nickname the bots nick
	 */
	public void connectTeamSpeakQuery(int serverID, String nickname) {
		if (!this.isConnected()) {
			selectVirtualServer(serverID); // select the virtualServer
			query.getWriter().executeReadErrorCommand("clientupdate client_nickname=" + Formatter.toTsFormat(nickname));
			query.debug(DebugOutputType.QUERY, "Query is sucessfully connected");
			setConnected(true);
		} else {
			query.getLogger().log(Logger.ERROR, "Query is already conncted");
		}

	}

	/**
	 * Selects the virtual server to connect to
	 * 
	 * @param server_id of the virtual server
	 */
	public void selectVirtualServer(int server_id) {
		query.getWriter().executeReadErrorCommand("use " + server_id);
	}

	public void addTs3Listener(TsEvent event) {
		query.getEventManager().addTs3Listener(event);
	}

	public void pokeClient(int clientid, String message) {
		query.getWriter()
				.executeReadErrorCommand("clientpoke clid=" + clientid + " msg=" + Formatter.toTsFormat(message));
	}

	public void pokeClient(ClientInfo c, String message) {
		query.getWriter().executeReadErrorCommand(
				"clientpoke clid=" + c.getClientID() + " msg=" + Formatter.toTsFormat(message));
	}

	public void moveClient(int clientid, int channelid) {
		query.getWriter().executeReadErrorCommand("clientmove clid=" + clientid + " cid=" + channelid);
	}

	public void moveClient(ClientInfo c, int channelid) {
		query.getWriter().executeReadErrorCommand("clientmove clid=" + c.getClientID() + " cid=" + channelid);
	}

	public void moveClient(int clientid, ChannelInfo cinfo) {
		query.getWriter().executeReadErrorCommand("clientmove clid=" + clientid + " cid=" + cinfo.getChannelID());
	}

	public void moveClient(ClientInfo c, ChannelInfo cinfo) {
		query.getWriter()
				.executeReadErrorCommand("clientmove clid=" + c.getClientID() + " cid=" + cinfo.getChannelID());
	}

	/**
	 * Switches the channel of the query bot
	 * 
	 * @param channelID
	 */
	public void goToChannel(int channelID) {
		moveClient(getQueryInfo().getClientID(), channelID);
	}

	/**
	 * Returns information about the Query Client
	 * 
	 * @return QueryClientInfo
	 */

	public QueryClientInfo getQueryInfo() {
		return new QueryClientInfo(query.getWriter().executeReadCommand("whoami")[0].split(" "));
	}

	public int getVirtualServerIDByPort(int port) {
		// "serveridgetbyport virtualserver_port=" + port
		return -1;
	}

	public int getPermissionID(String permissionName) {
		// "permidgetbyname permsid=" + permissionName
		return -1;
	}

	public String getPermissionName(int permissionID) {
		// "permget permid=" + permissionID
		return null;
	}

	public ArrayList<Permission> getPermissionList() {
		ArrayList<Permission> arraylist = new ArrayList<Permission>();
		// "permissionlist"
		return arraylist;
	}

	public ArrayList<Permission> getChannelPermissions(int channelID) {
		ArrayList<Permission> arraylist = new ArrayList<Permission>();
		// "channelpermlist cid=" + channelID
		return arraylist;
	}

	public ArrayList<Permission> getClientPermissions(int clientDataBaseID) {
		ArrayList<Permission> arraylist = new ArrayList<Permission>();
		// "clientpermlist cldbid=" + clientDataBaseID
		return arraylist;
	}

	public ArrayList<ServerGroup> getServerGroups() {
		ArrayList<ServerGroup> arraylist = new ArrayList<ServerGroup>();
		// "servergrouplist"
		return arraylist;
	}

	public ArrayList<ChannelGroup> getChannelGroups() {
		ArrayList<ChannelGroup> arraylist = new ArrayList<ChannelGroup>();
		// "channelgrouplist"
		return arraylist;
	}

	public VirtualServerInfo getVirtualServerInfo() {
		return getServerInfo();
	}

	public VirtualServerInfo getServerInfo() {
		// "serverinfo"
		return null;
	}

	public ConnectionInfo getConnectionInfo() {
		// "serverrequestconnectioninfo"
		return null;
	}

	public OfflineMessage getOfflineMessage(int id) {
		// "messageget msgid=" + id
		return null;
	}

	public ArrayList<DataBaseClientInfo> getClientDBList() {
		ArrayList<DataBaseClientInfo> arraylist = new ArrayList<DataBaseClientInfo>();
		// "clientdblist"
		return arraylist;
	}

	public ArrayList<OfflineMessage> getOfflineMessages() {
		ArrayList<OfflineMessage> arraylist = new ArrayList<OfflineMessage>();
		// "messagelist"
		return arraylist;
	}

	public HostInfo getHostInfo() {
		// "hostinfo;
		return null;

	}

	public boolean isClientOnline(int clientID) {
		try {
			return getClientInfo(clientID) != null;
		} catch (UnknownClientInfoException var3) {
			return false;
		}
	}

	public boolean isClientOnline(String uuid) {
//		getOnlineClients().stream().anyMatch(c -> c.getClientUUID().equalsIgnoreCase(uuid));

		for (ClientInfo clientinfo : getOnlineClients()) {
			if (clientinfo.getClientUUID().equalsIgnoreCase(uuid)) {
				return true;
			}
		}

		return false;
	}

	public String getClientName(String clientUUID) {
		// "clientgetnamefromuid cluid=" + clientUUID
		return null;
	}

	public String getClientName(int clientDataBaseID) {
		// "clientgetnamefromdbid cldbid=" + clientDataBaseID
		return null;
	}

	public String getClientUUID(int clientDataBaseID) {
		// "clientgetnamefromdbid cldbid=" + clientDataBaseID
		return null;
	}

	public int getClientDataBaseID(String clientUUID) {
		// "clientgetdbidfromuid cluid=" + clientUUID
		return -1;
	}

	public ClientInfo getClientInfo(int clientID) throws UnknownClientInfoException {
		ClientInfo clientinfo = getClient(clientID);
		if (clientinfo != null) {
			return clientinfo;
		} else {
			throw new UnknownClientInfoException(
					"The Client is null! Please use the DataBaseClientInfo to get the Information of the Player");
		}
	}

	public ClientInfo getClient(int clientid) {
		// "clientinfo clid=" + clientid
		return null;
	}

	public ClientInfo getClientByUUID(String uuid) {
		for (ClientInfo clientinfo : this.getOnlineClients()) {
			if (clientinfo.getClientUUID().equalsIgnoreCase(uuid)) {
				return clientinfo;
			}
		}
		return null;
	}

	public ClientInfo getClientInfoByUUID(String uuid) throws UnknownClientInfoException {
		for (ClientInfo clientinfo : getOnlineClients()) {
			if (clientinfo.getClientUUID().equalsIgnoreCase(uuid)) {
				return clientinfo;
			}
		}

		throw new UnknownClientInfoException("Client with UUID :" + uuid + " was not found");
	}

	public ArrayList<ClientInfo> getOnlineClients() {
		ArrayList<ClientInfo> arraylist = new ArrayList<ClientInfo>();
		// "clientlist"
		return arraylist;

	}

	public int getOnlineClientSize() {
		return this.getVirtualServerInfo().getOnlineClientsSize();
	}

	public int getOnlineClientsRealSize() {
		int i = 0;

		for (ClientInfo clientinfo : this.getOnlineClients()) {
			if (!clientinfo.isServerQueryClient()) {
				++i;
			}
		}

		return i;
	}

	public DataBaseClientInfo getDataBaseClientInfo(int clientdatabaseID) {
		// "clientdbinfo cldbid=" + clientdatabaseID
		return null;

	}

	public ChannelInfo getChannel(int channelid) {
		// "channelinfo cid=" + channelid
		return null;

	}

	public ChannelInfo getChannelByName(String channelName) {
		for (ChannelInfo channelinfo : this.getChannels()) {
			if (channelinfo.getChannelName().equalsIgnoreCase(channelName)) {
				return channelinfo;
			}
		}

		return null;
	}

	public ArrayList<ChannelInfo> getChannels() {
		ArrayList<ChannelInfo> arraylist = new ArrayList<ChannelInfo>();
		// "channellist"
		return arraylist;
	}

	public String getVersion() {
		// "version"
		return null;
	}

	public ArrayList<Complain> getComplainsFormClient(int clientDataBaseID) {
		ArrayList<Complain> arraylist = new ArrayList<Complain>();
		return arraylist;
	}

	public ArrayList<PrivilegeKey> getPrivilegeKeys() {
		ArrayList<PrivilegeKey> arraylist = new ArrayList<PrivilegeKey>();
		// "tokenlist"
		return arraylist;
	}

	public ArrayList<Complain> getComplains() {
		ArrayList<Complain> arraylist = new ArrayList<Complain>();
		// "complainlist"
		return arraylist;
	}

	public ArrayList<Ban> getBans() {
		ArrayList<Ban> arraylist = new ArrayList<Ban>();
		// "banlist"
		return arraylist;
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

}
