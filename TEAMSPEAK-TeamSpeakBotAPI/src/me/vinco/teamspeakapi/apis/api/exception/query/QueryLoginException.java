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
package me.vinco.teamspeakapi.apis.api.exception.query;

public class QueryLoginException extends Throwable {
	   private static final long serialVersionUID = 1L;
	   String message;

	   public QueryLoginException() {
	   }

	   public QueryLoginException(String msg) {
	      this.message = null;
	   }

	   public void printStackTrace() {
	      if(this.message != null) {
	         System.err.println(this.message);
	      } else {
	         System.err.println("The Username or Password is wrong");
	      }
	      super.printStackTrace();
	   }
}
