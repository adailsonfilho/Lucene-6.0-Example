package facade;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import business.LuceneSearch;

public class Facade {
	
	LuceneSearch ls;
	
	public Facade(){
		ls = new LuceneSearch("documents");
	}
	
	public String[][] query(String str) throws ParseException, IOException{
		
		return ls.query(str);
	}
}
