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
import net.devcube.vinco.teamspeakapi.api.api.keepalive.KeepAliveThread;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AnsycAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryReader;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

/**
 * @apiNote This is the mainclass of the TeamSpeak Query API, it handles the connection between the client and the server and set the API up
 */

public class Ts3ServerQuery {

	private Socket socket;

	private QueryReader reader;
	private QueryWriter writer;
	private QueryConfig config = new QueryConfig(this);

	private Ts3SyncAPI syncAPI = new Ts3SyncAPI(this);
	private Ts3AnsycAPI ansycAPI = new Ts3AnsycAPI(this);
	private EventManager eventManager = new EventManager(this);
	private Logger logger = new Logger(this);
	private KeepAliveThread keepAliveThread = new KeepAliveThread(this);
		
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

	public void connect(String host, int port, String username, String password, int virtualServerID,
			String queryNickName, int defaultchannelID) throws IOException, QueryLoginException {
		socket = new Socket(host, port);
		reader = new QueryReader(this, socket);
		writer = new QueryWriter(this, socket);
		reader.start(); //starts the readerThread
		login(username, password);
		syncAPI.connectTeamSpeakQuery(virtualServerID, queryNickName);
		registerAllEvents();
		syncAPI.goToChannel(defaultchannelID);
		socket.setKeepAlive(true);
		keepAliveThread.start(); //starts KeepAlivThread
		
	}

	/**
	 * Log in the Client to the Server using the login information
	 * 
	 * @param username
	 * @param password
	 */
	private void login(String username, String password) throws QueryLoginException {
		writer.executeCommand("login " + username + " " + password);
		
		String s = reader.nextError();
		if (s != null && s.equalsIgnoreCase("error id=520 msg=invalid\\sloginname\\sor\\spassword")) {
			debug(DebugOutputType.QUERY, "Login failed");
			throw new QueryLoginException();
		} else {
			debug(DebugOutputType.QUERY, "Logged in sucessfully");
		}
	}

	public void stopQuery() {
		writer.executeCommand("quit");
		keepAliveThread.interrupt();
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Read all Incoming start Messages
	 */
	public void readAllMessages() {
		if (!syncAPI.isConnected()) {

		}
	}
	
	// register all Events
	
	public void registerAllEvents() {
		debug(DebugOutputType.QUERY, "Registering all Events");

	      writer.executeCommand("servernotifyregister event=server");
	      writer.executeCommand("servernotifyregister event=channel id=0");
	      writer.executeCommand("servernotifyregister event=textserver");
	      writer.executeCommand("servernotifyregister event=textchannel");
	      writer.executeCommand("servernotifyregister event=textprivate");
	      writer.executeCommand("servernotifyregister event=tokenused");
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
	 * The Debugging Method Checks if the debug is enabled
	 * 
	 * @param debug
	 * 
	 * @deprecated
	 */
	public void debug(String debug) {
		if (config.isDebug())
			getLogger().log(Logger.INFO, debug);
	}

	/**
	 * The Debugging Method Checks if the debug is enabled
	 * 
	 * @param debug
	 * 
	 * @deprecated
	 */
	public void debug(int level, String debug) {
		if (config.isDebug())
			getLogger().log(level, debug);
	}
	
	
	/** 
	 * new debug Method for more specified debugging and outputting
	 * 
	 * @param type
	 * @param debug message
	 * @see DebugOutputType
	 * @see QueryConfig
	 */
	
	public void debug(DebugOutputType type, String debug) {
		switch (type) {
		case GENERAL:
			if(config.isGeneralDebug() || config.isEverything())
				logger.log(1, debug);
			break;
		case EVENTMANAGER:
			if(config.isEventManagerDebug() || config.isEverything())
				logger.log(5, debug);
			break;
		case KEEPALIVETHREAD:
			if(config.isKeepAliveThreadDebug() || config.isEverything())
				logger.log(1, debug);
			break;
		case QUERY:
			if(config.isQueryDebug() || config.isEverything())
				logger.log(4, debug);
			break;
		case QUERYREADER:
			if(config.isQueryReaderDebug() || config.isEverything())
				logger.log(4, debug);
			break;
		case QUERYWRITER:
			if(config.isQueryWriterDebug() || config.isEverything())
				logger.log(4, debug);
			break;
		default:
			if(config.isInDebug(type) || config.isEverything()) {
				logger.log(5, debug);
			}
			break;
		}
	}
}
