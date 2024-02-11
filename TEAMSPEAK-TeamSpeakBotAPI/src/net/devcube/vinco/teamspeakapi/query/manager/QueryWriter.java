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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import net.devcube.vinco.teamspeakapi.api.api.util.Command;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture.Transformator;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryWriter {

	private Ts3ServerQuery query;
	
	long timeout = -1;

	public QueryWriter(Ts3ServerQuery query) {
		this.query = query;
		this.timeout = query.getConfig().getConnectionTimeout();
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
	public String[] executeReadCommand(String command) {
		Command cmd = new Command(command);
		executeCommand(cmd);
		while (!cmd.isFinished())
			;
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

	public String[] executeReadCommand(StringBuilder command) {
		return executeReadCommand(command.toString());
	}

	/**
	 * Async commands executed here and send to the server. Then wait for an
	 * response.
	 * 
	 * 
	 * @param command
	 *                    Command that is send to the server.
	 * @return {Normal Packet, Error}
	 */

	public <T> T executeAsyncReadCommand(String cmd, Transformator<T> transformator) {
		return executeAsyncCommand(cmd, transformator).get(timeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * Async command executed and send to the server. Does not wait for an response.
	 * 
	 * @param command
	 *                    Command that is send to the server.
	 * @return CommandFuture Class containing an information about the result.
	 */

	
	public <T> CommandFuture<T> executeAsyncCommand(String command, Transformator<T> transformator) {
		Command cmd = new Command(command);

		FutureTask<Command> task = new FutureTask<>(new Callable<Command>() {

			@Override
			public Command call() throws Exception {
				query.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + cmd.getCommand() + ")");
				query.getReader().addCommand(cmd);
				while (!cmd.isFinished());
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + cmd.getPackets().size());
				query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
				return cmd;
			}

		});
		new Thread(task, "ASYN").start();
		return new CommandFuture<T>(task, transformator);
	}

}
