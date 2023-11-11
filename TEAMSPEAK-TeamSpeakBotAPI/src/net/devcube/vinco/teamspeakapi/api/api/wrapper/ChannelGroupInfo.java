package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ChannelGroupInfo extends DefaultInfo {

	public ChannelGroupInfo(String[] infos) {
		super(infos);
	}
	
	public int getID() {
		return getChannelGroupID();
	}
	
	public int getChannelGroupID() {
		return toInt(get("cgid"));
	}
	
	public String getName() {
		return getChannelGroupName();
	}

	public String getChannelGroupName() {
		return Formatter.toNormalFormat(get("name"));
	}
	
	private int getTypeID() {
		return toInt(get("type"));
	}

	public ChannelGroupType getType() {
		for (ChannelGroupType type : ChannelGroupType.values()) {
			if (getTypeID() == type.getValue()) {
				return type;
			}
		}

		return null;
	}

	public boolean isSaved() {
		return toBol(toInt(get("savedb")));
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
		return toInt(get("n_member_removep"));
	}
	
	public String toString() {
		return "ChannelGroup[Name=" + getName() + ",ID=" + getID() + "]";
	}
}
