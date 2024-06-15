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
import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class FileInfo extends DefaultInfo {

	
	public FileInfo(String[] infos) {
		super(infos);
	}
	
	public FileInfo(String infos) {
		super(infos);
	}

	public int getChannelID() {
		return toIntI("cid");
	}
	
	public String getPath() {
		return Formatter.toNormalFormat(get("path"));
	}
	
	public String getName() {
		return Formatter.toNormalFormat(get("name"));
	}
	
	public long getSize() {
		return toLongI("size");
	}
	
	public long getDateTime() {
		return toLongI("datetime");
	}
	
	public int getFileTypeInt() {
		return toIntI("type");
	}
	
	public FileType getFileType() {
		return getFileTypeInt() == 0 ? FileType.FILE : FileType.DIRECTORY;
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("File[");
		resultBuilder.append("ChannelID=").append(getChannelID());
		resultBuilder.append(",Name=").append(getName());
		resultBuilder.append(",Size=").append(getSize());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
