import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Chatt {
	private List<User> users; 
	private List<String> messages;
	
	public Chatt(String user, BufferedReader in, PrintWriter out) {
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<String>();
		addUser(user, in, out);
	}
	
	public void addUser(String user, BufferedReader in, PrintWriter out) {
		User tempuser = new User(user, in, out);
		users.add(tempuser);
	}
	
	public void removeUser(User user) {
		users.remove(users.indexOf(user));
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
	
	public List<String> getMessages(){
		return messages;
	}
	
}
