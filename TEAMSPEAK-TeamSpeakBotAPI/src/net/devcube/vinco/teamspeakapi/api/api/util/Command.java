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
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Command<I> {
	
	private String command;
	private List<String> packets;
	private String error;
	private CommandFuture<I> future;
	private AtomicBoolean finished = new AtomicBoolean(false);


	public Command(String command, CommandFuture<I> future) {
		this.command = command;
		this.packets = new ArrayList<>();
		this.future = future;
	}
	
	public boolean isFinished() {
		return finished.get();
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
		finished.set(true);
		if (future != null && future.getOnFinish() != null) {
			future.getOnFinish().accept(future.get());
		}
	}
	
	/**
	 * @return the packets
	 */
	public List<String> getPackets() {
		return packets;
	}
	
	public String getResult() {
		StringJoiner resPackets = new StringJoiner(System.lineSeparator());
	    packets.forEach(resPackets::add);
	    return resPackets.toString();
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
}
