import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
	
	List<Chat> chats;
	List<Thread> threads;
	String myName = "LeoTesting";
	// Create new chats if client
	
	public ClientModel() {
		chats = new ArrayList<Chat>();
		threads = new ArrayList<Thread>();
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
		try {
			    echoSocket = new Socket(hostName, portNumber);
			    out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
			    in =
			        new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    
			    System.out.println("Managed to set up comunication\n");
			    
			    // Set up communication with server
			    Thread.sleep(1000);
			    out.println(myName);
			    System.out.println("Transmitted name");
			    Thread.sleep(1000);
			    if(pub) {
			    		out.println("True");
			    		System.out.println("Transmitted True");
			    }else {
			    		out.println("False");
			    }
			    
			    usr = new User("Server", in, out, echoSocket);
			    ch = new Chat("Chat with: " +  hostName);
			    ch.addUser(usr);
			    chats.add(ch);
			    System.out.println("A chat now exists");
			    
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		threads.add(new Thread(ch));
		threads.get(threads.size()-1).start();
		System.out.println("Started client thread");
		return true;


	}
}
