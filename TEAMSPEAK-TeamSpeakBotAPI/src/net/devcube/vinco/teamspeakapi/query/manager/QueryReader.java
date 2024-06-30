/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 * <p>
 * Author : vincent
 * <p>
 * Jahr 2019
 * <p>
 * Datum : 10.02.2019
 * <p>
 * Uhrzeit : 20:00:09
 */
package net.devcube.vinco.teamspeakapi.query.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManagerUpdater;
import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.util.Command;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.query.connection.QueryConnection;

public class QueryReader {

    private QueryConfig config;
    private QueryConnection connection;
    private Logger logger;
    private EventManager eventManager;
    private Queue<Command> commands = new LinkedList<>();
    private Queue<Command> hover = new LinkedList<>();
    private boolean allowed = true;
    private Thread readerThread;
    private Thread eventThread;

    public QueryReader(QueryConfig config, Logger logger, EventManager eventManager) {
        this.config = config;
        this.connection = config.getConnection();
        this.logger = logger;
        this.eventManager = eventManager;
    }


    /**
     * Starts the QueryReader thread which listens for incoming packets from the server.
     * The thread is named "QURT" and it executes the listenForPackets method.
     */
    public void start() {
        logger.debug(DebugOutputType.QUERYREADER, "Starting listening in QueryReader");
        readerThread = new Thread(this::listenForPackets, "QURT");
        readerThread.start();
    }

    /**
     * Listens for incoming packets from the server. If the reader is ready, it handles the incoming messages.
     * If the reader is not ready, it executes commands. This method continues to run as long as the connection
     * to the server is active. If an IOException occurs, it logs the error and prints the stack trace.
     */
    private void listenForPackets() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            while (connection.isConnected()) { // <-- while loop here
                if (reader.ready()) { // Handle Packets here
                    handleMessages(reader);
                } else { // Execute Commands here
                    executeCommands(writer);
                }
            }
        } catch (IOException e) {
            logger.debug(DebugOutputType.ERROR, "Something went wrong with the QueryReader! Exception: IOException");
            e.printStackTrace();
        }
    }

    private void handleMessages(BufferedReader reader) throws IOException {
        String msg = rl(reader);
        if (isError(msg)) {// Error handling
            logger.debug(DebugOutputType.QUERYREADER, "Added to Errors: " + msg);
            logger.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Errors: " + msg);
            if (!hover.isEmpty())
                hover.poll().setError(msg);
            return;
        }
        if (isEvent(msg)) { // Event here
            logger.debug(DebugOutputType.QUERYREADER, "Read Event: " + msg);
            callEvents(msg);
            return;
        }
        if (!hover.isEmpty()) {
            logger.debug(DebugOutputType.QUERYREADER, "Added to Packets: " + msg);
            logger.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Packets: " + msg);
            hover.peek().getPackets().add(msg);
        }
    }

    private void executeCommands(PrintWriter writer) {
        FloodRate floodRate = config.getFloodRate();
        if (!hover.isEmpty() || commands.isEmpty() || !allowed)
            return;

        Command cmd = commands.poll();
        writer.println(cmd.getCommand());
        if (floodRate.getValue() > 0) {
            setCommandSenderSleeping(floodRate.getValue());
            logger.debug(DebugOutputType.QUERYREADERQUEUE, "Send Command to Server > (" + cmd.getCommand() + ")");
        }
        hover.add(cmd);
        writer.flush();
    }

    private void callEvents(String msg) {
        String[] infos = msg.split(" ");

        /*
         * Decide which EventCall you prefer here the new one it is also the default one
         *
         * @see QueryConfig#isEventCallType()
         */
        eventThread = new Thread(() -> {
            if (!config.getCachingList().isEmpty()) {
                logger.debug(DebugOutputType.CACHEMANAGER, "Calling eventupdater for event " + infos[0]);
                eventManager.callNewEventClass(infos, new CacheManagerUpdater());
            }

            try {
                if (config.isEventCallType(EventCallType.NEW)) { // New one
                    eventManager.callNewEvent(infos);
                } else if (config.isEventCallType(EventCallType.OLD)) { // Old one
                    eventManager.callEvent(infos);
                } else { // And here both types of Event call
                    eventManager.callNewEvent(infos);
                    eventManager.callEvent(infos);
                }
            } catch (Exception e) {
                logger.debug(DebugOutputType.ERROR, "Got an Exception from calling an event caused by " + e.getClass().getName() + "!");
                e.printStackTrace();
            }
        }, "EVMA");
        eventThread.start();
    }

    private void setCommandSenderSleeping(int sleepTime) {
        allowed = false;

        Executors.newScheduledThreadPool(1).schedule(() -> {
            allowed = true;
        }, sleepTime, TimeUnit.MILLISECONDS);
    }

    public synchronized void stopThreads() {
        commands.forEach(cmd -> cmd.setError("Stopped"));
        commands.clear();
        hover.forEach(cmd -> cmd.setError("Stopped"));
        hover.clear();

        if (eventThread != null)
            eventThread.interrupt();

        readerThread.interrupt();
    }

    private boolean isError(String rs) {
        return rs.startsWith("error");
    }

    private boolean isEvent(String rs) {
        return rs.startsWith("notify");
    }

    private String rl(BufferedReader reader) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = reader.read()) != '\r') {
            if (c == '\n')
                continue;
            line.append((char) c);
        }
        return line.toString();
    }

    /**
     * @return the commands
     */
    public Queue<Command> getCommands() {
        return new LinkedList<>(commands);
    }

    protected synchronized void addCommand(Command command) {
        commands.add(command);
    }
}
