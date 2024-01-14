/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.04.2023
 * 
 * Uhrzeit : 18:07:56
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class TempPasswordInfo extends DefaultInfo {

	/**
	 * @param infos
	 */
	public TempPasswordInfo(String[] infos) {
		super(infos);
	}
	
	public TempPasswordInfo(String infos) {
		super(infos);
	}
	
	public String getInvokerName() {
		return Formatter.toNormalFormat(get("nickname"));
	}
	
	public String getInvokerUUID() {
		return get("uid");
	}
	
	public String getDescription() {
		return Formatter.toNormalFormat(get("desc"));
	}
	
	public String getPassword() {
		return Formatter.toNormalFormat(get("pw_clear"));
	}
	
	public long getStartTime() {
		return toLong(get("start"));
	}
	
	public long getEndTime() {
		return toLong(get("end"));
	}
	
	public int getTargetChannelID() {
		return toInt(get("tcid"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("TempPassword[");
		resultBuilder.append("InvokerName=").append(getInvokerName());
		resultBuilder.append(",InvokerUUID=").append(getInvokerUUID());
		resultBuilder.append(",Password=").append(getPassword());
		resultBuilder.append(",StartTime=").append(getStartTime());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
