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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

	private Ts3ServerQuery query;
	private Socket socket;

	private Queue<String> commands = new LinkedList<>();
	private Queue<String> hover = new LinkedList<>();

	private Queue<ArrayList<String>> resultpackets = new LinkedList<>();
	private Queue<ArrayList<String>> resulterrors = new LinkedList<>();
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
		resultpackets.add(new ArrayList<>());
		FloodRate floodRate = query.getConfig().getFloodRate();

		readerThread = new Thread(new Runnable() { // New Thread so async

			@Override
			public void run() { // starting the while loop here to listen for packets
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					PrintWriter writer = new PrintWriter(socket.getOutputStream());
					while (socket.isConnected()) { // <-- while loop here
						if (reader.ready()) {
							String msg = rl(reader);
							if (isError(msg)) {// Error handeling
								query.debug(DebugOutputType.QUERYREADER, "Added to Errors: " + msg);
								query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Errors: " + msg);
								resulterrors.peek().add(msg);
								if (!hover.isEmpty())
									hover.poll();
								continue;
							}
							if (isEvent(msg)) { // Event here
								callEvents(msg);
								continue;
							}

							query.debug(DebugOutputType.QUERYREADER, "Added to Packets: " + msg);
							query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Packets: " + msg);
							resultpackets.peek().add(msg);
						} else { // Execute Commands here
							if (!hover.isEmpty())
								continue;

							if (commands.isEmpty())
								continue;

							if (!allowed)
								continue;

							resultpackets.add(new ArrayList<>());
							resulterrors.add(new ArrayList<>());
							writer.println(commands.peek());
							if (floodRate.getValue() > 0) {
								query.debug(DebugOutputType.QUERYREADERQUEUE, "Send Command to Server > (" + commands.peek() + ")");
								setCommandSenderSleeping(floodRate.getValue());
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
					if (query.getConfig().isEventCallType(EventCallType.NEW)) { // New one
						query.debug(DebugOutputType.QUERYREADER, "Called New Event: " + msg);
						query.getEventManager().callNewEvent(infos);
					} else if (query.getConfig().isEventCallType(EventCallType.OLD)) { // Old one
						query.debug(DebugOutputType.QUERYREADER, "Called Old Event: " + msg);
						query.getEventManager().callEvent(infos, infos[0]);

					} else { // And here both types of Event call
						query.debug(DebugOutputType.QUERYREADER, "Called both Events: " + msg);
						query.getEventManager().callNewEvent(infos);
						query.getEventManager().callEvent(infos, infos[0]);
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
	
	
	public void stopThreads() {
		readerThread.interrupt();
		if (eventThread != null)
			eventThread.interrupt();
		if (sleepThread != null)
			sleepThread.interrupt();
	}
	
	private boolean isError(String rs) {
		return rs.startsWith("error");
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

	private boolean isEvent(String rs) {
		return rs.startsWith("notify");
	}
	
	/**
	 * @return the readerThread
	 */
	public synchronized Thread getReaderThread() {
		return readerThread;
	}

	/**
	 * @return the hover
	 */
	public Queue<String> getHover() {
		return hover;
	}

	/**
	 * @return the commands
	 */
	public synchronized Queue<String> getCommands() {
		return commands;
	}

	/**
	 * @return the resultpackets
	 */
	public synchronized Queue<ArrayList<String>> getResultPackets() {
		return resultpackets;
	}

	/**
	 * @return the resultpackets
	 */
	public synchronized Queue<ArrayList<String>> getResultErrors() {
		return resulterrors;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket
	 *                   the socket to set
	 */
	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	public synchronized boolean isCommandFinished() {
		return hover.isEmpty() && resulterrors.peek() != null && !resulterrors.peek().isEmpty();
	}

	public synchronized boolean isAsyncCommandAllowed() {
		return hover.isEmpty() && commands.isEmpty() && resulterrors.isEmpty() && resultpackets.isEmpty();
	}

	public synchronized boolean isCommandFinished(String command) {
		return !hover.contains(command) && !commands.contains(command) && resulterrors.peek() != null && !resulterrors.peek().isEmpty();
	}

	public synchronized String nextPacket() {
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + resultpackets.peek().size());

		StringBuilder resPackets = new StringBuilder();
		for (String result : resultpackets.poll()) {
			resPackets.append(result + System.lineSeparator());
		}

		return resPackets.toString().isEmpty() ? resPackets.toString() : resPackets.substring(0, resPackets.toString().length() - 1);
	}

	/**
	 * See Java Documentation to poll and peek
	 */
	public synchronized String nextSavePacket() {
		StringBuilder resPackets = new StringBuilder();
		for (String result : resultpackets.peek()) {
			resPackets.append(result + System.lineSeparator());

		}

		return resPackets.toString().isEmpty() ? resPackets.toString() : resPackets.substring(0, resPackets.toString().length() - 1);
	}

	public synchronized String nextError() {
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: " + resulterrors.peek().size());
		StringBuilder resErrors = new StringBuilder();
		for (String result : resulterrors.poll()) {
			resErrors.append(result + System.lineSeparator());
		}

		return resErrors.toString().isEmpty() ? resErrors.toString() : resErrors.substring(0, resErrors.toString().length() - 1);
	}

	/**
	 * See Java Documentation to poll and peek
	 */
	public synchronized String nextSaveError() {
		StringBuilder resErrors = new StringBuilder();
		for (String result : resulterrors.peek()) {
			resErrors.append(result + System.lineSeparator());

		}

		return resErrors.toString().isEmpty() ? resErrors.toString() : resErrors.substring(0, resErrors.toString().length() - 1);
	}

	
}
