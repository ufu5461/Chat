

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/** Class displaying basic material from Model. Implements Scroll bars 
 * for material of larger sizes. 
 * 
 * @see JScrollPane
 * @author Adam
 */
public class View extends JScrollPane {
	private Model m;
	
        /** Constructor of the View class. Associates a Model with a screen view
         * 
         * 
         * @param m Model from which to display web page
         */
	public View(Model m) {
		this.m = m;
		this.setPreferredSize(new Dimension(650,500));
		this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
	}
	/** Method updating the view displayed in the web browser. Takes html
         * code and builds it. 
         * 
         * @param edp html code to be displayed
         */
	public void displayPage(JEditorPane edp) {
		edp.setEditable(false);
		this.setViewportView(edp);
	}
	
}
