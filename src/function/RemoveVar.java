package function;

import exception.VariableNotFoundException;
import varmanage.VarManager;
/**
 * 
 * @author 薛浩
 * @since 2018.01.09
 * 对R的rm函数的封装
 */
public class RemoveVar {
	
	private VarManager varMan;
	
	public RemoveVar() {
		this.varMan = VarManager.getInstance();
	}
	
	public void rm(String varName) throws VariableNotFoundException {
		String[] varList = varName.split(",");
		for (String var : varList) {
			if (this.varMan.varContain(var) == null) {
				throw new VariableNotFoundException(var);
			} else {
				this.varMan.rmVar(var);
			}
		}
	}
	
}
