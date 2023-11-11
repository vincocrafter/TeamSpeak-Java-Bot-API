package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ChannelInfo extends DefaultInfo {


	public ChannelInfo(String[] infos) {
		super(infos);
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	public int getID() {
		return getChannelID();
	}

	public String getOrginalName() {
		return get("channel_name");
	}

	public String getName() {
		return Formatter.toNormalFormat(get("channel_name"));
	}

	public String getTopic() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}

	public String getDescription() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}

	public String getPassword() {
		return get("channel_password");
	}

	public int getQuality() {
		return toInt(get("channel_codec_quality"));
	}

	
	public ChannelCodec getCodec() {
		int codec = toInt(get("channel_codec"));
		
		for (ChannelCodec channelcodec : ChannelCodec.values()) {
			if (channelcodec.getValue() == codec) {
				return channelcodec;
			}
		}
		return null;
	}

	public int getMaxClients() {
		return toInt(get("channel_maxclients"));
	}

	public int getMaxFamilyClients() {
		return toInt(get("channel_maxfamilyclients"));
	}
	
	public int getOrder() {
		return toInt(get("channel_order"));
	}

	public int getNeededTalkPower() {
		return toInt(get("channel_needed_talk_power"));
	}

	public boolean isPermanent() {
		return toBol(toInt(get("channel_flag_permanent")));
	}

	public boolean isSemiPermanent() {
		return toBol(toInt(get("channel_flag_semi_permanent")));
	}

	public boolean isDefault() {
		return toBol(toInt(get("channel_flag_default")));
	}

	public boolean hasPassword() {
		return toBol(toInt(get("channel_flag_password")));
	}
	
	public String getFilePath() {
		return get("channel_filepath");
	}
	
	/**
	 * Formatted to a normal String format using the Formatter.
	 * @return
	 */
	
	public String getFilePathStr() {
		return Formatter.toNormalFormat(get("channel_filepath"));
	}
	
	public int getDeleteDelay() {
		return toInt(get("channel_delete_delay"));
	}

	public int getSecondsEmpty() {
		return toInt(get("seconds_empty"));
	}

	public int getIconID() {
		return toInt(get("channel_icon_id"));
	}
	
	public int getTotalClients() {
		return toInt(get("total_clients"));
	}
	
	public int getNeededSubscribePower() {
		return toInt(get("channel_needed_subscribe_power"));
	}
	
	@Override
	public String toString() {
		return "Channel[ID=" + getChannelID() + ",Name=" + getName() + "]";
	}
}
