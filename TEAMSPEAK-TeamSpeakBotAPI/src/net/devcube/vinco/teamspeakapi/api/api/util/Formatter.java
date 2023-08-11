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
}
