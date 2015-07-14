package GameObjects;

import java.awt.Graphics;

public class CollectibleSkill{
	
	private int myAmount;
	private String myType;
	private Stage myStage;
	private int myX;
	private int myY;
	private String mySkill;
	
	private boolean isDestroyed;
	
	public CollectibleSkill(int x, int y, Stage stage, String skill){
		myStage = stage;
		myX = x;
		myY = y;
		mySkill = skill;
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

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
	public String getSkill(){
		return mySkill;
	}
	
}