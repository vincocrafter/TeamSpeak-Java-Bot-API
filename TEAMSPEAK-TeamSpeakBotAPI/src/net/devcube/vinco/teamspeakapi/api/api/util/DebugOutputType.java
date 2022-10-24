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
	
	EVERYTHING, GENERAL, EVENTMANAGER, KEEPALIVETHREAD, QUERY, QUERYREADER, QUERYWRITER, 
	
	/**
	 *  Debug for changes in the QueryReader Queues
	 *  
	 *  @see QueryReader
	 */
	QUERYREADERQUEUE,
	
	//to select debug for a specific Event
	CHANNEL_CREATED, CHANNEL_DELETED, CHANNEL_DESCRIPTION_EDITED, CHANNEL_EDITED, CHANNEL_PASSWORD_CHANGED,
	CLIENT_JOIN, CLIENT_LEAVE, CLIENT_MOVE, PRIVILEGEKEY_USED, SERVER_EDITED, TEXT_MESSAGE;
	
}
