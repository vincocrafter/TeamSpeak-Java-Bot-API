package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ClientMoveEvent extends BaseEvent {

	
	public ClientMoveEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
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

	public boolean isTargetChannelID(int id) {
		return getTargetChannelID() == id;
	}

	public boolean hasBeenMoved() {
		return getReasonID() == 1;
	}

	public int getInvokerID() {
		if (hasBeenMoved())
			return toInt(get("invokerid"));
		return -1;
	}
	
	public String getInvokerName() {
		return get("invokername");
	}
	
	public String getInvokerUUID() {
		return get("invokeruid");
	}
	
	public String toString() {
		return "ClientMoveEvent [ClientID=" + getClientID() + ",ReasonID=" + getReasonID() + ",TargetChannelID=" + getTargetChannelID() + "]";
	}
}
