package UtilitiesData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CollectibleSkillBuilder {

	public static final int NUMBER_OF_SKILLS = 100;
	public static String[] skillData;
	public static Map<Integer, CollectibleSkillData> mySkills;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		skillData = new String[NUMBER_OF_SKILLS];
    	URL myFile = EnemyBuilder.class.getResource("/Skills");
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
    		skillData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		mySkills = new HashMap<Integer, CollectibleSkillData>();
		for(int i=0; i<2; i++){
			CollectibleSkillData e = new CollectibleSkillData(i);
			mySkills.put(i, e);
		}
	}
	public static CollectibleSkillData getSkillObject(int i){
		return mySkills.get(i);
	}
}
