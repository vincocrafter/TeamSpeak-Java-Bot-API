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

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class FileTransferInfo extends DefaultInfo {

	/**
	 * @param infos
	 */
	public FileTransferInfo(String[] infos) {
		super(infos);
		// TODO Auto-generated constructor stub
	}

	public int getClientID() {
		return toInt(get("clid"));
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
		return toLong(get("size"));
	}
	
	public long getSizeDone() {
		return toLong(get("sizedone"));
	}
	
	public int getClientFileTransferID() {
		return toInt(get("clientftfid"));
	}
	
	public int getServerFileTransferID() {
		return toInt(get("serverftfid"));
	}
	
	public int getStatus() {
		return toInt(get("status"));
	}
	
	public double getCurrentSpeed() {
		return toDouble(get("current_speed"));
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("FileTransfer[");
		resultBuilder.append("ClientID=" + getClientID());
		resultBuilder.append(",ChannelID=" + getChannelID());
		resultBuilder.append(",ClientFTID=" + getClientFileTransferID());
		resultBuilder.append(",ServerFTID=" + getServerFileTransferID());
		resultBuilder.append(",Size=" + getSize());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
