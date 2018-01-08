package exception;

public class ComputException extends Exception{

	public ComputException(String message) {
		super("意外的错误出现在： "+message);
		// TODO Auto-generated constructor stub
	}
	
}
