import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Parser{
	private Chat c;
	
	public Parser(Chat c) {
		this.c = c;
	}
	
	public Parser() {
		
	}
	
	public String formatMessage(Message msg) {
		if(msg.getXML() != null) {
			return msg.getXML();
		}
		Encrypter enc = new Encrypter();
		String sender = msg.getSender();
		String text = msg.getContent();
		String col = msg.getRgb();
		String encryption = msg.getEncryption();
		switch(encryption) {
			case "AES":
				text = enc.AES(text);
			case "caesar":
				text = enc.Caesar(text);
			default:
				text = text;
		}
		text = "<text color=\"" + col + "\">"+text+"</text>";
		text = "<message sender=\"" + sender + "\">" + text + "</message>";
		return text;
	}
	
	public Message parseXML(String XML, Message msg) {
		msg.setXML(XML);
		
		// Remove unsupported tags
		XML = XML.replaceAll("<fetstil>", "").
				replaceAll("</fetstil>","").
				replaceAll("<kursiv>","").
				replaceAll("</kursiv>","");
		
		// Check for disconnect tag <disconnect />
		
		// Check for color attribute <text color="#RRGGBB">MessageText</text>
		
		// Check for encryption <encrypted type="AES" key="publicKey">EncryptedText</encrypted>
		
		// File transfer <filerequest name="filename" size="filesize">Textaboutfile</filerequest>
		// Wait for file response <fileresponse answer="yes">
		
		// Check for tag that request connection.
		
		// 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(XML))); // Create parser doc from string
			NodeList message = doc.getElementsByTagName("message");
			NodeList filerequest = doc.getElementsByTagName("filerequest");
			NodeList request = doc.getElementsByTagName("request");
			NodeList fileresponse = doc.getElementsByTagName("fileresponse"); 
			if(message.getLength() != 0) {
				msg = parseMessage(message,msg);
			}else if(filerequest.getLength() != 0) {
				msg = parseFileRequest(filerequest, msg);
			}else if(request.getLength() != 0) {
				msg = parseRequest(request,msg);
			}else if(fileresponse.getLength() != 0) {
				msg = parseFileResponse(fileresponse,msg);
			}else {
				msg.setUnknown(true);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			msg.setContent("WARNING! MALFORMATED MESSAGE! " + XML);
			msg.setUnknown(true);
		}
		
		return msg;
	}
	
	private Message parseMessage(NodeList message, Message msg) {
		for(int i = 0; i<message.getLength();i++) {
			Node messageNode = message.item(i);
			if(messageNode.getNodeType()==Node.ELEMENT_NODE) {
				Element msgElem = (Element) messageNode;
				if(msgElem.getTagName().equals("disconnect")) {
					DisconnectMessage dmsg = new DisconnectMessage(msg);
					dmsg.setDisconnect();
					return dmsg;
				}
				msg.setSender(msgElem.getAttribute("sender"));	// Get sender
				
				NodeList children = msgElem.getChildNodes(); // List of all childrens to msg
				for(int j = 0; j<children.getLength();j++) {
					Node textNode = children.item(i);
					if(textNode.getNodeType()==Node.ELEMENT_NODE) {
						Element textElem = (Element) textNode;
						msg.setRgb(textElem.getAttribute("color")); // RGB Code #RRGGBB (Hexadecimal)
						msg.setContent(textElem.getTextContent()); // Set text in Message
					}
				}
			}
		}
		
		return msg;
	}

	private Message parseRequest(NodeList request, Message msg) {
		for(int i = 0; i<request.getLength();i++) {
			Node messageNode = request.item(i);
			if(messageNode.getNodeType()==Node.ELEMENT_NODE) {
				Element msgElem = (Element) messageNode;
				
				msg.setSender(msgElem.getAttribute("sender"));	// Get sender
				msg.setPub(msgElem.getAttribute("public")); // If public
				System.out.println(msg.getSender());
				System.out.println(msg.getPub());
				
				msg.setContent(msgElem.getTextContent()); // Get text
			}
		}
		
		return msg;
	}

	private Message parseFileRequest(NodeList filerequest, Message msg) {
		FileMessage fmsg = null;
		System.out.println("recieved file request");
		for(int i = 0; i<filerequest.getLength();i++) {
			Node messageNode = filerequest.item(i);
			if(messageNode.getNodeType()==Node.ELEMENT_NODE) {
				Element msgElem = (Element) messageNode;
				
				String sender = msgElem.getAttribute("sender");	// Get sender
				String fileName = msgElem.getAttribute("name"); // Get file name
				String fileSize = msgElem.getAttribute("size");	// Get file size
				String text = msgElem.getTextContent(); // Get text
				
				fmsg = new FileMessage(msg);
				fmsg.setFileRequest();
				fmsg.setFileName(fileName);
				fmsg.setFileSize(fileSize);
			}
		}
		return fmsg;
	}

	private Message parseFileResponse(NodeList fileresponse, Message msg) {
		System.out.println("Recieved file response");
		FileMessage fmsg = new FileMessage(msg);
		for(int i = 0; i<fileresponse.getLength();i++) {
			Node messageNode = fileresponse.item(i);
			if(messageNode.getNodeType()==Node.ELEMENT_NODE) {
				System.out.println("Parser: HAs element node");
				Element msgElem = (Element) messageNode;
				
				String response = msgElem.getAttribute("reply");
				System.out.println("Parser: File response was " + response);
				if(response.equals("yes")) {
					System.out.println("Parser: Response set to true");
					String port = msgElem.getAttribute("port");
					fmsg.setFilePort(port);
					fmsg.setFileReply(true);
				}else {
					System.out.println("Parser: Response set to false");
					fmsg.setFileReply(false);
				}
				fmsg.setContent(msgElem.getTextContent()); // Get text
			}
		}
		
		return fmsg;
	}
}
