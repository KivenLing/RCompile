package varmanage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import basicvar.CommonVar;

public class CommonVarManager<Var extends CommonVar> {
	private static CommonVarManager comVarMan = null;
	private static Map comVarMap;
	
	private CommonVarManager() {
		comVarMap = new HashMap<String, Var>();
	}
	
	public static CommonVarManager<CommonVar> getInstance(){
		if (comVarMan == null){
			comVarMan = new CommonVarManager();
		}
		return comVarMan;
	}
	
	public boolean isContains(String name) {
		return comVarMap.containsKey(name);
	}
	
	public void addElement(String name, Var var) {
		comVarMap.put(name, var);
	}
	
	public Var getElement(String name){
		if (comVarMap.containsKey(name)) {
			return (Var) comVarMap.get(name);
		} else {
			return null;
		}
	}
	
	public boolean rmElement(String name) {
		if (comVarMap.containsKey(name)) {
			comVarMap.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public String printElement() {
		String varListStr = "";
		Set<String> keys = comVarMap.keySet();
		for (String key : keys) {
			varListStr += " " + key;
		}
		return varListStr;
	}
}
