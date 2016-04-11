package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class LuceneSearch {
	public static void main(String[] args)
	{
		BufferedReader br = null;
		
		try
		{
			
			File folder = new File("documents");
            File[] listOfFiles = folder.listFiles();
            
            PrintWriter writer = new PrintWriter("documents/output_search.txt", "UTF-8");
            String sCurrentLine;
            
			//	Specify the analyzer for tokenizing text.
		   //	The same analyzer should be used for indexing and searching
			StandardAnalyzer analyzer = new StandardAnalyzer();
			
			//	Code to create the index
			Directory index = new RAMDirectory();
			
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter w = new IndexWriter(index, config);
			String content = "";
			
			for (File file : listOfFiles) {
            	if (file.isFile()) {
            		br = new BufferedReader(new FileReader(file.getPath()));
            		while ((sCurrentLine = br.readLine()) != null) {            			
                        content = content + sCurrentLine;
                    }
            		addDoc(w, file.getName(), content);
            		content = "";
            	}
            }
            writer.close();
			w.close();
			
			//	Text to search
			String querystr = args.length > 0 ? args[0] : "movies in iraq";
			
			//	The "title" arg specifies the default field to use when no field is explicitly specified in the query
			//Query q = new QueryParser("title", analyzer).parse(querystr);
			Query q = new QueryParser("content", analyzer).parse(querystr);
			
			// Searching code
			int hitsPerPage = 300;
		   IndexReader reader = DirectoryReader.open(index);
		   IndexSearcher searcher = new IndexSearcher(reader);
		   TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		   searcher.search(q, collector);
		   ScoreDoc[] hits = collector.topDocs().scoreDocs;
		   
		   //	Code to display the results of search
		   System.out.println("Found " + hits.length + " hits.");
		   for(int i=0;i<hits.length;++i) 
		   {
		     int docId = hits[i].doc;
		     Document d = searcher.doc(docId);
		     System.out.println((i + 1) + ". " + d.get("title"));
		   }
		   
		   // reader can only be closed when there is no need to access the documents any more
		   reader.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	private static void addDoc(IndexWriter w, String title, String content) throws IOException 
	{
		 Document doc = new Document();
		 // A text field will be tokenized
		 doc.add(new TextField("title", title, Field.Store.YES));
		 doc.add(new TextField("content", content, Field.Store.YES));
		 w.addDocument(doc);
	}
}