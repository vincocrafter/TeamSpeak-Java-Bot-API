package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

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
	
	public int getID() {
		return getClientDataBaseID();
	}
	
	public String getClientName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}

	public long getCreatingTime() {
		return toLong(get("client_created"));
	}

	public long getLastConnection() {
		return toLong(get("client_lastconnected"));
	}

	public int getTotalConnections() {
		return toInt(get("client_totalconnections"));
	}

	public String getLastIP() {
		return get("client_lastip");
	}

	public String getDescription() {
		return Formatter.toNormalFormat(get("client_description"));
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("DataBaseClient[");
		sb.append("UUID=" + getClientUUID() + ", ");
		sb.append("DBID=" + getClientDataBaseID() + ", ");
		sb.append("Name=" + getClientName());
		sb.append("]");
		return sb.toString();
	}
}
