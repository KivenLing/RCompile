package util;
/**
 * @author 周文杰
 * data:2018.01.07
 * 关键字表
 */
//关键字表
public enum KeywordTable {
	keyword1("if"),
	keyword2("else"),
	keyword3("break"),
	keyword4("continue"),
	keyword5("while"),
	keyword6("for"),
	keyword7("do"),
	keyword8("switch"),
	keyword9("return"),

	;
	private String keywordname;
	
	private KeywordTable(String keywordname) {
		this.keywordname = keywordname;
	}
	
	public String getKeyWordName() {
		return this.keywordname;
	}
}
