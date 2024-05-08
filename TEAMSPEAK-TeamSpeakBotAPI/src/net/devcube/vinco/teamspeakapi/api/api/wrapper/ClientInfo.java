package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
	
/**
 * This class represents information about a client in TeamSpeak.
 * It extends the DefaultInfo class, which provides basic functionality for storing and accessing information.
 */

public class ClientInfo extends DefaultInfo {
	
	/**
     * Constructs a new ClientInfo object with the given array of client information.
     * 
     * @param infos An array containing the client information.
     */
	
	public ClientInfo(String[] infos) {
		super(infos);
	}
	
	/**
     * Constructs a new ClientInfo object with the given client information string.
     * 
     * @param infos A string containing the client information.
     */

	public ClientInfo(String infos) {
		super(infos);
	}
	
	/**
     * Gets the name of the client.
     * 
     * @return The name of the client.
     */
	
	public String getName() {
		return getNickName();
	}
	
	/**
     * Gets the nickname of the client.
     * 
     * @return The nickname of the client.
     */
	
	public String getNickName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}
	
	/**
     * Gets the UUID of the client.
     * 
     * @return The UUID of the client.
     */

	public String getUUID() {
		return getClientUUID();
	}
	
	/**
     * Gets the UUID of the client.
     * 
     * @return The UUID of the client.
     */
	
	public String getClientUUID() {
		return get("client_unique_identifier");
	}
	
	/**
     * Gets the unique identifier of the client.
     * 
     * @return The unique identifier of the client.
     */
	
	public String getUniqueIdentifier() {
		return getUUID();
	}
	
	/**
     * Gets the ID of the client.
     * 
     * @return The ID of the client.
     */

	public int getID() {
		return getClientID();
	}
	
	/**
     * Gets the ID of the client.
     * 
     * @return The ID of the client.
     */
	
	public int getClientID() {
		return toInt(get("clid"));
	}
	
	/**
     * Returns the unique ID of the client.
     * @return The client ID.
     */
	
	public int getId() {
		return getClientID();
	}
	
	/**
     * Returns the client ID.
     * @return The client ID.
     */
	
	public int getClientId() {
		return getClientID();
	}
	
	/**
     * Returns the database ID of the client.
     * @return The database ID of the client.
     */

	public int getClientDataBaseID() {
		return toInt(get("client_database_id"));
	}
	
	/**
     * Returns the platform of the client.
     * @return The platform of the client.
     */
	
	public String getPlatform() {
		return get("client_platform");
	}
	
	/**
     * Returns the ID of the channel where the client is located.
     * @return The channel ID.
     */
	
	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	/**
     * Checks if the client has input muted.
     * @return true if input is muted, false otherwise.
     */
	
	public boolean isInputMuted() {
		return get("client_input_muted").equalsIgnoreCase("1");
	}
	
	/**
	 * Checks if the client has output muted.
	 * @return true if the client's output is muted, false otherwise.
	 */
	
	public boolean isOutputMuted() {
		return get("client_output_muted").equalsIgnoreCase("1");
	}
	

	/**
	 * Checks if the client is currently recording.
	 * @return true if the client is recording, false otherwise.
	 */
	
	public boolean isRecording() {
		return get("client_is_recording").equalsIgnoreCase("1");
	}
	
	/**
	 * Retrieves the list of server groups the client belongs to.
	 * @return A list of server group IDs associated with the client.
	 */
	
	public List<Integer> getServerGroups() {
		List<Integer> arraylist = new ArrayList<Integer>();
		if (get("client_servergroups").contains(",")) {
			String[] astring = get("client_servergroups").split(",");
			for (String s : astring) {
				arraylist.add(toInt(s));
			}
		} else {
			arraylist.add(toInt(get("client_servergroups")));
		}

		return arraylist;
	}
	
	/**
	 * Retrieves the creation time of the client.
	 * @return The creation time of the client in milliseconds since the epoch.
	 */
	
	public long getCreatingTime() {
		return toLong(get("client_created"));
	}
	
	/**
	 * Retrieves the client type as an integer.
	 * @return The integer representation of the client type.
	 */
	
	private int getClientTypeInt() {
		return toInt(get("client_type"));
	}
	
	/**
	 * Checks if the client is a server query client.
	 * @return true if the client type is server query, false otherwise.
	 */

	public boolean isServerQueryClient() {
		return getClientType() == ClientType.QUERY;
	}
	
	/**
	 * Checks if the client is a regular user.
	 * @return true if the client type is user, false otherwise.
	 */
	
	public boolean isUser() {
		return getClientType() == ClientType.CLIENT;
	}
	
	/**
	 * Checks if the client has a description.
	 * @return true if the client has a description, false otherwise.
	 */
	
	public boolean hasClientDescription() {
		return !getClientDescription().isEmpty();
	}
	
	/**
	 * Retrieves the description of the client.
	 * @return The description of the client.
	 */
	
	public String getClientDescription() {
		return Formatter.toNormalFormat(get("client_description"));
	}

	/**
	 * Checks if the client is in the specified server group.
	 * @param groupid The ID of the server group to check.
	 * @return true if the client is in the specified server group, false otherwise.
	 */
	
	public boolean isInServerGroup(int groupid) {
		return getServerGroups().contains(groupid);
	}
	
	/**
	 * Retrieves the timestamp of the client's last connection.
	 * @return The timestamp of the client's last connection in milliseconds since the epoch.
	 */
	
	public long getLastConnection() {
		return toLong(get("client_lastconnected"));
	}
	

	/**
	 * Retrieves the type of the client.
	 * @return The type of the client.
	 */
	
	public ClientType getClientType() {
		return getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
	}
	
	/**
	 * Retrieves the ID of the channel group the client belongs to.
	 * @return The ID of the channel group.
	 */
	
	public int getChannelGroupID() {
		return toInt(get("client_channel_group_id"));
	}
	
	/**
	 * Retrieves the IP address of the client's connection.
	 * @return The IP address of the client's connection.
	 */
	
	public String getIP() {
		return get(get("connection_client_ip"));
	}
	
	/**
	 * Retrieves the away message of the client.
	 * @return The away message of the client.
	 */
	
	public String getAwayMessage() {
		return get("lient_away_message");
	}
	
	/**
	 * Retrieves the country of the client.
	 * @return The country of the client.
	 */
	
	public String getCountry() {
		return get("client_country");
	}
	
	/**
	 * Retrieves the talk power of the client.
	 * @return The talk power of the client.
	 */
	
	public int getTalkPower() {
		return toInt(get("client_talk_power"));
	}
	
	/**
	 * Retrieves the version of the client.
	 * @return The version of the client.
	 */
	
	public String getVersion() {
		return Formatter.toNormalFormat(get("client_version"));
	}
	
	/**
	 * Checks if the client is away.
	 * @return true if the client is away, false otherwise.
	 */
	
	public boolean isAway() {
		return toBol(toInt(get("client_away")));
	}
	
	/**
	 * Retrieves the total number of connections made by the client.
	 * @return The total number of connections made by the client.
	 */
	
	public int getAllTimeConnections() {
		return toInt(get("client_totalconnections"));
	}
	
	/**
	 * Alias for getAllTimeConnections().
	 * @return The total number of connections made by the client.
	 */
	
	public int getTotalConnections() {
		return getAllTimeConnections();
	}
	
	/**
	 * Checks if the client has a TeamSpeak ID.
	 * @return true if the client has a TeamSpeak ID, false otherwise.
	 */
	
	public boolean hasMyTeamspeakID() {
		return !getMyTeamspeakID().isEmpty();
	}
	
	/**
	 * Retrieves the TeamSpeak ID of the client.
	 * @return The TeamSpeak ID of the client.
	 */
	
	public String getMyTeamspeakID() {
		return get("client_myteamspeak_id");
	}
	
	/**
	 * Retrieves the required server query view power of the client.
	 * @return The required server query view power of the client.
	 */
	
	public int getNeededServerQueryViewPower() {
		return toInt(get("client_needed_serverquery_view_power"));
	}
	
	/**
	 * Checks if the client is a talker.
	 * @return true if the client is a talker, false otherwise.
	 */
	
	public boolean isTalker() {
		return toBol(get("client_is_talker"));
	}
	
	/**
	 * Alias for isTalker().
	 * @return true if the client is a talker, false otherwise.
	 */
	
	public boolean canTalk() {
		return isTalker();
	}
	
	/**
	 * Checks if the client is a priority speaker.
	 * @return true if the client is a priority speaker, false otherwise.
	 */
	
	public boolean isPrioritySpeaker() {
		return toBol(get("client_is_priority_speaker"));
	}
	
	/**
	 * Checks if the client is a channel commander.
	 * @return true if the client is a channel commander, false otherwise.
	 */
	
	public boolean isChannelCommander() {
		return toBol(get("client_is_channel_commander"));
	}
	
	/**
	 * Checks if the client is requesting talk power.
	 * @return true if the client is requesting talk power, false otherwise.
	 */
	
	public boolean isTalkPowerRequesting() {
		return toBol(get("client_talk_request"));
	}
	
	/**
	 * Retrieves the talk power request message of the client.
	 * @return The talk power request message of the client.
	 */
	
	public String getTalkPowerRequestMsg() {
		return get("client_talk_request_msg");
	}
	

	/**
	 * Checks if the client has a phonetic name.
	 * @return true if the client has a phonetic name, false otherwise.
	 */
	
	public boolean hasPhoneticName() {
		return !getPhoneticName().isEmpty();
	}
	
	/**
	 * Retrieves the phonetic name of the client.
	 * @return The phonetic name of the client.
	 */
	
	public String getPhoneticName() {
		return get("client_nickname_phonetic");
	}
	
	/**
	 * Retrieves the default channel ID of the client.
	 * @return The default channel ID of the client. Returns 0 if the default channel is empty or cannot be parsed to an integer.
	 */
	
	public int getDefaultChannelID() {
		String s = Formatter.toNormalFormat(get("client_default_channel"));
		return s.isEmpty() ? 0 : toInt(s);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Client[");
		result.append("UUID=").append(getUUID());
		result.append(",ID=").append(getID());
		result.append(",Name=").append(getName());
		result.append("]");
		return result.toString();

	}
}
