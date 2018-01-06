package exception;
/**
 * 
 * @author 凌凯
 * data:2018.01.06
 * 语法分析中的异常类
 */
@SuppressWarnings("serial")
public class SyntaxException extends Exception{

	public SyntaxException(String message) {
		super(message + " has an error!");
	}
}
