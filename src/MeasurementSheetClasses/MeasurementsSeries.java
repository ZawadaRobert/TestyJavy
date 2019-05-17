package MeasurementSheetClasses;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MeasurementsSeries {
	private Characteristic characteristic;
	private ArrayList<BigDecimal> measurements;
	
	public MeasurementsSeries(Characteristic characteristic) {
		this.characteristic=characteristic;
		measurements= new ArrayList<BigDecimal>();
	}
	
	public void addMeasurement(BigDecimal measurement) {
		measurements.add(measurement);
	}
	public void removeLastMeasurement() {
		if(measurements.size()>0)
			measurements.remove(measurements.size()-1);
	}
	
	public void setMeasurement(int pos, BigDecimal measurement) {
		measurements.set(pos, measurement);
	}
	
	public BigDecimal getMeasurements(int measurement) {
		return measurements.get(measurement);
	}
	
	public Characteristic getCharacteristic() {
		return characteristic;
	}
	
	public void setCharacteristic(Characteristic characteristic) {
		this.characteristic = characteristic;
	}
}