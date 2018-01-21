package messages;
import java.util.ArrayList;


/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the registration message 
 *
 */
public class RegisterMessage extends Message{
		private static final long serialVersionUID = 1L;
		/**
		 *  list List containing non-mandatory register fields 
		 */
		private ArrayList<String> list;

		/**
		 * Constructor to create a new register Message.
		 * @param username Message's username.
		 * @param password Password of message's user.
		 * @param command Command to send to the server.
		 * @param list Parameters from Register action (email, address, phone number).
		 */
		public RegisterMessage(String username, String password, String command, ArrayList<String> list ) {
			super(username, password, command);
			this.list = list;
		}
		
		@Override
		public String toString() {
			return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command;
			
		}
		
		/**
		 * Getter for list
		 * @return list Returns the list containing the parameters from register action.
		 */
		public ArrayList<String> getList() {
			return this.list;
		}
}