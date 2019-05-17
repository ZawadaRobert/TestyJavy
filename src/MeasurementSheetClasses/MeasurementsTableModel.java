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
	
	public Object getValueAt(int row, int column) {
		Object temp = null;
		if (column == 0)
			temp = row+1;
		else if (column == 1)
			temp = data.get(row).getCharacteristic();
		else if (column == 2)
			temp = data.get(row).getCharacteristic().getLowTol();
		else if (column == 3)
			temp = data.get(row).getCharacteristic().getUpTol();
		else
			temp = data.get(row).getMeasurements(column-4);
			//temp = null;
		return temp;
	}
	
	public boolean isCellEditable(int row, int column) {
		if (column<=3)
			return false;
		else
			return true;
	}
	
	public void addRow(Characteristic characteristic) {
		MeasurementsSeries series = new MeasurementsSeries(characteristic);
		for (int i=0; i<getColumnCount()-4; i++)
			series.addMeasurement(null);
		data.add(series);
		fireTableRowsInserted(1,0);
	}
	
	public void addColumn(String header) {
		for (MeasurementsSeries series : data)
			series.addMeasurement(null);
		headers.add(header);
		fireTableStructureChanged();
	}
	
	public void removeColumn(int column) {
		for (MeasurementsSeries series : data)
			series.removeLastMeasurement();
		headers.remove(column);
		fireTableStructureChanged();
	}
	
	// Overide SetValue vy edit?
	public void setValueAt(Object value, int row, int column) {
		if (column>=2)
			data.get(row).setMeasurement(column-4,new BigDecimal(value.toString()));
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