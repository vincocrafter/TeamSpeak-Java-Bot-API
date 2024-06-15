package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel deletion event in the TeamSpeak API.
 * This event is triggered whenever a channel is deleted from the server.
 */
public class ChannelDeletedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelDeletedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelDeletedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the name of the client who deleted the channel.
     *
     * @return the client name
     */
    public String getClientName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the ID of the client who deleted the channel.
     *
     * @return the client ID
     */
    public int getClientID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the unique identifier (UUID) of the client who deleted the channel.
     *
     * @return the client UUID
     */
    public String getClientUUID() {
        return get("invokeruid");
    }

    /**
     * Gets the ID of the deleted channel.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Returns a string representation of the {@code ChannelDeletedEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelDeleteEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientName=").append(getClientName())
          .append(",ClientUUID=").append(getClientUUID())
          .append(",ChannelID=").append(getChannelID())
          .append("]");
        return sb.toString();
    }
}
