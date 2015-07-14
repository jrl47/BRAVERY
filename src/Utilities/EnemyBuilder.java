package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class EnemyBuilder {

	public static final int NUMBER_OF_ENEMIES = 100;
	public static String[] enemyData;
	public static Map<Integer, EnemyData> myEnemies;
	public static Map<String, EnemyData> myStringEnemies;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		enemyData = new String[100];
    	URL myFile = EnemyBuilder.class.getResource("/Enemies");
    	BufferedReader b = null;
		try {
			b = new BufferedReader(new InputStreamReader(myFile.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	String currentLine = null;
		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
		int counter = 0;
    	while(currentLine!=null){
    		// Breaks up at colons
    		enemyData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myEnemies = new HashMap<Integer, EnemyData>();
		myStringEnemies = new HashMap<String, EnemyData>();
		for(int i=0; i<1; i++){
			EnemyData e = new EnemyData(i);
			myEnemies.put(i, e);
			myStringEnemies.put(myEnemies.get(i).getMyName(), myEnemies.get(i));
		}
	}
	public static EnemyData getEnemyObject(int i){
		return myEnemies.get(i);
	}
	public static EnemyData getEnemyObject(String s){
		return myStringEnemies.get(s);
	}
	
}
