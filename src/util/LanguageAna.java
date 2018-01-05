package util;

import java.util.ArrayList;
import java.util.List;

import basicvar.CommonVar;
import function.TableFunc;

public class LanguageAna {
	private int whichWord = 0;
	private int whichLine = 0;
	private List<WordMean> line;
	private WordMean words;
	private TableFunc onlyBool = TableFunc.getOnlyBool();
	private LanguageAna(){}
	private static LanguageAna la = new LanguageAna();
	public static LanguageAna getLa() {
		return la;
	}

	public void setWords(List<WordMean> line) {
		this.line = line;
		whichWord = 0;
	}
	private Token getNext() {
		if(whichWord >= words.getWord().size()) {
			return null;
		}
		whichWord++;
		return words.getWord().get(whichWord);
	}
	public void begin() {
		while(whichLine<line.size()) {
			words = line.get(whichLine);
			whichWord = 0;
			commonBegin();
			whichLine++;
		}
	}
	public void commonBegin() {
		Token token = getNext();
		if(token.getWordMean().equals(CommonVar.COMMONVAR)) {
			if(false) { //判断是否为关键字 
				//
			} else {
				mathOpreate();
			}
		} else if(isMath(token.getWordMean())) {
			mathOpreate();
		} else if(errorBegin(whichWord, token.getWordMean(), token.getWord())) {
			System.out.println(token.getWord()+"错误");
			return;
		} 
	}
	private boolean errorBegin(int whichWord, String wds, String word) {
		return (whichWord==0 && wds.equals("symbol") && !(word.equals("+")||word.equals("-")));
	}
	private String mathOpreate() {
		Token token;

		List<String> valueStack = new ArrayList<String>();
		List<String> valueMean = new ArrayList<String>();
		List<String> symbolStack = new ArrayList<String>();
		while((token = getNext()) != null) {
			if(token.getWordMean().equals(CommonVar.COMMONVAR)) {
				String var = token.getWord();
				if(false) { //判断是否为关键字
					//ar = hanshu(); //关键字处理
				} else { //变量处理
					valueStack.add(var);
					valueMean.add(CommonVar.VAR);
				}
			} else if(isMath(token.getWord())) { //数字处理
				valueStack.add(token.getWord());
				valueMean.add(token.getWordMean());
			} else if(token.getWordMean().equals("symbol")) { //符号处理
				String wd = token.getWord();
				Token next = getNext();
				if(next.getWordMean().equals("symbol")) { //判断下面是不是连续符号
					String symbolTemp = next.getWord();
					next = getNext();
					while(next.getWordMean().equals("symbol")) {
						symbolTemp += ("`"+next.getWord());
						next = getNext();
					}
					ContSymbol(symbolTemp.split("`"),next.getWord()); //处理连续符号
				}
				
				//恢复上个状态，上一步处理无论哪种情况都会导致多读一次
				whichWord--;
				
				if(symbolStack.isEmpty()) {
					symbolStack.add(wd);
				} else if(onlyBool.getPrio(symbolStack.get(symbolStack.size()-1)) >= onlyBool.getPrio(wd)) {
					symbolStack.get(symbolStack.size()-1);
					symbolStack.remove(symbolStack.size()-1);
					continue;
				} else {
					symbolStack.add(wd);
				}
			} else {
				System.out.println("erro");
			}
			whichWord++;
		}
		return null;
	}
	private void ContSymbol(String[] split, String var) {
	}

	private boolean isMath(String word) {
		boolean result = false;
		if(word.equals(CommonVar.NUMERIC)) {
			result = true;
		} else if(word.equals(CommonVar.HIDEVAR)) {
			result = true;
		} else if(word.equals(CommonVar.INTEGER)) {
			result = true;
		} else if(word.equals(CommonVar.COMPLEX)) {
			result = true;
		}
		return result;
	}
}