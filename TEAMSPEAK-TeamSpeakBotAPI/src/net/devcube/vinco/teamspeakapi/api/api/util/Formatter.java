package net.devcube.vinco.teamspeakapi.api.api.util;

public final class Formatter {

	private Formatter() {
		
	}
	
	public static String toTsFormat(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace(" ", "\\s");
		str = str.replace("/", "\\/");
		str = str.replace("|", "\\p");
		str = str.replace("\b", "\\b");
		str = str.replace("\f", "\\f");
		str = str.replace("\n", "\\n");
		str = str.replace("\r", "\\r");
		str = str.replace("\t", "\\t");
		str = str.replace(String.valueOf('\u0007'), "\\a");
		str = str.replace(String.valueOf('\u000b'), "\\v");
		return str;
	}

	public static String toNormalFormat(String s) {
		String str = s;
		str = str.replace("\\s", " ");
		str = str.replace("\\\\", "\\");
		str = str.replace("\\/", "/");
		str = str.replace("\\p", "|");
		str = str.replace("\\b", "\\b");
		str = str.replace("\\f", "\\f");
		str = str.replace("\\n", "\n");
		str = str.replace("\\r", "\r");
		str = str.replace("\\t", "\t");
		return str;
	}
	
	public static String connectString(String[] args) {
		return connectString(args, " ");
	}
	
	public static String connectString(String[] args, String splitter) {
		String s = "";
		for (String s1 : args) {
			s = s + splitter + s1;
		}

		return s.trim();
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
		result.append(years).append(" Jahr(e), ");
		result.append(months).append(" Monat(e), ");
		result.append(weeks).append(" Woche(n), ");
		result.append(days).append(" Tag(e), ");
		result.append(hours).append(" Stunde(n), ");
		result.append(minutes).append(" Minute(n), ");
		result.append(seconds).append(" Sekunden");
		return result.toString();
	}
}
