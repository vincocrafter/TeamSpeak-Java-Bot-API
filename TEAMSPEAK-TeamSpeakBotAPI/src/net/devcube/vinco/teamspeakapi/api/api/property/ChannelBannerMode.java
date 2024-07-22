/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 19.07.2024
 * 
 * Uhrzeit : 14:34:18
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ChannelBannerMode {
	
	   NOT_ADJUST(0),
	   ADJUST_IGNORE_ASPECT_RATIO(1),
	   ADJUST_KEEP_ASPECT_RATIO(2);

	   private int value = 0;

	   
	   private ChannelBannerMode(int i) {
	      this.value = i;
	   }

	   public int getValue() {
	      return this.value;
	   }
	
}
