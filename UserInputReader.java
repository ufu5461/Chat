import java.io.BufferedReader;

public class UserInputReader implements Runnable{
	private Chat c;
	private User u;
	private int userIndex;
	private BufferedReader in;
	private Parser p;

	public UserInputReader(Chat c, User u, int i) {
		this.c = c;
		this.u = u;
		p = new Parser();
		userIndex = i;
		in = u.getIn();
		
	}
	
	@Override
	public void run() {
		
		System.out.println("Created reader for user: " + userIndex);
		
		while(true) {
			try {
				String input = in.readLine();
				if(input != null) {
					Message msg = new Message();
					System.out.print(input);
					msg.setXML(input);
					msg.setSenderUser(u);
					msg = p.parseXML(input, msg);
					c.recieveMessage(msg, userIndex);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
