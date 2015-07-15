package GameObjects;

import java.awt.Graphics;

public class CollectibleSkill extends Collectible{
	private String mySkill;
	
	public CollectibleSkill(int x, int y, Stage stage, String skill){
		super(x, y, stage, -1);
		mySkill = skill;
	}
	
	public String getSkill(){
		return mySkill;
	}
	
	@Override
	public void draw(Graphics g){
		if(myStage.getRelativeX(myX)*Stage.BLOCK_SIZE <0 || 
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) < 0 ||
				myStage.getRelativeX(myX)*Stage.BLOCK_SIZE >= myStage.getWidth()*Stage.BLOCK_SIZE ||
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) >= myStage.getHeight()*Stage.BLOCK_SIZE)
			return;
		g.drawImage(myStage.getManager().getImage(this), myStage.getRelativeX(myX)*Stage.BLOCK_SIZE,
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE), null);
	}
}