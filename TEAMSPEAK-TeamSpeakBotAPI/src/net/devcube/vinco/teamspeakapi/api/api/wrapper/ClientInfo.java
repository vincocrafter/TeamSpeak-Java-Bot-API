package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ClientInfo extends DefaultInfo {

	public ClientInfo(String[] infos) {
		super(infos);
	}
	
	public ClientInfo(String infos) {
		super(infos);
	}

	public String getName() {
		return getNickName();
	}

	public String getNickName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}

	public String getUUID() {
		return getClientUUID();
	}

	public String getClientUUID() {
		return get("client_unique_identifier").concat("=");
	}

	public int getID() {
		return getClientID();
	}

	public int getClientID() {
		return toInt(get("clid"));
	}

	public int getClientDataBaseID() {
		return toInt(get("client_database_id"));
	}

	public String getPlatform() {
		return get("client_platform");
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}

	public boolean isInputMuted() {
		return get("client_input_muted").equalsIgnoreCase("1");
	}

	public boolean isOutputMuted() {
		return get("client_output_muted").equalsIgnoreCase("1");
	}

	public boolean isRecording() {
		return get("client_is_recording").equalsIgnoreCase("1");
	}

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

	public long getCreatingTime() {
		return toLong(get("client_created"));
	}

	private int getClientTypeInt() {
		return toInt(get("client_type"));
	}

	public boolean isServerQueryClient() {
		return getClientType() == ClientType.QUERY;
	}

	public boolean isUser() {
		return getClientType() == ClientType.CLIENT;
	}

	public boolean hasClientDescription() {
		return !getClientDescription().equalsIgnoreCase("");
	}

	public String getClientDescription() {
		return Formatter.toNormalFormat(get("client_description"));
	}

	public boolean isInServerGroup(int groupid) {
		return getServerGroups().contains(groupid);
	}

	public long getLastConnection() {
		return toLong(get("client_lastconnected"));
	}

	public ClientType getClientType() {
		return getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
	}

	public int getChannelGroupID() {
		return toInt(get("client_channel_group_id"));
	}

	public String getIP() {
		return get(get("connection_client_ip"));
	}

	public String getAwayMessage() {
		return get("lient_away_message");
	}

	public String getCountry() {
		return get("client_country=");
	}

	public int getTalkPower() {
		return toInt(get("client_talk_power"));
	}

	public String getVersion() {
		return Formatter.toNormalFormat(get("client_version"));
	}

	public boolean isAway() {
		return toBol(toInt(get("client_away")));
	}

	public int getAllTimeConnections() {
		return toInt(get("client_totalconnections"));
	}

	public int getTotalConnections() {
		return getAllTimeConnections();
	}

	public boolean hasMyTeamspeakID() {
		return !getMyTeamspeakID().isEmpty();
	}

	public String getMyTeamspeakID() {
		return get("client_myteamspeak_id");
	}

	public int getNeededServerQueryViewPower() {
		return toInt(get("client_needed_serverquery_view_power"));
	}

	public boolean isTalker() {
		return toBol(get("client_is_talker"));
	}
	
	public boolean canTalk() {
		return isTalker();
	}

	public boolean isPrioritySpeaker() {
		return toBol(get("client_is_priority_speaker"));
	}

	public boolean isChannelCommander() {
		return toBol(get("client_is_channel_commander"));
	}

	public boolean isTalkPowerRequesting() {
		return toBol(get("client_talk_request"));
	}

	public String getTalkPowerRequestMsg() {
		return get("client_talk_request_msg");
	}

	public boolean hasPhoneticName() {
		return !getPhoneticName().isEmpty();
	}

	public String getPhoneticName() {
		return get("client_nickname_phonetic");
	}

	public int getDefaultChannelID() {
		String s = Formatter.toNormalFormat(get("client_default_channel"));
		return s.isEmpty() ? 0 : toInt(s);
	}

	@Override
	public String toString() {
		return "Client[Name=" + getName() + ",ID=" + getID() + ",UUID=" + getUUID() + "]";
	}
}
