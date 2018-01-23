
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public Class Chatcontroller implements Actionlisterner{
private Chatviewer v;
private Chat ch;
  
public Chatcontroller(Chat ch){
  this.v = New Chatviewer(this)
  this.ch=ch;
  
               \\     public void updateView(){
               \\     List<Message> previousMessages = ch.getMessages
               \\     StringBuilder stringBuilder = new StringBuilder();
               \\     for(int i=0, i < previousMessages.size(), i++){
               \\     stringBuilder.append(previousMessages[i].getHtml())
               \\     };
               \\     String finalHtml = stringBuilder.toString();
               \\     v.publishChatView(finalHtml);} 
  
public void updateView(){
  List<Message> previousMessages = ch.getMessages();
    Message lastMessage = previousMessages.get(previousMessages.size() - 1); 
    v.publishChatview(lastmessage.getHtml());
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
