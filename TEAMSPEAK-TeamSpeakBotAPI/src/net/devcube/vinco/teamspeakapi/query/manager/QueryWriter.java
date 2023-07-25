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
		query.getReader().getCommands().add(command);
		//writer.println(command);
		//writer.flush();
	}
	
	/**
	 * Now -> Default use for Command sending instead of executeCommand()
	 * Same as below, but some Commands are only getting an Error as answer
	 * 
	 * @param command
	 * @return {Errors}
	 */
	public String executeReadErrorCommand(String command) {
		return executeReadCommand(command)[1];
	}
	
	/**
	 * Idea of sending a Command and reading the following Message from the Server
	 * 
	 * @param command
	 * @return {Normal Packet, Error}
	 */
	public synchronized String[] executeReadCommand(String command) {
		executeCommand(command);
		
		while (!query.getReader().isCommandFinished(command));
	
		String packets = query.getReader().nextPacket();
		String errors = query.getReader().nextError();
		return new String[] { packets, errors };
	}
	
	/**
	 * Möglich Werte zurückzubekommen????
	 * return String[] ?
	 * @param command
	 */
	
	public void executeAsyncCommand(String command) {
		new Thread(new Runnable() {

			public void run() {
				while(!query.getReader().isAsyncCommandAllowed());
				query.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + command + ")");
				query.getReader().getCommands().add(command);
				while (!query.getReader().isCommandFinished(command));
				query.getReader().nextPacket();
				query.getReader().nextError();
			}

		}, "ASYNC").start();
	}
}
