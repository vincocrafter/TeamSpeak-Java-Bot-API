package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class Complain extends _DefaultInfo {
	
	public Complain(String[] infos) {
		super(infos);
	}

	public int getTargetClientDataBaseID() {
		return toInt(get(0));
	}

	public String getTargetName() {
		return get(1);
	}

	public int getSenderClientDataBaseID() {
		return toInt(get(2));
	}

	public String getSenderName() {
		return get(1);
	}

	public String getMessage() {
		return get(4);
	}

	public long getTime() {
		return toLong(get(5));
	}
}
