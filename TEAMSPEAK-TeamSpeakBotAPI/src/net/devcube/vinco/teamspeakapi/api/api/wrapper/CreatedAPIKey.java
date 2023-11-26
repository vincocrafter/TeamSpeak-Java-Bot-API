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
	public CreatedAPIKey(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}

	public String getAPIKey() {
		return get("apikey");
	}

	public int getID() {
		return toInt(get("id"));
	}

	public int getServerID() {
		return toInt(get("sid"));
	}

	public int getClientDataBaseID() {
		return toInt(get("cldbid"));
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
		return toLong(get("created_at"));
	}
	
	public long getTimeExpire() {
		return toLong(get("expires_at"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("CreatedAPIKey[");
		resultBuilder.append("APIKey=" + getAPIKey());
		resultBuilder.append(",ID=" + getID());
		resultBuilder.append(",ServerID=" + getServerID());
		resultBuilder.append(",ClientDataBaseID=" + getClientDataBaseID());
		resultBuilder.append(",Scope=" + getScope().getValue());
		resultBuilder.append(",TimeLeft=" + getTimeLeft());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
