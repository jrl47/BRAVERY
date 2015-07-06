package UtilityObjects;

public class Action {
	
	private int myCost;
	private String myType;
	private int myPower;
	
	public Action(int cost, String type, int power){
		myCost = cost;
		myType = type;
		myPower = power;
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
	
}
