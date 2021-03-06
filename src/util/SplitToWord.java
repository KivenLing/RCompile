package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import basicvar.CommonVar;
import exception.WordException;
import function.TableFunc;

public class SplitToWord {
	//单例
	private TableFunc onlyBool = TableFunc.getOnlyBool();
	private SplitToWord(){}
	private static SplitToWord onlySplWord = new SplitToWord();
	public static SplitToWord getOnlySplWord() {
		return onlySplWord;
	}
	
	//处理函数，并抛出自定义异常
	public WordMean handel(String[] arrayLine) throws WordException {
		List<Token> wordAnaliTmp = new ArrayList<Token>();
		WordMean result = new WordMean();
		String word=arrayLine[0];
		for(int i=1; i<arrayLine.length; i++) {
			if(onlyBool.ifexit(arrayLine[i])) {
				if(!word.equals("")) {
					wordAnaliTmp.add(new Token(word,whatType(word)));
				}
				word = "";
				if(arrayLine[i].equals("\'") || arrayLine[i].equals("\"")) {
					String strSymbol = arrayLine[i];
					i++;
					while(!arrayLine[i].equals(strSymbol)) {
						word += arrayLine[i];
						i++;
					}
					wordAnaliTmp.add(new Token(word,CommonVar.CHARACTER));
					word="";
					continue;
				}
				while(onlyBool.ifexit(word)) {
					if(i >= arrayLine.length) {
						word += "q";
						i++;
						break;
					}
					word += arrayLine[i];
					i++;
				}
				i-=2;
				word = word.substring(0, word.length()-1);
				wordAnaliTmp.add(new Token(word,"symbol"));
				word="";
			} else {
				if(word.equals("else")) {
					wordAnaliTmp.add(new Token(word,whatType(word)));
					word = "";
				}
				word += arrayLine[i];
			}
		}
		if(!word.equals("")) {
			wordAnaliTmp.add(new Token(word,whatType(word)));
		}   
		result.setWord(wordAnaliTmp);
		return result;
	}

	//判断类型
	private String whatType(String word) throws WordException {
		final String VALUES = "[a-zA-Z_](_{0,1}[a-zA-z0-9]){0,}";
		final String HIDVALUSE = "\\.[a-zA-Z_]{1,}_{0,1}[a-zA-z0-9]-*";
		//final String NUMBERS = "[0-9]-*{1,}(.[0-9]-*){0,1}[0-9]{0,}";
		final String NUMBERS = "^\\d+(\\.\\d+)?$";
		final String INTEGER = "[0-9]-*{1,}L";
		final String COMPLEX = "[0-9]-*{1,}(.[0-9]-*){0,1}[0-9]{0,}i";
		String typeResult="";
		if(word.matches(NUMBERS)) {
			typeResult = CommonVar.NUMERIC;
		} else if(word.matches(HIDVALUSE)) {
			typeResult = CommonVar.HIDEVAR;
		} else if(onlyBool.ifexit(word)) {
			typeResult = "symbol";
		} else if(word.matches(INTEGER)) {
			typeResult = CommonVar.INTEGER;
		} else if(word.equals("TRUE")||word.equals("FALSE")) { 
			typeResult = CommonVar.LOGICAL;
		} else if(word.matches(COMPLEX)) {
			typeResult = CommonVar.COMPLEX;
		} else if(word.matches(VALUES)) {
			typeResult = CommonVar.COMMONVAR;
		} else {
			throw new WordException(word);
		} 
		return typeResult;
	}
}
