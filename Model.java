import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

// Connection to other clients here, message send and receive
public class Model {
	
	// List of chats
	
	// Create new chats if client
	public boolean connectTo(String hostName, int portNumber) {
		try {
			    Socket echoSocket = new Socket(hostName, portNumber);
			    PrintWriter out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
			    BufferedReader in =
			        new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    //BufferedReader stdIn =
			     //   new BufferedReader(
			      //      new InputStreamReader(System.in));
		} catch(Exception e){
			return false;
		}
		return true;

	}
	// Create new chats if server
	
	// Send message
	
	// Receive message
	
	
}
