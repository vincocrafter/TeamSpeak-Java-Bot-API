package net.devcube.vinco.teamspeakapi.api.api.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

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
	
	private QueryConfig config;

	public Logger(QueryConfig config) {
		this.config = config;
	}

	/**
	 * New debug Method for more specified debugging and logging. Uses QueryConfig
	 * for console and/or file debugging.
	 *
	 * @param type
	 *                  Type of the debugmessage
	 * @param debug
	 *                  message
	 * @see DebugOutputType
	 * @see DebugType
	 * @see QueryConfig
	 * @see Logger
	 */

	public synchronized void debug(DebugOutputType type, String debug) {
		if (config.getDebugList().isEmpty() || (!config.isEverything() && !config.isInDebug(type)))
			return;

		TSLogLevel logLevel = type.getLogLevel();
		if (config.isDebugType(DebugType.CONSOLE) || config.isDebugType(DebugType.BOTH))
			log(logLevel, debug);
		if (config.isDebugType(DebugType.FILE) || config.isDebugType(DebugType.BOTH))
			logFile(logLevel, debug);
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
		if (config.isShowDate()) {
			prefix.append("[").append(getDate()).append("]").append(" ");
		}
		prefix.append("[").append(getTime()).append("]").append(" ");

		logMessage.append(prefix);
		logMessage.append("[").append(logLevel.getValue()).append("]");
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
		String date = getLogDate();
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
	
	private String getTime() {
		String format = "";
		if (config.isShowTimeMilliseconds()) {
			format = "HH:mm:ss.SSS";
		} else {
			format = "HH:mm:ss";
		}
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		Date date = new Date();

		return simpledateformat.format(date);
	}

	private String getDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/YYYY");
		Date date = new Date();
		return simpledateformat.format(date);
	}

	private String getLogDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("YYYY/MM/dd");
		Date date = new Date();
		return simpledateformat.format(date);
	}
	
}
