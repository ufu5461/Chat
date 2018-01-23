import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Connection to other clients here, message send and receive
public class ServerModel {
	
	int portNumber;
	String myName;
	ServerSocket serverSocket;
	private static int maxConnections = 10;
	private static User[] users = new User[maxConnections];
	private static Thread[] chatsThread = new Thread[maxConnections];
	private static Chat[] chats = new Chat[maxConnections];
	
	public ServerModel() {
		portNumber = 8080;
		// Open new server connection
		try{
		      serverSocket = new ServerSocket(portNumber);
		    } catch (IOException e) {
		    	  e.printStackTrace();
		    }
		// Create a public chat in Thread[0]
		chats[0] = new Chat("Public: " + myName);
		chatsThread[0] = new Thread(chats[0]);
		chatsThread[0].start();
		System.out.println("Initializing server");
		incomming();
	}
	
	private String readName(BufferedReader in) {
		try {
			while(true) {
				String input = in.readLine();
				if(input != null) {
					System.out.println("Returned: " + input);
					return input;
				}
		}
		}catch(Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}
	
	private boolean wantPub(BufferedReader in) {
		try {
		while(true) {
			String input = in.readLine();
			System.out.println("Input: " + input);
			if(input != null) {
				if(input.equals("True")) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void incomming()  {
			int i = 0;
		  	while(true) {
		  		try {
		  			BufferedReader in;
		  			PrintWriter out;
		  			// Wait for new connection
		  			System.out.println("Waiting for connection");
					Socket clientSocket = serverSocket.accept();
		  			System.out.println("Incomming connection");
					
					// If more connections available create a user
					if(i < maxConnections) {
						System.out.println("Trying to set up connection");
						out = new PrintWriter(clientSocket.getOutputStream(), true);
						in = new BufferedReader(
							        new InputStreamReader(clientSocket.getInputStream()));
						System.out.println("Initialized out/in streams");
						// Assumes first input name next public bool
						String name = readName(in);
						System.out.println(name);
						users[i] = new User(name, in, out, clientSocket);
						if(wantPub(in)) {
							chats[0].addUser(users[i]); // Add to public chat
						}else { // Create private chat
							chats[i] = new Chat("Private: " + myName + " and " + name);
							chatsThread[i] = new Thread(chats[i]);
							chatsThread[i].start();
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  	}
		
	}
}
