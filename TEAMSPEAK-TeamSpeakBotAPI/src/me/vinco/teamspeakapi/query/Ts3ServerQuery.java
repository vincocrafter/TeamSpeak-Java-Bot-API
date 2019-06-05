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
package me.vinco.teamspeakapi.query;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.vinco.teamspeakapi.apis.api.event.EventManager;
import me.vinco.teamspeakapi.apis.api.exception.query.QueryLoginException;
import me.vinco.teamspeakapi.apis.async.Ts3AnsycAPI;
import me.vinco.teamspeakapi.apis.sync.Ts3SyncAPI;
import me.vinco.teamspeakapi.query.manager.QueryConfig;
import me.vinco.teamspeakapi.query.manager.QueryReader;
import me.vinco.teamspeakapi.query.manager.QueryWriter;

public class Ts3ServerQuery {

	Socket socket;

	QueryReader reader;
	QueryWriter writer;
	QueryConfig config;

	Ts3SyncAPI syncAPI = new Ts3SyncAPI(this);
	Ts3AnsycAPI ansycAPI = new Ts3AnsycAPI(this);
	EventManager eventManager = new EventManager(this);

	/**
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param virtualServerID
	 * @param queryNickName
	 * @param defaultchannelID
	 * @throws IOException
	 * @throws QueryLoginException 
	 */

	public void connect(String host, int port, String username, String password, int virtualServerID, String queryNickName, int defaultchannelID) throws IOException, QueryLoginException {
		socket = new Socket(host, port);
		reader = new QueryReader(this, socket);
		writer = new QueryWriter(this, socket);
		config = new QueryConfig(this);
			
		login(username, password);
	}
	
	/**
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
}
