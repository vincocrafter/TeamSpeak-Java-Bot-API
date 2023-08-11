package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ServerGroupInfo extends DefaultInfo {

	public ServerGroupInfo(String[] infos) {
		super(infos);
	}

	public int getID() {
		return toInt(get("sgid"));
	}

	public String getName() {
		return Formatter.toNormalFormat(get("name"));
	}

	private int getTypeID() {
		return toInt(get("type"));
	}

	public ServerGroupType getType() {
		for (ServerGroupType servergrouptype : ServerGroupType.values()) {
			if (getTypeID() == servergrouptype.getValue()) {
				return servergrouptype;
			}
		}

		return null;
	}

	public long getIconID() {
		return toLong(get("iconid"));
	}

	public boolean isSaved() {
		return toBol(Integer.parseInt(get("savedb")));
	}

	public int getSortID() {
		return toInt(get("sortid"));
	}
	
	public int getNameMode() {
		return toInt(get("namemode"));
	}
	
	public int getNeededModifyPower() {
		return toInt(get("n_modifyp"));
	}

	public int getNeededMemberAddPower() {
		return toInt(get("n_member_addp"));
	}

	public int getNeededMemberRemovePower() {
		return toInt(get("n_member_removep").replace("|", ""));
	}

	public String toString() {
		return "ServerGroup[Name=" + getName() + ",ID=" + getID() + "]";
	}
}
