package AzGUI;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AzRadioButtonGroup extends JPanel{
	private String[] list;
	private JRadioButton[] ComponentArray;
	
	public AzRadioButtonGroup (String[] list) {
		this.list=list;
		ButtonGroup group = new ButtonGroup();
		int x = this.list.length;
		int z = 0;
		
		ComponentArray = new JRadioButton[x];
		
		for (String l : this.list) {
			
			ComponentArray[z] = new JRadioButton(l);
			ComponentArray[z].setBounds(200,100+(20*z),200,20);
			group.add(ComponentArray[z]);
			this.add(ComponentArray[z]);
			z++;
		}
		this.setBounds(200,100,200,400);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public AzRadioButtonGroup (String[] list, int y) {
		this.list=list;
		ButtonGroup group = new ButtonGroup();
		int x = this.list.length;
		int z = 0;
		
		ComponentArray = new JRadioButton[x];
		
		for (String l : this.list) {
			boolean bool = (z==y) ? true:false;
			
			ComponentArray[z] = new JRadioButton(l,bool);
			group.add(ComponentArray[z]);
			this.add(ComponentArray[z]);
			z++;
		}
		this.setBounds(200,100,200,400);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}
	
	public JRadioButton[] getRadioGroup() {
		return this.ComponentArray;	
	}
	
	public JRadioButton getRadioButton(int x) {
		return this.ComponentArray[x];	
	}
	
	public boolean isButtonSelected(int x) {
		return this.ComponentArray[x].isSelected();
	}

}