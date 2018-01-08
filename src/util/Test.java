package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import basicvar.RBoolean;
import basicvar.RNumeric;
import exception.CommBeginException;
import exception.ComputException;
import exception.SyntaxException;
import exception.WordException;
import varmanage.VarManager;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
SplitToWord spiltWord = SplitToWord.getOnlySplWord();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
			String bufferLine = br.readLine();
			LanguageAna a = LanguageAna.getLa();
			List<WordMean> wordResult = new ArrayList<WordMean>();
			while(bufferLine!=null){
				bufferLine = bufferLine.replaceAll(" ", "");
				bufferLine = bufferLine.replaceAll("	", "");
				WordMean wm = spiltWord.handel(bufferLine.split(""));
				bufferLine = br.readLine();
				wordResult.add(wm);
			}
			//初始化语法分析的词法分析
			a.setWords(wordResult);
		
				//语法测试
				try {
					a.begin();
				} catch (ComputException | SyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				VarManager vma = VarManager.getInstance();
				//System.out.println(((RBoolean)vma.getCommonVar("linkai")).getContent());
			
		} catch (IOException | WordException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
