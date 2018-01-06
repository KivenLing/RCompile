package table;
/**
 * @author 王启航
 * @since 2018.01.06
 * 详见BarData
 */
public class SeriesData {
	
	private double value;
	
	private int rank;
	
	private int defaultrank;

	public SeriesData(double value, double rank) {
		super();
		this.value = value;
		this.rank = (int) rank;
		this.defaultrank = 2000;
	}

	public double getDefaultrank() {
		return defaultrank;
	}

	public SeriesData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public double getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		return "SeriesData [value=" + value + ", rank=" + rank + ", defaultrank=" + defaultrank + "]";
	}

}
