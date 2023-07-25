package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ChannelCreateEvent extends BaseEvent {

	

	public ChannelCreateEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getClientID() {
		return Integer.parseInt(get("invokerid"));
	}

	public String getClientName() {
		return Formatter.toNormalFormat(get("invokername"));
	}
	
	public String getClientUUID() {
		return get("invokeruid");
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	public int getChannelParentID() {
		return toInt(get("cpid"));
	}
	
	public String getChannelName() {
		return Formatter.toNormalFormat(get("channel_name"));
	}

	public String getChannelTopic() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}
	
	
	public ChannelCodec getChannelCodec() {
		int codec = toInt(get("channel_codec"));
		
		for (ChannelCodec channelcodec : ChannelCodec.values()) {
			if (channelcodec.getValue() == codec) {
				return channelcodec;
			}
		}
		return null;
	
	}
	
	public int getChannelQuality() {
		return toInt(get("channel_codec_quality"));
	}
	
	public int getChannelOrder() {
		return toInt(get("channel_order"));
	}
	
	public int getChannelMaxClients() {
		return toInt(get("channel_maxclients"));
	}
	
	public int getChannelMaxFamilyClients() {
		return toInt(get("channel_maxfamilyclients"));
	}
	
	
	
	public String toString() {
		return "ChannelCreateEvent[ClientID=" + getClientID() + ",ClientName=" + getClientName() + ",ClientUUID=" + getClientUUID() + ",ChannelID=" + getChannelID() + ",ChannelName="
				+ getChannelName() + "]";
	}
}
