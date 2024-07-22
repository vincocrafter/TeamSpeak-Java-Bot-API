package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.EncryptionMode;
import net.devcube.vinco.teamspeakapi.api.api.property.HostBannerMode;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a server edited event in the TeamSpeak API.
 * This event is triggered whenever a server configuration is edited.
 */
public class ServerEditedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ServerEditedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ServerEditedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the client who edited the server.
     *
     * @return the client ID
     */
    public int getClientID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the name of the client who edited the server.
     *
     * @return the client name
     */
    public String getClientName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the unique identifier (UUID) of the client who edited the server.
     *
     * @return the client UUID
     */
    public String getClientUUID() {
        return get("invokeruid");
    }

    /**
     * Checks if a specific server property has been edited.
     *
     * @param value the property name to check
     * @return true if the property has been edited, false otherwise
     */
    private boolean hasBeenEdited(String value) {
        return getSplitMap().containsKey(value);
    }

    /**
     * Checks if the server name has been edited.
     *
     * @return true if the server name has been edited, false otherwise
     */
    public boolean hasNameBeenEdited() {
        return hasBeenEdited("virtualserver_name");
    }

    /**
     * Gets the edited server name.
     *
     * @return the edited server name
     */
    public String getEditedName() {
        return get("virtualserver_name");
    }

    /**
     * Checks if the server codec encryption mode has been edited.
     *
     * @return true if the server codec encryption mode has been edited, false otherwise
     */
    public boolean hasCodecEncryptionModeBeenEdited() {
        return hasBeenEdited("virtualserver_codec_encryption_mode");
    }

    /**
     * Gets the edited server codec encryption mode as an integer value.
     *
     * @return the edited server codec encryption mode
     */
    public int getEditedEncryptionModeInt() {
        return toIntI("virtualserver_codec_encryption_mode");
    }

    /**
     * Gets the edited server codec encryption mode as an {@link EncryptionMode} enum.
     *
     * @return the edited server codec encryption mode
     */
    public EncryptionMode getEditedEncryptionMode() {
        int mode = getEditedEncryptionModeInt();
        for (EncryptionMode encryptionModes : EncryptionMode.values()) {
            if (encryptionModes.getValue() == mode) {
                return encryptionModes;
            }
        }
        return null;
    }

    /**
     * Checks if the default server group has been edited.
     *
     * @return true if the default server group has been edited, false otherwise
     */
    public boolean hasDefaultServerGroupBeenEdited() {
        return hasBeenEdited("virtualserver_default_server_group");
    }

    /**
     * Gets the edited default server group.
     *
     * @return the edited default server group
     */
    public int getEditedDefaultServerGroup() {
        return toIntI("virtualserver_default_server_group");
    }

    /**
     * Checks if the default channel group has been edited.
     *
     * @return true if the default channel group has been edited, false otherwise
     */
    public boolean hasDefaultChannelGroupBeenEdited() {
        return hasBeenEdited("virtualserver_default_channel_group");
    }

    /**
     * Gets the edited default channel group.
     *
     * @return the edited default channel group
     */
    public int getEditedDefaultChannelGroup() {
        return toIntI("virtualserver_default_channel_group");
    }

    /**
     * Checks if the host banner URL has been edited.
     *
     * @return true if the host banner URL has been edited, false otherwise
     */
    public boolean hasHostBannerURLBeenEdited() {
        return hasBeenEdited("virtualserver_hostbanner_url");
    }

    /**
     * Gets the edited host banner URL.
     *
     * @return the edited host banner URL
     */
    public String getEditedHostBannerURL() {
        return Formatter.toNormalFormat(get("virtualserver_hostbanner_url"));
    }

    /**
     * Checks if the host banner graphics URL has been edited.
     *
     * @return true if the host banner graphics URL has been edited, false otherwise
     */
    public boolean hasHostBannerGFXURLBeenEdited() {
        return hasBeenEdited("virtualserver_hostbanner_gfx_url");
    }

    /**
     * Gets the edited host banner graphics URL.
     *
     * @return the edited host banner graphics URL
     */
    public String getEditedHostBannerGFXURL() {
        return Formatter.toNormalFormat(get("virtualserver_hostbanner_gfx_url"));
    }

    /**
     * Checks if the host banner graphics interval has been edited.
     *
     * @return true if the host banner graphics interval has been edited, false otherwise
     */
    public boolean hasHostBannerGFXIntervalBeenEdited() {
        return hasBeenEdited("virtualserver_hostbanner_gfx_interval");
    }

    /**
     * Gets the edited host banner graphics interval.
     *
     * @return the edited host banner graphics interval
     */
    public int getEditedHostBannerGFXInterval() {
        return toIntI("virtualserver_hostbanner_gfx_interval");
    }

    /**
     * Checks if the priority speaker modifier has been edited.
     *
     * @return true if the priority speaker modifier has been edited, false otherwise
     */
    public boolean hasPrioritySpeakerModifierBeenEdited() {
        return hasBeenEdited("virtualserver_priority_speaker_dimm_modificator");
    }

    /**
     * Gets the edited priority speaker modifier.
     *
     * @return the edited priority speaker modifier
     */
    public int getEditedPrioritySpeakerModifier() {
        String info = get("virtualserver_priority_speaker_dimm_modificator");
        return toInt(info.substring(0, info.indexOf(".")));
    }

    /**
     * Checks if the host button tooltip has been edited.
     *
     * @return true if the host button tooltip has been edited, false otherwise
     */
    public boolean hasTooltipBeenEdited() {
        return hasBeenEdited("virtualserver_hostbutton_tooltip");
    }

    /**
     * Gets the edited host button tooltip.
     *
     * @return the edited host button tooltip
     */
    public String getEditedTooltip() {
        return get("virtualserver_hostbutton_tooltip");
    }

    /**
     * Checks if the host button URL has been edited.
     *
     * @return true if the host button URL has been edited, false otherwise
     */
    public boolean hasHostbuttonURLBeenEdited() {
        return hasBeenEdited("virtualserver_hostbutton_url");
    }

    /**
     * Gets the edited host button URL.
     *
     * @return the edited host button URL
     */
    public String getEditedHostbuttonURL() {
        return Formatter.toNormalFormat(get("virtualserver_hostbutton_url"));
    }

    /**
     * Checks if the host button graphics URL has been edited.
     *
     * @return true if the host button graphics URL has been edited, false otherwise
     */
    public boolean hasHostbuttonGFXURLBeenEdited() {
        return hasBeenEdited("virtualserver_hostbutton_gfx_url");
    }

    /**
     * Gets the edited host button graphics URL.
     *
     * @return the edited host button graphics URL
     */
    public String getEditedHostbuttonGFXURL() {
        return Formatter.toNormalFormat(get("virtualserver_hostbutton_gfx_url"));
    }

    /**
     * Checks if the phonetic name has been edited.
     *
     * @return true if the phonetic name has been edited, false otherwise
     */
    public boolean hasPhoneticNameBeenEdited() {
        return hasBeenEdited("virtualserver_name_phonetic");
    }

    /**
     * Gets the edited phonetic name.
     *
     * @return the edited phonetic name
     */
    public String getEditedPhoneticName() {
        return Formatter.toNormalFormat(get("virtualserver_name_phonetic"));
    }

    /**
     * Checks if the server icon ID has been edited.
     *
     * @return true if the server icon ID has been edited, false otherwise
     */
    public boolean hasIconIDBeenEdited() {
        return hasBeenEdited("virtualserver_icon_id");
    }

    /**
     * Gets the edited server icon ID.
     *
     * @return the edited server icon ID
     */
    public int getEditedIconID() {
        return toIntI("virtualserver_icon_id");
    }

    /**
     * Checks if the host banner mode has been edited.
     *
     * @return true if the host banner mode has been edited, false otherwise
     */
    public boolean hasHostBannerModeBeenEdited() {
        return hasBeenEdited("virtualserver_hostbanner_mode");
    }

    /**
     * Gets the edited host banner mode.
     *
     * @return the edited host banner mode
     */
    public int getEditedHostBannerModeInt() {
        return toIntI("virtualserver_hostbanner_mode");
    }

    public HostBannerMode getEditedHostBannerMode() {
        int mode = getEditedHostBannerModeInt();
        for (HostBannerMode hostBannerModes : HostBannerMode.values()) {
            if (hostBannerModes.getValue() == mode) {
                return hostBannerModes;
            }
        }
        return null;
    }

    /**
     * Checks if the channel delete delay has been edited.
     *
     * @return true if the channel delete delay has been edited, false otherwise
     */

	public boolean hasChannelDeleteDelayBeenEdited() {
		return hasBeenEdited("virtualserver_channel_temp_delete_delay_default");
	}
	
	/**
	 * Gets the edited channel delete delay.
	 *
	 * @return the edited channel delete delay
	 */
	public int getEditedChannelDeleteDelay() {
	    return toIntI("virtualserver_channel_temp_delete_delay_default");
	}

	/**
	 * Gets the reason ID associated with the server editing event.
	 *
	 * @return the reason ID (always 10 for server editing)
	 */
	public int getReasonID() {
	    return toIntI("reasonid");
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("ServerEditedEvent[")
	      .append("ClientID=").append(getClientID())
	      .append(",ClientName=").append(getClientName())
	      .append(",ClientUUID=").append(getClientUUID())
	      .append("]");
	      return sb.toString();
	}
}
