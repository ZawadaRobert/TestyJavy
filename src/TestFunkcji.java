import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import AzGUI.CharacterFilter;

import java.math.BigDecimal;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;

public class TestFunkcji {

	private JFrame frame;
	private JTable table;
	private JButton btnNewButton;
	private JPanel panel;
	private JTextField textField_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_output;
	private JPanel panel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFunkcji window = new TestFunkcji();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestFunkcji() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 775, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Wymiar", "Próbka 1", "Próbka 2", "Próbka 3", "Próbka 4"
			}
		));
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		frame.getContentPane().add(table, BorderLayout.SOUTH);
		
		JButton btnDodajWiersz = new JButton("Dodaj wiersz");
		btnDodajWiersz.addActionListener(e -> model.addRow(new String[]{}));
		frame.getContentPane().add(btnDodajWiersz, BorderLayout.NORTH);
		
		btnNewButton = new JButton("Dodaj");
		btnNewButton.addActionListener(e -> addCharacteristic());
		frame.getContentPane().add(btnNewButton, BorderLayout.WEST);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		textField_1 = new JTextField();
		panel.add(textField_1, BorderLayout.CENTER);
		textField_1.setColumns(10);
		defaultTo(textField_1,"0");
		((AbstractDocument) textField_1.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		defaultTo(textField_2,"0");
		((AbstractDocument) textField_2.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		
		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		defaultTo(textField_3,"0");
		((AbstractDocument) textField_3.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		
		textField_output = new JTextField();
		frame.getContentPane().add(textField_output, BorderLayout.CENTER);
		textField_output.setColumns(10);
		
	}
	
	//Jeszcze nie dzia³a
	public void defaultTo(JTextField field, String text) {
		field.setText(text);
		field.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent focusEvent) {
				if (field.getText().equals(text)) {
					field.setText("");
				}
			}
			
			public void focusLost(FocusEvent focusEvent) {
				if (field.getText().equals("")) {
					field.setText(text);
				}
			}
		});
	}
	
	public void addCharacteristic() {
		Characteristic temp = new Characteristic(new BigDecimal(textField_1.getText()),
												new BigDecimal(textField_2.getText()),
												new BigDecimal(textField_3.getText()));
		temp.setSuffix("\u2300");
		textField_output.setText(temp.toString());
		System.out.println(temp);
	}

}
