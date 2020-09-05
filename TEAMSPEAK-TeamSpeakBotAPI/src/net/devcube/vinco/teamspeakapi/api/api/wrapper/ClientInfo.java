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
		return this.infos;
	}

	public String getClientName() {
		String s = this.get(3);
		s = s.replace("\\", " ");
		s = s.replace("\\s", " ");
		s = s.replace(" s", " ");
		return s;
	}

	public String getNickName() {
		return this.getClientName();
	}

	public String getClientUUID() {
		return this.get(2);
	}

	public int getClientID() {
		return this.id;
	}

	public int getClientDatabaseID() {
		return Integer.parseInt(this.get(17));
	}

	public String getPlatform() {
		return this.get(5);
	}

	public int getChannelID() {
		return Integer.parseInt(this.get(0));
	}

	public boolean isInputMuted() {
		return this.get(6).equalsIgnoreCase("1");
	}

	public boolean isOutputMuted() {
		return this.get(7).equalsIgnoreCase("1");
	}

	public boolean isRecording() {
		return this.get(13).equalsIgnoreCase("1");
	}

	public ArrayList<Integer> getServerGroups() {
		ArrayList<Integer> arraylist = new ArrayList<Integer>();
		if (this.infos[19].split("=")[1].contains(",")) {
			String[] astring = this.infos[19].split("=")[1].split(",");

			for (String s : astring) {
				arraylist.add(Integer.valueOf(Integer.parseInt(s)));
			}
		} else {
			arraylist.add(Integer.valueOf(Integer.parseInt(this.infos[19].split("=")[1])));
		}

		return arraylist;
	}

	public long getCreated() {
		return this.toLong(this.get(20));
	}

	private int getClientTypeInt() {
		return this.toInt(this.get(25));
	}

	public boolean isServerQueryClient() {
		return this.getClientType() == ClientType.QUERY;
	}

	public boolean isUser() {
		return getClientType() == ClientType.CLIENT;
	}

	public boolean hasClientDescription() {
		return !this.getClientDescription().equalsIgnoreCase("");
	}

	public String getClientDescription() {
		String s = this.get(24);
		s = s.replace("\\", " ");
		s = s.replace("\\s", " ");
		s = s.replace(" s", " ");
		return s;
	}

	public boolean isInServerGroup(int groupid) {
		return this.getServerGroups().contains(Integer.valueOf(groupid));
	}

	public long getLastConncetion() {
		return Long.parseLong(this.get(21));
	}

	public ClientType getClientType() {
		return this.getClientTypeInt() == 0 ? ClientType.CLIENT : ClientType.QUERY;
	}

	public int getChannelGroupID() {
		return Integer.parseInt(this.get(18));
	}

	public String getIP() {
		return this.get(59);
	}

	public String getAwayMessage() {
		return this.get(24);
	}

	public String getCountry() {
		return this.get(42);
	}

	public int getTalkPower() {
		return Integer.parseInt(this.get(27));
	}

	public String getVersion() {
		return this.get(4);
	}

	public boolean isAway() {
		return this.toBol(this.toInt(this.get(23)));
	}

	public int getAllTimeConnections() {
		return this.toInt(this.get(22));
	}

	public int getTotalConncetions() {
		return this.getAllTimeConnections();
	}

	public boolean hasMyTeamspeakID() {
		return !this.getMyTeamspeakID().equalsIgnoreCase("");
	}

	public String getMyTeamspeakID() {
		return this.get(45);
	}

	public int getNeededServerQueryViewPower() {
		return this.toInt(this.get(38));
	}

	public boolean isTalker() {
		return this.toBol(this.toInt(this.get(31)));
	}

	public boolean isPrioritySpeaker() {
		return this.toBol(this.toInt(this.get(36)));
	}

	public boolean isChannelCommander() {
		return this.toBol(this.toInt(this.get(41)));
	}

	public boolean isTalkPowerRequesting() {
		return this.toBol(this.toInt(this.get(28)));
	}

	public String getTalkPowerRequestMsg() {
		return this.get(29);
	}

	public boolean hasPhoneticName() {
		return !this.getPhoneticName().equalsIgnoreCase("");
	}

	public String getPhoneticName() {
		return this.get(37);
	}

	public int getDefaultChannelID() {
		String s = this.get(11).replace("\\/", "");
		return !s.equalsIgnoreCase("") && !s.isEmpty() ? this.toInt(s) : -1;
	}
}
