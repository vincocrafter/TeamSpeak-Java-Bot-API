package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnkownVirtualServerInfoException extends Exception {
	
	private static final long serialVersionUID = 1656152312213321L;
	
	String message;

	public UnkownVirtualServerInfoException() {
	}

	public UnkownVirtualServerInfoException(String msg) {
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
