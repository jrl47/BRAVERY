package UtilitiesData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SkillBuilder {
	public static final int NUMBER_OF_SKILLS = 100;
	public static String[] earthData;
	public static Map<Integer, SkillData> myEarthSkills;
	public static String[] airData;
	public static Map<Integer, SkillData> myAirSkills;
	public static String[] waterData;
	public static Map<Integer, SkillData> myWaterSkills;
	public static String[] fireData;
	public static Map<Integer, SkillData> myFireSkills;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		earthData = new String[NUMBER_OF_SKILLS];
    	URL myFile = SkillBuilder.class.getResource("/EarthSkills");
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
    		earthData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myEarthSkills = new HashMap<Integer, SkillData>();
		for(int i=0; i<1; i++){
			SkillData e = new SkillData(i, "earth");
			myEarthSkills.put(i, e);
		}
		
		airData = new String[NUMBER_OF_SKILLS];
    	myFile = SkillBuilder.class.getResource("/AirSkills");
    	b = null;
		try {
			b = new BufferedReader(new InputStreamReader(myFile.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	currentLine = null;
		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
		counter = 0;
    	while(currentLine!=null){
    		// Breaks up at colons
    		airData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myAirSkills = new HashMap<Integer, SkillData>();
		for(int i=0; i<1; i++){
			SkillData e = new SkillData(i, "air");
			myAirSkills.put(i, e);
		}
		
		waterData = new String[NUMBER_OF_SKILLS];
    	myFile = SkillBuilder.class.getResource("/WaterSkills");
    	b = null;
		try {
			b = new BufferedReader(new InputStreamReader(myFile.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	currentLine = null;
		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
		counter = 0;
    	while(currentLine!=null){
    		// Breaks up at colons
    		waterData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myWaterSkills = new HashMap<Integer, SkillData>();
		for(int i=0; i<1; i++){
			SkillData e = new SkillData(i, "water");
			myWaterSkills.put(i, e);
		}
		
		fireData = new String[NUMBER_OF_SKILLS];
    	myFile = SkillBuilder.class.getResource("/FireSkills");
    	b = null;
		try {
			b = new BufferedReader(new InputStreamReader(myFile.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	currentLine = null;
		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
		counter = 0;
    	while(currentLine!=null){
    		// Breaks up at colons
    		fireData[counter] = currentLine;
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myFireSkills = new HashMap<Integer, SkillData>();
		for(int i=0; i<1; i++){
			SkillData e = new SkillData(i, "fire");
			myFireSkills.put(i, e);
		}
	}
	
	public static SkillData getSkill(int myIndex, String type){
		if(type.equals("earth")){
			return getEarthObject(myIndex);
		}
		if(type.equals("air")){
			return getAirObject(myIndex);
		}
		if(type.equals("water")){
			return getWaterObject(myIndex);
		}
		if(type.equals("fire")){
			return getFireObject(myIndex);
		}
		return null;
	}
	private static SkillData getEarthObject(int myIndex){
		return myEarthSkills.get(myIndex);
	}
	private static SkillData getAirObject(int myIndex) {
		return myAirSkills.get(myIndex);
	}
	private static SkillData getWaterObject(int myIndex){
		return myWaterSkills.get(myIndex);
	}
	private static SkillData getFireObject(int myIndex) {
		return myFireSkills.get(myIndex);
	}
}
