package function;

import util.FuncTable;
import util.SymbolTable;

public class TableFunc {
	private TableFunc() {}
	private static TableFunc onlyBool = new TableFunc();

	public static TableFunc getOnlyBool() {
		return onlyBool;
	}
	
	public int getPrio(String keyword) {
		int prio = 0;
		for(SymbolTable Rkeyword : SymbolTable.values()) {
			if(Rkeyword.getRSgin().toString().indexOf(keyword) != -1) {
				prio = Rkeyword.getPriority();
			}
		}
		return prio;
	}
	
	public boolean ifexit(String keyword) {
		for(SymbolTable Rkeyword : SymbolTable.values()) {
			if(Rkeyword.getRSgin().toString().indexOf(keyword) != -1) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean iffunc(String keyword) {
		for(FuncTable name : FuncTable.values()) {
			if(name.getFuncName().equals(keyword)) {
				return true;
			}
	}
		return false;
}
	
}
