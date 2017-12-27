package basicvar;
/*
 * R复数变量的封装
 */
public class RComplex extends CommonVar{
	//复数的实数部分
	private double real;
	//复数的虚数部分
	private double virtual;
	
	//复数构造函数
	public RComplex(double realVal, double virtualVal) {
		this.classType = CommonVar.COMPLEX;
		this.real = realVal;
		this.virtual = virtualVal;
	}
	
	//取出复数实数部分
	public double getReal() {
		return this.real;
	}
	
	//取出复数虚数部分
	public double getVirtual() {
		return this.virtual;
	}
	
	//去掉整数后多余小数点部分
	private String transInt(double content) {
		if ((content - (int)content) == 0) {
			return "" + (int)content;
		} else {
			return "" + content;
		}
	}
	
	@Override
	public String toString() {
		String content = "";
		if (this.virtual >= 0) {
			content = content + transInt(this.real) + "+" + transInt(this.virtual) + "i";
		} else {
			content = content + transInt(this.real) + transInt(this.virtual) + "i";
		}
		return content;
	}
}
