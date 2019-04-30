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
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class TestFunkcji {

	private JFrame frame;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnDodajWiersz;
	private JButton btnDodajKolumne;
	private JButton btnUsunKolumne;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private JTextField textField_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_output;
	private JPanel panel_1;
	private JPanel northPane;
	private JTextField columnNameField;

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
		
		String data[][] = null;
		
		String[] header = {"Wymiar", "Próbka 1", "Próbka 2"};
		
		DefaultTableModel model = new DefaultTableModel(data,header);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		mainPane = new JPanel();
		frame.getContentPane().add(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		mainPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		defaultTo(textField_2,"0");
		((AbstractDocument) textField_2.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		panel_1.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		defaultTo(textField_3,"0");
		((AbstractDocument) textField_3.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		panel_1.add(textField_3);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		defaultTo(textField_1,"0");
		((AbstractDocument) textField_1.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		panel_1.add(textField_1);
		
		textField_output = new JTextField();
		panel_1.add(textField_output);
		textField_output.setColumns(10);
		
		northPane = new JPanel();
		mainPane.add(northPane, BorderLayout.NORTH);
		northPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnDodajWiersz = new JButton("Dodaj wiersz");
		btnDodajWiersz.addActionListener(e -> model.addRow(new String[]{}));
		northPane.add(btnDodajWiersz);
		
		btnDodajKolumne = new JButton("Dodaj kolumnê");
		btnDodajKolumne.addActionListener(e -> {
			model.addColumn(columnNameField.getText());
			defaultTo(columnNameField,"Próbka "+model.getColumnCount());
		});
		
		columnNameField = new JTextField();
		northPane.add(columnNameField);
		columnNameField.setColumns(10);
		defaultTo(columnNameField,"Próbka "+model.getColumnCount());
		northPane.add(btnDodajKolumne);
		
		btnUsunKolumne = new JButton("Usuñ kolumnê");
		btnUsunKolumne.addActionListener(e -> {
			if (model.getColumnCount()>1)
				model.setColumnCount(model.getColumnCount()-1);
				defaultTo(columnNameField,"Próbka "+model.getColumnCount());
		});
		northPane.add(btnUsunKolumne);
		
		btnNewButton = new JButton("Dodaj");
		northPane.add(btnNewButton);
		btnNewButton.addActionListener(e -> addCharacteristic());
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setModel(model);
		
		scrollPane = new JScrollPane(table);
		mainPane.add(scrollPane, BorderLayout.CENTER);
		((AbstractDocument) textField_3.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.]"));
		
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
