package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ServerEditedEvent extends BaseEvent {

	
	public ServerEditedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getClientID() {
		return toInt(get("invokerid"));
	}

	public String getClientName() {
		return get("invokername");
	}

	public String getClientUUID() {
		return get("invokeruid");
	}
	
	/**
	 * @return everytime 10, because it is the id for serverediting
	 */
	public int getReasonID() {
		return toInt(get("reasonid"));
	}
}
