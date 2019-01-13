package AzGUI;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AzButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	private int x;
	private int y;
	private int width;
	private int heigh;
	private String text;
	
	public AzButton (String text, int x, int y, int width, int heigh) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.heigh=heigh;
		this.text=text;
		setText(this.text);
		setBounds(this.x, this.y, this.width, this.heigh);
	}
	
	public void performEventClose(JFrame frame) {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame PopUpFrame = new JFrame("PotwierdŸ");
				PopUpFrame.setSize(250,150);
				PopUpFrame.setLayout(new GridLayout(2, 1));
				PopUpFrame.setResizable(false);
				
				JLabel ConfirmLabel = new JLabel("<html><center>Czy aby na pewno chcesz zakoñczyæ program?</center></html>",JLabel.CENTER);
				ConfirmLabel.setBackground(Color.CYAN);
				ConfirmLabel.setOpaque(true);
				PopUpFrame.add(ConfirmLabel);
				
				JButton OkButton = new JButton ("OK");
				OkButton.setSize(100,100);
				
				JButton CancelButton = new JButton ("Anuluj");
				CancelButton.setSize(100,100);
				
				JPanel ButtonsPane = new JPanel();
				ButtonsPane.add(OkButton);
				ButtonsPane.add(CancelButton);
				PopUpFrame.add(ButtonsPane);
				
				PopUpFrame.setLocationRelativeTo(null);
				PopUpFrame.setVisible(true);
				
				OkButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpFrame.dispose();
						frame.dispose();
					}
				});
				
				CancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PopUpFrame.dispose();
					}
				});
				
			}
		});
	}
}
