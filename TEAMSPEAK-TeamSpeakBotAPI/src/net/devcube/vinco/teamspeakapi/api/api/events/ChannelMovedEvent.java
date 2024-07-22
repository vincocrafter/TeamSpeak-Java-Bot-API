/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023
 *
 * Datum : 06.04.2023
 *
 * Uhrzeit : 12:49:50
 */
package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a channel moved event in the TeamSpeak API.
 * This event is triggered whenever a channel is moved on the server.
 */
public class ChannelMovedEvent extends BaseEvent {

    /**
     * Constructs a new {@code ChannelMovedEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ChannelMovedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Gets the ID of the moved channel.
     *
     * @return the channel ID
     */
    public int getChannelID() {
        return toIntI("cid");
    }

    /**
     * Gets the ID of the parent channel to which the channel was moved.
     *
     * @return the parent channel ID
     */
    public int getChannelParentID() {
        return toIntI("cpid");
    }

    /**
     * Gets the order position of the moved channel.
     *
     * @return the order position
     */
    public int getOrder() {
        return toIntI("order");
    }

    /**
     * Gets the reason ID for the channel move.
     *
     * @return the reason ID
     */
    public int getReasonID() {
        return toIntI("reasonid");
    }

    /**
     * Gets the ID of the invoker who moved the channel.
     *
     * @return the invoker ID
     */
    public int getInvokerID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the name of the invoker who moved the channel.
     *
     * @return the invoker name
     */
    public String getInvokerName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the unique identifier (UUID) of the invoker who moved the channel.
     *
     * @return the invoker UUID
     */
    public String getInvokerUUID() {
        return Formatter.toNormalFormat(get("invokeruid"));
    }

    /**
     * Gets the ID of the client involved in the channel move.
     *
     * @return the client ID
     */
    public int getClientID() {
        return toIntI("clid");
    }

    /**
     * Returns a string representation of the {@code ChannelMovedEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChannelMovedEvent");
        sb.append("[ChannelID=").append(getChannelID())
          .append(",ChannelParentID=").append(getChannelParentID())
          .append(",Order=").append(getOrder())
          .append(",ReasonID=").append(getReasonID())
          .append(",InvokerID=").append(getInvokerID())
          .append(",InvokerName=").append(getInvokerName())
          .append(",InvokerUUID=").append(getInvokerUUID())
          .append("]");
        return sb.toString();
    }
}
