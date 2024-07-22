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

import java.util.List;
import java.util.concurrent.*;

import net.devcube.vinco.teamspeakapi.api.api.util.Command;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture;
import net.devcube.vinco.teamspeakapi.api.api.util.CommandFuture.Transformator;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryWriter {

	private QueryConfig config;
	private QueryReader reader;
	private Logger logger;

	long timeout = -1;

	public QueryWriter(QueryConfig config, Logger logger, QueryReader reader) {
		this.config = config;
		this.timeout = config.getConnectionTimeout();
		this.logger = logger;
		this.reader = reader;
	}

	/**
	 * All commands executed here and send to the server
	 *
	 * @param command
	 */
	private void executeCommand(Command command) {
		logger.debug(DebugOutputType.QUERYWRITER, "Executing Command > (" + command.getCommand() + ")");
		reader.addCommand(command);
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
		Command cmd = new Command(command, null);
		executeCommand(cmd);
		long start = System.currentTimeMillis();
		while (!cmd.isFinished()) {
			if ((System.currentTimeMillis() - start) > timeout) {
				return null;
			}
		}
		List<String> packets = cmd.getPackets();
		logger.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.size());
		logger.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
		return new String[] { cmd.getResult(), cmd.getError() };
	}

	/**
	 * Async commands executed here and send to the server. Then wait for an
	 * response.
	 * 
	 * 
	 * @param cmd
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
    CommandFuture<T> future = new CommandFuture<>(transformator);
	Command cmd = new Command(command, future);
	reader.addCommand(cmd);
	FutureTask<Command> task = new FutureTask<>(new Callable<Command>() {

        @Override
        public Command call() {
            logger.debug(DebugOutputType.QUERYWRITER, "Executing AsyncCommand > (" + cmd.getCommand() + ")");
			while (!cmd.isFinished());
            logger.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + cmd.getPackets().size());
            logger.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: 1");
            return cmd;
        }
    });
    future.setTask(task);
	new Thread(task, Thread.currentThread().getName()).start();
    return future;
}
}
