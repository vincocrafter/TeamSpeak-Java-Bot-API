package net.devcube.vinco.teamspeakapi.apis.api.event;

import net.devcube.vinco.teamspeakapi.apis.api.events.ChannelCreateEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ChannelDeletedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ChannelDescriptionEditedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ChannelEditedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ChannelPasswordChangedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ClientJoinEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ClientLeaveEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ClientMoveEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.PrivilegeKeyUsedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.ServerEditedEvent;
import net.devcube.vinco.teamspeakapi.apis.api.events.TextMessageEvent;

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
