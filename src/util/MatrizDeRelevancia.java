package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class MatrizDeRelevancia {
	
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
	
	public boolean checkValue(String docName, int queryId, int value){
		StringBuffer prefix = new StringBuffer();
		StringBuffer id = new StringBuffer();
		boolean lastWasDigit = false;
		for(int i = 0; i < docName.length(); i++){
			
			char c = docName.charAt(i);
			
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
		
		return values[queryId][column] == value;
	}
	

}
