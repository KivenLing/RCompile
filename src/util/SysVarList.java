package util;

import java.util.ArrayList;

public class SysVarList {
	
	private static SysVarList sysVar = null;
	
	private ArrayList<String> varList;
	
	private final String prefix = ".1";
	
	private SysVarList() {
		this.varList = new ArrayList<>();
	}
	
	public static SysVarList getInstance() {
		if (sysVar == null) {
			sysVar = new SysVarList();
		}
		return sysVar;
	}
	
	public String createVar() {
		String tempName = new String();
		tempName = this.prefix + this.varList.size();
		this.varList.add(tempName);
		return tempName;
	}

}
