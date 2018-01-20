import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public Class Chatview extends JFrame implements ActionListerner{
private JPanel writingPanel = New JPanel
private JScrollPanel messagePanel = New JScrollPanel;
private JButton sendButton = New JButton("Send");
  
  public Chatview(Controller c, String name){
    super(name);
    
    
  }
  sendButton.addActionListener(this) \\eller ska det vara till controllern?
  
  public void actionPerformed(ActionEvent e){
    c.updatechatViewer }
}

