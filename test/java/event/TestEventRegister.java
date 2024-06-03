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
package java.event;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import net.devcube.vinco.teamspeakapi.api.api.event.EventManager;
import net.devcube.vinco.teamspeakapi.api.api.event.TsEventAdapter;
import net.devcube.vinco.teamspeakapi.api.api.events.TextMessageEvent;
import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.exception.wrapper.UnknownEventException;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

public class TestEventRegister {
	
	private static Ts3ServerQuery query;

	@BeforeAll
	public static void connectQuery() {
		query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "EventRegisterTest", -1);
		} catch (IOException | QueryLoginException e) {
			Assertions.fail();
		}
	}
	
	
	@AfterAll
	public static void quitQuery() {
		query.stopQuery();
	}
	
	@Test
	public void testRegistering() {
		EventManager ev = query.getEventManager();
		
		assertThrows(UnknownEventException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				ev.registerEvent(null);
			}
		});
		
		ev.addTs3Listener(null); //Noting should happen
		Assertions.assertEquals(0, ev.getEvents().size());
		
		TsEventAdapter adapter = new TsEventAdapter() {
			
			@Override
			public void onTextMessage(TextMessageEvent e) {
				
			}
		};
		
		ev.addTs3Listener(adapter);
		Assertions.assertEquals(1, ev.getEvents().size());
		
		assertThrows(UnknownEventException.class, new Executable() {
			
			@Override
			public void execute() throws Throwable {
				ev.unregisterEvent(null);
			}
		});
		
		ev.removeTs3Listener(null); //Noting should happen
		
		Assertions.assertEquals(1, ev.getEvents().size());
		
		ev.removeTs3Listener(adapter);
		
		Assertions.assertEquals(0, ev.getEvents().size());
	}

}
