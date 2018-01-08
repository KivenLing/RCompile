package util;

import java.util.List;
import java.util.Stack;

import org.jfree.data.Value;

import basicvar.*;
import collection.RCollection;
import collection.RVector;
import exception.ComputException;
import exception.SyntaxException;
import function.FuncConstant;
import function.ListL;
import function.ListVar;
import function.RemoveVar;
import function.TableFunc;
import function.VarClass;
import function.VarPaste;
import function.VarPrint;
import function.VarUpLow;
import function.VecC;
import table.DrawDiagram;
import varmanage.VarManager;

/**
 * 
 * @author 陈文儒、凌凯 ，王启航
 * data:2018.01.06 
 * 语法语义分析：完成部分运算、分支、循环、函数调用
 * 陈文儒完成运算赋值的基础部分
 * 凌凯完成分支，循环，以及break语句部分
 * 王启航完成系统函数调用
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
	//循环的语句结束位置的栈
	private Stack<PairOfEndMess> loopStack = new Stack<PairOfEndMess>();
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
	public void begin() throws ComputException, SyntaxException { 
		while(whichLine < line.size()) {
			words = line.get(whichLine);
			whichWord = 0;
			commonBegin();
			whichLine++;
		}
	}
	
	 //语法分析的一行的开始
	public void commonBegin() throws ComputException, SyntaxException {
		Token token = getNextToken();
		//判断是不是变量或者关键字
		if(token.getWordMean().equals(CommonVar.COMMONVAR)) { 
			
			//判断是否为关键字
			if(LogicAndLoopUtil.containKeyWordOfLB(token.getWord())) {  
				if(LogicAndLoopUtil.isIF(token.getWord()))
					logicalStruct(LogicAndLoopUtil.DEFAULTELSEBOOL);
				else if(LogicAndLoopUtil.isBreak(token.getWord()))
					breakStruct();
				else if(LogicAndLoopUtil.isRepeat(token.getWord()))
					repeatLoop();
				// 是 转到关键字处理
			} else if(onlyBool.if_func_bool(token.getWord())){
				whichWord--;
				fuctionOperation();
			} else {
				//不是关键字，是变量传到运算处理
				whichWord--;
				mathOpreate(); 
			}
		} else if(isMath(token.getWordMean())) { 
			//数字开头转到数字处理
			whichWord--;
			mathOpreate();
		} else if(errorBegin(whichWord, token.getWordMean(), token.getWord())) {
			//错误处理机制
			throw new ComputException("错误的开头:"+token.getWord());
		} 
	}
	
	//判断是否为错误的开始
	private boolean errorBegin(int whichWord, String wds, String word) { 
		return (whichWord==0 && wds.equals("symbol") && !(word.equals("+")||word.equals("-")));
	}
	
	// 运算操作
	private String mathOpreate() throws ComputException {
		final String localCollection = "localCollection";
		Token token;
		StackCWR valueStack = new StackCWR(); // 变量栈
		StackCWR valueMean = new StackCWR(); // 对应变量的意思
		StackCWR symbolStack = new StackCWR(); // 符号栈
		Stack<RCollection<CommonVar>> collectionStack = new Stack<RCollection<CommonVar>>();
		while ((token = getNextToken()) != null) {
			if (token.getWordMean().equals(CommonVar.COMMONVAR)) {
				String var = token.getWord();
				// 判断是否为关键字
				if (onlyBool.if_func_bool(token.getWord())){
					whichWord--;
					valueStack.push(fuctionOperation());
					valueMean.push(varManager.COLLECTION);
				} else {
					// 变量处理
					valueStack.push(var);
					valueMean.push(CommonVar.VAR);
				}
			} else if (isMath(token.getWordMean())) {
				// 数字处理
				valueStack.push(token.getWord());
				valueMean.push(token.getWordMean());
			} else if (token.getWordMean().equals("symbol")) {
				// 符号处理
				String wd = token.getWord();

				// 如果符号栈为空，那么让符号入栈
				if (symbolStack.isEmpty()) {
					symbolStack.push(wd);
				} else if (onlyBool.getPrio(symbolStack.getValue()) >= onlyBool.getPrio(wd)) {
					// 判断优先级，进行处理
					if (wd.equals("(")) {
						symbolStack.push(wd);
						continue;
					}
					String lastSym = symbolStack.pop();
					if (wd.equals(")")) {
						if (lastSym.equals("(")) {
							continue;
						} else if (symbolStack.getValue().equals("(")) {
							symbolStack.pop();
						}
					} else {
						// 优先级低的入栈
						symbolStack.push(wd);
					}

					// 没有特殊情况 下面进行处理
					// 变量出栈+操作
					String num = valueStack.pop();
					String type = judgeType(num, valueMean.getValue());
					
					if (valueMean.getValue().equals(varManager.COLLECTION)) {
						RCollection<CommonVar> collection1;
						RCollection<CommonVar> collection2;
						if(num.equals(localCollection)) {
							collection1 = collectionStack.pop();
						} else {
							collection1 = (RVector<CommonVar>) varManager.getCollection(num);
						}
						num = valueStack.pop();
						valueMean.pop();
						if(num.equals(localCollection)) {
							collection2 = collectionStack.pop();
						} else {
							collection2 = (RVector<CommonVar>) varManager.getCollection(num);
						}
						valueMean.pop();
						collectionStack.push(vectorOperate(lastSym, collection2, collection1));
						valueStack.push(localCollection);
						valueMean.push(varManager.COLLECTION);
					} else {
						CommonVar cnum1;
						CommonVar cnum2;
						if (varManager.getCommonVar(num) != null) {
							cnum1 = varManager.getCommonVar(num);
						} else {
							String type1 = valueMean.pop();
							if (type1.equals(CommonVar.INTEGER)) {
								cnum1 = new RInteger(Integer.parseInt(num.substring(0, num.length() - 1)));
							} else if (type1.equals(CommonVar.NUMERIC)) {
								cnum1 = new RNumeric(Double.parseDouble(num));
							} else if (type1.equals(CommonVar.LOGICAL)) {
								cnum1 = new RBoolean(num);
							} else {
								throw new ComputException("line:" + (whichLine + 1) + " " + "不存在的变量：" + num);

							}
							num = valueStack.pop();
							if (varManager.getCommonVar(num) != null) {
								cnum2 = varManager.getCommonVar(num);
							} else {
								String type2 = valueMean.pop();
								if (type2.equals(CommonVar.INTEGER)) {
									cnum2 = new RInteger(Integer.parseInt(num.substring(0, num.length() - 1)));
								} else if (type2.equals(CommonVar.NUMERIC)) {
									cnum2 = new RNumeric(Double.parseDouble(num));
								} else if (type2.equals(CommonVar.LOGICAL)) {
									cnum2 = new RBoolean(num);
								} else {
									throw new ComputException("line:" + (whichLine + 1) + " " + "不存在的变量：" + num);
								}
							}
							String[] result = operate(lastSym, cnum2, cnum1).split(",");
							valueStack.push(result[0]);
							valueMean.push(result[1]);
						}
					}
					// 出栈

				} else {
					// 判断优先级，进行处理
					symbolStack.push(wd);
				}
			} else {
				// 没有处理机制，报错
				throw new ComputException("line:" + (whichLine + 1) + " " + token.getWord());
			}
		}
		// 赋值语句的处理
		while (!symbolStack.isEmpty()) {
			String symbol = symbolStack.pop();
			if (onlyBool.getFunctionNum(symbol) > 18) {
				String num1 = valueStack.pop();
				String num2 = valueStack.pop();
				switch (onlyBool.getFunctionNum(symbol)) {
				case 19:
					String type = valueMean.pop();
					if (type.equals(varManager.COLLECTION)) {
						if(num1.equals(localCollection)) {
							varManager.addCollVar(num2, collectionStack.pop());
						} else {
							varManager.addCollVar(num2, varManager.getCollection(num1));
						}
					} else {
						if (type.equals(CommonVar.LOGICAL)) {
							varManager.addCommonVar(num2, new RBoolean(num1));
						} else {
							varManager.addCommonVar(num2, new RNumeric(Double.parseDouble(num1)));
						}

					}
					break;
				case 20:
					valueMean.pop();
					String finalType = valueMean.pop();
					if (finalType.equals(varManager.COLLECTION)) {
						if(num2.equals(localCollection)) {
							varManager.addCollVar(num1, collectionStack.pop());
						} else {
							varManager.addCollVar(num1, varManager.getCollection(num2));
						}
					} else {
						if (finalType.equals(CommonVar.LOGICAL)) {
							varManager.addCommonVar(num1, new RBoolean(num2));
						} else {

							varManager.addCommonVar(num1, new RNumeric(Double.parseDouble(num2)));

						}
					}
					break;
				}
				return null;
			} else {
				String num = valueStack.pop();
				String type = judgeType(num, valueMean.getValue());

				if (valueMean.getValue().equals(varManager.COLLECTION)) {
					RCollection<CommonVar> collection1;
					RCollection<CommonVar> collection2;
					if(num.equals(localCollection)) {
						collection1 = collectionStack.pop();
					} else {
						collection1 = (RVector<CommonVar>) varManager.getCollection(num);
					}
					num = valueStack.pop();
					valueMean.pop();
					if(num.equals(localCollection)) {
						collection2 = collectionStack.pop();
					} else {
						collection2 = (RVector<CommonVar>) varManager.getCollection(num);
					}
					valueMean.pop();
 					collectionStack.push(vectorOperate(symbol, collection2, collection1));
					valueStack.push(localCollection);
					valueMean.push(varManager.COLLECTION);
				} else {
					CommonVar cnum1;
					CommonVar cnum2;
					if (!type.equals("notvar")) {
						cnum1 = varManager.getCommonVar(num);
					} else {
						String type1 = valueMean.pop();
						if (type1.equals(CommonVar.INTEGER)) {
							cnum1 = new RInteger(Integer.parseInt(num.substring(0, num.length() - 1)));
						} else if (type1.equals(CommonVar.NUMERIC)) {
							cnum1 = new RNumeric(Double.parseDouble(num));
						} else if (type1.equals(CommonVar.LOGICAL)) {
							cnum1 = new RBoolean(num);
						} else {
							throw new ComputException("line:" + (whichLine + 1) + " " + "不存在的变量：" + num);
						}
						num = valueStack.pop();
						if (varManager.getCommonVar(num) != null) {
							cnum2 = varManager.getCommonVar(num);
						} else {
							String type11 = valueMean.pop();
							if (type11.equals(CommonVar.INTEGER)) {
								cnum2 = new RInteger(Integer.parseInt(num.substring(0, num.length() - 1)));
							} else if (type11.equals(CommonVar.NUMERIC)) {
								cnum2 = new RNumeric(Double.parseDouble(num));
							} else if (type11.equals(CommonVar.LOGICAL)) {
								cnum2 = new RBoolean(num);
							} else {
								throw new ComputException("line:" + (whichLine + 1) + " " + "不存在的变量：" + num);
							}
						}
						String[] result = operate(symbol, cnum2, cnum1).split(",");
						valueStack.push(result[0]);
						valueMean.push(result[1]);
					}
				}

			}
		}
		// 返回可能需要用到的结果，如果是赋值将会在上一步返回空
		return valueStack.pop();
	}

	private RVector<CommonVar> vectorOperate(String lastSym, RCollection<CommonVar> collection1, RCollection<CommonVar> collection2) throws ComputException {
		// TODO Auto-generated method stub
		String result = "";
		String type = "";
		for(int i=0; i<collection1.sizeof(); i++) {
			String[] resultTemp = operate(lastSym,collection1.getElement(i),collection2.getElement(i)).split(",");
			result += "," + resultTemp[0];
			type = resultTemp[1];
		}
		result = result.substring(1, result.length());
		VecC vecc = new VecC();
		return vecc.createVector(result.split(","),type);
	}

	// 变量运算
	private String operate(String sym, CommonVar cvar1, CommonVar cvar2) throws ComputException {
		// +1 -2 *3 /4 %%5 %/%6 ^7 >8 <9 ==10 <=11 >=12 !=13 :14 &15 |16 &&17
		// ||18
		String result = null;
		try {
			String num1 = getVar(cvar1);
			String num2 = getVar(cvar2);

			int funcNum = onlyBool.getFunctionNum(sym);
			switch (funcNum) {
			case 1:
				result = "" + (Double.parseDouble(num1) + Double.parseDouble(num2));
				break;
			case 2:
				result = "" + (Double.parseDouble(num1) - Double.parseDouble(num2));
				break;
			case 3:
				result = "" + (Double.parseDouble(num1) * Double.parseDouble(num2));
				break;
			case 4:
				result = "" + (Double.parseDouble(num1) / Double.parseDouble(num2));
				break;
			case 5:
				result = "" + (Double.parseDouble(num1) % Double.parseDouble(num2));
				break;
			case 6:
				result = "" + (int) (Double.parseDouble(num1) / Double.parseDouble(num2));
				break;
			case 7:
				result = "" + (Math.pow(Double.parseDouble(num1), Double.parseDouble(num2)));
				break;
			case 8:
				result = "FALSE";
				if ((Double.parseDouble(num1) > Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 9:
				result = "FALSE";
				if ((Double.parseDouble(num1) < Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 10:
				result = "FALSE";
				if ((Double.parseDouble(num1) == Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 11:
				result = "FALSE";
				if ((Double.parseDouble(num1) <= Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 12:
				result = "FALSE";
				if ((Double.parseDouble(num1) >= Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 13:
				result = "FALSE";
				if ((Double.parseDouble(num1) != Double.parseDouble(num2))) {
					result = "TRUE";
				}
				break;
			case 14:
				String num = "";
				for(int i=(int)Double.parseDouble(num1); i<=(int)Double.parseDouble(num2);i++) {
					num += "," + i;
				}
				num.substring(1,num.length());
				VecC vc = new VecC();
				result = vc.c(num, CommonVar.INTEGER);
				break;
			case 15:
				result = "FALSE";
				if (returnBool(num1) && returnBool(num2)) {
					result = "TRUE";
				}
				break;
			case 16:
				result = "FALSE";
				if (returnBool(num1) || returnBool(num2)) {
					result = "TRUE";
				}
				break;
			case 17:
				result = "FALSE";
				if (returnBool(num1) && returnBool(num2)) {
					result = "TRUE";
				}
				break;
			case 18:
				result = "FALSE";
				if (returnBool(num1) || returnBool(num2)) {
					result = "TRUE";
				}
				break;
			default:
				throw new ComputException("line:" + (whichLine + 1) + " " + "运算没有匹配到运算符" + sym);
			}
		} catch (Exception e) {
			throw new ComputException("line:" + (whichLine + 1) + " " + "运算类型不匹配");
		}
		if (result.equals("TRUE") || result.equals("FALSE")) {
			result += "," + CommonVar.LOGICAL;
		} else if (result.matches("^\\d+(\\.\\d+)?$")) {
			result += "," + CommonVar.NUMERIC;
		} else {
			result += "," + varManager.COLLECTION;
		}
		return result;
	}

	// 将不知道什么类型的转换成boolean
	private boolean returnBool(String value) {
		boolean result = true;
		if (value.equals("FALSE")) {
			result = false;
		} else if ("TRUE".equals(value)) {
			result = true;
		} else if (Double.parseDouble(value) == 0) {
			result = false;
		}
		return result;
	}

	// 从commvar里面获取值 结果返回
	private String getVar(CommonVar comvar) {
		String classType = comvar.getClassType();
		String result;
		if (classType.equals(comvar.INTEGER)) {
			result = "" + ((RInteger) comvar).getContent();
		} else if (classType.equals(comvar.NUMERIC)) {
			result = "" + ((RNumeric) comvar).getContent();
		} else {
			if(((RBoolean) comvar).getContent()) {
				result = "" + "TRUE";
			} else {
				result = "" + "FALSE";
			}
		}
		return result;
	}

	// 判断collection或者commonvar类型
	private String judgeType(String num1, String mean) throws ComputException {
		if (mean.equals(CommonVar.VAR)) {
			String varTemp = varManager.varContain(num1);
			if (varTemp != null) {
				if (varTemp.equals(varManager.COMMONVAR)) {
					return varManager.COMMONVAR;

				} else if (varManager.varContain(num1).equals(varManager.COLLECTION)) {
					return varManager.COLLECTION;
				} else {
					throw new ComputException("line:" + (whichLine + 1) + " " + "\"" + num1 + "\"" + "无法被识别");
				}
			}
		}
		return "notvar";
	}

	// 判断是否为数字
	private boolean isMath(String word) {
		boolean result = false;
		if (word.equals(CommonVar.NUMERIC)) {
			result = true;
		} else if (word.equals(CommonVar.HIDEVAR)) {
			result = true;
		} else if (word.equals(CommonVar.INTEGER)) {
			result = true;
		} else if (word.equals(CommonVar.COMPLEX)) {
			result = true;
		} else if (word.equals(CommonVar.LOGICAL)) {
			result = true;
		}
		return result;
	}
	/*
	 * 分支语句的处理
	 */
	
	private void logicalStruct(boolean elseBool) throws SyntaxException, ComputException {
		Token curToken = this.getNextToken();
		//当前的符号不符合语法要求，if后一定跟小括号“(”
		if(curToken == null || !LogicAndLoopUtil.isLeftSB(curToken.getWord()))
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		//维护一行的索引
		whichWord--;
		String result = mathOpreate();
		System.out.println(result);
		if(!RBoolean.TRUE.equals(result) && !RBoolean.FALSE.equals(result))
			throw new SyntaxException("在第" + (this.whichLine + 1) + "行" + "if的参数不是逻辑值！");
		if(RBoolean.TRUE.equals(result) || elseBool == false)
			elseBool = false;
		//转入下一行
		if (!this.goNextLine())
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		PairOfEndMess endMess = this.getCurBlock();
		if (result.equals(RBoolean.TRUE)) {// r的if判断是true
			// 当whichLine = endMess.getIndexOfLine()退出循环
			// 即whichLine指向block结束那一行
			if (whichLine == endMess.getIndexOfLine()) {
				commonBegin();
			} else {
				goNextLine();
				while (whichLine < endMess.getIndexOfLine()) {
					commonBegin();
					clearIndexOfWordMean();
					if(whichLine > endMess.getIndexOfLine())
						return;
					goNextLine();
				}
				//whichLine == endMess.getIndexOfLine()正常退出
			}
		} else {// 判断为false，直接忽略中间部分
			whichLine = endMess.getIndexOfLine();
			whichWord = endMess.getIndexOfToken();
		}
		//正常whichLine应该指向if语句末尾，当whichLine大于正常值时，说明发生了break
		if (!goNextLine())
			return;
		//此时指向新的一行
		curToken = this.getNextToken();
		
		if(LogicAndLoopUtil.isElse(curToken.getWord())) {//匹配到else关键字
			curToken = getNextToken();
			if(curToken != null && LogicAndLoopUtil.isIF(curToken.getWord())) {// else if
				this.logicalStruct(elseBool);
			}
			/*
			 * //只有else******************
			 */
			else if(curToken == null) {
				if (!this.goNextLine())
					throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
				endMess = this.getCurBlock();
				if (elseBool) {// 执行else语句
					// 当whichLine = endMess.getIndexOfLine()退出循环
					// 即whichLine指向block结束那一行
					if (whichLine == endMess.getIndexOfLine()) {
						commonBegin();
					} else {
						goNextLine();
						while (whichLine < endMess.getIndexOfLine()) {
							commonBegin();
							clearIndexOfWordMean();
							if(whichLine > endMess.getIndexOfLine())
								return;
							goNextLine();
						}
					}
				} else {// 判断为false，直接忽略中间部分
					whichLine = endMess.getIndexOfLine();
					whichWord = endMess.getIndexOfToken();
					// 此时索引值指向语句块结束的标志“}”下一行位置
				}
				if (!goNextLine())
					return;
			}else//一般会报错，最好R不要有这种代码格式
				throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		}else {//没有匹配到else关键字
			goLastLine();
		}
		return;
	}
	
	//repeat循环语句分析
	private void repeatLoop() throws SyntaxException, ComputException {
		if (!this.goNextLine())
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + "行");
		int curIndex0fToken = whichWord;
		int curIndexOfLine = whichLine;
		PairOfEndMess endMess = this.getCurBlock();
		loopStack.push(endMess);
		while(whichLine <= endMess.getIndexOfLine()) {
			whichLine = curIndexOfLine;
			whichWord = curIndex0fToken;
			if (whichLine == endMess.getIndexOfLine()) {
				commonBegin();
			} else {
				if (!goNextLine())
					return;
				while (whichLine < endMess.getIndexOfLine()) {
					commonBegin();
					clearIndexOfWordMean();
					goNextLine();
				}
				//正常whichLine指向语句块最后一行，whichLine == endMess.getIndexOfLine()
				//当遇到break，使whichLine到最后一行，运行++后，
				//whichLine会大于endMess.getIndexOfLine()
			}
		}
		return;
	}
	
	//break语句的解析
	private void breakStruct() throws SyntaxException {
		if(loopStack.isEmpty())
			throw new SyntaxException("语法错误在" + (this.whichLine + 1) + 
					"行, \"break\"不能在循环外使用");
		whichLine = loopStack.pop().indexOfLine;
		words = line.get(whichLine);
		clearIndexOfWordMean();
		return;
	}
	//维护语句的索引
	private void clearIndexOfWordMean() {
		whichWord = 0;
	}
	
	//到语句下一行
	//若已经最后一行，那么whichLine索引不变，并且返回false
	private boolean goNextLine() {
		whichLine++;
		clearIndexOfWordMean();
		if(whichLine >= line.size()) {
			whichLine--;
			return false;
		}
		words = line.get(whichLine);
		return true;
	}
	
	//回到上一行
	private boolean goLastLine() {
		whichLine--;
		clearIndexOfWordMean();
		if(whichLine < 0) {
			whichLine = 0;
			return false;
		}
		words = line.get(whichLine);
		return true;
	}
	//获取语句块
	private PairOfEndMess getCurBlock() throws SyntaxException {
		int curIndexOfWM = whichWord;
		int curIndexOfLine = whichLine;
		PairOfEndMess endMess = null;
		Token tempToken = getNextToken();
		if(LogicAndLoopUtil.isLeftLB(tempToken.getWord())) {
			int flag = 1;//表示标志
			//flag标志以及语句读到最后一行结束
			while(flag > 0 && whichLine < line.size()) {
				tempToken = this.getNextToken();
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
			endMess = new PairOfEndMess(whichWord, whichLine);
			whichWord = curIndexOfWM;
			whichLine = curIndexOfLine;
		}else {
			endMess = new PairOfEndMess(whichWord, whichLine);
			whichWord = curIndexOfWM;
			whichLine = curIndexOfLine;
		}
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
	//------------------------------------------------------MR.SAIL-------------------------------------------------------//
	
	//R语言中对自身所含基本函数调用的处理
	private String fuctionOperation() {
		Token currToken = this.getNextToken();
		String funcName = currToken.getWord();
		if (!this.onlyBool.if_func_bool(funcName)) {
			//EXCEPTION
			return "";
		}
		String result = this.commonFunc(funcName);
		return result;
	}
	
	private String commonFunc(String funcName) {
		if (funcName.equals(FuncConstant.CLASS)) {
			VarClass vclass = new VarClass();
			String classRes = vclass.Class(this.handleParam());
//			System.out.println(classRes);
			return classRes;
		} else if (funcName.equals(FuncConstant.LS)) {
			ListVar lsVar = new ListVar();
			String lsRes = "";
			String param = this.handleParam();
			if (param.isEmpty() || param.equals("")) {
				lsRes += lsVar.ls();
			} else {
				lsRes += lsVar.ls(param);
			}
//			VarPrint vp = new VarPrint();
//			vp.printVar(lsRes);
			return lsRes;
		} else if (funcName.equals(FuncConstant.PRINT)) {
			VarPrint varPrinter = new VarPrint();
			String printRes = varPrinter.printVar(this.handleParam());
//			System.out.println(printRes);
			return printRes;
		} else if (funcName.equals(FuncConstant.CAT)) {
			VarPrint varPrinter = new VarPrint();
			String catRes = varPrinter.catVar(this.handleParam());
			return catRes;
		} else {
			return this.createFunc(funcName);
		}
	}
	
	private String createFunc(String funcName) {
		if (funcName.equals(FuncConstant.C)) {
			VecC cfunc = new VecC();
			String classType = this.CGetClassType();
			String paraGet = this.handleParam();
			String cRes = cfunc.c(paraGet, classType);
//			VarPrint vp = new VarPrint();
//			vp.printVar(cRes);
			return cRes;
		} else if (funcName.equals(FuncConstant.LIST)) {
			ListL listfunc = new ListL();
			String classType = this.ListGetClassType();
			String paraGet = this.handleParam();
			String listRes = listfunc.list(paraGet, classType);
//			VarPrint vp = new VarPrint();
//			System.out.println(classType);
//			System.out.println(paraGet);
//			vp.printVar(listRes);
			return listRes;
		} else {
			return this.stringFunc(funcName);
		}
	}
	
	private String stringFunc(String funcName) {
		if (funcName.equals(FuncConstant.PASTE)) {
			VarPaste paster = new VarPaste();
			String pasteRes = paster.RPaste(this.handleParam());
			return pasteRes;
		} else if (funcName.equals(FuncConstant.RM)) {
			RemoveVar remover = new RemoveVar();
			remover.rm(this.handleParam());
			return "";
		} else if (funcName.equals(FuncConstant.TOUPPER)) {
			VarUpLow varup = new VarUpLow();
			String upRes = varup.Rtoupper(this.handleParam());
			return upRes;
		} else if (funcName.equals(FuncConstant.TOLOWER)) {
			VarUpLow varlow = new VarUpLow();
			String lowRes = varlow.Rtolower(this.handleParam());
			return lowRes;
		} else {
			return this.daigrameFunc(funcName);
		}
	}
	
	private String daigrameFunc(String funcName) {
		DrawDiagram drawDiagrame = new DrawDiagram();
		if (funcName.equals(FuncConstant.PLOT)) {
			drawDiagrame.creatChart(FuncConstant.PLOT, this.handleParam());
		} else if (funcName.equals(FuncConstant.BARPLOT)) {
			drawDiagrame.creatChart(FuncConstant.BARPLOT, this.handleParam());
		} else if (funcName.equals(FuncConstant.PIE)) {
			drawDiagrame.creatChart(FuncConstant.PIE, this.handleParam());
		} else if (funcName.equals(FuncConstant.SETWD)) {
			drawDiagrame.SetDict(this.handleParam());
		} else if (funcName.equals(FuncConstant.PNG)) {
			drawDiagrame.SetFilename(this.handleParam());
		} else {
			System.out.println("这个不可能GG的，不存在的"+funcName);
			//Error(当然也是下一个重定向的地方）
		}
		return "";
	}
	
	private String handleParam() {
		Token currToken = this.getNextToken();
		String param = "";
		if (!this.matchSymbol(currToken.getWord(), "(")) {
			System.out.println("EXCEPTION CLASS");
			return null;
		}
		currToken = this.getNextToken();
		while (!this.matchSymbol(currToken.getWord(), ")")) {
			 String paraName = currToken.getWord();
			 if (this.onlyBool.if_func_bool(paraName)) {
				 this.whichWord--;
				 param += this.fuctionOperation();
				 currToken = this.getNextToken();
				 continue;
			 }
			 if (false) { //已经没有这种操作了
				 //configureParam
				 //continue;
			 }
			 param += paraName;
			 currToken = this.getNextToken();
		}
		return param;
	}
	
	private String CGetClassType() {
		Token currToken = this.getNextToken();
		if (!this.matchSymbol(currToken.getWord(), "(")) {
			System.out.println("EXCEPTION CLASS");
			return null;
		}
		currToken = this.getNextToken();
		this.whichWord -= 2;
		return currToken.getWordMean();
	}
	
	private String ListGetClassType() {
		Token currToken = this.getNextToken();
		String paraRes = "";
		if (!this.matchSymbol(currToken.getWord(), "(")) {
			System.out.println("EXCEPTION CLASS");
			return null;
		}
		int i = 1;
		while (!this.matchSymbol(currToken.getWord(), ")")) {
			 currToken = this.getNextToken();
			 i++;
			 if (currToken.getWordMean().equals("symbol")) {
				 continue;
			 }
			 paraRes += currToken.getWordMean() + " ";
		}
		paraRes = paraRes.trim();
		paraRes = paraRes.replaceAll(" ", ",");
		this.whichWord -= i;
		return paraRes;
	}
	
	private boolean matchSymbol(String symbol, String target) {
		return symbol.equals(target);
	}
	
	//------------------------------------------------------MR.SAIL-------------------------------------------------------//
	
}
