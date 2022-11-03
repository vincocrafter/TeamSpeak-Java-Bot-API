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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
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
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

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
			query.debug(DebugOutputType.EVENTMANAGER, "Registering Event : " + event.getClass().getName());
			events.add(event);
		}
	}

	public void addTs3Listener(TsEvent event) {
		if (event != null) {
			query.debug(DebugOutputType.EVENTMANAGER, "Registering Event : " + event.getClass().getName());
			events.add(event);
		}
	}

	public void unregisterEvent(TsEvent event) throws UnknownEventException {
		if (event == null) {
			throw new UnknownEventException();
		} else {
			if (events.contains(event)) {
				query.debug(DebugOutputType.EVENTMANAGER, "Unregistering Event : " + event.getClass().getName());
				events.remove(event);
			} else {
				query.getLogger().log(5, "Event was not found");
				query.debug(DebugOutputType.EVENTMANAGER, "Event was not found" + event.getClass().getName());
			}

		}
	}

	/**
	 * Old Event Call
	 * 
	 * @param infos
	 * @param eventName
	 * @throws UnknownEventException
	 * @deprecated
	 */
	public void callEvent(String[] infos, String eventName) throws UnknownEventException {

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
			throw new UnknownEventException("Event that is called is not found : " + eventName);
		}
	}

	/**
	 * New Method of calling Events, using Annotations
	 * 
	 * @param String[]
	 *                     eventInformation
	 */

	public void callNewEvent(String[] infos) {
		BaseEvent event = getEventByName(infos);
		String eventName = infos[0];

		debugNewEvent(eventName, Formatter.connectString(infos)); // new debug Method for Event calling

		for (TsEvent registeredEvents : events) {
			for (Method meth : registeredEvents.getClass().getDeclaredMethods()) {
				if (meth.isAnnotationPresent(EventHandler.class)) { // Check
					for (AnnotatedType ann1 : meth.getAnnotatedParameterTypes()) {
						if (ann1.getType().getTypeName().equals(event.getClass().getName())) { // Check for parameter name is equal to the
							// (Base)Event Class Name
							// Example -> public void test(PrivilegeKeyUsedEvent ev) {
							// PrivilegeKeyUsedEvent is the name of the parameter and the name of the Class
							try {
								meth.invoke(meth, getEventByName(infos));
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Returners Different (BaseEvent)Classes depending by the given information.
	 * Only called by the callNewEvent Method
	 * 
	 * @param String[]
	 *                     eventInformation
	 */

	private BaseEvent getEventByName(String[] infos) {
		String eventName = infos[0];
		switch (eventName) {
		case "notifytokenused": // TOKEN USED EVENT
			return new PrivilegeKeyUsedEvent(infos);
		case "notifycliententerview": // CLIENT JOIN EVENT
			return new ClientJoinEvent(infos);
		case "notifyclientleftview": // CLIENT LEAVE EVENT
			return new ClientLeaveEvent(infos);
		case "notifychanneldescriptionchanged": // CHANNEL DESCRIPTION CHANGED
			return new ChannelDescriptionEditedEvent(infos);
		case "notifychanneledited": // CHANNEL EDITED
			return new ChannelEditedEvent(infos);
		case "notifyserveredited": // SERVER EDITED
			return new ServerEditedEvent(infos);
		case "notifyclientmoved": // CLIENT MOVED
			return new ClientMoveEvent(infos);
		case "notifychannelcreated": // CHANNEL CREATED
			return new ChannelCreateEvent(infos);
		case "notifychanneldeleted": // CHANNEL DELETED
			return new ChannelDeletedEvent(infos);
		case "notifychannelpasswordchanged": // CHANNEL PASSWORD CHANGED
			return new ChannelPasswordChangedEvent(infos);
		case "notifytextmessage": // TEXT MESSAGE SEND
			return new TextMessageEvent(infos);
		default:
			return null;
		}
	}

	/**
	 * debugs selected Events
	 * 
	 * @param eventName
	 * @param infos
	 * @see callNewEvent()
	 * @see QueryConfig
	 * @see DebugOutputType
	 */
	private void debugNewEvent(String eventName, String infos) {
		query.debug(DebugOutputType.EVENTMANAGER, eventName + " was called");

		if (eventName.equalsIgnoreCase("notifychannelcreated")) {
			query.debug(DebugOutputType.E_CHANNEL_CREATED, infos);
		} else if (eventName.equalsIgnoreCase("notifychanneldeleted")) {
			query.debug(DebugOutputType.E_CHANNEL_DELETED, infos);
		} else if (eventName.equalsIgnoreCase("notifychanneldescriptionchanged")) {
			query.debug(DebugOutputType.E_CHANNEL_DESCRIPTION_EDITED, infos);
		} else if (eventName.equalsIgnoreCase("notifychanneledited")) {
			query.debug(DebugOutputType.E_CHANNEL_EDITED, infos.toString());
		} else if (eventName.equalsIgnoreCase("notifychannelpasswordchanged")) {
			query.debug(DebugOutputType.E_CHANNEL_PASSWORD_CHANGED, infos);
		} else if (eventName.equalsIgnoreCase("notifycliententerview")) {
			query.debug(DebugOutputType.E_CLIENT_JOIN, infos);
		} else if (eventName.equalsIgnoreCase("notifyclientleftview")) {
			query.debug(DebugOutputType.E_CLIENT_LEAVE, infos);
		} else if (eventName.equalsIgnoreCase("notifyclientmoved")) {
			query.debug(DebugOutputType.E_CLIENT_MOVE, infos);
		} else if (eventName.equalsIgnoreCase("notifytokenused")) {
			query.debug(DebugOutputType.E_PRIVILEGEKEY_USED, infos);
		} else if (eventName.equalsIgnoreCase("notifyserveredited")) {
			query.debug(DebugOutputType.E_SERVER_EDITED, infos);
		} else if (eventName.equalsIgnoreCase("notifytextmessage")) {
			query.debug(DebugOutputType.E_TEXT_MESSAGE, infos);
		}

	}

}
