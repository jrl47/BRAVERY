package UtilitiesData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NPCBuilder {
	public static final int NUMBER_OF_NPCS = 100;
	public static String[] npcData;
	public static Map<Integer, NPCData> myNPCs;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		npcData = new String[NUMBER_OF_NPCS];
    	URL myFile = EnemyBuilder.class.getResource("/NPCs");
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
    		npcData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myNPCs = new HashMap<Integer, NPCData>();
		for(int i=0; i<1; i++){
			NPCData e = new NPCData(i);
			myNPCs.put(i, e);
		}
	}
	public static NPCData getNPCObject(int i){
		return myNPCs.get(i);
	}
}
