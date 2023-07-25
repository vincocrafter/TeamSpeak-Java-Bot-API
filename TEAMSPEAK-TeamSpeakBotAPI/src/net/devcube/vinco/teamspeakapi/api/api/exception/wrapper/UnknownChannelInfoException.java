package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnknownChannelInfoException extends Exception {

	private static final long serialVersionUID = 111257498768565464L;

	String message;

	public UnknownChannelInfoException() {
	}

	public UnknownChannelInfoException(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("Der Channel ist ungültig");
		}

		super.printStackTrace();
	}
}
