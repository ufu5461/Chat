
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

public class ChatController implements ActionListener, WindowListener{
	private ChatViewer v;
	private Chat ch;
	private Color msgColor = Color.BLACK;
	private String encrypt = "None";
	
	
	public ChatController(Chat ch, String name){
	  this.v = new ChatViewer(this, name);
	  this.ch = ch;
	  

	}
	  	 
	private void closeDown(){
		v.setVisible(false);
		v.dispose();
		ch.closeChat(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JButton source = (JButton) e.getSource();
			String message = source.getText();
			
			switch(message) {
				case "Send":
					String msg = v.getMessageText();
					ch.newMessageToSend(msg, msgColor);
					v.clearSendWindow();
					break;
					
				case "Encryption":
					setEncryption();
					break;
					
				case "Transfer File":
					ch.sendFile(chooseUser());
					break;

				case "Color":
					msgColor = v.getColor();
					break;

			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		if(v.exitView()) {
			closeDown();
		}
	}
	
	public void userConnected(User usr) {
		String html = "<p><b>" + usr.getName() + " connected to the chat</b></p>";
		v.append(html);
	}
	
	public void setEncryption() {
		// 1 Get user to encrypt to
		// 2 Select means of encryption
		// 3 Send a keyrequest
		// 4 wait for result for a max of one minute
		
		User usr = chooseUser();
		String encryption = v.getEncryption();
		usr.setSendEncryption(encryption);
		if(!encryption.equals("None")) {
			usr.sendKeyRequest();
			usr.setWaiting(true);
			// A random string to store which wait it is
			try{
				// Random String 
				int length = 32;
				String signs = "ABCDEFGHIJKLMNOPQRSTUVXYZ" + 
						"abcdefghijklmnopqrstuvxyz" +
						"1234567890";
				char[] chars = signs.toCharArray();
		        StringBuilder sb = new StringBuilder();
		        Random random = new SecureRandom();
		        for (int i = 0; i < length; i++) {
		            char c = chars[random.nextInt(chars.length)];
		            sb.append(c);
				}
		        final String waitingString = sb.toString();
		        usr.setWaitString(waitingString);
			
		        new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(60000);
						if(usr.getWaiting() && usr.getWaitingString().equals(waitingString)) {
							v.couldNotUse(usr, encryption);
							usr.setSendEncryption("None");
							usr.setWaiting(false);
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}
				
			}).start();
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// Create a thread that will wait for 60 seconds 
		// and then checking if the user did respond on
		// encryption request
		
		
		
	}
		
	/**
	 * A method that allows the user to choose a User
	 * <p>
	 * Gets a list of all users connected to a chat
	 * from the chat associated with this instance
	 * of ChatController. Opens a dialog via viewer
	 * 
	 * @return The chosen User object
	 */
	private User chooseUser() {
		List<User> users = ch.getUsers();
		int size = users.size();
		System.out.println(size);
		ArrayList<String> choices = new ArrayList<String>();
		
		for(int i = 0; i < size; i++) {
			String name = users.get(i).getName();
			System.out.println(name);
			
			// If two users have the same name, append a number at end
			// in order to be able to distinguish the two.
			int j = 0;
			String tempname = name;
			boolean iter = true;
			while(iter) {
				if(choices.contains(tempname)) {
					j++;
					tempname = name += j;
				}else {
					iter = false;
				}
			}
			choices.add(tempname);
		}
		String choice = v.getUserName((String[]) choices.toArray(new String[0]));
		
		return users.get(choices.indexOf(choice));
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void newMessage(String html) {
		v.append(html);
	}
	
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
 
}
