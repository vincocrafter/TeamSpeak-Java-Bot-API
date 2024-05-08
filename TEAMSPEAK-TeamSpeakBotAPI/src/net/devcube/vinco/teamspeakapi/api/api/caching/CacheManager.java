/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 06.09.2023
 * 
 * Uhrzeit : 13:58:26
 */
package net.devcube.vinco.teamspeakapi.api.api.caching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.devcube.vinco.teamspeakapi.api.api.util.CommandBuilder;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

public class CacheManager {

	private final String TS_INFO_SEPARATOR = "\\|";
	private final String SEPARATOR = "_";
	private final String CLIENT = "CLIENT";
	private final String DB_CLIENT = "DBCLIENT";
	private final String CHANNEL = "CHANNEL";
	private final String QUERY = "QUERY";
	private final String VSERVER = "VSERVER";

	private final String CLIENT_LIST = "CLIENTLIST";
	private final String CHANNEL_LIST = "CHANNELLIST";
	private final String DBCLIENT_LIST = "DBCLIENTLIST";
	private final String S_GROUP_LIST = "SERVERGROUPLIST";
	private final String C_GROUP_LIST = "CHANNELGROUPLIST";
	private final String PERMS_LIST = "PERMISSIONLIST";
	private final String VSERVER_LIST = "VSERVERLIST";

	private final String PROPERTIES = "PROPERTIES";
	
	private Ts3ServerQuery query;
	private QueryConfig config;
	private Map<String, String> cache = new HashMap<String, String>();

	public CacheManager(Ts3ServerQuery query) {
		this.query = query;
		this.config = query.getConfig();
	}


	/**
	 * @return a copy of the cache
	 */
	public Map<String, String> getCache() {
		return new HashMap<>(cache);
	}

	public void prepareCache() {
		long startTime = System.currentTimeMillis();
		query.debug(DebugOutputType.CACHEMANAGER, "Preparing caching process for the first time....");
		
		if (config.isClientsCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating clients cache");
			updateClientListCache(); // update Clientlist
			List<Integer> clientIDs = new ArrayList<>();
			for (String clients : getClientsList().split(TS_INFO_SEPARATOR)) {
				clientIDs.add(Integer.parseInt(Formatter.get(clients, "clid=")));
			}
			
			updateClientsCache(clientIDs);
		}
		
		if (config.isDataBaseCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating database cache");
			updateDBClientsListCache();
			
			List<Integer> clientdbIDs = new ArrayList<>();
			for (String dbClients : getDBClientsList().split(TS_INFO_SEPARATOR)) {
				clientdbIDs.add(Integer.parseInt(Formatter.get(dbClients, "cldbid=")));
			}
			List<DataBaseClientInfo> clients = query.getBasicAPI().getDataBaseClientsByDBIDs(clientdbIDs);
			clients.forEach(client -> updateDBClientCache(client.getClientDataBaseID(), client.getRawInfos()));
		}
		
		if (config.isChannelsCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating channels cache");
			updateChannelsListCache();
			for (String channels : getChannelsList().split(TS_INFO_SEPARATOR)) {
				updateChannelCache(Integer.parseInt(Formatter.get(channels, "cid=")));
			}
		}
		
		if (config.isGroupsCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating groups cache");
			updateServerGroupsCache();
			updateChannelGroupsCache();
		}
		
		if (config.isPermissionCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating permissions cache");
			updatePermissionsList();
		}
		
		if (config.isQueryCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating query cache");
			updateQueryPropsCache();
		}
		
		if (config.isVirtualServerCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating virtualserver cache");
			updateVirtualServerCache();
			updateVirtualServerListCache();
		}
		
		query.debug(DebugOutputType.CACHEMANAGER, "Preparing caching process took " + (System.currentTimeMillis() - startTime) + " Miliseconds to finish!");

	}


	public void updateClientListCache() {
		cache.put(CLIENT_LIST, getInformation(CommandBuilder.buildGetClientsCommand())); // update clientlist
	}

	public void updateClientCache(int clientID) {
		cache.put(CLIENT + SEPARATOR + clientID, 
				getInformation(CommandBuilder.buildGetClientsByIDsCommand(Collections.singletonList(clientID))).concat(" clid=" + clientID));
	}
	
	public void updateClientsCache(List<Integer> clientIDs) {
		String info = getInformation(CommandBuilder.buildGetClientsByIDsCommand(clientIDs));

		int counter = 0;
		for (String clientInfo : info.split(TS_INFO_SEPARATOR)) {
			updateClientCache(clientIDs.get(counter), clientInfo);
			counter++;
		}
	}
	
	public void updateClientCache(int clientID, String information) {
		cache.put(CLIENT + SEPARATOR + clientID, information.concat(" clid=" + clientID));
	}
	
