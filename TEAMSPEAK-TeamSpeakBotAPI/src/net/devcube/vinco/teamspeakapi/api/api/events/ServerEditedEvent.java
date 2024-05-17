package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.EncryptionMode;
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
	
	private boolean hasBeenEdited(String value) {
		return getSplitMap().containsKey(value);
	}
	
	public boolean hasNameBeenEdited() {
		return hasBeenEdited("virtualserver_name");
	}
	
	public String getEditedName() {
		return get("virtualserver_name");
	}
	
	public boolean hasCodecEncryptionBeenEdited() {
		return hasBeenEdited("virtualserver_codec_encryption_mode");
	}
	
	public int getEditedEncryptionModeInt() {
		return toInt(get("virtualserver_codec_encryption_mode"));
	}
	
	public EncryptionMode getEditedEncryptionMode() {
		int mode = getEditedEncryptionModeInt();
		for (EncryptionMode encryptionModes : EncryptionMode.values()) {
			if (encryptionModes.getValue() == mode) {
				return encryptionModes;
			}
		}
		return null;
	}
	
	public boolean hasDefaultServerGroupBeenEdited() {
		return hasBeenEdited("virtualserver_default_server_group");
	}
	
	public int getEditedDefaultServerGroup() {
		return toInt(get("virtualserver_default_server_group"));
	}
	
	public boolean hasDefaultChannelGroupBeenEdited() {
		return hasBeenEdited("virtualserver_default_channel_group");
	}
	
	public int getEditedDefaultChannelGroup() {
		return toInt(get("virtualserver_default_channel_group"));
	}
	
	public boolean hasHostBannerURLBeenEdited() {
		return hasBeenEdited("virtualserver_hostbanner_url");
	}
	
	public String getEditedHostBannerURL() {
		return get("virtualserver_hostbanner_url");
	}
	
	public boolean hasHostBannerGFXURLBeenEdited() {
		return hasBeenEdited("virtualserver_hostbanner_gfx_url");
	}
	
	public String getEditedHostBannerGFXURL() {
		return get("virtualserver_hostbanner_gfx_url");
	}
	
	public boolean hasHostBannerGFXIntervalBeenEdited() {
		return hasBeenEdited("virtualserver_hostbanner_gfx_interval");
	}
	
	public int getEditedHostBannerGFXInterval() {
		return toInt(get("virtualserver_hostbanner_gfx_interval"));
	}
	
	public boolean hasPrioritySpeakerModifierBeenEdited() {
		return hasBeenEdited("virtualserver_priority_speaker_dimm_modificator");
	}
	
	public int getEditedPrioritySpeakerModifier() {
		String info = get("virtualserver_priority_speaker_dimm_modificator");
		return toInt(info.substring(0, info.indexOf(".")));
	}
	
	public boolean hasTooltipBeenEdited() {
		return hasBeenEdited("virtualserver_hostbutton_tooltip");
	}
	
	public String getEditedTooltip() {
		return get("virtualserver_hostbutton_tooltip");
	}
	
	public boolean hasHostbuttonURLBeenEdited() {
		return hasBeenEdited("virtualserver_hostbutton_url");
	}
	
	public String getEditedHostbuttonURL() {
		return get("virtualserver_hostbutton_url");
	}
	
	public boolean hasHostbuttonGFXURLBeenEdited() {
		return hasBeenEdited("virtualserver_hostbutton_gfx_url");
	}
	
	public String getEditedHostbuttonGFXURL() {
		return get("virtualserver_hostbutton_gfx_url");
	}
	
	public boolean hasPoneticNameBeenEdited() {
		return hasBeenEdited("virtualserver_name_phonetic");
	}
	
	public String getEditedPhoneticName() {
		return get("virtualserver_name_phonetic");
	}
	
	public boolean hasIconIDBeenEdited() {
		return hasBeenEdited("virtualserver_icon_id");
	}
	
	public int getEditedIconID() {
		return toInt(get("virtualserver_icon_id"));
	}
	
	public boolean hasHostbannerModeBeenEdited() {
		return hasBeenEdited("virtualserver_hostbanner_mode");
	}
	
	public int getEditedHostbannerMode() {
		return toInt(get("virtualserver_hostbanner_mode"));
	}
	
	public boolean hasChannelDeleteDelayBeenEdited() {
		return hasBeenEdited("virtualserver_channel_temp_delete_delay_default");
	}
	
	public int getEditedChannelDeleteDelay() {
		return toInt(get("virtualserver_hostbanner_mode"));
	}
	
	/**
	 * @return everytime 10, because it is the id for serverediting
	 */
	public int getReasonID() {
		return toInt(get("reasonid"));
	}
}
