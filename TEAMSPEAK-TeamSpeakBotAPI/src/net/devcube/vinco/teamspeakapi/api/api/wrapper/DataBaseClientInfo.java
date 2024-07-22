package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class DataBaseClientInfo extends DefaultInfo {

	public DataBaseClientInfo(String infos) {
		super(infos);
	}
	
	public String getClientUUID() {
		return Formatter.toNormalFormat(get("client_unique_identifier"));
	}
	
	public String getUniqueIdentifier() {
		return getClientUUID();
	}
	
	public String getUUID() {
		return getClientUUID();
	}
	
	public int getClientDataBaseID() {
		return toIntI("client_database_id");
	}

	public int getID() {
		return getClientDataBaseID();
	}
	
	public int getDatabaseId() {
		return getClientDataBaseID();
	}
	
	public String getClientName() {
		return Formatter.toNormalFormat(get("client_nickname"));
	}

	public long getCreatingTime() {
		return toLongI("client_created");
	}

	public long getLastConnection() {
		return toLongI("client_lastconnected");
	}

	public int getTotalConnections() {
		return toIntI("client_totalconnections");
	}

	public String getLastIP() {
		return get("client_lastip");
	}
	
	public String getIP() {
		return getLastIP();
	}

	public String getDescription() {
		return Formatter.toNormalFormat(get("client_description"));
	}
	
	public String getAvatar() {
		return get("client_flag_avatar");
	}

	public int getMonthBytesUploaded() {
		return toIntI("client_month_bytes_uploaded");
	}

	public int getMonthBytesDownloaded() {
		return toIntI("client_month_bytes_downloaded");
	}

	public int getTotalBytesUploaded() {
		return toIntI("client_total_bytes_uploaded");
	}

	public int getTotalBytesDownloaded() {
		return toIntI("client_total_bytes_downloaded");
	}

	public String getBase64HashUID() {
		return get("client_base64HashClientUID");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("DataBaseClient[");
		sb.append("UUID=").append(getClientUUID());
		sb.append(",DBID=").append(getClientDataBaseID());
		sb.append(",Name=").append(getClientName());
		sb.append("]");
		return sb.toString();
	}
}
