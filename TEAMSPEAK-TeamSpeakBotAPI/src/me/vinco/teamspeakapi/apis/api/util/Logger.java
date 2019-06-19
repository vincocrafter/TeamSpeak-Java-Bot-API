package me.vinco.teamspeakapi.apis.api.util;

import me.vinco.teamspeakapi.query.Ts3ServerQuery;

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

	public void log(int logLevel, Object message) {
		if (serverQuery.getConfig().isDebug()) {
			String t = "[" + Thread.currentThread().getName() + "] ";
			String time = "[" + serverQuery.getTime() + "] ";

			switch (logLevel) {
			case 1:
				System.out.println(time + t + "[INFO] :  " + message);
				break;
			case 2:
				System.out.println(time + t + "[ERROR] : " + message);
				break;
			case 3:
				System.out.println(time + t + "[WARNING] : " + message);
				break;
			case 4:
				System.out.println(time + t + "[QUERY] : " + message);
				break;
			case 5:
				System.out.println(time + t + "[Event Manager] : " + message);
				break;
			default:
				System.out.println(time + t + "[Other] : " + message);
				break;
			}
		}
	}

	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
}
