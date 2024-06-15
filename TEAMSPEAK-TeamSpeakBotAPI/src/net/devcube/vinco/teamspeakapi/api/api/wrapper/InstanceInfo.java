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

import java.math.BigInteger;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class InstanceInfo extends DefaultInfo {

	/**
	 * @param infos
	 */
	public InstanceInfo(String[] infos) {
		super(infos);
	}
	
	public InstanceInfo(String infos) {
		super(infos);
	}
	
	public int getDataBaseVersion() {
		return toIntI("serverinstance_database_version");
	}
	
	public int getFileTransferPort() {
		return toIntI("serverinstance_filetransfer_port");
	}
	
	public BigInteger getMaxDownloadTotalBandwidth() {
		return new BigInteger(get("serverinstance_max_download_total_bandwidth"));
	}
	
	public BigInteger getMaxUploadTotalBandwidth() {
		return new BigInteger(get("serverinstance_max_upload_total_bandwidth"));
	}
	
	public int getGuestServerQueryGroup() {
		return toIntI("serverinstance_guest_serverquery_group");
	}
	
	public int getServerQueryFloodCommands() {
		return toIntI("serverinstance_serverquery_flood_commands");
	}
	
	public int getServerQueryFloodTime() {
		return toIntI("serverinstance_serverquery_flood_time");
	}
	
	public int getServerQueryBanTime() {
		return toIntI("serverinstance_serverquery_ban_time");
	}
	
	public int getTemplateServerAdminGroup() {
		return toIntI("serverinstance_template_serveradmin_group");
	}
	
	public int getTemplateServerDefaultGroup() {
		return toIntI("serverinstance_template_serverdefault_group");
	}
	
	public int getTemplateChannelAdminGroup() {
		return toIntI("serverinstance_template_channeladmin_group");
	}
	
	public int getTemplateChannelDefaultGroup() {
		return toIntI("serverinstance_template_channeldefault_group");
	}
		
	public int getPermissionsVersion() {
		return toIntI("serverinstance_permissions_version");
	}
	
	public int getPendingConnectionsPerIP() {
		return toIntI("serverinstance_pending_connections_per_ip");
	}
	
	public int getServerQueryMaxConnectionsPerIP() {
		return toIntI("serverinstance_serverquery_max_connections_per_ip");
	}
}
