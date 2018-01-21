package protocol; 
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the database connection to the server
 *
 */
public class DBO {

	
	/**
	 * @param c Represents the connection to database
	 * @param stmt Represents the query asked to database
	 * @param rs Represents the response from database
	 */
	private Connection c = null;
	private Statement stmt = null;
	private ResultSet rs = null; 

	/**
	 * Generic constructor for DBO
	 */
	public DBO() {
		try {
			Class.forName("org.postgresql.Driver");
			this.c = DriverManager
					.getConnection("jdbc:postgresql://dbm.fe.up.pt:5432/sibd17g14", "sibd17g14", "toni2017");
			this.c.setSchema("LPRO");
			this.stmt = c.createStatement();
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			//System.exit(0);
		}
	}

	/**
	 * Funtion that verifies that user input data matches the data stores in the database, validating or not the login
	 * @param username String that represents the username of the client
	 * @param password String that represents the password of the client
	 * @return boolean true if login is successful or false if login is unsuccessful
	 */
	public boolean login(String username, String password) {
		try {
			this.rs=stmt.executeQuery("SELECT * FROM users WHERE username = " + "'"+username+"'");	
			String hashedPw = null;
			while( rs.next() ) {
				hashedPw = rs.getString("pass");
			}
			if(BCrypt.checkpw(password, hashedPw)) return true;
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println("Error in authentication, invalid username/password combination");
			return false; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that list all childs from specified folder
	 * @param nid Represents the node ID of the folder to share
	 * @return list List of all the childs from specified folder
	 */
	public ArrayList<String> listSharedFilesFromFolder(int nid){
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT path " + 
									  "FROM nodes " +
									  "WHERE parent = " + nid
					);
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("path"));
			}
			System.out.println("BASE DE DADOS:");
			for(int i=0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that lists all descendants from a specified folder shared with an user 
	 * @param uid Represents user ID
	 * @param pathname String that represents the username of the client
	 * @return list List of all descendants
	 */
	public ArrayList<String> listExistingDescendants (int uid, String pathname){
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT path "
					+ "FROM sharedwith "
					+ "JOIN nodes USING(nid) "
					+ "WHERE path ILIKE '" + pathname + "%' AND uid = " + uid );
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("path"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
		
	}
	
	/**
	 * Function that lists everything that a user has shared with someone 
	 * @param uid Represents the ID from the user that shared the nodes
	 * @return list List with everything that user has shared
	 */
	public ArrayList<String> listSharingWith (int uid){
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT DISTINCT path "
					+ "FROM sharedwith "
					+ "JOIN nodes USING(nid) "
					+ "WHERE owner = " + uid);
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("path"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}


	/**
	 * Function that lists every user that has been shared a specific node and the respective permission
	 * @param pathname String that represents the pathname of the node
	 * @param can_edit List with permissions of every user
	 * @return list List of every user that have been shared the node
	 */
	public ArrayList<String> listOfUsers(String pathname, ArrayList<Boolean> can_edit){
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT email, edit " +
					"FROM sharedwith " +
					"JOIN nodes ON nodes.nid = sharedwith.nid " +
					"JOIN users ON users.id = sharedwith.uid " +
					"WHERE path ILIKE '" + pathname + "'");
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("email"));
				can_edit.add(this.rs.getBoolean("edit"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that returns the user's permission to a specific shared node
	 * @param nid Represents the node ID from the node that has been shared
	 * @param uid Represents the user ID from the user that the node has been shared with
	 * @return boolean Represents the user's permission to the shared node
	 */
	public boolean checkEdit(int nid, int uid) {
		try {
			this.rs=stmt.executeQuery("SELECT edit " +
					"FROM sharedwith " +
					"JOIN nodes USING(nid) " +
					"WHERE nid = " + nid + " AND uid = " + uid);
			if(!this.rs.isBeforeFirst())	
				return false;
			this.rs.next();				
			return this.rs.getBoolean("edit");
			
		} catch (Exception e) {
			e.printStackTrace();
			return false; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}
	
	/**
	 * Function that list all the files shared with an user
	 * @param uid Represents the user ID
	 * @return list List of all files shared with the user
	 */
	public ArrayList<String> listSharedWithMe(int uid) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT path "
					+ "FROM nodes "
					+ "JOIN sharedwith USING(nid) "
					+ "WHERE uid = " + uid);
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("path"));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/*public ArrayList<String> listSharedFoldersWithMe(int uid) {
		try {
			ArrayList<String> list = new ArrayList<String>();
			this.rs=stmt.executeQuery("SELECT path "
					+ "FROM sharedwith "
					+ "JOIN nodes ON nodes.nid = sharedwith.nid "
					+ "WHERE uid = " + uid + "AND type = 'Folder'");
			if(!this.rs.isBeforeFirst())	
				return null;
			while( this.rs.next() ) {
				list.add(this.rs.getString("path"));
			}
			for(int i=0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}
	 */
	/**
	 * Function that checks if the node already exists in the database
	 * @param path String that represents the pathname of the node
	 * @return boolean true if node doesn't exist or false if node exists
	 */
	public boolean checkNodeIsNullFromPath(String path) {
		try {
			this.rs=stmt.executeQuery("SELECT * FROM nodes WHERE path = " + "'" + path + "'");
			return (!this.rs.isBeforeFirst()); //retorna true se o valor for null e false se o valor for diferente de null

		} catch (Exception e) {
			e.printStackTrace();
			return true; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that inserts node to the database
	 * @param path String that represents the pathname of the node
	 * @param type String that represents the type of the node (Folder or File)
	 * @param owner Integer representing the owner's ID of the node 
	 * @param parent Integer representing the parent node
	 * @return boolean returns true if successful, false otherwise
	 */
	public boolean insertNodeToShare(String path, String type, int owner, int parent) {
		try {
			if(parent == -1) {
				this.rs = stmt.executeQuery("INSERT INTO nodes (path, type, owner, parent) VALUES (" + "'" + path + "'" + "," + "'" + type + "'" + "," 
						+ owner + "," + null + ");");
			}
			else {
				this.rs = stmt.executeQuery("INSERT INTO nodes (path, type, owner, parent) VALUES (" + "'" + path + "'" + "," + "'" + type + "'" + "," 
						+ owner + "," + parent + ");");
			}

			return true;
		} catch(Exception e) {
			if(e.getMessage().contains("No results were returned by the query"))
				return true;
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function that inserts the relation share between a node and a user
	 * @param nid Integer representing the node ID 
	 * @param uid Integer representing the user ID
	 * @return boolean returns true if successful, false otherwise
	 */
	public boolean insertSharedWith(int nid, int uid) {
		try {

			this.rs = stmt.executeQuery("INSERT INTO sharedwith (nid, uid) VALUES (" + nid + "," + uid + ");");
			return true;

		} catch(Exception e) {
			if(e.getMessage().contains("No results were returned by the query")) // INSERT INTO bem sucedido
				return true;
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Function that changes the permission of a share relation
	 * @param nid Integer representing the node ID
	 * @param uid Integer representing the user ID
	 * @param permission Boolean representing the permission
	 */
	public void updatePermission (int nid, int uid, boolean permission) {
		try {
			this.rs=stmt.executeQuery("UPDATE sharedwith SET edit = " + permission + " WHERE nid = " + nid + " AND uid = " + uid + " ;");
			this.rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			return; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that deletes relation shared
	 * @param nid Integer representing the node ID
	 * @param uidToShare Integer representing the user ID
	 */
	public void deleteSharedWithMe(int nid, int uidToShare) {
		try {
			this.rs=stmt.executeQuery("DELETE FROM sharedwith WHERE nid = " + nid + "AND uid = " + uidToShare);
			this.rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			return; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}
	
	/**
	 * Function that deletes node from database
	 * @param nid Integer representing the node ID
	 */
	public void deleteNode(int nid) {
		try {
			this.rs=stmt.executeQuery("DELETE FROM nodes WHERE nid = " + nid);
			this.rs.next();

		} catch (Exception e) {
			e.printStackTrace();
			return; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that returns user ID 
	 * @param username  String that represents the username from user
	 * @return id Integer that represents user identifier
	 */
	public int getUIDbyUsername(String username) {
		try {
			this.rs=stmt.executeQuery("SELECT id FROM users WHERE username = " + "'" + username + "'");
			this.rs.next();
			return this.rs.getInt("id");

		} catch (Exception e) {
			e.printStackTrace();
			return -1; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that returns node ID from pathname
	 * @param path  String that represents the pathname of the node
	 * @return int Integer that refers to node ID
	 */
	public int getNIDbyPath(String path) {
		try {
			this.rs=stmt.executeQuery("SELECT nid FROM nodes WHERE path = " + "'" + path + "'");
			this.rs.next();
			return this.rs.getInt("nid");

		} catch (Exception e) {
			e.printStackTrace();
			return -1; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that returns user ID from email
	 * @param email email associated to user ID
	 * @return uid Integer that represents user ID
	 */
	public int getUIDbyEmail(String email) {
		try {
			System.out.println("Email -> " + email );
			this.rs=stmt.executeQuery("SELECT id FROM users WHERE email = " + "'" + email + "'");	
			this.rs.next();
			return this.rs.getInt("id");

		} catch (Exception e) {
			e.printStackTrace();
			return -1; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**Function that verifies if user exists on database
	 * @param username  String that represents the username
	 * @return boolean returns true if user exists, false otherwise
	 */
	public boolean userExists(String username) {
		try {
			this.rs=stmt.executeQuery("SELECT * FROM users WHERE username ILIKE " + "'" + username + "'");	
			if(!this.rs.isBeforeFirst())	
				return false;
			else
				return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false; //meti isto por default mas pode-se fazer testes ao erro que vem 
		}
	}

	/**
	 * Function that regists a new user into the database
	 * @param username String that represents the username of the client
	 * @param password String that represents the password of the client
	 * @param list List of parameters from Register action (email, address, phone number).
	 * @return boolean true if register is successful, false otherwise
	 */
	public boolean register(String username, String password, ArrayList<String> list) {
		try {
			username = "'"+username+"'";
			String name = "'"+list.get(0)+"'";
			String hashPw = "'"+BCrypt.hashpw(password, BCrypt.gensalt())+"'";
			String email = "'"+list.get(1)+"'";
			String address = "'"+list.get(2)+"'";
			int phone;
			if(list.get(2).equals("")) address = null;
			if(!list.get(3).equals("")) {
				phone = Integer.parseInt(list.get(3));
				this.rs = stmt.executeQuery("INSERT INTO users(username, pass, address, name, email, phone) VALUES ("
						+ username + ", " + hashPw + ", " + address + ", " + name + ", " + email 
						+ ", " + phone + ");");
			}
			else {
				this.rs = stmt.executeQuery("INSERT INTO users(username, pass, address, name, email) VALUES ("
						+ username + ", " + hashPw + ", " + address + ", " + name + ", " + email + ");");
			}



			return true;
		} catch(Exception e) {
			if(e.getMessage().contains("No results were returned by the query"))
				return true;
			e.printStackTrace();
			return false;
		}
	}
}
