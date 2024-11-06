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
import java.util.Set;

import net.devcube.vinco.teamspeakapi.api.api.caching.CacheManager;
import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.keepalive.KeepAliveThread;
import net.devcube.vinco.teamspeakapi.api.api.property.TSPermission;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugType;
import net.devcube.vinco.teamspeakapi.api.api.util.Logger;
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

	private QueryReader reader;
	private QueryWriter writer;
	private QueryConfig config = new QueryConfig();
	private CacheManager cache;
	private Ts3SyncAPI syncAPI;
	private Ts3AsyncAPI asyncAPI;
	private Ts3BasicAPI basicAPI;
	private EventManager eventManager;
	private Logger logger;
	private KeepAliveThread keepAliveThread;

	/**
	 * Establishes a connection to the TeamSpeak server, logs in with the provided credentials,
	 * and connects the TeamSpeak query client to the specified virtual server.
	 * 
	 * @param hostname         The hostname or IP address of the TeamSpeak server.
	 * @param port             The query port number of the TeamSpeak server.
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
	 * @param port     The query port number of the TeamSpeak server.
	 * @throws IOException If an I/O error occurs while connecting to the server.
	 */


	public void connect(String hostname, int port) throws IOException {
		this.config.getConnection().connect(hostname, port);
		this.logger = new Logger(config);
		this.eventManager = new EventManager(this);
		this.reader = new QueryReader(config, logger, eventManager);
		this.writer = new QueryWriter(config, logger, reader);
		this.asyncAPI = new Ts3AsyncAPI(writer);
		this.keepAliveThread = new KeepAliveThread(asyncAPI, logger);
		this.cache = new CacheManager(config, logger, writer);
		this.basicAPI = new Ts3BasicAPI(config, logger, writer, cache);
		this.syncAPI = new Ts3SyncAPI(config, logger, writer, cache);

		reader.start(); // starts the reader Thread
		keepAliveThread.start(); // starts KeepAliveThread
	}

	/**
	 * Stops the Query, Socket and all Threads
	 * 
	 */

	public void stopQuery() {
		logger.debug(DebugOutputType.QUERY, "Stopping Query");
		keepAliveThread.interrupt();
		syncAPI.unRegisterAllEvents();
		syncAPI.quit();
		reader.stopThreads();
		try {
			config.getConnection().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(DebugOutputType.QUERY, "Stopping Query successful");
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
}
