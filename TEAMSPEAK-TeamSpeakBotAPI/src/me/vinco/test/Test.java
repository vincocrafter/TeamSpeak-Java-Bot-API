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
package me.vinco.test;

import java.io.IOException;

import me.vinco.teamspeakapi.apis.api.exception.query.QueryLoginException;
import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Test {
	
	static Ts3ServerQuery query = new Ts3ServerQuery();
	
	public static void main(String[] args) {
		query.getConfig().setDebug(true);
		try {
			query.connect("94.16.119.194", 10011, "", "", 1, "Ts³-Bot", 1);
		} catch (IOException | QueryLoginException e) {
		
			e.printStackTrace();
		}
	}
}
