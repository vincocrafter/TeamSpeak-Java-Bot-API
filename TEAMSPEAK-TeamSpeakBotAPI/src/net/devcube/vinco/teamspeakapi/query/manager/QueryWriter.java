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

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
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

	/**
	 *  All commands executed here and send to the server
	 * @param command
	 */
	private void executeCommand(String command) {
		query.debug(DebugOutputType.QUERYWRITER, "Executing Command > (" + command + ")");
		writer.println(command);
		writer.flush();
	}
	
	/**
	 * Now -> Default use for Command sending instead of executeCommand()
	 * Same as below, but some Commands are only getting an Error as answer
	 * 
	 * @param command
	 * @return {Errors}
	 */
	public String executeReadErrorCommand(String command) {
		executeCommand(command);
		while (query.getReader().nextSaveError() == null);
		return query.getReader().nextError();
	}
	
	/**
	 * Idea of sending a Command and reading the following Message from the Server
	 * 
	 * @param command
	 * @return {Normal Packets, Errors}
	 */
	public String[] executeReadCommand(String command) {
		executeCommand(command);
//		while (query.getReader().nextSavePacket() == null);
		while (query.getReader().nextSaveError() == null);
		return new String[] { query.getReader().nextPacket(), query.getReader().nextError() };
	}
	
	public void executeAsyncCommand(String command) {
		new Thread() {

			public void run() {
				query.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + command + ")");
				writer.println(command);
				writer.flush();
			}

		}.start();
	}
}
