package GameObjects;

import java.awt.Graphics;
import java.util.Random;

import Utilities.CollectibleBuilder;
import Utilities.CollectibleData;

public class Collectible{
	
	private int myAmount;
	protected String myType;
	protected Stage myStage;
	protected int myX;
	protected int myY;
	
	private boolean isDestroyed;
	
	public Collectible(int x, int y, Stage stage, int index){
		CollectibleData data = CollectibleBuilder.getCollectibleObject(index);
		myStage = stage;
		myX = x;
		myY = y;
		if(index==-1) return;
		
		Random r = new Random();
		int type = r.nextInt(data.getTotalChance());
		if(type<data.getEarthChance()){
			myAmount = data.getEarthAmount();
			myType = "earth";
			return;
		}
		type-=data.getEarthChance();
		if(type<data.getAirChance()){
			myAmount = data.getAirAmount();
			myType = "air";
			return;
		}
		type-=data.getAirChance();
		if(type<data.getWaterChance()){
			myAmount = data.getWaterAmount();
			myType = "water";
			return;
		}
		type-=data.getWaterChance();
		if(type<data.getFireChance()){
			myAmount = data.getFireAmount();
			myType = "fire";
			return;
		}
		type-=data.getHealthChance();
		if(type<data.getHealthChance()){
			myAmount = data.getHealthAmount();
			myType = "health";
			return;
		}
		type-=data.getHealthChance();
	}
	
	public void draw(Graphics g){
		if(myStage.getRelativeX(myX)*Stage.BLOCK_SIZE <0 || 
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) < 0 ||
				myStage.getRelativeX(myX)*Stage.BLOCK_SIZE >= myStage.getWidth()*Stage.BLOCK_SIZE ||
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) >= myStage.getHeight()*Stage.BLOCK_SIZE)
			return;
		g.drawImage(myStage.getManager().getImage(this), myStage.getRelativeX(myX)*Stage.BLOCK_SIZE,
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE), null);
	}

	public int getAmount(){
		return myAmount;
	}
	
	public String getType(){
		return myType;
	}

	public void destroy() {
		isDestroyed = true;
	}
	public boolean isDestroyed(){
		return isDestroyed;
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
}
