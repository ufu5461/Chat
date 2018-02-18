import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chat implements Runnable {
	private String chattName;
	private List<User> users; 
	private List<Message> messages;
	private List<FileReciever> transfers;
	private List<UserInputReader> readers;
	private View v;
	private FileSender sending;
	private String myHiddenASEkey;
	private String myPublicASEkey;
	private String myCasearKey;
	private Chatcontroller c; 
	private Parser p;
	private String myName = "Leo";
	private ConnectionManager manager;
	
	
	/**
	 * Constructor to Chat
	 * <p>
	 * Initializes arrays of users, messages.
	 * Creates a Controller with this chat object
	 * as parameter.
	 * Creates a Parser object to read input chat msg
	 * Set chat name
	 * 
	 * @param  name  A string with the name of the chat
	 */
	public Chat(String name) {
		this.users = new ArrayList<User>();
		this.messages = new ArrayList<Message>();
		this.readers = new ArrayList<UserInputReader>();
		//c = new Controller(this);
		p = new Parser(this);
		chattName = name;
		manager = new ConnectionManager(chattName);
	}
	
	/**
	 * Add a chat User to the user list
	 * 
	 * @param  us  	a User object
	 */
	public void addUser(User us) {
		// Function to check user
		UserInputReader read = new UserInputReader(this,us, users.size());
		new Thread(read).start();
		readers.add(read);
		users.add(us);
		sendFile(users.get(0));
	}
	
	/**
	 * Remove a chat User from the user list
	 * 
	 * @param  us  	a User object
	 */
	public void removeUser(User us) {
		users.remove(us);
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	/**
	 * Send a message to all except one
	 * <p>
	 * The message will be sent to all except 
	 * specified users connected to the chat.
	 * 
	 * @param  msg  	a message string 
	 * @param  i 	index of user not to receive message
	 */
	public void sendMessage(Message msg, int i) {
		String msgFormat = p.formatMessage(msg);
		for(int j = 0; j < users.size(); j++) {
			if(j != i) {
				users.get(j).getOut().println(msgFormat);
			}
		}
	}
	
	public void addMessage(Message msg) {
		messages.add(msg);
	}
	
	/**
	 * Send a message to all
	 * <p>
	 * The message will be sent all users
	 * 
	 * @param  msg  	a message string 
	 */
	public void sendMessage(Message msg) {
		String msgFormat = p.formatMessage(msg);
		for(int j = 0; j < users.size(); j++) {
			users.get(j).getOut().println(msgFormat);
		}
	}
	
	public void sendMessageRaw(String msg) {
		for(int j = 0; j < users.size(); j++) {
			users.get(j).getOut().println(msg);
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
	public void recieveMessage(Message msg, int i) { // This is to return a message object
		System.out.println("Incoming message: " + msg);
		messages.add(msg);
		System.out.println(msg.getHTML());
		
		// If User i disconnects
		if(msg instanceof DisconnectMessage) {
			User usr = msg.sentBy();
			manager.closeConnection(usr.getIn(),usr.getOut(),usr.getS());
			users.remove(usr);
		}
		if(msg instanceof FileMessage) {
			FileMessage fmsg = (FileMessage) msg;
			if(fmsg.isFileRequest()) {
				FileReciever ftrans = new FileReciever(fmsg.getContent(), 
						fmsg.getFileName(), fmsg.getFileSize(), 
						fmsg.getSender(), fmsg.sentBy());
				(new Thread(ftrans)).run();
			}
			else {
				try {
					sending.transferResponse(fmsg.getFileReply(), fmsg.getFilePort());
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			sendMessage(msg, i);
		}
		//c.updateView();
		
	}
	
	/**
	 * Closes connections
	 * <p>
	 * For each user in chat, call closeConnection method
	 */
	public void closeChat() {
		for(int i = 0; i < users.size(); i++) {
			users.get(i).closeConnection();
		}
	}
	
	public void sendFile(User usr) {
		sending = new FileSender(usr, myName, this);
		(new Thread(sending)).start();
		
		
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
		System.out.println("Chat thread running");
		for(int i = 0; i < users.size(); i++) {
			readers.add(new UserInputReader(this,users.get(i), i));
			(new Thread(readers.get(i))).start();
			
		}
		// PLACEHOLDER READING MY INPUT AND SENDING
		Scanner sc = new Scanner(System.in);
		while(true) {
			String msg = sc.nextLine();
			Message message = new Message();
			message.setContent(msg);
			message.setSender(myName);
			sendMessage(message);
		}
	}
	
		
}
