package function;

import basicvar.CommonVar;
import collection.RCollection;
import varmanage.VarManager;

public class VarClass {
	
	private VarManager varMan;
	
	public VarClass() {
		this.varMan = VarManager.getInstance();
	}
	
	public String Class(String varName) {
		String type = this.varMan.varContain(varName);
		if (type == null) {
			return CommonVar.CHARACTER;
		} else if (type.equals(VarManager.COLLECTION)) {
			return this.varMan.getCollection(varName).getClassType();
		} else if (type.equals(VarManager.COMMONVAR)) {
			return this.varMan.getCommonVar(varName).getClassType();
		}
		return "ewq";
	}
}
