package function;

import basicvar.CommonVar;
import basicvar.VarFactory;
import varmanage.VarManager;

public class ListVar {
	
	private VarManager varMan;
	
	private VarPrint printer;
	
	private VarFactory varFac;
	
	public ListVar() {
		this.varMan = VarManager.getInstance();
		this.printer = new VarPrint();
		this.varFac = new VarFactory();
	}
	
	public String ls() {
		String Answer = varMan.printVar();
		return this.printer.printVar(this.varFac.createVar(CommonVar.CHARACTER, Answer));
	}
	
	public String ls(String param) {
		String Answer = varMan.printVar(true);
		return this.printer.printVar(this.varFac.createVar(CommonVar.CHARACTER, Answer));
	}

}
