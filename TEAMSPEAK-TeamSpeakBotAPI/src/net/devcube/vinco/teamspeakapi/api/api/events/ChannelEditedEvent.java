package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel edited event in the TeamSpeak API.
 * This event is triggered whenever a channel is edited on the server.
 */
public class ChannelEditedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelEditedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelEditedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the client who edited the channel.
     *
     * @return the client ID
     */
    public int getClientID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the name of the client who edited the channel.
     *
     * @return the client name
     */
    public String getClientName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the unique identifier (UUID) of the client who edited the channel.
     *
     * @return the client UUID
     */
    public String getClientUUID() {
        return Formatter.toNormalFormat(get("invokeruid"));
    }

    /**
     * Gets the ID of the edited channel.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Gets the reason ID for the channel edit.
     * 
     * @return the reason ID, which is always 10 for channel editing
     */
    public int getReasonID() {
        return toIntI("reasonid");
    }
    
    private boolean hasBeenEdited(String value) {
		return getSplitMap().containsKey(value);
	}

    /**
     * Gets the name of the channel.
     *
     * @return the channel name
     */
    public String getEditedChannelName() {
        return get("channel_name");
    }

    /**
     * Checks if the channel name has been edited.
     *
     * @return true if the channel name has been edited, false otherwise
     */
    public boolean hasChannelNameBeenEdited() {
        return hasBeenEdited("channel_name");
    }

    /**
     * Gets the topic of the channel.
     *
     * @return the channel topic
     */
    public String getEditedChannelTopic() {
        return get("channel_topic");
    }

    /**
     * Checks if the channel topic has been edited.
     *
     * @return true if the channel topic has been edited, false otherwise
     */
    public boolean hasChannelTopicBeenEdited() {
        return hasBeenEdited("channel_topic");
    }

    /**
     * Gets the codec used in the channel.
     *
     * @return the channel codec
     */
    public int getEditedChannelCodecInt() {
        return toIntI("channel_codec");
    }

    public ChannelCodec getEditedChannelCodec() {
        int codec = getEditedChannelCodecInt();
        for (ChannelCodec channelcodec : ChannelCodec.values()) {
            if (channelcodec.getValue() == codec) {
                return channelcodec;
            }
        }
        return null;
    }

    /**
     * Checks if the channel codec has been edited.
     *
     * @return true if the channel codec has been edited, false otherwise
     */
    public boolean hasChannelCodecBeenEdited() {
        return hasBeenEdited("channel_codec");
    }

    /**
     * Gets the codec quality used in the channel.
     *
     * @return the channel codec quality
     */
    public int getEditedChannelCodecQuality() {
        return toIntI("channel_codec_quality");
    }

    /**
     * Checks if the channel codec quality has been edited.
     *
     * @return true if the channel codec quality has been edited, false otherwise
     */
    public boolean hasChannelCodecQualityBeenEdited() {
        return hasBeenEdited("channel_codec_quality");
    }

    /**
     * Gets the maximum number of clients allowed in the channel.
     *
     * @return the maximum number of clients
     */
    public int getEditedChannelMaxClients() {
        return toIntI("channel_maxclients");
    }

    /**
     * Checks if the maximum number of clients has been edited.
     *
     * @return true if the maximum number of clients has been edited, false otherwise
     */
    public boolean hasChannelMaxClientsBeenEdited() {
        return hasBeenEdited("channel_maxclients");
    }

    /**
     * Gets the maximum number of family clients allowed in the channel.
     *
     * @return the maximum number of family clients
     */
    public int getEditedChannelMaxFamilyClients() {
        return toIntI("channel_maxfamilyclients");
    }

    /**
     * Checks if the maximum number of family clients has been edited.
     *
     * @return true if the maximum number of family clients has been edited, false otherwise
     */
    public boolean hasChannelMaxFamilyClientsBeenEdited() {
        return hasBeenEdited("channel_maxfamilyclients");
    }

    /**
     * Gets the order of the channel.
     *
     * @return the channel order
     */
    public int getEditedChannelOrder() {
        return toIntI("channel_order");
    }

    /**
     * Checks if the channel order has been edited.
     *
     * @return true if the channel order has been edited, false otherwise
     */
    public boolean hasChannelOrderBeenEdited() {
        return hasBeenEdited("channel_order");
    }

    /**
     * Checks if the channel is permanent.
     *
     * @return true if the channel is permanent, false otherwise
     */
    public boolean isEditedChannelPermanent() {
        return toBolI("channel_flag_permanent");
    }

    /**
     * Checks if the channel permanency has been edited.
     *
     * @return true if the channel permanency has been edited, false otherwise
     */
    public boolean hasChannelPermanentBeenEdited() {
        return hasBeenEdited("channel_flag_permanent");
    }

    /**
     * Checks if the channel is semi-permanent.
     *
     * @return true if the channel is semi-permanent, false otherwise
     */
    public boolean isEditedChannelSemiPermanent() {
        return toBolI("channel_flag_semi_permanent");
    }

    /**
     * Checks if the channel semi-permanency has been edited.
     *
     * @return true if the channel semi-permanency has been edited, false otherwise
     */
    public boolean hasChannelSemiPermanentBeenEdited() {
        return hasBeenEdited("channel_flag_semi_permanent");
    }

    /**
     * Checks if the channel is default.
     *
     * @return true if the channel is default, false otherwise
     */
    public boolean isEditedChannelDefault() {
        return toBolI("channel_flag_default");
    }

    /**
     * Checks if the channel default status has been edited.
     *
     * @return true if the channel default status has been edited, false otherwise
     */
    public boolean hasChannelDefaultBeenEdited() {
        return hasBeenEdited("channel_flag_default");
    }

    /**
     * Checks if the channel has a password.
     *
     * @return true if the channel has a password, false otherwise
     */
    public boolean hasEditedChannelPassword() {
        return toBolI("channel_flag_password");
    }

    /**
     * Checks if the channel password status has been edited.
     *
     * @return true if the channel password status has been edited, false otherwise
     */
    public boolean hasChannelPasswordBeenEdited() {
        return hasBeenEdited("channel_flag_password");
    }

    /**
     * Gets the codec latency factor of the channel.
     *
     * @return the channel codec latency factor
     */
    public float getEditedChannelCodecLatencyFactor() {
        return toFloat("channel_codec_latency_factor");
    }

    /**
     * Checks if the channel codec latency factor has been edited.
     *
     * @return true if the channel codec latency factor has been edited, false otherwise
     */
    public boolean hasChannelCodecLatencyFactorBeenEdited() {
        return hasBeenEdited("channel_codec_latency_factor");
    }

    /**
     * Checks if the channel codec is unencrypted.
     *
     * @return true if the channel codec is unencrypted, false otherwise
     */
    public boolean isEditedChannelCodecUnencrypted() {
        return toBolI("channel_codec_is_unencrypted");
    }

    /**
     * Checks if the channel codec encryption status has been edited.
     *
     * @return true if the channel codec encryption status has been edited, false otherwise
     */
    public boolean hasChannelCodecUnencryptedBeenEdited() {
        return hasBeenEdited("channel_codec_is_unencrypted");
    }

    /**
     * Gets the delete delay of the channel.
     *
     * @return the channel delete delay
     */
    public int getEditedChannelDeleteDelay() {
        return toIntI("channel_delete_delay");
    }

    /**
     * Checks if the channel delete delay has been edited.
     *
     * @return true if the channel delete delay has been edited, false otherwise
     */
    public boolean hasChannelDeleteDelayBeenEdited() {
        return hasBeenEdited("channel_delete_delay");
    }

    /**
     * Checks if the maximum number of clients in the channel is unlimited.
     *
     * @return true if the maximum number of clients is unlimited, false otherwise
     */
    public boolean isEditedChannelMaxClientsUnlimited() {
        return toBolI("channel_flag_maxclients_unlimited");
    }

    /**
     * Checks if the channel maximum clients unlimited status has been edited.
     *
     * @return true if the channel maximum clients unlimited status has been edited, false otherwise
     */
    public boolean hasChannelMaxClientsUnlimitedBeenEdited() {
        return hasBeenEdited("channel_flag_maxclients_unlimited");
    }

    /**
     * Checks if the maximum number of family clients in the channel is unlimited.
     *
     * @return true if the maximum number of family clients is unlimited, false otherwise
     */
    public boolean isEditedChannelMaxFamilyClientsUnlimited() {
        return toBolI("channel_flag_maxfamilyclients_unlimited");
    }

    /**
     * Checks if the channel maximum family clients unlimited status has been edited.
     *
     * @return true if the channel maximum family clients unlimited status has been edited, false otherwise
     */
    public boolean hasChannelMaxFamilyClientsUnlimitedBeenEdited() {
        return hasBeenEdited("channel_flag_maxfamilyclients_unlimited");
    }

    /**
     * Checks if the maximum number of family clients in the channel is inherited.
     *
     * @return true if the maximum number of family clients is inherited, false otherwise
     */
    public boolean isEditedChannelMaxFamilyClientsInherited() {
        return toBolI("channel_flag_maxfamilyclients_inherited");
    }

    /**
     * Checks if the channel maximum family clients inherited status has been edited.
     *
     * @return true if the channel maximum family clients inherited status has been edited, false otherwise
     */
    public boolean hasChannelMaxFamilyClientsInheritedBeenEdited() {
        return hasBeenEdited("channel_flag_maxfamilyclients_inherited");
    }

    /**
     * Gets the needed talk power to enter the channel.
     *
     * @return the needed talk power
     */
    public int getEditedChannelNeededTalkPower() {
        return toIntI("channel_needed_talk_power");
    }

    /**
     * Checks if the channel needed talk power has been edited.
     *
     * @return true if the channel needed talk power has been edited, false otherwise
     */
    public boolean hasChannelNeededTalkPowerBeenEdited() {
        return hasBeenEdited("channel_needed_talk_power");
    }

    /**
     * Gets the phonetic name of the channel.
     *
     * @return the channel phonetic name
     */
    public String getEditedChannelNamePhonetic() {
        return get("channel_name_phonetic");
    }

    /**
     * Checks if the channel phonetic name has been edited.
     *
     * @return true if the channel phonetic name has been edited, false otherwise
     */
    public boolean hasChannelNamePhoneticBeenEdited() {
        return hasBeenEdited("channel_name_phonetic");
    }

    /**
     * Gets the icon ID of the channel.
     *
     * @return the channel icon ID
     */
    public int getEditedChannelIconID() {
        return toIntI("channel_icon_id");
    }

    /**
     * Checks if the channel icon ID has been edited.
     *
     * @return true if the channel icon ID has been edited, false otherwise
     */
    public boolean hasChannelIconIDBeenEdited() {
        return hasBeenEdited("channel_icon_id");
    }
    
    /**
     * Returns a string representation of the {@code ChannelEditedEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelEditedEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientName=").append(getClientName())
          .append(",ClientUUID=").append(getClientUUID())
          .append(",ChannelID=").append(getChannelID())
          .append(",ReasonID=").append(getReasonID())
          .append("]");
        return sb.toString();
    }
}