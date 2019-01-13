package AzGUI;
import javax.swing.JTextField;

public class AzTextField extends JTextField {
	public int x;
	public int y;
	public int width;
	public int heigh;
	public String text;
	
	public AzTextField (String text, int x, int y, int width, int heigh) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.heigh=heigh;
		this.text=text;
		setText(this.text);
		setBounds(this.x, this.y, this.width, this.heigh);
	}
}
