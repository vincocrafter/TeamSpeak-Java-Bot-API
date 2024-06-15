package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel password changed event in the TeamSpeak API.
 * This event is triggered whenever the password of a channel is changed on the server.
 */
public class ChannelPasswordChangedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelPasswordChangedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelPasswordChangedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the channel whose password was changed.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Returns a string representation of the {@code ChannelPasswordChangedEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelPasswordChangedEvent");
        sb.append("[ChannelID=").append(getChannelID()).append("]");
        return sb.toString();
    }
}