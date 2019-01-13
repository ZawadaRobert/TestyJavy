package AzGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AzEvent implements ActionListener {
	JButton button;
	String l;


	public AzEvent(JButton button, String l) {
		this.l=l;
		this.button=button;
		
		this.button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(this.l);
	}
}