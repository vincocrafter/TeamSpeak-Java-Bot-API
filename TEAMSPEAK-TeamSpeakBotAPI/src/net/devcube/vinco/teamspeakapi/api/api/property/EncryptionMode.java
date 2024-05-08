/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 03.05.2024
 * 
 * Uhrzeit : 21:45:46
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum EncryptionMode {

	INDIVIDUAL(0), 
	GLOBAL_DISABLED(1), 
	GLOBAL_ENABLED(2);

	private int value;

	private EncryptionMode(int i) {
		this.value = i;
	}

	public int getValue() {
		return this.value;
	}

}
