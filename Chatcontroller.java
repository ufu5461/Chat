
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public Class Chatcontroller implements Actionlistern  er{
private Chatviewer v;
private Chat ch;

  
public Chatcontroller(Chat ch){
  this.v = New Chatviewer(this)
  this.ch=ch;
  
 public void updatechatViewer(){ \\printa ut ny meddelandehistorik
  List<Message> list = ch.getMessages();
            
                                
                               }
@Override
 public void actionPerformed(ActionEvent e){
   String command = e.getActionCommand();
   if(command =="Send"){
   
   ch.addmessage(v. det som står i meddelanderutan); 
    v. cleara meddelanderutan;
    updatechatviewer();
   }
   else{
   this.closeDown();
   ch.closeChat();
   }
   
     
     
 public void closeDown(){
  v.setVisible(false);
  v.dispose();
  ch.closeChat(); 
    }
   
   
 
 
}
}


}
