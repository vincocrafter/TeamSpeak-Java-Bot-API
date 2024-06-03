/**
 * Projekt: TEAMSPEAK - TestBot
 *
 * Autor : Vincent
 *
 * Jahr 2023  
 *
 * Datum : 09.08.2023
 * 
 * Uhrzeit : 21:20:44
 */
package java.connect;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;
import static org.junit.jupiter.api.Assertions.*;

public class TestQueryConnect {

	@Test
	public void testConnectionNormal() {
		Ts3ServerQuery query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");
		assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
		assertDoesNotThrow(new Executable() {
			@Override
			public void execute() throws Throwable {
					query.connect("localhost", 10011, "serveradmin", password, 1, "Test", -1);
					query.stopQuery();
			}
		});
	}
	

	@Test
	public void testConnectionOtherOne() {
		Ts3ServerQuery query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");
		assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
		assertDoesNotThrow(new Executable() {
			@Override
			public void execute() throws Throwable {
				query.connect("localhost", 10011);
				query.getSyncAPI().login("serveradmin", password);
				query.getSyncAPI().connectTeamSpeakQuery(1, "Test");
				query.getSyncAPI().registerAllEvents();
				query.stopQuery();
			}
		});
	}



	@Test
	public void testConnectionOtherTwo() {
		Ts3ServerQuery query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");
		assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
		
		assertDoesNotThrow(new Executable() {

			@Override
			public void execute() throws Throwable {
				query.connect("localhost", 10011);
				query.getSyncAPI().login("serveradmin", password);
				query.getSyncAPI().selectVirtualServer(1);
				query.getSyncAPI().updateQueryName("Test");
				query.getSyncAPI().registerAllEvents();
				query.stopQuery();
			}
		});
	}


	
	@Test
	public void testConnectionWithoutLogin() {
		Ts3ServerQuery query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");
		assertNotNull(password, "Environment variable TS3_SERVER_PASSWORD is not set.");
		assertDoesNotThrow(new Executable() {

			@Override
			public void execute() throws Throwable {
				query.connect("localhost", 10011);
				query.getSyncAPI().selectVirtualServer(1);
				query.getSyncAPI().updateQueryName("Test");
				query.getSyncAPI().getQueryInfo();
				query.stopQuery();
			}
		});
	}
	
}
