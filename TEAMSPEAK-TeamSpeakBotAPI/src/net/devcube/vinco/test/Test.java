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

import java.io.IOException;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Test {
	
	static Ts3ServerQuery query = new Ts3ServerQuery();
	
	public static void main(String[] args) {
		query.getConfig().setDebug(true);
		try {
			query.connect("hostnane", 10011, "Username", "Passwort", 1, "Ts³-Bot", 1);
		} catch (IOException | QueryLoginException e) {
		
			e.printStackTrace();
		}
		
	}
}
