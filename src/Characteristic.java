import java.math.BigDecimal;

public class Characteristic {
	private BigDecimal value;
	private BigDecimal deviation1;
	private BigDecimal deviation2;
	private BigDecimal upToleranceLimit;
	private BigDecimal lowToleranceLimit;
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
		calculateToleranceLimits();
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
		calculateToleranceLimits();
	}
	
	public Characteristic(BigDecimal value) {
		this.value=value;
		this.deviation1=BigDecimal.ZERO;
		this.deviation2=BigDecimal.ZERO;
		setPrefix("");
		setSuffix("");
		calculateToleranceLimits();
	}
	
	public void calculateToleranceLimits() {
		this.upToleranceLimit = value.add(deviation1);
		this.lowToleranceLimit = value.add(deviation2);
	}
	
	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
		calculateToleranceLimits();
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
		calculateToleranceLimits();
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
		calculateToleranceLimits();
	}

	@Override
	public String toString() {
		return "Characteristic [value=" + prefix + value + suffix + ", dev1=" + deviation1 + ", dev2=" + deviation2
				+ ", " + lowToleranceLimit + "–" + upToleranceLimit + "]";
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

	public BigDecimal getUpToleranceLimit() {
		return upToleranceLimit;
	}

	public BigDecimal getLowToleranceLimit() {
		return lowToleranceLimit;
	}

}
