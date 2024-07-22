package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ComplaintInfo extends DefaultInfo {

	public ComplaintInfo(String infos) {
		super(infos);
	}

	public int getTargetClientDataBaseID() {
		return toIntI("tcldbid");
	}

	public String getTargetName() {
		return Formatter.toNormalFormat(get("tname"));
	}

	public int getSenderClientDataBaseID() {
		return toIntI("fcldbid");
	}

	public String getSenderName() {
		return Formatter.toNormalFormat(get("fname"));
	}

	public String getMessage() {
		return Formatter.toNormalFormat(get("message"));
	}

	public long getTime() {
		return toLongI("timestamp");
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
