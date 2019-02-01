package AzGUI;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AzRadioButtonGroup extends JPanel{
	private String[] list;
	private JRadioButton[] buttonArray;
	private ButtonGroup group;
	
	
	public AzRadioButtonGroup (String[] list) {
		this.list=list;
		group = new ButtonGroup();
		int x = this.list.length;
		int z = 0;
		
		buttonArray = new JRadioButton[x];
		
		for (String l : this.list) {
			
			buttonArray[z] = new JRadioButton(l);
			buttonArray[z].setBounds(200,100+(20*z),200,20);
			group.add(buttonArray[z]);
			this.add(buttonArray[z]);
			z++;
		}
		this.setBounds(200,100,200,400);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public AzRadioButtonGroup (String[] list, int y) {
		this.list=list;
		group = new ButtonGroup();
		int x = this.list.length;
		int z = 0;
		
		buttonArray = new JRadioButton[x];
		
		for (String l : this.list) {
			boolean bool = z==y;
			
			buttonArray[z] = new JRadioButton(l,bool);
			group.add(buttonArray[z]);
			this.add(buttonArray[z]);
			z++;
		}
		this.setBounds(200,100,200,400);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public JRadioButton[] getRadioGroup() {
		return this.buttonArray;	
	}
	
	public JRadioButton getRadioButton(int x) {
		return this.buttonArray[x];	
	}
	
	public boolean isButtonSelected(int x) {
		return this.buttonArray[x].isSelected();
	}
	
	public int whichButtonSelected() {
		
		int z=0;
		
		for (JRadioButton button : this.buttonArray) {
			if (button.isSelected()) {
				return z;
			}
			else {
			}
			z++;
		}
		return 0;
	}

}