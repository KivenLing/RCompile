package util;

public enum SymbolTable {
	//算数运算符
	math1("+","+",1),
	math2("-","-",1),
	math3("*","*",2),
	math4("/","/",2),
	math5("%%","%",3),
	math6("%/%","/",3),
	math7("(","(",4),
	math8(")",")",4),
	math9("^","pow",3),
	math10(".",".",9),
	//关系运算符
	relation1(">",">",0),
	relation2("<","<",0),
	relation3("==","==",0),
	relation4("<=","<=",0),
	relation5(">=",">=",0),
	relation6("!=","!=",0),
	//逻辑运算符
	logical1("&","&&",0),
	logical2("|","||",0),
	logical3("!","!",0),
	logical4("&&","&&",0),
	logical5("||","||",0),
	//赋值运算符
	assign1("<-","=",0),
	assign2("=","=",0),
	assign3("<<-","=",0),
	assign4("->","=",0),
	assign5("->>","=",0),
	//其他运算符
	other1("\"","\"",9),
	other2("\'","\'",9),
	//other1(":")
	//other2("%in%")
	//other3("%*%")
	
	;
	private String Rsign;
	private String javasign;
	private int priority;
	
	private SymbolTable(String Rsign, String javasign,int priority) {
		this.Rsign = Rsign;
		this.javasign = javasign;
		this.priority = priority;
	}
	
	public String getRSgin() {
		return this.Rsign;
	}
	public String getJavaSign() {
		return this.javasign;
	}
	public int getPriority() {
		return this.priority;
	}
}
