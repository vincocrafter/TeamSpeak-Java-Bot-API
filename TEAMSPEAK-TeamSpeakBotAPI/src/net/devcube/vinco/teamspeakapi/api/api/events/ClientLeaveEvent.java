package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ClientLeaveEvent extends BaseEvent {

	public ClientLeaveEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	/**
	 * You should not try to use 
	 * getClientInfo() or
	 * getClient()
	 * because the Client is already Offline and the result would be null.
	 * 
	 * @see Ts3SyncAPI#getClientInfo()
	 * @see Ts3SyncAPI#getClient()
	 */
	
	@Override
	public int getClientID() {
		return toInt(get("clid"));
	}
	
	public int getClientChannel() {
		return toInt(get("cfid"));
	}
	
	/**
	 * @return -1, if there is no reasonid
	 */
	public int getReasonID() {
		if (get("reasonid") != null) { //to prevent an Error in toInt, if there is no reasonid in the information Array
			return toInt(get("reasonid"));
		}
		return -1;
	}

	public boolean hasBeenKicked() {
		return getReasonID() == 5;
	}
	
	public boolean hasBeenBanned() {
		return getReasonID() == 6;
	}
	
	public boolean hasClientLostConnection() {
		return getReasonID() == 3;
	}
	
	/**
	 * @return -1, if there is no invokerid
	 */
	public int getInvokerID() {
		if(get("invokerid") != null) { //to prevent an Error in toInt, if there is no invokerid in the information Array
			return toInt(get("invokerid"));
		}
		return -1;
	}
	
	public String getInvokerName() {
		return Formatter.toNormalFormat(get("invokername"));
	}
	
	public String getInvokerUUID() {
		return get("invokeruid");
	}
	
	public String getReasonMsg() {
		return get("reasonmsg");
	}
	/**
	 * @return -1, if there is no bantime
	 */
	public int getBanTime() {
		if(get("bantime") != null) { //to prevent an Error in toInt, if there is no bantime in the information Array
			return toInt(get("bantime"));
		}
		return -1;
	}
}
