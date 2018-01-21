package gui;


/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the client
 *
 */

public class Client {
	
	/**
	 *  Host name and port number refers to the IP address and port of the socket used for communication.
	 *  Username and password are the fields that hold client log in data
	 */
	private String hostName;
	private int portNumber;
	private String username;
	private String password;
	
	
	/**
	 * Constructor for client class  
	 * @param hostName IP address of server
	 * @param portNumber port of server
	 * @param username username of logged user
	 * @param password password of logged user
	 * @throws Exception FileNotFoundException
	 *
	 */
	public Client (String hostName, int portNumber, String username, String password) throws Exception{
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Getter for hostname
	 * @return hostName string
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Setter for the hostName
	 * @param hostName string
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * Getter for PortNumber
	 * @return portNumber string
	 */
	public int getPortNumber() {
		return portNumber;
	}

	/**
	 * Setter for portNumber
	 * @param portNumber string
	 */
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	/**
	 * Getter for client Username
	 * @return username string
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * @return password string
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * @param password string
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
