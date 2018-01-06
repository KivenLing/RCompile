package table;

/**
 * @author 王启航
 * @since 2018.01.06
 * 此类与PieData，SerData
 * 分别负责柱状图，饼图，折线图的具体图表类在
 * 生成时所需数据。
 */
public class BarData {
	
	private double value;
	
	private String name;
	
	private String name2;

	public BarData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BarData(double value, String name) {
		super();
		this.value = value;
		this.name = name;
		this.name2 = name;
	}

	public BarData(double value, String name, String name2) {
		super();
		this.value = value;
		this.name = name;
		this.name2 = name2;
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

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
	
}
