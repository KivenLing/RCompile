package exception;

@SuppressWarnings("serial")
public class WordException extends Exception{
	public WordException(String message) {
		super("不期待出现的单词：" + message);
	}
}
