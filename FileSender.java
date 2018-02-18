import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;

public class FileSender implements Runnable{
	
	private String fileTransferPort = "8081";
	private PopUps pop = new PopUps();
	private String myName;
	private User target;
	private int fileSize;
	private String fileName;
	private Chat c;
	private enum RESPONSE  {
	    YES, NO, WAITING
	}
	private static RESPONSE resp = RESPONSE.WAITING;
	private int targetPort;
	ConnectionManager manager = new ConnectionManager("FileSender");
	
	public FileSender(User target, String myName, Chat c) {
		this.target = target;
		this.myName = myName;
		this.c = c;
	}
	
	public void transferResponse(boolean accept, String port) {
		System.out.println("FileSender: ToggleResponse");
		if(accept) {
			System.out.println("FileSender: Response Was Yes");
			resp = RESPONSE.YES;
			this.targetPort = Integer.parseInt(port);
		}else {
			resp = RESPONSE.NO;
		}
		if(resp == RESPONSE.YES) {
			System.out.println("This should indeed work!!!! :(");
		}
	}
	
	private boolean transferFile(File file) {
		System.out.println("FileSender: Sending File");
		return manager.sendFile(target, file);
		
	}
	
	private void sendFileRequest() {
		String XML;
		XML = "<filerequest sender=\"" + myName+  "\" size=\"" + fileSize + "\" name=\"" + fileName +"\"></filerequest>";
		c.sendMessageRaw(XML);
	}

	private boolean checkResponseYes() {
		if(resp == RESPONSE.YES) {
			System.out.println("FileSender: checked yes");
			return true;
		}
		return false;
	}
	
	private boolean checkResponseNo() {
		if(resp == RESPONSE.NO) {
			System.out.println("FileSender: checked no");
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		System.out.println("File Sender initialized");
		File fileToTransfer = null;
		try {
			fileToTransfer = pop.chooseFile();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = fileToTransfer.getName();
		fileSize = (int) fileToTransfer.length();
		sendFileRequest();
		System.out.println("FileSender: FileRequestSent");
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(checkResponseYes() || checkResponseNo()) {
				System.out.println("FileSender: Should leave loop now");
				break;
			} 
		}
		System.out.println("FileSender: Left Infinity Loop");
		if(resp == RESPONSE.YES) {
			boolean success = transferFile(fileToTransfer);
			System.out.println("FileSender: Response was yes");
			if(success) {
				System.out.println("FileSender: Transfer failed");
			}
		}else {
			System.out.println("FileSender: Transfer denied");
		}
	}

}
