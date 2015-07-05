package GameObjects;

public class Collectible {
	
	public int myAmount;
	public String myType;
	
	public Collectible(int amount, String type){
		myAmount = amount;
		myType = type;
	}

	public int getAmount(){
		return myAmount;
	}
	public void setAmount(int a){
		myAmount = a;
	}
	
	public String getType(){
		return myType;
	}
	
}
