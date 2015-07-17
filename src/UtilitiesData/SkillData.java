package UtilitiesData;

public class SkillData {
	private String myName;
	private int myCost;
	private int myPower;
	private int myRange;
	private int isRound;
	private String myType;
	public SkillData(int i, String type){
		myType = type;
		if(type.equals("earth")){
			String dataLine = SkillBuilder.earthData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myCost = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			myRange = Integer.parseInt(data[3]);
			isRound = Integer.parseInt(data[4]);
		}
		if(type.equals("air")){
			String dataLine = SkillBuilder.airData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myCost = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			myRange = Integer.parseInt(data[3]);
			isRound = Integer.parseInt(data[4]);
		}
		if(type.equals("water")){
			String dataLine = SkillBuilder.waterData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myCost = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			myRange = Integer.parseInt(data[3]);
			isRound = Integer.parseInt(data[4]);
		}
		if(type.equals("fire")){
			String dataLine = SkillBuilder.fireData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myCost = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			myRange = Integer.parseInt(data[3]);
			isRound = Integer.parseInt(data[4]);
		}
	}
	public String getName() {
		return myName;
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
}
