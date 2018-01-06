package table;
/**
 * @author 王启航
 * @since 2018.01.06
 * 详见BarData
 */
public class PieData {
	
	private double value;
	
	private String name;

	public PieData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PieData(String name, double value) {
		super();
		this.value = value;
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
