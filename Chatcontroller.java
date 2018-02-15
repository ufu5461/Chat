
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Chatcontroller implements ActionListener{
	private Chatviewer v;
	private Chat ch;
	  
	public Chatcontroller(Chat ch){
	  this.v = new Chatviewer(this);
	  this.ch=ch;
	  
	               //     public void updateView(){
	               //     List<Message> previousMessages = ch.getMessages
	               //     StringBuilder stringBuilder = new StringBuilder();
	               //     for(int i=0, i < previousMessages.size(), i++){
	               //     stringBuilder.append(previousMessages[i].getHtml())
	               //     };
	               //     String finalHtml = stringBuilder.toString();
	               //     v.publishChatView(finalHtml);} 
	}
	  
	public void updateView(){
	  List<Message> previousMessages = ch.getMessages();
	    Message lastMessage = previousMessages.get(previousMessages.size() - 1); 
	    v.publishChatview(lastMessage.getHTML());
	}
	  
	@Override
	 public void actionPerformed(ActionEvent e){
	   String command = e.getActionCommand();
	   if(command == "Send") {
		   try {
			   ch.addMessage(v.exportMessage, v.exportColour, "Me");
			   this.updateView();
			   v.clearSendWindow();
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
	   else {
		   v.exitView();
	   }
	}
	   
	  public void closeDown(){
		  v.setVisible(false);
		  v.dispose();
		  ch.closeChat(); 
	    }
 
}
