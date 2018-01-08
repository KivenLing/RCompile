package function;

import basicvar.CommonVar;
import basicvar.VarFactory;
import collection.CollectionFactory;
import collection.RCollection;
import collection.RList;
import collection.RVector;
import util.SysVarList;
import varmanage.VarManager;

public class ListL {
	
	private VarManager varMan;
	
	private VarFactory varFac;
	
	private SysVarList sysVar;
	
	private CollectionFactory collFac;
	
	public ListL() {
		this.varMan = VarManager.getInstance();
		this.sysVar = SysVarList.getInstance();
		this.varFac = new VarFactory();
		this.collFac = new CollectionFactory();
	}
	
	public String list(String Value, String classType) {
		String[] vlList = Value.split(",");
		String[] tpList = classType.split(",");
		int size = vlList.length;
		String varName = sysVar.createVar();
		assert size == tpList.length : "紧急！长度未匹配";

		RList tempList = (RList) this.collFac.createCollection(varName, RCollection.LIST);

		int i;
		for (i = 0; i < size; i++) {
			tempList.addElement(varFac.createVar(tpList[i], vlList[i]));
		}
		return varName;
	}
	
}
