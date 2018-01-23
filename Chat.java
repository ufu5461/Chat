import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Runnable {
	private String chattName;
	private List<User> users; 
	private List<Message> messages;
	private View v;
	private Controller c; 
	private Parser p;
	
	public Chat(String name) {
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<Message>();
		c = new Controller(this);
		p = new Parser();
		chattName = name;
	}
	
	public void addUser(User us) {
		users.add(us);
	}
	
	public void removeUser(User us) {
		users.remove(us);
	}
	
	public void sendMessage(String msg) {
		for(int i = 0; i < users.size(); i++) {
			users.get(i).out.println(msg);
		}
	}
	
	public void recieveMessage(String msg) { // This is to return a message object
		System.out.println(msg);
		messages.add(p.parseMessage(msg));
		c.updateView();
		
	}

	@Override
	public void run() {
		// Content here can be executed at the same time as methods above can be called
		String input;
		while(true) {
			for(int i = 0; i < users.size(); i++) {
				try {
				if((input = users.get(i).in.readLine()) != null) {
					recieveMessage(input);
				}
				}catch(Exception e) {
					
				}
			}
		}
	}
	
		
}
