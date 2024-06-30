/**
 * Projekt: TEAMSPEAK - TeamSpeakBotJavaAPI
 *
 * Autor : Vincent
 *
 * Jahr 2024  
 *
 * Datum : 16.06.2024
 * 
 * Uhrzeit : 11:06:19
 */
package net.devcube.vinco.teamspeakapi.query.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketConnection implements QueryConnection {
	
	private Socket socket;
	
	@Override
	public void connect(String host, int port) throws IOException {
		this.socket = new Socket(host, port);
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	@Override
	public boolean isConnected() {
		return !socket.isClosed();
	}

}
