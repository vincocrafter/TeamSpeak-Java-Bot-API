package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a client leave event in the TeamSpeak API.
 * This event is triggered whenever a client leaves the server.
 */
public class ClientLeaveEvent extends BaseEvent {

    /**
     * Constructs a new {@code ClientLeaveEvent} with the specified information and server query.
     *
     * @param infos       an array of strings containing information about the event
     * @param serverQuery an instance of {@link Ts3ServerQuery} associated with this event
     */
    public ClientLeaveEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Note: Avoid using getClientInfo() or getClient() as the client is already offline,
     * resulting in null values.
     * 
     * @see net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI#getClientInfo(int) 
     * @see net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI#getClient(int)
     */

    public int getClientID() {
        return toIntI("clid");
    }


    /**
     * Gets the ID of the channel from which the client left.
     *
     * @return the channel ID
     */
    public int getClientChannel() {
        return toIntI("cfid");
    }

    /**
     * Gets the reason ID for the client leave event.
     *
     * @return the reason ID, or -1 if there is no reason ID
     */
    public int getReasonID() {
        return toIntI("reasonid");
    }

    /**
     * Checks if the client has been kicked.
     *
     * @return {@code true} if the client has been kicked, otherwise {@code false}
     */
    public boolean hasBeenKicked() {
        return getReasonID() == 5;
    }

    /**
     * Checks if the client has been banned.
     *
     * @return {@code true} if the client has been banned, otherwise {@code false}
     */
    public boolean hasBeenBanned() {
        return getReasonID() == 6;
    }

    /**
     * Checks if the client lost connection.
     *
     * @return {@code true} if the client lost connection, otherwise {@code false}
     */
    public boolean hasClientLostConnection() {
        return getReasonID() == 3;
    }

    /**
     * Gets the ID of the invoker who triggered the client leave event.
     *
     * @return the invoker ID, or -2 if there is no invoker ID
     */
    public int getInvokerID() {
        return toIntI("invokerid");
    }

    /**
     * Gets the name of the invoker who triggered the client leave event.
     *
     * @return the invoker name
     */
    public String getInvokerName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Gets the unique identifier (UUID) of the invoker who triggered the client leave event.
     *
     * @return the invoker UUID
     */
    public String getInvokerUUID() {
        return Formatter.toNormalFormat(get("invokeruid"));
    }

    /**
     * Gets the reason message associated with the client leave event.
     *
     * @return the reason message
     */
    public String getReasonMsg() {
        return get("reasonmsg");
    }

    /**
     * Gets the ban time associated with the client leave event.
     *
     * @return the ban time, or -2 if there is no ban time
     */
    public int getBanTime() {
        return toIntI("bantime");
    }

    /**
     * Returns a string representation of the {@code ClientLeaveEvent}.
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClientLeaveEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientChannel=").append(getClientChannel())
          .append(",ReasonID=").append(getReasonID())
          .append("]");
        return sb.toString();
    }
}
