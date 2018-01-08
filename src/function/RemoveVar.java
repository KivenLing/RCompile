package function;

import varmanage.VarManager;

public class RemoveVar {
	
	private VarManager varMan;
	
	public RemoveVar() {
		this.varMan = VarManager.getInstance();
	}
	
	public void rm(String varName) {
		String[] varList = varName.split(",");
		for (String var : varList) {
			assert this.varMan.varContain(var) != null : "找不到本对象";
			//扔异常，这个你得扔，必须得扔，不能assert，赶紧的写个exception给我。
			this.varMan.rmVar(var);
		}
	}
	
}
