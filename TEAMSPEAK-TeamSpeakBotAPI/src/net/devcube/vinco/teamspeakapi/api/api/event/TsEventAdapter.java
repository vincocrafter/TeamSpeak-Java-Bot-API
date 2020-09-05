package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.events.ChannelCreateEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelDeletedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelDescriptionEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ChannelPasswordChangedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientJoinEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientLeaveEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ClientMoveEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.PrivilegeKeyUsedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.ServerEditedEvent;
import net.devcube.vinco.teamspeakapi.api.api.events.TextMessageEvent;

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
