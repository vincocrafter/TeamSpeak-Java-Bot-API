package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

public class DefaultInfo {

	String[] infos;

	public DefaultInfo(String[] infos) {
		this.infos = infos;
	}

	public void printInfos() {
		System.out.println(Formatter.connectString(infos));
	}

	protected String get(int i) {
		try {
			String s = this.infos[i];
			return s.substring(s.indexOf("=") + 1, s.length());
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String get(String valueName) {
        for (String s : this.infos) {
            String[] split = s.split("=");
            if(split[0].equals(valueName)){
            	return split.length < 2 ? null : split[1];
            }
        }
        return "";
    }
	
	public void addInfo(String key, String value) {
		String fInfos = Formatter.connectString(this.infos) + " " + key + "=" + value;
		this.infos = fInfos.split(" ");
	}
	
	public String[] getInfos() {
		return this.infos;
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
