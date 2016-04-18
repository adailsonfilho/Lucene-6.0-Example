package util;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

public class Test1 {
	
	public static void main(String[] args) {
		
		String indexPath = "index";
		String docsPath = "documents";
		
		boolean stop = true;
		boolean stem = true;
		
		IndexFiles lw = new IndexFiles(indexPath,docsPath, stop, stem);
		try {
			SearchFiles sf = new SearchFiles(indexPath, stop, stem);
			sf.query("Movies in Iraq", "contents");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
