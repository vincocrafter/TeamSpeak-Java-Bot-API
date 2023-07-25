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

public class CreatedVirtualServer extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedVirtualServer(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}
	
	public int getID() {
		return toInt(get("sid"));
	}
	
	public int getPort() {
		return toInt(get("virtualserver_port"));
	}
	
	public String getKey() {
		return get("token");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("CreateVirtualServer[");
		resultBuilder.append("ID=" + getID());
		resultBuilder.append(",Port=" + getPort());
		resultBuilder.append(",Key=" + getKey());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
