package AzGUI;
import javax.swing.JCheckBox;

public class AzCheckBox extends JCheckBox {
	private int x;
	private int y;
	private int width;
	private int heigh;
	private String text;
	
	public AzCheckBox (String text, int x, int y, int width, int heigh) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.heigh=heigh;
		this.text=text;
		setText(this.text);
		setBounds(this.x, this.y, this.width, this.heigh);
	}
}
