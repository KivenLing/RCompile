package exception;

public class WordException extends Exception{

	public WordException(String message) {
		super("不期望出现的符号："+message);
		// TODO Auto-generated constructor stub
	}	
}
