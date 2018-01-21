package messages;
/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the acknowledgements from the server to clients' requests
 *
 */


public class AckMessage extends Message{
	private static final long serialVersionUID = 1L;
		/**
	 	* Represents the content of the message, if the server successfully answers the client's request, sends SUCCESS else sends an error message
	 	*/
		private String content;
		/**
		 * Constructor of AckMessage with its own content
		 * @param username username of the user
		 * @param password for verifying proposes
		 * @param command field to hold the string with the ACK command
		 * @param content field to hold the string with the ACK content
		 * @throws Exception Exception on error
		 */
		public AckMessage(String username, String password, String command, String content) throws Exception{
			super(username, password, command);
			this.content = content;
		}
		/**
		 * Getter for content
		 * @return Returns a string representing the content of the message
		 */
		public String getContent() {
			return this.content;
		}
		/**
		 * Setter for content
		 * @param content String content represent string to set
		 */
		public void setContent(String content) {
			this.content = content;
		}
		@Override
		public String toString() {
			return "Username: " + this.username + "\nPassword: " + this.password + "\nCommand: " + this.command + "\nContent: " + this.content;
			
		}
}
