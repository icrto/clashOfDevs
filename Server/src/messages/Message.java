package messages;

import java.io.*;
import java.net.*;

import utils.Parser;

/**
 * @author Clash of Devs
 * @version 1.0
 * This is the abstract class for the messages exchanged between client and server
 *
 */
public abstract class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String username;
	/**
	 *  username String that represents the username of the client
	 *  password String that represents the password of the client
	 *  command Represents the command of the message
	 */
	protected String password;
	protected String command;

	/**
	 * Constructor to create a new Message.
	 * @param username Username of the client
	 * @param password Password of the client
	 * @param command Command to send to the server
	 */
	public Message(String username, String password, String command) {
		this.username = username;
		this.password = password;
		this.command = command;
	}

	/**
	 * Getter for username
	 * @return username Returns the string containing the username in the object message 
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username
	 * @param username String username representing the clients' username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for password
	 * @return password Returns the string containing the password in the object message
	 */
	public String getPassword() {
		return username;
	}

	/**
	 * Setter for password
	 * @param password String password representing the clients' password
	 * 	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for command
	 * @return command Returns a string containing the command of the message
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Setter for command
	 * @param command Command to send to the server
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * Method to receive Server Greeting
	 * @param in ObjectInputStream 
	 * @return boolean If received Greet from Server returns true
	 * @throws Exception throws exception on error.
	 */
	public boolean receiveServerGreet(ObjectInputStream in) throws Exception{ //method to receive Server Greeting

		try{
			AckMessage greet = (AckMessage) in.readObject();
			if(greet == null) return false;
			else {
				System.out.println(greet.toString());
				if(greet.getCommand().equals("GREET") && greet.getContent().equals("HELLO")) return true;
			}
		}catch(IOException e) {
			System.err.println("Couldn't get I/O for the connection");
			System.exit(1);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return false;

	}

	/**
	 * Method to receive Server Acknowledge.
	 * @param in ObjectInputStream
	 * @return boolean if Server answer with an ACK Message
	 * @throws Exception Exception thrown on error
	 */
	public boolean receiveServerACK(ObjectInputStream in) throws Exception{ //method to receive Server Acknowledge

		try{
			AckMessage ack = (AckMessage) in.readObject();

			if(ack == null) return false;
			else {
				System.out.println(ack.toString());
				if(ack.getUsername().equals(this.getUsername()) && ack.getPassword().equals(this.getPassword()) && ack.getCommand().equals("ACK") && ack.getContent().equals("SUCCESS")) return true;
			}
		}catch(IOException e) {
			System.err.println("Couldn't get I/O for the connection");
			System.exit(1);
		}

		return false;

	}

	/**
	 * Method to send a message through a socket and process the response
	 * @param hostName host IP address
	 * @param portNumber host application port number
	 * @return boolean true or false depending on success
	 * @throws Exception throws exception on error
	 */
	public Object send(String hostName, int portNumber) throws Exception{ //method to send a message & receive server response
		try (
				Socket socket = new Socket(hostName, portNumber);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				)
		{
			String ret = null;
			System.out.println("WAITING GREET");
			if(receiveServerGreet(in)){
				System.out.println("GREET RECEIVED");
				out.writeObject(this);

				if(this.getCommand().equals("DOWNLOAD") || this.getCommand().equals("DOWNLOAD FOLDER")) {
					System.out.println("DOWNLOADING");
					ActionMessage download = (ActionMessage) in.readObject();
					Parser parser = new Parser();
					System.out.println(System.getProperty("user.home") + File.separator + parser.parser(download.getPathname()));
					File novo = new File(System.getProperty("user.home") + File.separator + parser.parser(download.getPathname()));
					if(novo.createNewFile()) {
						FileOutputStream fileOut = new FileOutputStream(novo);
						fileOut.write(download.getFile(), 0, download.getFile().length);
						fileOut.close();
					}

					System.out.println("DOWNLOAD COMPLETE");
					return download;
				}
				else if(this.getCommand().equals("LIST")) {
					System.out.println("LISTING");
					ListMessage listMessage = (ListMessage) in.readObject();
					System.out.println("LIST COMPLETE");
					return listMessage;
				}
				else if(this.getCommand().equals("LIST FOLDERS")) {
					System.out.println("LISTING FOLDERS");
					ListFolderMessage listMessage = (ListFolderMessage) in.readObject();

					System.out.println("LIST FOLDERS COMPLETE");
					return listMessage;
				}
				else if(this.getCommand().equals("LIST SHARED FOLDERS")){
					System.out.println("LISTING SHARED FOLDERS");
					Message message = (Message) in.readObject();
					if(message.getCommand().equals("ACK")) {
						return null;
					}
					else return (ListFolderMessage) message;
				}
				else if(this.getCommand().equals("LIST SHARED") || this.getCommand().equals("LIST SHARING WITH") || this.getCommand().equals("USERS")) {
					System.out.println("LISTING SHARED FOLDERS");
					Message message = (Message) in.readObject();
					if(message.getCommand().equals("ACK")) {
						return null;
					}
					else return (ListMessage) message;

				}
				else if(this.getCommand().equals("SHARE")) {
					AckMessage message = (AckMessage) in.readObject();
					return message.getContent();
				}
				else if(this.getCommand().equals("UPLOAD") || this.getCommand().equals("UPLOAD FOLDER") || this.getCommand().equals("CREATE FOLDER")){
					System.out.println("WAITING ACK");
					if(receiveServerACK(in)) {
						System.out.println("ACK RECEIVED");
						ret = "ACK RECEIVED";
					}
					else {
						System.out.println("FILE/FOLDER ALREADY EXISTS");
						ret = "FILE/FOLDER ALREADY EXISTS";
					}
				}
				else {
					System.out.println("WAITING ACK");
					if(receiveServerACK(in)) {
						System.out.println("ACK RECEIVED");
						ret = "ACK RECEIVED";
					}
					else {
						System.out.println("ACK NOT RECEIVED");
						ret = "ACK NOT RECEIVED";
					}
				}
			}
			else {
				System.out.println("GREET NOT RECEIVED");
				ret  =  "GREET NOT RECEIVED";
			}
			in.close();
			out.close();
			socket.close();
			return ret;
		}catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
		return null;
	}
}
