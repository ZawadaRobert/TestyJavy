import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;

import AzGUI.CharacterFilter;
import MeasurementSheetClasses.Characteristic;
import MeasurementSheetClasses.MeasurementsSeries;
import MeasurementSheetClasses.MeasurementsTableModel;

public class TestFunkcji {

	private JFrame frame;
	private JTable table;
	private JButton btnNewButton;
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
	
	private MeasurementsTableModel model;
	
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
		
		//Przyk³adowy wpis do testu
		ArrayList<MeasurementsSeries> myList = new ArrayList<MeasurementsSeries>();
		
		Characteristic test1 = new Characteristic(new BigDecimal(10), new BigDecimal(1), new BigDecimal(-1));
		MeasurementsSeries series1 = new MeasurementsSeries(test1);
		series1.addMeasurement(new BigDecimal(9));
		series1.addMeasurement(new BigDecimal(2));
		series1.addMeasurement(new BigDecimal(3));
		myList.add(series1);
		
		Characteristic test2 = new Characteristic(new BigDecimal(30), new BigDecimal(3), new BigDecimal(-3));
		MeasurementsSeries series2 = new MeasurementsSeries(test2);
		series2.addMeasurement(new BigDecimal(30));
		series2.addMeasurement(new BigDecimal(29));
		series2.addMeasurement(new BigDecimal(27));
		myList.add(series2);
		
		
		model = new MeasurementsTableModel(myList);
		
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
		
		btnDodajKolumne = new JButton("Dodaj kolumnê");
		btnDodajKolumne.addActionListener(e -> {
			model.addColumn(columnNameField.getText());
			defaultTo(columnNameField,"Próbka "+Integer.sum(model.getColumnCount(),-3));
		});
		
		columnNameField = new JTextField();
		northPane.add(columnNameField);
		columnNameField.setColumns(10);
		defaultTo(columnNameField,"Próbka "+Integer.sum(model.getColumnCount(),-3));
		northPane.add(btnDodajKolumne);
		
		btnUsunKolumne = new JButton("Usuñ kolumnê");
		btnUsunKolumne.addActionListener(e -> {
			if (model.getColumnCount()>4)
				model.removeColumn(model.getColumnCount()-1);
			defaultTo(columnNameField,"Próbka "+Integer.sum(model.getColumnCount(),-3));
		});
		northPane.add(btnUsunKolumne);
		
		btnNewButton = new JButton("Dodaj");
		northPane.add(btnNewButton);
		btnNewButton.addActionListener(e -> addCharacteristic());
		
		table = new JTable() {
			//Ustawienie renderowania tabeli
			final CustomCellRenderer rendererRedFont = new CustomCellRenderer();
			
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return rendererRedFont;
			}
		};
		
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setModel(model);
		
		
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
	
	//Filtr wprowadzania do tabeli
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
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			BigDecimal lowTol= (BigDecimal)table.getValueAt(row, 2);
			BigDecimal upTol= (BigDecimal)table.getValueAt(row, 3);
			if (value instanceof BigDecimal&&column>=4) {
				boolean cond1 = ((BigDecimal)value).compareTo(lowTol)<0;
				boolean cond2 = ((BigDecimal)value).compareTo(upTol)>0;
				if(cond1||cond2) {
					setForeground(Color.RED);
				} else {
					setForeground(Color.BLUE);
				}
			} else {
				setForeground(Color.BLACK);
			}
			return this;
		}
	}
	
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
		Characteristic characteristic = new Characteristic(new BigDecimal(valueField.getText()),
														new BigDecimal(dev1Field.getText()),
														new BigDecimal(dev2Field.getText()));
		characteristic.setSuffix("\u2300");
		
		model.addRow(characteristic);
	}

}
