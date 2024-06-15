package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents an event where a privilege key has been used by a client.
 */
public class PrivilegeKeyUsedEvent extends BaseEvent {

    /**
     * Constructs a PrivilegeKeyUsedEvent object with the provided information and server query instance.
     *
     * @param infos       An array of strings containing event information.
     * @param serverQuery The Ts3ServerQuery instance associated with the event.
     */
    public PrivilegeKeyUsedEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Retrieves the ID of the client who used the privilege key.
     *
     * @return The ID of the client who used the privilege key.
     */
    public int getClientID() {
        return toIntI("clid");
    }

    /**
     * Retrieves the database ID of the client who used the privilege key.
     *
     * @return The database ID of the client who used the privilege key.
     */
    public int getClientDataBaseID() {
        return toIntI("cldbid");
    }

    /**
     * Retrieves the unique identifier (UUID) of the client who used the privilege key.
     *
     * @return The UUID of the client who used the privilege key.
     */
    public String getClientUUID() {
        return get("cluid");
    }

    /**
     * Retrieves the token associated with the privilege key.
     *
     * @return The token used in the privilege key.
     */
    public String getToken() {
        return get("token");
    }

    /**
     * Retrieves the group ID associated with the privilege key.
     *
     * @return The group ID indicated in the privilege key token.
     */
    public int getGroupID() {
        return toIntI("token1");
    }

    /**
     * Retrieves the channel ID associated with the privilege key.
     *
     * @return The channel ID indicated in the privilege key token.
     */
    public int getChannelID() {
        return toIntI("token2");
    }

    /**
     * Retrieves the type of privilege key used.
     *
     * @return The type of privilege key used, either CHANNEL_GROUP or SERVER_GROUP.
     */
    public PrivilegeKeyType getKeyType() {
        return getChannelID() == 0 ? PrivilegeKeyType.CHANNEL_GROUP : PrivilegeKeyType.SERVER_GROUP;
    }

    /**
     * Generates a string representation of the PrivilegeKeyUsedEvent object using StringBuilder.
     *
     * @return A string describing the PrivilegeKeyUsedEvent object, including ClientID, ClientUUID, Token, GroupID, and ChannelID.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PrivilegeKeyUsedEvent");
        sb.append("[ClientID=").append(getClientID())
          .append(",ClientUUID=").append(getClientUUID())
          .append(",Token=").append(getToken())
          .append(",GroupID=").append(getGroupID())
          .append(",ChannelID=").append(getChannelID())
          .append("]");
        return sb.toString();
    }

}

