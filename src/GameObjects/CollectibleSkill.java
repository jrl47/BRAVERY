package GameObjects;

import java.awt.Graphics;

import UtilitiesData.CollectibleSkillBuilder;
import UtilitiesData.CollectibleSkillData;
import UtilitiesData.SkillBuilder;
import UtilitiesData.SkillData;

public class CollectibleSkill extends Collectible{
	
	private String myGenericSkill;
	
	private SkillData mySkill;
	private int  roomX;
	private int roomY;
	
	public CollectibleSkill(int i, Stage stage){
		super(CollectibleSkillBuilder.getSkillObject(i).getX(),CollectibleSkillBuilder.getSkillObject(i).getY(), stage, -1);
		CollectibleSkillData data = CollectibleSkillBuilder.getSkillObject(i);
		myGenericSkill = data.getName();
		mySkill = SkillBuilder.getSkill(data.getIndex(), data.getType());
		roomX = data.getRoomX();
		roomY = data.getRoomY();
	}
	
	public String getGenericSkill(){
		return myGenericSkill;
	}
	public SkillData getSkill(){
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
	public int getRoomX() {
		return roomX;
	}
	public int getRoomY(){
		return roomY;
	}
}