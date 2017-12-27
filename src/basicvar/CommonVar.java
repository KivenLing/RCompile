package basicvar;
/*
 * R基本变量的抽象类
 */
public abstract class CommonVar {
	public static final String COMPLEX = "complex";
	public static final String NUMERIC = "numeric";
	public static final String LOGICAL = "logical";
	public static final String INTEGER = "integer";
	public static final String CHARACTER = "character";
	String classType;
	
	public String getClassType() {
		return classType;
	}
}
