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

public class QueryLoginException extends Exception {

	private static final long serialVersionUID = 1685446564654566564L;

	String message;

	public QueryLoginException() {
	}

	public QueryLoginException(String msg) {
		this.message = msg;
	}

	public void printStackTrace() {
		if (this.message != null) {
			System.err.println(this.message);
		} else {
			System.err.println("The Username or Password is wrong");
		}
		super.printStackTrace();
	}
}
