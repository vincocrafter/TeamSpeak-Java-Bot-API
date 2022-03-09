/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 19:13:51
 */
package net.devcube.vinco.teamspeakapi.api.sync;

import net.devcube.vinco.teamspeakapi.api.api.event.TsEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Ts3SyncAPI {

	private Ts3ServerQuery query;
	private boolean connected = false;

	/**
	 * Initiation of the Sync API
	 * @param Serverquery class
	 */
	public Ts3SyncAPI(Ts3ServerQuery query) {
		this.query = query;
	}

	/**
	 * @return the query
	 */
	public Ts3ServerQuery getQuery() {
		return query;
	}

	/**
	 * Connects the Query bot to the virtual server
	 * And changes the Query bot's name to the param nickname
	 * @param serverID from the virtual server
	 * @param QueryBot nickname
	 */
	public void connectTeamSpeakQuery(int serverID, String nickname) {
		if (!this.isConnected()) {
			selectVirtualServer(serverID); //select the virtualServer 
			query.getWriter().executeCommand("clientupdate client_nickname=" + Formatter.toTsFormat(nickname));
			query.debug(DebugOutputType.QUERY, "Query is sucessfully connected");

			setConnected(true);
			query.readAllMessages();
		} else {
			query.getLogger().log(Logger.ERROR, "Query is already conncted");
		}

	}

	/**
	 * Selects the virtual server to connect to
	 * @param serverid of the virtual server
	 */
	public void selectVirtualServer(int serverid) {
		query.getWriter().executeCommand("use " + serverid);
	}

	public void addTs3Listener(TsEvent event) {
		this.query.getEventManager().addTs3Listener(event);
	}

	/**
	 * switches the channel of the query bot
	 * @param channelID
	 */
	public void goToChannel(int channelID) {
		
	}

	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * @param connected
	 *                      the connected to set
	 */
	private void setConnected(boolean connected) {
		this.connected = connected;
	}

}
