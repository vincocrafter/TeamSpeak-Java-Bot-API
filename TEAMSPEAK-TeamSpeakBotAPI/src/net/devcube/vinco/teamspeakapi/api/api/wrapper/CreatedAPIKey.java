/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 26.11.2023
 * 
 * Uhrzeit : 15:48:32
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.APIScope;
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class CreatedAPIKey extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedAPIKey(String infos) {
		super(infos);
	}

	public String getAPIKey() {
		return get("apikey");
	}

	public int getID() {
		return toIntI("id");
	}

	public int getServerID() {
		return toIntI("sid");
	}

	public int getClientDataBaseID() {
		return toIntI("cldbid");
	}

	public APIScope getScope() {
		String val = get("scope");
		for (APIScope scope : APIScope.values()) {
			if (scope.getValue().equalsIgnoreCase(val))
				return scope;
		}
		return null;
	}

	public long getTimeLeft() {
		String val = get("time_left");
		if (val.equalsIgnoreCase("unlimited"))
			return 0;
		return toLong(val);
	}
	
	public long getTimeCreated() {
		return toLongI("created_at");
	}
	
	public long getTimeExpire() {
		return toLongI("expires_at");
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("CreatedAPIKey[");
		result.append("APIKey=").append(getAPIKey());
		result.append(",ID=").append(getID());
		result.append(",ServerID=").append(getServerID());
		result.append(",ClientDataBaseID=").append(getClientDataBaseID());
		result.append(",Scope=").append(getScope().getValue());
		result.append(",TimeLeft=").append(getTimeLeft());
		result.append("]");
		return  result.toString();
	}
	
}
