package basicvar;

/*
 * R语言逻辑判断变量封装
 */
public class RBoolean extends CommonVar {
	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";
	//变量内容
	private boolean content;
	
	//构造函数，boolean型参数
	public RBoolean(boolean contentVal) {
		this.classType = CommonVar.LOGICAL;
		this.content = contentVal;
	}
	//添加的构造函数
	public RBoolean(String content) {
		this.classType = CommonVar.LOGICAL;
		boolean contentVal = false;
		if(content.equals("TRUE")) {
			contentVal = true;
		}
		this.content = contentVal;
	}
	//返回内容，为boolean
	public boolean getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		if (this.content == true) {
			return "TRUE";
		} else {
			return "FALSE";
		} 
	}
	
	
}
