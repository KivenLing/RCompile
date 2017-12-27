package basicvar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VarFactory {
	
	public CommonVar createVar(String name, String varType, String value) {//throws Exception {
		if (varType.equals(CommonVar.CHARACTER)) {
			CommonVar rCharVar = createChar(value);
			return rCharVar;
		}else if(varType.equals(CommonVar.COMPLEX)) {
			CommonVar rComplex = createComplex(value);
			return rComplex;
		}else if(varType.equals(CommonVar.INTEGER)) {
			CommonVar rInteger = createInteger(value);
			return rInteger;
		}else if(varType.equals(CommonVar.LOGICAL)) {
			CommonVar rLogical = createLogical(value);
			return rLogical;
		}else if (varType.equals(CommonVar.NUMERIC)) {
			CommonVar rNumric = createNumeric(value);
			return rNumric;
		}else {
			//throw new Exception("变量类型不存在");
			return null;
		}
	}
	
	private RChar createChar(String value) {
		RChar rCharVar = new RChar(value);
		return rCharVar;
	}
	
	private RBoolean createLogical(String value) {
		RBoolean rLogiVar = null;
		if(value.equals("TRUE")) {
			rLogiVar = new RBoolean(true);
		}else {
			rLogiVar = new RBoolean(false);
		}
		return rLogiVar;
	}
	
	//未完成
	private RComplex createComplex(String value) {
		RComplex rCompVar = null;
		value = value.trim();
		String patternStr1 = ".+\\+.+i";
		Pattern patt = Pattern.compile(patternStr1);
		Matcher match = patt.matcher(value);
		if (match.find()) {
			String[] synax = value.split("\\+");
			synax[1] = synax[1].substring(0, synax[1].length() - 1);
			rCompVar = new RComplex(Double.parseDouble(synax[0]), 
					Double.parseDouble(synax[1]));
		} else {
			String[] synax = value.split("\\-");
			synax[1] = "-" + synax[1].substring(0, synax[1].length() - 1);
			rCompVar = new RComplex(Double.parseDouble(synax[0]), 
					Double.parseDouble(synax[1]));
		}
		return rCompVar;
	}
	
	private RNumeric createNumeric(String value) {
		double val = Double.parseDouble(value);
		RNumeric rNumric = new RNumeric(val);
		return rNumric;
	}
	
	private RInteger createInteger(String value) {
		int length = value.length();
		int val = Integer.parseInt(value.substring(0, length - 1));
		return new RInteger(val);
	}
	
}
