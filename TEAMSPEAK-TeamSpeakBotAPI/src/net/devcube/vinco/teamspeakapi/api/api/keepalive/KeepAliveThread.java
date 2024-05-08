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

public class KeepAliveThread {

	private static final int SLEEP = 240_000; //Default Time for Timeout are 300 Seconds
	private final Ts3ServerQuery query;
	private Thread keepAliveThread;
	
	
	public KeepAliveThread(Ts3ServerQuery query) {
		this.query = query;
	}
	
	
	/**
	 * Starts the KeepAliveThread.
	 * 
	 * This method initializes and starts a new thread responsible for sending KeepAlive messages 
	 * to maintain the connection with the TeamSpeak server.
	 * 
	 * The KeepAliveThread runs in a loop until its owning thread is alive. It periodically sends 
	 * a KeepAlive message to the server and retrieves the version of the TeamSpeak server asynchronously.
	 */
	public void start() {
		query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has been started");
		
		keepAliveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (Thread.currentThread().isAlive()) {
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						break;
					}
					
					query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveMessage has been send");
					query.getAsyncAPI().getVersion();
				}
				
			}
		}, "KALT");
		keepAliveThread.start();
	}
	
	
	/**
	 * Stops the KeepAliveThread.
	 * 
	 * This method interrupts the KeepAliveThread, causing it to stop sending KeepAlive messages 
	 * and terminate its execution.
	 */
	public void interrupt() {
		keepAliveThread.interrupt();
		query.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has beeen stopped");
	}
	
	/**
	 * @return the keepAliveThread
	 */
	public Thread getKeepAliveThread() {
		return keepAliveThread;
	}
	
	/**
	 * @return the sleep durantion in ms
	 */
	public static int getSleep() {
		return SLEEP;
	}

}
