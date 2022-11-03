package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class Complain extends DefaultInfo {

	public Complain(String[] infos) {
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
}
