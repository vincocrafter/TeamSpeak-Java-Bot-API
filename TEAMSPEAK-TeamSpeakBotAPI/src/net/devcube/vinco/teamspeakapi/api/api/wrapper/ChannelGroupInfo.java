package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;
import net.devcube.vinco.teamspeakapi.api.api.property.NameMode;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class ChannelGroupInfo extends DefaultInfo {

	public ChannelGroupInfo(String[] infos) {
		super(infos);
	}
	
	public ChannelGroupInfo(String infos) {
		super(infos);
	}
	
	public int getID() {
		return getChannelGroupID();
	}
	
	public int getId() {
		return getChannelGroupID();
	}
	
	public int getChannelGroupID() {
		return toIntI("cgid");
	}
	
	public String getName() {
		return getChannelGroupName();
	}

	public String getChannelGroupName() {
		return Formatter.toNormalFormat(get("name"));
	}
	
	private int getTypeID() {
		return toIntI("type");
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
		return toBolI("savedb");
	}
	
	public int getSortID() {
		return toIntI("sortid");
	}
	
	public int getNameModeInt() {
		return toIntI("namemode");
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
		return toIntI("n_modifyp");
	}

	public int getNeededMemberAddPower() {
		return toIntI("n_member_addp");
	}

	public int getNeededMemberRemovePower() {
		return toIntI("n_member_removep");
	}
	
	public int getIconID() {
		return toIntI("iconid");
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder("ChannelGroup[");
		result.append("ID=").append(getID());
		result.append(",Name=").append(getName());
		result.append(",Type=").append(getType());
		result.append("]");
		return  result.toString();
		
	}
}
