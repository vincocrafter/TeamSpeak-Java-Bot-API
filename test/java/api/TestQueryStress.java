/**
 * Projekt: TEAMSPEAK - TestBot
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 09.08.2023
 * 
 * Uhrzeit : 21:31:33
 */
package java.api;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.async.Ts3AsyncAPI;
import net.devcube.vinco.teamspeakapi.api.sync.Ts3SyncAPI;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueryStress {

	private static Ts3ServerQuery query;
	private static Ts3SyncAPI sync;

	@BeforeAll
	public static void connectQuery() {
		String password = System.getenv("TS3_SERVER_PASSWORD");
		assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
		query = new Ts3ServerQuery();

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "Test", -1);
		} catch (IOException | QueryLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(query.getSyncAPI().isConnected());

		sync  = query.getSyncAPI();
	}
	
	@AfterAll
	public static void stopQuery() {
		query.stopQuery();
	}
	
	@RepeatedTest(value = 10)
	public void testStress() {
		
		try {
			assertTrue(sync.getDatabaseIDsByChannelGroup(5).size() > 0); // ChannelGroup is the argument here, example is 5.
			assertTrue(sync.getDatabaseClientInfosByChannelGroup(5).size() > 0); // ChannelGroup is the argument here, example is 5.

			assertTrue(	sync.getDatabaseIDsByServerGroup(10).size() > 0);
			assertTrue(sync.getDatabaseClientInfosByServerGroup(10).size() > 0);

			assertTrue(sync.getChannelGroupsByChannelID(5).size() > 0); // ChannelID is the argument here, example is 5.
			assertEquals(6, sync.getChannelGroupsByDatabaseID(3).size()); // DatabaseID is the argument here, example is 3.
			assertEquals(4, sync.getDatabaseClientInfosByServerGroup(10).size());
			assertTrue(sync.getChannelGroupInfosByDatabaseID(3).size() > 0); // DatabaseID is the argument here, example is 3.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
