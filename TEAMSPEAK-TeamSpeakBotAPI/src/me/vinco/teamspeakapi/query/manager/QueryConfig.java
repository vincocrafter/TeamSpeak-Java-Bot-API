/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 20:06:48
 */
package me.vinco.teamspeakapi.query.manager;

import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryConfig {
	
private Ts3ServerQuery query;
		
	private boolean debug;
	private boolean eventDebug;
	private boolean keepAliveDebug;
	
	
	public QueryConfig(Ts3ServerQuery query) {
		this.query = query;
	}
	
	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the eventDebug
	 */
	public boolean isEventDebug() {
		return eventDebug;
	}

	/**
	 * @param eventDebug the eventDebug to set
	 */
	public void setEventDebug(boolean eventDebug) {
		this.eventDebug = eventDebug;
	}
	
	/**
	 * @return the keepAliveDebug
	 */
	public boolean isKeepAliveDebug() {
		return keepAliveDebug;
	}
	
	/**
	 * @param keepAliveDebug the keepAliveDebug to set
	 */
	public void setKeepAliveDebug(boolean keepAliveDebug) {
		this.keepAliveDebug = keepAliveDebug;
	}
	
	
}
