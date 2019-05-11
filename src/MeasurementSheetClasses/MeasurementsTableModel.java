package MeasurementSheetClasses;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

public class MeasurementsTableModel extends AbstractTableModel { 
	private ArrayList<String> headers = new ArrayList<String>( 
				Arrays.asList("Nr","Wymiar","LowTol","UpTol","Próbka 1","Próbka 2","Próbka 3")); 
	
	private ArrayList<MeasurementsSeries> data;
	
	public MeasurementsTableModel(ArrayList<MeasurementsSeries> list) {
		this.data = list;
	}
	
	public int getColumnCount() {
		return headers.size();
	}
	
	public int getRowCount() {
		int size;
		if (data == null)
			size = 0;
		else
			size = data.size();
		return size;
	}
	
	public Object getValueAt(int row, int col) {
		Object temp = null;
		if (col == 0)
			temp = row+1;
		else if (col == 1)
			temp = data.get(row).getCharacteristic();
		else if (col == 2)
			temp = data.get(row).getCharacteristic().getLowTol();
		else if (col == 3)
			temp = data.get(row).getCharacteristic().getUpTol();
		else if (col == 4)
			temp = data.get(row).getCharacteristic().getLowTol();
		else if (col == 5)
			temp = data.get(row).getCharacteristic().getUpTol();
		else if (col == 6)
			temp = data.get(row).getCharacteristic().toString();
		else
			return "null";
		return temp;
	}
	
	public boolean isCellEditable(int row, int column) {
		if (column<=3)
			return false;
		else
			return true;
	}
	
	public void addRow(Characteristic characteristic) {
		data.add(new MeasurementsSeries(characteristic));
		fireTableRowsInserted(1,0);
	}
	
	public void addColumn(String header) {
		headers.add(header);
		fireTableStructureChanged();
	}
	
	public String getColumnName(int col) {
		return headers.get(col);
	}
	
	public Class getColumnClass(int col) {
		if (col == 0)
			return Integer.class;
		else if (col == 1)
			return String.class;
		else {
			return BigDecimal.class;
		}
	}
}