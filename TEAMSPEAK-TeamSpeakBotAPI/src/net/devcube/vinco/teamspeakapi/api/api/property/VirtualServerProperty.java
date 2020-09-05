package net.devcube.vinco.teamspeakapi.api.api.property;

public enum VirtualServerProperty {
	
   VIRTUALSERVER_NAME("virtualserver_name"),
   VIRTUALSERVER_WELCOMEMESSAGE("virtualserver_welcomemessage"),
   VIRTUALSERVER_MAXCLIENTS("virtualserver_maxclients"),
   VIRTUALSERVER_PASSWORD("virtualserver_password"),
   VIRTUALSERVER_HOSTMESSAGE("virtualserver_hostmessage"),
   VIRTUALSERVER_DEFAULT_SERVER_GROUP("virtualserver_default_server_group"),
   VIRTUALSERVER_DEFAULT_CHANNEL_GROUP("virtualserver_default_channel_group"),
   VIRTUALSERVER_DEFAULT_CHANNEL_ADMIN_GROUP("virtualserver_default_channel_admin_group"),
   VIRTUALSERVER_MAX_DOWNLOAD_TOTAL_BANDWIDTH("virtualserver_max_download_total_bandwidth"),
   VIRTUALSERVER_MAX_UPLOAD_TOTAL_BANDWIDTH("virtaulserver_max_upload_total_bandwidth"),
   VIRTUALSERVER_HOSTBANNER_URL("virtualserver_hostbanner_url"),
   VIRTUALSERVER_HOSTBANNER_GFX_URL("virtualserver_hostbanner_gfx_url"),
   VIRTUALSERVER_HOSTBANNER_GFX_INTERVAL("virtualserver_hostbanner_gfx_inerval"),
   VIRTUALSERVER_COMPLAIN_AUTOBAN_COUNT("virtualserver_complain_autoban_count"),
   VIRTUALSERVER_COMPLAIN_AUTOBAN_TIME("virtualserver_complain_autoban_time"),
   VIRTUALSERVER_COMPLAIN_REMOVE_TIME("virtualserver_complain_remove_time"),
   VIRTUALSERVER_MIN_CLIENTS_IN_CHANNEL_BEFORE_FORCED_SILENCE("virtualserver_mix_clients_in_channel_before_forced_silence"),
   VIRTUALSERVER_PRIORITY_SPEAKER_DIMM_MODIFICATOR("virtualserver_priority_speaker_dimm_modificator"),
   VIRTUALSERVER_ANTIFLOOD_POINTS_TICK_REDUCE("virtualserver_antiflood_points_tick_reduce"),
   VIRTUALSERVER_ANTIFLOOD_POINTS_NEEDED_COMMAND_BLOCK("virtualserver_antiflood_poins_neeeded_command_block"),
   VIRTUALSERVER_ANTIFLOOD_POINTS_NEEDED_IP_BLOCK("virualserver_antiflood_points_needed_ip_block"),
   VIRTUALSERVER_HOSTBUTTON_TOOLTIP("virtualserver_hostbutton_tooltip"),
   VIRTUALSERVER_HOSTBUTTON_GFX_URL("virtualserver_hostbutton_gfx_url"),
   VIRTUALSERVER_HOSTBUTTON_URL("virtualserver_hostbutton_url"),
   VIRTUALSERVER_DOWNLOAD_QUOTA("virtualserver_download_quota"),
   VIRTUALSERVER_UPLOAD_QUOTA("virtualserver_upload_quota"),
   VIRTUALSERVER_MACHINE_ID("virtualserver_machine_id"),
   VIRTUALSERVER_PORT("virtualserver_port"),
   VIRTUALSERVER_AUTOSTART("virtualserver_autostart"),
   VIRTUALSERVER_STATUS("virtualserver_status"),
   VIRTUALSERVER_LOG_CLIENT("virtualserver_log_client"),
   VIRTUALSERVER_LOG_QUERY("virtualserver_log_query"),
   VIRTUALSERVER_LOG_CHANNEL("virtualserver_log_channel"),
   VIRTUALSERVER_LOG_PERMISSIONS("virtualserver_log_permissions"),
   VIRTUALSERVER_LOG_SERVER("virtualserver_log_server"),
   VIRTUALSERVER_LOG_FILETRANSFER("virtualserver_log_filetransfer"),
   VIRTUALSERVER_MIN_CLIENT_VERSION("virtualserver_min_client_version"),
   VIRTUALSERVER_MIN_ANDROID_VERSION("virtualserver_min_android_version"),
   VIRTUALSERVER_MIN_IOS_VERSION("virutalserver_min_ios_version"),
   VIRTUALSERVER_MIN_WINPHONE_VERSION("virtualserver_min_winphone_version"),
   VIRTUALSERVER_NEEDED_IDENTITY_SECURITY_LEVEL("virtualserver_needed_identity_security_level"),
   VIRTUALSERVER_NAME_PHONETIC("virtualserver_name_phoentic"),
   VIRTUALSERVER_ICON_ID("virtualserver_icon_id"),
   VIRTUALSERVER_RESERVED_SLOTS("virtualserver_reserved_slots"),
   VIRTUALSERVER_WEBLIST_ENABLED("virtualserver_weblist_enabled"),
   VIRTUALSERVER_CODEC_ENCRYPTION_MODE("virtualserver_codec_encryption_mode");

   String value = "";

   private VirtualServerProperty(String s) {
      this.value = s;
   }

   public String getValue() {
      return this.value;
   }
}
