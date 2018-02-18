import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
	private String name;
	private BufferedReader in;
	private PrintWriter out;
	private Socket s;
	private int port;
	private String publicAESkey;
	private String publicCaesarkey;
	private boolean allowed = false;
	private boolean pub = false;
	
	private static String CLOSE_MESSAGE = "<message><disconnect/></message>";
	private static String REQUEST_REPLY = "<request reply=\"no\"></request>";
	
	
	public User(String name, BufferedReader in, PrintWriter out, Socket s) {
		this.name = name;
		this.in = in;
		this.out = out;
		this.s = s;
	}
	
	public void sendRefused() {
		out.println(REQUEST_REPLY);
	}
	
	public String getName() {
		return name;
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public Socket getS() {
		return s;
	}
	
	public void allowUser() {
		allowed = true;
	}
	
	public boolean getAllowed() {
		return allowed;
	}
	
	public boolean getPub() {
		return pub;
	}
	
	public void setPub(boolean pub) {
		this.pub = pub;
	}
	
	/**
	 * Closes connection to the user
	 * 
	 * @return true or false depending on if operation succeded
	 */
	
	public void closeConnection() {
		out.println(CLOSE_MESSAGE);
	}
}
