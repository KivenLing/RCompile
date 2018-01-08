package function;

import basicvar.CommonVar;
import basicvar.VarFactory;
import collection.CollectionFactory;
import collection.RCollection;
import collection.RList;
import collection.RVector;
import util.SysVarList;
import varmanage.VarManager;

public class VecC {
	
	private VarManager varMan;
	
	private VarFactory varFac;
	
	private CollectionFactory collFac;
	
	private SysVarList sysVar;
	
	public VecC() {
		this.varMan = VarManager.getInstance();
		this.sysVar = SysVarList.getInstance();
		this.varFac = new VarFactory();
		this.collFac = new CollectionFactory();
	}
	
	public String c(String Value, String classType) {
		String[] vlList = Value.split(",");
		int size = vlList.length;
		String name = this.sysVar.createVar();
		RVector<CommonVar> tmpRVec = (RVector<CommonVar>) this.collFac.createCollection(name, "");
		int i;
		for (i = 0; i < size; i++) {
			tmpRVec.addElement(varFac.createVar(classType, vlList[i]));
		}
		return name;
	}
	public RVector<CommonVar> createVector(String[] vlList, String vType) {
		int size = vlList.length;
		RVector<CommonVar> tmpRVec = (RVector<CommonVar>) this.collFac.createCollection("");
		int i;
		for (i = 0; i < size; i++) {
			tmpRVec.addElement(varFac.createVar(vType, vlList[i]));
		}
		return tmpRVec;
	}
	
}
