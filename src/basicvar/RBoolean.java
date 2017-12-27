package basicvar;

/*
 * R语言逻辑判断变量封装
 */
public class RBoolean extends CommonVar {
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
			return "TRUE";
		} else {
			return "FALSE";
		} 
	}
	
	
}
