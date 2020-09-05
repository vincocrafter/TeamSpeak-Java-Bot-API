/**
 * Projekt: TEAMSPEAK - TeamSpeakBotAPI - New
 *
 *Author : vincent
 *
 *Jahr 2019  
 *
 *Datum : 10.02.2019
 * 
 *Uhrzeit : 19:12:48
 */
package net.devcube.vinco.teamspeakapi.query;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AnsycAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryReader;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

public class Ts3ServerQuery {

	private Socket socket;

	private QueryReader reader;
	private QueryWriter writer;
	private QueryConfig config = new QueryConfig(this);

	private Ts3SyncAPI syncAPI = new Ts3SyncAPI(this);
	private Ts3AnsycAPI ansycAPI = new Ts3AnsycAPI(this);
	private EventManager eventManager = new EventManager(this);
	private Logger logger = new Logger(this);
	
	/**
	 * Connect's the Socket to the Server
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param virtualServerID
	 * @param queryNickName
	 * @param defaultchannelID
	 * 
	 * @throws IOException
	 * @throws QueryLoginException 
	 */

	public void connect(String host, int port, String username, String password, int virtualServerID, String queryNickName, int defaultchannelID) throws IOException, QueryLoginException {
		socket = new Socket(host, port);
		reader = new QueryReader(this, socket);
		writer = new QueryWriter(this, socket);
		login(username, password);
		syncAPI.connectTeamSpeakQuery(virtualServerID);
		syncAPI.goToChannel(defaultchannelID);
		socket.setKeepAlive(true);
	}
	
	/**
	 * Log in the Client to the Server using :
	 * 
	 * @param username
	 * @param password
	 */
	private void login(String username, String password) throws QueryLoginException {
		this.writer.executeCommand("login " + username + " " + password);

		String s = "";
		if (s != null && s.equalsIgnoreCase("error id=520 msg=invalid\\sloginname\\sor\\spassword")) {
			throw new QueryLoginException();
		}
	}
	
	public void stopQuery() {
		writer.executeCommand("quit");
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  Read all Incoming start Messages
	 */
	public void readAllMessages() {
		if(!syncAPI.isConnected()) {
			
		}
	}
	
	
	/**
	 * @returns the Usage of the Processor in percent
	 */

	public double getProcessorUsage() {
		OperatingSystemMXBean sys = ManagementFactory.getOperatingSystemMXBean();
		return (sys.getSystemLoadAverage() / sys.getAvailableProcessors()) * 100;
	}

	/**
	 * @return the Queryconfig
	 */
	public QueryConfig getConfig() {
		return config;
	}

	/**
	 * @return the QueryReader
	 */
	public QueryReader getReader() {
		return reader;
	}

	/**
	 * @return the QueryWriter
	 */
	public QueryWriter getWriter() {
		return writer;
	}

	/**
	 * @return the syncAPI
	 */
	public Ts3SyncAPI getSyncAPI() {
		return syncAPI;
	}

	/**
	 * @return the ansycAPI
	 */
	public Ts3AnsycAPI getAnsycAPI() {
		return ansycAPI;
	}
	
	/**
	 * @return the eventManager
	 */
	public EventManager getEventManager() {
		return eventManager;
	}
	
	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	public String getTime() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return simpledateformat.format(date);
	}

	public String getDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/YYYY");
		Date date = new Date();
		return simpledateformat.format(date);
	}

	/**
	 * @param debug
	 */
	public void debug(String debug) {
		if(config.isDebug())
			getLogger().log(Logger.INFO, debug);
	}

	

	
}
