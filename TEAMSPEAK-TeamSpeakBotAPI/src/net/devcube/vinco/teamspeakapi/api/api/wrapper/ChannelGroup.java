package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelGroupType;

public class ChannelGroup extends DefaultInfo {

	public ChannelGroup(String[] infos) {
		super(infos);
	}

	public int getID() {
		return toInt(get(0));
	}

	public String getName() {
		return get(1).replace("\\s", " ");
	}

	private int getTypeID() {
		return toInt(get(2));
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
		return toBol(toInt(get(4)));
	}

	public int getNeededModifyPower() {
		return toInt(get(7));
	}

	public int getNeededMemberAddPower() {
		return toInt(get(8));
	}

	public int getNeededMemberRemovePower() {
		return toInt(get(9));
	}
}
