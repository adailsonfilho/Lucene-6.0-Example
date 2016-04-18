package util;

import java.io.File;
import java.io.IOException;

public class TestsReport {
	
	public static void main(String[] args) {
		
		MatrizDeRelevancia mr = null;
		try {
			mr = new MatrizDeRelevancia();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		
		String indexPath = "index";
		String docsPath = "documents";
		
		
		boolean[][] configs = {
		{false,false},
		{false,true},
		{true,false},
		{true,true}		
		};
		
		String[] queries ={
				"war movies",
				"iraqi refugees",
				"refugees in usa"
		};
		
		//testa todas as configuracoes
		for(boolean[] stopstem : configs){
		
			System.out.println("Configuration: ");
			System.out.println("- Stop: "+stopstem[0]);
			System.out.println("- Stem: "+stopstem[1]);
			//indexa
			
			try{
				File indexFile = new File(indexPath);
				indexFile.delete();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			IndexFiles lw = new IndexFiles(indexPath,docsPath, stopstem[0], stopstem[1]);
			try {
				
				//prepara buscador
				SearchFiles sf = new SearchFiles(indexPath, stopstem[0], stopstem[1]);
				
				//busca todas as queries
				int queryId = 0;
				for(String query :queries){
					String[][] result = sf.query(query, "contents");
					
					//calcula métricas
					double precision = mr.precision(getTitles(result),queryId);
					double coverage = mr.coverage(getTitles(result),queryId);
					double fmeasure = mr.fmeasure(coverage, precision);
					
					System.out.println("Query: "+query);
					System.out.println("- Precision: "+precision);
					System.out.println("- Coverage: "+coverage);
					System.out.println("- F-Measure: "+fmeasure);
					
					queryId++;
					System.out.println();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println();
			for(int i = 0; i< 50; i++){
				System.out.print('-');
			}
			
			System.out.println();
		}
		
	}
	
	public static String[] getTitles(String[][] result){
		String[] titles = new String[result.length];
		
		int i = 0;
		for(String[] line : result ){
			titles[i++] = line[1];
		}
		
		return titles;
		
	}
}
