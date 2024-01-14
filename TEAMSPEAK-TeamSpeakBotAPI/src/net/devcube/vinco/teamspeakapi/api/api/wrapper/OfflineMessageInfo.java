package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class OfflineMessageInfo extends DefaultInfo {

	public OfflineMessageInfo(String[] infos) {
		super(infos);
	}
	
	public OfflineMessageInfo(String infos) {
		super(infos);
	}

	public int getMessageID() {
		return toInt(get("msgid"));
	}
	
	public int getID() {
		return getMessageID();
	}

	public String getClientUUID() {
		return get("cluid");
	}

	public String getSubject() {
		return Formatter.toNormalFormat(get("subject"));
	}

	public String getMessage() {
		return Formatter.toNormalFormat(get("message"));
	}

	public long getTime() {
		return toLong(get("time"));
	}
	
	public boolean hasRead() {
		return toBol(get("flag_read"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("OfflineMessage[");
		resultBuilder.append("MessageID=").append(getMessageID());
		resultBuilder.append(",ClientUUID=").append(getClientUUID());
		resultBuilder.append(",Subject=").append(getSubject());
		resultBuilder.append(",Message=").append(getMessage());
		resultBuilder.append(",Time=").append(getTime());
		resultBuilder.append(",Read=").append(hasRead());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
