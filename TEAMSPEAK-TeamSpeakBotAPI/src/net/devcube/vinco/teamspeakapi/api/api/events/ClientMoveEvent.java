package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ClientMoveEvent extends BaseEvent {

	public ClientMoveEvent(String[] infos) {
		super(infos);
	}

	public int getClientID() {
		return toInt(get("clid"));
	}

	public int getReasonID() {
		return toInt(get("reasonid"));
	}

	public int getTargetChannelID() {
		return toInt(get("ctid"));
	}

	public String toString() {
		return "ClientMoveEvent [ClientID=" + getClientID() + ",ReasonID=" + getReasonID() + ",TargetChannelID=" + getTargetChannelID() + "]";
	}

	public boolean isTargetChannelID(int id) {
		return getTargetChannelID() == id;
	}
}
