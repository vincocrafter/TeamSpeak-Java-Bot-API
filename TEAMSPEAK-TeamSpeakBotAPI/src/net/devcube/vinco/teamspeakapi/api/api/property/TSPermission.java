/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 22.04.2023
 * 
 * Uhrzeit : 12:56:36
 */
package net.devcube.vinco.teamspeakapi.api.api.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public enum TSPermission {

	B_SERVERINSTANCE_HELP_VIEW(1, "b_serverinstance_help_view"),
	B_SERVERINSTANCE_INFO_VIEW(2, "b_serverinstance_info_view"),
	B_SERVERINSTANCE_VIRTUALSERVER_LIST(3, "b_serverinstance_virtualserver_list"),
	B_SERVERINSTANCE_BINDING_LIST(4, "b_serverinstance_binding_list"),
	B_SERVERINSTANCE_PERMISSION_LIST(5, "b_serverinstance_permission_list"),
	B_SERVERINSTANCE_PERMISSION_FIND(6, "b_serverinstance_permission_find"),
	
	B_VIRTUALSERVER_CREATE(7, "b_virtualserver_create"),
	B_VIRTUALSERVER_DELETE(8, "b_virtualserver_delete"),
	B_VIRTUALSERVER_START_ANY(9, "b_virtualserver_start_any"),
	B_VIRTUALSERVER_STOP_ANY(10, "b_virtualserver_stop_any"),
	B_VIRTUALSERVER_CHANGE_MACHINE_ID(11, "b_virtualserver_change_machine_id"),
	B_VIRTUALSERVER_CHANGE_TEMPLATE(12, "b_virtualserver_change_template"),

	B_SERVERQUERY_LOGIN(13, "b_serverquery_login"),
	B_SERVERQUERY_LOGIN_CREATE(14, "b_serverquery_login_create"),
	B_SERVERQUERY_LOGIN_DELETE(15, "b_serverquery_login_delete"),
	B_SERVERQUERY_LOGIN_LIST(16, "b_serverquery_login_list"), 
	
	B_SERVERINSTANCE_TEXTMESSAGE_SEND(17, "b_serverinstance_textmessage_send"),
	B_SERVERINSTANCE_LOG_VIEW(18, "b_serverinstance_log_view"),
	B_SERVERINSTANCE_LOG_ADD(19, "b_serverinstance_log_add"),
	B_SERVERINSTANCE_STOP(20, "b_serverinstance_stop"),

	B_SERVERINSTANCE_MODIFY_SETTINGS(21, "b_serverinstance_modify_settings"),
	B_SERVERINSTANCE_MODIFY_QUERYGROUP(22, "b_serverinstance_modify_querygroup"),
	B_SERVERINSTANCE_MODIFY_TEMPLATES(23, "b_serverinstance_modify_templates"),

	B_VIRTUALSERVER_SELECT(24, "b_virtualserver_select"),
	B_VIRTUALSERVER_INFO_VIEW(25, "b_virtualserver_info_view"),
	B_VIRTUALSERVER_CONNECTIONINFO_VIEW(26, "b_virtualserver_connectioninfo_view"),
	B_VIRTUALSERVER_CHANNEL_LIST(27, "b_virtualserver_channel_list"),
	B_VIRTUALSERVER_CHANNEL_SEARCH(28, "b_virtualserver_channel_search"),
	B_VIRTUALSERVER_CLIENT_LIST(29, "b_virtualserver_client_list"),
	B_VIRTUALSERVER_CLIENT_SEARCH(30, "b_virtualserver_client_search"),
	B_VIRTUALSERVER_CLIENT_DBLIST(31, "b_virtualserver_client_dblist"),
	B_VIRTUALSERVER_CLIENT_DBSEARCH(32, "b_virtualserver_client_dbsearch"),
	B_VIRTUALSERVER_CLIENT_DBINFO(33, "b_virtualserver_client_dbinfo"),
	B_VIRTUALSERVER_PERMISSION_FIND(34, "b_virtualserver_permission_find"),
	B_VIRTUALSERVER_CUSTOM_SEARCH(35, "b_virtualserver_custom_search"),

	B_VIRTUALSERVER_START(36, "b_virtualserver_start"),
	B_VIRTUALSERVER_STOP(37, "b_virtualserver_stop"),
	B_VIRTUALSERVER_TOKEN_LIST(38, "b_virtualserver_token_list"),
	B_VIRTUALSERVER_TOKEN_ADD(39, "b_virtualserver_token_add"),
	B_VIRTUALSERVER_TOKEN_USE(40, "b_virtualserver_token_use"),
	B_VIRTUALSERVER_TOKEN_DELETE(41, "b_virtualserver_token_delete"),
	B_VIRTUALSERVER_APIKEY_ADD(42, "b_virtualserver_apikey_add"),
	B_VIRTUALSERVER_APIKEY_MANAGE(43, "b_virtualserver_apikey_manage"),
	B_VIRTUALSERVER_LOG_VIEW(44, "b_virtualserver_log_view"),
	B_VIRTUALSERVER_LOG_ADD(45, "b_virtualserver_log_add"),
	B_VIRTUALSERVER_JOIN_IGNORE_PASSWORD(46, "b_virtualserver_join_ignore_password"),
	B_VIRTUALSERVER_NOTIFY_REGISTER(47, "b_virtualserver_notify_register"),
	B_VIRTUALSERVER_NOTIFY_UNREGISTER(48, "b_virtualserver_notify_unregister"),
	B_VIRTUALSERVER_SNAPSHOT_CREATE(49, "b_virtualserver_snapshot_create"),
	B_VIRTUALSERVER_SNAPSHOT_DEPLOY(50, "b_virtualserver_snapshot_deploy"),
	B_VIRTUALSERVER_PERMISSION_RESET(51, "b_virtualserver_permission_reset"),

	B_VIRTUALSERVER_MODIFY_NAME(52, "b_virtualserver_modify_name"),
	B_VIRTUALSERVER_MODIFY_WELCOMEMESSAGE(53, "b_virtualserver_modify_welcomemessage"),
	B_VIRTUALSERVER_MODIFY_MAXCLIENTS(54, "b_virtualserver_modify_maxclients"),
	B_VIRTUALSERVER_MODIFY_RESERVED_SLOTS(55, "b_virtualserver_modify_reserved_slots"),
	B_VIRTUALSERVER_MODIFY_PASSWORD(56, "b_virtualserver_modify_password"),
	B_VIRTUALSERVER_MODIFY_DEFAULT_SERVERGROUP(57, "b_virtualserver_modify_default_servergroup"),
	B_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELGROUP(58, "b_virtualserver_modify_default_channelgroup"),
	B_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELADMINGROUP(59, "b_virtualserver_modify_default_channeladmingroup"),
	B_VIRTUALSERVER_MODIFY_CHANNEL_FORCED_SILENCE(60, "b_virtualserver_modify_channel_forced_silence"),
	B_VIRTUALSERVER_MODIFY_COMPLAIN(61, "b_virtualserver_modify_complain"),
	B_VIRTUALSERVER_MODIFY_ANTIFLOOD(62, "b_virtualserver_modify_antiflood"),
	B_VIRTUALSERVER_MODIFY_FT_SETTINGS(63, "b_virtualserver_modify_ft_settings"),
	B_VIRTUALSERVER_MODIFY_FT_QUOTAS(64, "b_virtualserver_modify_ft_quotas"),
	B_VIRTUALSERVER_MODIFY_HOSTMESSAGE(65, "b_virtualserver_modify_hostmessage"),
	B_VIRTUALSERVER_MODIFY_HOSTBANNER(66, "b_virtualserver_modify_hostbanner"),
	B_VIRTUALSERVER_MODIFY_HOSTBUTTON(67, "b_virtualserver_modify_hostbutton"),
	B_VIRTUALSERVER_MODIFY_PORT(68, "b_virtualserver_modify_port"),
	B_VIRTUALSERVER_MODIFY_AUTOSTART(69, "b_virtualserver_modify_autostart"),
	B_VIRTUALSERVER_MODIFY_NEEDED_IDENTITY_SECURITY_LEVEL(70, "b_virtualserver_modify_needed_identity_security_level"),
	B_VIRTUALSERVER_MODIFY_PRIORITY_SPEAKER_DIMM_MODIFICATOR(71, "b_virtualserver_modify_priority_speaker_dimm_modificator"),
	B_VIRTUALSERVER_MODIFY_LOG_SETTINGS(72, "b_virtualserver_modify_log_settings"),
	B_VIRTUALSERVER_MODIFY_MIN_CLIENT_VERSION(73, "b_virtualserver_modify_min_client_version"),
	B_VIRTUALSERVER_MODIFY_ICON_ID(74, "b_virtualserver_modify_icon_id"),
	B_VIRTUALSERVER_MODIFY_WEBLIST(75, "b_virtualserver_modify_weblist"),
	B_VIRTUALSERVER_MODIFY_CODEC_ENCRYPTION_MODE(76, "b_virtualserver_modify_codec_encryption_mode"),
	B_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS(77, "b_virtualserver_modify_temporary_passwords"),
	B_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS_OWN(78, "b_virtualserver_modify_temporary_passwords_own"),
	B_VIRTUALSERVER_MODIFY_CHANNEL_TEMP_DELETE_DELAY_DEFAULT(79, "b_virtualserver_modify_channel_temp_delete_delay_default"),
	B_VIRTUALSERVER_MODIFY_NICKNAME(80, "b_virtualserver_modify_nickname"),
	B_VIRTUALSERVER_MODIFY_INTEGRATIONS(81, "b_virtualserver_modify_integrations"),

	I_CHANNEL_MIN_DEPTH(82, "i_channel_min_depth"),
	I_CHANNEL_MAX_DEPTH(83, "i_channel_max_depth"),
	B_CHANNEL_GROUP_INHERITANCE_END(84, "b_channel_group_inheritance_end"),
	I_CHANNEL_PERMISSION_MODIFY_POWER(85, "i_channel_permission_modify_power"),
	I_CHANNEL_NEEDED_PERMISSION_MODIFY_POWER(86, "i_channel_needed_permission_modify_power"),

	B_CHANNEL_INFO_VIEW(87, "b_channel_info_view"),

	B_CHANNEL_CREATE_CHILD(88, "b_channel_create_child"),
	B_CHANNEL_CREATE_PERMANENT(89, "b_channel_create_permanent"),
	B_CHANNEL_CREATE_SEMI_PERMANENT(90, "b_channel_create_semi_permanent"),
	B_CHANNEL_CREATE_TEMPORARY(91, "b_channel_create_temporary"),
	B_CHANNEL_CREATE_WITH_TOPIC(92, "b_channel_create_with_topic"),
	B_CHANNEL_CREATE_WITH_DESCRIPTION(93, "b_channel_create_with_description"),
	B_CHANNEL_CREATE_WITH_PASSWORD(94, "b_channel_create_with_password"),
	B_CHANNEL_CREATE_WITH_BANNER(95, "b_channel_create_with_banner"),
	B_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSVOICE(96, "b_channel_create_modify_with_codec_opusvoice"),
	B_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSMUSIC(97, "b_channel_create_modify_with_codec_opusmusic"),
	I_CHANNEL_CREATE_MODIFY_WITH_CODEC_MAXQUALITY(98, "i_channel_create_modify_with_codec_maxquality"),
	I_CHANNEL_CREATE_MODIFY_WITH_CODEC_LATENCY_FACTOR_MIN(99, "i_channel_create_modify_with_codec_latency_factor_min"),
	B_CHANNEL_CREATE_WITH_MAXCLIENTS(100, "b_channel_create_with_maxclients"),
	B_CHANNEL_CREATE_WITH_MAXFAMILYCLIENTS(101, "b_channel_create_with_maxfamilyclients"),
	B_CHANNEL_CREATE_WITH_SORTORDER(102, "b_channel_create_with_sortorder"),
	B_CHANNEL_CREATE_WITH_DEFAULT(103, "b_channel_create_with_default"),
	B_CHANNEL_CREATE_WITH_NEEDED_TALK_POWER(104, "b_channel_create_with_needed_talk_power"),
	B_CHANNEL_CREATE_MODIFY_WITH_FORCE_PASSWORD(105, "b_channel_create_modify_with_force_password"),
	I_CHANNEL_CREATE_MODIFY_WITH_TEMP_DELETE_DELAY(106, "i_channel_create_modify_with_temp_delete_delay"),

	B_CHANNEL_MODIFY_PARENT(107, "b_channel_modify_parent"),
	B_CHANNEL_MODIFY_MAKE_DEFAULT(108, "b_channel_modify_make_default"),
	B_CHANNEL_MODIFY_MAKE_PERMANENT(109, "b_channel_modify_make_permanent"),
	B_CHANNEL_MODIFY_MAKE_SEMI_PERMANENT(110, "b_channel_modify_make_semi_permanent"),
	B_CHANNEL_MODIFY_MAKE_TEMPORARY(111, "b_channel_modify_make_temporary"),
	B_CHANNEL_MODIFY_NAME(112, "b_channel_modify_name"),
	B_CHANNEL_MODIFY_TOPIC(113, "b_channel_modify_topic"),
	B_CHANNEL_MODIFY_DESCRIPTION(114, "b_channel_modify_description"),
	B_CHANNEL_MODIFY_PASSWORD(115, "b_channel_modify_password"),
	B_CHANNEL_MODIFY_BANNER(116, "b_channel_modify_banner"),
	B_CHANNEL_MODIFY_CODEC(117, "b_channel_modify_codec"),
	B_CHANNEL_MODIFY_CODEC_QUALITY(118, "b_channel_modify_codec_quality"),
	B_CHANNEL_MODIFY_CODEC_LATENCY_FACTOR(119, "b_channel_modify_codec_latency_factor"),
	B_CHANNEL_MODIFY_MAXCLIENTS(120, "b_channel_modify_maxclients"),
	B_CHANNEL_MODIFY_MAXFAMILYCLIENTS(121, "b_channel_modify_maxfamilyclients"),
	B_CHANNEL_MODIFY_SORTORDER(122, "b_channel_modify_sortorder"),
	B_CHANNEL_MODIFY_NEEDED_TALK_POWER(123, "b_channel_modify_needed_talk_power"),
	I_CHANNEL_MODIFY_POWER(124, "i_channel_modify_power"),
	I_CHANNEL_NEEDED_MODIFY_POWER(125, "i_channel_needed_modify_power"),
	B_CHANNEL_MODIFY_MAKE_CODEC_ENCRYPTED(126, "b_channel_modify_make_codec_encrypted"),
	B_CHANNEL_MODIFY_TEMP_DELETE_DELAY(127, "b_channel_modify_temp_delete_delay"),

	B_CHANNEL_DELETE_PERMANENT(128, "b_channel_delete_permanent"),
	B_CHANNEL_DELETE_SEMI_PERMANENT(129, "b_channel_delete_semi_permanent"),
	B_CHANNEL_DELETE_TEMPORARY(130, "b_channel_delete_temporary"),
	B_CHANNEL_DELETE_FLAG_FORCE(131, "b_channel_delete_flag_force"),
	I_CHANNEL_DELETE_POWER(132, "i_channel_delete_power"),
	I_CHANNEL_NEEDED_DELETE_POWER(133, "i_channel_needed_delete_power"),

	B_CHANNEL_JOIN_PERMANENT(134, "b_channel_join_permanent"),
	B_CHANNEL_JOIN_SEMI_PERMANENT(135, "b_channel_join_semi_permanent"),
	B_CHANNEL_JOIN_TEMPORARY(136, "b_channel_join_temporary"),
	B_CHANNEL_JOIN_IGNORE_PASSWORD(137, "b_channel_join_ignore_password"),
	B_CHANNEL_JOIN_IGNORE_MAXCLIENTS(138, "b_channel_join_ignore_maxclients"),
	I_CHANNEL_JOIN_POWER(139, "i_channel_join_power"),
	I_CHANNEL_NEEDED_JOIN_POWER(140, "i_channel_needed_join_power"),
	I_CHANNEL_SUBSCRIBE_POWER(141, "i_channel_subscribe_power"),
	I_CHANNEL_NEEDED_SUBSCRIBE_POWER(142, "i_channel_needed_subscribe_power"),
	I_CHANNEL_DESCRIPTION_VIEW_POWER(143, "i_channel_description_view_power"),
	I_CHANNEL_NEEDED_DESCRIPTION_VIEW_POWER(144, "i_channel_needed_description_view_power"),

	I_ICON_ID(145, "i_icon_id"),
	I_MAX_ICON_FILESIZE(146, "i_max_icon_filesize"),
	B_ICON_MANAGE(147, "b_icon_manage"),
	B_GROUP_IS_PERMANENT(148, "b_group_is_permanent"),
	I_GROUP_AUTO_UPDATE_TYPE(149, "i_group_auto_update_type"),
	I_GROUP_AUTO_UPDATE_MAX_VALUE(150, "i_group_auto_update_max_value"),
	I_GROUP_SORT_ID(151, "i_group_sort_id"),
	I_GROUP_SHOW_NAME_IN_TREE(152, "i_group_show_name_in_tree"),

	B_VIRTUALSERVER_SERVERGROUP_LIST(153, "b_virtualserver_servergroup_list"),
	B_VIRTUALSERVER_SERVERGROUP_PERMISSION_LIST(154, "b_virtualserver_servergroup_permission_list"),
	B_VIRTUALSERVER_SERVERGROUP_CLIENT_LIST(155, "b_virtualserver_servergroup_client_list"),
	B_VIRTUALSERVER_CHANNELGROUP_LIST(156, "b_virtualserver_channelgroup_list"),
	B_VIRTUALSERVER_CHANNELGROUP_PERMISSION_LIST(157, "b_virtualserver_channelgroup_permission_list"),
	B_VIRTUALSERVER_CHANNELGROUP_CLIENT_LIST(158, "b_virtualserver_channelgroup_client_list"),
	B_VIRTUALSERVER_CLIENT_PERMISSION_LIST(159, "b_virtualserver_client_permission_list"),
	B_VIRTUALSERVER_CHANNEL_PERMISSION_LIST(160, "b_virtualserver_channel_permission_list"),
	B_VIRTUALSERVER_CHANNELCLIENT_PERMISSION_LIST(161, "b_virtualserver_channelclient_permission_list"),
	
	B_VIRTUALSERVER_SERVERGROUP_CREATE(162, "b_virtualserver_servergroup_create"),
	B_VIRTUALSERVER_CHANNELGROUP_CREATE(163, "b_virtualserver_channelgroup_create"),

	I_GROUP_MODIFY_POWER(164, "i_group_modify_power"),
	I_GROUP_NEEDED_MODIFY_POWER(165, "i_group_needed_modify_power"),
	I_GROUP_MEMBER_ADD_POWER(166, "i_group_member_add_power"),
	I_GROUP_NEEDED_MEMBER_ADD_POWER(167, "i_group_needed_member_add_power"),
	I_GROUP_MEMBER_REMOVE_POWER(168, "i_group_member_remove_power"),
	I_GROUP_NEEDED_MEMBER_REMOVE_POWER(169, "i_group_needed_member_remove_power"),
	I_PERMISSION_MODIFY_POWER(170, "i_permission_modify_power"),
	B_PERMISSION_MODIFY_POWER_IGNORE(171, "b_permission_modify_power_ignore"),

	B_VIRTUALSERVER_SERVERGROUP_DELETE(172, "b_virtualserver_servergroup_delete"),
	B_VIRTUALSERVER_CHANNELGROUP_DELETE(173, "b_virtualserver_channelgroup_delete"),

	I_CLIENT_PERMISSION_MODIFY_POWER(174, "i_client_permission_modify_power"),
	I_CLIENT_NEEDED_PERMISSION_MODIFY_POWER(175, "i_client_needed_permission_modify_power"),
	I_CLIENT_MAX_CLONES_UID(176, "i_client_max_clones_uid"),
	I_CLIENT_MAX_IDLETIME(177, "i_client_max_idletime"),
	I_CLIENT_MAX_AVATAR_FILESIZE(178, "i_client_max_avatar_filesize"),
	I_CLIENT_MAX_CHANNEL_SUBSCRIPTIONS(179, "i_client_max_channel_subscriptions"),
	B_CLIENT_IS_PRIORITY_SPEAKER(180, "b_client_is_priority_speaker"),
	B_CLIENT_SKIP_CHANNELGROUP_PERMISSIONS(181, "b_client_skip_channelgroup_permissions"),
	B_CLIENT_FORCE_PUSH_TO_TALK(182, "b_client_force_push_to_talk"),
	B_CLIENT_IGNORE_BANS(183, "b_client_ignore_bans"),
	B_CLIENT_IGNORE_ANTIFLOOD(184, "b_client_ignore_antiflood"),
	B_CLIENT_USE_RESERVED_SLOT(185, "b_client_use_reserved_slot"),
	B_CLIENT_USE_CHANNEL_COMMANDER(186, "b_client_use_channel_commander"),
	B_CLIENT_REQUEST_TALKER(187, "b_client_request_talker"),
	B_CLIENT_AVATAR_DELETE_OTHER(188, "b_client_avatar_delete_other"),
	B_CLIENT_IS_STICKY(189, "b_client_is_sticky"),
	B_CLIENT_IGNORE_STICKY(190, "b_client_ignore_sticky"),

	B_CLIENT_INFO_VIEW(191, "b_client_info_view"),
	B_CLIENT_PERMISSIONOVERVIEW_VIEW(192, "b_client_permissionoverview_view"),
	B_CLIENT_PERMISSIONOVERVIEW_OWN(193, "b_client_permissionoverview_own"),
	B_CLIENT_REMOTEADDRESS_VIEW(194, "b_client_remoteaddress_view"),
	I_CLIENT_SERVERQUERY_VIEW_POWER(195, "i_client_serverquery_view_power"),
	I_CLIENT_NEEDED_SERVERQUERY_VIEW_POWER(196, "i_client_needed_serverquery_view_power"),
	B_CLIENT_CUSTOM_INFO_VIEW(197, "b_client_custom_info_view"),

	I_CLIENT_KICK_FROM_SERVER_POWER(198, "i_client_kick_from_server_power"),
	I_CLIENT_NEEDED_KICK_FROM_SERVER_POWER(199, "i_client_needed_kick_from_server_power"),
	I_CLIENT_KICK_FROM_CHANNEL_POWER(200, "i_client_kick_from_channel_power"),
	I_CLIENT_NEEDED_KICK_FROM_CHANNEL_POWER(201, "i_client_needed_kick_from_channel_power"),
	I_CLIENT_BAN_POWER(202, "i_client_ban_power"),
	I_CLIENT_NEEDED_BAN_POWER(203, "i_client_needed_ban_power"),
	I_CLIENT_MOVE_POWER(204, "i_client_move_power"),
	I_CLIENT_NEEDED_MOVE_POWER(205, "i_client_needed_move_power"),
	I_CLIENT_COMPLAIN_POWER(206, "i_client_complain_power"),
	I_CLIENT_NEEDED_COMPLAIN_POWER(207, "i_client_needed_complain_power"),
	B_CLIENT_COMPLAIN_LIST(208, "b_client_complain_list"),
	B_CLIENT_COMPLAIN_DELETE_OWN(209, "b_client_complain_delete_own"),
	B_CLIENT_COMPLAIN_DELETE(210, "b_client_complain_delete"),
	B_CLIENT_BAN_LIST(211, "b_client_ban_list"),
	B_CLIENT_BAN_CREATE(212, "b_client_ban_create"),
	B_CLIENT_BAN_DELETE_OWN(213, "b_client_ban_delete_own"),
	B_CLIENT_BAN_DELETE(214, "b_client_ban_delete"),
	I_CLIENT_BAN_MAX_BANTIME(215, "i_client_ban_max_bantime"),

	I_CLIENT_PRIVATE_TEXTMESSAGE_POWER(216, "i_client_private_textmessage_power"),
	I_CLIENT_NEEDED_PRIVATE_TEXTMESSAGE_POWER(217, "i_client_needed_private_textmessage_power"),
	B_CLIENT_SERVER_TEXTMESSAGE_SEND(218, "b_client_server_textmessage_send"),
	B_CLIENT_CHANNEL_TEXTMESSAGE_SEND(219, "b_client_channel_textmessage_send"),
	B_CLIENT_OFFLINE_TEXTMESSAGE_SEND(220, "b_client_offline_textmessage_send"),
	I_CLIENT_TALK_POWER(221, "i_client_talk_power"),
	I_CLIENT_NEEDED_TALK_POWER(222, "i_client_needed_talk_power"),
	I_CLIENT_POKE_POWER(223, "i_client_poke_power"),
	I_CLIENT_NEEDED_POKE_POWER(224, "i_client_needed_poke_power"),
	B_CLIENT_SET_FLAG_TALKER(225, "b_client_set_flag_talker"),
	I_CLIENT_WHISPER_POWER(226, "i_client_whisper_power"),
	I_CLIENT_NEEDED_WHISPER_POWER(227, "i_client_needed_whisper_power"),

	B_CLIENT_MODIFY_DESCRIPTION(228, "b_client_modify_description"),
	B_CLIENT_MODIFY_OWN_DESCRIPTION(229, "b_client_modify_own_description"),
	B_CLIENT_MODIFY_DBPROPERTIES(230, "b_client_modify_dbproperties"),
	B_CLIENT_DELETE_DBPROPERTIES(231, "b_client_delete_dbproperties"),
	B_CLIENT_CREATE_MODIFY_SERVERQUERY_LOGIN(232, "b_client_create_modify_serverquery_login"),

	B_FT_IGNORE_PASSWORD(233, "b_ft_ignore_password"),
	B_FT_TRANSFER_LIST(234, "b_ft_transfer_list"),
	I_FT_FILE_UPLOAD_POWER(235, "i_ft_file_upload_power"),
	I_FT_NEEDED_FILE_UPLOAD_POWER(236, "i_ft_needed_file_upload_power"),
	I_FT_FILE_DOWNLOAD_POWER(237, "i_ft_file_download_power"),
	I_FT_NEEDED_FILE_DOWNLOAD_POWER(238, "i_ft_needed_file_download_power"),
	I_FT_FILE_DELETE_POWER(239, "i_ft_file_delete_power"),
	I_FT_NEEDED_FILE_DELETE_POWER(240, "i_ft_needed_file_delete_power"),
	I_FT_FILE_RENAME_POWER(241, "i_ft_file_rename_power"),
	I_FT_NEEDED_FILE_RENAME_POWER(242, "i_ft_needed_file_rename_power"),
	I_FT_FILE_BROWSE_POWER(243, "i_ft_file_browse_power"),
	I_FT_NEEDED_FILE_BROWSE_POWER(244, "i_ft_needed_file_browse_power"),
	I_FT_DIRECTORY_CREATE_POWER(245, "i_ft_directory_create_power"),
	I_FT_NEEDED_DIRECTORY_CREATE_POWER(246, "i_ft_needed_directory_create_power"),
	I_FT_QUOTA_MB_DOWNLOAD_PER_CLIENT(247, "i_ft_quota_mb_download_per_client"),
	I_FT_QUOTA_MB_UPLOAD_PER_CLIENT(248, "i_ft_quota_mb_upload_per_client"),

	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_HELP_VIEW(32769, "i_needed_modify_power_serverinstance_help_view"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_INFO_VIEW(32770, "i_needed_modify_power_serverinstance_info_view"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_VIRTUALSERVER_LIST(32771, "i_needed_modify_power_serverinstance_virtualserver_list"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_BINDING_LIST(32772, "i_needed_modify_power_serverinstance_binding_list"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_PERMISSION_LIST(32773, "i_needed_modify_power_serverinstance_permission_list"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_PERMISSION_FIND(32774, "i_needed_modify_power_serverinstance_permission_find"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CREATE(32775, "i_needed_modify_power_virtualserver_create"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_DELETE(32776, "i_needed_modify_power_virtualserver_delete"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_START_ANY(32777, "i_needed_modify_power_virtualserver_start_any"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_STOP_ANY(32778, "i_needed_modify_power_virtualserver_stop_any"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANGE_MACHINE_ID(32779, "i_needed_modify_power_virtualserver_change_machine_id"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANGE_TEMPLATE(32780, "i_needed_modify_power_virtualserver_change_template"),
	
	I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN(32781, "i_needed_modify_power_serverquery_login"),
	I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_CREATE(32782, "i_needed_modify_power_serverquery_login_create"),
	I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_DELETE(32783, "i_needed_modify_power_serverquery_login_delete"),
	I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_LIST(32784, "i_needed_modify_power_serverquery_login_list"),
	
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_TEXTMESSAGE_SEND(32785, "i_needed_modify_power_serverinstance_textmessage_send"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_LOG_VIEW(32786, "i_needed_modify_power_serverinstance_log_view"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_LOG_ADD(32787, "i_needed_modify_power_serverinstance_log_add"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_STOP(32788, "i_needed_modify_power_serverinstance_stop"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_SETTINGS(32789, "i_needed_modify_power_serverinstance_modify_settings"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_QUERYGROUP(32790, "i_needed_modify_power_serverinstance_modify_querygroup"),
	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_TEMPLATES(32791, "i_needed_modify_power_serverinstance_modify_templates"),
	
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SELECT(32792, "i_needed_modify_power_virtualserver_select"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_INFO_VIEW(32793, "i_needed_modify_power_virtualserver_info_view"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CONNECTIONINFO_VIEW(32794, "i_needed_modify_power_virtualserver_connectioninfo_view"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_LIST(32795, "i_needed_modify_power_virtualserver_channel_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_SEARCH(32796, "i_needed_modify_power_virtualserver_channel_search"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_LIST(32797, "i_needed_modify_power_virtualserver_client_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_SEARCH(32798, "i_needed_modify_power_virtualserver_client_search"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBLIST(32799, "i_needed_modify_power_virtualserver_client_dblist"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBSEARCH(32800, "i_needed_modify_power_virtualserver_client_dbsearch"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBINFO(32801, "i_needed_modify_power_virtualserver_client_dbinfo"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_PERMISSION_FIND(32802, "i_needed_modify_power_virtualserver_permission_find"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CUSTOM_SEARCH(32803, "i_needed_modify_power_virtualserver_custom_search"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_START(32804, "i_needed_modify_power_virtualserver_start"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_STOP(32805, "i_needed_modify_power_virtualserver_stop"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_LIST(32806, "i_needed_modify_power_virtualserver_token_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_ADD(32807, "i_needed_modify_power_virtualserver_token_add"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_USE(32808, "i_needed_modify_power_virtualserver_token_use"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_DELETE(32809, "i_needed_modify_power_virtualserver_token_delete"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_APIKEY_ADD(32810, "i_needed_modify_power_virtualserver_apikey_add"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_APIKEY_MANAGE(32811, "i_needed_modify_power_virtualserver_apikey_manage"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_LOG_VIEW(32812, "i_needed_modify_power_virtualserver_log_view"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_LOG_ADD(32813, "i_needed_modify_power_virtualserver_log_add"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_JOIN_IGNORE_PASSWORD(32814, "i_needed_modify_power_virtualserver_join_ignore_password"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_NOTIFY_REGISTER(32815, "i_needed_modify_power_virtualserver_notify_register"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_NOTIFY_UNREGISTER(32816, "i_needed_modify_power_virtualserver_notify_unregister"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SNAPSHOT_CREATE(32817, "i_needed_modify_power_virtualserver_snapshot_create"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SNAPSHOT_DEPLOY(32818, "i_needed_modify_power_virtualserver_snapshot_deploy"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_PERMISSION_RESET(32819, "i_needed_modify_power_virtualserver_permission_reset"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NAME(32820, "i_needed_modify_power_virtualserver_modify_name"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_WELCOMEMESSAGE(32821, "i_needed_modify_power_virtualserver_modify_welcomemessage"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_MAXCLIENTS(32822, "i_needed_modify_power_virtualserver_modify_maxclients"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_RESERVED_SLOTS(32823, "i_needed_modify_power_virtualserver_modify_reserved_slots"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PASSWORD(32824, "i_needed_modify_power_virtualserver_modify_password"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_SERVERGROUP(32825, "i_needed_modify_power_virtualserver_modify_default_servergroup"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELGROUP(32826, "i_needed_modify_power_virtualserver_modify_default_channelgroup"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELADMINGROUP(32827, "i_needed_modify_power_virtualserver_modify_default_channeladmingroup"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CHANNEL_FORCED_SILENCE(32828, "i_needed_modify_power_virtualserver_modify_channel_forced_silence"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_COMPLAIN(32829, "i_needed_modify_power_virtualserver_modify_complain"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_ANTIFLOOD(32830, "i_needed_modify_power_virtualserver_modify_antiflood"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_FT_SETTINGS(32831, "i_needed_modify_power_virtualserver_modify_ft_settings"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_FT_QUOTAS(32832, "i_needed_modify_power_virtualserver_modify_ft_quotas"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTMESSAGE(32833, "i_needed_modify_power_virtualserver_modify_hostmessage"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTBANNER(32834, "i_needed_modify_power_virtualserver_modify_hostbanner"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTBUTTON(32835, "i_needed_modify_power_virtualserver_modify_hostbutton"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PORT(32836, "i_needed_modify_power_virtualserver_modify_port"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_AUTOSTART(32837, "i_needed_modify_power_virtualserver_modify_autostart"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NEEDED_IDENTITY_SECURITY_LEVEL(32838, "i_needed_modify_power_virtualserver_modify_needed_identity_security_level"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PRIORITY_SPEAKER_DIMM_MODIFICATOR(32839, "i_needed_modify_power_virtualserver_modify_priority_speaker_dimm_modificator"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_LOG_SETTINGS(32840, "i_needed_modify_power_virtualserver_modify_log_settings"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_MIN_CLIENT_VERSION(32841, "i_needed_modify_power_virtualserver_modify_min_client_version"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_ICON_ID(32842, "i_needed_modify_power_virtualserver_modify_icon_id"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_WEBLIST(32843, "i_needed_modify_power_virtualserver_modify_weblist"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CODEC_ENCRYPTION_MODE(32844, "i_needed_modify_power_virtualserver_modify_codec_encryption_mode"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS(32845, "i_needed_modify_power_virtualserver_modify_temporary_passwords"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS_OWN(32846, "i_needed_modify_power_virtualserver_modify_temporary_passwords_own"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CHANNEL_TEMP_DELETE_DELAY_DEFAULT(32847, "i_needed_modify_power_virtualserver_modify_channel_temp_delete_delay_default"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NICKNAME(32848, "i_needed_modify_power_virtualserver_modify_nickname"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_INTEGRATIONS(32849, "i_needed_modify_power_virtualserver_modify_integrations"),
	
	I_NEEDED_MODIFY_POWER_CHANNEL_MIN_DEPTH(32850, "i_needed_modify_power_channel_min_depth"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MAX_DEPTH(32851, "i_needed_modify_power_channel_max_depth"),
	I_NEEDED_MODIFY_POWER_CHANNEL_GROUP_INHERITANCE_END(32852, "i_needed_modify_power_channel_group_inheritance_end"),
	I_NEEDED_MODIFY_POWER_CHANNEL_PERMISSION_MODIFY_POWER(32853, "i_needed_modify_power_channel_permission_modify_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_PERMISSION_MODIFY_POWER(32854, "i_needed_modify_power_channel_needed_permission_modify_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_INFO_VIEW(32855, "i_needed_modify_power_channel_info_view"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_CHILD(32856, "i_needed_modify_power_channel_create_child"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_PERMANENT(32857, "i_needed_modify_power_channel_create_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_SEMI_PERMANENT(32858, "i_needed_modify_power_channel_create_semi_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_TEMPORARY(32859, "i_needed_modify_power_channel_create_temporary"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_TOPIC(32860, "i_needed_modify_power_channel_create_with_topic"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_DESCRIPTION(32861, "i_needed_modify_power_channel_create_with_description"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_PASSWORD(32862, "i_needed_modify_power_channel_create_with_password"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_BANNER(32863, "i_needed_modify_power_channel_create_with_banner"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSVOICE(32864, "i_needed_modify_power_channel_create_modify_with_codec_opusvoice"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSMUSIC(32865, "i_needed_modify_power_channel_create_modify_with_codec_opusmusic"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_MAXQUALITY(32866, "i_needed_modify_power_channel_create_modify_with_codec_maxquality"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_LATENCY_FACTOR_MIN(32867, "i_needed_modify_power_channel_create_modify_with_codec_latency_factor_min"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_MAXCLIENTS(32868, "i_needed_modify_power_channel_create_with_maxclients"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_MAXFAMILYCLIENTS(32869, "i_needed_modify_power_channel_create_with_maxfamilyclients"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_SORTORDER(32870, "i_needed_modify_power_channel_create_with_sortorder"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_DEFAULT(32871, "i_needed_modify_power_channel_create_with_default"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_NEEDED_TALK_POWER(32872, "i_needed_modify_power_channel_create_with_needed_talk_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_FORCE_PASSWORD(32873, "i_needed_modify_power_channel_create_modify_with_force_password"),
	I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_TEMP_DELETE_DELAY(32874, "i_needed_modify_power_channel_create_modify_with_temp_delete_delay"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_PARENT(32875, "i_needed_modify_power_channel_modify_parent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_DEFAULT(32876, "i_needed_modify_power_channel_modify_make_default"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_PERMANENT(32877, "i_needed_modify_power_channel_modify_make_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_SEMI_PERMANENT(32878, "i_needed_modify_power_channel_modify_make_semi_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_TEMPORARY(32879, "i_needed_modify_power_channel_modify_make_temporary"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_NAME(32880, "i_needed_modify_power_channel_modify_name"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_TOPIC(32881, "i_needed_modify_power_channel_modify_topic"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_DESCRIPTION(32882, "i_needed_modify_power_channel_modify_description"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_PASSWORD(32883, "i_needed_modify_power_channel_modify_password"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_BANNER(32884, "i_needed_modify_power_channel_modify_banner"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC(32885, "i_needed_modify_power_channel_modify_codec"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC_QUALITY(32886, "i_needed_modify_power_channel_modify_codec_quality"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC_LATENCY_FACTOR(32887, "i_needed_modify_power_channel_modify_codec_latency_factor"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAXCLIENTS(32888, "i_needed_modify_power_channel_modify_maxclients"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAXFAMILYCLIENTS(32889, "i_needed_modify_power_channel_modify_maxfamilyclients"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_SORTORDER(32890, "i_needed_modify_power_channel_modify_sortorder"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_NEEDED_TALK_POWER(32891, "i_needed_modify_power_channel_modify_needed_talk_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_POWER(32892, "i_needed_modify_power_channel_modify_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_MODIFY_POWER(32893, "i_needed_modify_power_channel_needed_modify_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_CODEC_ENCRYPTED(32894, "i_needed_modify_power_channel_modify_make_codec_encrypted"),
	I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_TEMP_DELETE_DELAY(32895, "i_needed_modify_power_channel_modify_temp_delete_delay"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_PERMANENT(32896, "i_needed_modify_power_channel_delete_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_SEMI_PERMANENT(32897, "i_needed_modify_power_channel_delete_semi_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_TEMPORARY(32898, "i_needed_modify_power_channel_delete_temporary"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_FLAG_FORCE(32899, "i_needed_modify_power_channel_delete_flag_force"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_POWER(32900, "i_needed_modify_power_channel_delete_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_DELETE_POWER(32901, "i_needed_modify_power_channel_needed_delete_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_PERMANENT(32902, "i_needed_modify_power_channel_join_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_SEMI_PERMANENT(32903, "i_needed_modify_power_channel_join_semi_permanent"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_TEMPORARY(32904, "i_needed_modify_power_channel_join_temporary"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_IGNORE_PASSWORD(32905, "i_needed_modify_power_channel_join_ignore_password"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_IGNORE_MAXCLIENTS(32906, "i_needed_modify_power_channel_join_ignore_maxclients"),
	I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_POWER(32907, "i_needed_modify_power_channel_join_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_JOIN_POWER(32908, "i_needed_modify_power_channel_needed_join_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_SUBSCRIBE_POWER(32909, "i_needed_modify_power_channel_subscribe_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_SUBSCRIBE_POWER(32910, "i_needed_modify_power_channel_needed_subscribe_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_DESCRIPTION_VIEW_POWER(32911, "i_needed_modify_power_channel_description_view_power"),
	I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_DESCRIPTION_VIEW_POWER(32912, "i_needed_modify_power_channel_needed_description_view_power"), 
	
	I_NEEDED_MODIFY_POWER_ICON_ID(32913, "i_needed_modify_power_icon_id"),
	I_NEEDED_MODIFY_POWER_MAX_ICON_FILESIZE(32914, "i_needed_modify_power_max_icon_filesize"),
	I_NEEDED_MODIFY_POWER_ICON_MANAGE(32915, "i_needed_modify_power_icon_manage"),
	
	I_NEEDED_MODIFY_POWER_GROUP_IS_PERMANENT(32916, "i_needed_modify_power_group_is_permanent"),
	I_NEEDED_MODIFY_POWER_GROUP_AUTO_UPDATE_TYPE(32917, "i_needed_modify_power_group_auto_update_type"),
	I_NEEDED_MODIFY_POWER_GROUP_AUTO_UPDATE_MAX_VALUE(32918, "i_needed_modify_power_group_auto_update_max_value"),
	I_NEEDED_MODIFY_POWER_GROUP_SORT_ID(32919, "i_needed_modify_power_group_sort_id"),
	I_NEEDED_MODIFY_POWER_GROUP_SHOW_NAME_IN_TREE(32920, "i_needed_modify_power_group_show_name_in_tree"),
	
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_LIST(32921, "i_needed_modify_power_virtualserver_servergroup_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_PERMISSION_LIST(32922, "i_needed_modify_power_virtualserver_servergroup_permission_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_CLIENT_LIST(32923, "i_needed_modify_power_virtualserver_servergroup_client_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_LIST(32924, "i_needed_modify_power_virtualserver_channelgroup_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_PERMISSION_LIST(32925, "i_needed_modify_power_virtualserver_channelgroup_permission_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_CLIENT_LIST(32926, "i_needed_modify_power_virtualserver_channelgroup_client_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_PERMISSION_LIST(32927, "i_needed_modify_power_virtualserver_client_permission_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_PERMISSION_LIST(32928, "i_needed_modify_power_virtualserver_channel_permission_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELCLIENT_PERMISSION_LIST(32929, "i_needed_modify_power_virtualserver_channelclient_permission_list"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_CREATE(32930, "i_needed_modify_power_virtualserver_servergroup_create"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_CREATE(32931, "i_needed_modify_power_virtualserver_channelgroup_create"),
	
	I_NEEDED_MODIFY_POWER_GROUP_MODIFY_POWER(32932, "i_needed_modify_power_group_modify_power"),
	I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MODIFY_POWER(32933, "i_needed_modify_power_group_needed_modify_power"),
	I_NEEDED_MODIFY_POWER_GROUP_MEMBER_ADD_POWER(32934, "i_needed_modify_power_group_member_add_power"),
	I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MEMBER_ADD_POWER(32935, "i_needed_modify_power_group_needed_member_add_power"),
	I_NEEDED_MODIFY_POWER_GROUP_MEMBER_REMOVE_POWER(32936, "i_needed_modify_power_group_member_remove_power"),
	I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MEMBER_REMOVE_POWER(32937, "i_needed_modify_power_group_needed_member_remove_power"),
	
	I_NEEDED_MODIFY_POWER_PERMISSION_MODIFY_POWER(32938, "i_needed_modify_power_permission_modify_power"),
	I_NEEDED_MODIFY_POWER_PERMISSION_MODIFY_POWER_IGNORE(32939, "i_needed_modify_power_permission_modify_power_ignore"),
	
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_DELETE(32940, "i_needed_modify_power_virtualserver_servergroup_delete"),
	I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_DELETE(32941, "i_needed_modify_power_virtualserver_channelgroup_delete"),
	
	I_NEEDED_MODIFY_POWER_CLIENT_PERMISSION_MODIFY_POWER(32942, "i_needed_modify_power_client_permission_modify_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_PERMISSION_MODIFY_POWER(32943, "i_needed_modify_power_client_needed_permission_modify_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_MAX_CLONES_UID(32944, "i_needed_modify_power_client_max_clones_uid"),
	I_NEEDED_MODIFY_POWER_CLIENT_MAX_IDLETIME(32945, "i_needed_modify_power_client_max_idletime"),
	I_NEEDED_MODIFY_POWER_CLIENT_MAX_AVATAR_FILESIZE(32946, "i_needed_modify_power_client_max_avatar_filesize"),
	I_NEEDED_MODIFY_POWER_CLIENT_MAX_CHANNEL_SUBSCRIPTIONS(32947, "i_needed_modify_power_client_max_channel_subscriptions"),
	I_NEEDED_MODIFY_POWER_CLIENT_IS_PRIORITY_SPEAKER(32948, "i_needed_modify_power_client_is_priority_speaker"),
	I_NEEDED_MODIFY_POWER_CLIENT_SKIP_CHANNELGROUP_PERMISSIONS(32949, "i_needed_modify_power_client_skip_channelgroup_permissions"),
	I_NEEDED_MODIFY_POWER_CLIENT_FORCE_PUSH_TO_TALK(32950, "i_needed_modify_power_client_force_push_to_talk"),
	I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_BANS(32951, "i_needed_modify_power_client_ignore_bans"),
	I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_ANTIFLOOD(32952, "i_needed_modify_power_client_ignore_antiflood"),
	I_NEEDED_MODIFY_POWER_CLIENT_USE_RESERVED_SLOT(32953, "i_needed_modify_power_client_use_reserved_slot"),
	I_NEEDED_MODIFY_POWER_CLIENT_USE_CHANNEL_COMMANDER(32954, "i_needed_modify_power_client_use_channel_commander"),
	I_NEEDED_MODIFY_POWER_CLIENT_REQUEST_TALKER(32955, "i_needed_modify_power_client_request_talker"),
	I_NEEDED_MODIFY_POWER_CLIENT_AVATAR_DELETE_OTHER(32956, "i_needed_modify_power_client_avatar_delete_other"),
	I_NEEDED_MODIFY_POWER_CLIENT_IS_STICKY(32957, "i_needed_modify_power_client_is_sticky"),
	I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_STICKY(32958, "i_needed_modify_power_client_ignore_sticky"),
	I_NEEDED_MODIFY_POWER_CLIENT_INFO_VIEW(32959, "i_needed_modify_power_client_info_view"),
	I_NEEDED_MODIFY_POWER_CLIENT_PERMISSIONOVERVIEW_VIEW(32960, "i_needed_modify_power_client_permissionoverview_view"),
	I_NEEDED_MODIFY_POWER_CLIENT_PERMISSIONOVERVIEW_OWN(32961, "i_needed_modify_power_client_permissionoverview_own"),
	I_NEEDED_MODIFY_POWER_CLIENT_REMOTEADDRESS_VIEW(32962, "i_needed_modify_power_client_remoteaddress_view"),
	I_NEEDED_MODIFY_POWER_CLIENT_SERVERQUERY_VIEW_POWER(32963, "i_needed_modify_power_client_serverquery_view_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_SERVERQUERY_VIEW_POWER(32964, "i_needed_modify_power_client_needed_serverquery_view_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_CUSTOM_INFO_VIEW(32965, "i_needed_modify_power_client_custom_info_view"),
	I_NEEDED_MODIFY_POWER_CLIENT_KICK_FROM_SERVER_POWER(32966, "i_needed_modify_power_client_kick_from_server_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_KICK_FROM_SERVER_POWER(32967, "i_needed_modify_power_client_needed_kick_from_server_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_KICK_FROM_CHANNEL_POWER(32968, "i_needed_modify_power_client_kick_from_channel_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_KICK_FROM_CHANNEL_POWER(32969, "i_needed_modify_power_client_needed_kick_from_channel_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_POWER(32970, "i_needed_modify_power_client_ban_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_BAN_POWER(32971, "i_needed_modify_power_client_needed_ban_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_MOVE_POWER(32972, "i_needed_modify_power_client_move_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_MOVE_POWER(32973, "i_needed_modify_power_client_needed_move_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_POWER(32974, "i_needed_modify_power_client_complain_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_COMPLAIN_POWER(32975, "i_needed_modify_power_client_needed_complain_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_LIST(32976, "i_needed_modify_power_client_complain_list"),
	I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_DELETE_OWN(32977, "i_needed_modify_power_client_complain_delete_own"),
	I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_DELETE(32978, "i_needed_modify_power_client_complain_delete"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_LIST(32979, "i_needed_modify_power_client_ban_list"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_CREATE(32980, "i_needed_modify_power_client_ban_create"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_DELETE_OWN(32981, "i_needed_modify_power_client_ban_delete_own"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_DELETE(32982, "i_needed_modify_power_client_ban_delete"),
	I_NEEDED_MODIFY_POWER_CLIENT_BAN_MAX_BANTIME(32983, "i_needed_modify_power_client_ban_max_bantime"),
	I_NEEDED_MODIFY_POWER_CLIENT_PRIVATE_TEXTMESSAGE_POWER(32984, "i_needed_modify_power_client_private_textmessage_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_PRIVATE_TEXTMESSAGE_POWER(32985, "i_needed_modify_power_client_needed_private_textmessage_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_SERVER_TEXTMESSAGE_SEND(32986, "i_needed_modify_power_client_server_textmessage_send"),
	I_NEEDED_MODIFY_POWER_CLIENT_CHANNEL_TEXTMESSAGE_SEND(32987, "i_needed_modify_power_client_channel_textmessage_send"),
	I_NEEDED_MODIFY_POWER_CLIENT_OFFLINE_TEXTMESSAGE_SEND(32988, "i_needed_modify_power_client_offline_textmessage_send"),
	I_NEEDED_MODIFY_POWER_CLIENT_TALK_POWER(32989, "i_needed_modify_power_client_talk_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_TALK_POWER(32990, "i_needed_modify_power_client_needed_talk_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_POKE_POWER(32991, "i_needed_modify_power_client_poke_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_POKE_POWER(32992, "i_needed_modify_power_client_needed_poke_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_SET_FLAG_TALKER(32993, "i_needed_modify_power_client_set_flag_talker"),
	I_NEEDED_MODIFY_POWER_CLIENT_WHISPER_POWER(32994, "i_needed_modify_power_client_whisper_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_WHISPER_POWER(32995, "i_needed_modify_power_client_needed_whisper_power"),
	I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_DESCRIPTION(32996, "i_needed_modify_power_client_modify_description"),
	I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_OWN_DESCRIPTION(32997, "i_needed_modify_power_client_modify_own_description"),
	I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_DBPROPERTIES(32998, "i_needed_modify_power_client_modify_dbproperties"),
	I_NEEDED_MODIFY_POWER_CLIENT_DELETE_DBPROPERTIES(32999, "i_needed_modify_power_client_delete_dbproperties"),
	I_NEEDED_MODIFY_POWER_CLIENT_CREATE_MODIFY_SERVERQUERY_LOGIN(33000, "i_needed_modify_power_client_create_modify_serverquery_login"),
	
	I_NEEDED_MODIFY_POWER_FT_IGNORE_PASSWORD(33001, "i_needed_modify_power_ft_ignore_password"),
	I_NEEDED_MODIFY_POWER_FT_TRANSFER_LIST(33002, "i_needed_modify_power_ft_transfer_list"),
	I_NEEDED_MODIFY_POWER_FT_FILE_UPLOAD_POWER(33003, "i_needed_modify_power_ft_file_upload_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_UPLOAD_POWER(33004, "i_needed_modify_power_ft_needed_file_upload_power"),
	I_NEEDED_MODIFY_POWER_FT_FILE_DOWNLOAD_POWER(33005, "i_needed_modify_power_ft_file_download_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_DOWNLOAD_POWER(33006, "i_needed_modify_power_ft_needed_file_download_power"),
	I_NEEDED_MODIFY_POWER_FT_FILE_DELETE_POWER(33007, "i_needed_modify_power_ft_file_delete_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_DELETE_POWER(33008, "i_needed_modify_power_ft_needed_file_delete_power"),
	I_NEEDED_MODIFY_POWER_FT_FILE_RENAME_POWER(33009, "i_needed_modify_power_ft_file_rename_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_RENAME_POWER(33010, "i_needed_modify_power_ft_needed_file_rename_power"),
	I_NEEDED_MODIFY_POWER_FT_FILE_BROWSE_POWER(33011, "i_needed_modify_power_ft_file_browse_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_BROWSE_POWER(33012, "i_needed_modify_power_ft_needed_file_browse_power"),
	I_NEEDED_MODIFY_POWER_FT_DIRECTORY_CREATE_POWER(33013, "i_needed_modify_power_ft_directory_create_power"),
	I_NEEDED_MODIFY_POWER_FT_NEEDED_DIRECTORY_CREATE_POWER(33014, "i_needed_modify_power_ft_needed_directory_create_power"),
	I_NEEDED_MODIFY_POWER_FT_QUOTA_MB_DOWNLOAD_PER_CLIENT(33015, "i_needed_modify_power_ft_quota_mb_download_per_client"),
	I_NEEDED_MODIFY_POWER_FT_QUOTA_MB_UPLOAD_PER_CLIENT(33016, "i_needed_modify_power_ft_quota_mb_upload_per_client");

	private int value = 0;
	private String name = "";

	private TSPermission(int value, String name) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}

	private static List<Integer> getPermissions(int min, int max) {
		List<Integer> resultList = new ArrayList<>();
		for (TSPermission perm : values()) {
			int value = perm.getValue();
			if (value >= min && value <= max) {
				resultList.add(perm.getValue());
			}
		}
		return resultList;
	}

	public static void checkQueryFullPermissions(Set<Integer> queryPerms) {
		for (TSPermission perms : values()) {
			if (!queryPerms.contains(perms.getValue())) {
				System.out.println("Did not find Permission. ID: " + perms.getValue());
			}
		}
	}

	public static void checkQueryConnectPermissions(Set<Integer> queryPerms) {
		if (!queryPerms.contains(B_SERVERQUERY_LOGIN.getValue()))
			System.out.println("Did not find Login Permission. ID: " + B_SERVERQUERY_LOGIN.getValue());
		if (!queryPerms.contains(B_VIRTUALSERVER_SELECT.getValue()))
			System.out.println("Did not find Use Permission. ID: " + B_VIRTUALSERVER_SELECT.getValue());
		if (!queryPerms.contains(B_CLIENT_IGNORE_ANTIFLOOD.getValue()))
			System.out.println("Did not find Ignore-Antiflood Permission. ID: " + B_CLIENT_IGNORE_ANTIFLOOD.getValue());
	}

	public static void checkQueryInformationPermissions(Set<Integer> queryPerms) {
		getPermissions(0, 6).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Global Information Permission. ID: " + infoPerm);
		});

		getPermissions(24, 35).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Virtual Server Information Permission. ID: " + infoPerm);
		});

		if (!queryPerms.contains(B_CHANNEL_INFO_VIEW.getValue())) {
			System.out.println("Did not find one Channel Information Permission. ID: " + B_CHANNEL_INFO_VIEW.getValue());
		}

		getPermissions(153, 161).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Group Information Permission. ID: " + infoPerm);
		});

		getPermissions(191, 197).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Client Information Permission. ID: " + infoPerm);
		});
	}

	public static void checkQueryActionPermissions(Set<Integer> queryPerms) {
		getPermissions(7, 12).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Virtual Server management Permission. ID: " + infoPerm);
		});

		getPermissions(36, 51).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Virtual Server action Permission. ID: " + infoPerm);
		});

		getPermissions(52, 81).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Virtual Server modify Permission. ID: " + infoPerm);
		});

		getPermissions(88, 106).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Channel create Permission. ID: " + infoPerm);
		});

		getPermissions(107, 127).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Channel modify Permission. ID: " + infoPerm);
		});

		getPermissions(162, 163).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Group create Permission. ID: " + infoPerm);
		});

		getPermissions(164, 171).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Group administrate Permission. ID: " + infoPerm);
		});

		getPermissions(172, 173).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Group delete Permission. ID: " + infoPerm);
		});

		getPermissions(174, 190).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Client Permission modify Permission. ID: " + infoPerm);
		});

		getPermissions(190, 215).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Client administrate Permission. ID: " + infoPerm);
		});

		getPermissions(216, 227).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Client text Permission. ID: " + infoPerm);
		});

		getPermissions(228, 232).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Client modify Permission. ID: " + infoPerm);
		});

		getPermissions(233, 248).forEach(infoPerm -> {
			if (!queryPerms.contains(infoPerm))
				System.out.println("Did not find one Filetransfer administrate Permission. ID: " + infoPerm);
		});
	}

}
