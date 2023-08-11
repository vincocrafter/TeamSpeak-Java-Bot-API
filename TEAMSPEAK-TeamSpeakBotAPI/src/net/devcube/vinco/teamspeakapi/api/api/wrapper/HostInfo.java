package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class HostInfo extends DefaultInfo {

	public HostInfo(String[] infos) {
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
		resultBuilder.append("InstanceUptime=" + getInstanceUptime());
		resultBuilder.append(",VirtualServersRunning=" + getVirtualServersRunning());
		resultBuilder.append(",MaxClients=" + getMaxClients());
		resultBuilder.append(",ClientsOnline=" + getClientsOnline());
		resultBuilder.append(",Channels=" + getChannels());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
}
