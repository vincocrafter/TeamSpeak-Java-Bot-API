package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.events.*;

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
