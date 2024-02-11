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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.ClientInfo;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

public class CacheManager {

	private final String TS_INFO_SEPERATOR = "\\|";
	private final String SEPERATOR = "_";
	private final String CLIENT = "CLIENT";
	private final String DBCLIENT = "DBCLIENT";
	private final String CHANNEL = "CHANNEL";
	private final String QUERY = "QUERY";
	private final String VSERVER = "VSERVER";

	private final String CLIENTLIST = "CLIENTLIST";
	private final String CHANNELLIST = "CHANNELLIST";
	private final String DBCLIENTLIST = "DBCLIENTLIST";
	private final String SGROUPLIST = "SERVERGROUPLIST";
	private final String CGROUPLIST = "CHANNELGROUPLIST";
	private final String PERMSLIST = "PERMISSIONLIST";

	private final String PROPERTIES = "PROPERTIES";
	
	private Ts3ServerQuery query;
	private QueryConfig config;
	private Map<String, String> cache = new HashMap<String, String>();

	public CacheManager(Ts3ServerQuery query) {
		this.query = query;
		this.config = query.getConfig();
	}


	/**
	 * @return a copy the cache
	 */
	public Map<String, String> getCache() {
		return new HashMap<>(cache);
	}

	public void prepareCache() {
		query.debug(DebugOutputType.CACHEMANAGER, "Starting caching process for the first time....");
		
		if (config.isClientsCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating clients cache");
			updateClientListCache(); // update Clientlist
			List<Integer> clientIDs = new ArrayList<>();
			for (String clients : getClientsList().split(TS_INFO_SEPERATOR)) {
				clientIDs.add(Integer.parseInt(Formatter.get(clients, "clid=")));
			}
			
			List<ClientInfo> clients = query.getBasicAPI().getClientsByIDs(clientIDs);
			for (ClientInfo client : clients) {
				updateClientCache(client.getID(), client.getRawInfos());
			}
		}
		
		if (config.isDataBaseCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating database cache");
			updateDBClientsListCache();
			
			List<Integer> clientdbIDs = new ArrayList<>();
			for (String dbClients : getDBClientsList().split(TS_INFO_SEPERATOR)) {
				clientdbIDs.add(Integer.parseInt(Formatter.get(dbClients, "cldbid=")));
			}
			List<DataBaseClientInfo> clients = query.getBasicAPI().getDataBaseClientsByDBIDs(clientdbIDs);
			for (DataBaseClientInfo client : clients) {
				updateDBClientCache(client.getClientDataBaseID(), client.getRawInfos());
			}
		}
		
		if (config.isChannelsCached()) {
			query.debug(DebugOutputType.CACHEMANAGER, "Updating channels cache");
			updateChannelsListCache();
			for (String channels : getChannelsList().split(TS_INFO_SEPERATOR)) {
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
		}

		query.debug(DebugOutputType.CACHEMANAGER, "Starting caching process finished!");

	}


	public void updateClientListCache() {
		cache.put(CLIENTLIST, getInformation("clientlist -uid -away -voice -times -groups -info -country -ip -badges")); // update clientlist
	}

	public void updateClientCache(int clientID) {
		cache.put(CLIENT + SEPERATOR + clientID, getInformation("clientinfo clid=" + clientID).concat(" clid=" + clientID));
	}
	
	public void updateClientCache(int clientID, String information) {
		cache.put(CLIENT + SEPERATOR + clientID, information.concat(" clid=" + clientID));
	}
	
	public void updateChannelsListCache() {
		cache.put(CHANNELLIST, getInformation("channellist -topic -flags -voice -limits -icon -secondsempty -banners")); // update Channellist
	}
	
	public void updateChannelCache(int channelID) {
		cache.put(CHANNEL + SEPERATOR + channelID, getInformation("channelinfo cid=" + channelID).concat(" cid=" + channelID)); // Update individual channel
	}

	
	public void updateServerGroupsCache() {
		cache.put(SGROUPLIST, getInformation("servergrouplist")); // update channel groups
	}
	
	public void updateChannelGroupsCache() {
		cache.put(CGROUPLIST, getInformation("channelgrouplist")); // update channel groups
	}
	
	public void updateDBClientsListCache() {
		cache.put(DBCLIENTLIST, getInformation("clientdblist")); // update DataBaseclientslist
	}
	
	public void updateDBClientCache(int clientDBID) {
		cache.put(DBCLIENT + SEPERATOR + clientDBID, getInformation("clientdbinfo cldbid=" + clientDBID).replace("cldbid", "client_database_id")); // Update database client
	}
	
	public void updateDBClientCache(int clientDBID, String information) {
		cache.put(DBCLIENT + SEPERATOR + clientDBID, information.replace("cldbid", "client_database_id")); // Update database client
	}
	
	public void updateVirtualServerCache() {
		cache.put(VSERVER + SEPERATOR + PROPERTIES, getInformation("serverinfo"));
	}
	
	public void updateQueryPropsCache() {
		cache.put(QUERY + SEPERATOR + PROPERTIES, getInformation("whoami"));
	}
	
	public void updateQueryPropsCache(String information) {
		cache.put(QUERY + SEPERATOR + PROPERTIES, information);
	}
	
	public void updatePermissionsList() {
		cache.put(PERMSLIST, getInformation("permissionlist"));
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
		cache.remove(CLIENT + SEPERATOR + clientID);
	}

	public void cacheRemoveChannel(int channelID) {
		cache.remove(CHANNEL + SEPERATOR + channelID);
	}

	public String getClientInfo(int clientID) {
		return cache.get(CLIENT + SEPERATOR + clientID);
	}

	public String getChannelInfo(int channelID) {
		return cache.get(CHANNEL + SEPERATOR + channelID);
	}

	public String getDBClientInfo(int clientDBID) {
		return cache.get(DBCLIENT + SEPERATOR + clientDBID);
	}

	public String getClientsList() {
		return cache.get(CLIENTLIST);
	}

	public String getChannelsList() {
		return cache.get(CHANNELLIST);
	}

	public String getDBClientsList() {
		return cache.get(DBCLIENTLIST);
	}

	public String getServerGroupsList() {
		return cache.get(SGROUPLIST);
	}

	public String getChannelGroupsList() {
		return cache.get(CGROUPLIST);
	}

	public String getVirtualServerProperties() {
		return cache.get(VSERVER + SEPERATOR + PROPERTIES);
	}
	
	public String getQueryProperties() {
		return cache.get(QUERY + SEPERATOR + PROPERTIES);
	}
	
	public String getPermissionsList() {
		return cache.get(PERMSLIST);
	}
}
