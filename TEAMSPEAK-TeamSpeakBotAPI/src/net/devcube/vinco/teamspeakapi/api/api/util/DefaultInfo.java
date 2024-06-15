package net.devcube.vinco.teamspeakapi.api.api.util;

import java.util.Map;
import java.util.TreeMap;

public class DefaultInfo {

	private String[] infos;
	private Map<String, String> splitInfos = new TreeMap<>();
	
	public DefaultInfo(String[] infos) {
		this.infos = infos;
		for (String info : infos) {
			if (info.isEmpty())
				continue;
			int splitIndex = info.indexOf("=");
			String key = info;
			String value = "";
			if (splitIndex != -1) {
				key = info.substring(0, splitIndex);
				value = info.substring(splitIndex + 1);
			}
			
			splitInfos.put(key, value);
		}
	}
	
	public DefaultInfo(String infos, String splitter) {
		this(infos.split(splitter));
	}
	
	public DefaultInfo(String infos) {
		this(infos, " ");
	}
	
	protected String get(String valueName) {
		return splitInfos.get(valueName);
	}
	
	protected void addInfo(String key, String value) {
		splitInfos.put(key, value);
	}
	
	protected void removeInfo(String key) {
		splitInfos.remove(key);
	}
	
	protected void copyInfoTo(String key, DefaultInfo copyTo, String otherKey) {
		copyTo.addInfo(otherKey, get(key));
		removeInfo(key);
	}
	
	public Map<String, String> getSplitMap() {
		return new TreeMap<>(splitInfos);
	}
	
	protected String[] getInfos() {
		return this.infos;
	}
	
	public String getRawInfos() {
		return Formatter.connectString(infos);
	}

	protected int toInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -2;
	}

	protected int toIntI(String s) {
		return toInt(get(s));
	}
	
	protected double toDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	protected double toDoubleI(String s) {
		return toDouble(get(s));
	}
	
	protected float toFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	protected double toFloatI(String s) {
		return toFloat(get(s));
	}

	protected long toLong(String s) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return -2;
	}
	
	protected long toLongI(String s) {
		return toLong(get(s));
	}
	
	protected boolean toBol(int i) {
		return i == 1;
	}
	
	protected boolean toBolI(String s) {
		return toIntI(s) == 1;
	}
	
	protected boolean toBol(String s) {
		return Boolean.parseBoolean(s);
	}
}
