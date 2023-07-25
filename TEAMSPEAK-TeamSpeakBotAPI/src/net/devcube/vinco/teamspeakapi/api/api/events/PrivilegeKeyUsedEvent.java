package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class PrivilegeKeyUsedEvent extends BaseEvent {

	
	public PrivilegeKeyUsedEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
		// TODO Auto-generated constructor stub
	}

	public int getClientID() {
		return toInt(get("clid"));
	}

	public int getClientDataBaseID() {
		return toInt(get("cldbid"));
	}

	public String getClientUUID() {
		return get("cluid");
	}

	public String getToken() {
		return get("token");
	}

	public int getGroupID() {
		return toInt(get("token1"));
	}

	public int getChannelID() {
		return toInt(get("token2"));
	}

	public PrivilegeKeyType getKeyType() {
		return getChannelID() == 0 ? PrivilegeKeyType.CHANNEL_GROUP : PrivilegeKeyType.SERVER_GROUP;
	}
}
