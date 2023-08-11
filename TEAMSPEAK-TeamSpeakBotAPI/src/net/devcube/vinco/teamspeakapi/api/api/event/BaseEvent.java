package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsycAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

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
	
	public Ts3SyncAPI getSyncAPI() {
		return serverQuery.getSyncAPI();
	}
	
	public Ts3BasicAPI getBasicAPI() {
		return serverQuery.getBasicAPI();
	}
 	
	public Ts3AsycAPI getAsyncAPI() {
		return serverQuery.getAsycAPI();
	}

	public int getClientID() {
		return -1;
	}

	public String getClientName() {
		return "";
	}
}
