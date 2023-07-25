package net.devcube.vinco.teamspeakapi.api.api.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Logger {

	public static final int INFO = 1;
	public static final int ERROR = 2;
	public static final int WARING = 3;
	public static final int QUERY = 4;
	public static final int EVENT_MANAGER = 5;
	public static final int QUERY_WRITER = 6;
	public static final int QUERY_READER = 7;
	public static final int QUERY_READER_QUEUE = 8;
	private Ts3ServerQuery serverQuery;

	public Logger(Ts3ServerQuery serverQuery) {
		this.serverQuery = serverQuery;
	}

	/**
	 * Format -> [THREADNAME] [HH:mm:ss/.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */

	public synchronized void log(int logLevel, Object message) {
		String t = "[" + Thread.currentThread().getName() + "] ";
		String time = "[" + serverQuery.getTime() + "] ";
		String date = "";
		if (serverQuery.getConfig().isShowDate()) {
			date = "[" + serverQuery.getDate() + "] ";
		}

		String prefix = t + date + time;
		String type = "";
		
		switch (logLevel) {
		case 1:
			type = "[INFO]";
			break;
		case 2:
			type = "[ERROR]";
			break;
		case 3:
			type = "[WARNING]";
			break;
		case 4:
			type = "[QUERY]";
			break;
		case 5:
			type = "[Event Manager]";
			break;
		case 6:
			type = "[QUERY WRITER]";
			break;
		case 7:
			type = "[QUERY READER]";
			break;
		case 8:
			type = "[QUERY READER QUEUE]";
			break;
		default:
			type = "[Other]";
			break;
		}
		String msg = prefix + type + " : " + message;

		System.out.println(msg);
	}

	/**
	 * Format -> [THREADNAME] [YYYY/MM/dd] [HH:mm:ss.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */
	public synchronized void logFile(int logLevel, String logFilePath, Object message) {
		String t = "[" + Thread.currentThread().getName() + "] ";
		String time = "[" + serverQuery.getTime() + "] ";

		String date = "";
		if (serverQuery.getConfig().isShowDate()) {
			date = "[" + serverQuery.getDate() + "] ";
		}

		String prefix = t + date + time;
		String type = "";
		
		switch (logLevel) {
		case 1:
			type = "[INFO]";
			break;
		case 2:
			type = "[ERROR]";
			break;
		case 3:
			type = "[WARNING]";
			break;
		case 4:
			type = "[QUERY]";
			break;
		case 5:
			type = "[Event Manager]";
			break;
		case 6:
			type = "[QUERY WRITER]";
			break;
		case 7:
			type = "[QUERY READER]";
			break;
		case 8:
			type = "[QUERY READER QUEUE]";
			break;
		default:
			type = "[Other]";
			break;
		}
		String msg = prefix + type + " : " + message;

		writeInLog(logFilePath, msg);
	}

	/**
	 * Format -> [THREADNAME] [YYYY/MM/dd] [HH:mm:ss.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */
	public synchronized void logFile(int logLevel, Object message) {
		String date = serverQuery.getLogDate();
		if (!new File("Logs").exists()) {
			new File("Logs/").mkdir();
		}

		logFile(logLevel, "Logs/Log " + date.replace("/", ".") + ".txt", message);
	}

	private synchronized void writeInLog(String path, String infos) {
		File cfg = new File(path);

		if (!cfg.exists()) {
			try {
				cfg.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (cfg.exists() && cfg.isFile()) {
			try {
				FileWriter fw = new FileWriter(cfg.getPath(), true);
				fw.write(infos + System.lineSeparator());
				fw.flush();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
