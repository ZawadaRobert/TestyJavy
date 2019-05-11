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
	
	public void addMeasurements(BigDecimal measurements) {
		measurements.add(measurements);
	}
	
	public BigDecimal getMeasurements(int i) {
		return measurements.get(1);
	}
	
	public Characteristic getCharacteristic() {
		return characteristic;
	}
	
	public void setCharacteristic(Characteristic characteristic) {
		this.characteristic = characteristic;
	}
}