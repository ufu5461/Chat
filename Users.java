import java.io.BufferedReader;
import java.io.PrintWriter;

public class User {
	String name;
	BufferedReader in;
	PrintWriter out;
	
	public User(String name, BufferedReader in, PrintWriter out) {
		this.name = name;
		this.in = in;
		this.out = out;
	}
}
