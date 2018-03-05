import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController implements ActionListener {
	private ClientView v;
	private ClientModel m;
	
	public ClientController(String name) {
		v = new ClientView(this);
		m = new ClientModel(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		String host = v.getHost();
		int port = v.getPort();
		if(port == -1) {
			return;
		}
		
		if("Public Chat".equals(command)) {
			if(!m.connectTo(host, port, true)) {
				v.connectionError();
			};
		}
		else if("Private Chat".equals(command)) {
			if(!m.connectTo(host, port, false)) {
				v.connectionError();
			}
		}
		
	}
}
