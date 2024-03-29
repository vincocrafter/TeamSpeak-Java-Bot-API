package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnknownClientInfoException extends Exception {

	private static final long serialVersionUID = 1798698654654684576L;

	String message;

	public UnknownClientInfoException() {
	}

	public UnknownClientInfoException(String msg) {
		this.message = msg;

	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("Die ClientInfo ist ungültig!");
		}

		super.printStackTrace();
	}
}
