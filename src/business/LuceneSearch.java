package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import util.IndexFiles;
import util.SearchFiles;

public class LuceneSearch {

	SearchFiles sf;
	
	public LuceneSearch()
	{

		String indexPath = "index";
		String docsPath = "documents";
		
		boolean stop = true;
		boolean stem = true;
		
		IndexFiles lw = new IndexFiles(indexPath,docsPath,stop, stem);
		try {
			sf = new SearchFiles(indexPath, stop, stem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[][] query(String queryString) throws ParseException, IOException{

		String[][] result = sf.query(queryString, "contents");		
		
		return result;
		
	}
}