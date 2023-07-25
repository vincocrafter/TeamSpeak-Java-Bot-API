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

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class CreatedQueryLogin extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedQueryLogin(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}
	
	public int getClientDataBaseID() {
		return toInt(get("cldbid"));
	}
	
	public int getVirtualServerID() {
		return toInt(get("sid"));
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
		StringBuilder resultBuilder = new StringBuilder("CreateQueryLogin[");
		resultBuilder.append("ClientDBID=" + getClientDataBaseID());
		resultBuilder.append(",VirtualServerID=" + getVirtualServerID());
		resultBuilder.append(",LoginName=" + getClientLoginName());
		resultBuilder.append(",LoginPassword=" + getClientLoginPassword());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
