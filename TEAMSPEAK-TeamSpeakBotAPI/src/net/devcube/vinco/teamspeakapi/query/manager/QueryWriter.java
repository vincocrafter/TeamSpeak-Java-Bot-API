/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 20:00:17
 */

package net.devcube.vinco.teamspeakapi.query.manager;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryWriter {

	private Ts3ServerQuery query;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter writer;
	
	
	public QueryWriter(Ts3ServerQuery query, Socket socket) {
		this.query = query;
		this.socket = socket;

		try {
			this.outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer = new PrintWriter(new OutputStreamWriter(this.outputStream));
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @return the writer
	 */
	public PrintWriter getWriter() {
		return writer;
	}
	
	// Alle Commands werden hier abgesendet
	// Hier änderbar
	public void executeCommand(String command) {
		query.debug("Executing Command > (" + command + ")");
		writer.println(command);
		writer.flush();
	}
}
