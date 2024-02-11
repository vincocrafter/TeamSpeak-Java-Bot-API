package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManager;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

public class BaseEvent extends DefaultInfo {

	private Ts3ServerQuery serverQuery;
	
	public BaseEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos);
		this.serverQuery = serverQuery;
	}

	
	/**
	 * @return the serverQuery
	 */
	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
	
	public QueryConfig getConfig() {
		return serverQuery.getConfig();
	}
	
	public CacheManager getCache() {
		return serverQuery.getCache();
	}
	
	public Ts3SyncAPI getSyncAPI() {
		return serverQuery.getSyncAPI();
	}
	
	public Ts3BasicAPI getBasicAPI() {
		return serverQuery.getBasicAPI();
	}
 	
	public Ts3AsyncAPI getAsyncAPI() {
		return serverQuery.getAsyncAPI();
	}
		
	public int getClientID() {
		return -1;
	}

	public String getClientName() {
		return "";
	}
}
