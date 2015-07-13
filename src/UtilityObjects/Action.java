package UtilityObjects;

public class Action {
	
	private String myName;
	private String myType;
	private int myCost;
	private int myPower;
	private int myRange;
	private boolean roundSplash;
	
	public Action(String name){
		myName = name;
		myType = "";
	}
	
	public String getName(){
		return myName;
	}
	public String getType(){
		return myType;
	}
	public void setType(String type){
		myType = type;
	}
	public int getCost(){
		return myCost;
	}
	public void setCost(int cost){
		myCost = cost;
	}
	public int getPower(){
		return myPower;
	}
	public void setPower(int power){
		myPower = power;
	}
	public int getRange(){
		return myRange;
	}
	public void setRange(int range){
		myRange = range;
	}
	public boolean isRoundSplash(){
		return roundSplash;
	}
	public void setSplash(boolean round){
		roundSplash = round;
	}
	
}
