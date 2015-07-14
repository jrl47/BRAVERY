package GameObjects;

import java.awt.Graphics;

public class Collectible{
	
	private int myAmount;
	private String myType;
	private Stage myStage;
	private int myX;
	private int myY;
	
	private boolean isDestroyed;
	
	public Collectible(int amount, String type, int x, int y, Stage stage){
		myAmount = amount;
		myStage = stage;
		myType = type;
		myX = x;
		myY = y;
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
	public void setAmount(int a){
		myAmount = a;
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
	
}
