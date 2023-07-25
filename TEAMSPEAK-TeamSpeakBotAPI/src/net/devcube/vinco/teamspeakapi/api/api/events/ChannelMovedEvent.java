/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 06.04.2023
 * 
 * Uhrzeit : 12:49:50
 */
package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ChannelMovedEvent extends BaseEvent {

	public ChannelMovedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	public int getChannelParentID() {
		return toInt(get("cpid"));
	}
	
	public int getOrder() {
		return toInt(get("order"));
	}
	
	public int getReasonID() {
		return toInt(get("reasonid"));
	}
	
	public int getClientID() {
		return toInt(get("invokerid"));
	}
	
	public String getClientName() {
		return Formatter.toNormalFormat(get("invokername"));
	}
	
	public String getClientUUID() {
		return get("invokeruid");
	}
	
}
