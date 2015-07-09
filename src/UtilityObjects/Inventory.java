package UtilityObjects;

public class Inventory {
	
	private int myEarth;
	private int myAir;
	private int myWater;
	private int myFire;
	
	public Inventory(){
		myEarth = 15000;
		myAir = 15000;
		myWater = 15000;
		myFire = 15000;
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
}
