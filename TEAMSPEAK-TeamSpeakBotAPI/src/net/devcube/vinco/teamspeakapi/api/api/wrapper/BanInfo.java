package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class BanInfo extends DefaultInfo {

	public BanInfo(String[] infos) {
		super(infos);
	}

	public int getID() {
		return getBanID();
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

	public String getInvokerName() {
		return Formatter.toNormalFormat(get("invokername"));
	}

	public int getInvokerClientDataBaseID() {
		return toInt(get("invokercldbid"));
	}

	public String getInvokerClientUUID() {
		return get("invokeruid");
	}

	public String getBanReason() {
		return Formatter.toNormalFormat(get("reason"));
	}

	public int getBanEnforcements() {
		return toInt(get("enforcements").replace("|", ""));
	}
	
	@Override
	public String toString() {
		return "Ban[UUID=" + getClientUUID() + ",ID=" + getBanID() + "]";
	}
}
