package net.devcube.vinco.teamspeakapi.api.api.event;

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

public interface TsEvent {
	
   void onTextMessage(TextMessageEvent e);

   void onClientMove(ClientMoveEvent e);

   void onClientJoin(ClientJoinEvent e);

   void onClientLeave(ClientLeaveEvent e);

   void onServerEdit(ServerEditedEvent e);

   void onChannelEdit(ChannelEditedEvent e);
   
   void onChannelMoved(ChannelMovedEvent e);

   void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e);

   void onChannelCreate(ChannelCreateEvent e);

   void onChannelDeleted(ChannelDeletedEvent e);

   void onChannelPasswordChanged(ChannelPasswordChangedEvent e);

   void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e);
}
