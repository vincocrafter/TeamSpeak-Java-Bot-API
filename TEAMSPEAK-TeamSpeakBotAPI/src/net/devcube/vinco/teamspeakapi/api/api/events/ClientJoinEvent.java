package net.devcube.vinco.teamspeakapi.api.api.events;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

/**
 * Represents a client join event in the TeamSpeak server.
 * Extends BaseEvent to inherit basic event information.
 */
public class ClientJoinEvent extends BaseEvent {

    /**
     * Constructs a ClientJoinEvent with the provided information and server query.
     *
     * @param infos       An array of information related to the event.
     * @param serverQuery The TeamSpeak server query instance.
     */
    public ClientJoinEvent(String[] infos, Ts3ServerQuery serverQuery) {
        super(infos, serverQuery);
    }

    /**
     * Retrieves the ID of the client who joined.
     *
     * @return The client ID.	
     */
    public int getClientID() {
        return toIntI("clid");
    }

    /**
     * Retrieves the name of the client who joined.
     *
     * @return The client name.
     */
    public String getClientName() {
        return Formatter.toNormalFormat(get("client_nickname"));
    }

    /**
     * Retrieves the unique identifier (UUID) of the client who joined.
     *
     * @return The client UUID.
     */
    public String getClientUUID() {
        return get("client_unique_identifier");
    }

    /**
     * Retrieves the ID of the target channel the client joined.
     *
     * @return The target channel ID.
     */
    public int getTargetChannelID() {
        return toIntI("ctid");
    }

    /**
     * Retrieves the ID of the source channel the client joined from.
     *
     * @return The source channel ID.
     */
    public int getSourceChannelID() {
        return toIntI("cfid");
    }

    /**
     * Retrieves the reason ID for the client join event.
     *
     * @return The reason ID.
     */
    public int getReasonID() {
        return toIntI("reasonid");
    }

    /**
     * Checks if the client's input is muted.
     *
     * @return True if the client's input is muted, false otherwise.
     */
    public boolean isClientInputMuted() {
        return toBolI("client_input_muted");
    }

    /**
     * Checks if the client's output is muted.
     *
     * @return True if the client's output is muted, false otherwise.
     */
    public boolean isClientOutputMuted() {
        return toBolI("client_output_muted");
    }

    /**
     * Checks if the client's output only is muted.
     *
     * @return True if the client's output only is muted, false otherwise.
     */
    public boolean isClientOutputOnlyMuted() {
        return toBolI("client_outputonly_muted");
    }

    /**
     * Checks if the client's input is hardware-based.
     *
     * @return True if the client's input is hardware-based, false otherwise.
     */
    public boolean isClientInputHardware() {
        return toBolI("client_input_hardware");
    }

    /**
     * Checks if the client's output is hardware-based.
     *
     * @return True if the client's output is hardware-based, false otherwise.
     */
    public boolean isClientOutputHardware() {
        return toBolI("client_output_hardware");
    }

    /**
     * Checks if the client is recording.
     *
     * @return True if the client is recording, false otherwise.
     */
    public boolean isClientRecording() {
        return toBolI("client_is_recording");
    }

    /**
     * Retrieves the database ID of the client.
     *
     * @return The client's database ID.
     */
    public int getClientDataBaseID() {
        return toIntI("client_database_id");
    }

    /**
     * Retrieves the channel group ID of the client.
     *
     * @return The client's channel group ID.
     */
    public int getClientChannelGroupID() {
        return toIntI("client_channel_group_id");
    }

    /**
     * Retrieves the inherited channel group ID of the client.
     *
     * @return The client's inherited channel group ID.
     */
    public int getClientChannelGroupInheritedChannelID() {
        return toIntI("client_channel_group_inherited_channel_id");
    }

    /**
     * Retrieves the list of server groups that the client belongs to.
     *
     * @return An ArrayList containing the IDs of server groups.
     */
    public List<Integer> getServerGroups() {
		String[] split = get("client_servergroups").split(",");
		List<Integer> arraylist = new ArrayList<>(split.length);
		for (String ids : split) {
			arraylist.add(toInt(ids));
		}
		return arraylist;
	}

    /**
     * Checks if the client is marked as away.
     *
     * @return True if the client is away, false otherwise.
     */
    public boolean isClientAway() {
        return toBolI("client_away");
    }

    /**
     * Retrieves the away message of the client.
     *
     * @return The away message.
     */
    public String getAwayMessage() {
        return Formatter.toNormalFormat(get("client_away_message"));
    }

