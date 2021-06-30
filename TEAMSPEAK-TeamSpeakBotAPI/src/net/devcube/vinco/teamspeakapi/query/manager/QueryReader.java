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

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

	private Ts3ServerQuery query;
	private Socket socket;
	
	private Queue<String> packets = new LinkedList<>();
	
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
			
			@Override
			public void run() { //starting the while loop here to listen for packets
				while(socket.isConnected()) { // <-- while loop here
					
				}
			}
		}).start();
	}
	
	//Just a check if the packet is an error message, so it can be ignored
	public boolean isError(String rs) {
		return rs.startsWith("error");
	}
	
	//Just a check to validate the packet
	public boolean isResultValid(String rs) {
		if (rs == null)
			return false;
		if(rs.isEmpty())
			return false;
		return true;
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
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
}
