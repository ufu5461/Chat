import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class ClientView {
    JFrame frame;
    JPanel buttonPane, fieldsPanel;
    JLabel host, port;
    JTextField hostField, portField;
    JButton connectPub, connectPriv;
    ClientController c;

    public ClientView(ClientController c) {
    		this.c = c;
    	
    		frame = new JFrame("ChatClient");
        buttonPane = new JPanel();
        fieldsPanel = new JPanel();
        host = new JLabel("Host");
        port = new JLabel("Port");
        hostField = new JTextField("");
        portField = new JTextField("");
        connectPub = new JButton("Public Chat");
        connectPriv = new JButton("Private Chat");
        
        connectPub.addActionListener(c);
        connectPriv.addActionListener(c);

        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
        buttonPane.setLayout(new FlowLayout());

        fieldsPanel.add(host);
        fieldsPanel.add(hostField);
        fieldsPanel.add(port);
        fieldsPanel.add(portField);
        buttonPane.add(connectPub);
        buttonPane.add(connectPriv);
        frame.add(fieldsPanel, BorderLayout.PAGE_START);
        frame.add(buttonPane, BorderLayout.PAGE_END);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public String getHost() {
    		return hostField.getText();
    }
    
    public void connectionError() {
	    	JOptionPane.showMessageDialog(frame,
				    "Connection failed",
				    "Conenction error",
				    JOptionPane.ERROR_MESSAGE);
    }
    
    public int getPort() {
    		try {
    			int port = Integer.parseInt(portField.getText());
    			return port;
    		}catch(Exception e) {
    			JOptionPane.showMessageDialog(frame,
    				    "Port number needs to be an integer",
    				    "Input Error",
    				    JOptionPane.ERROR_MESSAGE);
    			return -1;
    			
    		}
    }
}
