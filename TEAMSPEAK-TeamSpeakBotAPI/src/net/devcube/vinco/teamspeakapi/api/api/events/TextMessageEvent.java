package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a TextMessageEvent that occurs when a text message is sent in TeamSpeak to/from the query.
 * This event provides information about the sender, message content, and target recipients.
 */
public class TextMessageEvent extends BaseEvent {

    /**
     * Constructs a TextMessageEvent object with the given information and server query.
     *
     * @param infos      Array of information related to the event.
     * @param serverQuery The Ts3ServerQuery instance used to query the TeamSpeak server.
     */
    public TextMessageEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Retrieves the client ID of the message sender.
     *
     * @return The client ID of the message sender.
     */
    public int getClientID() {
        return toIntI("invokerid");
    }

    /**
     * Retrieves the name of the client who sent the message.
     *
     * @return The name of the client who sent the message.
     */
    public String getClientName() {
        return get("invokername");
    }

    /**
     * Retrieves the target mode ID indicating the type of recipients the message is intended for.
     *
     * @return The target mode ID of the message.
     */
    public int getTargetModeID() {
        return toIntI("targetmode");
    }

    /**
     * Retrieves the message content sent by the client.
     *
     * @return The message content sent by the client.
     */
    public String getMessage() {
        return Formatter.toNormalFormat(get("msg"));
    }

    /**
     * Retrieves the TextMessageType based on the target mode ID.
     *
     * @return The TextMessageType corresponding to the target mode ID.
     */
    public TextMessageType getTextMessageType() {
        for (TextMessageType type : TextMessageType.values()) {
            if (getTargetModeID() == type.getValue()) {
                return type;
            }
        }
        return null;
    }

    /**
     * Alias for {@link #getTextMessageType()}.
     *
     * @return The TextMessageType of the message.
     */
    public TextMessageType getTargetMode() {
        return getTextMessageType();
    }

    /**
     * Retrieves the target ID indicating the specific recipient or channel for the message.
     *
     * @return The target ID of the message.
     */
    public int getTargetID() {
        return toIntI("target");
    }

    /**
     * Generates a string representation of the TextMessageEvent object using StringBuilder.
     *
     * @return A string describing the TextMessageEvent object, including ClientID, ClientName, TargetMode, Message, and TargetID.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TextMessageEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientName=").append(getClientName())
          .append(",TargetMode=").append(getTextMessageType())
          .append(",Message=").append(getMessage())
          .append(",TargetID=").append(getTargetID())
          .append("]");
        return sb.toString();
    }
}

