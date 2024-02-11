/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 01.12.2023
 * 
 * Uhrzeit : 12:43:11
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

import java.util.ArrayList;
import java.util.List;

public class Command {
	
	private String command;
	private List<String> packets;
	private String error;
	
	
	public Command(String command) {
		this.command = command;
		this.packets = new ArrayList<>();		
	}
	
	public synchronized boolean isFinished() {
		return error != null;
	}
		
	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * @param error the error to set
	 */
	public synchronized void setError(String error) {
		this.error = error;
	}
	
	/**
	 * @return the packets
	 */
	public List<String> getPackets() {
		return packets;
	}
	
	public String getResult() {
		StringBuilder resPackets = new StringBuilder();
		packets.forEach(result -> {
			resPackets.append(result);
			resPackets.append(" ");
		});
		return resPackets.toString();
	}
	

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	
}
