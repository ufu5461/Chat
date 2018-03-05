import java.awt.Color;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingWorker;

public class Chat extends SwingWorker {
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
	private ChatController c; 
	private Parser p;
	private String myName;
	private boolean closed = false;
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
		c = new ChatController(this, name);
		p = new Parser(this);
		chattName = name;
		myName = name;
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
		c.userConnected(us);
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
	
	public List<User> getUsers(){
		return users;
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
		
		for(int j = 0; j < users.size(); j++) {
			if(j != i) {
				String msgFormat = p.formatMessage(msg, users.get(j));
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
		for(int j = 0; j < users.size(); j++) {
			String msgFormat = p.formatMessage(msg, users.get(j));
			System.out.println(users.get(j).getEncryption());
			users.get(j).getOut().println(msgFormat);
		}
	}
	
	public void sendMessageRaw(String msg) {
		for(int j = 0; j < users.size(); j++) {
			users.get(j).getOut().println(msg);
		}
	}
	
	/**
	 * Takes a msg object and determines what to do 
	 * with it
	 * <p>
	 * If it is a DisconnectMessage, user is disconnected properly
	 * <p>
	 * If it is a File message it is passed on to the fileMsg handler.
	 *  
	 * @param  msg  	a Message object
	 * @param  i		the index of the user the message was received from
	 */
	public void recieveMessage(Message msg, int i) { 
		System.out.println("\nIncoming message: " + msg);
		messages.add(msg);
		
		// If User i disconnects
		if(msg instanceof DisconnectMessage) {
			discMessage((DisconnectMessage) msg);
			
		}else if(msg instanceof FileMessage) {
			fileMsg((FileMessage) msg);
		}else if(msg instanceof KeyRequestMessage) {
			keyMsg((KeyRequestMessage) msg);
		}else if(msg instanceof KeyResponseMessage) {
			keyRespMsg((KeyResponseMessage) msg);
		}else {
			sendMessage(msg, i);
			c.newMessage(msg.getHTML());
		}
		
		
	}
	
	public void refusedAccess() {
		String message = "<br><b>Acces to chat refused</b><br>";
		c.newMessage(message);
		closeChat();
	}
	
	private void discMessage(DisconnectMessage msg) {
		User usr = msg.sentBy();
		manager.closeConnection(usr.getIn(),usr.getOut(),usr.getS());
		users.remove(usr);
		sendMessage(msg);
		c.newMessage(msg.getDiscHTML());
	}
	
	/**
	 * Function that takes a file message and initializes a 
	 * thread running a file receiver if it is a file request
	 * or starting to send file if it is a positive
	 * response
	 * 
	 * @param msg
	 */
	private void fileMsg(FileMessage msg) {
		FileMessage fmsg = (FileMessage) msg;
		if(fmsg.isFileRequest()) {
			FileReciever ftrans = new FileReciever(fmsg.getContent(), 
					fmsg.getFileName(), fmsg.getFileSize(), 
					fmsg.getSender(), fmsg.sentBy(), fmsg.getEncryption());
			(new Thread(ftrans)).run();
		}
		else {
			try {
				sending.transferResponse(fmsg.getFileReply(), fmsg.getFilePort());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void keyRespMsg(KeyResponseMessage msg) {
		User sender = msg.sentBy();
		String type = msg.getType();
		String key = msg.getKey();
		if(sender.getWaiting() && sender.getSendWith().equals(type)) {
			sender.setSendEncryption(type);
			sender.setWaiting(false);
			sender.setKey(key);
		}else {
			System.out.println("Chat: keyRespMsg ERROR");
		}
	}
	
	private void keyMsg(KeyRequestMessage msg) {
		User sender = msg.sentBy();
		String type = msg.getType();
		System.out.println("CHAT: request type: " + type);
		if(type.equals("AES") || type.equals("Caesar")) {
			System.out.println("CHAT: Key request recieved");
			sender.setEncryption(type);
			System.out.println("CHAT: Encryption Set");
			sender.sendKeyResponse();
		}
	}
	
	
	/**
	 * Closes connections
	 * <p>
	 * For each user in chat, call closeConnection method
	 */
	public void closeChat() {
		closed = true;
		for(int i = 0; i < users.size(); i++) {
			users.get(i).closeConnection();
		}
	}
	
	/**
	 * Function that initializes a thread 
	 * to manage the sending of a file
	 * 
	 * @param usr User to recieve file
	 */
	public void sendFile(User usr) {
		sending = new FileSender(usr, myName, this);
		(new Thread(sending)).start();
		
		
	}
	
	/**
	 * Creates a new Message object
	 * <p>
	 * Given the parameters this function creates a new
	 * Message object with this programme user as sender and
	 * the given content, color and encryption.
	 * 
	 * @param	msg	A string with the message text
	 * @param	c	A Color object
	 * @param	encryption A string with the encryption name
	 */
	public void newMessageToSend(String msg, Color col) {
		Message message = new Message();
		message.setContent(msg);
		message.setSender(myName);
		int red = col.getRed();
		int green = col.getGreen();
		int blue = col.getBlue();
		String color = "rgb(" + Integer.toString(red) + "," + Integer.toString(green) + "," + Integer.toString(blue) + ")";
		message.setRgb(color);
		sendMessage(message);
		message.setSender("You");
		c.newMessage(message.getHTML());
	}
	
	/**
	 * The runnable component of the class
	 * <p>
	 * Listens for new input from other users
	 *  
	 * @param  msg  	a message string
	 */
	@Override
	protected Object doInBackground() throws Exception {
		System.out.println("Chat thread running");
		for(int i = 0; i < users.size(); i++) {
			readers.add(new UserInputReader(this,users.get(i), i));
			(new Thread(readers.get(i))).start();
		}
		
		// PLACEHOLDER READING MY INPUT AND SENDING
		//Scanner sc = new Scanner(System.in);
		//while(true) {
		//	String msg = sc.nextLine();
		//	Message message = new Message();
		//	message.setContent(msg);
		//	message.setSender(myName);
		//	sendMessage(message);
		//}
		return null;
	}
	
		
}
