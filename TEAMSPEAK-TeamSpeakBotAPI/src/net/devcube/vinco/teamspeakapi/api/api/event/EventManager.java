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
package net.devcube.vinco.teamspeakapi.api.api.event;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Timer;

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
import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownEventException;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

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
				query.getLogger().log(5, "Registering Event : " + event.getClass().getName());
			}
			events.add(event);
		}
	}

	public void addTs3Listener(TsEvent event) {
		if (event != null) {
			if (query.getConfig().isEventDebug()) {
				query.getLogger().log(5, "Registering Event : " + event.getClass().getName());
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
					query.getLogger().log(5, "Unregistering Event : " + event.getClass().getName());
				}

				events.remove(event);
			} else if (query.getConfig().isEventDebug()) {
				query.getLogger().log(5, "Event was not found");
			}

		}
	}

	public void callEvent(String[] infos, String eventName) throws UnknownEventException {
		if (query.getConfig().isEventDebug()) {
			query.getLogger().log(5, eventName + " was called!");
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
	
	// New Method of calling Events, using Annotations
	
	public void callNewEvent(String eventName, String[] infos) {
		if (query.getConfig().isEventDebug()) {
			query.getLogger().log(5, eventName + " was called!");
		}
		
		for (TsEvent registeredEvents : events) {
			for (Method meth : registeredEvents.getClass().getDeclaredMethods()) {
				if (meth.isAnnotationPresent(EventHandler.class)) { //Check annotation
					for(Parameter par : meth.getParameters()) { //Gets the parameters
						System.out.println(par);
					}
				}
			}
		}
	}
	
	public void startListening() {

	}

}
