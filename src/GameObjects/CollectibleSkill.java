package GameObjects;

import java.awt.Graphics;

import Utilities.RoomNetwork;
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
		super(0, 0, stage, -1);
		CollectibleSkillData data = CollectibleSkillBuilder.getSkillObject(i);
		myGenericSkill = "";
//		myGenericSkill = data.getName();
		mySkill = SkillBuilder.getSkill(data.getIndex(), data.getType());
		roomX = data.getRoomX();
		roomY = data.getRoomY();
		RoomNetwork myRooms = stage.getRooms();
		int difX = roomX - myRooms.getX(roomX, roomY);
		int difY = roomY - myRooms.getY(roomX, roomY);
		myX = data.getX() + difX*Stage.ROOM_SIZE;
		myY = data.getY() + difY*Stage.ROOM_SIZE;
	}
	
	public String getGenericSkill(){
		return myGenericSkill;
	}
	public SkillData getSkill(){
		return mySkill;
	}
	
	public int getRoomX() {
		return roomX;
	}
	public int getRoomY(){
		return roomY;
	}
}