package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;

public class ChannelEditedEvent extends BaseEvent {

	public ChannelEditedEvent(String[] infos) {
		super(infos);
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
	
	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	
	/**
	 * 
	 * @return everytime 10, because it is the id for channelediting
	 */
	public int getReasonID() {
		return toInt(get("reasonid"));
	}
	
}
