package com.sysbuddy.util;

/**
 * Class for containing FTP information
 * @author darius
 */
public class FTPCredentials {
	
	/**
	 * The server address
	 */
	private final String server;
	
	/**
	 * The server port.
	 */
	private final int port;
	
	/**
	 * The username for authentication.
	 */
	private final String username;
	
	/**
	 * The password for authentication.
	 */
	private final String password;
	
	/**
	 * Creates FTP credentials
	 * @param server The server address
	 * @param port The server port
	 * @param username The username
	 * @param password The password
	 */
	public FTPCredentials(String server, int port, String username, String password) {
		this.server = server;
		this.username = username;
		this.password = password;
		this.port = port;
	}
	
	/**
	 * Creates FTP credentials with a default port of 21.
	 * @param server The server address.
	 * @param username The username.
	 * @param password The password.
	 */
	public FTPCredentials(String server, String username, String password) {
		this(server, 21, username, password);
	}
	
	/**
	 * Gets the server address.
	 * @return The address.
	 */
	public String getServer() {
		return server;
	}

	/**
	 * Gets the server port.
	 * @return The server port
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Gets the username.
	 * @return The username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the password.
	 * @return The password
	 */
	public String getPassword() {
		return password;
	}
}
