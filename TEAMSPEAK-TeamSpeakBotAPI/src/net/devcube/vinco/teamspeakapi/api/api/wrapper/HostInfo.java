package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class HostInfo extends DefaultInfo {
	
	private Connection hostConnection;
	
	public HostInfo(String[] infos) {
		super(infos);
	}

	public HostInfo(String infos) {
		super(infos);
	}

	public int getInstanceUptime() {
		return toIntI("instance_uptime");
	}

	public int getVirtualServersRunning() {
		return toIntI("virtualservers_running_total");
	}

	public int getMaxClients() {
		return toIntI("virtualservers_total_maxclients");
	}

	public int getClientsOnline() {
		return toIntI("virtualservers_total_clients_online");
	}

	public int getChannels() {
		return toIntI("virtualservers_total_channels_online");
	}

	public String getTimestampUTC() {
		return get("host_timestamp_utc");
	}

	public int getFileTransfersBytesSend() {
		return toIntI("connection_filetransfer_bytes_sent_total");
	}

	public int getFileTransfersBytesReceived() {
		return toIntI("connection_filetransfer_bytes_received_total");
	}

	public Connection getConnection() {
		if (hostConnection == null) {
			this.hostConnection = new Connection("");
			List<String> copyInfos = new ArrayList<>();
			
			copyInfos.add("connection_filetransfer_bandwidth_sent");
			copyInfos.add("connection_filetransfer_bandwidth_received");
			copyInfos.add("connection_packets_sent_total");
			copyInfos.add("connection_bytes_sent_total");
			copyInfos.add("connection_packets_received_total");
			copyInfos.add("connection_bytes_received_total");
			copyInfos.add("connection_bandwidth_sent_last_second_total");
			copyInfos.add("connection_bandwidth_sent_last_minute_total");
			copyInfos.add("connection_bandwidth_received_last_second_total");
			copyInfos.add("connection_bandwidth_received_last_minute_total");

			for (String infos : copyInfos) {
				copyInfoTo(infos, hostConnection, infos);
			}
			
			copyInfoTo("instance_uptime", hostConnection, "connection_connected_time");
		}
		return hostConnection;
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("HostInfo[");
		resultBuilder.append("InstanceUptime=").append(getInstanceUptime());
		resultBuilder.append(",VirtualServersRunning=").append(getVirtualServersRunning());
		resultBuilder.append(",MaxClients=").append(getMaxClients());
		resultBuilder.append(",ClientsOnline=").append(getClientsOnline());
		resultBuilder.append(",Channels=").append(getChannels());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
