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

	B_SERVERINSTANCE_HELP_VIEW(1), B_SERVERINSTANCE_INFO_VIEW(2), B_SERVERINSTANCE_VIRTUALSERVER_LIST(3), B_SERVERINSTANCE_BINDING_LIST(4), B_SERVERINSTANCE_PERMISSION_LIST(
			5), B_SERVERINSTANCE_PERMISSION_FIND(6),

	B_VIRTUALSERVER_CREATE(7), B_VIRTUALSERVER_DELETE(8), B_VIRTUALSERVER_START_ANY(9), B_VIRTUALSERVER_STOP_ANY(10), B_VIRTUALSERVER_CHANGE_MACHINE_ID(11), B_VIRTUALSERVER_CHANGE_TEMPLATE(12),

	B_SERVERQUERY_LOGIN(13), B_SERVERQUERY_LOGIN_CREATE(14), B_SERVERQUERY_LOGIN_DELETE(15), B_SERVERQUERY_LOGIN_LIST(16), B_SERVERINSTANCE_TEXTMESSAGE_SEND(17), B_SERVERINSTANCE_LOG_VIEW(
			18), B_SERVERINSTANCE_LOG_ADD(19), B_SERVERINSTANCE_STOP(20),

	B_SERVERINSTANCE_MODIFY_SETTINGS(21), B_SERVERINSTANCE_MODIFY_QUERYGROUP(22), B_SERVERINSTANCE_MODIFY_TEMPLATES(23),

	B_VIRTUALSERVER_SELECT(24), B_VIRTUALSERVER_INFO_VIEW(25), B_VIRTUALSERVER_CONNECTIONINFO_VIEW(26), B_VIRTUALSERVER_CHANNEL_LIST(27), B_VIRTUALSERVER_CHANNEL_SEARCH(
			28), B_VIRTUALSERVER_CLIENT_LIST(29), B_VIRTUALSERVER_CLIENT_SEARCH(30), B_VIRTUALSERVER_CLIENT_DBLIST(
					31), B_VIRTUALSERVER_CLIENT_DBSEARCH(32), B_VIRTUALSERVER_CLIENT_DBINFO(33), B_VIRTUALSERVER_PERMISSION_FIND(34), B_VIRTUALSERVER_CUSTOM_SEARCH(35),

	B_VIRTUALSERVER_START(36), B_VIRTUALSERVER_STOP(37), B_VIRTUALSERVER_TOKEN_LIST(38), B_VIRTUALSERVER_TOKEN_ADD(39), B_VIRTUALSERVER_TOKEN_USE(40), B_VIRTUALSERVER_TOKEN_DELETE(
			41), B_VIRTUALSERVER_APIKEY_ADD(42), B_VIRTUALSERVER_APIKEY_MANAGE(43), B_VIRTUALSERVER_LOG_VIEW(44), B_VIRTUALSERVER_LOG_ADD(
					45), B_VIRTUALSERVER_JOIN_IGNORE_PASSWORD(46), B_VIRTUALSERVER_NOTIFY_REGISTER(
							47), B_VIRTUALSERVER_NOTIFY_UNREGISTER(48), B_VIRTUALSERVER_SNAPSHOT_CREATE(49), B_VIRTUALSERVER_SNAPSHOT_DEPLOY(50), B_VIRTUALSERVER_PERMISSION_RESET(51),

	B_VIRTUALSERVER_MODIFY_NAME(52), B_VIRTUALSERVER_MODIFY_WELCOMEMESSAGE(53), B_VIRTUALSERVER_MODIFY_MAXCLIENTS(54), B_VIRTUALSERVER_MODIFY_RESERVED_SLOTS(55), B_VIRTUALSERVER_MODIFY_PASSWORD(
			56), B_VIRTUALSERVER_MODIFY_DEFAULT_SERVERGROUP(57), B_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELGROUP(58), B_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELADMINGROUP(
					59), B_VIRTUALSERVER_MODIFY_CHANNEL_FORCED_SILENCE(60), B_VIRTUALSERVER_MODIFY_COMPLAIN(61), B_VIRTUALSERVER_MODIFY_ANTIFLOOD(62), B_VIRTUALSERVER_MODIFY_FT_SETTINGS(
							63), B_VIRTUALSERVER_MODIFY_FT_QUOTAS(64), B_VIRTUALSERVER_MODIFY_HOSTMESSAGE(65), B_VIRTUALSERVER_MODIFY_HOSTBANNER(
									66), B_VIRTUALSERVER_MODIFY_HOSTBUTTON(67), B_VIRTUALSERVER_MODIFY_PORT(68), B_VIRTUALSERVER_MODIFY_AUTOSTART(
											69), B_VIRTUALSERVER_MODIFY_NEEDED_IDENTITY_SECURITY_LEVEL(70), B_VIRTUALSERVER_MODIFY_PRIORITY_SPEAKER_DIMM_MODIFICATOR(
													71), B_VIRTUALSERVER_MODIFY_LOG_SETTINGS(72), B_VIRTUALSERVER_MODIFY_MIN_CLIENT_VERSION(73), B_VIRTUALSERVER_MODIFY_ICON_ID(
															74), B_VIRTUALSERVER_MODIFY_WEBLIST(75), B_VIRTUALSERVER_MODIFY_CODEC_ENCRYPTION_MODE(76), B_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS(
																	77), B_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS_OWN(78), B_VIRTUALSERVER_MODIFY_CHANNEL_TEMP_DELETE_DELAY_DEFAULT(
																			79), B_VIRTUALSERVER_MODIFY_NICKNAME(80), B_VIRTUALSERVER_MODIFY_INTEGRATIONS(81),

	I_CHANNEL_MIN_DEPTH(82), I_CHANNEL_MAX_DEPTH(83), B_CHANNEL_GROUP_INHERITANCE_END(84), I_CHANNEL_PERMISSION_MODIFY_POWER(85), I_CHANNEL_NEEDED_PERMISSION_MODIFY_POWER(86),

	B_CHANNEL_INFO_VIEW(87),

	B_CHANNEL_CREATE_CHILD(88), B_CHANNEL_CREATE_PERMANENT(89), B_CHANNEL_CREATE_SEMI_PERMANENT(90), B_CHANNEL_CREATE_TEMPORARY(91), B_CHANNEL_CREATE_WITH_TOPIC(
			92), B_CHANNEL_CREATE_WITH_DESCRIPTION(93), B_CHANNEL_CREATE_WITH_PASSWORD(94), B_CHANNEL_CREATE_WITH_BANNER(95), B_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSVOICE(
					96), B_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSMUSIC(97), I_CHANNEL_CREATE_MODIFY_WITH_CODEC_MAXQUALITY(98), I_CHANNEL_CREATE_MODIFY_WITH_CODEC_LATENCY_FACTOR_MIN(
							99), B_CHANNEL_CREATE_WITH_MAXCLIENTS(100), B_CHANNEL_CREATE_WITH_MAXFAMILYCLIENTS(101), B_CHANNEL_CREATE_WITH_SORTORDER(102), B_CHANNEL_CREATE_WITH_DEFAULT(
									103), B_CHANNEL_CREATE_WITH_NEEDED_TALK_POWER(104), B_CHANNEL_CREATE_MODIFY_WITH_FORCE_PASSWORD(105), I_CHANNEL_CREATE_MODIFY_WITH_TEMP_DELETE_DELAY(106),

	B_CHANNEL_MODIFY_PARENT(107), B_CHANNEL_MODIFY_MAKE_DEFAULT(108), B_CHANNEL_MODIFY_MAKE_PERMANENT(109), B_CHANNEL_MODIFY_MAKE_SEMI_PERMANENT(110), B_CHANNEL_MODIFY_MAKE_TEMPORARY(
			111), B_CHANNEL_MODIFY_NAME(112), B_CHANNEL_MODIFY_TOPIC(113), B_CHANNEL_MODIFY_DESCRIPTION(114), B_CHANNEL_MODIFY_PASSWORD(115), B_CHANNEL_MODIFY_BANNER(116), B_CHANNEL_MODIFY_CODEC(
					117), B_CHANNEL_MODIFY_CODEC_QUALITY(118), B_CHANNEL_MODIFY_CODEC_LATENCY_FACTOR(119), B_CHANNEL_MODIFY_MAXCLIENTS(120), B_CHANNEL_MODIFY_MAXFAMILYCLIENTS(
							121), B_CHANNEL_MODIFY_SORTORDER(122), B_CHANNEL_MODIFY_NEEDED_TALK_POWER(
									123), I_CHANNEL_MODIFY_POWER(124), I_CHANNEL_NEEDED_MODIFY_POWER(125), B_CHANNEL_MODIFY_MAKE_CODEC_ENCRYPTED(126), B_CHANNEL_MODIFY_TEMP_DELETE_DELAY(127),

	B_CHANNEL_DELETE_PERMANENT(128), B_CHANNEL_DELETE_SEMI_PERMANENT(129), B_CHANNEL_DELETE_TEMPORARY(130), B_CHANNEL_DELETE_FLAG_FORCE(131), I_CHANNEL_DELETE_POWER(
			132), I_CHANNEL_NEEDED_DELETE_POWER(133),

	B_CHANNEL_JOIN_PERMANENT(134), B_CHANNEL_JOIN_SEMI_PERMANENT(135), B_CHANNEL_JOIN_TEMPORARY(136), B_CHANNEL_JOIN_IGNORE_PASSWORD(137), B_CHANNEL_JOIN_IGNORE_MAXCLIENTS(
			138), I_CHANNEL_JOIN_POWER(139), I_CHANNEL_NEEDED_JOIN_POWER(
					140), I_CHANNEL_SUBSCRIBE_POWER(141), I_CHANNEL_NEEDED_SUBSCRIBE_POWER(142), I_CHANNEL_DESCRIPTION_VIEW_POWER(143), I_CHANNEL_NEEDED_DESCRIPTION_VIEW_POWER(144),

	I_ICON_ID(145), I_MAX_ICON_FILESIZE(146), B_ICON_MANAGE(147), B_GROUP_IS_PERMANENT(148), I_GROUP_AUTO_UPDATE_TYPE(149), I_GROUP_AUTO_UPDATE_MAX_VALUE(150), I_GROUP_SORT_ID(
			151), I_GROUP_SHOW_NAME_IN_TREE(152),

	B_VIRTUALSERVER_SERVERGROUP_LIST(153), B_VIRTUALSERVER_SERVERGROUP_PERMISSION_LIST(154), B_VIRTUALSERVER_SERVERGROUP_CLIENT_LIST(155), B_VIRTUALSERVER_CHANNELGROUP_LIST(
			156), B_VIRTUALSERVER_CHANNELGROUP_PERMISSION_LIST(157), B_VIRTUALSERVER_CHANNELGROUP_CLIENT_LIST(
					158), B_VIRTUALSERVER_CLIENT_PERMISSION_LIST(159), B_VIRTUALSERVER_CHANNEL_PERMISSION_LIST(160), B_VIRTUALSERVER_CHANNELCLIENT_PERMISSION_LIST(161),

	B_VIRTUALSERVER_SERVERGROUP_CREATE(162), B_VIRTUALSERVER_CHANNELGROUP_CREATE(163),

	I_GROUP_MODIFY_POWER(164), I_GROUP_NEEDED_MODIFY_POWER(165), I_GROUP_MEMBER_ADD_POWER(166), I_GROUP_NEEDED_MEMBER_ADD_POWER(167), I_GROUP_MEMBER_REMOVE_POWER(
			168), I_GROUP_NEEDED_MEMBER_REMOVE_POWER(169), I_PERMISSION_MODIFY_POWER(170), B_PERMISSION_MODIFY_POWER_IGNORE(171),

	B_VIRTUALSERVER_SERVERGROUP_DELETE(172), B_VIRTUALSERVER_CHANNELGROUP_DELETE(173),

	I_CLIENT_PERMISSION_MODIFY_POWER(174), I_CLIENT_NEEDED_PERMISSION_MODIFY_POWER(175), I_CLIENT_MAX_CLONES_UID(176), I_CLIENT_MAX_IDLETIME(177), I_CLIENT_MAX_AVATAR_FILESIZE(
			178), I_CLIENT_MAX_CHANNEL_SUBSCRIPTIONS(179), B_CLIENT_IS_PRIORITY_SPEAKER(180), B_CLIENT_SKIP_CHANNELGROUP_PERMISSIONS(181), B_CLIENT_FORCE_PUSH_TO_TALK(182), B_CLIENT_IGNORE_BANS(
					183), B_CLIENT_IGNORE_ANTIFLOOD(184), B_CLIENT_USE_RESERVED_SLOT(
							185), B_CLIENT_USE_CHANNEL_COMMANDER(186), B_CLIENT_REQUEST_TALKER(187), B_CLIENT_AVATAR_DELETE_OTHER(188), B_CLIENT_IS_STICKY(189), B_CLIENT_IGNORE_STICKY(190),

	B_CLIENT_INFO_VIEW(191), B_CLIENT_PERMISSIONOVERVIEW_VIEW(192), B_CLIENT_PERMISSIONOVERVIEW_OWN(193), B_CLIENT_REMOTEADDRESS_VIEW(194), I_CLIENT_SERVERQUERY_VIEW_POWER(
			195), I_CLIENT_NEEDED_SERVERQUERY_VIEW_POWER(196), B_CLIENT_CUSTOM_INFO_VIEW(197),

	I_CLIENT_KICK_FROM_SERVER_POWER(198), I_CLIENT_NEEDED_KICK_FROM_SERVER_POWER(199), I_CLIENT_KICK_FROM_CHANNEL_POWER(200), I_CLIENT_NEEDED_KICK_FROM_CHANNEL_POWER(201), I_CLIENT_BAN_POWER(
			202), I_CLIENT_NEEDED_BAN_POWER(203), I_CLIENT_MOVE_POWER(204), I_CLIENT_NEEDED_MOVE_POWER(205), I_CLIENT_COMPLAIN_POWER(206), I_CLIENT_NEEDED_COMPLAIN_POWER(
					207), B_CLIENT_COMPLAIN_LIST(208), B_CLIENT_COMPLAIN_DELETE_OWN(209), B_CLIENT_COMPLAIN_DELETE(
							210), B_CLIENT_BAN_LIST(211), B_CLIENT_BAN_CREATE(212), B_CLIENT_BAN_DELETE_OWN(213), B_CLIENT_BAN_DELETE(214), I_CLIENT_BAN_MAX_BANTIME(215),

	I_CLIENT_PRIVATE_TEXTMESSAGE_POWER(216), I_CLIENT_NEEDED_PRIVATE_TEXTMESSAGE_POWER(217), B_CLIENT_SERVER_TEXTMESSAGE_SEND(218), B_CLIENT_CHANNEL_TEXTMESSAGE_SEND(
			219), B_CLIENT_OFFLINE_TEXTMESSAGE_SEND(220), I_CLIENT_TALK_POWER(221), I_CLIENT_NEEDED_TALK_POWER(
					222), I_CLIENT_POKE_POWER(223), I_CLIENT_NEEDED_POKE_POWER(224), B_CLIENT_SET_FLAG_TALKER(225), I_CLIENT_WHISPER_POWER(226), I_CLIENT_NEEDED_WHISPER_POWER(227),

	B_CLIENT_MODIFY_DESCRIPTION(228), B_CLIENT_MODIFY_OWN_DESCRIPTION(229), B_CLIENT_MODIFY_DBPROPERTIES(230), B_CLIENT_DELETE_DBPROPERTIES(231), B_CLIENT_CREATE_MODIFY_SERVERQUERY_LOGIN(232),

	B_FT_IGNORE_PASSWORD(233), B_FT_TRANSFER_LIST(234), I_FT_FILE_UPLOAD_POWER(235), I_FT_NEEDED_FILE_UPLOAD_POWER(236), I_FT_FILE_DOWNLOAD_POWER(237), I_FT_NEEDED_FILE_DOWNLOAD_POWER(
			238), I_FT_FILE_DELETE_POWER(239), I_FT_NEEDED_FILE_DELETE_POWER(240), I_FT_FILE_RENAME_POWER(241), I_FT_NEEDED_FILE_RENAME_POWER(
					242), I_FT_FILE_BROWSE_POWER(243), I_FT_NEEDED_FILE_BROWSE_POWER(
							244), I_FT_DIRECTORY_CREATE_POWER(245), I_FT_NEEDED_DIRECTORY_CREATE_POWER(246), I_FT_QUOTA_MB_DOWNLOAD_PER_CLIENT(247), I_FT_QUOTA_MB_UPLOAD_PER_CLIENT(248),

	I_NEEDED_MODIFY_POWER_SERVERINSTANCE_HELP_VIEW(32769), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_INFO_VIEW(32770), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_VIRTUALSERVER_LIST(
			32771), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_BINDING_LIST(32772), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_PERMISSION_LIST(32773), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_PERMISSION_FIND(
					32774), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CREATE(32775), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_DELETE(32776), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_START_ANY(
							32777), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_STOP_ANY(32778), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANGE_MACHINE_ID(
									32779), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANGE_TEMPLATE(32780), I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN(
											32781), I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_CREATE(32782), I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_DELETE(
													32783), I_NEEDED_MODIFY_POWER_SERVERQUERY_LOGIN_LIST(32784), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_TEXTMESSAGE_SEND(
															32785), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_LOG_VIEW(32786), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_LOG_ADD(
																	32787), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_STOP(32788), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_SETTINGS(
																			32789), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_QUERYGROUP(
																					32790), I_NEEDED_MODIFY_POWER_SERVERINSTANCE_MODIFY_TEMPLATES(
																							32791), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SELECT(
																									32792), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_INFO_VIEW(
																											32793), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CONNECTIONINFO_VIEW(
																													32794), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_LIST(
																															32795), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_SEARCH(
																																	32796), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_LIST(
																																			32797), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_SEARCH(
																																					32798), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBLIST(
																																							32799), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBSEARCH(
																																									32800), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_DBINFO(
																																											32801), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_PERMISSION_FIND(
																																													32802), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CUSTOM_SEARCH(
																																															32803), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_START(
																																																	32804), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_STOP(
																																																			32805), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_LIST(
																																																					32806), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_ADD(
																																																							32807), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_USE(
																																																									32808), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_TOKEN_DELETE(
																																																											32809), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_APIKEY_ADD(
																																																													32810), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_APIKEY_MANAGE(
																																																															32811), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_LOG_VIEW(
																																																																	32812), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_LOG_ADD(
																																																																			32813), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_JOIN_IGNORE_PASSWORD(
																																																																					32814), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_NOTIFY_REGISTER(
																																																																							32815), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_NOTIFY_UNREGISTER(
																																																																									32816), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SNAPSHOT_CREATE(
																																																																											32817), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SNAPSHOT_DEPLOY(
																																																																													32818), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_PERMISSION_RESET(
																																																																															32819), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NAME(
																																																																																	32820), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_WELCOMEMESSAGE(
																																																																																			32821), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_MAXCLIENTS(
																																																																																					32822), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_RESERVED_SLOTS(
																																																																																							32823), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PASSWORD(
																																																																																									32824), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_SERVERGROUP(
																																																																																											32825), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELGROUP(
																																																																																													32826), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_DEFAULT_CHANNELADMINGROUP(
																																																																																															32827), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CHANNEL_FORCED_SILENCE(
																																																																																																	32828), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_COMPLAIN(
																																																																																																			32829), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_ANTIFLOOD(
																																																																																																					32830), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_FT_SETTINGS(
																																																																																																							32831), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_FT_QUOTAS(
																																																																																																									32832), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTMESSAGE(
																																																																																																											32833), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTBANNER(
																																																																																																													32834), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_HOSTBUTTON(
																																																																																																															32835), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PORT(
																																																																																																																	32836), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_AUTOSTART(
																																																																																																																			32837), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NEEDED_IDENTITY_SECURITY_LEVEL(
																																																																																																																					32838), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_PRIORITY_SPEAKER_DIMM_MODIFICATOR(
																																																																																																																							32839), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_LOG_SETTINGS(
																																																																																																																									32840), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_MIN_CLIENT_VERSION(
																																																																																																																											32841), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_ICON_ID(
																																																																																																																													32842), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_WEBLIST(
																																																																																																																															32843), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CODEC_ENCRYPTION_MODE(
																																																																																																																																	32844), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS(
																																																																																																																																			32845), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_TEMPORARY_PASSWORDS_OWN(
																																																																																																																																					32846), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_CHANNEL_TEMP_DELETE_DELAY_DEFAULT(
																																																																																																																																							32847), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_NICKNAME(
																																																																																																																																									32848), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_MODIFY_INTEGRATIONS(
																																																																																																																																											32849), I_NEEDED_MODIFY_POWER_CHANNEL_MIN_DEPTH(
																																																																																																																																													32850), I_NEEDED_MODIFY_POWER_CHANNEL_MAX_DEPTH(
																																																																																																																																															32851), I_NEEDED_MODIFY_POWER_CHANNEL_GROUP_INHERITANCE_END(
																																																																																																																																																	32852), I_NEEDED_MODIFY_POWER_CHANNEL_PERMISSION_MODIFY_POWER(
																																																																																																																																																			32853), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_PERMISSION_MODIFY_POWER(
																																																																																																																																																					32854), I_NEEDED_MODIFY_POWER_CHANNEL_INFO_VIEW(
																																																																																																																																																							32855), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_CHILD(
																																																																																																																																																									32856), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_PERMANENT(
																																																																																																																																																											32857), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_SEMI_PERMANENT(
																																																																																																																																																													32858), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_TEMPORARY(
																																																																																																																																																															32859), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_TOPIC(
																																																																																																																																																																	32860), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_DESCRIPTION(
																																																																																																																																																																			32861), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_PASSWORD(
																																																																																																																																																																					32862), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_BANNER(
																																																																																																																																																																							32863), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSVOICE(
																																																																																																																																																																									32864), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_OPUSMUSIC(
																																																																																																																																																																											32865), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_MAXQUALITY(
																																																																																																																																																																													32866), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_CODEC_LATENCY_FACTOR_MIN(
																																																																																																																																																																															32867), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_MAXCLIENTS(
																																																																																																																																																																																	32868), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_MAXFAMILYCLIENTS(
																																																																																																																																																																																			32869), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_SORTORDER(
																																																																																																																																																																																					32870), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_DEFAULT(
																																																																																																																																																																																							32871), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_WITH_NEEDED_TALK_POWER(
																																																																																																																																																																																									32872), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_FORCE_PASSWORD(
																																																																																																																																																																																											32873), I_NEEDED_MODIFY_POWER_CHANNEL_CREATE_MODIFY_WITH_TEMP_DELETE_DELAY(
																																																																																																																																																																																													32874), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_PARENT(
																																																																																																																																																																																															32875), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_DEFAULT(
																																																																																																																																																																																																	32876), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_PERMANENT(
																																																																																																																																																																																																			32877), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_SEMI_PERMANENT(
																																																																																																																																																																																																					32878), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_TEMPORARY(
																																																																																																																																																																																																							32879), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_NAME(
																																																																																																																																																																																																									32880), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_TOPIC(
																																																																																																																																																																																																											32881), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_DESCRIPTION(
																																																																																																																																																																																																													32882), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_PASSWORD(
																																																																																																																																																																																																															32883), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_BANNER(
																																																																																																																																																																																																																	32884), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC(
																																																																																																																																																																																																																			32885), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC_QUALITY(
																																																																																																																																																																																																																					32886), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_CODEC_LATENCY_FACTOR(
																																																																																																																																																																																																																							32887), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAXCLIENTS(
																																																																																																																																																																																																																									32888), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAXFAMILYCLIENTS(
																																																																																																																																																																																																																											32889), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_SORTORDER(
																																																																																																																																																																																																																													32890), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_NEEDED_TALK_POWER(
																																																																																																																																																																																																																															32891), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_POWER(
																																																																																																																																																																																																																																	32892), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_MODIFY_POWER(
																																																																																																																																																																																																																																			32893), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_MAKE_CODEC_ENCRYPTED(
																																																																																																																																																																																																																																					32894), I_NEEDED_MODIFY_POWER_CHANNEL_MODIFY_TEMP_DELETE_DELAY(
																																																																																																																																																																																																																																							32895), I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_PERMANENT(
																																																																																																																																																																																																																																									32896), I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_SEMI_PERMANENT(
																																																																																																																																																																																																																																											32897), I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_TEMPORARY(
																																																																																																																																																																																																																																													32898), I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_FLAG_FORCE(
																																																																																																																																																																																																																																															32899), I_NEEDED_MODIFY_POWER_CHANNEL_DELETE_POWER(
																																																																																																																																																																																																																																																	32900), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_DELETE_POWER(
																																																																																																																																																																																																																																																			32901), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_PERMANENT(
																																																																																																																																																																																																																																																					32902), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_SEMI_PERMANENT(
																																																																																																																																																																																																																																																							32903), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_TEMPORARY(
																																																																																																																																																																																																																																																									32904), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_IGNORE_PASSWORD(
																																																																																																																																																																																																																																																											32905), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_IGNORE_MAXCLIENTS(
																																																																																																																																																																																																																																																													32906), I_NEEDED_MODIFY_POWER_CHANNEL_JOIN_POWER(
																																																																																																																																																																																																																																																															32907), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_JOIN_POWER(
																																																																																																																																																																																																																																																																	32908), I_NEEDED_MODIFY_POWER_CHANNEL_SUBSCRIBE_POWER(
																																																																																																																																																																																																																																																																			32909), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_SUBSCRIBE_POWER(
																																																																																																																																																																																																																																																																					32910), I_NEEDED_MODIFY_POWER_CHANNEL_DESCRIPTION_VIEW_POWER(
																																																																																																																																																																																																																																																																							32911), I_NEEDED_MODIFY_POWER_CHANNEL_NEEDED_DESCRIPTION_VIEW_POWER(
																																																																																																																																																																																																																																																																									32912), I_NEEDED_MODIFY_POWER_ICON_ID(
																																																																																																																																																																																																																																																																											32913), I_NEEDED_MODIFY_POWER_MAX_ICON_FILESIZE(
																																																																																																																																																																																																																																																																													32914), I_NEEDED_MODIFY_POWER_ICON_MANAGE(
																																																																																																																																																																																																																																																																															32915), I_NEEDED_MODIFY_POWER_GROUP_IS_PERMANENT(
																																																																																																																																																																																																																																																																																	32916), I_NEEDED_MODIFY_POWER_GROUP_AUTO_UPDATE_TYPE(
																																																																																																																																																																																																																																																																																			32917), I_NEEDED_MODIFY_POWER_GROUP_AUTO_UPDATE_MAX_VALUE(
																																																																																																																																																																																																																																																																																					32918), I_NEEDED_MODIFY_POWER_GROUP_SORT_ID(
																																																																																																																																																																																																																																																																																							32919), I_NEEDED_MODIFY_POWER_GROUP_SHOW_NAME_IN_TREE(
																																																																																																																																																																																																																																																																																									32920), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_LIST(
																																																																																																																																																																																																																																																																																											32921), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_PERMISSION_LIST(
																																																																																																																																																																																																																																																																																													32922), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_CLIENT_LIST(
																																																																																																																																																																																																																																																																																															32923), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_LIST(
																																																																																																																																																																																																																																																																																																	32924), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_PERMISSION_LIST(
																																																																																																																																																																																																																																																																																																			32925), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_CLIENT_LIST(
																																																																																																																																																																																																																																																																																																					32926), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CLIENT_PERMISSION_LIST(
																																																																																																																																																																																																																																																																																																							32927), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNEL_PERMISSION_LIST(
																																																																																																																																																																																																																																																																																																									32928), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELCLIENT_PERMISSION_LIST(
																																																																																																																																																																																																																																																																																																											32929), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_CREATE(
																																																																																																																																																																																																																																																																																																													32930), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_CREATE(
																																																																																																																																																																																																																																																																																																															32931), I_NEEDED_MODIFY_POWER_GROUP_MODIFY_POWER(
																																																																																																																																																																																																																																																																																																																	32932), I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MODIFY_POWER(
																																																																																																																																																																																																																																																																																																																			32933), I_NEEDED_MODIFY_POWER_GROUP_MEMBER_ADD_POWER(
																																																																																																																																																																																																																																																																																																																					32934), I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MEMBER_ADD_POWER(
																																																																																																																																																																																																																																																																																																																							32935), I_NEEDED_MODIFY_POWER_GROUP_MEMBER_REMOVE_POWER(
																																																																																																																																																																																																																																																																																																																									32936), I_NEEDED_MODIFY_POWER_GROUP_NEEDED_MEMBER_REMOVE_POWER(
																																																																																																																																																																																																																																																																																																																											32937), I_NEEDED_MODIFY_POWER_PERMISSION_MODIFY_POWER(
																																																																																																																																																																																																																																																																																																																													32938), I_NEEDED_MODIFY_POWER_PERMISSION_MODIFY_POWER_IGNORE(
																																																																																																																																																																																																																																																																																																																															32939), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_SERVERGROUP_DELETE(
																																																																																																																																																																																																																																																																																																																																	32940), I_NEEDED_MODIFY_POWER_VIRTUALSERVER_CHANNELGROUP_DELETE(
																																																																																																																																																																																																																																																																																																																																			32941), I_NEEDED_MODIFY_POWER_CLIENT_PERMISSION_MODIFY_POWER(
																																																																																																																																																																																																																																																																																																																																					32942), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_PERMISSION_MODIFY_POWER(
																																																																																																																																																																																																																																																																																																																																							32943), I_NEEDED_MODIFY_POWER_CLIENT_MAX_CLONES_UID(
																																																																																																																																																																																																																																																																																																																																									32944), I_NEEDED_MODIFY_POWER_CLIENT_MAX_IDLETIME(
																																																																																																																																																																																																																																																																																																																																											32945), I_NEEDED_MODIFY_POWER_CLIENT_MAX_AVATAR_FILESIZE(
																																																																																																																																																																																																																																																																																																																																													32946), I_NEEDED_MODIFY_POWER_CLIENT_MAX_CHANNEL_SUBSCRIPTIONS(
																																																																																																																																																																																																																																																																																																																																															32947), I_NEEDED_MODIFY_POWER_CLIENT_IS_PRIORITY_SPEAKER(
																																																																																																																																																																																																																																																																																																																																																	32948), I_NEEDED_MODIFY_POWER_CLIENT_SKIP_CHANNELGROUP_PERMISSIONS(
																																																																																																																																																																																																																																																																																																																																																			32949), I_NEEDED_MODIFY_POWER_CLIENT_FORCE_PUSH_TO_TALK(
																																																																																																																																																																																																																																																																																																																																																					32950), I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_BANS(
																																																																																																																																																																																																																																																																																																																																																							32951), I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_ANTIFLOOD(
																																																																																																																																																																																																																																																																																																																																																									32952), I_NEEDED_MODIFY_POWER_CLIENT_USE_RESERVED_SLOT(
																																																																																																																																																																																																																																																																																																																																																											32953), I_NEEDED_MODIFY_POWER_CLIENT_USE_CHANNEL_COMMANDER(
																																																																																																																																																																																																																																																																																																																																																													32954), I_NEEDED_MODIFY_POWER_CLIENT_REQUEST_TALKER(
																																																																																																																																																																																																																																																																																																																																																															32955), I_NEEDED_MODIFY_POWER_CLIENT_AVATAR_DELETE_OTHER(
																																																																																																																																																																																																																																																																																																																																																																	32956), I_NEEDED_MODIFY_POWER_CLIENT_IS_STICKY(
																																																																																																																																																																																																																																																																																																																																																																			32957), I_NEEDED_MODIFY_POWER_CLIENT_IGNORE_STICKY(
																																																																																																																																																																																																																																																																																																																																																																					32958), I_NEEDED_MODIFY_POWER_CLIENT_INFO_VIEW(
																																																																																																																																																																																																																																																																																																																																																																							32959), I_NEEDED_MODIFY_POWER_CLIENT_PERMISSIONOVERVIEW_VIEW(
																																																																																																																																																																																																																																																																																																																																																																									32960), I_NEEDED_MODIFY_POWER_CLIENT_PERMISSIONOVERVIEW_OWN(
																																																																																																																																																																																																																																																																																																																																																																											32961), I_NEEDED_MODIFY_POWER_CLIENT_REMOTEADDRESS_VIEW(
																																																																																																																																																																																																																																																																																																																																																																													32962), I_NEEDED_MODIFY_POWER_CLIENT_SERVERQUERY_VIEW_POWER(
																																																																																																																																																																																																																																																																																																																																																																															32963), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_SERVERQUERY_VIEW_POWER(
																																																																																																																																																																																																																																																																																																																																																																																	32964), I_NEEDED_MODIFY_POWER_CLIENT_CUSTOM_INFO_VIEW(
																																																																																																																																																																																																																																																																																																																																																																																			32965), I_NEEDED_MODIFY_POWER_CLIENT_KICK_FROM_SERVER_POWER(
																																																																																																																																																																																																																																																																																																																																																																																					32966), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_KICK_FROM_SERVER_POWER(
																																																																																																																																																																																																																																																																																																																																																																																							32967), I_NEEDED_MODIFY_POWER_CLIENT_KICK_FROM_CHANNEL_POWER(
																																																																																																																																																																																																																																																																																																																																																																																									32968), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_KICK_FROM_CHANNEL_POWER(
																																																																																																																																																																																																																																																																																																																																																																																											32969), I_NEEDED_MODIFY_POWER_CLIENT_BAN_POWER(
																																																																																																																																																																																																																																																																																																																																																																																													32970), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_BAN_POWER(
																																																																																																																																																																																																																																																																																																																																																																																															32971), I_NEEDED_MODIFY_POWER_CLIENT_MOVE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																	32972), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_MOVE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																			32973), I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																					32974), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_COMPLAIN_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																							32975), I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_LIST(
																																																																																																																																																																																																																																																																																																																																																																																																									32976), I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_DELETE_OWN(
																																																																																																																																																																																																																																																																																																																																																																																																											32977), I_NEEDED_MODIFY_POWER_CLIENT_COMPLAIN_DELETE(
																																																																																																																																																																																																																																																																																																																																																																																																													32978), I_NEEDED_MODIFY_POWER_CLIENT_BAN_LIST(
																																																																																																																																																																																																																																																																																																																																																																																																															32979), I_NEEDED_MODIFY_POWER_CLIENT_BAN_CREATE(
																																																																																																																																																																																																																																																																																																																																																																																																																	32980), I_NEEDED_MODIFY_POWER_CLIENT_BAN_DELETE_OWN(
																																																																																																																																																																																																																																																																																																																																																																																																																			32981), I_NEEDED_MODIFY_POWER_CLIENT_BAN_DELETE(
																																																																																																																																																																																																																																																																																																																																																																																																																					32982), I_NEEDED_MODIFY_POWER_CLIENT_BAN_MAX_BANTIME(
																																																																																																																																																																																																																																																																																																																																																																																																																							32983), I_NEEDED_MODIFY_POWER_CLIENT_PRIVATE_TEXTMESSAGE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																									32984), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_PRIVATE_TEXTMESSAGE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																											32985), I_NEEDED_MODIFY_POWER_CLIENT_SERVER_TEXTMESSAGE_SEND(
																																																																																																																																																																																																																																																																																																																																																																																																																													32986), I_NEEDED_MODIFY_POWER_CLIENT_CHANNEL_TEXTMESSAGE_SEND(
																																																																																																																																																																																																																																																																																																																																																																																																																															32987), I_NEEDED_MODIFY_POWER_CLIENT_OFFLINE_TEXTMESSAGE_SEND(
																																																																																																																																																																																																																																																																																																																																																																																																																																	32988), I_NEEDED_MODIFY_POWER_CLIENT_TALK_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																			32989), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_TALK_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																					32990), I_NEEDED_MODIFY_POWER_CLIENT_POKE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																							32991), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_POKE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																									32992), I_NEEDED_MODIFY_POWER_CLIENT_SET_FLAG_TALKER(
																																																																																																																																																																																																																																																																																																																																																																																																																																											32993), I_NEEDED_MODIFY_POWER_CLIENT_WHISPER_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																													32994), I_NEEDED_MODIFY_POWER_CLIENT_NEEDED_WHISPER_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																															32995), I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_DESCRIPTION(
																																																																																																																																																																																																																																																																																																																																																																																																																																																	32996), I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_OWN_DESCRIPTION(
																																																																																																																																																																																																																																																																																																																																																																																																																																																			32997), I_NEEDED_MODIFY_POWER_CLIENT_MODIFY_DBPROPERTIES(
																																																																																																																																																																																																																																																																																																																																																																																																																																																					32998), I_NEEDED_MODIFY_POWER_CLIENT_DELETE_DBPROPERTIES(
																																																																																																																																																																																																																																																																																																																																																																																																																																																							32999), I_NEEDED_MODIFY_POWER_CLIENT_CREATE_MODIFY_SERVERQUERY_LOGIN(
																																																																																																																																																																																																																																																																																																																																																																																																																																																									33000), I_NEEDED_MODIFY_POWER_FT_IGNORE_PASSWORD(
																																																																																																																																																																																																																																																																																																																																																																																																																																																											33001), I_NEEDED_MODIFY_POWER_FT_TRANSFER_LIST(
																																																																																																																																																																																																																																																																																																																																																																																																																																																													33002), I_NEEDED_MODIFY_POWER_FT_FILE_UPLOAD_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																															33003), I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_UPLOAD_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																	33004), I_NEEDED_MODIFY_POWER_FT_FILE_DOWNLOAD_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																			33005), I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_DOWNLOAD_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																					33006), I_NEEDED_MODIFY_POWER_FT_FILE_DELETE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																							33007), I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_DELETE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																									33008), I_NEEDED_MODIFY_POWER_FT_FILE_RENAME_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																											33009), I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_RENAME_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																													33010), I_NEEDED_MODIFY_POWER_FT_FILE_BROWSE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																															33011), I_NEEDED_MODIFY_POWER_FT_NEEDED_FILE_BROWSE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	33012), I_NEEDED_MODIFY_POWER_FT_DIRECTORY_CREATE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																			33013), I_NEEDED_MODIFY_POWER_FT_NEEDED_DIRECTORY_CREATE_POWER(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					33014), I_NEEDED_MODIFY_POWER_FT_QUOTA_MB_DOWNLOAD_PER_CLIENT(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																							33015), I_NEEDED_MODIFY_POWER_FT_QUOTA_MB_UPLOAD_PER_CLIENT(
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																									33016);

	private int value = 0;

	private TSPermission(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
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
