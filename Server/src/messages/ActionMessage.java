package messages;

import java.io.*;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the data exchange message between the client and server
 *
 */
public class ActionMessage extends Message{
	private static final long serialVersionUID = 1L;
	/**
	 *  pathname Represents the pathname of the file
	 *  file Represents file's info
	 */
	private String pathname;
	private byte[] file;
	/**
	 * Constructor for ActionMessage for download, create folder and delete requests.
	 * @param username username of sender.
	 * @param password password from sender.
	 * @param command command to be processed.
	 * @param pathname pathname of file/folder to be processed.
	 * @throws Exception Exception on error
	 */
	public ActionMessage(String username, String password, String command, String pathname) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
	}
	
	/** 
	 * Constructor for download or upload actions.
	 * @param username username of client.
	 * @param password client's password.
	 * @param command commmand to be processed.
	 * @param pathname pathname of file/folder processed.
	 * @param f File being downloaded or uploaded. 
	 * @throws Exception Exception on error
	 */
	public ActionMessage(String username, String password, String command, String pathname, File f) throws Exception{
		super(username, password, command);
		this.pathname = pathname;
		try (
				FileInputStream fileIn = new FileInputStream(f);
				){
			file = new byte[(int)f.length()];
			fileIn.read(file, 0, (int)f.length());
			
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find the file " + pathname);
			System.exit(1);
		}
	}

	/**Getter for pathname.
	 * @return pathname Pathname from class.
	 */
	public String getPathname() {
		return this.pathname;
	}
	/**Setter for pathname.
	 * @param pathname Pathname from class.
	 */
	public void setPathname(String pathname) {
		this.pathname = pathname;
	}
	/**Getter for File to be processed.
	 * @return file File from class.
	 */
	public byte[] getFile() {
		return file;
	}
	/**Setter for file.
	 * @param file File from class.
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\nPathname: " + this.pathname;
		
	}
}

