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
		try {
			query.connect("", 10011, "", "", 1, "", 1);
		} catch (IOException | QueryLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
