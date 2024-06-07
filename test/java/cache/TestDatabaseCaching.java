/**
 * Projekt: TEAMSPEAK - TestBot
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 05.11.2023
 * 
 * Uhrzeit : 20:05:07
 */
package cache;

import java.io.IOException;

import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.wrapper.DataBaseClientInfo;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3BasicAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;


public class TestDatabaseCaching {
	

	private static Ts3ServerQuery query;
	private static Ts3BasicAPI basic;

	@BeforeAll
	public static void connectQuery() {
		query = new Ts3ServerQuery();
		query.getConfig().setFloodRate(FloodRate.DEFAULT_TEAMSPEAK);
		String password = System.getenv("TS3_SERVER_PASSWORD");

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "CachingTest", -1);
		} catch (IOException | QueryLoginException e) {
			Assertions.fail();
		}
		basic = query.getBasicAPI();
	}
	
	@AfterAll
	public static void stopQuery() {
		query.stopQuery();
	}
	
	@RepeatedTest(value = 2)
	public void testGetDatabaseClientList() {
		basic.getDataBaseClients(-1, -1);
	}
	
	@RepeatedTest(value = 2)
	public void testGetDatabseClients() {
		for (DataBaseClientInfo dbClients : basic.getDataBaseClients(-1, -1)) {
			basic.getDataBaseClient(dbClients.getID());
		}
	}
	
}
