package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ServerEditedEvent extends BaseEvent {

	public ServerEditedEvent(String[] infos) {
		super(infos);
	}

	public int getClientID() {
		return toInt(get(2));
	}

	public String getClientName() {
		return get(3);
	}

	public String getClientUUID() {
		return get(4);
	}

	public int getReasonID() {
		return toInt(get(1));
	}
}
