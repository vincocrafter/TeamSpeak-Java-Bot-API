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

import java.util.ArrayList;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryConfig {
	
private Ts3ServerQuery query;
		

	
	private EventCallType eventCallType = EventCallType.NEW;
	
	/**
	 * idea of specify much more the types of output, array to select more as one type for more detailed debugging
	 * @see Ts3ServerQuery.debug()
	 */
	private ArrayList<DebugOutputType> debuglist = new ArrayList<DebugOutputType>();
	
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
	 * @return the eventCallType
	 */
	public EventCallType getEventCallType() {
		return eventCallType;
	}

	/**
	 * CHange the type between the old and the new system to call Events
	 * @param eventCallType the eventCallType to set
	 */
	public void setEventCallType(EventCallType eventCallType) {
		this.eventCallType = eventCallType;
	}
	
	/**
	 * Get a list of all selected Types of debugging
	 * @return the debuglist
	 */
	public ArrayList<DebugOutputType> getDebuglist() {
		return debuglist;
	}
	
	//new booleans for new Method

	public void addDebugItem(DebugOutputType debugOutputType) {
		debuglist.add(debugOutputType);
	}
	
	public void removeDebugItem(DebugOutputType debugOutputType) {
		debuglist.remove(debugOutputType);
	}
	
	
	public boolean isInDebug(DebugOutputType debugOutputType) {
		return debuglist.contains(debugOutputType);
	}
	
	public boolean isEverything() {
		return isInDebug(DebugOutputType.EVERYTHING);
	}
	
	public boolean isGeneralDebug() {
		return isInDebug(DebugOutputType.GENERAL);
	}
	
	public boolean isEventManagerDebug() {
		return isInDebug(DebugOutputType.EVENTMANAGER);
	}
	
	public boolean isKeepAliveThreadDebug() {
		return isInDebug(DebugOutputType.KEEPALIVETHREAD);
	}
	
	public boolean isQueryDebug() {
		return isInDebug(DebugOutputType.QUERY);
	}
	
	public boolean isQueryReaderDebug() {
		return isInDebug(DebugOutputType.QUERYREADER);
	}
	
	public boolean isQueryReaderQueueDebug() {
		return isInDebug(DebugOutputType.QUERYREADERQUEUE);
	}
	
	public boolean isQueryWriterDebug() {
		return isInDebug(DebugOutputType.QUERYWRITER);
	}
	 
}
