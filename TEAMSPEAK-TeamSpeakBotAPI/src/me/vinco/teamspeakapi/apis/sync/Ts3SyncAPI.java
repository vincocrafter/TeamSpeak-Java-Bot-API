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
package me.vinco.teamspeakapi.apis.sync;

import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Ts3SyncAPI {
	
	private Ts3ServerQuery query;
	
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
	
	
	
}
