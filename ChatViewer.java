import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ChatViewer extends JFrame{
	private ChatController c; // Controller holds all listeners
	private JTextField messageBox = new JTextField();
	private JScrollPane historyScroll = new JScrollPane();
	private JEditorPane messagePane = new JEditorPane();
	private JButton sendButton = new JButton("Send");
	private JButton encryptButton = new JButton("Encryption");
	private JButton sendFile = new JButton("Transfer File");
	private JButton colorButton = new JButton("Color");
	private String currentHtml;
	  
	public ChatViewer(ChatController c, String name){
	    this.setTitle(name);
	    this.c = c;
	    
	    this.setPreferredSize(new Dimension(500, 500));
	    this.addWindowListener(c);
	    sendButton.addActionListener(c);
	    encryptButton.addActionListener(c);
	    colorButton.addActionListener(c);
	    sendFile.addActionListener(c);
	    
	    messagePane.setPreferredSize(new Dimension(400,300));
	    messagePane.setMinimumSize(new Dimension(400,300));
	    historyScroll.setPreferredSize(new Dimension(400,300));
	    historyScroll.setMinimumSize(new Dimension(400,300));
	    messageBox.setPreferredSize(new Dimension(400,200));
	    messageBox.setMinimumSize(new Dimension(400,200));
	    
	    historyScroll.setViewportView(messagePane);
	    
	    //Panel to hold components
	    GridBagLayout layout = new GridBagLayout();
	    this.getContentPane().setLayout(layout);
	    GridBagConstraints gbc = new GridBagConstraints();
	    HTMLEditorKit kit = new HTMLEditorKit();
	    messagePane.setEditable(true);
	    messagePane.setEditorKit(kit);
	    messagePane.setDocument(kit.createDefaultDocument());
	    messagePane.setContentType("text/html");
	    messagePane.setText("<html><body><p><b>Welcome to this chat window</b></p></body></html>");
	    	    
	    historyScroll.setVerticalScrollBarPolicy(
	    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    //Constraints on components
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = GridBagConstraints.RELATIVE;
	    gbc.gridheight = GridBagConstraints.RELATIVE;
	    this.getContentPane().add(historyScroll,gbc);
	    
	    gbc.gridx = GridBagConstraints.RELATIVE;
	    gbc.gridy = 0;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    this.getContentPane().add(sendFile,gbc);
	    
	    gbc.gridx = GridBagConstraints.RELATIVE;
	    gbc.gridy = 1;
	    this.getContentPane().add(encryptButton,gbc);
	    
	    gbc.gridx = GridBagConstraints.RELATIVE;
	    gbc.gridy = 2;
	    this.getContentPane().add(colorButton,gbc);
	    
	    
	    gbc.gridx = 0;
	    gbc.gridy = GridBagConstraints.RELATIVE;
	    gbc.gridheight = GridBagConstraints.REMAINDER;
	    gbc.gridwidth = GridBagConstraints.RELATIVE;
	    this.getContentPane().add(messageBox,gbc);
	    
	    gbc.gridx = GridBagConstraints.RELATIVE;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    this.getContentPane().add(sendButton,gbc);
	    
	   
	    this.pack();
	    this.setVisible(true);
	    
	  }
	
	/**
	 * Method to add to textfield
	 * <p>
	 * Add the supplied message to the end of the
	 * JEditorPane.
	 * 
	 * @param	msg	A string with the new message
	 */
	public void append(String msg) {
		try {
			HTMLDocument doc = (HTMLDocument) messagePane.getDocument();
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()),msg);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		messagePane.repaint();
		historyScroll.repaint();
		this.revalidate();
		JScrollBar vert = historyScroll.getVerticalScrollBar();
		vert.setValue(vert.getMaximum());
	}
	  
	/**
	 * Method that returns the content of the textbox
	 * 
	 * @return String containing the text of the message box
	 */
	public String getMessageText() {
		return messageBox.getText();
	}

	/**
	 * Clears the textfield where input is entered
	 */
	public void clearSendWindow(){
		messageBox.setText(null); 
	}
	
	public void couldNotUse(User usr, String encr) {
		JOptionPane.showMessageDialog(this, "The user: " + usr.getName() 
		+ " could not use the " + encr + " encryption protocoll.");
	}
	
	/**
	 * Method to choose a color
	 * <p>
	 * Opens a JColorChooser dialog. 
	 * 
	 * @return	newColor The choosen color
	 */
	public Color getColor() {
		Color newColor = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
		return newColor;
	}
	
	/**
	 * Method to choose encryption
	 * <p>
	 * Opens dialog where user may choose from list of
	 * available encryption algorithms
	 * 
	 * @return	input 	The choosen method of encryption
	 */
	public String getEncryption() {
		String[] choices = { "Caesar", "AES", "None"};
	    String input = (String) JOptionPane.showInputDialog(this, "Choose your prefered encryption:",
	        "Encryption", JOptionPane.QUESTION_MESSAGE, null,
	        choices, // Array of choices
	        choices[1]); // Initial choice
		return input;
	}
	
	/**
	 * Method to choose a user
	 * <p>
	 * Supplied by a list of usernames it creates an Input Dialog 
	 * allowing the user to choose
	 * 
	 * @param	choices	A string array with user names
	 * @return	input 	The choosen username
	 */
	public String getUserName(String[] choices) {
		String input = (String) JOptionPane.showInputDialog(null, "User to transfer file to:",
			   "User", JOptionPane.QUESTION_MESSAGE, null,
		       choices, // Array of choices
			   choices[0]); // Initial choice
		return input;
	}
	  
	/**
	 * Exit prompt
	 * <p>
	 * Opens a prompt asking if user is sure it wants to
	 * disconnect
	 * 
	 * @return	boolean true if disconnect, false if not
	 */  
	public boolean exitView(){
	   int n = JOptionPane.showConfirmDialog(
			   this, "Exit", "Are you sure you want to disconnect?",JOptionPane.YES_NO_OPTION);
	   if(n == JOptionPane.YES_OPTION){
		   return true;
	   }
	   
	   return false;
	}
	  
}

