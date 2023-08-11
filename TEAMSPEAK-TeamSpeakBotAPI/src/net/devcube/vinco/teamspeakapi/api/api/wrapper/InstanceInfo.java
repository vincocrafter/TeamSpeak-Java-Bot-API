/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.04.2023
 * 
 * Uhrzeit : 14:34:42
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class InstanceInfo extends DefaultInfo {

	/**
	 * @param infos
	 */
	public InstanceInfo(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}
	
	public int getDataBaseVersion() {
		return toInt(get("serverinstance_database_version"));
	}
	
	public int getFileTransferPort() {
		return toInt(get("serverinstance_filetransfer_port"));
	}
	
	public long getMaxDownloadTotalBandwidth() {
		return toLong(get("serverinstance_max_download_total_bandwidth"));
	}
	
	public long getMaxUploadTotalBandwidth() {
		return toLong(get("serverinstance_max_upload_total_bandwidth"));
	}
	
	public int getGuestServerQueryGroup() {
		return toInt(get("serverinstance_guest_serverquery_group"));
	}
	
	public int getServerQueryFloodCommands() {
		return toInt(get("serverinstance_serverquery_flood_commands"));
	}
	
	public int getServerQueryFloodTime() {
		return toInt(get("serverinstance_serverquery_flood_time"));
	}
	
	public int getServerQueryBanTime() {
		return toInt(get("serverinstance_serverquery_ban_time"));
	}
	
	public int getTemplateServerAdminGroup() {
		return toInt(get("serverinstance_template_serveradmin_group"));
	}
	
	public int getTemplateServerDefaultGroup() {
		return toInt(get("serverinstance_template_serverdefault_group"));
	}
	
	public int getTemplateChannelAdminGroup() {
		return toInt(get("serverinstance_template_channeladmin_group"));
	}
	
	public int getTemplateChannelDefaultGroup() {
		return toInt(get("serverinstance_template_channeldefault_group"));
	}
		
	public int getPermissionsVersion() {
		return toInt(get("serverinstance_permissions_version"));
	}
	
	public int getPendingConnectionsPerIP() {
		return toInt(get("serverinstance_pending_connections_per_ip"));
	}
	
	public int getServerQueryMaxConnectionsPerIP() {
		return toInt(get("serverinstance_serverquery_max_connections_per_ip"));
	}
}
