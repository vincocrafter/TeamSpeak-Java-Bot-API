package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class Ban extends DefaultInfo {

	public Ban(String[] infos) {
		super(infos);
	}

	public int getBanID() {
		return toInt(get("banid"));
	}

	public String getIP() {
		return get("ip");
	}

	public String getClientUUID() {
		return get("uid");
	}
	
	public String getMyTeamSpeakID() {
		return get("mytsid");
	}
	
	public String getLastNickName() {
		return get("lastnickname");
	}

	public long getCreatingTime() {
		return toLong(get("created"));
	}

	public int getBanDuration() {
		return toInt(get("duration"));
	}

	public String getBannerName() {
		return Formatter.toNormalFormat(get("invokername"));
	}

	public int getBannerClientDataBaseID() {
		return toInt(get("invokercldbid"));
	}

	public String getBannerClientUUID() {
		return get("invokeruid");
	}

	public String getBanReason() {
		return Formatter.toNormalFormat(get("reason"));
	}

	public int getBanEnforcements() {
		return toInt(get("enforcements").replace("|", ""));
	}
}
