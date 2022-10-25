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
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownEventException;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

	private Ts3ServerQuery query;
	private Socket socket;

	private Queue<String> packets = new LinkedList<>();
	private Queue<String> errors = new LinkedList<>();
	
	
	public QueryReader(Ts3ServerQuery query, Socket socket) {
		this.query = query;
		this.setSocket(socket);
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * start listening for incoming packets async
	 */

	public void start() {
		query.debug(DebugOutputType.QUERYREADER, "Starting listening in QueryReader");
		
		new Thread(new Runnable() { // New Thread so async
			
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() { // starting the while loop here to listen for packets
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while (socket.isConnected()) { // <-- while loop here
						if (reader.ready()) {
							String msg = reader.readLine();
							if (isResultValid(msg)) {
								query.debug(DebugOutputType.QUERYREADER, "Got incoming Packet: " + msg); //maybe stupid (bc. debugged below)
								if (!isError(msg)) {
									if (!isEvent(msg)) { // Information here
										query.debug(DebugOutputType.QUERYREADER, "Added to Packets: " + msg);
										query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Packets: " + msg);
										packets.add(msg);
									} else {  //Event here
										String[] infos = msg.split(" ");
										/*
										 * Decide which EventCall you prefer
										 * here the new one
										 * it is also the default one 
										 * @see QueryConfig.eventCallType
										 */
										if (query.getConfig().getEventCallType() == EventCallType.NEW) { //New one
											query.debug(DebugOutputType.QUERYREADER, "Called New Event: " + msg);
											query.getEventManager().callNewEvent(infos);
										} else if (query.getConfig().getEventCallType() == EventCallType.OLD) { //Old one
											query.debug(DebugOutputType.QUERYREADER, "Called Old Event: " + msg);
											try {
												query.getEventManager().callEvent(infos, infos[0]);
											} catch (UnknownEventException e) {
												query.debug(DebugOutputType.QUERYREADER, "Got an Error in Old Event: ");
												e.printStackTrace();
											}
										} else { //And here both types of Event call
											query.debug(DebugOutputType.QUERYREADER, "Called both Events: " + msg);
											query.getEventManager().callNewEvent(infos);
											try {
												query.getEventManager().callEvent(infos, infos[0]);
											} catch (UnknownEventException e) {
												query.debug(DebugOutputType.QUERYREADER, "Got an Error in Old Event: ");
												e.printStackTrace();
											}
										}
									}
								} else {
									// Error handeling
									query.debug(DebugOutputType.QUERYREADER, "Added to Errors: " + msg);
									query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Errors: " + msg);
									errors.add(msg);
								}
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, "TeamSpeak3-BotAPI > QueryReaderThreat").start();
	}

	// Just a check if the packet is an error message, so it can be ignored
	private boolean isError(String rs) {
		return rs.startsWith("error");
	}

	// Just a check to validate the packet
	private boolean isResultValid(String rs) {
		if (rs == null)
			return false;
		if (rs.isEmpty())
			return false;
		return true;
	}

	// Just check if it is an Event
	private boolean isEvent(String rs) {
		return rs.startsWith("notify");
	}

	/**
	 * @return the packets
	 */
	public Queue<String> getPackets() {
		return packets;
	}
	
	/**
	 * @return the errors
	 */
	public Queue<String> getErrors() {
		return errors;
	}
	
	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @param socket the socket to set
	 */
	private void setSocket(Socket socket) {
		this.socket = socket;
	}

	public synchronized String nextPacket() {
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.peek());
		return packets.poll();
	}

	/**
	 *  See Java Documentation to poll and peek
	 */
	public synchronized String nextSavePacket() {
		return packets.peek();
	}

	public synchronized String nextError() {
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: " + errors.peek());
		return errors.poll();
	}

	/**
	 *  See Java Documentation to poll and peek
	 */
	public synchronized String nextSaveError() {
		return errors.peek();
	}
}
