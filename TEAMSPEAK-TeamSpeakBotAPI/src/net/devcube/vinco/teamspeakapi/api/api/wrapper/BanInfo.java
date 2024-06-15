package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import net.devcube.vinco.teamspeakapi.api.api.util.DefaultInfo;
import net.devcube.vinco.teamspeakapi.api.api.util.Formatter;

/**
 * This class represents information about a ban in TeamSpeak.
 * It extends the DefaultInfo class, which provides basic functionality for storing and accessing information.
 */

public class BanInfo extends DefaultInfo {
	
	/**
     * Constructs a new BanInfo object with the given array of ban information.
     * 
     * @param infos An array containing the ban information.
     */
	
	public BanInfo(String[] infos) {
		super(infos);
	}
	
	/**
     * Constructs a new BanInfo object with the given ban information string.
     * 
     * @param infos A string containing the ban information.
     */
	
	public BanInfo(String infos) {
		super(infos);
	}
	
	/**
     * Gets the ID of the ban.
     * 
     * @return The ID of the ban.
     */
	
	public int getID() {
		return getBanID();
	}
		
	/**
     * Gets the ID of the ban.
     * 
     * @return The ID of the ban.
     */
	
	public int getBanID() {
		return toIntI("banid");
	}
	
	/**
     * Gets the IP address associated with the ban.
     * 
     * @return The IP address associated with the ban.
     */
	
	public String getIP() {
		return get("ip");
	}
	
	 /**
     * Gets the UUID of the banned client.
     * 
     * @return The UUID of the banned client.
     */
	
	public String getClientUUID() {
		return get("uid");
	}
	
	/**
     * Gets the MyTeamSpeak ID of the banned client.
     * 
     * @return The MyTeamSpeak ID of the banned client.
     */
	
	public String getMyTeamSpeakID() {
		return get("mytsid");
	}
		
	/**
     * Gets the last nickname used by the banned client.
     * 
     * @return The last nickname used by the banned client.
     */
	
	public String getLastNickName() {
		return get("lastnickname");
	}
	
	/**
     * Gets the timestamp when the ban was created.
     * 
     * @return The timestamp when the ban was created.
     */
	
	public long getCreatingTime() {
		return toLongI("created");
	}
	
	/**
     * Gets the duration of the ban in seconds.
     * 
     * @return The duration of the ban in seconds.
     */
	
	public int getBanDuration() {
		return toIntI("duration");
	}
	
	/**
     * Gets the name of the client who invoked the ban.
     * 
     * @return The name of the client who invoked the ban.
     */
	
	public String getInvokerName() {
		return Formatter.toNormalFormat(get("invokername"));
	}
	
	/**
     * Gets the client database ID of the client who invoked the ban.
     * 
     * @return The client database ID of the client who invoked the ban.
     */

	public int getInvokerClientDataBaseID() {
		return toIntI("invokercldbid");
	}
	
	/**
     * Gets the UUID of the client who invoked the ban.
     * 
     * @return The UUID of the client who invoked the ban.
     */
	
	public String getInvokerClientUUID() {
		return get("invokeruid");
	}
	
	/**
     * Gets the reason for the ban.
     * 
     * @return The reason for the ban.
     */
	
	public String getBanReason() {
		return Formatter.toNormalFormat(get("reason"));
	}
	
	/**
     * Gets the number of times the ban has been enforced.
     * 
     * @return The number of times the ban has been enforced.
     */
	
	public int getBanEnforcements() {
		return toInt(get("enforcements").replace("|", ""));
	}
		
	/**
     * Returns a string representation of the BanInfo object.
     * 
     * @return A string representation of the BanInfo object.
     */
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Ban[");
		result.append("UUID=").append(getClientUUID());
		result.append(",ID=").append(getID());
		result.append("]");
		return  result.toString();
	}
}
