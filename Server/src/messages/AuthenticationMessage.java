package messages;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the message to be used between the server and client to validate client's authentication info
 *
 */
public class AuthenticationMessage extends Message {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for an authentication message.
	 * @param username Username from the user that sent the message.
	 * @param password Password from the user.
	 * @param command Command identifier.
	 */
	public AuthenticationMessage(String username, String password, String command) {
		super(username, password, command);
	}
	
	@Override
	public String toString() {
		return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command;
		
	}
	
}
