import java.io.BufferedReader;
import java.io.IOException;
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
	
	public ClientModel(String name) {
		chats = new ArrayList<Chat>();
		threads = new ArrayList<Thread>();
		myName = name;
		manager = new ConnectionManager(myName);
	}
	
	public void shutDown() {
		for(int i = 0; i < threads.size(); i++) {
			chats.get(i).closeChat();
		}
	}
	
	private boolean allowedAcces(User usr) {
		try {
			String input = usr.getIn().readLine();
			Parser p = new Parser();
			Message msg = new Message();
			msg = p.parseXML(input,msg);
			if(msg.getReply().equals("no")) {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public boolean connectTo(String hostName, int portNumber, boolean pub) {
		PrintWriter out;
		BufferedReader in;
		String name;
		Socket echoSocket;
		User usr;
		Chat ch;
		
		usr = manager.connectTo(hostName, portNumber, pub);
		
		boolean response;
		
		if(usr != null) {
			//response = allowedAcces(usr);
			response = true;
			ch = new Chat("Chat with: " + hostName);
			ch.addUser(usr);
			chats.add(ch);
			if(!response) {
				ch.refusedAccess();
			}
			
		}else {
			return false;
		}
		
		ch.execute();
		System.out.println("ClientModel: Started client thread");
		return true;
		
		

	}
}
