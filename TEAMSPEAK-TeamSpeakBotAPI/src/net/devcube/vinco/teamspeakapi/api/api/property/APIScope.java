/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 26.11.2023
 * 
 * Uhrzeit : 15:46:05
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum APIScope {

	MANAGE("manage"), 
	WRITE("write"), 
	READ("read");

	String value = "";

	private APIScope(String s) {
		this.value = s;
	}

	public String getValue() {
		return this.value;
	}

}
