import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

public class ConnectionManager {
	private String myName;
	private ServerSocket serverSocket;
	private int maxConnections;
	private int connections;
	private int fileSocket = 8081;
	
	public ConnectionManager(String myName) {
		this.myName = myName;
	}
	
	public void closeConnection(BufferedReader in, PrintWriter out, Socket s) {
		try {
			in.close();
			out.close();
			s.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public User connectTo(String hostName, int portNumber, boolean pub) {
		PrintWriter out;
		BufferedReader in;
		String name;
		Socket echoSocket;
		User usr;
		Chat ch;
		try {
			    echoSocket = new Socket(hostName, portNumber);
			    out =
			        new PrintWriter(echoSocket.getOutputStream(), true);
			    in =
			        new BufferedReader(
			            new InputStreamReader(echoSocket.getInputStream()));
			    
			    System.out.println("ConnectionManager: Managed to set up comunication\n");
			    
			    // Set up communication with server
			    Thread.sleep(1000);
			    if(pub) {
			    		String request = "<request sender=\"" 
			    + myName + "\" public=\"True\">I would like to join your public chat</request>";
			    		out.println(request);
			    		System.out.println("ConnectionManager: Transmitted True");
			    }else {
			    		String request = "<request sender=\"" 
			    + myName + "\" public=\"False\">I would like to chat in private</request>";
			    		out.println(request);
			    }
			    
			    usr = new User("Server", in, out, echoSocket);
			    System.out.println("ConnectionManager: Created user");
			    
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return usr;


	}

	public User incomming() {
	  	try {
	  		BufferedReader in;
	  		PrintWriter out;
	  		// Wait for new connection
	  		System.out.println("Waiting for connection");
			Socket clientSocket = serverSocket.accept();
	  		System.out.println("Incomming connection");
			
			// If more connections available create a user
			
			System.out.println("Trying to set up connection");
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(
				        new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Initialized out/in streams");
			// Assumes first input name next public bool
			User usr = new User("TemporaryUser", in, out, clientSocket);
			return usr;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  }

	public void setServerSocket(int portNumber) {
		try{
		      serverSocket = new ServerSocket(portNumber);
		    } catch (IOException e) {
		    	  e.printStackTrace();
		    }
	}

	public boolean incommingFile(String filePath, int size, String fileName) {
		byte[] bytes = new byte[size];
		try{	
			Socket s = serverSocket.accept();
			InputStream in = new BufferedInputStream(
				    new ProgressMonitorInputStream(
				        null,
				        "Reading " + fileName,
				        s.getInputStream()));
			FileOutputStream fileOut = 
				new FileOutputStream(filePath);
			BufferedOutputStream bu = new BufferedOutputStream(fileOut);
			bytes = in.readAllBytes();
			bu.write(bytes);
			bu.flush();
			System.out.println("ConnectionManager: file recieved");
			if(fileOut != null) fileOut.close();
			if(bu != null) bu.close();
			if(s != null) s.close();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean sendFile(User usr, File file) {
		System.out.println("ConnectionManager: entered send file method");
		Socket echoSocket;
		OutputStream os;
		FileInputStream fileIn;
		BufferedInputStream in;
		int fileSize = (int) file.length();
		byte[] bytes = new byte[fileSize];
		try {
			System.out.println("ConnectionManager; Sending a file this time");
			ProgressMonitor mon = new ProgressMonitor(null, "Sending file", null, 0, fileSize);
			mon.setMillisToPopup(0);
			echoSocket = new Socket(usr.getS().getInetAddress(), fileSocket);
		    os = echoSocket.getOutputStream();
		    fileIn = new FileInputStream(file.getAbsolutePath());
		    in = new BufferedInputStream(fileIn);
		    System.out.println("ConnectionManager: Transmitting file");
		    bytes = in.readAllBytes();
		    for(int i = 0; i < bytes.length; i++) {
		    		os.write(bytes[i]);
		    		mon.setProgress(i);
		    }
		    mon.setProgress(fileSize);
		    os.flush();
		    System.out.println("ConnectionManager: File transmitted");
		    if(fileIn != null) fileIn.close();
		    if(in != null) in.close();
		    if(os != null) os.close();
		    if(echoSocket != null) echoSocket.close();
		    return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
