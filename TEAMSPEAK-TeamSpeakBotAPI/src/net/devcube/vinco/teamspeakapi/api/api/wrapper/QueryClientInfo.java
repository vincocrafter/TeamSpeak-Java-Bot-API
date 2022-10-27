package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class QueryClientInfo extends DefaultInfo {

	public QueryClientInfo(String[] infos) {
		super(infos);
	}

	public String getNickName() {
		return this.get("client_nickname");
	}

	public String getClientName() {
		return this.getNickName();
	}

	public String getClientUUID() {
		return this.get("client_unique_identifie");
	}

	public int getClientID() {
		return toInt(get("client_id"));
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
}
