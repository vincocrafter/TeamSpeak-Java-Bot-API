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
		return toIntI("token_id1");
	}

	public int getChannelID() {
		return toIntI("token_id2");
	}

	public long getCreatedTime() {
		return toLongI("token_created");
	}

	public String getDescription() {
		return get("token_description");
	}

	public int getKeyType() {
		return toIntI("token_type");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("PrivilegeKey[");
		resultBuilder.append("Key=").append(getKey());
		resultBuilder.append(",Type=").append(getType());
		resultBuilder.append(",ServerGroupID=").append(getServerGroup());
		resultBuilder.append(",ChannelID=").append(getChannelID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
