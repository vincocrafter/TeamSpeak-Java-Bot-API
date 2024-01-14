package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerStatus;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class VirtualServerInfo extends DefaultInfo {

	public VirtualServerInfo(String[] infos) {
		super(infos);
	}
	
	public VirtualServerInfo(String infos) {
		super(infos);
	}
	
	public String getServerUUID() {
		return get("virtualserver_unique_identifier");
	}
	
	public String getUUID() {
		return getServerUUID();
	}

	public String getServerName() {
		return Formatter.toNormalFormat(get("virtualserver_name"));
	}
	
	public String getName() {
		return getServerName();
	}

	public String getWelcomeMessage() {
		return Formatter.toNormalFormat(get("virtualserver_welcomemessage"));
	}
	
	public VirtualServerStatus getStatus() {
		String status = get("virtualserver_status");
		for (VirtualServerStatus serverStatus : VirtualServerStatus.values()) {
			if (serverStatus.equals(status)) {
				return serverStatus;
			}
		}
		return null;
	}
	
	public int getServerID() {
		return toInt(get("virtualserver_id"));
	}
	
	public int getID() {
		return getServerID();
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
		String info = get("virtualserver_priority_speaker_dimm_modificator");
		return toInt(info.substring(0, info.indexOf(".")));
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
		return Formatter.toRemeaningTime(getUptime());
	}

	public String getCreatedTime() {
		return Formatter.toRemeaningTime(getCreatingTime());
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
		StringBuilder result = new StringBuilder("VirtualServer[");
		result.append("UUID=").append(getServerUUID());
		result.append(",ID=").append(getServerID());
		result.append(",Name=").append(getServerName());
		result.append(",MaxClientsOnline=").append(getMaxclients());
		result.append(",OnlineClients=").append(getOnlineClientsSize());
		result.append("]");
		return result.toString();
	}
}
