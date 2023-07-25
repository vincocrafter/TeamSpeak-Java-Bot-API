package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ChannelDeletedEvent extends BaseEvent {

	
	public ChannelDeletedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public String getClientName() {
		return Formatter.toNormalFormat(get("invokername"));
	}

	public int getClientID() {
		return toInt(get("invokerid"));
	}

	public String getClientUUID() {
		return get("invokeruid");
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}

	public String toString() {
		return "ChannelDeleteEvent[ClientID=" + getClientID() + ",ClientName=" + getClientName() + ",ClientUUID=" + getClientUUID() + ",ChannelID=" + getChannelID() + "]";
	}
}
