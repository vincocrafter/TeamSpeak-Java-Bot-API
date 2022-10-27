package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.util.ArrayList;

import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ClientInfo extends DefaultInfo {


	public ClientInfo(String[] infos) {
		super(infos);
	}

	public String[] getClientInfos() {
		return infos;
	}

	public String getClientName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}

	public String getNickName() {
		return getClientName();
	}

	public String getClientUUID() {
		return get("client_unique_identifier");
	}

	public int getClientID() {
		return toInt(get("cid"));
	}

	public int getClientDatabaseID() {
		return toInt("client_database_id");
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

	public ArrayList<Integer> getServerGroups() {
		ArrayList<Integer> arraylist = new ArrayList<Integer>();
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

	public long getCreated() {
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

	public long getLastConncetion() {
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

	public int getTotalConncetions() {
		return getAllTimeConnections();
	}

	public boolean hasMyTeamspeakID() {
		return !getMyTeamspeakID().equalsIgnoreCase("");
	}

	public String getMyTeamspeakID() {
		return get("client_myteamspeak_id");
	}

	public int getNeededServerQueryViewPower() {
		return toInt(get("client_needed_serverquery_view_power"));
	}

	public boolean isTalker() {
		return toBol(toInt(get("client_is_talker")));
	}

	public boolean isPrioritySpeaker() {
		return toBol(toInt(get("client_is_priority_speaker")));
	}

	public boolean isChannelCommander() {
		return toBol(toInt(get("client_is_channel_commander")));
	}

	public boolean isTalkPowerRequesting() {
		return toBol(toInt(get("client_talk_request")));
	}

	public String getTalkPowerRequestMsg() {
		return get("client_talk_request_msg");
	}

	public boolean hasPhoneticName() {
		return !getPhoneticName().equalsIgnoreCase("");
	}

	public String getPhoneticName() {
		return get("client_nickname_phonetic");
	}

	public int getDefaultChannelID() {
		String s = get("client_default_channel").replace("\\/", "");
		return !s.equalsIgnoreCase("") && !s.isEmpty() ? toInt(s) : -1;
	}
}
