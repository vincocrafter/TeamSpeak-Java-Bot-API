/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 19:14:06
 */
package net.devcube.vinco.teamspeakapi.api.async;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Ts3AsycAPI {
		
	private Ts3ServerQuery query;
	
	
	public Ts3AsycAPI(Ts3ServerQuery query ) {
		this.query = query;
	}
	
	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}
	
	
	
}
