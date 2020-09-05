package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.util.ArrayList;

import net.devcube.vinco.teamspeakapi.api.api.property.ClientType;

public class ClientInfo extends _DefaultInfo {

	int id;
	
	public ClientInfo(String[] infos, int id) {
		super(infos);
		this.id = id;
	}

	public String[] getClientInfos() {
		return infos;
	}

	public String getClientName() {
		String s = get(3);
		s = s.replace("\\", " ");
		s = s.replace("\\s", " ");
		s = s.replace(" s", " ");
		return s;
	}

	public String getNickName() {
		return getClientName();
	}

	public String getClientUUID() {
		return get(2);
	}

	public int getClientID() {
		return id;
	}

	public int getClientDatabaseID() {
		return Integer.parseInt(get(17));
	}

	public String getPlatform() {
		return get(5);
	}

	public int getChannelID() {
		return Integer.parseInt(get(0));
	}

	public boolean isInputMuted() {
		return get(6).equalsIgnoreCase("1");
	}

	public boolean isOutputMuted() {
		return get(7).equalsIgnoreCase("1");
	}

	public boolean isRecording() {
		return get(13).equalsIgnoreCase("1");
	}

	public ArrayList<Integer> getServerGroups() {
		ArrayList<Integer> arraylist = new ArrayList<Integer>();
		if (infos[19].split("=")[1].contains(",")) {
			String[] astring = infos[19].split("=")[1].split(",");

			for (String s : astring) {
				arraylist.add(Integer.valueOf(Integer.parseInt(s)));
			}
		} else {
			arraylist.add(Integer.valueOf(Integer.parseInt(infos[19].split("=")[1])));
		}

		return arraylist;
	}

	public long getCreated() {
		return toLong(get(20));
	}

	private int getClientTypeInt() {
		return toInt(get(25));
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
		String s = get(24);
		s = s.replace("\\", " ");
		s = s.replace("\\s", " ");
		s = s.replace(" s", " ");
		return s;
	}

	public boolean isInServerGroup(int groupid) {
		return getServerGroups().contains(Integer.valueOf(groupid));
	}

	public long getLastConncetion() {
		return Long.parseLong(get(21));
	}

	public ClientType getClientType() {
		return getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
	}

	public int getChannelGroupID() {
		return Integer.parseInt(get(18));
	}

	public String getIP() {
		return get(59);
	}

	public String getAwayMessage() {
		return get(24);
	}

	public String getCountry() {
		return get(42);
	}

	public int getTalkPower() {
		return Integer.parseInt(get(27));
	}

	public String getVersion() {
		return get(4);
	}

	public boolean isAway() {
		return toBol(toInt(get(23)));
	}

	public int getAllTimeConnections() {
		return toInt(get(22));
	}

	public int getTotalConncetions() {
		return getAllTimeConnections();
	}

	public boolean hasMyTeamspeakID() {
		return !getMyTeamspeakID().equalsIgnoreCase("");
	}

	public String getMyTeamspeakID() {
		return get(45);
	}

	public int getNeededServerQueryViewPower() {
		return toInt(get(38));
	}

	public boolean isTalker() {
		return toBol(toInt(get(31)));
	}

	public boolean isPrioritySpeaker() {
		return toBol(toInt(get(36)));
	}

	public boolean isChannelCommander() {
		return toBol(toInt(get(41)));
	}

	public boolean isTalkPowerRequesting() {
		return toBol(toInt(get(28)));
	}

	public String getTalkPowerRequestMsg() {
		return get(29);
	}

	public boolean hasPhoneticName() {
		return !getPhoneticName().equalsIgnoreCase("");
	}

	public String getPhoneticName() {
		return get(37);
	}

	public int getDefaultChannelID() {
		String s = get(11).replace("\\/", "");
		return !s.equalsIgnoreCase("") && !s.isEmpty() ? toInt(s) : -1;
	}
}
