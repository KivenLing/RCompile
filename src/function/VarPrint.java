package function;

import basicvar.CommonVar;
import collection.RCollection;
import collection.RList;
import collection.RVector;
import varmanage.VarManager;

/**
 * @author 王启航
 * @since 2018.01.04
 * 封装R语言功能中的print和cat
 * 两个函数，以便调用。
 */
public class VarPrint {
	private VarManager VarMan = null;
	
	public VarPrint() {
		this.VarMan = VarManager.getInstance();
	}
	
	public void printVar(String varName) {
		String varType = this.VarMan.varContain(varName);
		if (varType == null){
			System.out.println("这个变量就不存在 ");
		} else {
			if (varType.equals(VarManager.COMMONVAR)) {
				this.printCommonVar(this.VarMan.getCommonVar(varName));
				System.out.print("\n");
			} else if (varType.equals(VarManager.COLLECTION)) {
				this.printCollectionVar(this.VarMan.getCollection(varName));
				System.out.print("\n");
			} else {
				System.out.println("这明明是一个不可能发生的错误 ");
			}
		}
	}
	
	private void printCommonVar(CommonVar var) {
		System.out.print("[1] " + var.toString());
	}
	
	private void printCollectionVar(RCollection<CommonVar> var) {
		String classType = var.getClassType();
		if (classType.equals(RCollection.NULL)) {
			System.out.println("[1]");
		} else if (classType.equals(RCollection.LIST)){
			this.printList((RList) var);
		} else {
			this.printVector((RVector<CommonVar>) var);
		}
		
	}
	
	private void printVector(RVector<CommonVar> var) {
		String[] part = var.toString().split(" ");
		int len = part.length;
		System.out.print("[1]");
		for (int i = 1; i < len; i++) {
			System.out.print(" " + part[i]);
		}
	}
	
	private void printList(RList var) {
		String[] part = var.toString().split(" ");
		int len = part.length;
		for (int i = 1; i < len; i++) {
			System.out.println("[[" + i + "]]");
			System.out.println("[1]" + " " + part[i]);
		}
	}
	
	public void catVar(String varList) {
		varList = varList.replaceAll(" ","");
		varList = varList.replaceAll("	","");
		String[] var = varList.split(",");
		System.out.print("[1]");
		for(String varName : var) {
			String varType = this.VarMan.varContain(varName);
			if (varType == null){
				System.out.print("这个变量不存在! ");
			} else {
				if (varType.equals(VarManager.COMMONVAR)) {
					this.catCommonVar(this.VarMan.getCommonVar(varName));
				} else if (varType.equals(VarManager.COLLECTION)) {
					this.catCollectionVar(this.VarMan.getCollection(varName));
				} else {
					System.out.print("这明明是一个不可能发生的错误");
				}
			}
		}
	}
	
	private void catCommonVar(CommonVar var) {
		System.out.print(" " + var.toString());
	}
	
	private void catCollectionVar(RCollection<CommonVar> var) {
		String classType = var.getClassType();
		if (classType.equals(RCollection.NULL)) {
			return;
		} else if (classType.equals(RCollection.LIST)){
			System.out.println("对List不提供支持 ");
		} else {
			this.catVector((RVector<CommonVar>) var);
		}
	}
	
	private void catVector(RVector<CommonVar> var) {
		String[] part = var.toString().split(" ");
		int len = part.length;
		for (int i = 1; i < len; i++) {
			System.out.print(" " + part[i]);
		}
	}
	
}
