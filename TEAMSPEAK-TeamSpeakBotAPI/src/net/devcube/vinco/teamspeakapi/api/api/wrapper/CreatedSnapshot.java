/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 26.11.2023
 * 
 * Uhrzeit : 15:24:12
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class CreatedSnapshot extends DefaultInfo {

	/**
	 * @param infos
	 */
	public CreatedSnapshot(String[] infos) {
		super(infos);
	}
	
	/**
	 * @param infos
	 */
	public CreatedSnapshot(String infos) {
		super(infos);
	}
	
	public int getVersion() {
		return toInt(get("version"));
	}
	
	public String getSalt() {
		return get("salt");
	}
	
	public String getData() {
		return get("data");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("CreatedSnapshot[");
		resultBuilder.append("Version=" + getVersion());
		resultBuilder.append(",Salt=" + getSalt());
		resultBuilder.append(",Data=" + getData());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
