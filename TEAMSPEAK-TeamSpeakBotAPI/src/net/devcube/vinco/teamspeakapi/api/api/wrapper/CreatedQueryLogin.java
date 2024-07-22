/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.04.2023
 * 
 * Uhrzeit : 11:49:52
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class CreatedQueryLogin extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedQueryLogin(String infos) {
		super(infos);
	}
	
	public int getClientDataBaseID() {
		return toIntI("cldbid");
	}
	
	public int getVirtualServerID() {
		return toIntI("sid");
	}
	
	public int getServerID() {
		return getVirtualServerID();
	}
	
	public String getClientLoginName() {
		return Formatter.toNormalFormat(get("client_login_name"));
	}
	
	public String getClientLoginPassword() {
		return get("client_login_password");
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("CreateQueryLogin[");
		result.append("ClientDBID=").append(getClientDataBaseID());
		result.append(",VirtualServerID=").append(getVirtualServerID());
		result.append(",LoginName=").append(getClientLoginName());
		result.append(",LoginPassword=").append(getClientLoginPassword());
		result.append("]");
		return  result.toString();
	}
	
}
