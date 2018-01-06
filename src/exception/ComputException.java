package exception;

/**
 * 
 * @author 陈文儒
 * data:2018.01.06
 * 运算异常类
 */
@SuppressWarnings("serial")
public class ComputException extends Exception{

	public ComputException(String message) {
		super("意外的错误出现在： "+message);
		// TODO Auto-generated constructor stub
	}
	
}
