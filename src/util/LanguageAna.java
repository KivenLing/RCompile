package util;

import java.util.ArrayList;
import java.util.List;

import basicvar.CommonVar;
import basicvar.RBoolean;
import basicvar.RNumeric;
import exception.ComputException;
import exception.SyntaxException;
import function.TableFunc;
import varmanage.VarManager;

/**
 * 
 * @author 陈文儒、凌凯
 * data:2018.01.06
 * 语法语义分析：完成部分运算、分支、循环、函数调用
 */
public class LanguageAna {
	//每一行的下标
	private int whichWord = 0;
	//行的下标
	private int whichLine = 0;
	//词法分析结果
	private List<WordMean> line;
	//每一行的暂存
	private WordMean words;
	//引用单例表
	private TableFunc onlyBool = TableFunc.getOnlyBool();
	//引用单例变量管理
	private VarManager varManager = VarManager.getInstance();
	//将构造器私有化
	private LanguageAna(){}
	//生成单例
	private static LanguageAna la = new LanguageAna();
	//获取单例
	public static LanguageAna getLa() {
		return la;
	}

	//输入词法分析结果
	public void setWords(List<WordMean> line) { 
		this.line = line;
		whichWord = 0;
	}
	
	 //获取下一个词法单元
	private Token getNextToken() {
		if(whichWord >= words.getWord().size()) {
			whichWord = 0;
			return null;
		}
		whichWord++;
		return words.getWord().get(whichWord-1);
	}
	
	//语法的主控（按行分析）
	public void begin() throws ComputException { 
		while(whichLine<line.size()) {
			words = line.get(whichLine);
			whichWord = 0;
			commonBegin();
			whichLine++;
		}
	}
	
	 //语法分析的一行的开始
	public void commonBegin() throws ComputException {
		Token token = getNextToken();
		//判断是不是变量或者关键字
		if(token.getWordMean().equals(CommonVar.COMMONVAR)) { 
			
			//判断是否为关键字
			if(false) {  
				// 是 转到关键字处理
			} else {
				//不是关键字，是变量传到运算处理
				mathOpreate(); 
			}
		} else if(isMath(token.getWordMean())) { 
			//数字开头转到数字处理
			mathOpreate();
		} else if(errorBegin(whichWord, token.getWordMean(), token.getWord())) {
			//错误处理机制
			System.out.println(token.getWord()+"错误");
			return;
		} 
	}
	
	//判断是否为错误的开始
	private boolean errorBegin(int whichWord, String wds, String word) { 
		return (whichWord==0 && wds.equals("symbol") && !(word.equals("+")||word.equals("-")));
	}
	
	//运算操作
	private String mathOpreate() throws ComputException {
		Token token;
		List<String> valueStack = new ArrayList<String>(); //变量栈
		List<String> valueMean = new ArrayList<String>(); //对应变量的意思
		List<String> symbolStack = new ArrayList<String>(); //符号栈
		while((token = getNextToken()) != null) {
			if(token.getWordMean().equals(CommonVar.COMMONVAR)) {
				String var = token.getWord();
				//判断是否为关键字
				if(false) { 
					//var = hanshu(); //关键字处理
				} else { 
					//变量处理
					valueStack.add(var);
					valueMean.add(CommonVar.VAR);
				}
			} else if(isMath(token.getWord())) {
				 //数字处理
				valueStack.add(token.getWord());
				valueMean.add(token.getWordMean());
			} else if(token.getWordMean().equals("symbol")) { 
				//符号处理
				String wd = token.getWord();
				Token next = getNextToken();
				if(next.getWordMean().equals("symbol")) { 
					//判断下面是不是连续符号
					String symbolTemp = next.getWord();
					next = getNextToken();
					//收集连续符号
					while(next.getWordMean().equals("symbol")) { 
						symbolTemp += ("`"+next.getWord());
						next = getNextToken();
					}
					//处理连续符号,并入栈
					valueStack.add(ContSymbol(symbolTemp, next.getWord()));
					whichWord++;
				}
				
				//恢复上个状态，上一步处理无论哪种情况都会导致多读一次
				whichWord--;
				
				//如果符号栈为空，那么让符号入栈
				if(symbolStack.isEmpty()) { 
					symbolStack.add(wd);
				} else if(onlyBool.getPrio(symbolStack.get(symbolStack.size()-1)) >= onlyBool.getPrio(wd)) {
					// 判断优先级，进行处理
					String sym = symbolStack.get(symbolStack.size()-1);
					if(wd.equals("(")) {
						symbolStack.add(wd);
						continue;
					}
					symbolStack.remove(symbolStack.size()-1);
					if(sym.equals("(") && wd.equals(")")) {
						continue;
					} 
					//优先级低的入栈
					symbolStack.add(wd);
					
					//没有特殊情况 下面进行处理
					//变量出栈+操作
					String num = valueStack.get(-1);
					String type = judgeType(num,valueMean.get(-1));
					//出栈
					valueMean.remove(-1);
					valueStack.remove(-1);
					
					if(type.equals(varManager.COLLECTION)) {
						
					} else {
						CommonVar cnum1;
						CommonVar cnum2;
						if(varManager.getCommonVar(num) != null) {
							cnum1 = varManager.getCommonVar(num);
						} else {
							cnum1 = new RNumeric(Double.parseDouble(num));
							num = valueStack.get(-1);
							if(varManager.getCommonVar(num) != null) {
								cnum2 = varManager.getCommonVar(num);
							} else {
								cnum2 = new RNumeric(Double.parseDouble(num));
							}
							operate(sym, cnum2, cnum1);
						}
						//commonvar操作
					}
					//出栈
					valueMean.remove(-1);
					valueStack.remove(-1);

					
				} else {
					// 判断优先级，进行处理
					symbolStack.add(wd);
				}
			} else {
				//没有处理机制，报错
				throw new ComputException(token.getWord());
			}
		}
		//返回可能需要用到的结果，如果是赋值将会在上一步返回空
		return null; 
	}
	
