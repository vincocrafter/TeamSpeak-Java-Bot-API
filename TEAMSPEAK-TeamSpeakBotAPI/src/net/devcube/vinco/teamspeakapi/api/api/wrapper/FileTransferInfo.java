/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.04.2023
 * 
 * Uhrzeit : 13:14:05
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class FileTransferInfo extends DefaultInfo {

	public FileTransferInfo(String infos) {
		super(infos);
	}

	public int getClientID() {
		return toIntI("clid");
	}
	
	public String getFilePath() {
		return Formatter.toNormalFormat(get("path"));
	}
	
	public int getVirtualServerID() {
		return toInt(getFilePath().split("virtualserver_")[1].split("/")[0]);
	}
	
	public int getChannelID() {
		return toInt(getFilePath().split("channel_")[1].split(" ")[0]);
	}
	
	public String getName() {
		return get("name");
	}
	
	public long getSize() {
		return toLongI("size");
	}
	
	public long getSizeDone() {
		return toLongI("sizedone");
	}
	
	public int getClientFileTransferID() {
		return toIntI("clientftfid");
	}
	
	public int getServerFileTransferID() {
		return toIntI("serverftfid");
	}
	
	public int getStatus() {
		return toIntI("status");
	}
	
	public double getCurrentSpeed() {
		return toDoubleI("current_speed");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("FileTransfer[");
		resultBuilder.append("ClientID=").append(getClientID());
		resultBuilder.append(",ChannelID=").append(getChannelID());
		resultBuilder.append(",ClientFTID=").append(getClientFileTransferID());
		resultBuilder.append(",ServerFTID=").append(getServerFileTransferID());
		resultBuilder.append(",Size=").append(getSize());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
