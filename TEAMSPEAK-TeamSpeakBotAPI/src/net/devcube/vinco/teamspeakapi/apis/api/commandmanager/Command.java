/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 12.02.2019
 * 
 *Uhrzeit : 15:15:10
 */
package net.devcube.vinco.teamspeakapi.apis.api.commandmanager;

import net.devcube.vinco.teamspeakapi.apis.api.events.TextMessageEvent;
import net.devcube.vinco.teamspeakapi.apis.api.wrapper.ClientInfo;

public class Command {
	   private String name;
	   private String command;
	   private String help;

	   public Command(String name, String command, String help) {
	      this.name = name;
	      this.command = command;
	      this.help = help;
	   }

	   public void onExecute(String[] cmd, ClientInfo c, TextMessageEvent e) {
		   
	   	}

	   public String getName() {
	      return this.name;
	   }

	   public String getCommand() {
	      return this.command;
	   }

	   public String getHelp() {
	      return this.help;
	   }
}
