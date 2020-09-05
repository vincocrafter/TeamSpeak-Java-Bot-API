package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VirtualServerInfo extends _DefaultInfo {
	
	public VirtualServerInfo(String[] infos) {
		super(infos);
	}

	public String getServerUUID() {
		return this.get(0);
	}

	public String getServerName() {
		return this.get(1);
	}

	public String getWelcomeMessage() {
		return this.get(2);
	}

	public int getServerID() {
		return this.toInt(this.get(29));
	}

	public String getPlatform() {
		return this.get(3);
	}

	public String getVersion() {
		return this.get(4);
	}

	public String getPassword() {
		return this.get(5);
	}

	public int getMaxclients() {
		return this.toInt(this.get(5));
	}

	public int getChannels() {
		return this.toInt(this.get(8));
	}

	public long getCreatingTime() {
		return this.toLong(this.get(9));
	}

	public long getUptime() {
		return this.toLong(this.get(10));
	}

	public int getOnlineClientsSize() {
		return this.toInt(this.get(7));
	}

	public String getHostMessage() {
		return this.get(12).replace("\\s", " ");
	}

	public int getHostMessageMode() {
		return this.toInt(this.get(13));
	}

	public int getDefaultServerGroup() {
		return this.toInt(this.get(15));
	}

	public int getDefaultChannelGroup() {
		return this.toInt(this.get(16));
	}

	public boolean hasPassword() {
		return this.toBol(this.toInt(this.get(17)));
	}

	public int getDefaultChannelAdminGroup() {
		return this.toInt(this.get(18));
	}

	public String getHostBannerURL() {
		return this.get(21);
	}

	public int getComplainAutoBanCount() {
		return this.toInt(this.get(24));
	}

	public int getAutoBanTime() {
		return this.toInt(this.get(25));
	}

	public int getServerPort() {
		return this.toInt(this.get(45));
	}

	public boolean isLogClient() {
		return this.toBol(this.toInt(this.get(49)));
	}

	public boolean isLogQuery() {
		return this.toBol(this.toInt(this.get(50)));
	}

	public boolean isLogChannel() {
		return this.toBol(this.toInt(this.get(51)));
	}

	public boolean isLogPermissions() {
		return this.toBol(this.toInt(this.get(51)));
	}

	public boolean isLogServer() {
		return this.toBol(this.toInt(this.get(53)));
	}

	public boolean isLogFiletransfer() {
		return this.toBol(this.toInt(this.get(54)));
	}

	public int getReservedSlots() {
		return this.toInt(this.get(58));
	}

	public int getSecurityLevel() {
		return this.toInt(this.get(48));
	}

	public String getOnlineTime() {
		String s = "";
		long i = this.getUptime();
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
		long i = this.getCreatingTime();
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
		Date date = new Date(this.getCreatingTime());
		String s = simpledateformat.format(date);
		SimpleDateFormat simpledateformat1 = new SimpleDateFormat("dd.MM.yyyy");
		Date date1 = new Date();
		String s1 = simpledateformat1.format(date1);
		return s1 + " " + s;
	}

	public String toString() {
		String s = "VirtualServer[";
		s = this.add(s, "ServerName=" + this.getServerName());
		s = this.add(s, "ServerID=" + this.getServerID());
		s = this.add(s, "ServerUUID=" + this.getServerUUID());
		s = this.add(s, "MaxClientsOnline=" + this.getMaxclients());
		s = this.add(s, "OnlineClients=" + this.getOnlineClientsSize());
		s = this.add(s, "Channels=" + this.getChannels());
		s = this.add(s, "HostMessage=" + this.getHostMessage());
		s = this.add(s, "OnlineTime=" + this.getOnlineTime());
		s = this.add(s, "CreatedDate=" + this.getCreatingDate());
		s = this.add(s, "]");
		return s;
	}

	private String add(String org, String s) {
		return org + " " + s;
	}
}
