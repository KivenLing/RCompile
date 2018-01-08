package varmanage;

import basicvar.CommonVar;
import collection.RCollection;

public class VarManager {
	public static final String COLLECTION = "collection";
	public static final String COMMONVAR = "commonvar";
	private CommonVarManager<CommonVar> comVarMan = null;
	private CollectionManager<RCollection<CommonVar>> collMan = null;
	private static VarManager varMan = null;
	
	private VarManager() {
		comVarMan = CommonVarManager.getInstance();
		collMan = CollectionManager.getInstance();
	}
	
	public static VarManager getInstance() {
		if (varMan == null) {
			varMan = new VarManager();
		}
		return varMan;
	}
	
	public String varContain(String name) {
		if (collMan.isContains(name)) {
			return COLLECTION;
		} else if (comVarMan.isContains(name)) {
			return COMMONVAR;
		}
		return null;
	}
	
	public void addCommonVar(String name, CommonVar var) {
		if (collMan.isContains(name)) {
			collMan.rmElement(name);
		} else if (comVarMan.isContains(name)) {
			comVarMan.rmElement(name);
		}
		comVarMan.addElement(name, var);
	}
	
	public void addCollVar(String name, RCollection<CommonVar> var) {
		if (collMan.isContains(name)) {
			collMan.rmElement(name);
		} else if (comVarMan.isContains(name)) {
			comVarMan.rmElement(name);
		}
		collMan.addElement(name, var);
	}
	
	public boolean rmVar(String name) {
		boolean flag = (collMan.rmElement(name) || comVarMan.rmElement(name));
		return flag;
	}
	
	public CommonVar getCommonVar(String name) {
		return comVarMan.getElement(name);
	}
	
	public RCollection<CommonVar> getCollection(String name) {
		return collMan.getElement(name);
	}
	
	public String printVar() {
		String Answer = "";
		String[] comVar = comVarMan.printElement().split(" ");
		for (String var : comVar) {
			if (var.startsWith(".")) continue;
			else Answer += var + " ";
		}
		String[] collVar = collMan.printElement().split(" ");
		for (String var : collVar) {
			if (var.startsWith(".")) continue;
			else Answer += var + " ";
		}
		return Answer;
	}
	
	public String printVar(boolean Flag) {
		String Answer = "";
		String[] comVar = comVarMan.printElement().split(" ");
		for (String var : comVar) {
			if (var.startsWith(".1")) continue;
			else Answer += var + " ";
		}
		String[] collVar = collMan.printElement().split(" ");
		for (String var : collVar) {
			if (var.startsWith(".1")) continue;
			else Answer += var + " ";
		}
		return Answer;
	}
}
