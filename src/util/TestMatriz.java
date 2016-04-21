package util;

import java.io.IOException;

public class TestMatriz {
	
	public static void main(String[] args) {
		MatrizDeRelevancia mr = null;
		try {
			mr = new MatrizDeRelevancia();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		int q1 = mr.getCount(MatrizDeRelevancia.Q1_WAR_MOVIES);
		int q2 = mr.getCount(MatrizDeRelevancia.Q2_IRAQI_REFUGEES);
		int q3 = mr.getCount(MatrizDeRelevancia.Q3_REFUGEES_IN_USA);
		
		System.out.println("Q1: "+q1);
		System.out.println("Q2: "+q2);
		System.out.println("Q3: "+q3);
		
	}
}
