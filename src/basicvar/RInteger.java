package basicvar;

/*
 * 对R语言的整型进行封装
 */
public class RInteger extends CommonVar {
	//整型内容
	private Integer content;
	
	//构造函数
	public RInteger(int contentVal) {
		this.classType = CommonVar.INTEGER;
		this.content = contentVal;
	}
	
	//返回整型内容
	public Integer getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		return "" + content;
	}
	
}
