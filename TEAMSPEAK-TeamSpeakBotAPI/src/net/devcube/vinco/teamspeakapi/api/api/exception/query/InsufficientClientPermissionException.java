/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 11.02.2019
 * 
 *Uhrzeit : 18:23:31
 */
package net.devcube.vinco.teamspeakapi.api.api.exception.query;

public class InsufficientClientPermissionException extends Exception {

	private static final long serialVersionUID = 1222321221202133321L;

	String message;

	public InsufficientClientPermissionException() {
	}

	public InsufficientClientPermissionException(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("You do not have the needed permissions to perform the use command!");
		}
		super.printStackTrace();
	}
}
