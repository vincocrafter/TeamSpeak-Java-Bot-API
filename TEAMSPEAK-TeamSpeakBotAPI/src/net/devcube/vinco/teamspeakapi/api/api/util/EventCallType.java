/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 *Author : Vincent
 *
 *Jahr 2021  
 *
 *Datum : Nov 16, 2021
 * 
 *Uhrzeit : 9:57:28 AM
 */
package net.devcube.vinco.teamspeakapi.api.api.util;


/**
 * Enum for the preferred EventType to call Events
 * @see QueryConfig.eventCallType
 * @see QueryReader.start()
 */

public enum EventCallType {

	NEW, //is set as default -> see QueryConfig.eventCallType
	OLD, BOTH;

}
