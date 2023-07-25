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
import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
import net.devcube.vinco.teamspeakapi.api.api.util.TSError;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsycAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;
import net.devcube.vinco.teamspeakapi.query.manager.QueryReader;
import net.devcube.vinco.teamspeakapi.query.manager.QueryWriter;

/**
 * @apiNote This is the mainclass of the TeamSpeak Query API, it handles the
 *          connection between the client and the server and set the API up
 */

public class Ts3ServerQuery {

	private Socket socket;

	private QueryReader reader;
	private QueryWriter writer;
	private QueryConfig config = new QueryConfig(this);

	private Ts3SyncAPI syncAPI = new Ts3SyncAPI(this);
	private Ts3AsycAPI asycAPI = new Ts3AsycAPI(this);
	private Ts3BasicAPI basicAPI = new Ts3BasicAPI(this);
	
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

	public void connect(String hostname, int port, String username, String password, int virtualServerID, String queryNickName, int defaultchannelID) throws IOException, QueryLoginException {
		socket = new Socket(hostname, port);
		reader = new QueryReader(this, socket);
		writer = new QueryWriter(this, socket);
		reader.start(); // starts the reader Thread

		// retrive the first meesages from the server
		while (reader.getResultPackets().peek().size() < 2)
			;
		reader.nextPacket();

		login(username, password);
		syncAPI.connectTeamSpeakQuery(virtualServerID, queryNickName);
		if (defaultchannelID != -1)
			syncAPI.goToChannel(defaultchannelID);
		socket.setKeepAlive(true);
		keepAliveThread.start(); // starts KeepAlivThread
		registerAllEvents();
	}

	public void connect(String hostname, int port) throws IOException {
		socket = new Socket(hostname, port);
		reader = new QueryReader(this, socket);
		writer = new QueryWriter(this, socket);
		reader.start(); // starts the reader Thread

		// retrive the first meesages from the server
		while (reader.getResultPackets().peek().size() < 2)
			;
		reader.nextPacket();

		socket.setKeepAlive(true);
		keepAliveThread.start(); // starts KeepAlivThread
	}

	/**
	 * Log in the Client to the Server using the login information
	 * 
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) throws QueryLoginException {
		String res = writer.executeReadErrorCommand("login " + username + " " + password);

		if (TSError.isError(res, TSError.QUERY_INVALID_LOGIN)) {
			debug(DebugOutputType.ERROR, "Login failed! Invalid loginname or password!");
			throw new QueryLoginException("Invalid loginname or password!");
		} else {
			debug(DebugOutputType.QUERY, "Logged in sucessfully");
		}
	}

	/**
	 * Stops the Query, Socket and all Threads
	 * 
	 */

	public void stopQuery() {
		debug(DebugOutputType.QUERY, "Stopping Query");
		keepAliveThread.interrupt();
		writer.executeReadErrorCommand("quit");
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.stopThreads();
	}

	/**
	 * Register all Events
	 */

	public void registerAllEvents() {
		writer.executeReadErrorCommand("servernotifyregister event=server");
		writer.executeReadErrorCommand("servernotifyregister event=channel id=0");
		writer.executeReadErrorCommand("servernotifyregister event=textserver");
		writer.executeReadErrorCommand("servernotifyregister event=textchannel");
		writer.executeReadErrorCommand("servernotifyregister event=textprivate");
		writer.executeReadErrorCommand("servernotifyregister event=tokenused");
		debug(DebugOutputType.QUERY, "Registered all Events");
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
	public Ts3AsycAPI getAsycAPI() {
		return asycAPI;
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
	 * New debug Method for more specified debugging and outputting
	 * 
	 * @param type
	 * @param debug
	 *                  message
	 * @see DebugOutputType
	 * @see QueryConfig
	 */

	public synchronized void debug(DebugOutputType type, String debug) {
		int logLevel = -1;
		switch (type) {
		case GENERAL:
			if (config.isGeneralDebug() || config.isEverything()) {
				logLevel = Logger.INFO;
			}
			break;
		case EVENTMANAGER:
			if (config.isEventManagerDebug() || config.isEverything()) {
				logLevel = Logger.EVENT_MANAGER;
			}
			break;
		case KEEPALIVETHREAD:
			if (config.isKeepAliveThreadDebug() || config.isEverything()) {
				logLevel = Logger.INFO;
			}
			break;
		case QUERY:
			if (config.isQueryDebug() || config.isEverything()) {
				logLevel = Logger.QUERY;
			}
			break;
		case QUERYREADER:
			if (config.isQueryReaderDebug() || config.isEverything()) {
				logLevel = Logger.QUERY_READER;
			}
			break;
		case QUERYREADERQUEUE:
			if (config.isQueryReaderQueueDebug() || config.isEverything()) {
				logLevel = Logger.QUERY_READER_QUEUE;
			}
			break;
		case QUERYWRITER:
			if (config.isQueryWriterDebug() || config.isEverything()) {
				logLevel = Logger.QUERY_WRITER;
			}
			break;
		case WARNING:
			if (config.isWarningDebug() || config.isEverything()) {
				logLevel = Logger.WARING;
			}
			break;
		case ERROR:
			if (config.isErrorDebug() || config.isEverything()) {
				logLevel = Logger.ERROR;
			}
			break;
		default:
			if (config.isInDebug(type) || config.isEverything()) {
				logLevel = Logger.EVENT_MANAGER;
			}
			break;
		}
		if (logLevel != -1) {
			if (config.isDebugType(DebugType.CONSOLE) || config.isDebugType(DebugType.BOTH))
				logger.log(logLevel, debug);
			if (config.isDebugType(DebugType.FILE) || config.isDebugType(DebugType.BOTH))
				logger.logFile(logLevel, debug);
		}

	}
}
