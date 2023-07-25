package net.devcube.vinco.teamspeakapi.api.api.events;

import java.util.ArrayList;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class ClientJoinEvent extends BaseEvent {

	
	public ClientJoinEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getClientID() {
		return toInt(get("clid"));
	}

	public String getClientName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}

	public String getClientUUID() {
		return get("client_unique_identifier");
	}
	
	public int getTargetChannelID() {
		return toInt(get("ctid"));
	}
	
	public boolean isClientInputMuted() {
		return toBol(toInt(get("client_input_muted")));
	}
	
	public boolean isClientOutputMuted() {
		return toBol(toInt(get("client_output_muted")));
	}
	
	public boolean isClientOutputOnlyMuted() {
		return toBol(toInt(get("client_outputonly_muted")));
	}
	
	public boolean isClientRecording() {
		return toBol(toInt(get("client_is_recording")));
	}
	
	public int getClChannelGroupID() {
		return toInt(get("client_channel_group"));
	}
	
	public ArrayList<Integer> getClientServerGroups() {
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
	
	public boolean isClientAway() {
		return toBol(toInt("client_away"));
	}
	
	public String getAwayMessage() {
		return get("client_away_message");
	}
	
	private int getClientTypeInt() {
		return toInt(get("client_type"));
	}

	public ClientType getClientType() {
		return getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
	}
	
	public boolean isServerQueryClient() {
		return getClientType() == ClientType.QUERY;
	}

	public boolean isUser() {
		return getClientType() == ClientType.CLIENT;
	}
	
	public int getClientTalkPower() {
		return toInt(get("client_talk_power"));
	}
	
	public boolean isClientTalkPowerRequesting() {
		return toBol(toInt(get("client_talk_request")));
	}

	public String getClientTalkPowerRequestMsg() {
		return get("client_talk_request_msg");
	}
	
	public String getClientDescription() {
		return get("client_description");
	}
	
	public boolean isClientTalker() {
		return toBol(toInt(get("client_is_talker")));
	}
	
	public boolean isClientPrioritySpeaker() {
		return toBol(toInt(get("client_is_priority_speaker")));
	}
	
	public String getClientPoneticName() {
		return get("client_nickname_phonetic ");
	}
	
	public int getClientNeededServerQueryViewPower() {
		return toInt(get("client_needed_serverquery_view_power"));
	}
	
	public int getClientIconID() {
		return toInt(get("client_icon_id"));
	}
	
	public boolean isClientChannelCommander() {
		return toBol(toInt(get("client_is_channel_commander")));
	}
	
	public String getCountry() {
		return get("client_country");
	}
	
	public String getClientMyTeamspeakID() {
		return get("client_myteamspeak_id");
	}
	
	public String toString() {
		return "ClientJoinEvent [ClientID=" + this.getClientID() + ",ClientName=" + this.getClientName() + ",ClientUUID=" + this.getClientUUID() + "]";
	}
}
