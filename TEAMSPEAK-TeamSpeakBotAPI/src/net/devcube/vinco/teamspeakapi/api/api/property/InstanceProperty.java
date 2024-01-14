/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.04.2023
 * 
 * Uhrzeit : 14:27:24
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

public enum InstanceProperty {

	DATABASE_VERSION("serverinstance_database_version"),
	FILETRANSFER_PORT("serverinstance_filetransfer_port"),
	MAX_DOWNLOAD_TOTAL_BANDWIDTH("serverinstance_max_download_total_bandwidth"),
	MAX_UPLOAD_TOTAL_BANDWIDTH("serverinstance_max_upload_total_bandwidth"),
	GUEST_SERVERQUERY_GROUP("serverinstance_guest_serverquery_group"),
	SERVERQUERY_FLOOD_COMMANDS("serverinstance_serverquery_flood_commands"),
	SERVERQUERY_FLOOD_TIME("serverinstance_serverquery_flood_time"),
	SERVERQUERY_BAN_TIME("serverinstance_serverquery_ban_time"),
	TEMPLATE_SERVERADMIN_GROUP("serverinstance_template_serveradmin_group"),
	TEMPLATE_SERVERDEFAULT_GROUP("serverinstance_template_serverdefault_group"),
	TEMPLATE_CHANNELADMIN_GROUP("serverinstance_template_channeladmin_group"),
	TEMPLATE_CHANNELDEFAULT_GROUP("serverinstance_template_channeldefault_group"),
	PERMISSIONS_VERSION("serverinstance_permissions_version"),
	PENDING_CONNECTIONS_PER_IP("serverinstance_pending_connections_per_ip"),
	SERVERQUERY_MAX_CONNECTIONS_PER_IP("serverinstance_serverquery_max_connections_per_ip");

	private String value = "";

	private InstanceProperty(String s) {
		this.value = s;
	}

	public String getValue() {
		return this.value;
	}

}
