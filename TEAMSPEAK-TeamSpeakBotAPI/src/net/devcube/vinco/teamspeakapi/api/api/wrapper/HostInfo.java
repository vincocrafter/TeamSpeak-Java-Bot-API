package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class HostInfo extends DefaultInfo {

	public HostInfo(String[] infos) {
		super(infos);
	}

	public HostInfo(String infos) {
		super(infos);
	}

	public int getInstanceUptime() {
		return toInt(get("instance_uptime"));
	}

	public int getVirtualServersRunning() {
		return toInt(get("virtualservers_running_total"));
	}

	public int getMaxClients() {
		return toInt(get("virtualservers_total_maxclients"));
	}

	public int getClientsOnline() {
		return toInt(get("virtualservers_total_clients_online"));
	}

	public int getChannels() {
		return toInt(get("virtualservers_total_channels_online"));
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
