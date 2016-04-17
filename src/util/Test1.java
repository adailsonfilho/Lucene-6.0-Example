package util;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import business.LuceneWrapped;

public class Test1 {
	
	public static void main(String[] args) {
		
		LuceneWrapped lw = new LuceneWrapped("documents");
		try {
			String[][] result = lw.query("movies in iraq",true, false);
			
			System.out.println(result.length+" hit");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
