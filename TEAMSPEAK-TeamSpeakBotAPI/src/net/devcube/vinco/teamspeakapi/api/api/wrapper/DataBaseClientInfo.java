package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class DataBaseClientInfo extends DefaultInfo {

	public DataBaseClientInfo(String[] infos) {
		super(infos);
	}

	public String getClientUUID() {
		return get("client_unique_identifier");
	}

	public int getClientDataBaseID() {
		return toInt(get("client_database_id"));
	}

	public String getClientName() {
		return get("client_nickname");
	}

	public long getCreatingTime() {
		return toLong(get("client_created"));
	}

	public long getLastConnection() {
		return toLong(get("client_lastconnected"));
	}

	public int getConnections() {
		return toInt(get("client_totalconnections"));
	}

	public String getLastIP() {
		return get("client_lastip");
	}

	public String getDescription() {
		return get("client_description");
	}
}
