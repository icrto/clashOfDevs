package messages;

import java.util.ArrayList;


/**
 * @author Clash of Devs
 * @version 1.0
 * This class is used to list all childs from a specified folder
 *
 */
public class ListMessage extends Message{
	private static final long serialVersionUID = 1L;
	/**
	 *  pathname Represents the pathname of the folder to list
	 *  list Array that represents all childs from a specified folder
	 */
	private String pathname;
	private ArrayList<String> list;
	
	/**
	 * Constructor to create a new List Message.
	 * @param username Message's username
	 * @param password Password of message's user
	 * @param command Command to send to the server
	 * @param pathname Pathname
	 * @throws Exception throws exception on error.
	 */
	public ListMessage(String username, String password, String command, String pathname) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
	}
	
	/**
	 * Constructor to create a new List Message wit List of files/folders to be listed.
	 * @param username Message's username.
	 * @param password Password of message's user.
	 * @param command Command to send to the server.
	 * @param pathname Pathname Pathname to the root folder.
	 * @param list List that contains the names of the folders/files to be sent.
	 * @throws Exception throws exception on error.
	 */
	public ListMessage(String username, String password, String command, String pathname, ArrayList<String> list) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
		if(list != null) this.list = list;
	}
	
	
	/**
	 * Returns the pathname from ListMessage class.
	 * @return pathname pathname from ListMessage class
	 */
	public String getPathname() {
		return this.pathname;
	}
	
	/**
	 * Sets the pathname from ListMessage class.
	 * @param pathname pathname from ListMessage class
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
	/**
	 * Returns the list from ListMessage class.
	 * @return list list from ListMessage class
	 */
	public ArrayList<String> getList() {
		return list;
	}
	/**
	 * Sets the list from ListMessage class.
	 * @param list list from ListMessage class
	 */
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\nPathname: " + this.pathname;

	}
}

