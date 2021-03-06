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

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class KeepAliveThread extends Thread {

	private static final int SLEEP = 180000;
	private final Ts3ServerQuery query;

	public KeepAliveThread(Ts3ServerQuery query) {
		super("[TeamSpeak3-BotAPI] > KeepAliveThread");
		this.query = query;
	}

	public void run() {
		while (!this.isInterrupted()) {
			if (this.query.getConfig().isKeepAliveDebug()) {
				query.getLogger().log(1, "KeepAliveMessage has been sent");
			}

			query.getWriter().executeCommand("version");

			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException interruptedexception) {
				interruptedexception.printStackTrace();
			}
		}

	}

	public void interrupt() {
		query.getLogger().log(2, "KeepAliveThread has been stopped");
		super.interrupt();
	}

	/**
	 * @return the sleep durantion in ms
	 */
	public static int getSleep() {
		return SLEEP;
	}

}
