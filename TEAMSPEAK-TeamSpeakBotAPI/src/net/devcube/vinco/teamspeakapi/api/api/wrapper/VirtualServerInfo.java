package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class VirtualServerInfo extends DefaultInfo {

	public VirtualServerInfo(String[] infos) {
		super(infos);
	}

	public String getServerUUID() {
		return get("virtualserver_unique_identifier");
	}

	public String getServerName() {
		return get("virtualserver_name");
	}

	public String getWelcomeMessage() {
		return Formatter.toNormalFormat(get("virtualserver_welcomemessage"));
	}

	public int getServerID() {
		return toInt(get("virtualserver_id"));
	}

	public String getPlatform() {
		return get("virtualserver_platform");
	}

	public String getVersion() {
		return get("virtualserver_version");
	}

	public String getPassword() {
		return get("virtualserver_password");
	}

	public int getMaxclients() {
		return toInt(get("virtualserver_maxclients"));
	}

	public int getChannels() {
		return toInt(get("virtualserver_channelsonline"));
	}

	public long getCreatingTime() {
		return toLong(get("virtualserver_created"));
	}

	public long getUptime() {
		return toLong(get("virtualserver_uptime"));
	}

	public int getOnlineClientsSize() {
		return toInt(get("virtualserver_clientsonline"));
	}

	public String getHostMessage() {
		return Formatter.toNormalFormat(get("virtualserver_hostmessage"));
	}

	public int getHostMessageMode() {
		return toInt(get("virtualserver_hostmessage_mode"));
	}

	public String getDefaultFilePath() {
		return Formatter.toNormalFormat(get("virtualserver_filebase"));
	}

	public int getDefaultServerGroup() {
		return toInt(get("virtualserver_default_server_group"));
	}

	public int getDefaultChannelGroup() {
		return toInt(get("virtualserver_default_channel_group"));
	}

	public int getDefaultChannelAdminGroup() {
		return toInt(get("virtualserver_default_channel_admin_group"));
	}

	public boolean hasPassword() {
		return toBol(toInt(get("virtualserver_flag_password")));
	}

	public String getHostBannerURL() {
		return get("virtualserver_hostbanner_url");
	}

	public int getComplainAutoBanCount() {
		return toInt(get("virtualserver_complain_autoban_count"));
	}

	public int getAutoBanTime() {
		return toInt(get("virtualserver_complain_autoban_time"));
	}
	
	public int getComplainRemoveTime() {
		return toInt(get("virtualserver_complain_remove_time"));
	}
	
	public int getMinClientsBeforeForcedSilence() {
		return toInt(get("virtualserver_min_clients_in_channel_before_forced_silence"));
	}
	
	public int getPrioritySpeakerDimmModificator() {
		return toInt(get("virtualserver_priority_speaker_dimm_modificator"));
	}
	
	public int getClientConnections() {
		return toInt(get("virtualserver_client_connections"));
	}
	
	public int getQueryConnections() {
		return toInt(get("virtualserver_query_client_connections"));
	}
	
	public int getServerPort() {
		return toInt(get("virtualserver_port"));
	}
	
	public boolean isAutoStart() {
		return toBol(toInt(get("virtualserver_autostart")));
	}
	
	public int getSecurityLevel() {
		return toInt(get("virtualserver_needed_identity_security_level"));
	}
	
	public boolean isLogClient() {
		return toBol(toInt(get("virtualserver_log_client")));
	}

	public boolean isLogQuery() {
		return toBol(toInt(get("virtualserver_log_query")));
	}

	public boolean isLogChannel() {
		return toBol(toInt(get("virtualserver_log_channel")));
	}

	public boolean isLogPermissions() {
		return toBol(toInt(get("virtualserver_log_permissions")));
	}

	public boolean isLogServer() {
		return toBol(toInt(get("virtualserver_log_server")));
	}

	public boolean isLogFiletransfer() {
		return toBol(toInt(get("virtualserver_log_filetransfer")));
	}

	public int getReservedSlots() {
		return toInt(get("virtualserver_reserved_slots"));
	}
	
	public int getServerIconID() {
		return toInt(get("virtualserver_icon_id"));
	}
		
	public double getTotalPacketlossSpeech() {
		return toDouble(get("virtualserver_total_packetloss_speech"));
	}
	
	public double getTotalPacketlossKeepAlive() {
		return toDouble(get("virtualserver_total_packetloss_keepalive"));
	}
	
	public double getTotalPacketlossControl() {
		return toDouble(get("virtualserver_total_packetloss_control"));
	}
	
	public double getTotalPacketlossTotal() {
		return toDouble(get("virtualserver_total_packetloss_total"));
	}
	
	public double getTotalPing() {
		return toDouble(get("virtualserver_total_ping"));
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
