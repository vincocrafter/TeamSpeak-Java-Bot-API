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

import java.util.HashSet;
import java.util.Set;

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
			CacheManager cache = e.getServerQuery().getCache();
			cache.updateChannelsListCache(); // only update channellist info
			cache.updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}
	}

	@EventHandler
	public void onChannelDeleted(ChannelDeletedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			CacheManager cache = e.getServerQuery().getCache();
			cache.removeChannelListCacheChannel(e.getChannelID()); // only update channellist info
			cache.cacheRemoveChannel(e.getChannelID());
		}
	}

	@EventHandler
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
		// Not needed, because ChannelEditedEvent
	}

	@EventHandler
	public void onChannelEdit(ChannelEditedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			CacheManager cache = e.getServerQuery().getCache();
			cache.updateChannelsListCache(); // only update channellist info
			cache.updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}
		
		
	}

	@EventHandler
	public void onChannelMoved(ChannelMovedEvent e) {
		if (e.getConfig().isChannelsCached()) {
			CacheManager cache = e.getServerQuery().getCache();
			cache.updateChannelsListCache(); // only update channellist info
			cache.updateChannelCache(e.getChannelID()); // only update channelinfo of channel
		}
	}

	@EventHandler
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
		// Not needed, because ChannelEditedEvent
	}

	@EventHandler
	public void onClientJoin(ClientJoinEvent e) {
		CacheManager cache = e.getServerQuery().getCache();

		if (e.getConfig().isClientsCached()) {
			cache.updateClientListCache(); // only update clientlist info
			cache.updateClientCache(e.getClientID()); // only update clientinfo of client
		}
		
		if (e.getConfig().isDataBaseCached()) {
			cache.updateDBClientCache(e.getClientDataBaseID()); // only update databaseinfo of client
			cache.updateDBClientsListCache();
		}
	}

	@EventHandler
	public void onClientLeave(ClientLeaveEvent e) {
		CacheManager cache = e.getServerQuery().getCache();

		if (e.getConfig().isDataBaseCached()) {
			cache.updateDBClientsListCache();
		}

		if (!e.getConfig().isClientsCached())
			return;

		int clientID = e.getClientID();
		if (e.getConfig().isDataBaseCached()) // update Database cache is only possible if client caching is enabled
			cache.updateDBClientCache(Integer.parseInt(Formatter.get(cache.getClientInfo(clientID), "client_database_id="))); // only update databaseinfo of client
		
		cache.removeClientListCacheClient(clientID);
		cache.cacheRemoveClient(clientID);
	}

	@EventHandler
	public void onClientMove(ClientMoveEvent e) {
		CacheManager cache = e.getServerQuery().getCache();

		if (e.getConfig().isClientsCached()) {
			cache.updateClientListCache(); // only update clientlist info
			Set<Integer> ids = new HashSet<>();
			ids.addAll(e.getClientIDs()); // update clientinfo of moved clients
			int invoker = e.getInvokerID();
			if (invoker != -1)
				ids.add(invoker); // update clientinfo of invoker
						
			cache.updateClientsCache(ids.stream().toList()); // only use one command for multiple clients
		}

		if (e.getConfig().isQueryCached()) {
			String information = cache.getQueryProperties();
			int queryID = Integer.parseInt(Formatter.get(information, "client_id="));
			if (!e.getClientIDs().contains(queryID)) // checks if the query has moved
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
		if (e.getConfig().isVirtualServerCached()) {
			CacheManager cache = e.getServerQuery().getCache();
			cache.updateVirtualServerCache(); // only update virtual server properties
			cache.updateVirtualServerListCache(); // only update list of virtualservers
		}
	}

	@EventHandler
	public void onTextMessage(TextMessageEvent e) {

	}

}
