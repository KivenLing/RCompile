package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import exception.ComputException;
import exception.SyntaxException;
import exception.VariableNotFoundException;
import exception.WordException;
import util.LanguageAna;
import util.SplitToWord;
import util.WordMean;
import varmanage.VarManager;
/**
 * 
 * @author 陈文儒
 * data:2018.01.07
 * 词法、语法的主控调用，与界面类的接口
 * 异常抛出的最总接受点，处理异常信息输出到控制台
 *
 */
public class MainController {
	@SuppressWarnings("deprecation")
	//主控函数
	public void mainHandeler(JTextPane textReadPane, String text) {
		SplitToWord spiltWord = SplitToWord.getOnlySplWord();
		BufferedReader br = null;
		try {
			//将文本打包成buffer
			br = new BufferedReader(new InputStreamReader(new StringBufferInputStream(text),"utf-8"));
			String bufferLine = br.readLine();
			LanguageAna languageAnalyze = new LanguageAna();
			//词法分析结果
			List<WordMean> wordResult = new ArrayList<WordMean>();
			//词法分析
			while (bufferLine != null) {
				if(isNullLine(bufferLine)) {
					bufferLine = br.readLine();
					continue;
				}
				System.out.println("as"+bufferLine);
				bufferLine = bufferLine.replaceAll(" ", "");
				bufferLine = bufferLine.replaceAll("	", "");
				WordMean wm = spiltWord.handel(bufferLine.split(""));
				wm.toString();
				bufferLine = br.readLine();
				wordResult.add(wm);
			}
			// 初始化语法分析的词法分析
			languageAnalyze.setWords(wordResult);
			// 初始化语法分析中的界面引用
			languageAnalyze.setTextReadPane(textReadPane);
			appendToPane(textReadPane, "\n" , Color.BLACK);
			// 语法分析部分
			try {
				languageAnalyze.begin();
			} catch (ComputException | SyntaxException | VariableNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				appendToPane(textReadPane, "\n" + e.getMessage(), Color.RED);
			}
			VarManager vma = VarManager.getInstance();
			// System.out.println(((RBoolean)vma.getCommonVar("linkai")).getContent());

		} catch (IOException | WordException e) {
			// TODO Auto-generated catch block
			// 文本追加
			appendToPane(textReadPane, "\n" + e.getMessage(), Color.RED);
			// 结束
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean isNullLine(String bufferLine) {
		if(bufferLine.equals("\t")) {
			return true;
		} else if(bufferLine.equals("\r")) {
			return true;
		} else if(bufferLine.equals("\n")) {
			return true;
		} else if(bufferLine.equals("")){
			return true;
		}
		return false;
	}

	//添加信息至控制台
	private void appendToPane(JTextPane tp, String msg, Color c) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "宋体");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
		aset = sc.addAttribute(aset, StyleConstants.FontSize, 30);
		Document docs = tp.getDocument();
		try {
			docs.insertString(docs.getLength(), msg, aset);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
