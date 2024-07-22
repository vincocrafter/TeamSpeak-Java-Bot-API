package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class ConnectionInfo extends Connection {

	public ConnectionInfo(String infos) {
		super(infos);
	}

	public int getFileTransfersBytesSend() {
		return toIntI("connection_filetransfer_bytes_sent_total");
	}

	public int getFileTransfersBytesReceived() {
		return toIntI("connection_filetransfer_bytes_received_total");
	}
	
	public double getPacketlossTotal() {
		return toDoubleI("connection_packetloss_total");
	}
	
	public double getPing() {
		return toDoubleI("connection_ping");
	}
}
