/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincocrafter
 *
 *Jahr 2019  
 *
 *Datum : 12.02.2019
 * 
 *Uhrzeit : 16:35:45
 */
package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnknownEventException extends Throwable {

	private static final long serialVersionUID = 1L;

	String message;

	public UnknownEventException() {
	}

	public UnknownEventException(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(message);
		} else {
			System.err.println("The Event is null or unkown");
		}

		super.printStackTrace();
	}
}
