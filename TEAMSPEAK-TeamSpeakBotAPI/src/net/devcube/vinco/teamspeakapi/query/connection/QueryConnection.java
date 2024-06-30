/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 16.06.2024
 * 
 * Uhrzeit : 11:03:30
 */
package net.devcube.vinco.teamspeakapi.query.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface QueryConnection {
	
	public void connect(String host, int port) throws IOException;
	
	public void close() throws IOException;
	
	public InputStream getInputStream() throws IOException;
	
	public OutputStream getOutputStream() throws IOException;
	
	public boolean isConnected();
	
}
