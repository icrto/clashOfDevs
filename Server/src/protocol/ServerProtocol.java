package protocol;

import java.io.*;
import java.util.ArrayList;

import messages.AckMessage;
import messages.ActionMessage;
import messages.AuthenticationMessage;
import messages.EditMessage;
import messages.ListFolderMessage;
import messages.ListMessage;
import messages.Message;
import messages.PermissionMessage;
import messages.RegisterMessage;
import messages.ShareMessage;
import utils.Parser;
import utils.Zip;


/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents the protocol of the server to answer client's requests
 *
 */
public class ServerProtocol {

	/**
	 * @param answer Generic message containing the request from the client
	 */
	private Message answer;

	/**
	 * Function that handles the logic of the response of the server in order to a client request
	 * @param message Message containing the information sent by the client
	 * @return answer Message containing the information of the response sent by the server
	 * @throws Exception 
	 */
	public Message processInput(Object message) throws Exception  {
		Message aux = (Message) message;

		if(message == null) { //Mensagem de Greeting
			try {
				answer = new AckMessage("", "", "GREET", "HELLO");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("REGISTER")) {
			RegisterMessage registerMessage = (RegisterMessage) message;
			DBO conn = new DBO();
			if(conn.userExists(registerMessage.getUsername())) {
				answer = new AckMessage(registerMessage.getUsername(),registerMessage.getPassword(),"ACK","USERNAME NOT VALID");	
			}
			else if(conn.register(registerMessage.getUsername(), registerMessage.getPassword(), registerMessage.getList())) {
				answer = new AckMessage(registerMessage.getUsername(),registerMessage.getPassword(),"ACK","SUCCESS");
				File novo = new File("root"+File.separator+registerMessage.getUsername());
				if(!novo.mkdirs()) answer = new AckMessage(registerMessage.getUsername(),registerMessage.getPassword(),"ACK","ERROR CREATING FOLDER");
				int uid = conn.getUIDbyUsername(registerMessage.getUsername());
				conn.insertNodeToShare(registerMessage.getUsername(), "Folder",uid , -1);
			}
			else
				answer = new AckMessage(registerMessage.getUsername(),registerMessage.getPassword(),"ACK","REGISTER NOT SUCCESSFUL");
		}
		else if(aux.getCommand().equals("LOGIN")) {
			AuthenticationMessage loginMessage = (AuthenticationMessage) message;
			if(isValidUser(loginMessage.getUsername(),loginMessage.getPassword()))
				answer = new AckMessage(loginMessage.getUsername(),loginMessage.getPassword(),"ACK","SUCCESS");
			else
				answer = new AckMessage(loginMessage.getUsername(),loginMessage.getPassword(),"ACK","LOGIN NOT SUCCESSFUL");
		}
		else if(aux.getCommand().equals("UPLOAD")) {
			try {
				Parser parser = new Parser();
				ActionMessage UploadMessage = (ActionMessage) message;
				if(!isValidUser(UploadMessage.getUsername(),UploadMessage.getPassword())) {
					return answer = new AckMessage(UploadMessage.getUsername(),UploadMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(UploadMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				File novo = new File("root",correctPathname);
				if(novo.createNewFile()) System.out.println("Ficheiro criado");
				else return answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "FILE/FOLDER ALREADY EXISTS");

				FileOutputStream fileOut = new FileOutputStream(novo);
				fileOut.write(UploadMessage.getFile(), 0, UploadMessage.getFile().length);	// Escreve o ficheiro todo
				fileOut.close();
				
				DBO conn = new DBO();
		
				int nid = conn.getNIDbyPath(parser.parserToDB(parser.getParentDirPathname(UploadMessage.getPathname())));
				int uid = conn.getUIDbyUsername(UploadMessage.getUsername());
				conn.insertNodeToShare(parser.parserToDB(UploadMessage.getPathname()), "File", uid, nid);
				
				answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "SUCCESS");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("UPLOAD FOLDER")) {
			try {
				Parser parser = new Parser();
				ActionMessage UploadMessage = (ActionMessage) message;
				if(!isValidUser(UploadMessage.getUsername(),UploadMessage.getPassword())) {
					return answer = new AckMessage(UploadMessage.getUsername(),UploadMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(UploadMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				String dirPathname = parser.getParentDirPathname(UploadMessage.getPathname());

				File novo = new File("root",correctPathname);
				if(novo.createNewFile()) System.out.println("Ficheiro criado");
				else return answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "FILE/FOLDER ALREADY EXISTS");

				FileOutputStream fileOut = new FileOutputStream(novo);
				fileOut.write(UploadMessage.getFile(), 0, UploadMessage.getFile().length);	// Escreve o ficheiro todo
				fileOut.close();

				Zip zip = new Zip();
				zip.unzip("root" + correctPathname, "root" + File.separator + dirPathname);

				novo.delete(); // porque novo é o zip
				
				File toInsert = new File("root" + File.separator + parser.removeExtension(UploadMessage.getPathname()));
				DBO conn = new DBO();
				int uid = conn.getUIDbyUsername(UploadMessage.getUsername());
				insertFiles(toInsert, uid);
				
				answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "SUCCESS");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("UPLOAD SHARED")) {
			try {
				Parser parser = new Parser();
				ActionMessage UploadMessage = (ActionMessage) message;
				if(!isValidUser(UploadMessage.getUsername(),UploadMessage.getPassword())) {
					return answer = new AckMessage(UploadMessage.getUsername(),UploadMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(UploadMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				File novo = new File("root",correctPathname);
				if(novo.isDirectory())
					delete(novo);
				else
					deleteFile(novo);
				if(novo.createNewFile()) System.out.println("Ficheiro criado");
				else return answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "FILE/FOLDER ALREADY EXISTS");

				FileOutputStream fileOut = new FileOutputStream(novo);
				fileOut.write(UploadMessage.getFile(), 0, UploadMessage.getFile().length);	// Escreve o ficheiro todo
				fileOut.close();
				
				answer = new AckMessage(UploadMessage.getUsername(), UploadMessage.getPassword(), "ACK", "SUCCESS");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("CREATE FOLDER")) {
			try {
				Parser parser = new Parser();
				ActionMessage CreateFolder = (ActionMessage) message;
				if(!isValidUser(CreateFolder.getUsername(),CreateFolder.getPassword())) {
					return answer = new AckMessage(CreateFolder.getUsername(),CreateFolder.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(CreateFolder.getPathname()); // Corrige o Pathname conforme o FileSystem
				File novo = new File("root",correctPathname);
				DBO conn = new DBO();
				if(novo.exists() == false) {
					if(novo.mkdir()) {
						answer = new AckMessage(CreateFolder.getUsername(), CreateFolder.getPassword(), "ACK", "SUCCESS");
						
						int nid = conn.getNIDbyPath(parser.parserToDB(parser.getParentDirPathname(CreateFolder.getPathname())));
						int uid = conn.getUIDbyUsername(CreateFolder.getUsername());
						conn.insertNodeToShare(parser.parserToDB(CreateFolder.getPathname()), "Folder", uid, nid);
					}
				}
				else answer = new AckMessage(CreateFolder.getUsername(), CreateFolder.getPassword(), "ACK", "FOLDER ALREADY EXISTS");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		else if(aux.getCommand().equals("DOWNLOAD")) {
			Parser parser = new Parser();
			ActionMessage DownloadMessage = (ActionMessage) message;
			try {
				if(!isValidUser(DownloadMessage.getUsername(),DownloadMessage.getPassword())) {
					return answer = new AckMessage(DownloadMessage.getUsername(),DownloadMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(DownloadMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				File novo = new File("root",correctPathname);
				answer = new ActionMessage(DownloadMessage.getUsername(),DownloadMessage.getPassword(),"DOWNLOAD",DownloadMessage.getPathname(), novo); //ATENCAO FALTA O CAMPO DE DESTINO
				System.out.println("DOWNLOAD COMPLETE");
			}
			catch (IOException e) {
				e.printStackTrace();
				return answer = new AckMessage(DownloadMessage.getUsername(), DownloadMessage.getPassword(), "ACK", "FILE NOT FOUND");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("DOWNLOAD FOLDER")) {
			Parser parser = new Parser();
			ActionMessage DownloadMessage = (ActionMessage) message;
			try {
				if(!isValidUser(DownloadMessage.getUsername(),DownloadMessage.getPassword())) {
					return answer = new AckMessage(DownloadMessage.getUsername(),DownloadMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(DownloadMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				new Zip("root" + correctPathname, parser.parser(correctPathname) + ".zip");
				File novo = new File(parser.parser(correctPathname) + ".zip");
				answer = new ActionMessage(DownloadMessage.getUsername(),DownloadMessage.getPassword(),"DOWNLOAD FOLDER",DownloadMessage.getPathname()+".zip", novo); //ATENCAO FALTA O CAMPO DE DESTINO
				System.out.println("DOWNLOAD FOLDER COMPLETE");
				novo.delete();
			}
			catch (IOException e) {
				e.printStackTrace();
				return answer = new AckMessage(DownloadMessage.getUsername(), DownloadMessage.getPassword(), "ACK", "FILE NOT FOUND");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("LIST")) {
			try {
				Parser parser = new Parser();
				ListMessage ListMessage = (ListMessage) message;
				ArrayList<String> list = new ArrayList<String>();
				if(!isValidUser(ListMessage.getUsername(),ListMessage.getPassword())) {
					return answer = new AckMessage(ListMessage.getUsername(),ListMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String folderName = parser.processPath(ListMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				File novo = new File("root",folderName);
				for(int i = 0; i < novo.list().length; i++)
					list.add(novo.list()[i]);
				answer = new ListMessage (ListMessage.getUsername(), ListMessage.getPassword(),"LIST",ListMessage.getPathname(),list);
				System.out.println("LISTING COMPLETE");

			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("LIST SHARED")) {
			try {
				ListMessage ListMessage = (ListMessage) message;
				ArrayList<String> listDB = new ArrayList<String>();
				
				if(!isValidUser(ListMessage.getUsername(),ListMessage.getPassword())) {
					return answer = new AckMessage(ListMessage.getUsername(),ListMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				Parser parser = new Parser();
				DBO conn = new DBO();
				int uid = conn.getUIDbyUsername(ListMessage.getUsername());
				if(uid == -1)
					return answer = new AckMessage(ListMessage.getUsername(), ListMessage.getPassword(), "ACK", "ERROR LOADING USER INFO"); 
				if(ListMessage.getPathname() == null) {
					listDB = conn.listSharedWithMe(uid);
					if(listDB == null) return answer = new AckMessage(ListMessage.getUsername(), ListMessage.getPassword(), "ACK", "NO SHARED MESSAGES"); 

				}
				else {
					System.out.println(parser.parserToDB(ListMessage.getPathname()));
					int nid = conn.getNIDbyPath(parser.parserToDB(ListMessage.getPathname()));
					listDB = conn.listSharedFilesFromFolder(nid);
				}
				if(listDB == null)
					return answer = new AckMessage(ListMessage.getUsername(), ListMessage.getPassword(), "ACK", "NO SHARED MESSAGES"); 
				answer = new ListMessage (ListMessage.getUsername(), ListMessage.getPassword(),"LIST SHARED",ListMessage.getPathname(),listDB);
				System.out.println("LISTING SHARED COMPLETE");
					
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("LIST FOLDERS")) {
			try {
				ListFolderMessage ListFolderMessage = (ListFolderMessage) message;
				if(!isValidUser(ListFolderMessage.getUsername(),ListFolderMessage.getPassword())) {
					return answer = new AckMessage(ListFolderMessage.getUsername(),ListFolderMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				ArrayList<String> list = new ArrayList<String>();
				ArrayList<Integer> listHierarchy = new ArrayList<Integer>();
				File folderToSend = new File("root"+File.separator+ListFolderMessage.getPathname());
				list.add(ListFolderMessage.getUsername());
				listHierarchy.add(1);
				listFolders(folderToSend,list,listHierarchy,2); 

				answer = new ListFolderMessage (ListFolderMessage.getUsername(), ListFolderMessage.getPassword(),"LIST FOLDERS",ListFolderMessage.getPathname(),list, listHierarchy);
				
				System.out.println("LISTING FOLDER COMPLETE");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("LIST SHARING WITH")) {
			ListMessage ListMessage = (ListMessage) message;
			try {
				if(!isValidUser(ListMessage.getUsername(),ListMessage.getPassword())) {
					return answer = new AckMessage(ListMessage.getUsername(),ListMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				DBO conn = new DBO();
				int uid = conn.getUIDbyUsername(ListMessage.getUsername());
				if(uid == -1)
					return answer = new AckMessage(ListMessage.getUsername(), ListMessage.getPassword(), "ACK", "ERROR LOADING USER INFO"); 
				ArrayList<String> list = new ArrayList<String>();
				list = conn.listSharingWith(uid);
				if(list == null)
					return answer = new AckMessage(ListMessage.getUsername(), ListMessage.getPassword(), "ACK", "NOTHING SHARED");
				
				answer = new ListMessage (ListMessage.getUsername(), ListMessage.getPassword(),"LIST SHARING WITH",ListMessage.getPathname(),list);
				System.out.println("LISTING SHARED WITH COMPLETE");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("USERS")) {
			Parser parser = new Parser();
			PermissionMessage PermissionMessage = (PermissionMessage) message;
			try {
				if(!isValidUser(PermissionMessage.getUsername(),PermissionMessage.getPassword())) {
					return answer = new AckMessage(PermissionMessage.getUsername(),PermissionMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				DBO conn = new DBO();
				int uid = conn.getUIDbyUsername(PermissionMessage.getUsername());
				if(uid == -1)
					return answer = new AckMessage(PermissionMessage.getUsername(), PermissionMessage.getPassword(), "ACK", "ERROR LOADING USER INFO"); 
				ArrayList<String> list = new ArrayList<String>();
				ArrayList<Boolean> can_edit = new ArrayList<Boolean>();
				list = conn.listOfUsers(parser.parserToDB(PermissionMessage.getPathname()),can_edit);
				if(list == null)
					return answer = new AckMessage(PermissionMessage.getUsername(), PermissionMessage.getPassword(), "ACK", "NOTHING SHARED");
			
				answer = new PermissionMessage(PermissionMessage.getUsername(), PermissionMessage.getPassword(),"USERS",PermissionMessage.getPathname(),list,can_edit);
				System.out.println("LISTING USERS COMPLETE");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("DELETE")) {
			Parser parser = new Parser();
			ActionMessage DeleteMessage = (ActionMessage) message;
			try {
				if(!isValidUser(DeleteMessage.getUsername(),DeleteMessage.getPassword())) {
					return answer = new AckMessage(DeleteMessage.getUsername(),DeleteMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				String correctPathname = parser.processPath(DeleteMessage.getPathname()); // Corrige o Pathname conforme o FileSystem
				DBO conn = new DBO();
				if(!conn.checkNodeIsNullFromPath(parser.parserToDB(DeleteMessage.getPathname()))) {
					conn.deleteNode(conn.getNIDbyPath(parser.parserToDB(DeleteMessage.getPathname())));
				}
					
				File novo = new File("root",correctPathname);
				if(novo.isDirectory())
					delete(novo);
				else
					deleteFile(novo);
				answer = new AckMessage(DeleteMessage.getUsername(), DeleteMessage.getPassword(), "ACK", "SUCCESS");
				System.out.println("DELETE COMPLETE");
			}
			catch (IOException e) {
				e.printStackTrace();
				return answer = new AckMessage(DeleteMessage.getUsername(), DeleteMessage.getPassword(), "ACK", "FILE NOT FOUND");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("EDIT PERMISSIONS")) {
			Parser parser = new Parser();
			PermissionMessage PermissionMessage = (PermissionMessage) message;
			try {
				if(!isValidUser(PermissionMessage.getUsername(),PermissionMessage.getPassword())) {
					return answer = new AckMessage(PermissionMessage.getUsername(),PermissionMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				DBO conn = new DBO();
				int nid, uidToShare;
				nid = conn.getNIDbyPath(parser.parserToDB(PermissionMessage.getPathname()));
				for(int i = 0; i < PermissionMessage.getEmailsList().size(); i++) {
					uidToShare = conn.getUIDbyEmail(PermissionMessage.getEmailsList().get(i));
					conn.updatePermission(nid, uidToShare, PermissionMessage.getEditList().get(i));
				}
				answer = new AckMessage(PermissionMessage.getUsername(),PermissionMessage.getPassword(),"ACK","SUCCESS");
				
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("DELETE PERMISSIONS")) {
			Parser parser = new Parser();
			PermissionMessage PermissionMessage = (PermissionMessage) message;
			try {
				if(!isValidUser(PermissionMessage.getUsername(),PermissionMessage.getPassword())) {
					return answer = new AckMessage(PermissionMessage.getUsername(),PermissionMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				DBO conn = new DBO();
				int nid, uidToShare;
				nid = conn.getNIDbyPath(parser.parserToDB(PermissionMessage.getPathname()));
				for(int i = 0; i < PermissionMessage.getEmailsList().size(); i++) {
					uidToShare = conn.getUIDbyEmail(PermissionMessage.getEmailsList().get(i));
					if(PermissionMessage.getEditList().get(i))
						conn.deleteSharedWithMe(nid, uidToShare);
				}
				answer = new AckMessage(PermissionMessage.getUsername(),PermissionMessage.getPassword(),"ACK","SUCCESS");
				
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("CHECK EDIT")) {
			EditMessage EditMessage = (EditMessage) message;
			Parser parser = new Parser();
			try {
				if(!isValidUser(EditMessage.getUsername(),EditMessage.getPassword())) {
					return answer = new AckMessage(EditMessage.getUsername(),EditMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				DBO conn = new DBO();
				int nid = conn.getNIDbyPath(parser.parserToDB(EditMessage.getPathname()));
				int uid = conn.getUIDbyUsername(EditMessage.getUsername());
				boolean edit = conn.checkEdit(nid, uid);
				answer = new EditMessage(EditMessage.getUsername(),EditMessage.getPassword(),"CHECK EDIT",EditMessage.getPathname(),edit);
				
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(aux.getCommand().equals("SHARE")) {
			ShareMessage ShareMessage = (ShareMessage) message;
			try {
				if(!isValidUser(ShareMessage.getUsername(),ShareMessage.getPassword())) {
					return answer = new AckMessage(ShareMessage.getUsername(),ShareMessage.getPassword(),"ACK","USER/PASS INVALID");
				}
				Parser parser = new Parser();
				ArrayList<String> listDB = new ArrayList<String>();
				DBO conn = new DBO();
				int uidToShare = conn.getUIDbyEmail(ShareMessage.getEmail());
				if(uidToShare == -1)
					return answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "USER NOT FOUND");
				int uid = conn.getUIDbyUsername(ShareMessage.getUsername());
				if(uid == -1)
					return answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "ERROR LOADING USER INFO"); 
				String str = parser.parserToDB(ShareMessage.getPath());
				
				int nid = conn.getNIDbyPath(str);
				if(nid != -1) {
					listDB = conn.listSharedWithMe(uidToShare);
					if(listDB != null) {
						for(int i = 0; i < listDB.size(); i++) {
							if(str.indexOf(listDB.get(i)) != -1)
								return answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "ALREADY SHARING");
						}
					}
					listDB = conn.listExistingDescendants(uidToShare, str);
					if(listDB != null) {
						for(int i = 0; i < listDB.size(); i++) {
							conn.deleteSharedWithMe(conn.getNIDbyPath(listDB.get(i)), uidToShare);
						}
					}
					if(conn.insertSharedWith(nid, uidToShare)) {

						answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "SUCCESS");
					}
					else
						answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "ALREADY SHARING");
				}
				else
					answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "FILE NOT FOUND");
			
			}
			catch (IOException e) {
				e.printStackTrace();
				return answer = new AckMessage(ShareMessage.getUsername(), ShareMessage.getPassword(), "ACK", "FILE NOT FOUND");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return answer;
	}


	/**
	 * Function that deletes a node and its descendants from the file system
	 * @param file Node to be deleted
	 * @throws IOException
	 */
	public void delete(File file) throws IOException {

		for (File childFile : file.listFiles()) {

			if (childFile.isDirectory()) {
				delete(childFile);
			} else {
				if (!childFile.delete()) {
					throw new IOException();
				}
			}
		}

		if (!file.delete()) { 				// only runs once to delete the folder
			throw new IOException();
		}


	}
	

	/**
	 * Function that deletes a file from the file system
	 * @param file File to be deleted
	 * @throws IOException
	 */
	public void deleteFile(File file) throws IOException {

		if (!file.delete()) {
			throw new IOException();
		}

	}
	
	
	public boolean isValidUser(String username, String password) {
		DBO conn = new DBO();
		return conn.login(username,password);
	}
	
	public void listFiles(File pathname, ArrayList<String> fileList) {
		Parser parser = new Parser();
		for(File file : pathname.listFiles()) {
			if(file.isDirectory()) {
				fileList.add(parser.parserToDB(parser.takeRootOut(file.getPath())));
				listFiles(file, fileList);
			}
			else
				fileList.add(parser.parserToDB(parser.takeRootOut(file.getPath())));
		}
	}
	
	/**
	 * Function that inserts the node and its descendants into the database
	 * @param pathname String that represents the pathname of the node
	 * @param uid User ID of the owner of the node
	 */
	public void insertFiles(File pathname, int uid) {
		DBO conn = new DBO();
		Parser parser = new Parser();
		String type = "File";
		if(pathname.isDirectory())	type = "Folder";
		int nid = conn.getNIDbyPath(parser.parserToDB(parser.takeRootOut(parser.getParentDirPathname(pathname.getPath()))));
		conn.insertNodeToShare(parser.parserToDB(parser.takeRootOut(pathname.getPath())), type , uid, nid);
		for(File file : pathname.listFiles()) {
			if(file.isDirectory()) {
				insertFiles(file, uid);
			}
			else {
				nid = conn.getNIDbyPath(parser.parserToDB(parser.takeRootOut(pathname.getPath())));
				conn.insertNodeToShare(parser.parserToDB(parser.takeRootOut(file.getPath())), "File" , uid, nid);
			}
		}
	}
	
	
	
	/**
	 * Function that lists all folders, including its own descendants, hierarchically into an array
	 * @param pathname String that represents the pathname of the node
	 * @param folderList List of folders
	 * @param listHierarchy Represents the hierarchy of the folder within the same index
	 * @param i Represents the level of the hierarchy
	 */
	public void listFolders(File pathname, ArrayList<String> folderList, ArrayList<Integer> listHierarchy, int i) {
		Parser parser = new Parser();
		if(pathname.isDirectory()) {
			for(File file :pathname.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File current, String name) {
						return new File(current, name).isDirectory();
					}
				})) {
				folderList.add(parser.takeRootOut(file.getPath()));
				listHierarchy.add(i);
				listFolders(file, folderList, listHierarchy , i + 1);
				
			}
		}
	}
}