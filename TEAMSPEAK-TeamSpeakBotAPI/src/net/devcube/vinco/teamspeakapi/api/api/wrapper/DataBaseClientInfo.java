package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class DataBaseClientInfo extends DefaultInfo {

	public DataBaseClientInfo(String[] infos) {
		super(infos);
	}

	public String getClientUUID() {
		return get(0);
	}

	public int getClientDataBaseID() {
		return Integer.parseInt(get(2));
	}

	public String getClientName() {
		return get(1);
	}

	public long getCreatingTime() {
		return Long.parseLong(get(3));
	}

	public long getLastConnection() {
		return Long.parseLong(get(4));
	}

	public int getConnections() {
		return Integer.parseInt(get(5));
	}

	public String getLastIP() {
		return get(13);
	}

	public String getDescription() {
		return get(7);
	}
}
