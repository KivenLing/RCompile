package exception;

/**
 * 
 * @author 王启航
 * @since 2018.01.09
 */
public class IlleagelDiagrameSyntaxException extends Exception{
	
	public IlleagelDiagrameSyntaxException(String message) {
		super("错误的参数："+message);
	}

}
