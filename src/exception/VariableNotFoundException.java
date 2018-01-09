package exception;

/**
 * 
 * @author 王启航
 * @since 2018.01.09
 */
public class VariableNotFoundException extends Exception {
	
	public VariableNotFoundException(String message) {
		super("无法找到的变量："+message);
	}	

}
