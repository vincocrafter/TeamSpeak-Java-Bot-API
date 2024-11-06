/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 * <p>
 * Autor : Vincent
 * <p>
 * Jahr 2023
 * <p>
 * Datum : 07.09.2023
 * <p>
 * Uhrzeit : 21:21:53
 */
package net.devcube.vinco.teamspeakapi.api.api.caching;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.devcube.vinco.teamspeakapi.api.api.event.EventHandler;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.*;
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
    public void onChannelEdit(ChannelEditedEvent e) {
        if (e.getConfig().isChannelsCached()) {
            int channelID = e.getChannelID();
            CacheManager cache = e.getServerQuery().getCache();
            List<String> blackList = new ArrayList<>();
            blackList.add("cid");
            blackList.add("reasonid");
            blackList.add("invokername");
            blackList.add("invokerid");
            blackList.add("invokeruid");
            blackList.add("notifychanneledited");
            blackList.add("reasonid");
            Set<String> elements = e.getSplitMap().keySet();
            elements.removeAll(blackList);

            String chInfo = cache.getChannelInfo(channelID);
            String chList = cache.getChannelListChannel(channelID);
            for (String keys : elements) {
                chList = cache.updateAttribute(chList, keys, e.getSplitMap().get(keys));
                chInfo = cache.updateAttribute(chInfo, keys, e.getSplitMap().get(keys));
            }
            cache.updateChannelListChannelProp(channelID, chList); // only update channellist info
            cache.updateChannelCache(channelID, chInfo); // only update channelinfo of channe
        }
    }

    @EventHandler
    public void onChannelMoved(ChannelMovedEvent e) {
        if (e.getConfig().isChannelsCached()) {
            CacheManager cache = e.getServerQuery().getCache();
            int channelID = e.getChannelID();
            String chList = cache.getChannelListChannel(channelID);
            chList = cache.updateAttribute(chList, "channel_order", String.valueOf(e.getOrder()));
            chList = cache.updateAttribute(chList, "pid", String.valueOf(e.getChannelParentID()));
            cache.updateChannelListChannelProp(channelID, chList);

            String chInfo = cache.getChannelInfo(channelID);
            chInfo = cache.updateAttribute(chInfo, "channel_order", String.valueOf(e.getOrder()));
            chInfo = cache.updateAttribute(chInfo, "pid", String.valueOf(e.getChannelParentID()));
            cache.updateChannelCache(channelID, chInfo);
        }
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
        List<Integer> movedClients = e.getClientIDs();

        if (e.getConfig().isClientsCached()) {
            cache.updateClientListCache(); // only update clientlist info
            Set<Integer> ids = new HashSet<>(movedClients); // update clientinfo of moved clients
            int invoker = e.getInvokerID();
            if (invoker != -1)
                ids.add(invoker); // update clientinfo of invoker
            cache.updateClientsCache(ids.stream().toList()); // only use one command for multiple clients
        }

        if (e.getConfig().isQueryCached()) {
            String information = cache.getQueryProperties();
            int queryID = Integer.parseInt(Formatter.get(information, "client_id="));
            if (!movedClients.contains(queryID)) // checks if the query has moved
                return;
            information = cache.updateAttribute(information, "client_channel_id", String.valueOf(e.getTargetChannelID()));
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
}
