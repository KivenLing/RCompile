package util;

public class Token {
	private String word;
	private String wordMean;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getWordMean() {
		return wordMean;
	}
	public void setWordMean(String wordMean) {
		this.wordMean = wordMean;
	}
	public Token(String word, String wordMean) {
		super();
		this.word = word;
		this.wordMean = wordMean;
	}
	@Override
	public String toString() {
		return "Token [word=" + word + ", wordMean=" + wordMean + "]";
	}
	
}
