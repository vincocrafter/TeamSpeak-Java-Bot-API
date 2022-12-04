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
		System.out.println("EXECUTING " + command + " WITHOUT QUEUEING FIRST - THIS WILL BREAK STUFF");
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

	public void queue_command(String command) {
		query.getReader().get_command_queue().add(command);
	}
	
	/**
	 * Idea of sending a Command and reading the following Message from the Server
	 * 
	 * @param command the command string
	 * @return {Normal Packets, Errors}
	 */
	public String[] executeReadCommand(String command) {
		System.out.println(command);
		queue_command(command);
		while (query.getReader().peekResponse() == null);

		StringBuilder response = new StringBuilder();
		for (int i = 0; i < query.getReader().get_response_queue().size(); i++) {
			response.append(query.getReader().get_response_queue().poll());
			if (i != query.getReader().get_response_queue().size() - 1) {
				response.append("\n");
			}
		}
		System.out.println("rezq size: " + query.getReader().get_response_queue().size());
		// query.getReader().pollCommand();
		return new String[] { response.toString(), query.getReader().pollError() };
	}


	/**
	 * TODO Write nice Comment
	 */
	protected void executeNextCommand() {
		executeCommand(query.getReader().get_command_queue().peek());
	}
}
