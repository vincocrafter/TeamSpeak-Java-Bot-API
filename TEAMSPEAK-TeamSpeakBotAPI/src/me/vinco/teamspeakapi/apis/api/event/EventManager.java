/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 12.02.2019
 * 
 *Uhrzeit : 16:34:08
 */
package me.vinco.teamspeakapi.apis.api.event;

import java.util.ArrayList;
import java.util.Timer;

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
import me.vinco.teamspeakapi.apis.api.exception.wrapper.UnknownEventException;
import me.vinco.teamspeakapi.apis.api.util.Logger;
import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class EventManager {

	Ts3ServerQuery query;
	Timer timer = new Timer();
	private ArrayList<TsEvent> events = new ArrayList<TsEvent>();

	public EventManager(Ts3ServerQuery query) {
		this.query = query;
	}

	public ArrayList<TsEvent> getEvents() {
		return events;
	}

	public void registerEvent(TsEvent event) throws UnknownEventException {
		if (event == null) {
			throw new UnknownEventException();
		} else {
			if (query.getConfig().isEventDebug()) {
				Logger.log(5, "Registering Event : " + event.getClass().getName());
			}
			events.add(event);
		}
	}

	public void addTs3Listener(TsEvent event) {
		if (event != null) {
			if (query.getConfig().isEventDebug()) {
				Logger.log(5, "Registering Event : " + event.getClass().getName());
			}
			events.add(event);
		}
	}

	public void unregisterEvent(TsEvent event) throws UnknownEventException {
		if (event == null) {
			throw new UnknownEventException();
		} else {
			if (events.contains(event)) {
				if (query.getConfig().isEventDebug()) {
					Logger.log(5, "Unregistering Event : " + event.getClass().getName());
				}

				events.remove(event);
			} else if (query.getConfig().isEventDebug()) {
				Logger.log(5, "Event was not found");
			}

		}
	}

	public void callEvent(String[] infos, String eventName) throws UnknownEventException {
		if (query.getConfig().isEventDebug()) {
			Logger.log(5, eventName + " was called!");
		}
		switch (eventName) {
		case "notifytokenused": // TOKEN USED EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onPrivilegeKeyUsed(new PrivilegeKeyUsedEvent(infos));
			}
			break;
		case "notifycliententerview": // CLIENT JOIN EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientJoin(new ClientJoinEvent(infos));
			}
			break;
		case "notifyclientleftview": // CLIENT LEAVE EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientLeave(new ClientLeaveEvent(infos));
			}
			break;
		case "notifychanneldescriptionchanged": // CHANNEL DESCRIPTION CHANGED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelDescriptionChanged(new ChannelDescriptionEditedEvent(infos));
			}
			break;
		case "notifychanneledited": // CHANNEL EDITED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelEdit(new ChannelEditedEvent(infos));
			}
			break;
		case "notifyserveredited": // SERVER EDITED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onServerEdit(new ServerEditedEvent(infos));
			}
			break;
		case "notifyclientmoved": // CLIENT MOVED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientMove(new ClientMoveEvent(infos));
			}
			break;
		case "notifychannelcreated": // CHANNEL CREATED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelCreate(new ChannelCreateEvent(infos));
			}
			break;
		case "notifychanneldeleted": // CHANNEL DELETED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelDeleted(new ChannelDeletedEvent(infos));
			}
			break;
		case "notifychannelpasswordchanged": // CHANNEL PASSWORD CHANGED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelPasswordChanged(new ChannelPasswordChangedEvent(infos));
			}

			break;
		case "notifytextmessage": // TEXT MESSAGE SEND
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onTextMessage(new TextMessageEvent(infos));
			}
			break;
		default:
			if (query.getConfig().isEventDebug()) {
				(new BaseEvent(infos)).printInfos();
			}
			throw new UnknownEventException("Event that is called is not found : " + eventName);
		}
	}

}
