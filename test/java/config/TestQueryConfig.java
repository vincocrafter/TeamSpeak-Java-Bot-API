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
package java.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.util.EventCallType;
import net.devcube.vinco.teamspeakapi.api.api.util.FloodRate;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import net.devcube.vinco.teamspeakapi.query.manager.QueryConfig;


public class TestQueryConfig {

	private static Ts3ServerQuery query;

	@BeforeAll
	public static void connectQuery() {
		query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "EventTest", -1);
		} catch (IOException | QueryLoginException e) {
			Assertions.fail();
		}
	}
	
	@AfterAll
	public static void stopQuery() {
		query.stopQuery();
	}

	@Test
	public void testConfig() {
		QueryConfig config = query.getConfig();
		config.setShowTimeMilliseconds(true);
		config.setShowDate(true);
		config.setEventCallType(EventCallType.BOTH);
		config.setFloodRate(FloodRate.custom(100));
		
		assertTrue(config.isShowTimeMilliseconds());
		assertTrue(config.isShowDate());
		assertTrue(config.getEventCallType() == EventCallType.BOTH);
		assertEquals(100, config.getFloodRate().getValue());
	}

}
