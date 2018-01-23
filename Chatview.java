import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public Class Chatview extends JFrame implements WindowListener{
private Controller c;
private JTextField messageBox = New JTextField;
private JScrollPane historyScroll = New JScrollPane;
private JTextArea messagePanel = New JTextArea;
private JButton sendButton = New JButton("Send");
private JButton leaveButton = New JButton("Leave chat");
  
public Chatview(Controller con, String name){
    super(name);
    this.c = con;
    this.addWindeowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
      c.closeDown()}};
    this.setSize(500, 500);  
    sendButton.addActionListener(c);
    leaveButton.addActionListener(c)  ;         
    this.add(historyscroll, BorderLayout.CENTER);
    this.add(messagePanel, BorderLayout.BOTTOM);    
    this.add(sendButton, BorderLayout.RIGHT);    
    this.add(leavButton, BorderLayout.RIGHT);    
    historyScroll.add(messagePanel); 
    this.pack()
    setVisible(true);
                          
    
  }
  
public void updateChatview(List<Message> mlist){}
  
public void clearSendwindow(){}
  
public void leaveChat(){}
  
public void exportMessage(){}
  

}

