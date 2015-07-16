package UtilitiesData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CollectibleBuilder {

	public static final int NUMBER_OF_COLLECTIBLES = 100;
	public static String[] collectibleData;
	public static Map<Integer, CollectibleData> myCollectibles;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		collectibleData = new String[NUMBER_OF_COLLECTIBLES];
    	URL myFile = EnemyBuilder.class.getResource("/Collectibles");
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
    		collectibleData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myCollectibles = new HashMap<Integer, CollectibleData>();
		for(int i=0; i<1; i++){
			CollectibleData c = new CollectibleData(i);
			myCollectibles.put(i, c);
		}
	}
	public static CollectibleData getCollectibleObject(int i){
		return myCollectibles.get(i);
	}
	
}