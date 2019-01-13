package AzGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AzBasicEvent {
	public void performDialogExitFromButton(AbstractButton button, JFrame frame) {
		button.addActionListener(e -> {
			eventDialogExit (frame);
		});
	}
	
	public void performDialogExitFromX(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				eventDialogExit (frame);
			}
		});
	}
	
	public void eventDialogExit (JFrame frame) {
		
		Object message = "Czy na pewno chcesz zakoñczyæ dzia³anie programu?";
		String title = "PotwierdŸ";
		int mode = JOptionPane.YES_NO_OPTION;
		int type = JOptionPane.QUESTION_MESSAGE;
		Icon icon = null;
		String[] options = {"Tak", "Nie"};
	
		int confirm = JOptionPane.showOptionDialog(frame, message, title, mode, type, icon, options, options[0]);
		if (confirm == 0) System.exit(0);
	}
}


