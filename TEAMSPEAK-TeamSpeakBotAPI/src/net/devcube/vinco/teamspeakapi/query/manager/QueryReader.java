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
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownEventException;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class QueryReader {

    private final Ts3ServerQuery query;
    private Socket socket;

    private final Queue<String> command_queue = new LinkedList<>();
    private final Queue<String> response_queue = new LinkedList<>();
    private final Queue<String> error_queue = new LinkedList<>();


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
        query.debug(DebugOutputType.QUERYREADER, "Starting listening in QueryReader");

        new Thread(new Runnable() { // New Thread so async


            @Override
            public void run() { // starting the while loop here to listen for packets
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (socket.isConnected()) { // <-- while loop here
                        if (!reader.ready()) {
                            continue;
                        }
                        String msg = reader.readLine();
                        if (!isResultValid(msg)) {
                            System.out.println("INVALID RESPONSE: " + msg);
                            continue;
                        }
                        if (isError(msg)) {
                            // ERROR
                            query.debug(DebugOutputType.QUERYREADER, "Added to Errors: " + msg);
                            query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Errors: " + msg);
                            error_queue.add(msg);
                            command_queue.poll();

                        } else if (isEvent(msg)) {
                            // EVENT
                            String[] infos = msg.split(" ");
                            /*
                             * Decide which EventCall you prefer
                             * here the new one
                             * it is also the default one
                             * @see QueryConfig.eventCallType
                             */
                            if (query.getConfig().getEventCallType() == EventCallType.NEW) { //New one
                                query.debug(DebugOutputType.QUERYREADER, "Called New Event: " + msg);
                                query.getEventManager().callNewEvent(infos);
                            } else if (query.getConfig().getEventCallType() == EventCallType.OLD) { //Old one
                                query.debug(DebugOutputType.QUERYREADER, "Called Old Event: " + msg);
                                try {
                                    query.getEventManager().callEvent(infos, infos[0]);
                                } catch (UnknownEventException e) {
                                    query.debug(DebugOutputType.QUERYREADER, "Got an Error in Old Event: ");
                                    e.printStackTrace();
                                }
                            } else { //And here both types of Event call
                                query.debug(DebugOutputType.QUERYREADER, "Called both Events: " + msg);
                                query.getEventManager().callNewEvent(infos);
                                try {
                                    query.getEventManager().callEvent(infos, infos[0]);
                                } catch (UnknownEventException e) {
                                    query.debug(DebugOutputType.QUERYREADER, "Got an Error in Old Event: ");
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            query.debug(DebugOutputType.QUERYREADER, "Added to Packets: " + msg);
                            query.debug(DebugOutputType.QUERYREADERQUEUE, "Added to Packets: " + msg);
                            response_queue.add(msg);
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, "QURT").start();
    }

    // Just a check if the packet is an error message, so it can be ignored
    private boolean isError(String rs) {
        return rs.startsWith("error");
    }

    // Just a check to validate the packet
    // checks that response is not empty of null. returns true if it isn't
    private boolean isResultValid(String rs) {
        return rs != null && !rs.isEmpty();
    }

    // Just check if it is an Event
    private boolean isEvent(String rs) {
        return rs.startsWith("notify");
    }

    /**
     * @return the packets
     */
    public Queue<String> get_command_queue() {
        return command_queue;
    }

    /**
     * @return the errors
     */
    public Queue<String> get_response_queue() {
        return response_queue;
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

    public String pollCommand() {
        query.debug(DebugOutputType.QUERYREADERQUEUE, "Removing from Command-Queue[{%d}]: ".formatted(command_queue.size()) + command_queue.peek());
        return command_queue.poll();
    }

    public String peekCommand() {
        return command_queue.peek();
    }

    public String pollResponse() {
        query.debug(DebugOutputType.QUERYREADERQUEUE, "Removing from Response-Queue[new size{%d}]: ".formatted(response_queue.size() - 1) + response_queue.peek());
        return response_queue.poll();
    }

    public String peekResponse() {
        return response_queue.peek();
    }

    /* TODO replace
    public synchronized String nextPacket() {
        query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Packets: " + packets.peek());
        System.out.println(packets.size());
        return packets.poll();
    }

     */

    /**
     *  See Java Documentation to poll and peek
     */
	/* TODO replace
	public synchronized String nextSavePacket() {
		return packets.peek();
	}
	*/

	/* TODO replace
	public synchronized String nextError() {
		query.debug(DebugOutputType.QUERYREADERQUEUE, "Removed from Errors: " + errors.peek());
		return errors.poll();
	}
	 */

    /**
     *  See Java Documentation to poll and peek
     */
	/* TODO replace
	public synchronized String nextSaveError() {

		return errors.peek();
	}
	 */
}
