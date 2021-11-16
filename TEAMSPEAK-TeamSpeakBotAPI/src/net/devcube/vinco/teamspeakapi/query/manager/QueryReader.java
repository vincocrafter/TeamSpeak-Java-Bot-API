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
								if (!isError(msg)) {
									if (!isEvent(msg)) {
										// Information here
										packets.add(msg);
									} else {
										// Call Event here
										String[] infos = msg.split(" ");
										/*
										 * Decide wich EventCall you prefer
										 * here the new one
										 * it is also the default one 
										 * @see QueryConfig.eventCallType
										 */
										if (query.getConfig().getEventCallType() == EventCallType.NEW) { //New one
											query.getEventManager().callNewEvent(infos);
										} else if (query.getConfig().getEventCallType() == EventCallType.OLD) { //Old one
											try {
												query.getEventManager().callEvent(infos, infos[0]);
											} catch (UnknownEventException e) {
												query.debug(5, "Unkown Event Exception: ");
												e.printStackTrace();
											}
										} else { //And here both types of Event call
											query.getEventManager().callNewEvent(infos);
											try {
												query.getEventManager().callEvent(infos, infos[0]);
											} catch (UnknownEventException e) {
												query.debug(5, "Unkown Event Exception: ");
												e.printStackTrace();
											}
										}
									}
								} else {
									// Error handeling
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
		}).start();
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

	public String nextPacket() {
		return packets.poll();
	}

	// See Java Documentation to poll and peek
	public String nextSavePacket() {
		return packets.peek();
	}

	public String nextError() {
		return errors.poll();
	}

	// See Java Documentation to poll and peek
	public String nextSaveError() {
		return errors.peek();
	}
}
