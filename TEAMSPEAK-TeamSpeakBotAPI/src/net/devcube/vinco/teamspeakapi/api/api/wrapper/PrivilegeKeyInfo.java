package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.PrivilegeKeyType;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class PrivilegeKeyInfo extends DefaultInfo {

	public PrivilegeKeyInfo(String[] infos) {
		super(infos);
	}
	
	public PrivilegeKeyInfo(String infos) {
		super(infos);
	}
	
	public String getKey() {
		return get("token");
	}

	public PrivilegeKeyType getType() {
		int i = getKeyType();
		for (PrivilegeKeyType privilegekeytype : PrivilegeKeyType.values()) {
			if (i == privilegekeytype.getValue()) {
				return privilegekeytype;
			}
		}

		return null;
	}

	public int getServerGroup() {
		return toInt(get("token_id1"));
	}

	public int getChannelID() {
		return toInt(get("token_id2"));
	}

	public long getCreatedTime() {
		return toLong(get("token_created"));
	}

	public String getDescription() {
		return get("token_description");
	}

	public int getKeyType() {
		return toInt(get("token_type"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("PrivilegeKey[");
		resultBuilder.append("Key=" + getKey());
		resultBuilder.append(",Type=" + getType());
		resultBuilder.append(",ServerGroupID=" + getServerGroup());
		resultBuilder.append(",ChannelID=" + getChannelID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}