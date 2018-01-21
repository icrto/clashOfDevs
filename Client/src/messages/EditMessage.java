package messages;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the message to exchange when to verify the permissions of a specific file/folder
 *
 */
public class EditMessage extends Message{
	private static final long serialVersionUID = 1L;
	/**
	 *  pathname String that represents the pathname of the node to check
	 *  edit Represents the permission
	 * 
	 */
	private String pathname;
	private boolean edit;

	/**
	 * Constructor for EditMessage used to ask the permission of a specific file or folder
	 * @param username Represents the username of the client
	 * @param password Represents the password of the client
	 * @param command Represents the command of the message
	 * @param pathname Represents the pathname of the node to check permissions
	 * @throws Exception Exception thrown
	 */
	public EditMessage(String username, String password, String command, String pathname) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
	}

	/**
	 * Constructor for EditMessage used to send the permission of a specific file or folder
	 * @param username Represents the username of the client
	 * @param password Represents the password of the client
	 * @param command Represents the command of the message
	 * @param pathname Represents the pathname of the node to check permissions
	 * @param edit Represents the permission given to the user
	 * @throws Exception Exception thrown on error
	 */
	public EditMessage(String username, String password, String command, String pathname, boolean edit) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
		this.edit = edit;
	}
	
	
	/**
	 * Getter for pathname
	 * @return pathname String that represents the pathname of a specific node
	 */
	public String getPathname() {
		return this.pathname;
	}
	
	/**
	 * Setter for pathname
	 * @param pathname String that represents the pathname of a specific node
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	/**
	 * Getter for edit
	 * @return edit Represents the permission given to the user to a specific node
	 */
	public boolean getEdit() {
		return this.edit;
	}
	
	/**
	 * Setter for edit
	 * @param edit Represents the permission to a specific node
	 */
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\n";

	}
}