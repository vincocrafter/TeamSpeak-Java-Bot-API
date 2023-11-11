/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 22.04.2023
 * 
 * Uhrzeit : 15:03:20
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

public final class FloodRate {
	
	public static FloodRate DEFAULT = new FloodRate(350); 
	public static FloodRate DEFAULT_TSAPI = new FloodRate(0);
	public static FloodRate UNLIMITED = new FloodRate(0);
	
	public static FloodRate custom(int mills) {
		if (mills > 0) {
			return new FloodRate(mills);
		}
		return null;
	}
	
	private int i = 0;

	private FloodRate(int i) {
		this.i = i;
	}

	public int getValue() {
		return this.i;
	}
}
