package UtilityObjects;

public class Action {
	
	private int myCost;
	private String myType;
	private int myPower;
	private int myRange;
	private boolean roundSplash;
	
	public Action(int cost, String type, int power, int range, boolean roundsplash){
		myCost = cost;
		myType = type;
		myPower = power;
		myRange = range;
		roundSplash = roundsplash;
	}
	
	public int getCost(){
		return myCost;
	}
	public int getPower(){
		return myPower;
	}
	public String getType(){
		return myType;
	}
	public int getRange(){
		return myRange;
	}
	public boolean isRoundSplash(){
		return roundSplash;
	}
	
}
