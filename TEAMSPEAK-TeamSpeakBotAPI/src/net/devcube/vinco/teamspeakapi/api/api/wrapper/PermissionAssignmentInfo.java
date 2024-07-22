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

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class PermissionAssignmentInfo extends DefaultInfo {

	public PermissionAssignmentInfo(String infos) {
		super(infos);
	}
	
	public int getTier() {
		return toIntI("t");
	}
	
	public int getFirstID() {
		return toIntI("id1");
	}
	
	public int getSecondID() {
		return toIntI("id2");
	}
	
	public int getPermID() {
		return toIntI("p");
	}
	
	public int getPermValue() {
		return toIntI("v");
	}
	
	public boolean isNegated() {
		return toBolI("n");
	}
	
	public boolean isSkip() {
		return toBolI("s");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("PermissionAssignment[");
		resultBuilder.append("Tier=").append(getTier());
		resultBuilder.append(",FirstID=").append(getFirstID());
		resultBuilder.append(",SecondID=").append(getSecondID());
		resultBuilder.append(",PermID=").append(getPermID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
