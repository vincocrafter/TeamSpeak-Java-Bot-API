package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ClientMoveEvent extends BaseEvent {
	
	public ClientMoveEvent(String[] infos) {
		super(infos);
	}

	public String[] getInfos() {
		return super.getInfos();
	}

	public String getClientName() {
		return "";
	}

	public int getClientID() {
		return Integer.parseInt(get(3));
	}

	public int getReasonID() {
		return Integer.parseInt(get(2));
	}

	public int getTargetChannelID() {
		return Integer.parseInt(get(1));
	}

	public String toString() {
		return "ClientMoveEvent [ClientID=" + getClientID() + ",ReasonID=" + getReasonID() + ",TargetChannelID=" + getTargetChannelID() + "]";
	}

	public boolean isTargetChannelID(int id) {
		return getTargetChannelID() == id;
	}
}
