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
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.util.CacheType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryConfig {

	private Ts3ServerQuery query;

	private boolean showTimeMilliseconds = false;
	private boolean showDate = false;

	/**
	 *  ENUM for specifing Type of Outputting : CONSOLE, FILE, BOTH 
	 */
	private DebugType debugType = DebugType.CONSOLE;

	/**
	 *  ENUM for specifing Type of EventCalling : OLD, NEW, BOTH
	 */
	private EventCallType eventCallType = EventCallType.NEW;

	/**
	 * idea of specify much more the types of output, array to select more as one
	 * type for more detailed debugging
	 * 
	 * @see Ts3ServerQuery#debug()
	 */
	private List<DebugOutputType> debuglist = new ArrayList<DebugOutputType>();
	
	private List<CacheType> cacheList = new ArrayList<CacheType>();
	
	private FloodRate floodRate = FloodRate.DEFAULT_TSAPI;
	private long connectionTimeout = 2000;
	
	
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
	 * Change the type between the old and the new system to call Events
	 * 
	 * @param eventCallType
	 *                          the eventCallType to set
	 */
	public void setEventCallType(EventCallType eventCallType) {
		this.eventCallType = eventCallType;
	}
	
	public boolean isEventCallType(EventCallType eventCallType) {
		return this.eventCallType == eventCallType;
	}
	
	/**
	 * @return the debugType
	 */
	public DebugType getDebugType() {
		return debugType;
	}

	/**
	 * @param debugType
	 *                      the debugType to set
	 */
	public void setDebugType(DebugType debugType) {
		this.debugType = debugType;
	}

	/**
	 * Get a list of all selected Types of debugging
	 * 
	 * @return the debuglist
	 */
	public List<DebugOutputType> getDebuglist() {
		return debuglist;
	}

	public void addDebugItem(DebugOutputType debugOutputType) {
		debuglist.add(debugOutputType);
	}

	public void removeDebugItem(DebugOutputType debugOutputType) {
		debuglist.remove(debugOutputType);
	}

	public boolean isDebugType(DebugType debugType) {
		return this.debugType == debugType;
	}
	
	/**
	 * Checks debugTypes are set in the debuglist.
	 * Used by the Query to retrieve which debugs should
	 * be debugged.
	 * @see QueryConfig#addDebugItem(DebugOutputType)
	 * @see Ts3ServerQuery#debug(DebugOutputType, String)
	 * @param debugOutputType
	 * @return
	 */

	public boolean isInDebug(DebugOutputType debugOutputType) {
		return debuglist.contains(debugOutputType);
	}

	public boolean isEverything() {
		return isInDebug(DebugOutputType.EVERYTHING);
	}

	public boolean isGeneralDebug() {
		return isInDebug(DebugOutputType.GENERAL);
	}
	
	public boolean isErrorDebug() {
		return isInDebug(DebugOutputType.ERROR);
	}

	public boolean isWarningDebug() {
		return isInDebug(DebugOutputType.WARNING);
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
	
	public boolean isCacheManagerDebug() {
		return isInDebug(DebugOutputType.CACHEMANAGER);
	}
	
	/**
	 * Get a list of all selected Types of debugging
	 * 
	 * @return the debuglist
	 */
	public List<CacheType> getCachingList() {
		return cacheList;
	}

	public void addCacheItem(CacheType cacheType) {
		cacheList.add(cacheType);
	}

	public void removeCacheItem(CacheType cacheType) {
		cacheList.remove(cacheType);
	}

	/**
	 * Checks cacheTypes are set in the cachelist.
	 * Used by the Query to retrieve which Objects should
	 * be cached.
	 * @see QueryConfig#addCacheItem(CacheType)
	 * @param cacheType
	 * @return
	 */

	public boolean isInCache(CacheType cacheType) {
		return cacheList.contains(cacheType);
	}
	
	public boolean isClientsCached() {
		return isInCache(CacheType.CLIENTS);
	}
	
	public boolean isChannelsCached() {
		return isInCache(CacheType.CHANNELS);
	}
	
	public boolean isGroupsCached() {
		return isInCache(CacheType.GROUPS);
	}
	
	public boolean isDataBaseCached() {
		return isInCache(CacheType.DATABASE);
	}
	
	public boolean isPermissionCached() {
		return isInCache(CacheType.PERMISSION);
	}
	
	public boolean isQueryCached() {
		return isInCache(CacheType.QUERY);
	}
	
	public boolean isVirtualServerCached() {
		return isInCache(CacheType.VIRTUALSERVER);
	}
	
	
	/**
	 * Time in Logging with Milliseconds
	 * 
	 * @return the timeMilliseconds
	 */
	public boolean isShowTimeMilliseconds() {
		return showTimeMilliseconds;
	}

	/**
	 * Change in the Logging with/without Milliseconds
	 * 
	 * @param timeMilliseconds
	 *                             the timeMilliseconds to set
	 */
	public void setShowTimeMilliseconds(boolean timeMilliseconds) {
		this.showTimeMilliseconds = timeMilliseconds;
	}

	/**
	 * @return the showDate
	 */
	public boolean isShowDate() {
		return showDate;
	}

	/**
	 * @param showDate
	 *                     the showDate to set
	 */
	public void setShowDate(boolean showDate) {
		this.showDate = showDate;
	}
	
	/**
	 * @return the floodRate
	 */
	public FloodRate getFloodRate() {
		return floodRate;
	}
	
	/**
	 * @param floodRate the floodRate to set
	 */
	public void setFloodRate(FloodRate floodRate) {
		this.floodRate = floodRate;
	}
	
	/**
	 * @param connectionTimeout the connectionTimeout to set
	 */
	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	/**
	 * @return the connectionTimeout
	 */
	public long getConnectionTimeout() {
		return connectionTimeout;
	}
}
