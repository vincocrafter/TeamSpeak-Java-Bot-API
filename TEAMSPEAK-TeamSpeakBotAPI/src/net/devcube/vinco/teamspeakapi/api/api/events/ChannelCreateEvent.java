package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel creation event in the TeamSpeak API.
 * This event is triggered whenever a new channel is created on the server.
 */
public class ChannelCreateEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelCreateEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelCreateEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the client who created the channel.
     *
     * @return the client ID
     */
    public int getClientID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the name of the client who created the channel.
     *
     * @return the client name
     */
    public String getClientName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the unique identifier (UUID) of the client who created the channel.
     *
     * @return the client UUID
     */
    public String getClientUUID() {
        return get("invokeruid");
    }

    /**
     * Gets the ID of the newly created channel.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Gets the ID of the parent channel of the newly created channel.
     *
     * @return the parent channel ID
     */
    public int getChannelParentID() {
        return toIntI("cpid");
    }

    /**
     * Gets the name of the newly created channel.
     *
     * @return the channel name
     */
    public String getChannelName() {
        return Formatter.toNormalFormat(get("channel_name"));
    }

    /**
     * Gets the topic of the newly created channel.
     *
     * @return the channel topic
     */
    public String getChannelTopic() {
        return Formatter.toNormalFormat(get("channel_topic"));
    }

    /**
     * Gets the codec used by the newly created channel.
     *
     * @return the channel codec
     */
    public ChannelCodec getChannelCodec() {
        int codec = toIntI("channel_codec");
        for (ChannelCodec channelcodec : ChannelCodec.values()) {
            if (channelcodec.getValue() == codec) {
                return channelcodec;
            }
        }
        return null;
    }

    /**
     * Gets the codec quality of the newly created channel.
     *
     * @return the channel codec quality
     */
    public int getChannelQuality() {
        return toIntI("channel_codec_quality");
    }

    /**
     * Gets the order position of the newly created channel.
     *
     * @return the channel order
     */
    public int getChannelOrder() {
        return toIntI("channel_order");
    }

    /**
     * Gets the maximum number of clients allowed in the newly created channel.
     *
     * @return the maximum number of clients
     */
    public int getChannelMaxClients() {
        return toIntI("channel_maxclients");
    }

    /**
     * Gets the maximum number of family clients allowed in the newly created channel.
     *
     * @return the maximum number of family clients
     */
    public int getChannelMaxFamilyClients() {
        return toIntI("channel_maxfamilyclients");
    }

    /**
     * Checks if the newly created channel is permanent.
     *
     * @return {@code true} if the channel is permanent, {@code false} otherwise
     */
    public boolean isChannelPermanent() {
        return toBolI("channel_flag_permanent");
    }

    /**
     * Checks if the newly created channel is semi-permanent.
     *
     * @return {@code true} if the channel is semi-permanent, {@code false} otherwise
     */
    public boolean isChannelSemiPermanent() {
        return toBolI("channel_flag_semi_permanent");
    }
    
    /**
     * Checks if the channel has the default flag set.
     *
     * @return {@code true} if the channel has the default flag set; {@code false} otherwise.
     */
    public boolean isChannelDefault() {
        return toBolI("channel_flag_default");
    }
    
    /**
     * Checks if the newly created channel has a password.
     *
     * @return {@code true} if the channel has a password, {@code false} otherwise
     */
    public boolean hasChannelPassword() {
        return toBolI("channel_flag_password");
    }
    
    /**
     * Checks if the channel's codec is unencrypted.
     *
     * @return {@code true} if the channel's codec is unencrypted; {@code false} otherwise.
     */
    public boolean isUnencrypted() {
        return toBolI("channel_codec_is_unencrypted");
    }

    /**
     * Retrieves the channel's delete delay in seconds.
     *
     * @return The delete delay of the channel.
     */
    public int getDeleteDelay() {
        return toIntI("channel_delete_delay");
    }

    /**
     * Checks if the channel has unlimited maximum clients.
     *
     * @return {@code true} if the channel has unlimited maximum clients; {@code false} otherwise.
     */
    public boolean isMaxClientsUnlimited() {
        return toBolI("channel_flag_maxclients_unlimited");
    }

    /**
     * Checks if the channel has unlimited maximum family clients.
     *
     * @return {@code true} if the channel has unlimited maximum family clients; {@code false} otherwise.
     */
    public boolean isMaxFamilyClientsUnlimited() {
        return toBolI("channel_flag_maxfamilyclients_unlimited");
    }

    /**
     * Checks if the channel inherits maximum family clients from its parent.
     *
     * @return {@code true} if the channel inherits maximum family clients from its parent; {@code false} otherwise.
     */
    public boolean isMaxFamilyClientsInherited() {
        return toBolI("channel_flag_maxfamilyclients_inherited");
    }

    /**
     * Retrieves the needed talk power required to speak in the channel.
     *
     * @return The needed talk power required to speak in the channel.
     */
    public int getNeededTalkPower() {
        return toIntI("channel_needed_talk_power");
    }

    /**
     * Retrieves the phonetic name of the channel.
     *
     * @return The phonetic name of the channel.
     */
    public String getNamePhonetic() {
        return get("channel_name_phonetic");
    }

    
    /**
     * Returns a string representation of the {@code ChannelCreateEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelCreateEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientName=").append(getClientName())
          .append(",ClientUUID=").append(getClientUUID())
          .append(",ChannelID=").append(getChannelID())
          .append(",ChannelName=").append(getChannelName())
          .append("]");
        return sb.toString();
    }

}
