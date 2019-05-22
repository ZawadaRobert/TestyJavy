package MeasurementSheetClasses;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CharacteristicSmallPane extends JPanel {
	
	private JTextField valueField;
	private JPanel innerPane;
	private JTextField dev1Field;
	private JTextField dev2Field;
	static final DecimalFormat fmt = new DecimalFormat("+#,##0.00;-#");
	static final DecimalFormat fmt2 = new DecimalFormat("±#,##0.00;±#");
	static final Font regularFont = new Font("Monospaced", Font.PLAIN, 14);
	static final Font smallFont = new Font("Monospaced", Font.PLAIN, 10);
	
	public CharacteristicSmallPane(Characteristic characteristic) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		valueField = new JTextField();
		add(valueField);
		
		BigDecimal value = characteristic.getValue();
		String prefix = characteristic.getPrefix();
		String suffix = characteristic.getSuffix();
		
		valueField.setText(prefix+value+suffix);
		valueField.setBorder(BorderFactory.createEmptyBorder());
		valueField.setFont(regularFont);
		
		innerPane = new JPanel();
		innerPane.setPreferredSize(new Dimension(35, 0));
		innerPane.setLayout(new GridLayout(0, 1));
		add(innerPane);
		
		BigDecimal dev1 = characteristic.getDeviation1();
		BigDecimal dev2 = characteristic.getDeviation2();
		
		if (dev2.compareTo(BigDecimal.ZERO)==0) {
			dev2Field = new JTextField();
			dev2Field.setText(fmt.format(dev1));
			dev2Field.setBorder(BorderFactory.createEmptyBorder());
			dev2Field.setFont(smallFont);
			innerPane.add(dev2Field);
		}
		else if (dev1.add(dev2).compareTo(BigDecimal.ZERO)==0) {
			dev2Field = new JTextField();
			dev2Field.setText(fmt2.format(dev1));
			dev2Field.setBorder(BorderFactory.createEmptyBorder());
			dev2Field.setFont(smallFont);
			innerPane.add(dev2Field);
		}
		else {
			dev2Field = new JTextField();
			dev2Field.setText(fmt.format(dev1));
			dev2Field.setBorder(BorderFactory.createEmptyBorder());
			dev2Field.setFont(smallFont);
			innerPane.add(dev2Field);
			
			dev1Field = new JTextField();
			dev1Field.setText(fmt.format(dev2));
			dev1Field.setBorder(BorderFactory.createEmptyBorder());
			dev1Field.setFont(smallFont);
			innerPane.add(dev1Field);
		}
	}
}
