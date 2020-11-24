/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 *Author : Vincent
 *
 *Jahr 2020  
 *
 *Datum : Nov 24, 2020
 * 
 *Uhrzeit : 9:40:21 AM
 */
package net.devcube.vinco.test;

import net.devcube.vinco.teamspeakapi.api.api.event.EventHandler;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientJoinEvent;

public class TsEvents extends TsEventAdapter {
	
	@EventHandler
	public void onTesting(ClientJoinEvent e) {
		System.out.println(e.toString());
	}
	
}
