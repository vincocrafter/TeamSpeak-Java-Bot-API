package net.devcube.vinco.teamspeakapi.api.api.event;

import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsycAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class BaseEvent {

	private String[] infos;
	private Ts3ServerQuery serverQuery;
	
	public BaseEvent(String[] infos, Ts3ServerQuery serverQuery) {
		this.infos = infos;
		this.serverQuery = serverQuery;
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
        for (String s : infos) {
            String[] split = s.split("=");
            if(split[0].equals(valueName)){
            	return split.length < 2 ? null : split[1];
            }
        }
        return "";
    }
	
	
	public String[] getInfos() {
		return this.infos;
	}
	
	/**
	 * TODO eventuell wieder raus
	 * (Warum sollte jemand in einem Event direkt auf die Query zugreifen wollen, kann ja selbst über eigene Instanz machen?)
	 * @return the serverQuery
	 */
	public Ts3ServerQuery getServerQuery() {
		return serverQuery;
	}
	
	public Ts3SyncAPI getSyncAPI() {
		return serverQuery.getSyncAPI();
	}
	
	public Ts3BasicAPI getBasicAPI() {
		return serverQuery.getBasicAPI();
	}
 	
	public Ts3AsycAPI getAsyncAPI() {
		return serverQuery.getAsycAPI();
	}
	
	public void printInfos() {
		System.out.println(Formatter.connectString(infos));
	}

	public int getClientID() {
		return -1;
	}

	public String getClientName() {
		return "";
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
