import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User {
	String name;
	BufferedReader in;
	PrintWriter out;
	Socket s;
	
	public User(String name, BufferedReader in, PrintWriter out, Socket s) {
		this.name = name;
		this.in = in;
		this.out = out;
		this.s = s;
	}
}
