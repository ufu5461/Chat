
public class FileMessage extends Message {
	private boolean fileReply = false;
	private boolean fileRequest = false;
	private String filePort;
	private String fileName, fileSize;
	
	public FileMessage(Message msg) {
		super.usr = msg.sentBy();
	}

	public boolean getFileReply() {
		return fileReply;
	}
	public boolean isFileRequest() {
		return fileRequest;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public String getFilePort() {
		return filePort;
	}
	
	public void setFileReply(boolean reply) {
		fileReply = reply;
	}
	public void setFileRequest() {
		fileRequest = true;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public void setFilePort(String filePort) {
		this.filePort = filePort;
	}
}