    /**
     * Retrieves the integer value of the client's type.
     *
     * @return The integer value representing the client's type.
     */
    private int getClientTypeInt() {
        return toIntI("client_type");
    }

    /**
     * Retrieves the client's type (either Client or Query).
     *
     * @return The ClientType enumeration value representing the client's type.
     * @see ClientType
     */
    public ClientType getClientType() {
        return getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
    }

    /**
     * Checks if the client is a server query client.
     *
     * @return True if the client is a server query client, false otherwise.
     */
    public boolean isServerQueryClient() {
        return getClientType() == ClientType.QUERY;
    }

    /**
     * Checks if the client is a regular user (not a server query client).
     *
     * @return True if the client is a regular user, false otherwise.
     */
    public boolean isUser() {
        return getClientType() == ClientType.CLIENT;
    }

    /**
     * Retrieves the talk power of the client.
     *
     * @return The talk power value.
     */
    public int getClientTalkPower() {
        return toIntI("client_talk_power");
    }

    /**
     * Checks if the client is requesting talk power.
     *
     * @return True if the client is requesting talk power, false otherwise.
     */
    public boolean isClientTalkPowerRequesting() {
        return toBolI("client_talk_request");
    }

    /**
     * Retrieves the message related to the client's talk power request.
     *
     * @return The talk power request message.
     */
    public String getClientTalkPowerRequestMsg() {
        return get("client_talk_request_msg");
    }

    /**
     * Retrieves the description of the client.
     *
     * @return The client's description.
     */
    public String getClientDescription() {
        return Formatter.toNormalFormat(get("client_description"));
    }

    /**
     * Checks if the client is a talker.
     *
     * @return True if the client is a talker, false otherwise.
     */
    public boolean isClientTalker() {
        return toBolI("client_is_talker");
    }

    /**
     * Checks if the client is a priority speaker.
     *
     * @return True if the client is a priority speaker, false otherwise.
     */
    public boolean isClientPrioritySpeaker() {
        return toBolI("client_is_priority_speaker");
    }

    /**
     * Retrieves the phonetic name of the client.
     *
     * @return The client's phonetic name.
     */
    public String getClientPhoneticName() {
        return Formatter.toNormalFormat(get("client_nickname_phonetic"));
    }

    /**
     * Retrieves the server query view power needed by the client.
     *
     * @return The server query view power needed.
     */
    public int getClientNeededServerQueryViewPower() {
        return toIntI("client_needed_serverquery_view_power");
    }

    /**
     * Retrieves the icon ID of the client.
     *
     * @return The client's icon ID.
     */
    public int getClientIconID() {
        return toIntI("client_icon_id");
    }

    /**
     * Checks if the client is a channel commander.
     *
     * @return True if the client is a channel commander, false otherwise.
     */
    public boolean isClientChannelCommander() {
        return toBolI("client_is_channel_commander");
    }

    /**
     * Retrieves the country of the client.
     *
     * @return The client's country.
     */
    public String getCountry() {
        return get("client_country");
    }

    /**
     * Retrieves the TeamSpeak ID of the client.
     *
     * @return The client's TeamSpeak ID.
     */
    public String getClientMyTeamspeakID() {
        return get("client_myteamspeak_id");
    }

    /**
     * Retrieves the badges of the client.
     *
     * @return The client's badges.
     */
    public String getClientBadges() {
        return get("client_badges");
    }

    /**
     * Retrieves the integrations of the client.
     *
     * @return The client's integrations.
     */
    public String getClientIntegrations() {
        return get("client_integrations");
    }   

    /**
     * Retrieves the avatar associated with the client in MyTeamSpeak.
     *
     * @return The URL or path to the client's MyTeamSpeak avatar.
     */
    public String getClientMyTeamSpeakAvatar() {
        return get("client_myteamspeak_avatar");
    }

    /**
     * Retrieves the avatar flag associated with the client.
     *
     * @return The URL or path to the client's avatar flag.
     */
    public String getClientAvatar() {
        return get("client_flag_avatar");
    }

    /**
     * Returns a string representation of the ClientJoinEvent object.
     *
     * @return A string representation containing ClientID, ClientName, and ClientUUID.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ClientJoinEvent");
        sb.append("[ClientID=").append(this.getClientID())
          .append(",ClientName=").append(this.getClientName())
          .append(",ClientUUID=").append(this.getClientUUID())
          .append("]");
        return sb.toString();
    }
}
