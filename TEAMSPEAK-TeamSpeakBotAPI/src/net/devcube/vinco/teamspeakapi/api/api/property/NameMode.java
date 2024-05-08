/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 23.03.2024
 * 
 * Uhrzeit : 21:04:23
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum NameMode {

	NONE(0),
	BEFORE(1),
	BEHIND(2);

	private int value = 0;

	private NameMode(int i) {
		this.value = i;
	}

	public int getValue() {
		return this.value;
	}

}
