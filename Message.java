/*
 * <message sender="Name">
 * <text>
 * This is the text of this message
 * </text>
 * </message>
 * 
 * 
 */

public class Message {
	protected String content;
	protected String XML;
	protected String sender;
	protected String rgb = "PLACEHOLDER";
	protected String encrypted = "no";
	protected boolean unknown = false;
	protected boolean pub = false;
	protected User usr;
	
	public Message() {
		
	}
	
	private String toHTML() {
		String HTML = "<b>" + sender + ":<\b> <p>"+content+"</p>";
		return HTML;
	}
	// Getters
	public String getHTML() {
		return sender + ": " + content;
	}
	public String getXML() {
		return XML;
	}
	public String getContent() {
		return content;
	}
	public String getEncryption() {
		return encrypted;
	}
	public String getSender() {
		return sender;
	}
	public String getRgb() {
		return rgb;
	}
	public boolean isUnknown() {
		return unknown;
	}
	public boolean getPub() {
		return pub;
	}
	public User sentBy() {
		return usr;
	}
	
	// Setters
	public void setContent(String content) {
		this.content = content;
	}
	public void setXML(String XML) {
		this.XML = XML;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	public void setEncryption(String encrypted) {
		this.encrypted = encrypted;
	}
	public void setUnknown(boolean unknown) {
		this.unknown = unknown;
	}
	public void setPub(String publ) {
		if(publ.equals("True")) {
			pub = true;
		}
	}
	public void setSenderUser(User usr) {
		this.usr = usr;
	}

}
