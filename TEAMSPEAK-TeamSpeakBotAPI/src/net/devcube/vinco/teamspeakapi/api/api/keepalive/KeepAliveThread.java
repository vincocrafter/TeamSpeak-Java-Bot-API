/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 12.02.2019
 * 
 *Uhrzeit : 16:51:51
 */
package net.devcube.vinco.teamspeakapi.api.api.keepalive;

import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class KeepAliveThread extends Thread {

	private static final int SLEEP = 180000;
	private final Ts3ServerQuery query;

	public KeepAliveThread(Ts3ServerQuery query) {
		super("KALT");
		this.query = query;
	}
	
	
	// keeps the Socket connected to the (Teamspeak)Server
	public void run() {
		query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has been started");
		while (!this.isInterrupted()) {
			query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveMessage has been send");

//			query.getWriter().executeCommand("version");
//			query.getReader().nextPacket(); // remove the anwser from the Queues
//			query.getReader().nextError();
			
			//Same as above with new Method

			// TODO this MIGHT work :)
			query.getWriter().executeReadErrorCommand("version");

			
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	//stops the Thread
	public void interrupt() {
		query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has beeen stopped");
		super.interrupt();
	}

	/**
	 * @return the sleep durantion in ms
	 */
	public static int getSleep() {
		return SLEEP;
	}

}
