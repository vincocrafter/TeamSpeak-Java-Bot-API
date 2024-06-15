package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel description edited event in the TeamSpeak API.
 * This event is triggered whenever the description of a channel is edited on the server.
 */
public class ChannelDescriptionEditedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelDescriptionEditedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelDescriptionEditedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the channel whose description was edited.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Returns a string representation of the {@code ChannelDescriptionEditedEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelDescriptionEditedEvent");
        sb.append("[ChannelID=").append(getChannelID()).append("]");
        return sb.toString();
    }
}