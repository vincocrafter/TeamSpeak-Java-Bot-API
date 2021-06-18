package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnkownVirtualServerInfo extends Throwable {
	
	private static final long serialVersionUID = 1L;
	
	String message;

	public UnkownVirtualServerInfo() {
	}

	public UnkownVirtualServerInfo(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("Der Virtuelle Server ist ungültig");
		}

		super.printStackTrace();
	}
}
