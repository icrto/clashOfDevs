package messages;


/**
 * @author Clash of Devs
 * @version 1.0
 * This class is used to list all childs from a specified folder
 *
 */
public class ShareMessage extends Message{
	private static final long serialVersionUID = 1L;
	/**
	 *  path Represents the pathname of the file to share
	 *  email Email of the user to share the selected file
	 */
	private String path;
	private String email;

	/**
	 * Constructor to create a new Share Message.
	 * @param username Message's username.
	 * @param password Password of message's user.
	 * @param command Command to send to the server.
	 * @param path Path of shared file/folder.
	 * @param email Email of the user to share the selected file. 
	 * @throws Exception throws exception on error.
	 */
	public ShareMessage(String username, String password, String command, String path, String email) throws Exception{
		super(username, password, command);
		this.path = path;
		this.email = email;
	}
	
	/**
	 * Getter for pathname
	 * @return path Returns the pathname specified in the message
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Setter for pathname
	 * @param path Represents the pathname of the file to share
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Getter for email
	 * @return email Returns the email of the user to share the selected file
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Setter for email
	 * @param email Represents the email of the user to share files with
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\nPath: " + this.path + "\nEmail: " + this.email;

	}
}

