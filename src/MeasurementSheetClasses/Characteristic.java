package MeasurementSheetClasses;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Characteristic {
	private BigDecimal value;
	private BigDecimal deviation1;
	private BigDecimal deviation2;
	private String prefix;
	private String suffix;
	
	public Characteristic(BigDecimal value, BigDecimal deviation1, BigDecimal deviation2) {
		this.value=value;
		if (deviation1.compareTo(deviation2) < 0) {
			this.deviation1=deviation2;
			this.deviation2=deviation1;
		}
		else {
			this.deviation1=deviation1;
			this.deviation2=deviation2;
		}
		setPrefix("");
		setSuffix("");
	}
	
	
	
	public Characteristic(BigDecimal value, BigDecimal deviation1) {
		this.value=value;
		if (deviation1.compareTo(deviation2) < 0) {
			this.deviation2=deviation1;
			this.deviation1=BigDecimal.ZERO;
		}
		else {
			this.deviation1=deviation1;
			this.deviation2=BigDecimal.ZERO;
		}
		setPrefix("");
		setSuffix("");
	}
	
	public Characteristic(BigDecimal value) {
		this.value=value;
		this.deviation1=BigDecimal.ZERO;
		this.deviation2=BigDecimal.ZERO;
		setPrefix("");
		setSuffix("");
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getDeviation1() {
		return deviation1;
	}

	public void setDeviation1(BigDecimal deviation1) {
		if (deviation1.compareTo(this.deviation2) < 0) {
			this.deviation1 = this.deviation2;
			this.deviation2=deviation1;
		}
		else
			this.deviation1=deviation1;
	}

	public BigDecimal getDeviation2() {
		return deviation2;
	}

	public void setDeviation2(BigDecimal deviation2) {
		if (deviation2.compareTo(deviation1) > 0) {
			this.deviation2 = this.deviation1;
			this.deviation1=deviation2;
		}
		else
			this.deviation2=deviation2;
	}

	@Override
	public String toString() {
		DecimalFormat fmt = new DecimalFormat("+#,##0.00;-#");
		return "" + prefix + value + suffix + " " + fmt.format(deviation1) + " " + fmt.format(deviation2);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public BigDecimal getUpTol() {
		return value.add(deviation1);
	}

	public BigDecimal getLowTol() {
		return value.add(deviation2);
	}

}
