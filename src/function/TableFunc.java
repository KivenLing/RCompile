package function;
/**
 * @author 周文杰
 * data:2018.01.06
 * 三张表的功能接口
 */
import util.FuncTable;
import util.KeywordTable;
import util.SymbolTable;

public class TableFunc {
	private TableFunc() {}
	private static TableFunc onlyBool = new TableFunc();

	public static TableFunc getOnlyBool() {
		return onlyBool;
	}
	
	//获得符号优先级函数
	public int getPrio(String keyword) {
		int prio = 0;
		for(SymbolTable Rkeyword : SymbolTable.values()) {
			if(Rkeyword.getRSgin().toString().indexOf(keyword) != -1) {
				prio = Rkeyword.getPriority();
			}
		}
		return prio;
	}
	
	//判断是否在符号表中
	public boolean ifexit(String keyword) {
		for(SymbolTable Rkeyword : SymbolTable.values()) {
			if(Rkeyword.getRSgin().toString().indexOf(keyword) != -1) {
				return true;
			}
		}
		return false;
	}
	
	//判断是否在函数表（返回bool）
	public static boolean if_func_bool(String keyword) {
		for(FuncTable name : FuncTable.values()) {
			if(name.getFuncName().equals(keyword)) {
				return true;
			}
	}
		return false;
}
	
	//判断是否在函数表（返回String）
		public static String if_func_st(String keyword) {
			String res = null;
			for(FuncTable name : FuncTable.values()) {
				if(name.getFuncName().equals(keyword)) {
					res = keyword;
				}
		}
			return res;
	}
	
	//判断是否在函数表（返回bool）
	public static boolean if_keyword_bool(String keyword) {
		for(KeywordTable name : KeywordTable.values()) {
			if(name.getKeyWordName().equals(keyword)) {
				return true;
			}
		}
			return false;
	}	
		
	//判断是否在关键字表（返回string）
	public static String if_keyword_st(String keyword) {
		String res = null;
		for(KeywordTable name : KeywordTable.values()) {
			if(name.getKeyWordName().equals(keyword)) {
				res = keyword;
			}
	}
		return res;
	}
}
