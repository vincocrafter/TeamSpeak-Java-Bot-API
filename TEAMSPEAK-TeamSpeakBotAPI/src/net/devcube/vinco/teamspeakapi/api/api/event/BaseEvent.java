package net.devcube.vinco.teamspeakapi.api.api.event;

public class BaseEvent {

	private String[] infos;

	public BaseEvent(String[] infos) {
		this.infos = infos;
	}

	public String get(int i) {
		try {
			String s = this.infos[i];
			return s.substring(s.indexOf("=") + 1, s.length());
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String get(String value_name) {
        for (String s : infos) {
            String[] split = s.split("=");
            if(split[0].equals(value_name)){
            	return split.length < 2 ? null : split[1];
            }
        }
        return "";
    }
	
	public String[] getInfos() {
		return this.infos;
	}

	public void printInfos() {
		int i = 0;
		for (String s : this.infos) {
			System.out.println(i + " : > " + s);
			++i;
		}
	}

	public int getClientID() {
		return -1;
	}

	public String getClientName() {
		return "";
	}

	public int toInt(String s) {
		return Integer.parseInt(s);
	}

	public double toDouble(String s) {
		return Double.parseDouble(s);
	}

	public float toFloat(String s) {
		return Float.parseFloat(s);
	}

	public long toLong(String s) {
		return Long.parseLong(s);
	}
}
