package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VirtualServerInfo extends _DefaultInfo {
	
	public VirtualServerInfo(String[] infos) {
		super(infos);
	}

	public String getServerUUID() {
		return get(0);
	}

	public String getServerName() {
		return get(1);
	}

	public String getWelcomeMessage() {
		return get(2);
	}

	public int getServerID() {
		return toInt(get(29));
	}

	public String getPlatform() {
		return get(3);
	}

	public String getVersion() {
		return get(4);
	}

	public String getPassword() {
		return get(5);
	}

	public int getMaxclients() {
		return toInt(get(5));
	}

	public int getChannels() {
		return toInt(get(8));
	}

	public long getCreatingTime() {
		return toLong(get(9));
	}

	public long getUptime() {
		return toLong(get(10));
	}

	public int getOnlineClientsSize() {
		return toInt(get(7));
	}

	public String getHostMessage() {
		return get(12).replace("\\s", " ");
	}

	public int getHostMessageMode() {
		return toInt(get(13));
	}

	public int getDefaultServerGroup() {
		return toInt(get(15));
	}

	public int getDefaultChannelGroup() {
		return toInt(get(16));
	}

	public boolean hasPassword() {
		return toBol(toInt(get(17)));
	}

	public int getDefaultChannelAdminGroup() {
		return toInt(get(18));
	}

	public String getHostBannerURL() {
		return get(21);
	}

	public int getComplainAutoBanCount() {
		return toInt(get(24));
	}

	public int getAutoBanTime() {
		return toInt(get(25));
	}

	public int getServerPort() {
		return toInt(get(45));
	}

	public boolean isLogClient() {
		return toBol(toInt(get(49)));
	}

	public boolean isLogQuery() {
		return toBol(toInt(get(50)));
	}

	public boolean isLogChannel() {
		return toBol(toInt(get(51)));
	}

	public boolean isLogPermissions() {
		return toBol(toInt(get(51)));
	}

	public boolean isLogServer() {
		return toBol(toInt(get(53)));
	}

	public boolean isLogFiletransfer() {
		return toBol(toInt(get(54)));
	}

	public int getReservedSlots() {
		return toInt(get(58));
	}

	public int getSecurityLevel() {
		return toInt(get(48));
	}

	public String getOnlineTime() {
		String s = "";
		long i = getUptime();
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;

		int j1;
		for (j1 = 0; i >= 60L; ++j) {
			i -= 60L;
		}

		while (j >= 60) {
			j -= 60;
			++k;
		}

		while (k >= 24) {
			k -= 24;
			++l;
		}

		while (l >= 7) {
			l -= 7;
			++i1;
		}

		while (i1 >= 4) {
			++j1;
			i1 -= 7;
		}

		s = j1 + " Monat(e) " + i1 + " Woche(n), " + l + " Tag(e), " + k + " Stunde(n), " + j + " Minute(n) " + i + " Sekunden";
		return s;
	}

	public String getCreatedTime() {
		String s = "";
		long i = getCreatingTime();
		long j = 0L;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;

		int l1;
		for (l1 = 0; i >= 1000L; i -= 1000L) {
			++j;
		}

		while (j >= 60L) {
			j -= 60L;
			++k;
		}

		while (k >= 60) {
			k -= 60;
			++l;
		}

		while (l >= 24) {
			l -= 24;
			++i1;
		}

		while (i1 >= 7) {
			i1 -= 7;
			++j1;
		}

		while (j1 >= 4) {
			++k1;
			j1 -= 4;
		}

		while (k1 >= 12) {
			++l1;
			k1 -= 12;
		}

		s = l1 + " Jahr(e) " + k1 + " Monat(e) " + j1 + " Woche(n), " + i1 + " Tag(e), " + l + " Stunde(n), " + k + " Minute(n) " + j + " Sekunden";
		return s;
	}

	public String getCreatingDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(getCreatingTime());
		String s = simpledateformat.format(date);
		SimpleDateFormat simpledateformat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date date1 = new Date();
		String s1 = simpledateformat1.format(date1);
		return s1 + " " + s;
	}

	public String toString() {
		String s = "VirtualServer[";
		s = add(s, "ServerName=" + getServerName());
		s = add(s, "ServerID=" + getServerID());
		s = add(s, "ServerUUID=" + getServerUUID());
		s = add(s, "MaxClientsOnline=" + getMaxclients());
		s = add(s, "OnlineClients=" + getOnlineClientsSize());
		s = add(s, "Channels=" + getChannels());
		s = add(s, "HostMessage=" + getHostMessage());
		s = add(s, "OnlineTime=" + getOnlineTime());
		s = add(s, "CreatedDate=" + getCreatingDate());
		s = add(s, "]");
		return s;
	}

	private String add(String org, String s) {
		return org + " " + s;
	}
}
