package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class QueryClientInfo extends DefaultInfo {

	public QueryClientInfo(String[] infos) {
		super(infos);
	}

	public String getNickName() {
		return getName();
	}

	public String getName() {
		return get("client_nickname");
	}

	public String getUUID() {
		return get("client_unique_identifier");
	}

	public int getID() {
		return toInt(get("client_id"));
	}
	
	public int getClientID() {
		return getID();
	}
	
	public int getChannelID() {
		return toInt(get("client_channel_id"));
	}
		
	public int getDataBaseID() {
		return toInt(get("client_database_id"));
	}
	
	public int getVirtualServerID() {
		return toInt(get("virtualserver_id"));
	}
	
	public int getVirtualServerPort() {
		return toInt(get("virtualserver_port"));
	}
	
	public String getVirtualServerUUID() {
		return get("virtualserver_unique_identifier");
	}
	
	@Override
	public String toString() {
		StringBuilder resultBuilder = new StringBuilder("QueryClient[");
		resultBuilder.append("Name=" + getName());
		resultBuilder.append(",ID=" + getClientID());
		resultBuilder.append(",UUID=" + getUUID());
		resultBuilder.append(",VirtualServerID=" + getVirtualServerID());
		resultBuilder.append("]");
		return  resultBuilder.toString();
	}
	
}
