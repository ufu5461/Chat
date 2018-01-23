
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public Class Chatcontroller implements Actionlisterner{
private Chatviewer v;
private Chat ch;

  
public Chatcontroller(Chat ch){
  this.v = New Chatviewer(this)
  this.ch=ch;
  
 public void updatechatViewer(){ \\printa ut ny meddelandehistorik
  List<Message> list = ch.getMessages();
            
                                
                               }

 public void actionPerformed(ActionEvent e){
    ch.addmessage(v. det som står i meddelanderutan); 
    v. cleara meddelanderutan;
    updatechatviewer();
 
 
}
}


}
