package net.devcube.vinco.teamspeakapi.api.api.util;

public final class Formatter {

	private Formatter() {
		
	}
	
	public static String toTsFormat(String str) {
		String result = new String(str);
		//result = result.replace("\\", "\\\\");
		result = result.replace(" ", "\\s");
		result = result.replace("/", "\\/");
		result = result.replace("|", "\\p");
		result = result.replace("\b", "\\b");
		result = result.replace("\f", "\\f");
		result = result.replace("\n", "\\n");
		result = result.replace("\r", "\\r");
		result = result.replace("\t", "\\t");
		result = result.replace(String.valueOf('\u0007'), "\\a");
		result = result.replace(String.valueOf('\u000b'), "\\v");
		return result;
	}

	public static String toNormalFormat(String str) {
		String result = new String(str);
		result = result.replace("\\s", " ");
		result = result.replace("\\\\", "\\");
		result = result.replace("\\/", "/");
		result = result.replace("\\p", "|");
		result = result.replace("\\b", "\\b");
		result = result.replace("\\f", "\\f");
		result = result.replace("\\n", "\n");
		result = result.replace("\\r", "\r");
		result = result.replace("\\t", "\t");
		return result;
	}
	
	public static String get(String stringFrom, String splitter) {
		return stringFrom.split(splitter)[1].split(" ")[0].replace(System.lineSeparator(), "");
	}

	
	
	public static String connectString(String[] args) {
		return connectString(args, " ");
	}
	
	public static String connectString(String[] args, String splitter) {
		String result = "";
		for (String part : args) {
			result = result + splitter + part;
		}

		return result.trim();
	}
	
	public static int toInt(boolean bool) {
		return bool ? 1 : 0;
	}
	
	public static String toRemeaningTime(long mills) {
		StringBuilder result = new StringBuilder();
		long counter = mills;
		long seconds = 0L;
		int minutes = 0;
		int hours = 0;
		int days = 0;
		int weeks = 0;
		int months = 0;
		int years = 0;

		while (counter >= 1000L) {
			counter -= 1000;
			++seconds;
		}

		while (seconds >= 60L) {
			seconds -= 60L;
			++minutes;
		}

		while (minutes >= 60) {
			minutes -= 60;
			++hours;
		}

		while (hours >= 24) {
			hours -= 24;
			++days;
		}

		while (days >= 7) {
			days -= 7;
			++weeks;
		}

		while (weeks >= 4) {
			weeks -= 4;
			++months;
		}

		while (months >= 12) {
			++years;
			months -= 12;
		}
		result.append(years).append(" Year(s), ");
		result.append(months).append(" Month(s), ");
		result.append(weeks).append(" Week(s), ");
		result.append(days).append(" Day(s), ");
		result.append(hours).append(" Hour(s), ");
		result.append(minutes).append(" Minute(s), ");
		result.append(seconds).append(" Second(s)");
		return result.toString();
	}
}
