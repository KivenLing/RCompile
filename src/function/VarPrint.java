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
	
	private String result;
	
	public VarPrint() {
		this.VarMan = VarManager.getInstance();
		this.result = "";
	}
	
	public String printVar(CommonVar var) {
		this.result = "";
		this.result += this.printCommonVar(var);
		this.result += "\n";
		System.out.print("\n");
		return this.result;
	}
	
	public String printVar(RCollection<CommonVar> var) {
		this.result = "";
		this.result += this.printCollectionVar(var);
		this.result += "\n";
		System.out.print("\n");
		return this.result;
	}
	
	public String printVar(String varName) {
		this.result = "";
		String varType = this.VarMan.varContain(varName);
		if (varType == null){
			this.result += "[1] " + varName + "\n";
			System.out.println("[1] " + varName);
		} else {
			if (varType.equals(VarManager.COMMONVAR)) {
				this.result += this.printCommonVar(this.VarMan.getCommonVar(varName));
				System.out.print("\n");
				this.result += "\n";
			} else if (varType.equals(VarManager.COLLECTION)) {
				this.result += this.printCollectionVar(this.VarMan.getCollection(varName));
				System.out.print("\n");
				this.result += "\n";
			} else {
				System.out.println("这明明是一个不可能发生的错误 ");
			}
		}
		return this.result;
	}
	
	private String printCommonVar(CommonVar var) {
		String commres = "[1] " + var.toString();
		System.out.print("[1] " + var.toString());
		return commres;
	}
	
	private String printCollectionVar(RCollection<CommonVar> var) {
		String classType = var.getClassType();
		String collres = "";
		if (classType.equals(RCollection.NULL)) {
			collres = "[1]\n";
			System.out.println("[1]");
		} else if (classType.equals(RCollection.LIST)){
			collres += this.printList((RList) var);
		} else {
			collres += this.printVector((RVector<CommonVar>) var);
		}
		return collres;
		
	}
	
	private String printVector(RVector<CommonVar> var) {
		String vecres = "";
		String[] part = var.toString().split(" ");
		int len = part.length;
		System.out.print("[1]");
		vecres += "[1]";
		for (int i = 1; i < len; i++) {
			System.out.print(" " + part[i]);
			vecres += " " + part[i];
		}
		return vecres;
	}
	
	private String printList(RList var) {
		String listres = "";
		String[] part = var.toString().split(" ");
		int len = part.length;
		for (int i = 1; i < len; i++) {
			System.out.println("[[" + i + "]]");
			listres += "[[" + i + "]]\n";
			System.out.println("[1]" + " " + part[i]);
			listres += "[1]" + " " + part[i] + "\n";
		}
		return listres;
	}
	
	public String catVar(String varList) {
		this.result = "";
		varList = varList.replaceAll(" ","");
		varList = varList.replaceAll("	","");
		String[] var = varList.split(",");
		System.out.print("[1]");
		this.result += "[1]";
		for(String varName : var) {
			String varType = this.VarMan.varContain(varName);
			if (varType == null){
				System.out.print("这个变量不存在! ");
				//EXCEPTION
			} else {
				if (varType.equals(VarManager.COMMONVAR)) {
					this.result += this.catCommonVar(this.VarMan.getCommonVar(varName));
				} else if (varType.equals(VarManager.COLLECTION)) {
					this.result += this.catCollectionVar(this.VarMan.getCollection(varName));
				} else {
					System.out.print("这明明是一个不可能发生的错误");
				}
			}
		}
		return this.result;
	}
	
	private String catCommonVar(CommonVar var) {
		String comcat = "";
		System.out.print(" " + var.toString());
		comcat = " " + var.toString();
		return comcat;
	}
	
	private String catCollectionVar(RCollection<CommonVar> var) {
		String collcat = "";
		String classType = var.getClassType();
		if (classType.equals(RCollection.NULL)) {
			return collcat;
		} else if (classType.equals(RCollection.LIST)){
			System.out.println("对List不提供支持 ");
			collcat += "\tR语言cat方法对List不提供支持\t";
		} else {
			collcat += this.catVector((RVector<CommonVar>) var);
		}
		return collcat;
	}
	
	private String catVector(RVector<CommonVar> var) {
		String veccat = "";
		String[] part = var.toString().split(" ");
		int len = part.length;
		for (int i = 1; i < len; i++) {
			System.out.print(" " + part[i]);
			veccat += " " + part[i];
		}
		return veccat;
	}
	
}
