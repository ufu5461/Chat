import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chatt implements Runnable {
	private String chattName;
	private List<User> users; 
	private List<Message> messages;
	
	public Chatt(String name) {
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<Message>();
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
	
	public Message recieveMessage(String msg) {
		return new Message(msg);
	}

	@Override
	public void run() {
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
