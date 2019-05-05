import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;

import AzGUI.AzTextField;
import AzGUI.CharacterFilter;

import java.math.BigDecimal;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.AbstractCellEditor;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class TestFunkcji {

	private JFrame frame;
	private JTable table;
	private JButton btnNewButton;
	private JButton btnDodajWiersz;
	private JButton btnDodajKolumne;
	private JButton btnUsunKolumne;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private JTextField dev2Field;
	private JTextField valueField;
	private JTextField dev1Field;
	private JTextField textField_output;
	private JPanel southPane;
	private JPanel northPane;
	private JTextField columnNameField;
	private JPanel westPane;
	private JPanel devPane;
	
	private DefaultTableModel model; 

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
		
		model = new DefaultTableModel(data, header)  {
			public boolean isCellEditable(int row, int column) {
				if (column==0)
					return false;
				else
					return true;
			}
		};
		
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		mainPane = new JPanel();
		frame.getContentPane().add(mainPane);
		mainPane.setLayout(new BorderLayout(0, 0));
		
		southPane = new JPanel();
		mainPane.add(southPane, BorderLayout.SOUTH);
		southPane.setLayout(new BoxLayout(southPane, BoxLayout.Y_AXIS));
		
		textField_output = new JTextField();
		southPane.add(textField_output);
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
		
		final CustomCellRenderer rendererDefault = new CustomCellRenderer(Color.BLACK, Color.WHITE);
		final CustomCellRenderer rendererRedFont = new CustomCellRenderer(Color.RED, Color.WHITE);
		
		table = new JTable() {
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				
				String text = (String) model.getValueAt(row, column);
		//		System.out.println(""+row+" "+column+": "+text);;
				
				if (text==null)
					return rendererDefault;
				else {
					try {
						int value = Integer.parseInt(text);
						if (value==1050)
							return rendererRedFont;
						else
							return rendererDefault;
					} catch (NumberFormatException e) {
						return rendererDefault;
					}
				}
			}
		};;
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setModel(model);
		
		//
		TableColumn col = table.getColumnModel().getColumn(1);
		col.setCellEditor(new CustomCellEditor());
		
		scrollPane = new JScrollPane(table);
		mainPane.add(scrollPane, BorderLayout.CENTER);
		
		westPane = new JPanel();
		mainPane.add(westPane, BorderLayout.WEST);
		
		valueField = new JTextField();
		valueField.setColumns(6);
		defaultTo(valueField,"0");
		westPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		westPane.add(valueField);
		
		devPane = new JPanel();
		westPane.add(devPane);
		devPane.setLayout(new GridLayout(0, 1, 2, 0));
		
		dev1Field = new JTextField();
		devPane.add(dev1Field);
		dev1Field.setColumns(6);
		defaultTo(dev1Field,"0");
		
		dev2Field = new JTextField();
		devPane.add(dev2Field);
		dev2Field.setColumns(6);
		defaultTo(dev2Field,"0");
		((AbstractDocument) valueField.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.-]"));
		((AbstractDocument) dev1Field.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.-]"));
		((AbstractDocument) dev2Field.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.-]"));
	}
	
	//Próba filtrowania wprowadzania do tabeli
	public class CustomCellEditor extends AbstractCellEditor implements TableCellEditor {
		
		JTextField component = new JTextField();
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
			
			((AbstractDocument) component.getDocument()).setDocumentFilter(new CharacterFilter("[^0-9.-]"));
			component.setText((String) value);
			return component;
		}
		
		public Object getCellEditorValue() {
			return component.getText();
		}
	}
	
	class CustomCellRenderer extends DefaultTableCellRenderer {
		private Color foreground;
		private Color background;
		
		public CustomCellRenderer(Color foreground, Color background) {
			this.foreground = foreground;
			this.background = background;
		}
		
		public CustomCellRenderer() {
		}
		
		public Color getForeground() {
			return foreground;
		}

		public void setForeground(Color foreground) {
			this.foreground = foreground;
		}

		public Color getBackground() {
			return background;
		}

		public void setBackground(Color background) {
			this.background = background;
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				rendererComp.setForeground(foreground);
				rendererComp.setBackground(background);
				return rendererComp ;
		}
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
		Characteristic temp = new Characteristic(new BigDecimal(valueField.getText()),
												new BigDecimal(dev1Field.getText()),
												new BigDecimal(dev2Field.getText()));
		temp.setSuffix("\u2300");
		textField_output.setText(temp.toString());
		System.out.println(temp);
	}

}
