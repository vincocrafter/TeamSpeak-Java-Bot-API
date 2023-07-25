/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.04.2023
 * 
 * Uhrzeit : 11:07:18
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class PermissionAssignmentInfo extends DefaultInfo {

	/**
	 * @param infos
	 */
	public PermissionAssignmentInfo(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}
	
	public int getTier() {
		return toInt(get("t"));
	}
	
	public int getFirstID() {
		return toInt(get("id1"));
	}
	
	public int getSecondID() {
		return toInt(get("id2"));
	}
	
	public int getPermID() {
		return toInt(get("p"));
	}
	
	public int getPermValue() {
		return toInt(get("v"));
	}
	
	public boolean isNegated() {
		return toBol(get("n"));
	}
	
	public boolean isSkip() {
		return toBol(get("s"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("PermissionAssignment[");
		resultBuilder.append("Tier=" + getTier());
		resultBuilder.append(",FirstID=" + getFirstID());
		resultBuilder.append(",SecondID=" + getSecondID());
		resultBuilder.append(",PermID=" + getPermID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
