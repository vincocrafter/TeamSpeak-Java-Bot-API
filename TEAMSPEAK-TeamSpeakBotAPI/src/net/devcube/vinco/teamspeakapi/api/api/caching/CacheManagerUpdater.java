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

public class CacheManagerUpdater extends TsEventAdapter {
	
	@EventHandler
	public void onChannelCreate(ChannelCreateEvent e) {
		e.getServerQuery().getCache().updateChannelsListCache(); //only update channellist info
		e.getServerQuery().getCache().updateChannelCache(e.getChannelID()); //only update channelinfo of channel
	}
	
	@EventHandler
	public void onChannelDeleted(ChannelDeletedEvent e) {
		e.getServerQuery().getCache().updateChannelsListCache(); //only update channellist info
		e.getServerQuery().getCache().cacheRemoveChannel(e.getChannelID());
	}
	
	@EventHandler
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
		// Not needed, because ChannelEditedEvent
	}
	
	@EventHandler
	public void onChannelEdit(ChannelEditedEvent e) {
		e.getServerQuery().getCache().updateChannelsListCache(); //only update channellist info
		e.getServerQuery().getCache().updateChannelCache(e.getChannelID()); //only update channelinfo of channel
	}
	
	@EventHandler
	public void onChannelMoved(ChannelMovedEvent e) {
		e.getServerQuery().getCache().updateChannelsListCache(); //only update channellist info
		e.getServerQuery().getCache().updateChannelCache(e.getChannelID()); //only update channelinfo of channel		
	}
	
	@EventHandler
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
		// Not needed, because ChannelEditedEvent
	}
	
	@EventHandler
	public void onClientJoin(ClientJoinEvent e) {
		e.getServerQuery().getCache().updateClientListCache(); //only update clientlist info
		e.getServerQuery().getCache().updateClientCache(e.getClientID()); //only update clientinfo of client
		e.getServerQuery().getCache().updateDBClientCache(e.getSyncAPI().getDataBaseClientIDByUUID(e.getClientUUID())); //only update databaseinfo of client
	}
	
	@EventHandler
	public void onClientLeave(ClientLeaveEvent e) {
		e.getServerQuery().getCache().updateClientListCache(); //only update clientlist info
		e.getServerQuery().getCache().updateDBClientCache(e.getSyncAPI().getClientDataBaseIDByUUID(e.getSyncAPI().getClientUUIDByID(e.getClientID()))); //only update databaseinfo of client
		e.getServerQuery().getCache().cacheRemoveClient(e.getClientID());
	}
	
	@EventHandler
	public void onClientMove(ClientMoveEvent e) {
		if (e.getServerQuery().getConfig().isQueryCached()) {
			if (e.getClientID() == e.getBasicAPI().getQueryInfo().getClientID()) {
				e.getServerQuery().getCache().updateQueryPropsCache(); //only update whoami info
			}
		}
 		
		e.getServerQuery().getCache().updateClientListCache(); //only update clientlist info
		e.getServerQuery().getCache().updateClientCache(e.getClientID()); //only update clientinfo of client
	}
	
	@EventHandler
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
		
	}
	
	@EventHandler
	public void onServerEdit(ServerEditedEvent e) {
		e.getServerQuery().getCache().updateVirtualServerCache(); // only update virtual server properties
	}
	
	@EventHandler
	public void onTextMessage(TextMessageEvent e) {
		
	}
	
}
