package messages;

import java.util.ArrayList;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the message to list folders and folders' hierarchy associated with a client
 *
 */
public class ListFolderMessage extends Message{
	private static final long serialVersionUID = 1L;
	private String pathname;
	/**
	 *  pathname Represents the pathname of the parent folder
	 *  file Array that contains all the folders listed to the client
	 *  hierarchy Array that cointans information of the folders' hierarchy 
	 */
	private ArrayList<String> file;
	private ArrayList<Integer> hierarchy;

	/**
	 * @param username Message's username.
	 * @param password Password of message's user.
	 * @param command Command to send to the server.
	 * @param pathname Pathname of the folder. 
	 * @throws Exception throws exception on error.
	 */
	public ListFolderMessage(String username, String password, String command, String pathname) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
	}
	
	/**
	 * Constructor for a List Folder message.
	 * @param username Message's username.
	 * @param password Password of message's user.
	 * @param command Command to send to the server.
	 * @param pathname Pathname of the folder. 
	 * @param file File contains all folders bellow pathname.
	 * @param hierarchy Number that identifies relative position on the tree.
	 * @throws Exception throws exception on error.
	 */
	public ListFolderMessage(String username, String password, String command, String pathname, ArrayList<String> file, ArrayList<Integer> hierarchy) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
		if(file != null) this.file = file;
		if(hierarchy != null) this.hierarchy = hierarchy;

	}
	
	/**
	 * Returns pathname from ListFolderMessage class.
	 * @return pathname Pathname. 
	 */
	public String getPathname() {
		return this.pathname;
	}
	/**
	 * Sets pathname from listFolderMessage class. 
	 * @param pathname Pathname attribute.
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	
	/**
	 * Returns File List.
	 * @return file File attribute from listFolderMessage class.
	 */
	public ArrayList<String> getFileList() {
		return file;
	}
	
	/**
	 * Sets File List.
	 * @param list File attribute from listFolderMessage class. 
	 */
	public void setFileList(ArrayList<String> list) {
		this.file = list;
	}
	
	/**
	 * Returns hierarchy from listFolderMessage class.
	 * @return hierarchy Hierarchy attribute from listFolderMessage class.
	 */
	public ArrayList<Integer> getHierarchyList() {
		return hierarchy;
	}
	
	/**
	 * Sets list parameter from listFolderMessage class.
	 * @param list List attribute from listFolderMessage class
	 */
	public void setHierarchyList(ArrayList<Integer> list) {
		this.hierarchy = list;
	}
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\nPathname: " + this.pathname;

	}
}