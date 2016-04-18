package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class MatrizDeRelevancia {
	
	public final int Q1_WAR_MOVIES = 0;
	public final int Q2_IRAQI_REFUGEES = 1;
	public final int Q3_REFUGEES_IN_USA = 2;
	
	int [][] values;

	public MatrizDeRelevancia() throws IOException{
		
		this.values = null;
		
		values = new int[3][300];
		
		int i = 0;
		for (String line : Files.readAllLines(Paths.get("matriz_relev.csv"))) {
			if(i > 0 && i < 4){
				String[] line_ = line.split(",");
				for(int col = 2; col < line_.length ; col++){
					values[i-1][col-2] =Integer.parseInt(line_[col]);
				}				
		    }
			i++;
		}

	}
	
	public int getCount(int queryId){
		int acc = 0;
		for(int value : values[queryId]){
			if(value == 1){
				acc++;
			}
		}
		return acc;
		
	}
	
	public double precision(String[] docsTitles, int queryId){
		//Recuperados e relevantes / recuperados
		
		int relevants_cacthed = 0;
		for(String title : docsTitles){
			if(isRelevant(title, queryId))relevants_cacthed++;
		}
		
		return (double)relevants_cacthed/(double)docsTitles.length;
	}
	
	public double coverage(String[] docsTitles, int queryId){
		//Recuperados e relevantes / relevantes
		int relevants_cacthed = 0;
		for(String title : docsTitles){
			if(isRelevant(title, queryId))relevants_cacthed++;
		}
		
		int total_relevants = 0;
		for(int value : values[queryId]){
			if(value ==1 )total_relevants++;
		}
		
		return (double)relevants_cacthed/(double)total_relevants;
	}
	
	public double fmeasure(double coverage, double precision){
		return (2*coverage*precision)/(precision + coverage);
	}
	
	
	public boolean isRelevant(String docTitle, int queryId){
		StringBuffer prefix = new StringBuffer();
		StringBuffer id = new StringBuffer();
		boolean lastWasDigit = false;
		
		docTitle = docTitle.replace(".txt", "");
		
		for(int i = 0; i < docTitle.length(); i++){
			
			char c = docTitle.charAt(i);
			
			if(lastWasDigit) break;
			
			if(Character.isAlphabetic(c)){
				prefix.append(c);
			}else if(Character.isDigit(c)){
				id.append(c);
				lastWasDigit = true;
			}
		}
		
		int column = Integer.parseInt(id.toString());
		if(prefix.toString().equalsIgnoreCase("iraq")){
			column += -1;
		}else if(prefix.toString().equalsIgnoreCase("movies")){
			column += 99;
		}else if(prefix.toString().equalsIgnoreCase("F")){
			column += 199;
		}
	
		
		return values[queryId][column] == 1;
	}
	
	

}
