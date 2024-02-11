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

/**
 * Idea of specify the type of Debug the programmer wants from the API
 * 
 */

public enum DebugOutputType {
	
	EVERYTHING, 
	GENERAL, 
	EVENTMANAGER, 
	KEEPALIVETHREAD, 
	QUERY, 
	QUERYREADER, 
	QUERYWRITER, 
	ERROR, 
	WARNING,
	QUERYREADERQUEUE, 
	CACHEMANAGER,
	
	//to select debug for a specific Event
	E_CHANNEL_CREATED, 
	E_CHANNEL_DELETED, 
	E_CHANNEL_DESCRIPTION_EDITED, 
	E_CHANNEL_EDITED, 
	E_CHANNEL_PASSWORD_CHANGED,
	E_CHANNEL_MOVED, 
	E_CLIENT_JOIN, 
	E_CLIENT_LEAVE, 
	E_CLIENT_MOVE, 
	E_PRIVILEGEKEY_USED, 
	E_SERVER_EDITED, 
	E_TEXT_MESSAGE;
	
}
