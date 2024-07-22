package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelBannerMode;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ChannelInfo extends DefaultInfo {

	public ChannelInfo(String infos) {
		super(infos);
	}

	public int getChannelID() {
		return toIntI("cid");
	}
		
	public int getID() {
		return getChannelID();
	}
	
	public int getId() {
		return getChannelID();
	}
	
	public int getChannelId() {
		return getChannelID();
	}
	
	public int getChannelParentID() {
		return toIntI("pid");
	}
	
	public String getOriginalName() {
		return get("channel_name");
	}

	public String getName() {
		return Formatter.toNormalFormat(get("channel_name"));
	}

	public String getTopic() {
		return Formatter.toNormalFormat(get("channel_topic"));
	}

	public String getDescription() {
		return Formatter.toNormalFormat(get("channel_description"));
	}

	public String getPassword() {
		return get("channel_password");
	}

	public int getQuality() {
		return toIntI("channel_codec_quality");
	}

	public ChannelCodec getCodec() {
		int codec = toIntI("channel_codec");
		
		for (ChannelCodec channelcodec : ChannelCodec.values()) {
			if (channelcodec.getValue() == codec) {
				return channelcodec;
			}
		}
		return null;
	}

	public int getMaxClients() {
		return toIntI("channel_maxclients");
	}

	public int getMaxFamilyClients() {
		return toIntI("channel_maxfamilyclients");
	}
	
	public int getOrder() {
		return toIntI("channel_order");
	}

	public int getNeededTalkPower() {
		return toIntI("channel_needed_talk_power");
	}

	public boolean isPermanent() {
		return toBolI("channel_flag_permanent");
	}

	public boolean isSemiPermanent() {
		return toBolI("channel_flag_semi_permanent");
	}

	public boolean isDefault() {
		return toBolI("channel_flag_default");
	}

	public boolean hasPassword() {
		return toBolI("channel_flag_password");
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
		return toIntI("channel_delete_delay");
	}

	public int getSecondsEmpty() {
		return toIntI("seconds_empty");
	}

	public int getIconID() {
		return toIntI("channel_icon_id");
	}

	public String getCodecLatencyFactor() {
		return get("channel_codec_latency_factor");
	}
	
	public boolean isUnEncrypted() {
		return toBolI("channel_codec_is_unencrypted");
	}

	public String getUUID() {
		return Formatter.toNormalFormat(get("channel_unique_identifier"));
	}
	
	public boolean hasMaxClientsUnlimited() {
		return toBolI("channel_flag_maxclients_unlimited");
	}
	
	public boolean hasMaxFamilyClientsUnlimited() {
		return toBolI("channel_flag_maxfamilyclients_unlimited");
	}
	
	public boolean hasMaxFamilyClientsInherited() {
		return toBolI("channel_flag_maxfamilyclients_inherited");
	}
	
	public boolean isForcedSilence() {
		return toBolI("channel_forced_silence");
	}
	
	public String getPhoneticName() {
		return get("channel_name_phonetic");
	}
	
	public String getBannerGfxURL() {
		return get("channel_banner_gfx_url");
	}
	
	public int getBannerModeInt() {
		return toIntI("channel_banner_mode");
	}

	public ChannelBannerMode getBannerMode() {
		int mode = getBannerModeInt();
		for (ChannelBannerMode bannerMode : ChannelBannerMode.values()) {
			if (bannerMode.getValue() == mode) {
				return bannerMode;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Channel[");
		result.append("ID=").append(getID());
		result.append(",Name=").append(getName());
		result.append("]");
		return  result.toString();
	}
}
