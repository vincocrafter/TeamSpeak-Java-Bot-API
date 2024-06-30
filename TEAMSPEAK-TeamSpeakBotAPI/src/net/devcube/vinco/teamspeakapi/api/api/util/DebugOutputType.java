/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 *Author : Vincent
 *
 *Jahr 2022  
 *
 *Datum : Mar 2, 2022
 * 
 *Uhrzeit : 10:01:07 AM
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

import net.devcube.vinco.teamspeakapi.api.api.util.Logger.TSLogLevel;

/**
 * Enum representing different types of debug output.
 */
public enum DebugOutputType {
  
    EVERYTHING(TSLogLevel.INFO),
    GENERAL(TSLogLevel.INFO),
    EVENTMANAGER(TSLogLevel.EVENT_MANAGER),  
    KEEPALIVETHREAD(TSLogLevel.INFO),
    QUERY(TSLogLevel.QUERY),             
    QUERYREADER(TSLogLevel.QUERY_READER), 
    QUERYWRITER(TSLogLevel.QUERY_WRITER),  
    ERROR(TSLogLevel.ERROR),               
    WARNING(TSLogLevel.WARNING),           
    QUERYREADERQUEUE(TSLogLevel.QUERY_READER_QUEUE), 
    CACHEMANAGER(TSLogLevel.CACHE_MANAGER),            
    
    E_CHANNEL_CREATED(TSLogLevel.EVENT_MANAGER),       
    E_CHANNEL_DELETED(TSLogLevel.EVENT_MANAGER),       
    E_CHANNEL_DESCRIPTION_EDITED(TSLogLevel.EVENT_MANAGER),  
    E_CHANNEL_EDITED(TSLogLevel.EVENT_MANAGER),               
    E_CHANNEL_PASSWORD_CHANGED(TSLogLevel.EVENT_MANAGER),     
    E_CHANNEL_MOVED(TSLogLevel.EVENT_MANAGER),              
    E_CLIENT_JOIN(TSLogLevel.EVENT_MANAGER),                 
    E_CLIENT_LEAVE(TSLogLevel.EVENT_MANAGER),                
    E_CLIENT_MOVE(TSLogLevel.EVENT_MANAGER),                 
    E_PRIVILEGEKEY_USED(TSLogLevel.EVENT_MANAGER),           
    E_SERVER_EDITED(TSLogLevel.EVENT_MANAGER),              
    E_TEXT_MESSAGE(TSLogLevel.EVENT_MANAGER);

	private final TSLogLevel logLevel;

    /**
     * Constructor for DebugOutputType enum.
     * @param logLevel The log level associated with the debug output type.
     */
    DebugOutputType(TSLogLevel logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Get the log level associated with the debug output type.
     * @return The log level.
     */
    public TSLogLevel getLogLevel() {
        return logLevel;
    }
}

