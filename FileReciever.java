import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

public class FileReciever implements Runnable{
	
	private int fileTransferPort = 8081;
	private PopUps pop = new PopUps();
	private String desc;
	private String name;
	private String size; 
	private String sender; 
	private User usr;
	private ConnectionManager manager = new ConnectionManager("FileRec");
	
	public FileReciever(String desc, String name, String size, String sender, User usr) {
		this.desc = desc;
		this.name = name;
		this.size = size;
		this.usr = usr;
	}
	
	public File transferRequest() {
		boolean wait = true;
		boolean reply = false;
		String XML = null;
		File destination = null;
		while(wait) {
			boolean response = false;
			try {
				response = pop.documentRequest(sender, name, size);
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(response) {
				try {
					destination = pop.chooseDestination();
				} catch (InvocationTargetException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(destination != null) {
					XML = "<fileresponse reply=\"yes\" port=\"" + fileTransferPort + "\"></fileresponse>";
					reply = true;
					wait = false;
				}	
			}
			else {
				XML = "<fileresponse reply=\"no\"></fileresponse>";
				reply= false;
				wait = false;
			}
		}
		usr.getOut().println(XML);
		return destination;
	}

	@Override
	public void run() {
		File target = transferRequest();
		if(target != null) {
			manager.setServerSocket(fileTransferPort);
			boolean success = manager.incommingFile(target.getAbsolutePath(),Integer.parseInt(size), name);
			if(success) {
				System.out.println("FileReciever: Recieved file");
			}
			else {
				System.out.println("FileReiever: Failed to recieve file");
			}
		}
		
	}
	
}
