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
