package UtilitiesData;

import java.util.ArrayList;
import java.util.List;

public class SkillData {
	private String myName;
	private int myIndex;
	private int myCost;
	private int myPower;
	private int myRange;
	private int isRound;
	private String myType;
	private List<AttackRangeSpecs> myHitRange;
	public SkillData(int i, String type){
		myType = type;
		String dataLine = null;
		if(type.equals("earth")){
			dataLine = SkillBuilder.earthData[i];
		}
		if(type.equals("air")){
			dataLine = SkillBuilder.airData[i];
		}
		if(type.equals("water")){
			dataLine = SkillBuilder.waterData[i];
		}
		if(type.equals("fire")){
			dataLine = SkillBuilder.fireData[i];
		}
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myName = data[0];
		myIndex = Integer.parseInt(data[1]);
		myCost = Integer.parseInt(data[2]);
		myPower = Integer.parseInt(data[3]);
		myRange = Integer.parseInt(data[4]);
		isRound = Integer.parseInt(data[5]);
		myHitRange = new ArrayList<AttackRangeSpecs>();
		String[] hitRange = data[6].split("\\|");
		for(String  s : hitRange){
			myHitRange.add(new AttackRangeSpecs(s));
		}
	}
	public String getName() {
		return myName;
	}
	public int getIndex(){
		return myIndex;
	}
	public int getCost() {
		return myCost;
	}
	public int getPower() {
		return myPower;
	}
	public int getRange() {
		return myRange;
	}
	public boolean isRound() {
		return isRound==1;
	}
	public String getType() {
		return myType;
	}
	public List<AttackRangeSpecs> getHitRange(){
		return myHitRange;
	}
}
