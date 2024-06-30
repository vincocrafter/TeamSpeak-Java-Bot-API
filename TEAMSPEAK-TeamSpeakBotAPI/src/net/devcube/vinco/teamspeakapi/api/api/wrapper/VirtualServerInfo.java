package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.property.EncryptionMode;
import net.devcube.vinco.teamspeakapi.api.api.property.VirtualServerStatus;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class VirtualServerInfo extends DefaultInfo {

	private ConnectionInfo connectionInfo;

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

	public String getServerNickName() {
		return Formatter.toNormalFormat(get("virtualserver_nickname"));
	}

	public String getWelcomeMessage() {
		return Formatter.toNormalFormat(get("virtualserver_welcomemessage"));
	}

	public VirtualServerStatus getStatus() {
		String status = get("virtualserver_status");
		for (VirtualServerStatus serverStatus : VirtualServerStatus.values()) {
			if (serverStatus.getValue().equalsIgnoreCase(status)) {
				return serverStatus;
			}
		}
		return null;
	}

	public int getEncryptionModeInt() {
		return toIntI("virtualserver_codec_encryption_mode");
	}

	public EncryptionMode getEncryptionMode() {
		int mode = getEncryptionModeInt();
		for (EncryptionMode encryptionModes : EncryptionMode.values()) {
			if (encryptionModes.getValue() == mode) {
				return encryptionModes;
			}
		}
		return null;
	}

	public int getServerID() {
		return toIntI("virtualserver_id");
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
		return toIntI("virtualserver_maxclients");
	}

	public int getChannels() {
		return toIntI("virtualserver_channelsonline");
	}

	public long getCreatingTime() {
		return toLongI("virtualserver_created");
	}

	public int getOnlineClientsSize() {
		return toIntI("virtualserver_clientsonline");
	}

	public String getHostMessage() {
		return Formatter.toNormalFormat(get("virtualserver_hostmessage"));
	}

	public int getHostMessageMode() {
		return toIntI("virtualserver_hostmessage_mode");
	}

	public String getDefaultFilePath() {
		return Formatter.toNormalFormat(get("virtualserver_filebase"));
	}

	public int getDefaultServerGroup() {
		return toIntI("virtualserver_default_server_group");
	}

	public int getDefaultChannelGroup() {
		return toIntI("virtualserver_default_channel_group");
	}

	public int getDefaultChannelAdminGroup() {
		return toIntI("virtualserver_default_channel_admin_group");
	}

	public BigInteger getMaxDownloadTotalBandwidth() {
		return new BigInteger(get("virtualserver_max_download_total_bandwidth"));
	}

	public BigInteger getMaxUploadTotalBandwidth() {
		return new BigInteger(get("virtualserver_max_upload_total_bandwidth"));
	}

	public boolean hasPassword() {
		return toBolI("virtualserver_flag_password");
	}

	public String getHostBannerURL() {
		return get("virtualserver_hostbanner_url");
	}

	public int getComplainAutoBanCount() {
		return toIntI("virtualserver_complain_autoban_count");
	}

	public int getAutoBanTime() {
		return toIntI("virtualserver_complain_autoban_time");
	}

	public int getComplainRemoveTime() {
		return toIntI("virtualserver_complain_remove_time");
	}

	public int getMinClientsBeforeForcedSilence() {
		return toIntI("virtualserver_min_clients_in_channel_before_forced_silence");
	}

	public int getPrioritySpeakerDimmModificator() {
		String info = get("virtualserver_priority_speaker_dimm_modificator");
		return toInt(info.substring(0, info.indexOf(".")));
	}

	public int getClientConnections() {
		return toIntI("virtualserver_client_connections");
	}

	public int getQueryConnections() {
		return toIntI("virtualserver_query_client_connections");
	}

	public int getServerPort() {
		return toIntI("virtualserver_port");
	}

	public boolean isAutoStart() {
		return toBolI("virtualserver_autostart");
	}

	public int getSecurityLevel() {
		return toIntI("virtualserver_needed_identity_security_level");
	}

	public boolean isLogClient() {
		return toBolI("virtualserver_log_client");
	}

	public boolean isLogQuery() {
		return toBolI("virtualserver_log_query");
	}

	public boolean isLogChannel() {
		return toBolI("virtualserver_log_channel");
	}

	public boolean isLogPermissions() {
		return toBolI("virtualserver_log_permissions");
	}

	public boolean isLogServer() {
		return toBolI("virtualserver_log_server");
	}

	public boolean isLogFiletransfer() {
		return toBolI("virtualserver_log_filetransfer");
	}

	public int getReservedSlots() {
		return toIntI("virtualserver_reserved_slots");
	}

	public long getServerIconID() {
		return toLongI("virtualserver_icon_id");
	}

	public double getTotalPacketlossSpeech() {
		return toDoubleI("virtualserver_total_packetloss_speech");
	}

	public double getTotalPacketlossKeepAlive() {
		return toDoubleI("virtualserver_total_packetloss_keepalive");
	}

	public double getTotalPacketlossControl() {
		return toDoubleI("virtualserver_total_packetloss_control");
	}

	public int getQuerysOnlineSize() {
		return toIntI("virtualserver_queryclientsonline");
	}

	public String getHostbannerGFXURL() {
		return get("virtualserver_hostbanner_gfx_url");
	}

	public int getHostBannerGFXInterval() {
		return toIntI("virtualserver_hostbanner_gfx_interval");
	}

	public int getAntifloodPointsTickReduce() {
		return toIntI("virtualserver_antiflood_points_tick_reduce");
	}

	public int getAntifloodPointsNeededCommandBlock() {
		return toIntI("virtualserver_antiflood_points_needed_command_block");
	}

	public int getAntifloodPointsNeededIPBlock() {
		return toIntI("virtualserver_antiflood_points_needed_ip_block");
	}

	public String getHostbuttonTooltip() {
		return get("virtualserver_hostbutton_tooltip");
	}

	public String getHostbuttonURL() {
		return get("virtualserver_hostbutton_url");
	}

	public String getHostbuttonGFXURL() {
		return get("virtualserver_hostbutton_gfx_url");
	}

	public BigInteger getDownloadQuota() {
		return new BigInteger(get("virtualserver_download_quota"));
	}

	public BigInteger getUploadQuota() {
		return new BigInteger(get("virtualserver_upload_quota"));
	}

	public int getMonthBytesDownloaded() {
		return toIntI("virtualserver_month_bytes_downloaded");
	}

	public int getMonthBytesUploaded() {
		return toIntI("virtualserver_month_bytes_uploaded");
	}

	public int getTotalBytesDownloaded() {
		return toIntI("virtualserver_total_bytes_downloaded");
	}

	public int getTotalBytesUploaded() {
		return toIntI("virtualserver_total_bytes_uploaded");
	}

	public String getMachineID() {
		return get("virtualserver_machine_id");
	}

	public long getMinClientVersion() {
		return toLongI("virtualserver_min_client_version");
	}

	public String getPhoneticName() {
		return get("virtualserver_name_phonetic");
	}

	public String getIP() {
		return get("virtualserver_ip");
	}

	public boolean isWeblistEnabled() {
		return toBolI("virtualserver_weblist_enabled");
	}

	public boolean isAskForPrivilegeKey() {
		return toBolI("virtualserver_ask_for_privilegekey");
	}

	public int getHostbannerMode() {
		return toIntI("virtualserver_hostbanner_mode");
	}

	public int getChannelTempDeleteDefaultDelay() {
		return toIntI("virtualserver_channel_temp_delete_delay_default");
	}

	public long getMinAndroidVersion() {
		return toLongI("virtualserver_min_android_version");
	}

	public long getMinIOSVersion() {
		return toLongI("virtualserver_min_ios_version");
	}

	public int getAntifloodPointsNeededPluginBlock() {
		return toIntI("virtualserver_antiflood_points_needed_plugin_block");
	}

	public String getCapabilityExtensions() {
		return get("virtualserver_capability_extensions");
	}

	public String getFileStorageClass() {
		return get("virtualserver_file_storage_class");
	}

	public int getPacketsSendSpeech() {
		return toIntI("connection_packets_sent_speech");
	}

	public int getBytesPacketsSendSpeech() {
		return toIntI("connection_bytes_sent_speech");
	}

	public int getPacketsRecivedSpeech() {
		return toIntI("connection_packets_received_speech");
	}

	public int getBytesPacketsRecivedSpeech() {
		return toIntI("connection_bytes_received_speech");
	}

	public int getPacketsSendKeepalive() {
		return toIntI("connection_packets_sent_keepalive");
	}

	public int getBytesPacketsSendKeepalive() {
		return toIntI("connection_bytes_sent_keepalive");
	}

	public int getPacketsRecivedKeepalive() {
		return toIntI("connection_packets_received_keepalive");
	}

	public int getBytesPacketsRecivedKeepalive() {
		return toIntI("connection_bytes_received_keepalive");
	}

	public int getPacketsSendControl() {
		return toIntI("connection_packets_sent_control");
	}

	public int getBytesPacketsSendControl() {
		return toIntI("connection_bytes_sent_control");
	}

	public int getPacketsRecivedControl() {
		return toIntI("connection_packets_received_control");
	}

	public int getBytesPacketsRecivedControl() {
		return toIntI("connection_bytes_received_control");
	}

	public ConnectionInfo getConnectionInfo() {
		if (connectionInfo == null) {
			this.connectionInfo = new ConnectionInfo("");
			List<String> copyInfos = new ArrayList<>(12);
			copyInfos.add("connection_filetransfer_bandwidth_sent");
			copyInfos.add("connection_filetransfer_bandwidth_received");
			copyInfos.add("connection_filetransfer_bytes_sent_total");
			copyInfos.add("connection_filetransfer_bytes_received_total");
			copyInfos.add("connection_packets_sent_total");
			copyInfos.add("connection_bytes_sent_total");
			copyInfos.add("connection_packets_received_total");
			copyInfos.add("connection_bytes_received_total");
			copyInfos.add("connection_bandwidth_sent_last_second_total");
			copyInfos.add("connection_bandwidth_sent_last_minute_total");
			copyInfos.add("connection_bandwidth_received_last_second_total");
			copyInfos.add("connection_bandwidth_received_last_minute_total");
			
			for (String infos : copyInfos) {
				copyInfoTo(infos, connectionInfo, infos);
			}
			copyInfoTo("virtualserver_uptime", connectionInfo, "connection_connected_time");
			copyInfoTo("virtualserver_total_packetloss_total", connectionInfo, "connection_packetloss_total");
			copyInfoTo("virtualserver_total_ping", connectionInfo, "connection_ping");
		}
		return connectionInfo;
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
