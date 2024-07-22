package net.devcube.vinco.teamspeakapi.api.api.events;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents an event where one or more clients have moved channels.
 */
public class ClientMoveEvent extends BaseEvent {

    /**
     * Constructs a ClientMoveEvent object with the provided information and server query instance.
     *
     * @param infos       An array of strings containing event information.
     * @param serverQuery The Ts3ServerQuery instance associated with the event.
     */
    public ClientMoveEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Retrieves the IDs of the clients involved in the move event.
     *
     * @return A list of client IDs affected by the move event.
     */
    public List<Integer> getClientIDs() {
        String clids = get("clid").replace("clid=", "");
        String[] split = clids.split("\\|");
        List<Integer> res = new ArrayList<>(split.length);
        if (split.length == 1) {
            res.add(toInt(clids));
            return res;
        }

        for (String ids : split) {
            res.add(toInt(ids));
        }

        return res;
    }

    /**
     * Retrieves the ID of the primary client involved in the move event.
     *
     * @return The ID of the primary client that was moved.
     */
    public int getClientID() {
        return getClientIDs().get(0);
    }

    /**
     * Retrieves the reason ID associated with the move event.
     *
     * @return The reason ID indicating the cause of the client move.
     */
    public int getReasonID() {
        return toIntI("reasonid");
    }

    /**
     * Retrieves the target channel ID to which the clients were moved.
     *
     * @return The ID of the channel where the clients were moved.
     */
    public int getTargetChannelID() {
        return toIntI("ctid");
    }

    /**
     * Checks if the clients were moved to the specified target channel ID.
     *
     * @param id The channel ID to check against the target channel ID.
     * @return true if the clients were moved to the specified channel ID, false otherwise.
     */
    public boolean isTargetChannelID(int id) {
        return getTargetChannelID() == id;
    }

    /**
     * Checks if the move event was initiated by a client move action.
     *
     * @return true if the move event was initiated by a client move action, false otherwise.
     */
    public boolean hasBeenMoved() {
        return getReasonID() == 1;
    }

    /**
     * Retrieves the ID of the client who initiated the move event.
     *
     * @return The ID of the client who initiated the move, or -1 if not applicable.
     */
    public int getInvokerID() {
        if (hasBeenMoved())
            return toIntI("invokerid");
        return -1;
    }

    /**
     * Retrieves the name of the client who initiated the move event.
     *
     * @return The name of the client who initiated the move.
     */
    public String getInvokerName() {
        return Formatter.toNormalFormat(get("invokername"));
    }

    /**
     * Retrieves the unique identifier of the client who initiated the move event.
     *
     * @return The UUID of the client who initiated the move.
     */
    public String getInvokerUUID() {
        return Formatter.toNormalFormat(get("invokeruid"));
    }

    /**
     * Returns a string representation of the ClientMoveEvent object.
     *
     * @return A string representation containing ClientIDs, ReasonID, and TargetChannelID.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClientMoveEvent");
        sb.append("[ClientIDs=").append(getClientIDs())
                .append(",ReasonID=").append(getReasonID())
                .append(",TargetChannelID=").append(getTargetChannelID())
                .append("]");
        return sb.toString();
    }
}

