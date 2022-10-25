package net.devcube.vinco.teamspeakapi.api.api.util;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Logger {
	
	public static final int INFO = 1;
	public static final int ERROR = 2;
	public static final int WARING = 3;
	public static final int QUERY = 4;
	public static final int EVENT_MANAGER = 5;
	public static final int KEEP_ALIVE_THREAD = 6;
	private Ts3ServerQuery serverQuery;

	public Logger(Ts3ServerQuery serverQuery) {
		this.serverQuery = serverQuery;
	}
	
	/**
	 * Format -> [THREADNAME] [HH:mm:ss] [Type] : message 
	 * 
	 * @param logLevel
	 * @param message
	 */
	
	public void log(int logLevel, Object message) {
		String t = "[" + Thread.currentThread().getName() + "] ";
		String time = "[" + serverQuery.getTime() + "] ";

		switch (logLevel) {
		case 1:
			System.out.println(t + time +  "[INFO] :  " + message);
			break;
		case 2:
			System.err.println(t + time +  "[ERROR] : " + message);
			break;
		case 3:
			System.out.println(t + time + "[WARNING] : " + message);
			break;
		case 4:
			System.out.println(t + time + "[QUERY] : " + message);
			break;
		case 5:
			System.out.println(t + time + "[Event Manager] : " + message);
			break;
		case 6:
			System.out.println(t + time + "[QUERY WRITER] : " + message);
			break;
		case 7:
			System.out.println(t + time + "[QUERY READER] : " + message);
			break;
		default:
			System.out.println(t + time + "[Other] : " + message);
			break;
		}
	}

	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
}
