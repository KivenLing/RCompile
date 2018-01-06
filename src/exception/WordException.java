package exception;

/**
 * 
 * @author 陈文儒
 * data:2018.01.06
 * 词法分析异常类
 */
@SuppressWarnings("serial")
public class WordException extends Exception{
	public WordException(String message) {
		super("不期待出现的单词：" + message);
	}
}
