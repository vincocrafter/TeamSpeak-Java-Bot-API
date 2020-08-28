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
package net.devcube.vinco.teamspeakapi.apis.sync;

import net.devcube.vinco.teamspeakapi.apis.api.event.TsEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;


public class Ts3SyncAPI {
	
	private Ts3ServerQuery query;
	private boolean connected = false;
	
	/**
	 * @param query
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
	
	public void connectTeamSpeakQuery(int serverID) {
		if (!this.isConnected()) {
			selectVirtualServer(serverID);
			if (query.getConfig().isDebug()) {
				query.getLogger().log(1, "Query is sucessfully connceted");
			}

			setConnected(true);
			query.readAllMessages();
		} else {
			query.getLogger().log(3, "Query is already conncted");
		}

	}

	/**
	 * @param serverid
	 */
	public void selectVirtualServer(int serverid) {
		query.getWriter().executeCommand("use " + serverid);
	}
	
	public void addTs3Listener(TsEvent event) {
		this.query.getEventManager().addTs3Listener(event);
	}

	/**
	 * @param channelID
	 */
	public void goToChannel(int channelID) {
		
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
