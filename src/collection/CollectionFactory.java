package collection;
/**
 * @author 王启航
 * @since 2018.01.06
 * 对R的list封装
 */
import basicvar.CommonVar;
import varmanage.VarManager;

public class CollectionFactory {
	private VarManager varMan = null;
	
	public CollectionFactory() {
		this.varMan = this.varMan.getInstance(); 
	}
	
	public RCollection<CommonVar> createCollection(String name, String varType) {
		if (varType.equals(RCollection.LIST)) {
			RList listVar = new RList();
			this.addToManager(name, listVar);
			return listVar;
		} else {
			RVector<CommonVar> vectorVar = new RVector<CommonVar>();
			this.addToManager(name, vectorVar);
			return vectorVar;
		}
	}

	public RCollection<CommonVar> createCollection(String varType) {
		if (varType.equals(RCollection.LIST)) {
			RList listVar = new RList();
			return listVar;
		} else {
			RVector<CommonVar> vectorVar = new RVector<CommonVar>();
			return vectorVar;
		}
	}
	
	private void addToManager(String name, RCollection<CommonVar> var) {
		varMan.addCollVar(name, var);
	}
}
