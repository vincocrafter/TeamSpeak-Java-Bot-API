package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ComplainInfo extends DefaultInfo {

	public ComplainInfo(String[] infos) {
		super(infos);
	}
	
	public ComplainInfo(String infos) {
		super(infos);
	}

	public int getTargetClientDataBaseID() {
		return toInt(get("tcldbid"));
	}

	public String getTargetName() {
		return get("tname");
	}

	public int getSenderClientDataBaseID() {
		return toInt(get("fcldbid"));
	}

	public String getSenderName() {
		return Formatter.toNormalFormat(get("fname"));
	}

	public String getMessage() {
		return Formatter.toNormalFormat(get("message"));
	}

	public long getTime() {
		return toLong(get("timestamp"));
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Complain[");
		result.append("TargetName=").append(getTargetName());
		result.append(",TargetDBID=").append(getTargetClientDataBaseID());
		result.append(",SenderName=").append(getSenderName());
		result.append(",SenderDBID=").append(getSenderClientDataBaseID());
		result.append(",Message=").append(getMessage());
		result.append("]");
		return  result.toString();
	}
}
