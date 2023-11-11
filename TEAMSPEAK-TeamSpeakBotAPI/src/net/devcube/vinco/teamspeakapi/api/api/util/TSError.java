/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 15.04.2023
 * 
 * Uhrzeit : 15:11:09
 */
package net.devcube.vinco.teamspeakapi.api.api.util;

public enum TSError {

	QUERY_INVALID_LOGIN(520), // error id=520 msg=invalid\\sloginname\\sor\\spassword
	CHANNEL_NAME_IS_ALEARY_IN_USE(771), // error id=771 msg=channel\sname\sis\salready\sin\suse
	DATABASE_EMPTY_RESULT(1281), // error id=1281 msg=database\\sempty\\sresult\\sset
	INSUFFICIENT_CLIENT_PERMISSIONS(2568), // error id=2568 msg=insufficient\\sclient\\spermissions failed_permid=156
	VIRTUALSERVER_LIMIT_REACHED(2816);//error id=2816 msg=virtualserver\slimit\sreached
	private int i = 0;

	private TSError(int i) {
		this.i = i;
	}

	public int getValue() {
		return this.i;
	}

	public static boolean isError(String unformattedError, int errorID) {
		return Integer.parseInt(unformattedError.split("error id=")[1].split(" ")[0]) == errorID;
	}
	
	public static boolean isError(String unformattedError, TSError error) {
		return isError(unformattedError, error.getValue());
	}
}
