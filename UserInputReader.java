import java.io.BufferedReader;

public class UserInputReader implements Runnable{
	private Chat c;
	private User u;
	private int userIndex;
	private BufferedReader in;

	public UserInputReader(Chat c, User u, int i) {
		this.c = c;
		this.u = u;
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
					c.recieveMessage(input, userIndex);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
