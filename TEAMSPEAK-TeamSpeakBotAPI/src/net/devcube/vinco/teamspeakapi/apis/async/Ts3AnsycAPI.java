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
package net.devcube.vinco.teamspeakapi.apis.async;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Ts3AnsycAPI {
		
	private Ts3ServerQuery query;
	
	
	public Ts3AnsycAPI(Ts3ServerQuery query ) {
		this.query = query;
	}
	
	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}
	
	
	
}
