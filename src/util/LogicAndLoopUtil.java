package util;
/**
 * 
 * @author 陈文儒
 * data:2018.01.06
 * 逻辑判断所需要的静态工具
 * 为静态函数
 */
public class LogicAndLoopUtil {
	/*
	 *列出静态不能变的的关键字
	 * 以及符号
	 */
	public static final String IF = "if";
	public static final String ELSE = "else";
	public static final String BREAK = "break";
	public static final String NEXT = "next";
	public static final String WHILE = "while";
	public static final String IN = "in";
	public static final String FOR = "for";
	public static final String REPEAT = "repeat";
	public static final String LFETSB = "(";
	public static final String RIGHTSB = ")";
	public static final String LEFTLB = "{";
	public static final String RIGHTLB = "}";
	public static final String STRUCTKEYWORD = "strucKeyWord";
	private LogicAndLoopUtil() {}
	/*
	 * 定义一些判断函数
	 */
	public static boolean isIF(String s) {
		return IF.equals(s);
	}
	
	
	public static boolean isElse(String s) {
		return ELSE.equals(s);
	}
	public static boolean isBreak(String s) {
		return BREAK.equals(s);
	}
	
	public static boolean isRepeat(String s) {
		return REPEAT.equals(s);
	}
	
	public static boolean isLeftSB(String s) {
		return LFETSB.equals(s);
	}
	
	public static boolean isRightSB(String s) {
		return RIGHTSB.equals(s);
	}
	
	public static boolean isLeftLB(String s) {
		return LEFTLB.equals(s);
	}
	
	public static boolean isRightLB(String s) {
		return RIGHTLB.equals(s);
	}
}
