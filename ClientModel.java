import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
	
	List<Chat> chats;
	List<Thread> threads;
	String myName = "Client";
	ConnectionManager manager;
	// Create new chats if client
	
	public ClientModel() {
		chats = new ArrayList<Chat>();
		threads = new ArrayList<Thread>();
		manager = new ConnectionManager(myName);
	}
	
	public void shutDown() {
		for(int i = 0; i < threads.size(); i++) {
			chats.get(i).closeChat();
		}
	}
	
	public boolean connectTo(String hostName, int portNumber, boolean pub) {
		PrintWriter out;
		BufferedReader in;
		String name;
		Socket echoSocket;
		User usr;
		Chat ch;
		
		usr = manager.connectTo(hostName, portNumber, pub);
		
		if(usr != null) {
			ch = new Chat("Chat with: " + hostName);
			ch.addUser(usr);
			chats.add(ch);
			System.out.println("ClientModel: Created a chat");
			
		}else {
			System.out.println("ClientModel: Could not connect to " 
		+ hostName + "at port " + portNumber);
			return false;
		}
		
		threads.add(new Thread(ch));
		threads.get(threads.size()-1).start();
		System.out.println("ClientModel: Started client thread");
		return true;
		
		

	}
}
