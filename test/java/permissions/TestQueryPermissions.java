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
package java.permissions;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import net.devcube.vinco.teamspeakapi.api.api.exception.query.QueryLoginException;
import net.devcube.vinco.teamspeakapi.api.api.property.TSPermission;
import net.devcube.vinco.teamspeakapi.query.Ts3ServerQuery;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestQueryPermissions {
	
	private static Ts3ServerQuery query;

	@BeforeAll
	public static void connectQuery() {
		query = new Ts3ServerQuery();
		String password = System.getenv("TS3_SERVER_PASSWORD");

		try {
			query.connect("localhost", 10011, "serveradmin", password, 1, "PermissionTest", -1);
		} catch (IOException | QueryLoginException e) {
			Assertions.fail();
		}
	}
	
	@Test
	public void testQueryPermissions() {
		Set<Integer> queryPermissions = query.getSyncAPI().getQueryPermissions();
		assertTrue(queryPermissions.size() > 0);

		TSPermission.checkQueryConnectPermissions(queryPermissions); //Nessesary to connect to the TeamSpeak Server
		TSPermission.checkQueryInformationPermissions(queryPermissions); //Nessesary to get Information about everything the TeamSpeak Server
		TSPermission.checkQueryActionPermissions(queryPermissions); //Nessesary to administrate everything on the TeamSpeak Server
		TSPermission.checkQueryFullPermissions(queryPermissions); //Check for every Permission, NEEDED_MODIFY Permissions mostly are not needed.
	}

}
