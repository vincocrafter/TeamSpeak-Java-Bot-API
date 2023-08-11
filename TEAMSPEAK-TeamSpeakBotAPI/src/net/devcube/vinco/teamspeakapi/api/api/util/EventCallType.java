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

import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryReader;

/**
 * Enum for the preferred EventType to call Events
 * @see QueryConfig#isEventCallType(EventCallType)
 * @see QueryReader#callEvents()
 */

public enum EventCallType {
	
	/**
	 * Is set as defaut
	 * @see QueryConfig#setEventCallType
	 * @see QueryConfig#isEventCallType
	 */
	
	NEW, 
	OLD, 
	BOTH;

}
