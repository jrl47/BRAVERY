package UtilityObjects;


import Utilities.RoomNetwork;
import Utilities.State;
import GameObjects.Boss;
import GameObjects.Collectible;
import GameObjects.CollectibleSkill;
import GameObjects.Enemy;
import GameObjects.Stage;
import GameObjects.Text;

public class TileObjectInfoHandler extends SubMenu{
	private Text myTileObjectInfo1;
	private Text myTileObjectInfo2;
	private Text myTileObjectInfo3;
	private Text myTileObjectInfo4;
	private Text myTileObjectInfo5;
	
	private RoomNetwork myNetwork;
	
	public TileObjectInfoHandler(Stage stage, State state) {
		super(stage, state);
		myTileObjectInfo1 = new Text(740, 465, " ", 1.5, myFont);
		myTileObjectInfo2 = new Text(740, 495, " ", 1.5, myFont);
		myTileObjectInfo3 = new Text(740, 495, " ", 1.5, myFont);
		myTileObjectInfo4 = new Text(740, 525, " ", 1.5, myFont);
		myTileObjectInfo5 = new Text(740, 525, " ", 1.5, myFont);
		
		myNetwork = myStage.getRooms();
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);
		myObjects.add(myTileObjectInfo4);
		myObjects.add(myTileObjectInfo5);
	}

	public void manageInfo() {
		myObjects.clear();
		myTileObjectInfo5 = new Text(740, 465, " ", 2, myFont);
		myTileObjectInfo4 = new Text(740, 495, " ", 2, myFont);
		myTileObjectInfo3 = new Text(740, 495, " ", 2, myFont);
		myTileObjectInfo2 = new Text(740, 525, " ", 2, myFont);
		myTileObjectInfo1 = new Text(740, 525, " ", 2, myFont);
		if(myStage.wasInput()){
			drawTileData();
		}
		else if(!(myStage.getHoverRoomX()==-1 || myStage.getHoverRoomY()==-1)){
			drawRoomData();
		}
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);
		myObjects.add(myTileObjectInfo4);
		myObjects.add(myTileObjectInfo5);
	}

	private void drawTileData() {
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			myTileObjectInfo2 = new Text(740, 495, " ", 2, myFont);
			myTileObjectInfo1 = new Text(740, 525, " ", 2, myFont);
		} else if(myCells.get(xLoc).get(yLoc).getEnemy()!=null){
			Enemy e = myCells.get(xLoc).get(yLoc).getEnemy();
			myTileObjectInfo5 = new Text(740, 465, e.getName().toUpperCase(), 1.5, myFont);
			myTileObjectInfo4 = new Text(740, 495, "POWER: " + e.getPower(), 1.5, myFont);
			myTileObjectInfo3 = new Text(890, 495, "HEALTH: " + e.getHealth(), 1.5, myFont);
			myTileObjectInfo2 = new Text(740, 525, "VISION: " + e.getSightRange(), 1.5, myFont);
			myTileObjectInfo1 = new Text(890, 525, "RANGE: " + e.getAttackRange(), 1.5, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCollectible()!=null){
			Collectible c = myCells.get(xLoc).get(yLoc).getCollectible();
			myTileObjectInfo2 = new Text(740, 495, "COLLECTIBLE:", 1.5, myFont);
			if(c instanceof CollectibleSkill){
				if(!((CollectibleSkill) c).getGenericSkill().toUpperCase().equals("")){
					myTileObjectInfo1 = new Text(740,525, "NEW SKILL: " +  ((CollectibleSkill) c).getGenericSkill().toUpperCase(), 1.5, myFont);
				}
				else{
					myTileObjectInfo1 = new Text(740,525, "NEW SKILL: " +  ((CollectibleSkill) c).getSkill().getName().toUpperCase(), 1.5, myFont);
				}
			}
			else{
				myTileObjectInfo1 = new Text(740, 525, c.getAmount() + " " + c.getType().toUpperCase() + " ENERGY", 1.5, myFont);
				if(c.getType().equals("health")){
					myTileObjectInfo1 = new Text(740, 525, c.getAmount() + " " + c.getType().toUpperCase(), 1.5, myFont);
				}
			}
		}
	}
	
	private void drawRoomData() {
		boolean hasBoss = false;
		for(Boss b: myStage.getBosses()){
			if(myStage.getHoverRoomX()==b.getRoomX() && myStage.getHoverRoomY()==b.getRoomY() && !myStage.getPlayer().getShiftHeld()){
				myTileObjectInfo5 = new Text(740, 465, b.getName().toUpperCase(), 1.5, myFont);
				myTileObjectInfo4 = new Text(740, 495, "POWER: " + b.getPower(), 1.5, myFont);
				myTileObjectInfo3 = new Text(890, 495, "HEALTH: " + b.getHealth(), 1.5, myFont);
				myTileObjectInfo2 = new Text(740, 525, "VISION: " + b.getSightRange(), 1.5, myFont);
				myTileObjectInfo1 = new Text(890, 525, "RANGE: " + b.getAttackRange(), 1.5, myFont);
				hasBoss = true;
			}
		}
		for(CollectibleSkill c: myStage.getCollectibleSkills()){
			if(myStage.getHoverRoomX()==c.getRoomX() && myStage.getHoverRoomY()==c.getRoomY() && (!hasBoss || myStage.getPlayer().getShiftHeld())){
				myTileObjectInfo1 = new Text(740,525, "NEW SKILL: " +  ((CollectibleSkill) c).getSkill().getName().toUpperCase(), 1.5, myFont);
			}
		}
	}
}
