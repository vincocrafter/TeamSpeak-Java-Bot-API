/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.04.2023
 * 
 * Uhrzeit : 12:58:38
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum FileType {
	
   FILE(0),
   DIRECTORY(1);

   int i;

   private FileType(int i) {
      this.i = i;
   }

   public int getValue() {
      return this.i;
   }
}

