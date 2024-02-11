package net.devcube.vinco.teamspeakapi.api.api.util;

import java.util.HashMap;

public class DefaultInfo {

	private String[] infos;
	private HashMap<String, String> splitInfos = new HashMap<>();
	
	public DefaultInfo(String[] infos) {
		this.infos = infos;
		for (String info : infos) {
			String key = info.split("=")[0];
			String value = "";
				
			if (info.split("=").length > 1) {
				value = info.replace(key.concat("="), "").replace(System.lineSeparator(), "");
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
	
	/*
	public String get(String valueName) {
        for (String s : this.infos) {
            String[] split = s.split("=");
            if(split[0].equals(valueName)) {
            	return split.length < 2 ? null : split[1];
            }
        }
        return "";
    }
    */
    
	public String get(String valueName) {
		return splitInfos.get(valueName);
	}
	
	public void addInfo(String key, String value) {
		splitInfos.put(key, value);
	}
	
	public HashMap<String, String> getSplitMap() {
		return new HashMap<>(splitInfos);
	}
	
	public String[] getInfos() {
		return this.infos;
	}
	
	public String getRawInfos() {
		return Formatter.connectString(infos);
	}
	
	public void printRawInfos() {
		System.out.println(getRawInfos());
	}

	protected int toInt(String s) {
		return Integer.parseInt(s);
	}

	protected double toDouble(String s) {
		return Double.parseDouble(s);
	}

	protected float toFloat(String s) {
		return Float.parseFloat(s);
	}

	protected long toLong(String s) {
		return Long.parseLong(s);
	}

	protected boolean toBol(int i) {
		return i == 1;
	}

	protected boolean toBol(String s) {
		return Boolean.parseBoolean(s);
	}
}
