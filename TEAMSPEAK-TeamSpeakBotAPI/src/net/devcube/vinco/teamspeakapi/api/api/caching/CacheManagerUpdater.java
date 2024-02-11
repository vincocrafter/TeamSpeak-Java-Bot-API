/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 07.09.2023
 * 
 * Uhrzeit : 21:21:53
 */
package net.devcube.vinco.teamspeakapi.api.api.caching;

import net.devcube.vinco.teamspeakapi.api.api.event.EventHandler;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelCreateEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelDeletedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelDescriptionEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelMovedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelPasswordChangedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientJoinEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientLeaveEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientMoveEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.PrivilegeKeyUsedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ServerEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.TextMessageEvent;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class CacheManagerUpdater extends TsEventAdapter {

	@EventHandler
	public void onChannelCreate(ChannelCreateEvent e) {
		if (e.getConfig().isChannelsCached()) {
			e.getCache().updateChannelsListCache(); // only update channellist info
			e.getCache().updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}

	}

	@EventHandler
	public void onChannelDeleted(ChannelDeletedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			e.getCache().updateChannelsListCache(); // only update channellist info
			e.getCache().cacheRemoveChannel(e.getChannelID());
		}
	}

	@EventHandler
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
		// Not needed, because ChannelEditedEvent
	}

	@EventHandler
	public void onChannelEdit(ChannelEditedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			e.getCache().updateChannelsListCache(); // only update channellist info
			e.getCache().updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}
	}

	@EventHandler
	public void onChannelMoved(ChannelMovedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			e.getCache().updateChannelsListCache(); // only update channellist info
			e.getCache().updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}
	}

	@EventHandler
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
		// Not needed, because ChannelEditedEvent
	}

	@EventHandler
	public void onClientJoin(ClientJoinEvent e) {
		CacheManager cache = e.getCache();

		if (e.getConfig().isClientsCached()) {
			cache.updateClientListCache(); // only update clientlist info
			cache.updateClientCache(e.getClientID()); // only update clientinfo of client
		}

		if (e.getConfig().isDataBaseCached()) {
			int dbID = 0;
			if (e.getConfig().isClientsCached()) {
				dbID = Integer.parseInt(Formatter.get(cache.getClientInfo(e.getClientID()), "client_database_id="));
			} else {
				dbID = e.getSyncAPI().getClientDataBaseIDByUUID(e.getClientUUID());
			}
			cache.updateDBClientCache(dbID); // only update databaseinfo of client
		}
	}

	@EventHandler
	public void onClientLeave(ClientLeaveEvent e) {
		CacheManager cache = e.getServerQuery().getCache();

		if (!e.getConfig().isClientsCached())
			return;
		
		if (e.getConfig().isDataBaseCached()) //update Database cache is only possible if client caching is enabled
			cache.updateDBClientCache(Integer.parseInt(Formatter.get(cache.getClientInfo(e.getClientID()), "client_database_id="))); // only update databaseinfo of client
		
		cache.updateClientListCache(); // only update clientlist info
		cache.cacheRemoveClient(e.getClientID());
	}

	@EventHandler
	public void onClientMove(ClientMoveEvent e) {
		CacheManager cache = e.getCache();
		
		if (e.getConfig().isClientsCached()) {
			cache.updateClientListCache(); // only update clientlist info
			cache.updateClientCache(e.getClientID()); // only update clientinfo of client
		}
		
		if (e.getConfig().isQueryCached()) {
			String information = cache.getQueryProperties();
			int queryID = Integer.parseInt(Formatter.get(information, "client_id="));
			if (e.getClientID() != queryID) // checks if the query has moved
				return;
			information = cache.updateAttribute(information, "client_channel_id=", String.valueOf(e.getTargetChannelID()));
			cache.updateQueryPropsCache(information);
		}
	}

	@EventHandler
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {

	}

	@EventHandler
	public void onServerEdit(ServerEditedEvent e) {
		if (e.getConfig().isVirtualServerCached())
			e.getServerQuery().getCache().updateVirtualServerCache(); // only update virtual server properties
	}

	@EventHandler
	public void onTextMessage(TextMessageEvent e) {

	}

	

}
