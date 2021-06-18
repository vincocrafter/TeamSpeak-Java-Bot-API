package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ChannelCreateEvent extends BaseEvent {

	public ChannelCreateEvent(String[] infos) {
		super(infos);
	}

	public int getClientID() {
		return Integer.parseInt(get(9));
	}

	public String getClientName() {
		return get(9).replace("\\s", " ");
	}

	public int getChannelID() {
		return Integer.parseInt(get(1));
	}

	public String getChannelName() {
		return get(3);
	}

	public String getClientUUID() {
		return get(10);
	}

	public String toString() {
		return "ChannelCreateEvent[ClientID=" + getClientID() + ",ClientName=" + getClientName() + ",ClientUUID=" + getClientUUID() + ",ChannelID=" + getChannelID() + ",ChannelName="
				+ getChannelName() + "]";
	}
}
