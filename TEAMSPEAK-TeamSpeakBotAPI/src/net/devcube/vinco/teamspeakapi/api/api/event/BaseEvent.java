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
		return toInt(this.get(4));
	}

	public String getClientName() {
		return get(6);
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
