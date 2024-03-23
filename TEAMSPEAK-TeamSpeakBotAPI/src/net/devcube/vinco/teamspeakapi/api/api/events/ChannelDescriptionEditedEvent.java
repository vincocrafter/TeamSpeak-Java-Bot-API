package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ChannelDescriptionEditedEvent extends BaseEvent {


	public ChannelDescriptionEditedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	public String toString() {
		return "ChannelDeleteEvent[ChannelID=" + getChannelID() + "]";
	}
}
