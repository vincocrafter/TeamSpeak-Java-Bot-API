/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 15.06.2024
 * 
 * Uhrzeit : 11:13:25
 */
package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class Connection extends DefaultInfo {
	
	public Connection(String[] infos) {
		super(infos);
	}
	
	public Connection(String infos) {
		super(infos);
	}
	
	public int getFileTransfersBandwidthSend() {
		return toIntI("connection_filetransfer_bandwidth_sent");
	}
	
	public int getFileTransfersBandwidthReceived() {
		return toIntI("connection_filetransfer_bandwidth_received");
	}
	
	public int getPacketsSendTotal() {
		return toIntI("connection_packets_sent_total");
	}
	
	public int getBytesSendTotal() {
		return toIntI("connection_bytes_sent_total");
	}
	
	public int getPacketsReceivedTotal() {
		return toIntI("connection_packets_received_total");
	}
	
	public int getBytesReceivedTotal() {
		return toIntI("connection_bytes_received_total");
	}
	
	public int getBranwidthSendLastSecondTotal() {
		return toIntI("connection_bandwidth_sent_last_second_total");
	}
	
	public int getBranwidthSendLastMinuteTotal() {
		return toIntI("connection_bandwidth_sent_last_minute_total");
	}
	
	public int getBranwidthReceivedLastSecondTotal() {
		return toIntI("connection_bandwidth_received_last_second_total");
	}
	
	public int getBranwidthReceivedLastMinuteTotal() {
		return toIntI("connection_bandwidth_received_last_minute_total");
	}
	
	public long getConnectedTime() {
		return toLongI("connection_connected_time");
	}
	
}
