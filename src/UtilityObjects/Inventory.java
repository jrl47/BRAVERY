package UtilityObjects;

public class Inventory {
	
	private int myEarth;
	private int myAir;
	private int myWater;
	private int myFire;
	
	public Inventory(){
		myEarth = 48;
		myAir = 48;
		myWater = 48;
		myFire = 48;
	}
	
	public void setEarth(int e){
		myEarth = e;
	}
	public void setAir(int a){
		myAir = a;
	}
	public void setWater(int w){
		myWater = w;
	}
	public void setFire(int f){
		myFire = f;
	}
	
	public int getEarth(){
		return myEarth;
	}
	public int getAir(){
		return myAir;
	}
	public int getWater(){
		return myWater;
	}
	public int getFire(){
		return myFire;
	}

	public int getMin() {
		return Math.min(myEarth, Math.min(myAir, Math.min(myWater, myFire)));
	}
}
