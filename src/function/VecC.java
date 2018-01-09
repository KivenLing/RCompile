package function;

import basicvar.CommonVar;
import basicvar.VarFactory;
import collection.CollectionFactory;
import collection.RVector;
import util.SysVarList;
import varmanage.VarManager;
/**
 * 
 * @author 周文杰
 * @since 2018.01.08
 * 对R语言中C函数封装 
 */
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
	
	//C函数公有接口，接受一个string表示vector的类型，还有向量的属性值
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
	
	//其他组员另外加上，用于语法分析
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
