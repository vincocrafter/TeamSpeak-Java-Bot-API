package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ChannelPasswordChangedEvent extends BaseEvent {


	public ChannelPasswordChangedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
}
