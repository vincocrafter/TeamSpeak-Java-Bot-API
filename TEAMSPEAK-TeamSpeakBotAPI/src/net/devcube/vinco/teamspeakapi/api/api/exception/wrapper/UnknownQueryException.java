package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnknownQueryException extends Exception {

	private static final long serialVersionUID = 154541645651656544L;

	String message;

	public UnknownQueryException() {
	}

	public UnknownQueryException(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("Die Query ist ung\u00fcltig!");
		}

		super.printStackTrace();
	}
}
