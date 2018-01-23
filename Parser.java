import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser{
	
	public Parser() {
		
	}
	
	public String parseXML(String XML) {
		
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
		return null;
	}
}
