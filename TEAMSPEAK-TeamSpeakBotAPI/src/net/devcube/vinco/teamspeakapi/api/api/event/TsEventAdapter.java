package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.events.*;

public abstract class TsEventAdapter implements TsEvent {
	
	public void onTextMessage(TextMessageEvent e) {
	}

	public void onClientMove(ClientMoveEvent e) {
	}

	public void onClientJoin(ClientJoinEvent e) {
	}

	public void onClientLeave(ClientLeaveEvent e) {
	}

	public void onServerEdit(ServerEditedEvent e) {
	}

	public void onChannelEdit(ChannelEditedEvent e) {
	}
	
	public void onChannelMoved(ChannelMovedEvent e) {
	}
	
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
	}

	public void onChannelCreate(ChannelCreateEvent e) {
	}

	public void onChannelDeleted(ChannelDeletedEvent e) {
	}

	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
	}

	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
	}
}
