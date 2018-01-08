package util;
//+1 -2 *3 /4 %%5  %/%6  ^7 >8 <9 ==10 <=11 >=12 !=13 :14 &15 |16 &&17 ||18 
public enum SymbolTable {
	//算数运算符
	math1("+","1",1),
	math2("-","2",1),
	math3("*","3",2),
	math4("/","4",2),
	math5("%%","5",3),
	math6("%/%","6",3),
	math7("(","(",-1),
	math8(")",")",-1),
	math9("^","7",3),
	//关系运算符
	relation1(">","8",0),
	relation2("<","9",0),
	relation3("==","10",0),
	relation4("<=","11",0),
	relation5(">=","12",0),
	relation6("!=","13",0),
	//逻辑运算符
	logical1("&","15",0),
	logical2("|","16",0),
	logical4("&&","17",0),
	logical5("||","18",0),
	//赋值运算符
	assign1("<-","19",0),
	assign2("=","19",0),
	assign3("<<-","19",0),
	assign4("->","20",0),
	assign5("->>","20",0),
	//其他运算符
	other1("\"","\"",9),
	other2("\'","\'",9),
	other3("#'","#'",0),
	other4(":","14",3),
	other5(",",",",9),
	other6("{","99",9),
	other7("}","99",9),
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
