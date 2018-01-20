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
	private static Chatt[] chats = new Chatt[maxConnections];
	
	public ServerModel() {
		// Open new server connection
		try{
		      serverSocket = new ServerSocket(portNumber);
		    } catch (IOException e) {
		      System.out.println(e);
		    }	
		// Create a public chat in Thread[0]
		chats[0] = new Chatt("Public: " + myName);
		chatsThread[0] = new Thread(chats[0]);
		chatsThread[0].start();
		incomming();
	}
	
	public void incomming() {
			int i = 0;
		  	while(true) {
		  		try {
		  			BufferedReader in;
		  			PrintWriter out;
		  			// Wait for new connection
					Socket clientSocket = serverSocket.accept();
					// If more conenctions available create a user
					if(i < maxConnections) {
						out = new PrintWriter(clientSocket.getOutputStream(), true);
						in = new BufferedReader(
							        new InputStreamReader(clientSocket.getInputStream()));
						// Assumes first input name next public bool
						String name = in.readLine();
						users[i] = new User(name, in, out, clientSocket);
						boolean pub = false;
						if(in.readLine().equals("True")) {
							chats[0].addUser(users[i]);
						}else {
							chats[i] = new Chatt("Private: " + myName + " and " + name);
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
