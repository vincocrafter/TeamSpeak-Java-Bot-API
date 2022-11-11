package net.devcube.vinco.teamspeakapi.api.api.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

	public void log(int logLevel, Object message) {
		String t = "[" + Thread.currentThread().getName() + "] ";
		String time = "[" + serverQuery.getTime() + "] ";
		String date = "";
		if (serverQuery.getConfig().isShowDate()) {
			date = "[" + serverQuery.getDate() + "] ";
		}

		switch (logLevel) {
		case 1:
			System.out.println(t + date + time + "[INFO] : " + message);
			break;
		case 2:
			System.err.println(t + date + time + "[ERROR] : " + message);
			break;
		case 3:
			System.out.println(t + date + time + "[WARNING] : " + message);
			break;
		case 4:
			System.out.println(t + date + time + "[QUERY] : " + message);
			break;
		case 5:
			System.out.println(t + date + time + "[Event Manager] : " + message);
			break;
		case 6:
			System.out.println(t + date + time + "[QUERY WRITER] : " + message);
			break;
		case 7:
			System.out.println(t + date + time + "[QUERY READER] : " + message);
			break;
		case 8:
			System.out.println(t + date + time + "[QUERY READER QUEUE] : " + message);
			break;
		default:
			System.out.println(t + date + time + "[Other] : " + message);
			break;
		}
	}

	/**
	 * Format -> [THREADNAME] [HH:mm:ss/.SSS] [Type] : message
	 * 
	 * @param logLevel
	 * @param message
	 */
	public void logFile(int logLevel, Object message) {
		String t = "[" + Thread.currentThread().getName() + "] ";
		String time = "[" + serverQuery.getTime() + "] ";

		String date = "";
		if (serverQuery.getConfig().isShowDate()) {
			date = "[" + serverQuery.getDate() + "] ";
		}

		switch (logLevel) {
		case 1:
			writeInLog(t + date + time + "[INFO] : " + message);
			break;
		case 2:
			writeInLog(t + date + time + "[ERROR] : " + message);
			break;
		case 3:
			writeInLog(t + date + time + "[WARNING] : " + message);
			break;
		case 4:
			writeInLog(t + date + time + "[QUERY] : " + message);
			break;
		case 5:
			writeInLog(t + date + time + "[Event Manager] : " + message);
			break;
		case 6:
			writeInLog(t + date + time + "[QUERY WRITER] : " + message);
			break;
		case 7:
			writeInLog(t + date + time + "[QUERY READER] : " + message);
			break;
		case 8:
			writeInLog(t + date + time + "[QUERY READER QUEUE] : " + message);
			break;
		default:
			writeInLog(t + date + time + "[Other] : " + message);
			break;
		}
	}

	private void writeInLog(String infos) {
		String date = getServerQuery().getDate();
		File cfg = new File("Logs/Log " + date.replace("/", ".") + ".txt");
		if (!new File("Logs").exists()) {
			new File("Logs/").mkdir();
		}

		if (!cfg.exists()) {
			try {
				cfg.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if (cfg.exists() && cfg.isFile()) {
			ArrayList<String> oldLines = new ArrayList<>();
			try {
				FileReader fr = new FileReader(cfg);
				try (BufferedReader br = new BufferedReader(fr)) {
					br.lines().forEach(line -> {
						oldLines.add(line);
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				FileWriter fw = new FileWriter(cfg);
				BufferedWriter bw = new BufferedWriter(fw);
				for (String lines : oldLines) {
					if (lines != "") {
						bw.write(lines);
						bw.newLine();
					}
				}
				for (String lines : infos.split("\n")) {
					if (lines != "") {
						bw.write(lines);
						bw.newLine();
					}
				}
				bw.close();
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getLogInfos(String date) {
		ArrayList<String> lines = new ArrayList<>();
		File cfg = new File("Logs/Log " + date.replace("/", ".") + ".txt");

		if (cfg.exists() && cfg.isFile()) {
			try {
				FileReader fr = new FileReader(cfg);
				try (BufferedReader br = new BufferedReader(fr)) {
					br.lines().forEach(line -> {
						lines.add(line);
					});
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return lines;
	}

	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
}
