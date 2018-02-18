import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

// Connection to other clients here, message send and receive
public class ServerModel {
	
	int portNumber;
	String myName = "Server";
	ServerSocket serverSocket;
	private static int maxConnections = 10;
	private static User[] users = new User[maxConnections];
	private static Thread[] chatsThread = new Thread[maxConnections];
	private static Chat[] chats = new Chat[maxConnections];
	private ConnectionManager manager;
	private PopUps pop = new PopUps();
	
	public ServerModel() {
		portNumber = 8080;
		manager = new ConnectionManager(myName);
		manager.setServerSocket(portNumber);
		// Create a public chat in Thread[0]
		chats[0] = new Chat("Public: " + myName);
		chatsThread[0] = new Thread(chats[0]);
		chatsThread[0].start();
		System.out.println("Initializing server");
		incomming();
	}
	
	private User allowConnection(BufferedReader in, PrintWriter out, Socket clientSocket) {
		try {
			String input = in.readLine();
			System.out.println(input);
			Parser p = new Parser();
			Message msg = new Message();
			msg = p.parseXML(input,msg);
			boolean response;
			String name;
			User usr;
			if(msg.isUnknown()) {
				String content = "Connection request from an unknown user";
				name = "Unknown";
				response = pop.connectionRequest(content, name);
				usr = new User(name,in,out,clientSocket);
				usr.setPub(true);
			}else {
				System.out.println("Message was known");
				name = msg.getSender();
				response = pop.connectionRequest(msg.getContent(), name);
				usr = new User(name, in, out, clientSocket);
				usr.setPub(msg.getPub());
			}
			if(response) {
				return usr;
			}else {
				usr.sendRefused();
				usr.closeConnection();
				manager.closeConnection(in, out, clientSocket);
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void incomming()  {
		int i = 0;
		  while(true) {
		  	User usr = manager.incomming();
		  	usr = allowConnection(usr.getIn(),usr.getOut(),usr.getS());
		  	if(usr != null) {
				users[i] = usr;
				if(usr.getPub()) {
					chats[0].addUser(users[i]); // Add to public chat
				}else { // Create private chat
					chats[i] = new Chat("Private: " + myName + " and " + usr.getName());
					chats[i].addUser(usr);
					chatsThread[i] = new Thread(chats[i]);
					chatsThread[i].start();
				}
			}		
	  	}
	}	
}
