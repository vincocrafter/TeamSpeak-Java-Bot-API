package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.NameMode;
import net.devcube.vinco.teamspeakapi.api.api.property.ServerGroupType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ServerGroupInfo extends DefaultInfo {

	public ServerGroupInfo(String[] infos) {
		super(infos);
	}

	public ServerGroupInfo(String infos) {
		super(infos);
	}
	
	public int getServerGroupID() {
		return toInt(get("sgid"));
	}
	
	public int getID() {
		return getServerGroupID();
	}

	public String getServerGroupName() {
		return Formatter.toNormalFormat(get("name"));
	}
	
	public String getName() {
		return getServerGroupName();
	}
	

	private int getTypeID() {
		return toInt(get("type"));
	}

	public ServerGroupType getType() {
		int type = getTypeID();
		
		for (ServerGroupType servergrouptype : ServerGroupType.values()) {
			if (servergrouptype.getValue() == type) {
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
	
	public int getNameModeInt() {
		return toInt(get("namemode"));
	}
	
	public NameMode getNameMode() {
		int nameMode = getNameModeInt();
		for (NameMode modes : NameMode.values()) {
			if (modes.getValue() == nameMode) {
				return modes;
			}
		}
		return null;
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
		StringBuilder resultBuilder = new StringBuilder("ServerGroup[");
		resultBuilder.append("ID=").append(getID());
		resultBuilder.append(",Name=").append(getName());
		resultBuilder.append(",Type=").append(getType());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