	public void updateChannelsListCache() {
		cache.put(CHANNEL_LIST, getInformation(CommandBuilder.buildGetChannelsCommand())); // update Channellist
	}
	
	public void updateChannelCache(int channelID) {
		cache.put(CHANNEL + SEPARATOR + channelID, 
				getInformation(CommandBuilder.buildGetChannelCommand(channelID)).concat(" cid=" + channelID)); // Update individual channel
	}

	public void updateServerGroupsCache() {
		cache.put(S_GROUP_LIST, getInformation(CommandBuilder.buildGetServerGroupsCommand())); // update channel groups
	}
	
	public void updateChannelGroupsCache() {
		cache.put(C_GROUP_LIST, getInformation(CommandBuilder.buildGetChannelGroupsCommand())); // update channel groups
	}
	
	public void updateDBClientsListCache() {
		cache.put(DBCLIENT_LIST, getInformation(CommandBuilder.buildGetDataBaseClientsCommand(-1, -1))); // update DataBaseclientslist
	}
	
	public void updateDBClientCache(int clientDBID) {
		cache.put(DB_CLIENT + SEPARATOR + clientDBID, 
				getInformation(CommandBuilder.buildGetDataBaseClientsByDBIDsCommand(Collections.singletonList(clientDBID))).replace("cldbid", "client_database_id")); // Update database client
	}
	
	public void updateDBClientCache(int clientDBID, String information) {
		cache.put(DB_CLIENT + SEPARATOR + clientDBID, information.replace("cldbid", "client_database_id")); // Update database client
	}
	
	public void updateVirtualServerCache() {
		cache.put(VSERVER + SEPARATOR + PROPERTIES, getInformation(CommandBuilder.buildGetServerInfoCommand()));
	}
	
	public void updateVirtualServerListCache() {
		cache.put(VSERVER_LIST, getInformation(CommandBuilder.buildGetVirtualServersCommand()));
	}
	
	public void updateQueryPropsCache() {
		cache.put(QUERY + SEPARATOR + PROPERTIES, getInformation(CommandBuilder.buildgetQueryInfoCommand()));
	}
	
	public void updateQueryPropsCache(String information) {
		cache.put(QUERY + SEPARATOR + PROPERTIES, information);
	}
	
	public void updatePermissionsList() {
		String pList = getInformation(CommandBuilder.buildGetPermissionListCommand());
		cache.put(PERMS_LIST, pList);
		
		//Would store the List of permission ids as seperated list like 1 2 3...
		 StringBuilder ids = new StringBuilder();
		for (String perm : pList.split("permid=")) {
			String permID = perm.split(" ")[0];
			if (!permID.isEmpty())
				ids.append(permID).append(" ");
		}
		cache.put(PERMS_LIST + SEPARATOR + "IDS", ids.toString());
	}
	
	private String getInformation(String cmd) {
		return query.getWriter().executeReadCommand(cmd)[0];
	}
	
	public String updateAttribute(String information, String splitter, String newValue) {
		String curValue = Formatter.get(information, splitter);
		String replOrg = splitter + curValue;
		return information.replace(replOrg, splitter + newValue);
	}
	
	public void cacheRemoveClient(int clientID) {
		cache.remove(CLIENT + SEPARATOR + clientID);
	}

	public void cacheRemoveChannel(int channelID) {
		cache.remove(CHANNEL + SEPARATOR + channelID);
	}

	public String getClientInfo(int clientID) {
		return cache.get(CLIENT + SEPARATOR + clientID);
	}

	public String getChannelInfo(int channelID) {
		return cache.get(CHANNEL + SEPARATOR + channelID);
	}

	public String getDBClientInfo(int clientDBID) {
		return cache.get(DB_CLIENT + SEPARATOR + clientDBID);
	}

	public String getClientsList() {
		return cache.get(CLIENT_LIST);
	}

	public String getChannelsList() {
		return cache.get(CHANNEL_LIST);
	}

	public String getDBClientsList() {
		return cache.get(DBCLIENT_LIST);
	}

	public String getServerGroupsList() {
		return cache.get(S_GROUP_LIST);
	}

	public String getChannelGroupsList() {
		return cache.get(C_GROUP_LIST);
	}

	public String getVirtualServerProperties() {
		return cache.get(VSERVER + SEPARATOR + PROPERTIES);
	}
	
	public String getVirtualServerList() {
		return cache.get(VSERVER_LIST);
	}
	
	public String getQueryProperties() {
		return cache.get(QUERY + SEPARATOR + PROPERTIES);
	}
	
	public String getPermissionsList() {
		return cache.get(PERMS_LIST);
	}
	
	public String getPermissionsIDsList() {
		return cache.get(PERMS_LIST + SEPARATOR + "IDS");
	}
}
