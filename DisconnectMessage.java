
public class DisconnectMessage extends Message {
	private boolean disconnect = false;

	public DisconnectMessage(Message msg) {
		super.usr = msg.sentBy();
	}
	
	public boolean isDisconnect() {
		return disconnect;
	}
	
	public void setDisconnect() {
		disconnect = true;
	}

}
