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
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;

public class KeepAliveThread {

	private final int SLEEP = 240_000; //Default Time for Timeout are 300 Seconds
	private final Ts3AsyncAPI async;
	private final Logger logger;
	private Thread keepAliveThread;

	public KeepAliveThread(Ts3AsyncAPI async, Logger logger) {
		this.async = async;
		this.logger = logger;
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
		logger.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has been started");
		
		keepAliveThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (Thread.currentThread().isAlive()) {
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						break;
					}
					
					logger.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveMessage has been send");
					async.getVersion();
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
		logger.debug(DebugOutputType.KEEPALIVETHREAD, "KeepAliveThread has been stopped");
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
	public int getSleep() {
		return SLEEP;
	}

}
