package net.devcube.vinco.teamspeakapi.apis.api.wrapper;

public class Complain extends _DefaultInfo {
	
	public Complain(String[] infos) {
		super(infos);
	}

	public int getTargetClientDataBaseID() {
		return this.toInt(this.get(0));
	}

	public String getTargetName() {
		return this.get(1);
	}

	public int getSenderClientDataBaseID() {
		return this.toInt(this.get(2));
	}

	public String getSenderName() {
		return this.get(1);
	}

	public String getMessage() {
		return this.get(4);
	}

	public long getTime() {
		return this.toLong(this.get(5));
	}
}
