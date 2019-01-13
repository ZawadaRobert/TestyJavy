package AzGUI;
import java.awt.Component;
import java.util.Set;

import javax.swing.JFrame;

public class AzFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	
	public int width;
	public int height;
	public String name;
	
	public AzFrame(String l, int x, int y) {
		this.width=y;
		this.height=x;
		this.name=l;
		
		setSize(this.width,this.height);
		setTitle(this.name);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addAll(Set<Component> list) {
		for (Component comp : list) {
			add(comp);
		}
	}
	
	public void addAll(Component[] list) {
		for (Component comp : list) {
			add(comp);
		}
	}
}