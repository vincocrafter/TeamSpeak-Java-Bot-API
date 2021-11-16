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
package net.devcube.vinco.teamspeakapi.query.manager;

import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryConfig {
	
private Ts3ServerQuery query;
		
	private boolean debug = false;
	private boolean eventDebug = false;
	private boolean keepAliveDebug = false;
	private EventCallType eventCallType = EventCallType.NEW;
	
	
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

	/**
	 * @return the eventCallType
	 */
	public EventCallType getEventCallType() {
		return eventCallType;
	}

	/**
	 * @param eventCallType the eventCallType to set
	 */
	public void setEventCallType(EventCallType eventCallType) {
		this.eventCallType = eventCallType;
	}
	
	
}
