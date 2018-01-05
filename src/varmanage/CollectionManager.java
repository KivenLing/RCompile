package varmanage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import basicvar.CommonVar;
import collection.RCollection;


public class CollectionManager<Var extends RCollection<CommonVar>> {
	private static CollectionManager collMan = null;
	private static Map collMap;
	
	private CollectionManager() {
		this.collMap = new HashMap<String, Var>();
	}
	
	public static CollectionManager getInstance() {
		if (collMan == null) {
			collMan = new CollectionManager();
		}
		return collMan;
	}
	
	public boolean isContains(String name) {
		return collMap.containsKey(name);
	}
	
	public void addElement(String name, Var var) {
		collMap.put(name, var);
	}
	
	public Var getElement(String name){
		if (collMap.containsKey(name)) {
			return (Var) collMap.get(name);
		} else {
			return null;
		}
	}
	
	public boolean rmElement(String name) {
		if (collMap.containsKey(name)) {
			collMap.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
	public String printElement() {
		String varListStr = "";
		Set<String> keys = collMap.keySet();
		for (String key : keys) {
			varListStr += " " + key;
		}
		return varListStr;
	}
}
