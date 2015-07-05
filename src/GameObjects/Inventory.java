package GameObjects;

public class Inventory {
	
	private int myEarth;
	private int myAir;
	private int myWater;
	private int myFire;
	
	public Inventory(){
		myEarth = 10000;
		myAir = 10000;
		myWater = 10000;
		myFire = 10000;
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
