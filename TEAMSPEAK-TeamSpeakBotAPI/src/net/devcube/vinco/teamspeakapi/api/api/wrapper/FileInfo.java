/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.04.2023
 * 
 * Uhrzeit : 12:55:12
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.property.FileType;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class FileInfo extends DefaultInfo {

	
	public FileInfo(String[] infos) {
		super(infos);
	}

	public int getChannelID() {
		return toInt(get("cid"));
	}
	
	public String getPath() {
		return Formatter.toNormalFormat(get("path"));
	}
	
	public String getName() {
		return Formatter.toNormalFormat(get("name"));
	}
	
	public long getSize() {
		return toLong(get("size"));
	}
	
	public long getDateTime() {
		return toLong(get("datetime"));
	}
	
	public int getFileTypeInt() {
		return toInt(get("type"));
	}
	
	public FileType getFileType() {
		return getFileTypeInt() == 0 ? FileType.FILE : FileType.DIRECTORY ;
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("File[");
		resultBuilder.append("ChannelID=" + getChannelID());
		resultBuilder.append(",Path=" + getPath());
		resultBuilder.append(",Name=" + getName());
		resultBuilder.append(",Size=" + getSize());
		resultBuilder.append(",Type=" + getFileType());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
