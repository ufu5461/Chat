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
private JEditorPanel messagePanel = New JEditorPane;
private JTextField colourField = New JTextField  
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
    this.add(messageBox, BorderLayout.BOTTOM);    
    this.add(sendButton, BorderLayout.RIGHT);    
    this.add(leavButton, BorderLayout.RIGHT);
    this.add(colourField, BorderLayout.BOTTOM); 
    historyScroll.add(messagePanel);
    historyScroll.setEditable(False)
    historyScroll.setColumnHeader("Previous messages with" + name);
    this.pack();
    this.setVisible(true);
    messagPanel.setContentType(html);
  }
  
public void publishChatview(String htmlMessages){
  messagePanel.setText(htmlMessages);
  this.pack();}
                            
                            
                            
public void clearSendwindow(){
  messageBox.setText(null); }
  
  
public void exitView(){
   int n = JOptionPane.showConfirmDialog(
null, "Are you sure you want to disconnect?",JOptionPane.YES_NO_OPTION);
  if(true){
            c.closeDown();
        }
        else { break;}
  
public String exportMessage(){
  return messageBox.getText();
}
  public String exportColour(){
  return colourBox.getText()}
}

