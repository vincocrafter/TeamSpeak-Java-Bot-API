package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class OfflineMessageInfo extends DefaultInfo {

	public OfflineMessageInfo(String[] infos) {
		super(infos);
	}

	public int getMessageID() {
		return toInt(get("msgid"));
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
		resultBuilder.append("MessageID=" + getMessageID());
		resultBuilder.append(",ClientUUID=" + getClientUUID());
		resultBuilder.append(",Subject=" + getSubject());
		resultBuilder.append(",Message=" + getMessage());
		resultBuilder.append(",Time=" + getTime());
		resultBuilder.append(",Read=" + hasRead());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
