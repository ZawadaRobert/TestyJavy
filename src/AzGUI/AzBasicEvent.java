package AzGUI;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AzBasicEvent {
	public static void performDialogExitFromButton(JFrame frame, AbstractButton button) {
		button.addActionListener(e -> {
			eventDialogExit(frame);
		});
	}
	
	public static void performDialogExitFromX(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				eventDialogExit (frame);
			}
		});
	}
	
	public static void eventDialogExit(JFrame frame) {
		
		String message = "Czy na pewno chcesz zakoñczyæ dzia³anie programu?";
		String title = "PotwierdŸ";
		int mode = JOptionPane.YES_NO_OPTION;
		int type = JOptionPane.QUESTION_MESSAGE;
		Icon icon = null;
		String[] options = {"Tak", "Nie"};
	
		int confirm = JOptionPane.showOptionDialog(frame, message, title, mode, type, icon, options, options[0]);
		if (confirm == 0) System.exit(0);
	}
	
	public static void eventDialogError(JFrame frame, String errorText) {

		String message = errorText;
		String title = "B³¹d";
		int type = JOptionPane.ERROR_MESSAGE;
		Icon icon = null;
	
		JOptionPane.showMessageDialog(frame, message, title, type);
	}
}