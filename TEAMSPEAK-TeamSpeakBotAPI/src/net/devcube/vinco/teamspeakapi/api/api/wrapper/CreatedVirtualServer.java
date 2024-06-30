/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.04.2023
 * 
 * Uhrzeit : 12:09:44
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class CreatedVirtualServer extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedVirtualServer(String[] infos) {
		super(infos);
	}
	
	public CreatedVirtualServer(String infos) {
		super(infos);
	}
	
	public int getServerID() {
		return toIntI("sid");
	}
	
	public int getID() {
		return getServerID();
	}
	
	public int getPort() {
		return toIntI("virtualserver_port");
	}
	
	public String getKey() {
		return get("token");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("CreatedVirtualServer[");
		resultBuilder.append("ID=").append(getID());
		resultBuilder.append(",Port=").append(getPort());
		resultBuilder.append(",Key=").append(getKey());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
