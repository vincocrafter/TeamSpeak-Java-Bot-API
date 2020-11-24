/**
 * Projekt: TEAMSPEAK-TeamSpeakBotAPI
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 29.05.2019
 * 
 *Uhrzeit : 14:31:15
 */
package net.devcube.vinco.test;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Test {
	
	static Ts3ServerQuery query = new Ts3ServerQuery();
	
	public static void main(String[] args) {
		query.getConfig().setDebug(true);
		query.getConfig().setEventDebug(true);
		query.getEventManager().addTs3Listener(new TsEvents());
		query.getEventManager().callNewEvent("ClientJoinEvent", null);
		System.exit(0);
		
	}
}
