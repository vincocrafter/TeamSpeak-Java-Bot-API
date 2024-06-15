package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;

public class QueryClientInfo extends DefaultInfo {

	public QueryClientInfo(String[] infos) {
		super(infos);
	}

	/**
	 * @param infos
	 */
	public QueryClientInfo(String infos) {
		super(infos);
	}

	public String getNickName() {
		return get("client_nickname");
	}

	public String getName() {
		return getNickName();
	}

	public String getUUID() {
		return get("client_unique_identifier");
	}
	
	public String getUniqueIdentifier() {
		return getUUID();
	}

	public int getID() {
		return getClientID();
	}
	
	public int getId() {
		return getClientID();
	}
	
	public int getClientID() {
		return toIntI("client_id");
	}
	
	public int getChannelID() {
		return toIntI("client_channel_id");
	}
		
	public int getDataBaseID() {
		return toIntI("client_database_id");
	}
	
	public int getVirtualServerID() {
		return toIntI("virtualserver_id");
	}
	
	public int getVirtualServerPort() {
		return toIntI("virtualserver_port");
	}
	
	public String getVirtualServerUUID() {
		return get("virtualserver_unique_identifier");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("QueryClient[");
		resultBuilder.append("Name=").append(getName());
		resultBuilder.append(",ID=").append(getClientID());
		resultBuilder.append(",UUID=").append(getUUID());
		resultBuilder.append(",VirtualServerID=").append(getVirtualServerID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
