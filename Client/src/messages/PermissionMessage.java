package messages;

import java.util.ArrayList;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the message to exchange when to verify the permissions of a block of shared files and folders
 *
 */
public class PermissionMessage extends Message{
	private static final long serialVersionUID = 1L;
	private String pathname;
	/**
	 *  pathname Represents a pathname from a specific node
	 *  emails List of emalis of users that have been shared a specific node
	 *  editable List of permissions given to a specific node
	 * 
	 */
	private ArrayList<String> emails;
	private ArrayList<Boolean> editable;

	/**
	 * Generic constructor for PermissionMessage 
	 * @param username String that represents the username of the client
	 * @param password String that represents the password of the client
	 * @param command Represents the command of the message
	 * @param pathname String that represents the pathname of the node.
	 * @throws Exception throws exception on error.
	 */
	public PermissionMessage(String username, String password, String command, String pathname) throws Exception{
		super(username, password, command);
		this.pathname = pathname;

	}

	/**
	 * Constructor for PermissionMessage with list of emails of every user being shared the specified node and list of permissions of every user being shared
	 * @param username String that represents the username of the client
	 * @param password String that represents the password of the client
	 * @param command Represents the command of the message
	 * @param pathname String that represents the pathname of the node.
	 * @param emails List of strings that contains the email of the users that are being shared the specified node
	 * @param editable List of booleans representing the permissions given to every user being shared the specified node
	 * @throws Exception throws exception on error.
	 */
	public PermissionMessage(String username, String password, String command, String pathname, ArrayList<String> emails, ArrayList<Boolean> editable) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
		if(emails != null) this.emails = emails;
		if(editable != null) this.editable = editable;

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
	 * Getter for Email list
	 * @return emails List of emails refering to every user that have been shared a specific node
	 */
	public ArrayList<String> getEmailsList() {
		return emails;
	}
	
	
	/**
	 * Setter for Email list
	 * @param list List of emails refering to every user that have been shared a specific node
	 */
	public void setEmailsList(ArrayList<String> list) {
		this.emails = list;
	}
	
	
	/**
	 * Getter for Edit list
	 * @return List of permissions of every user that have been shared a specific node
	 */
	public ArrayList<Boolean> getEditList() {
		return editable;
	}
	
	
	/**
	 * Setter for Edit list
	 * @param list List of permissions of every user that have been shared a specific node
	 */
	public void setEditList(ArrayList<Boolean> list) {
		this.editable = list;
	}
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\n";

	}
}