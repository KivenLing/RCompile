package basicvar;

/**
 * @author 凌凯
 * @since 2018.01.06
 * 对R语言的Numeric变量进行封装
 */
public class RNumeric extends CommonVar {
	//数据存储
	private double content;
	
	//构造函数
	public RNumeric(double contentVal) {
		this.classType = CommonVar.NUMERIC;
		this.content = contentVal;
	}
	
	//输出内容
	public double getContent() {
		return this.content;
	}
	
	private boolean judgeInt() {
		if ((this.content - (int)this.content) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (judgeInt()) {
			return "" + (int)content;
		} else {
			return "" + content;
		}
	}
	
}
