package AzGUI;
import javax.swing.JLabel;

public class AzLabel extends JLabel {
	private int x;
	private int y;
	private int width;
	private int heigh;
	private String text;
	
	public AzLabel (String text, int x, int y, int width, int heigh) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.heigh=heigh;
		this.text=text;
		setText(this.text);
		setBounds(this.x,this.y,this.width,this.heigh);
	}
}
