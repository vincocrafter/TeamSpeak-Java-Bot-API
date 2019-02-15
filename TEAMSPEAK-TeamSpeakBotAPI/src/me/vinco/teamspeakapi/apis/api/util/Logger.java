package me.vinco.teamspeakapi.apis.api.util;

import me.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Logger {
	public static final int INFO = 1;
	public static final int ERROR = 2;
	public static final int WARING = 3;
	public static final int QUERY = 4;
	public static final int EVENT_MANAGER = 5;
	public static final int KEEP_ALIVE_THREAD = 6;
	private static Ts3ServerQuery serverQuery;

	public Logger(Ts3ServerQuery serverQuery) {
		Logger.serverQuery = serverQuery;
	}

	public static void log(int logLevel, Object message) {
		if (serverQuery.getConfig().isDebug()) {

			switch (logLevel) {
			case 1:
				System.out.println("[INFO] : (" + serverQuery.getTime() + ") > " + message);
				break;
			case 2:
				System.out.println("[ERROR] : (" + serverQuery.getTime() + ") > " + message);
				break;
			case 3:
				System.out.println("[WARNING] : (" + serverQuery.getTime() + ") > " + message);
				break;
			case 4:
				System.out.println("[QUERY] : (" + serverQuery.getTime() + ") > " + message);
				break;
			case 5:
				System.out.println("[Event Manager] : (" + serverQuery.getTime() + ") > " + message);
				break;
			case 6:
				System.out.println("[KeepAliveThread] : (" + serverQuery.getTime() + ") > " + message);
				break;
			default:
				System.out.println("[Miscellaneous] : (" + serverQuery.getTime() + ") > " + message);
				break;
			}
		}
	}

	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
}
