package basicvar;

/**
 * @author 凌凯
 * @since 2018.01.06
 * R语言逻辑判断变量封装
 */
public class RBoolean extends CommonVar {
	//R的布尔字符串形式
	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";
	//变量内容
	private boolean content;
	
	//构造函数，boolean型参数
	public RBoolean(boolean contentVal) {
		this.classType = CommonVar.LOGICAL;
		this.content = contentVal;
	}

	//返回内容，为boolean
	public boolean getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		if (this.content == true) {
			return RBoolean.TRUE;
		} else {
			return RBoolean.FALSE;
		} 
	}
	
	
}
