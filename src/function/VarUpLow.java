package function;

import basicvar.RChar;
import varmanage.VarManager;
/**
 * 
 * @author 周文杰
 * @since 2018.01.07
 * 封装R语言中的toupper和tolower
 *
 */
public class VarUpLow {
	private VarManager varMan = null;
	
	public  VarUpLow() {
		this.varMan = VarManager.getInstance();
	}
	
	public String Rtoupper(String varName) {
		String type = varMan.varContain(varName);
		if (type == null) {
			return varName.toUpperCase();
		} else if (type.equals(VarManager.COLLECTION)){
			return "";
		} else {
			RChar rChar = (RChar) varMan.getCommonVar(varName);
			String str = rChar.getContent();
			str = str.toUpperCase();
			return str;
		}
	}
	
	public String Rtolower(String varName) {
		String type = varMan.varContain(varName);
		if (type == null) {
			return varName.toLowerCase();
		} else if (type.equals(VarManager.COLLECTION)){
			return "";
		} else {
			RChar rChar = (RChar) varMan.getCommonVar(varName);
			String str = rChar.getContent();
			str = str.toLowerCase();
			return str;
		}
	}
}
