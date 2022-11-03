package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class ConnectionInfo extends DefaultInfo {

	public ConnectionInfo(String[] infos) {
		super(infos);
	}

	public int getFileTransfersBandwidthSend() {
		return toInt(get("connection_filetransfer_bandwidth_sent"));
	}

	public int getFileTransfersBandwidthReceived() {
		return toInt(get("connection_filetransfer_bandwidth_received"));
	}

	public int getFileTransfersBytesSend() {
		return toInt(get("connection_filetransfer_bytes_sent_total"));
	}

	public int getFileTransfersBytesReceived() {
		return toInt(get("connection_filetransfer_bytes_received_total"));
	}
	
	public int getPacketsSendTotal() {
		return toInt(get("connection_packets_sent_total"));
	}
	
	public int getBytesSendTotal() {
		return toInt(get("connection_bytes_sent_total"));
	}
 	
	public int getPacketsReceivedTotal() {
		return toInt(get("connection_packets_received_total"));
	}
	
	public int getBytesReceivedTotal() {
		return toInt(get("connection_bytes_received_total"));
	}
	
	public int getBranwidthSendLastSecondTotal() {
		return toInt(get("connection_bandwidth_sent_last_second_total"));
	}
	
	public int getBranwidthSendLastMinuteTotal() {
		return toInt(get("connection_bandwidth_sent_last_minute_total"));
	}
	
	public int getBranwidthReceivedLastSecondTotal() {
		return toInt(get("connection_bandwidth_received_last_second_total"));
	}
	
	public int getBranwidthReceivedLastMinuteTotal() {
		return toInt(get("connection_bandwidth_received_last_minute_total"));
	}
	
	public long getConnectedTime() {
		return toLong(get("connection_connected_time"));
	}
	
	public double getPacketlossTotal() {
		return toDouble(get("connection_packetloss_total"));
	}
	
	public double getPing() {
		return toDouble(get("connection_ping"));
	}
}
