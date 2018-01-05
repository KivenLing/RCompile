package util;

public enum FuncTable {
	func1("seq"),
	func2("mean"),
	func3("max"),
	func4("paste"),
	
	
	;
	private String funcname;
	
	private FuncTable(String funcname) {
		this.funcname = funcname;
	}
	
	public String getFuncName() {
		return this.funcname;
	}
}
