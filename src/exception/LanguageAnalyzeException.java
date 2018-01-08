package exception;

public class LanguageAnalyzeException extends Exception{

	public LanguageAnalyzeException(String message) {
		super("语法错误： "+message);
		// TODO Auto-generated constructor stub
	}
	
}
