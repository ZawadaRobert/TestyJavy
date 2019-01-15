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
}
