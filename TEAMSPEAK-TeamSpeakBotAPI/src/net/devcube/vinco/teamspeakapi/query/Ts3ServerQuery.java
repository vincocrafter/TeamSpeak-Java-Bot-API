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
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManager;
import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.keepalive.KeepAliveThread;
import net.devcube.vinco.teamspeakapi.api.api.property.TSPermission;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger.TSLogLevel;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryReader;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

/**
 * @apiNote This class serves as the main entry point for the TeamSpeak Query API. It manages the 
 * connection between the client and the server, as well as the setup of the API components.
 */

public class Ts3ServerQuery {

	private Socket socket;

	private QueryReader reader;
	private QueryWriter writer;
	private QueryConfig config = new QueryConfig(this);
	private CacheManager cache;

	private Ts3SyncAPI syncAPI;
	private Ts3AsyncAPI asyncAPI;
	private Ts3BasicAPI basicAPI;

	private EventManager eventManager = new EventManager(this);
	private Logger logger = new Logger(this);
	private KeepAliveThread keepAliveThread = new KeepAliveThread(this);

	/**
	 * Establishes a connection to the TeamSpeak server, logs in with the provided credentials,
	 * and connects the TeamSpeak query client to the specified virtual server.
	 * 
	 * @param hostname         The hostname or IP address of the TeamSpeak server.
	 * @param port             The port number of the TeamSpeak server.
	 * @param username         The username for authentication.
	 * @param password         The password for authentication.
	 * @param virtualServerID  The ID of the virtual server to connect to.
	 * @param queryNickName    The nickname for the query client.
	 * @param defaultchannelID The ID of the default channel to join (-1 if none).
	 * @throws IOException         If an I/O error occurs while connecting to the server.
	 * @throws QueryLoginException If the login process fails.
	 */


	public void connect(String hostname, int port, String username, String password, int virtualServerID, String queryNickName, int defaultchannelID) throws IOException, QueryLoginException {
		connect(hostname, port);
		syncAPI.login(username, password);
		syncAPI.connectTeamSpeakQuery(virtualServerID, queryNickName);
		syncAPI.registerAllEvents();
		if (defaultchannelID != -1)
			syncAPI.goToChannel(defaultchannelID);
	}
	
	/**
	 * Establishes a connection to the TeamSpeak server using the specified hostname and port.
	 * Initializes the query reader, writer, cache manager, and APIs.
	 * Starts the reader thread and the keep-alive thread.
	 * 
	 * @param hostname The hostname or IP address of the TeamSpeak server.
	 * @param port     The port number of the TeamSpeak server.
	 * @throws IOException If an I/O error occurs while connecting to the server.
	 */

	
	public void connect(String hostname, int port) throws IOException {
		this.socket = new Socket(hostname, port);
		this.reader = new QueryReader(this, socket);
		this.writer = new QueryWriter(this);
		this.asyncAPI = new Ts3AsyncAPI(this);
		this.cache = new CacheManager(this);
		this.basicAPI = new Ts3BasicAPI(this);
		this.syncAPI = new Ts3SyncAPI(this);
		reader.start(); // starts the reader Thread
		socket.setKeepAlive(true);
		keepAliveThread.start(); // starts KeepAlivThread
	}

	/**
	 * Stops the Query, Socket and all Threads
	 * 
	 */

	public void stopQuery() {
		debug(DebugOutputType.QUERY, "Stopping Query");
		keepAliveThread.interrupt();
		getSyncAPI().unRegisterAllEvents();
		getSyncAPI().quit();
		reader.stopThreads();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void checkQueryPermissions() {
		Set<Integer> queryPermissions = syncAPI.getQueryPermissions();
		TSPermission.checkQueryConnectPermissions(queryPermissions);
		TSPermission.checkQueryActionPermissions(queryPermissions);
		TSPermission.checkQueryInformationPermissions(queryPermissions);
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
	public Ts3AsyncAPI getAsyncAPI() {
		return asyncAPI;
	}

	/**
	 * @return the basicAPI
	 */
	public Ts3BasicAPI getBasicAPI() {
		return basicAPI;
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

	/**
	 * @return the cache
	 */
	public CacheManager getCache() {
		return cache;
	}

	public String getTime() {
		String format = "";
		if (config.isShowTimeMilliseconds()) {
			format = "HH:mm:ss.SSS";
		} else {
			format = "HH:mm:ss";
		}
		SimpleDateFormat simpledateformat = new SimpleDateFormat(format);
		Date date = new Date();

		return simpledateformat.format(date);
	}

	public String getDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/YYYY");
		Date date = new Date();
		return simpledateformat.format(date);
	}

	public String getLogDate() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("YYYY/MM/dd");
		Date date = new Date();
		return simpledateformat.format(date);
	}

	/**
	 * New debug Method for more specified debugging and logging. Uses QueryConfig
	 * for console and/or file debugging.
	 * 
	 * @param type
	 *                  Type of the debugmessage
	 * @param debug
	 *                  message
	 * @see DebugOutputType
	 * @see DebugType
	 * @see QueryConfig
	 * @see Logger
	 */

	public synchronized void debug(DebugOutputType type, String debug) {
		if (config.getDebuglist().isEmpty() || (!config.isEverything() && !config.isInDebug(type)))
			return;

		TSLogLevel logLevel = type.getLogLevel();
		if (config.isDebugType(DebugType.CONSOLE) || config.isDebugType(DebugType.BOTH))
			logger.log(logLevel, debug);
		if (config.isDebugType(DebugType.FILE) || config.isDebugType(DebugType.BOTH))
			logger.logFile(logLevel, debug);
	}
}
