package function;
/**
 * 
 * @author 周文杰
 * @since 2018.01.07
 * 封装R语言中的paste
 *
 */

import basicvar.RChar;
import collection.RVector;
import varmanage.VarManager;

public class VarPaste {
	private VarManager VarMan = null;
	
	public VarPaste() {
		this.VarMan = VarManager.getInstance();
	}
	
	public String RPaste(String varList) {
		if(varList.indexOf("collapse") != -1) {
			int indexsep = varList.indexOf("sep");
			String remainstr = varList.substring(0, indexsep);
			return Rpaste3(remainstr);
		}
		else if(varList.indexOf("sep") != -1) {
			String tempstr = varList.replaceAll(" ", "");
			int start = varList.indexOf("sep");
			String symbol = varList.substring(start+4);
			String remainstr = tempstr.substring(0, start);
			return Rpaste2(remainstr, symbol);
		}
		else
			return Rpaste1(varList);

	}
	
	//只含有第一参数时的调用
	private String Rpaste1(String varList) {
		varList = varList.replaceAll(" ", "");
		varList = varList.replaceAll("  ", "");
		String charpaste = "";
		String[] var = varList.split(",");
		for(String varName : var) {
			if(!VarMan.varContain(varName).equals(null)) {
				RChar tempchar = (RChar) VarMan.getCommonVar(varName);
				charpaste += tempchar.getContent();
				charpaste += " ";
			}
		}
		return charpaste;
	}
	
	//含有第一和第二参数时的调用
	private String Rpaste2(String varList, String symbol) {
		varList = varList.replaceAll(" ", "");
		varList = varList.replaceAll("  ", "");
		String charpaste = "";
		String[] var = varList.split(",");
		for(String varName : var) {
			if(!VarMan.varContain(varName).equals(null)) {
				RChar tempchar = (RChar) VarMan.getCommonVar(varName);
				charpaste += tempchar.getContent();
				charpaste += symbol;
			}
		}
		return charpaste;
	}
	
	//含有三个参数时的调用
		private String Rpaste3(String varList) {
			varList = varList.replaceAll(" ", "");
			varList = varList.replaceAll("  ", "");
			String charpaste = "";
			String[] var = varList.split(",");
			for(String varName : var) {
				if(!VarMan.varContain(varName).equals(null)) {
					RChar tempchar = (RChar) VarMan.getCommonVar(varName);
					charpaste += tempchar.getContent();
				}
			}
			return charpaste;
		}
		
	//paste在R语言第一个参数用RVector传进来，缺省sep和collapse
	public RChar RPaste(RVector<RChar> rVector) {
		int size = rVector.sizeof();
		String tempstr = "";
		for(int i = 0; i<size; i++ ) {
			tempstr += rVector.getElement(i).getContent();
			tempstr += " ";
		}
		RChar retchar = new RChar(tempstr);
		return retchar;
	}
	
	//paste在R语言第一个参数用RVector传进来，缺省collapse
	public RChar RPaste(RVector<RChar> rVector, String sep) {
		int size = rVector.sizeof();
		String tempstr = "";
		for(int i = 0; i<size; i++ ) {
			tempstr += rVector.getElement(i).getContent();
			tempstr += sep;
		}
		RChar retchar = new RChar(tempstr);
		return retchar;
	}
	
	//paste在R语言第一个参数用RVector传进来
		public RChar RPaste(RVector<RChar> rVector, String sep, String collapse) {
			int size = rVector.sizeof();
			String tempstr = "";
			for(int i = 0; i<size; i++ ) {
				tempstr += rVector.getElement(i).getContent();
			}
			RChar retchar = new RChar(tempstr);
			return retchar;
		}
}
