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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.devcube.vinco.teamspeakapi.api.api.util.Command;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryWriter {

	private Ts3ServerQuery query;
	private Socket socket;
	private OutputStream outputStream;
	private PrintWriter writer;

	long timeout = -1;

	public QueryWriter(Ts3ServerQuery query, Socket socket) {
		this.query = query;
		this.socket = socket;
		timeout = query.getConfig().getConnectionTimeout();

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
	 * All commands executed here and send to the server
	 * 
	 * @param command
	 */
	private void executeCommand(Command command) {
		query.debug(DebugOutputType.QUERYWRITER, "Executing Command > (" + command.getCommand() + ")");
		query.getReader().addCommand(command);
	}

	/**
	 * Now -> Default use for Command sending instead of executeCommand() Same as
	 * below, but some Commands are only getting an Error as answer
	 * 
	 * @param command
	 * @return {Errors}
	 */
	public String executeReadErrorCommand(String command) {
		return executeReadCommand(command)[1];
	}

	public String executeReadErrorCommand(StringBuilder command) {
		return executeReadErrorCommand(command.toString());
	}

	/**
	 * Idea of sending a Command and reading the following Message from the Server
	 * 
	 * @param command
	 * @return {Normal Packet, Error}
	 */
	public synchronized String[] executeReadCommand(String command) {
		Command cmd = new Command(command);
		executeCommand(cmd);
		while (!cmd.isFinished());
		List<String> packets = cmd.getPackets();
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.size());
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
		StringBuilder resPackets = new StringBuilder();
		packets.forEach(result -> {
			resPackets.append(result);
			resPackets.append(System.lineSeparator());
		});

		return new String[] { resPackets.toString(), cmd.getError() };
	}

	public synchronized String[] executeReadCommand(StringBuilder command) {
		return executeReadCommand(command.toString());
	}

	/**
	 * All async commands executed here and send to the server. TODO handle problems
	 * with getting Packets and Errors
	 * 
	 * Then is waited for an response.
	 * 
	 * @param command
	 *                    Command that is send to the server.
	 * @return {Normal Packet, Error}
	 */

	public String[] executeAsyncReadCommand(String command) {
		FutureTask<String[]> task = new FutureTask<>(new Callable<String[]>() {

			@Override
			public String[] call() throws Exception {
				query.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + command + ")");
				Command cmd = new Command(command);
				query.getReader().addCommand(cmd);
				while (!cmd.isFinished());
				List<String> packets = cmd.getPackets();
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.size());
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
				StringBuilder resPackets = new StringBuilder();
				packets.forEach(result -> {
					resPackets.append(result);
					resPackets.append(System.lineSeparator());
				});

				return new String[] { resPackets.toString(), cmd.getError() };
			}

		});
		
		new Thread(task, "ASYN").start();
		try {
			return task.get(timeout, TimeUnit.MILLISECONDS);
		} catch (ExecutionException | TimeoutException e) {
			e.printStackTrace();
			query.debug(DebugOutputType.ERROR, "Executing AsyncCommand > (" + command + ") caused an Exception :" + e.getCause());
		} catch (InterruptedException e) {
			// ignore
		}
		return null;
	}

	public void executeAsyncCommand(String command) {
		FutureTask<String[]> task = new FutureTask<>(new Callable<String[]>() {

			@Override
			public String[] call() throws Exception {
				query.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + command + ")");
				Command cmd = new Command(command);
				query.getReader().addCommand(cmd);
				while (!cmd.isFinished());
				List<String> packets = cmd.getPackets();
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.size());
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
				StringBuilder resPackets = new StringBuilder();
				packets.forEach(result -> {
					resPackets.append(result);
					resPackets.append(System.lineSeparator());
				});

				return new String[] { resPackets.toString(), cmd.getError() };
			}

		});
		new Thread(task, "ASYN").start();
	}
}
