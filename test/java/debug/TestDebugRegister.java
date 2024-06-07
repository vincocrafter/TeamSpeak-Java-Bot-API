/**
 * Projekt: TEAMSPEAK - TestBot
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 03.07.2023
 * 
 * Uhrzeit : 20:00:02
 */
package debug;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.util.DebugOutputType;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;

public class TestDebugRegister {

	private static Ts3ServerQuery query;
	private static QueryConfig config;

	@BeforeAll
	public static void connectQuery() {
		query = new Ts3ServerQuery();
		query.getConfig().setFloodRate(FloodRate.DEFAULT_TEAMSPEAK);
		String password = System.getenv("TS3_SERVER_PASSWORD");

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "DebugTest", -1);
		} catch (IOException | QueryLoginException e) {
			Assertions.fail();
		}
		config = query.getConfig();
	}
	
	@AfterAll
	public static void stopQuery() {
		config.removeDebugItem(DebugOutputType.QUERY);
		config.removeDebugItem(DebugOutputType.QUERYWRITER);
		config.removeDebugItem(DebugOutputType.QUERYREADER);
		config.removeDebugItem(DebugOutputType.QUERYREADERQUEUE);
		query.stopQuery();
	}
	
	@Test
	public void testDebug() {
		config.addDebugItem(DebugOutputType.EVENTMANAGER);
		config.addDebugItem(DebugOutputType.KEEPALIVETHREAD);
		config.addDebugItem(DebugOutputType.QUERY);
		config.addDebugItem(DebugOutputType.ERROR);
		config.addDebugItem(DebugOutputType.WARNING);
		config.addDebugItem(DebugOutputType.QUERYREADER);
		config.addDebugItem(DebugOutputType.QUERYREADERQUEUE);
		config.addDebugItem(DebugOutputType.QUERYWRITER);
		config.addDebugItem(DebugOutputType.CACHEMANAGER);
		config.addDebugItem(DebugOutputType.E_CHANNEL_CREATED);
		config.addDebugItem(DebugOutputType.E_CHANNEL_DELETED);
		config.addDebugItem(DebugOutputType.E_CHANNEL_DESCRIPTION_EDITED);
		config.addDebugItem(DebugOutputType.E_CHANNEL_EDITED);
		config.addDebugItem(DebugOutputType.E_CHANNEL_MOVED);
		config.addDebugItem(DebugOutputType.E_CHANNEL_PASSWORD_CHANGED);
		config.addDebugItem(DebugOutputType.E_CLIENT_JOIN);
		config.addDebugItem(DebugOutputType.E_CLIENT_LEAVE);
		config.addDebugItem(DebugOutputType.E_CLIENT_MOVE);
		config.addDebugItem(DebugOutputType.E_PRIVILEGEKEY_USED);
		config.addDebugItem(DebugOutputType.E_SERVER_EDITED);
		config.addDebugItem(DebugOutputType.E_TEXT_MESSAGE);
		
		Assertions.assertEquals(21, config.getDebuglist().size());
	}
	
	@RepeatedTest(value = 5)
	public void testCeckDebugEnabled() {
		config.isInDebug(DebugOutputType.CACHEMANAGER);
		config.isInDebug(DebugOutputType.ERROR);
		config.isInDebug(DebugOutputType.EVENTMANAGER);
		config.isInDebug(DebugOutputType.GENERAL);
		config.isInDebug(DebugOutputType.KEEPALIVETHREAD);
		config.isInDebug(DebugOutputType.QUERY);
		config.isInDebug(DebugOutputType.QUERYREADER);
		config.isInDebug(DebugOutputType.QUERYREADERQUEUE);
		config.isInDebug(DebugOutputType.QUERYWRITER);
		config.isInDebug(DebugOutputType.WARNING);
	}
	
	@RepeatedTest(value = 5)
	public void testCacheEnabled() {
		config.isDataBaseCached();
		config.isGroupsCached();
		config.isPermissionCached();
		config.isQueryCached();
		config.isVirtualServerCached();
	}
	
}
