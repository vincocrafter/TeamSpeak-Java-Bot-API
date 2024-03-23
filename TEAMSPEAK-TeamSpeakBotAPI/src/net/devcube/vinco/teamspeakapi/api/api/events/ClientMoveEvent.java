package net.devcube.vinco.teamspeakapi.api.api.events;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ClientMoveEvent extends BaseEvent {

	
	public ClientMoveEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public List<Integer> getClientIDs() {
		List<Integer> res = new ArrayList<>();
		String clids = get("clid");
		if (clids.contains("|")) {
			for (String clid : clids.split("\\|")) {
				res.add(toInt(clid));
			}
		} else {
			res.add(toInt(get("clid")));
		}
		
		return res;
	}
	
	public int getClientID() {
		return getClientIDs().get(0);
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
		return "ClientMoveEvent [ClientIDs=" + getClientIDs() + ",ReasonID=" + getReasonID() + ",TargetChannelID=" + getTargetChannelID() + "]";
	}
}
