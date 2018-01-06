package basicvar;

/**
 * @author 凌凯
 * @since 2018.01.06
 * 对R的list封装
 * R语言字符及字符串变量封装类
 */
public class RChar extends CommonVar {
	//字符串部分
	private String content;
	
	//构造函数，接受String
	public RChar(String contentVal) {
		this.classType = CommonVar.CHARACTER;
		this.content = contentVal;
	}
	
	//取出字符串
	public String getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		return "\"" + this.content + "\"";
	}
	
	
}
