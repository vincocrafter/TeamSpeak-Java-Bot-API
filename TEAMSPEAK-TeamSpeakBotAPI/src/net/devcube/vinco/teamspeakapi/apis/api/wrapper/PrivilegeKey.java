package me.vinco.teamspeakapi.apis.api.wrapper;

import me.vinco.teamspeakapi.apis.api.property.PrivilegeKeyType;

public class PrivilegeKey extends _DefaultInfo {

	public PrivilegeKey(String[] infos) {
		super(infos);
	}

	public String getKey() {
		return this.get(0);
	}

	public PrivilegeKeyType getType() {
		int i = this.getTyp();
		for (PrivilegeKeyType privilegekeytype : PrivilegeKeyType.values()) {
			if (i == privilegekeytype.getIndex()) {
				return privilegekeytype;
			}
		}

		return null;
	}

	public int getServerGroup() {
		return this.toInt(this.get(2));
	}

	public int getChannelID() {
		return this.toInt(this.get(3));
	}

	public long getCreatedTime() {
		return this.toLong(this.get(4));
	}

	public String getDescription() {
		return this.get(5);
	}

	private int getTyp() {
		String s = this.infos[1];
		return Integer.parseInt(s.split("=")[1]);
	}
}
