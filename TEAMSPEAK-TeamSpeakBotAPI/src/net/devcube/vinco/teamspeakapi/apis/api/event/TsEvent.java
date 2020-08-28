package me.vinco.teamspeakapi.apis.api.event;

import me.vinco.teamspeakapi.apis.api.events.ChannelCreateEvent;
import me.vinco.teamspeakapi.apis.api.events.ChannelDeletedEvent;
import me.vinco.teamspeakapi.apis.api.events.ChannelDescriptionEditedEvent;
import me.vinco.teamspeakapi.apis.api.events.ChannelEditedEvent;
import me.vinco.teamspeakapi.apis.api.events.ChannelPasswordChangedEvent;
import me.vinco.teamspeakapi.apis.api.events.ClientJoinEvent;
import me.vinco.teamspeakapi.apis.api.events.ClientLeaveEvent;
import me.vinco.teamspeakapi.apis.api.events.ClientMoveEvent;
import me.vinco.teamspeakapi.apis.api.events.PrivilegeKeyUsedEvent;
import me.vinco.teamspeakapi.apis.api.events.ServerEditedEvent;
import me.vinco.teamspeakapi.apis.api.events.TextMessageEvent;

public interface TsEvent {
	
   void onTextMessage(TextMessageEvent e);

   void onClientMove(ClientMoveEvent e);

   void onClientJoin(ClientJoinEvent e);

   void onClientLeave(ClientLeaveEvent e);

   void onServerEdit(ServerEditedEvent e);

   void onChannelEdit(ChannelEditedEvent e);

   void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e);

   void onChannelCreate(ChannelCreateEvent e);

   void onChannelDeleted(ChannelDeletedEvent e);

   void onChannelPasswordChanged(ChannelPasswordChangedEvent e);

   void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e);
}
