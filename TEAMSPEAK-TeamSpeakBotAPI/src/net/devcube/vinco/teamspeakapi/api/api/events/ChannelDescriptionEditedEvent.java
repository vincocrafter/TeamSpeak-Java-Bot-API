package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ChannelDescriptionEditedEvent extends BaseEvent {

	public ChannelDescriptionEditedEvent(String[] infos) {
		super(infos);
	}

	public int getChannelID() {
		return Integer.parseInt(this.get(1));
	}

	public String getClientName() {
		return "";
	}

	public int getClientID() {
		return 0;
	}
}
