/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 12.02.2019
 * 
 *Uhrzeit : 15:15:05
 */
package me.vinco.teamspeakapi.apis.api.commandmanager;

import java.util.ArrayList;

import me.vinco.teamspeakapi.apis.api.events.TextMessageEvent;
import me.vinco.teamspeakapi.apis.api.wrapper.ClientInfo;

public class CommandManager {
	private ArrayList<Command> commands = new ArrayList<Command>();

	public void addComamnd(Command cmd) {
		if (cmd != null) {
			this.commands.add(cmd);
		}

	}

	public void removeCommand(Command cmd) {
		if (cmd != null && this.commands.contains(cmd)) {
			this.commands.remove(cmd);
		}

	}

	public int getCommandsSize() {
		return this.commands.size();
	}

	public void callCommand(String[] cmd, ClientInfo client, TextMessageEvent e) {
		if (cmd != null && client != null && e != null) {
			this.commands.forEach(command -> {
				if (command.getCommand().equalsIgnoreCase(cmd[0])) {
					command.onExecute(cmd, client, e);
				}
			});
		}
	}

	public ArrayList<Command> getCommands() {
		return commands;
	}
}
