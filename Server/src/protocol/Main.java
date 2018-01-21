package protocol;

import java.io.*;
import java.net.*;



public class Main {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Usage: java Server <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
			while (listening) {
				System.out.println("Novo Thread");
				new ServerThread(serverSocket.accept()).start();
				System.out.println("Thread criado");

			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
		}
	}
}
