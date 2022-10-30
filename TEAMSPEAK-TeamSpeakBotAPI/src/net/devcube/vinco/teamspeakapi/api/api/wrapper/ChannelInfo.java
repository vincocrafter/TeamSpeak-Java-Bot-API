package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.io.File;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ChannelInfo extends DefaultInfo {

	private int cid;

	public ChannelInfo(String[] infos, int cid) {
		super(infos);
		this.cid = cid;
	}

	
	public int getChannelID() {
		return cid;
	}

	public String getChannelOrginalName() {
		return get("channel_name");
	}

	public String getChannelName() {
		return Formatter.toNormalFormat(get("channel_name"));
	}

	public String getChannelTopic() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}

	public String getChannelDescription() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}

	public String getChannelPassword() {
		return get("channel_password");
	}

	public int getChannelQuality() {
		return toInt(get("channel_codec_quality"));
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

	public int getChannelMaxClients() {
		return toInt(get("channel_maxclients"));
	}

	public int getChannelMaxFamilyClients() {
		return toInt(get("channel_maxfamilyclients"));
	}
	
	public int getChannelOrder() {
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

	public File getFilePath() {
		return new File(get("channel_filepath").replace("\\/", "/"));
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
}
