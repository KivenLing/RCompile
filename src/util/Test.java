package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import exception.WordException;

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
				bufferLine.trim();
				WordMean wm = spiltWord.handel(bufferLine.split(""));
				System.out.println(wm.getWord());
				bufferLine = br.readLine();
				wordResult.add(wm);
			}
			a.setWords(wordResult);
			a.commonBegin();
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
