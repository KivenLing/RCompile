package util;
/**
 * @author 周文杰
 * data:2018.01.06
 * 函数表
 */
public enum FuncTable {
	func1("seq"),
	func2("mean"),
	func3("max"),
	func4("paste"),
	func5("class"),
	func6("ls"),
	func7("print"),
	func8("cat"),
	func9("rm"),
	func10("c"),
	func11("list"),
	func12("plot"),
	func13("barplot"),
	func14("pie"),
	func15("setwd"),
	func16("png"),
	func17("toupper"),
	func18("tolower"),
	func19("paste"),
	
	;
	private String funcname;
	
	private FuncTable(String funcname) {
		this.funcname = funcname;
	}
	
	public String getFuncName() {
		return this.funcname;
	}
}
