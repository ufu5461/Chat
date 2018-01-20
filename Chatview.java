import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public Class Chatview implements ActionListener{
private JPanel writingPanel = New JPanel
private JFrame chatWindow = New JFrame
private JScrollPanel messagePanel = New JScrollPanel
private JButton sendButton = New JButton("Send")
  
  public Chatview(Controller c){
    
    
  }
  sendButton.addActionListener(this)
  
  public void actionPerformed(ActionEvent e){
    c.updatechatViewer }
}

