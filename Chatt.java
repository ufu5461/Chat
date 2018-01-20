import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Chatt {
	List<String> users; 
	List<BufferedReader> in;
	List<PrintWriter> out;
	
	public Chatt(String user, BufferedReader in, PrintWriter out) {
		this.users = new ArrayList<String>();
		this.in = new ArrayList<BufferedReader>();
		this.out = new ArrayList<PrintWriter>();
		addUser(user, in, out);
	}
	
	public void addUser(String user, BufferedReader in, PrintWriter out) {
		this.users.add(user);
		this.in.add(in);
		this.out.add(out);
	}
}
