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
	
	/**
	 * Add a chat User to the user list
	 * 
	 * @param  us  	a User object
	 */
	public void addUser(User us) {
		users.add(us);
	}
	
	/**
	 * Remove a chat User from the user list
	 * 
	 * @param  us  	a User object
	 */
	public void removeUser(User us) {
		users.remove(us);
	}
	
	/**
	 * Send a message to all except one
	 * <p>
	 * The message will be sent to all except 
	 * specified users connected to the chat.
	 * 
	 * @param  msg  	a message string 
	 * @param  i 	index of user not to receive mesage
	 */
	public void sendMessage(String msg, int i) {
		for(int j = 0; j < users.size(); j++) {
			if(j != i) {
				users.get(j).out.println(msg);
			}
		}
	}
	
	/**
	 * Send a message to all
	 * <p>
	 * The message will be sent all users
	 * 
	 * @param  msg  	a message string 
	 */
	public void sendMessage(String msg) {
		for(int j = 0; j < users.size(); j++) {
			users.get(j).out.println(msg);
		}
	}
	
	/**
	 * Creates a message object from a received string
	 * <p>
	 * called when a message arrives
	 *  
	 * @param  msg  	a message string
	 * @param  i		the index of the user the message was received from
	 */
	private void recieveMessage(String msg, int i) { // This is to return a message object
		
		System.out.println(msg);
		messages.add(p.parseMessage(msg));
		sendMessage(msg, i);
		c.updateView();
		
	}
	
	
	/**
	 * The runnable component of the class
	 * <p>
	 * Listens for new input from other users
	 *  
	 * @param  msg  	a message string
	 */
	@Override
	public void run() {
		// Content here can be executed at the same time as methods above can be called
		String input;
		while(true) {
			for(int i = 0; i < users.size(); i++) {
				try {
				if((input = users.get(i).in.readLine()) != null) {
					recieveMessage(input, i);
				}
				}catch(Exception e) {
					
				}
			}
		}
	}
	
		
}
