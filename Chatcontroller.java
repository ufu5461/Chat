
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public Class Chatcontroller implements Actionlistern  er{
private Chatviewer v;
private Chat ch;
  
public Chatcontroller(Chat ch){
  this.v = New Chatviewer(this)
  this.ch=ch;
  
public void updateView(){
  List<Message> previousMessages = ch.getMessages
}
            
@Override
 public void actionPerformed(ActionEvent e){
   String command = e.getActionCommand();
   if(command == "Send"){
   
    ch.addmessage(v.exportMessage, v.exportColour, String "Me")
    this.updateView();
    v.clearSendwindow();
   else{
   v.exitView;
   }
   
  public void closeDown(){
  v.setVisible(false);
  v.dispose();
  ch.closeChat(); 
    }
   
   
 
 
}
}


}
