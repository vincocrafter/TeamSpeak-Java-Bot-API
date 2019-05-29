/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 20:00:09
 */
package me.vinco.teamspeakapi.query.manager;

import java.net.Socket;

import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

	private Ts3ServerQuery query;
	Socket socket;

	public QueryReader(Ts3ServerQuery query, Socket socket) {
		this.query = query;
		this.socket = socket;
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * start listening for packets
	 */

	public void start() {
		
	}
	
	public boolean isError(String rs) {
		return rs.startsWith("error");
	}
	
	public boolean isResultValid(String rs) {
		if (rs == null)
			return false;
		if(rs.isEmpty())
			return false;
		
	
		return true;
	}
	
}
