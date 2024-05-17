package net.devcube.vinco.teamspeakapi.api.api.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class Logger {

	public static enum TSLogLevel {
		
		INFO("INFO"), 
		ERROR("ERROR"), 
		WARNING("WARNING"),
		QUERY("QUERY"),
		EVENT_MANAGER("EVENT MANAGER"),
		QUERY_WRITER("QUERY WRITER"),
		QUERY_READER("QUERY READER"),
		QUERY_READER_QUEUE("QUERY READER QUEUE"),
		CACHE_MANAGER("CACHE MANAGER");

		private String value = "";

		private TSLogLevel(String s) {
			this.value = s;
		}

		public String getValue() {
			return this.value;
		}
		
	}
	
	private Ts3ServerQuery serverQuery;

	public Logger(Ts3ServerQuery serverQuery) {
		this.serverQuery = serverQuery;
	}

	/**
	 * Format -> [THREADNAME] [dd/mm/yyyy] [HH:mm:ss/.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */

	private String buildDebugMessage(TSLogLevel logLevel, Object message) {
		StringBuilder logMessage = new StringBuilder();
		
		StringBuilder prefix = new StringBuilder();
		prefix.append("[").append(Thread.currentThread().getName()).append("]").append(" ");
		if (serverQuery.getConfig().isShowDate()) {
			prefix.append("[").append(serverQuery.getDate()).append("]").append(" ");
		}
		prefix.append("[").append(serverQuery.getTime()).append("]").append(" ");

		logMessage.append(prefix);
		logMessage.append(logLevel.getValue());
		logMessage.append(" : ");
		logMessage.append(message);
		return logMessage.toString();
	}

	public synchronized void log(TSLogLevel logLevel, Object message) {
		System.out.println(buildDebugMessage(logLevel, message));
	}

	/**
	 * Format -> [THREADNAME] [dd/MM/YYYY] [HH:mm:ss.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */
	public synchronized void logFile(TSLogLevel logLevel, String logFilePath, Object message) {
		writeInLog(logFilePath, buildDebugMessage(logLevel, message));
	}

	/**
	 * Format -> [THREADNAME] [YYYY/MM/dd] [HH:mm:ss.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */
	public synchronized void logFile(TSLogLevel logLevel, Object message) {
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
