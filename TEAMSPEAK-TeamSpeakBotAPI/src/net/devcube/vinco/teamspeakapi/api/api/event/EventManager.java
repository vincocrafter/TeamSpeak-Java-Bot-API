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

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownEventException;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

public class EventManager {

	Ts3ServerQuery query;
	private List<TsEvent> events = new ArrayList<TsEvent>();

	public EventManager(Ts3ServerQuery query) {
		this.query = query;
	}

	public List<TsEvent> getEvents() {
		return events;
	}

	public void registerEvent(TsEvent event) throws UnknownEventException {
		if (event == null) {
			query.debug(DebugOutputType.ERROR, "The selected Event was null!");
			throw new UnknownEventException("The selected Event was null!");
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
			query.debug(DebugOutputType.ERROR, "The selected Event was null!");
			throw new UnknownEventException("The selected Event was null!");
		} else {
			if (events.contains(event)) {
				query.debug(DebugOutputType.EVENTMANAGER, "Unregistering Event : " + event.getClass().getName());
				events.remove(event);
			} else {
				query.debug(DebugOutputType.EVENTMANAGER, "Event was not found" + event.getClass().getName());
			}
		}
	}
	
	public void removeTs3Listener(TsEvent event) {
		if (event != null) {
			if (events.contains(event)) {
				query.debug(DebugOutputType.EVENTMANAGER, "Unregistering Event : " + event.getClass().getName());
				events.remove(event);
			} else {
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
	public synchronized void callEvent(String[] infos, String eventName) throws UnknownEventException {

		switch (eventName) {
		case "notifytokenused": // TOKEN USED EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onPrivilegeKeyUsed(new PrivilegeKeyUsedEvent(infos, query));
			}
			break;
		case "notifycliententerview": // CLIENT JOIN EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientJoin(new ClientJoinEvent(infos, query));
			}
			break;
		case "notifyclientleftview": // CLIENT LEAVE EVENT
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientLeave(new ClientLeaveEvent(infos, query));
			}
			break;
		case "notifychanneldescriptionchanged": // CHANNEL DESCRIPTION CHANGED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelDescriptionChanged(new ChannelDescriptionEditedEvent(infos, query));
			}
			break;
		case "notifychanneledited": // CHANNEL EDITED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelEdit(new ChannelEditedEvent(infos, query));
			}
			break;
		case "notifyserveredited": // SERVER EDITED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onServerEdit(new ServerEditedEvent(infos, query));
			}
			break;
		case "notifychannelmoved": //CHANNEL MOVED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelMoved(new ChannelMovedEvent(infos, query));
			}
			break;
		case "notifyclientmoved": // CLIENT MOVED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onClientMove(new ClientMoveEvent(infos, query));
			}
			break;
		case "notifychannelcreated": // CHANNEL CREATED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelCreate(new ChannelCreateEvent(infos, query));
			}
			break;
		case "notifychanneldeleted": // CHANNEL DELETED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelDeleted(new ChannelDeletedEvent(infos, query));
			}
			break;
		case "notifychannelpasswordchanged": // CHANNEL PASSWORD CHANGED
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onChannelPasswordChanged(new ChannelPasswordChangedEvent(infos, query));
			}

			break;
		case "notifytextmessage": // TEXT MESSAGE SEND
			for (TsEvent event : getEvents()) {
				if (event == null) {
					throw new UnknownEventException("One Event is null");
				}
				event.onTextMessage(new TextMessageEvent(infos, query));
			}
			break;
		default:
			throw new UnknownEventException("Event that is called is not found : " + eventName);
		}
	}

	/**
	 * New Method of calling Events, using Annotations
	 * 
	 * @param infos String[]
	 *                     eventInformation
	 */

	public synchronized void callNewEvent(String[] infos) {
		String eventName = infos[0];

		debugNewEvent(eventName, Formatter.connectString(infos)); // new debug Method for Event calling
		events.forEach(registeredEvents -> callNewEventClass(infos, registeredEvents));
	}
	
	/**
	 * Split Methods to call specified Event at any point for specific class.
	 * 
	 * @param infos Eventinformation given by the query
	 * @param registeredEvent Eventclass which should be invoked
	 */
	
	public void callNewEventClass(String[] infos, TsEvent registeredEvent) {
		BaseEvent event = getEventByName(infos);
		
		for (Method meth : registeredEvent.getClass().getDeclaredMethods()) {
			if (meth.isAnnotationPresent(EventHandler.class)) { // Check
				for (AnnotatedType ann1 : meth.getAnnotatedParameterTypes()) {
					if (ann1.getType().getTypeName().equals(event.getClass().getName())) { // Check for parameter name is equal to the
						// (Base)Event Class Name
						// Example -> public void test(PrivilegeKeyUsedEvent ev) {
						// PrivilegeKeyUsedEvent is the name of the parameter and the name of the Class
						try {
							meth.invoke(registeredEvent, getEventByName(infos));
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							query.debug(DebugOutputType.ERROR, "Got an Exception from calling Eventmethod '" + meth.getName() 
									+ "' caused by " + e.getCause().getClass().getName() + "!");
							e.printStackTrace();
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
	 * @param infos
	 *                     eventInformation
	 */

	private BaseEvent getEventByName(String[] infos) {
		String eventName = infos[0];
		switch (eventName) {
		case "notifytokenused": // TOKEN USED EVENT
			return new PrivilegeKeyUsedEvent(infos, query);
		case "notifycliententerview": // CLIENT JOIN EVENT
			return new ClientJoinEvent(infos, query);
		case "notifyclientleftview": // CLIENT LEAVE EVENT
			return new ClientLeaveEvent(infos, query);
		case "notifychanneldescriptionchanged": // CHANNEL DESCRIPTION CHANGED
			return new ChannelDescriptionEditedEvent(infos, query);
		case "notifychanneledited": // CHANNEL EDITED
			return new ChannelEditedEvent(infos, query);
		case "notifychannelmoved": //CHANNEL MOVED
			return new ChannelMovedEvent(infos, query);
		case "notifyserveredited": // SERVER EDITED
			return new ServerEditedEvent(infos, query);
		case "notifyclientmoved": // CLIENT MOVED
			return new ClientMoveEvent(infos, query);
		case "notifychannelcreated": // CHANNEL CREATED
			return new ChannelCreateEvent(infos, query);
		case "notifychanneldeleted": // CHANNEL DELETED
			return new ChannelDeletedEvent(infos, query);
		case "notifychannelpasswordchanged": // CHANNEL PASSWORD CHANGED
			return new ChannelPasswordChangedEvent(infos, query);
		case "notifytextmessage": // TEXT MESSAGE SEND
			return new TextMessageEvent(infos, query);
		default:
			return null;
		}
	}

	/**
	 * debugs selected Events
	 * 
	 * @param eventName
	 * @param infos
	 * @see EventManager#callNewEvent()
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
		} else if(eventName.equalsIgnoreCase("notifychannelmoved")) {
			query.debug(DebugOutputType.E_CHANNEL_MOVED, infos);
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
