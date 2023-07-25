package net.devcube.vinco.teamspeakapi.api.api.events;

import net.devcube.vinco.teamspeakapi.api.api.event.BaseEvent;
import net.devcube.vinco.teamspeakapi.api.api.property.TextMessageType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class TextMessageEvent extends BaseEvent {

	public TextMessageEvent(String[] infos, Ts3ServerQuery serverQuery) {
		super(infos, serverQuery);
	}

	public int getClientID() {
		return toInt(get("invokerid"));
	}

	public String getClientName() {
		return get("invokername");
	}

	public int getTargetMode() {
		return toInt(get("targetmode"));
	}

	public String getMessage() {
		return Formatter.toNormalFormat(get("msg"));
	}

	public TextMessageType getTextMessageType() {
		for (TextMessageType type : TextMessageType.values()) {
			if (getTargetMode() == type.getValue()) {
				return type;
			}
		}
		return null;
	}

	public int getTargetID() {
		return toInt(get("target"));
	}
}
