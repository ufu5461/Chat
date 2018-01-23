import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {
	
	List<Chat> chats;
	List<Thread> threads;
	int portNumber;
	String myName;
	// Create new chats if client
	
	public ClientModel() {
		chats = new ArrayList<Chat>();
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
			    
			    // Set up communication with server
			    out.print(myName);
			    if(pub) {
			    		out.print("True");
			    }else {
			    		out.print("False");
			    }
			    
			    usr = new User("Server", in, out, echoSocket);
			    ch = new Chat("Chat with: " +  hostName);
			    chats.add(ch);
			    
			    
		} catch(Exception e){
			return false;
		}
		
		threads.add(new Thread(ch));
		threads.get(threads.size()-1).start();
		return true;


	}
}
