/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 20:00:09
 */
package net.devcube.vinco.teamspeakapi.query.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManagerUpdater;
import net.devcube.vinco.teamspeakapi.api.api.util.Command;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

	private Ts3ServerQuery query;
	private Socket socket;

	private Queue<Command> commands = new LinkedList<>();
	private Queue<Command> hover = new LinkedList<>();

	private boolean allowed = true;

	private Thread readerThread;
	private Thread eventThread;
	private Thread sleepThread;
	

	public QueryReader(Ts3ServerQuery query, Socket socket) {
		this.query = query;
		this.setSocket(socket);
	}

	/**
	 * Start listening for incoming packets async
	 */

	public void start() {
		query.debug(DebugOutputType.QUERYREADER, "Starting listening in QueryReader");
		FloodRate floodRate = query.getConfig().getFloodRate();

		readerThread = new Thread(new Runnable() { // New Thread so async

			@Override
			public void run() { // starting the while loop here to listen for packets
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					while (socket != null && socket.isConnected()) { // <-- while loop here
						if (reader.ready()) {
							String msg = rl(reader);
							if (isError(msg)) {// Error handeling
								query.debug(DebugOutputType.QUERYREADER, "Added to Errors: " + msg);
								query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Errors: " + msg);
								if (!hover.isEmpty())
									hover.poll().setError(msg);
								continue;
							}
							if (isEvent(msg)) { // Event here
								query.debug(DebugOutputType.QUERYREADER, "Read Event: " + msg);
								callEvents(msg);
								continue;
							}
							
							if (!hover.isEmpty()) {
								query.debug(DebugOutputType.QUERYREADER, "Added to Packets: " + msg);
								query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Packets: " + msg);
								hover.peek().getPackets().add(msg);
							}
								
						} else { // Execute Commands here
							if (!hover.isEmpty())
								continue;

							if (commands.isEmpty())
								continue;

							if (!allowed)
								continue;

							writer.println(commands.peek().getCommand());
							if (floodRate.getValue() > 0) {
								setCommandSenderSleeping(floodRate.getValue());
								query.debug(DebugOutputType.QUERYREADERQUEUE, "Send Command to Server > (" + commands.peek().getCommand() + ")");
							}
							hover.add(commands.poll());
							writer.flush();
						}
					}
				} catch (IOException e) {
					query.debug(DebugOutputType.ERROR, "Something went wrong with the QueryReader! Exception: IOException");
					e.printStackTrace();
				}
			}
		}, "QURT");
		readerThread.start();
	}

	private void callEvents(String msg) {
		String[] infos = msg.split(" ");
		
		
		Thread cacheManager = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if (!query.getConfig().getCachingList().isEmpty()) {
					query.debug(DebugOutputType.CACHEMANAGER, "Calling eventupdater for event " + infos[0]);
					query.getEventManager().callNewEventClass(infos, new CacheManagerUpdater());
				}
				
			}
		}, "CHMA");
		cacheManager.start();
		
		
		/*
		 * Decide which EventCall you prefer here the new one it is also the default one
		 * 
		 * @see QueryConfig#isEventCallType()
		 */
		
		
		eventThread = new Thread(new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					cacheManager.join();
				} catch (InterruptedException e1) {}
				
				try {					
					if (query.getConfig().isEventCallType(EventCallType.NEW)) { // New one
						query.getEventManager().callNewEvent(infos);
					} else if (query.getConfig().isEventCallType(EventCallType.OLD)) { // Old one
						query.getEventManager().callEvent(infos);
					} else { // And here both types of Event call
						query.getEventManager().callNewEvent(infos);
						query.getEventManager().callEvent(infos);
					}
				} catch (Exception e) {
					query.debug(DebugOutputType.ERROR, "Got an Exception from calling an event caused by " + e.getClass().getName() + "!");
					e.printStackTrace();
				}
			}
		}, "EVMA");
		eventThread.start();
	}

	private void setCommandSenderSleeping(int sleepTime) {
		allowed = false;
		sleepThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					
				}
				allowed = true;
			}
		}, "SLEP");
		sleepThread.start();
	}
	
	
	public synchronized void stopThreads() {
		commands.forEach(cmd -> cmd.setError("Stopped"));
		commands.clear();
		
		if (eventThread != null)
			eventThread.interrupt();
		if (sleepThread != null)
			sleepThread.interrupt();
		this.socket = null;
		readerThread.interrupt();
	}
	
	private boolean isError(String rs) {
		return rs.startsWith("error");
	}

	private boolean isEvent(String rs) {
		return rs.startsWith("notify");
	}
	
	private String rl(BufferedReader reader) throws IOException {
		StringBuilder line = new StringBuilder();
		int c;
		while ((c = reader.read()) != '\r') {
			if (c == '\n')
				continue;
			line.append((char) c);
		}
		return line.toString();
	}
	

	/**
	 * @return the commands
	 */
	public Queue<Command> getCommands() {
		return new LinkedList<>(commands);
	}
	
	protected synchronized void addCommand(Command command) {
		commands.add(command);
	}

	/**
	 * @param socket
	 *                   the socket to set
	 */
	private void setSocket(Socket socket) {
		this.socket = socket;
	}

}
