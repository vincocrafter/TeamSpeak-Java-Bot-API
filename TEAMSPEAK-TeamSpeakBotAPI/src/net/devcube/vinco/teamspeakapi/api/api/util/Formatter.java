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
}