	//变量运算
	private String operate(String sym, CommonVar cvar1, CommonVar cvar2) {
		
		return null;
	}
	
	
	//从commvar里面获取值
	/*private String getVar(CommonVar comvar) {
		String classType = comvar.getClassType();
		CommonVar.NUMERIC
		return ;
	}*/
	
	//判断collection或者commonvar类型
	private String judgeType(String num1, String mean) throws ComputException {
		if(mean.equals(CommonVar.VAR)){
			if(varManager.varContain(num1).equals(varManager.COMMONVAR)) {
				return varManager.COMMONVAR;
			} else if(varManager.varContain(num1).equals(varManager.COLLECTION)) {
				return varManager.COLLECTION;
			} else {
				throw new ComputException("\""+num1+"\""+"无法被识别");
			}
		} 
		return "notvar";
	}
	
	//连续符号的处理
	private String ContSymbol(String words, String var) throws ComputException {
		//统计减号个数
		int count = 0;
		//合法的连续字符
		final String LEGAL = "[-+]-*{1,}";
		if(!words.matches(LEGAL)) {
			throw new ComputException(words+var);
		}
		String[] splitSymbol = words.split("`");
		for(int i=0; i<splitSymbol.length; i++) {
			if(splitSymbol[i].equals("-")) {
				count++;
			}
		}
		if(count%2 == 1) {
			var = "-"+var;
		}
		return var;
	}
	
	//判断是否为数字
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
	/*
	 * 分支语句的处理
	 */
	private void logicalStruct() throws SyntaxException, ComputException {
		Token curToken = this.getNextToken();
		//当前的符号不符合语法要求，if后一定跟小括号“(”
		if(curToken == null || !LogicAndLoopUtil.isLeftSB(curToken.getWord()))
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		//维护一行的索引
		whichWord--;
		String result = mathOpreate();
		if(!result.equals(RBoolean.TRUE) && !result.equals(RBoolean.FALSE))
			throw new SyntaxException("在第" + (this.whichLine + 1) + "行" + "if的参数不是逻辑值！");
		//转入下一行
		if(!this.goNextLine())
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		this.clearIndexOfWordMean();
		curToken = this.getNextToken();
		if(LogicAndLoopUtil.isLeftLB(curToken.getWord())) {//说明程序有多行用"{}"包括
			//那么找到整个block
			//int startLine = this.whichLine;
			PairOfEndMess endMess = this.getCurBlock();
			if(result.equals(RBoolean.TRUE)) {//r的if判断是true
				//当whichLine = endMess.getIndexOfLine()退出循环
				//即whichLine指向block结束那一行
				while(whichLine < endMess.getIndexOfLine()) {
					commonBegin();
					this.whichLine++;
				}
				whichWord = endMess.getIndexOfToken();
				//此时索引值指向语句块结束的标志“}”下一个位置
			}else{//判断为false，直接忽略中间部分
				whichLine = endMess.getIndexOfLine();
				whichWord = endMess.getIndexOfToken();
				//此时索引值指向语句块结束的标志“}”下一个位置
			}
			curToken = this.getNextToken();
			
		}
	}
	//维护语句的索引
	private void clearIndexOfWordMean() {
		whichWord = 0;
	}
	
	//到语句下一行
	//若已经最后一行，那么whichLine索引不变，并且返回false
	private boolean goNextLine() {
		whichLine++;
		if(whichLine >= line.size()) {
			whichLine--;
			return false;
		}
			
		return true;
	}
	//获取多行的语句块，大括号包围的，返回值是Map，key值表示唯一标识的结束在哪一行，value表示}在哪个位置
	private PairOfEndMess getCurBlock() throws SyntaxException {
		int curIndexOfWM = whichWord;
		int curIndexOfLine = whichLine;
		int flag = 1;//表示标志
		//flag标志以及语句读到最后一行结束
		while(flag > 0 && whichLine < line.size()) {
			Token tempToken = this.getNextToken();
			if(tempToken == null) {//行末尾
				if(!this.goNextLine()) //转下一行失败
					throw new SyntaxException("在第" + (this.whichLine + 1) + "行" + "缺少token\"}\"");
				tempToken = this.getNextToken();
			}
			if(LogicAndLoopUtil.isLeftLB(tempToken.getWord()))//匹配到{
				flag++;
			else if(LogicAndLoopUtil.isRightLB(tempToken.getWord()))//匹配到}
				flag--;
		}
		if(flag > 0)//缺少}
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		PairOfEndMess endMess = new PairOfEndMess(whichWord, whichLine);
		whichWord = curIndexOfWM;
		whichLine = curIndexOfLine;
		return endMess;
	}
	//封装语句块结束的信息
	private class PairOfEndMess{
		private int indexOfToken;
		private int indexOfLine;
		
		public PairOfEndMess(int indexOfToken, int indexOfLine) {
			this.indexOfToken = indexOfToken;
			this.indexOfLine = indexOfLine;
		}

		public int getIndexOfToken() {
			return indexOfToken;
		}

		public int getIndexOfLine() {
			return indexOfLine;
		}
		
		
	}
}