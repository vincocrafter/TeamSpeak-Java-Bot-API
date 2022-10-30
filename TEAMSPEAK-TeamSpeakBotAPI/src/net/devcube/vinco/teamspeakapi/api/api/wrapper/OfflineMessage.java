package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class OfflineMessage extends DefaultInfo {

	public OfflineMessage(String[] infos) {
		super(infos);
	}

	public String[] getInfos() {
		return this.infos;
	}

	public int getMessageID() {
		return Integer.parseInt(get(0));
	}

	public String getClientUUID() {
		return get(1);
	}

	public String getSubject() {
		return get(2);
	}

	public String getMessage() {
		return get(3);
	}

	public long getTime() {
		return Long.parseLong(this.get(4));
	}
}
