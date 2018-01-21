package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gui.Client;
import messages.ActionMessage;
import messages.AuthenticationMessage;
import messages.ListMessage;
import messages.RegisterMessage;

public class test {

	private static Client cliente;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String hostName;
		try(
				BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "config.txt"));
				){
			hostName = in.readLine();
			int portNumber = Integer.parseInt(in.readLine());
			cliente = new Client(hostName,portNumber, "teste","teste");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testRegister() throws Exception {
		RegisterMessage message;
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("Junit");
		fields.add("junit@gmail.com");
		fields.add("");
		fields.add("");
		message = new RegisterMessage("junit", "junit", "REGISTER", fields);
		String result = (String)message.send(cliente.getHostName(), cliente.getPortNumber());
		assertEquals(result, "ACK RECEIVED");
		
	}
	
	@Test
	public void testLogin() throws Exception {
		
		AuthenticationMessage message;
		message = new AuthenticationMessage(cliente.getUsername(), cliente.getPassword(), "LOGIN");
		String result = (String)message.send(cliente.getHostName(), cliente.getPortNumber());
		assertEquals(result, "ACK RECEIVED");
		
			
	}
	
	@Test
	public void testDownload() throws Exception {
		/* Precisa de ter o ficheiro login.png na pasta teste */
		ActionMessage message = new ActionMessage(cliente.getUsername(), cliente.getPassword(), "DOWNLOAD", cliente.getUsername() + File.separator + "login.png");
		message.send(cliente.getHostName(), cliente.getPortNumber());
		File teste = new File("login.png");
		assertTrue(teste.exists());
		teste.delete();
	}
	
	@Test
	public void testUpload() throws Exception {
		/* Precisa de ter o ficheiro aula3_ex2.c na pasta do diretorio local */
		File f = new File("aula3_ex2.c");
		ActionMessage message = new ActionMessage(cliente.getUsername(), cliente.getPassword(), "UPLOAD", cliente.getUsername()+File.separator+f.getName() , f);
		String response = (String) message.send(cliente.getHostName(), cliente.getPortNumber());
		assertEquals(response, "ACK RECEIVED");
	}
	
	@Test
	public void testDelete() throws Exception {
		ActionMessage message = new ActionMessage(cliente.getUsername(), cliente.getPassword(), "DELETE", cliente.getUsername()+File.separator+"aula3_ex2.c");
		message.send(cliente.getHostName(), cliente.getPortNumber());
		
		ListMessage message2 = new ListMessage(cliente.getUsername(), cliente.getPassword(), "LIST", cliente.getUsername());
		ListMessage response = (ListMessage) message2.send(cliente.getHostName(), cliente.getPortNumber());
		for(int i = 0; i < response.getList().size(); i++) {
			if(response.getList().get(i).equals(cliente.getUsername() + File.separator + "aula3_ex2.c")) assertFalse(true);
		}
	}
	
	@Test
	public void testCreateFolder() throws Exception {
		ActionMessage message2 = new ActionMessage(cliente.getUsername(), cliente.getPassword(), "CREATE FOLDER", cliente.getUsername() + File.separator + "novapastadeteste");
		String response = (String) message2.send(cliente.getHostName(), cliente.getPortNumber());
		assertEquals(response,"ACK RECEIVED");
		
		/*Faz o delete */ 
		ActionMessage message = new ActionMessage(cliente.getUsername(), cliente.getPassword(), "DELETE", cliente.getUsername()+File.separator+"novapastadeteste");
		message.send(cliente.getHostName(), cliente.getPortNumber());
	}
		
}


