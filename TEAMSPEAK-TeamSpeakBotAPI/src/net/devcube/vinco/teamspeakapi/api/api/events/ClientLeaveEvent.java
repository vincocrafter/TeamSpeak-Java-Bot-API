package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ClientLeaveEvent extends BaseEvent {

	public ClientLeaveEvent(String[] infos) {
		super(infos);
	}

	public String[] getInfos() {
		return super.getInfos();
	}

	public int getClientID() {
		return Integer.parseInt(this.get(3));
	}
}
