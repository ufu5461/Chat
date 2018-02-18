import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PopUps {
	
	private static File selectedDest;
	private static File selectedFile;
	private static int resultDest;
	private static int resultFile;
	private static boolean responseChoiseRequest;

	public boolean connectionRequest(String content, String sender) {
		int resp = JOptionPane.showConfirmDialog(null, "Connection request from an unknown user", "Connection request", JOptionPane.YES_NO_OPTION);
		if(resp == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	public boolean documentRequest(String sender, String name, String size) throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				int response = JOptionPane.showConfirmDialog(null, sender + 
						" has requested to transfer " + name + " of size " + size + "to you."  
						, "File transfer request", JOptionPane.YES_NO_OPTION );
				if(response == JOptionPane.YES_OPTION) {
					responseChoiseRequest = true;
				}
				else {
					responseChoiseRequest = false;
				}
			}
		});
		return responseChoiseRequest;
	}
	
	public File chooseDestination() throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                String folder = System.getProperty("user.dir");
                JFileChooser fc = new JFileChooser(folder);
                resultDest = fc.showOpenDialog(null);
                if(resultDest == JFileChooser.APPROVE_OPTION) {
        				selectedDest = fc.getSelectedFile();
        			}
            }
        });
		return selectedDest;
	}
	
	public File chooseFile() throws InvocationTargetException, InterruptedException {
		EventQueue.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                String folder = System.getProperty("user.dir");
                JFileChooser fc = new JFileChooser(folder);
                resultFile = fc.showOpenDialog(null);
                System.out.println(resultFile);
                if(resultFile == JFileChooser.APPROVE_OPTION) {
                		System.out.println("Was correct option");
        				selectedFile = fc.getSelectedFile();
        				System.out.println(selectedFile);
        			}
            }
        });
		System.out.println("Left runnable loop");
		return selectedFile;
	}
}
