package protocol;

import java.io.Serializable;
import java.net.*;

import messages.Message;

import java.io.*;

public class ServerThread extends Thread implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Socket socket = null;

	public ServerThread(Socket socket) {
		super("ServerThread");
		this.socket = socket;
	}
	
	public void run() {
		// 1 thread por pedido !!! (Se for 1 thread por cliente falta o while)
		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Message inputLine, outputLine;
			ServerProtocol sp = new ServerProtocol();
			outputLine = sp.processInput(null);
			out.writeObject(outputLine); // Send Greeting
			
			inputLine = (Message) in.readObject();
			
			System.out.println("Client > " + inputLine+"");
			outputLine = sp.processInput(inputLine);
			out.writeObject(outputLine);
			System.out.println("Mandei Resposta!");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
