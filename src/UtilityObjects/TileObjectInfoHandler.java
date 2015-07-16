package UtilityObjects;

import Utilities.State;
import GameObjects.Collectible;
import GameObjects.CollectibleSkill;
import GameObjects.Enemy;
import GameObjects.Player;
import GameObjects.Stage;
import GameObjects.Text;

public class TileObjectInfoHandler extends SubMenu{
	private Text myTileObjectInfo1;
	private Text myTileObjectInfo2;
	private Text myTileObjectInfo3;
	private Text myTileObjectInfo4;
	private Text myTileObjectInfo5;
	
	public TileObjectInfoHandler(Stage stage, State state) {
		super(stage, state);
		myTileObjectInfo1 = new Text(900, 470, " ", 1.5, myFont);
		myTileObjectInfo2 = new Text(900, 440, " ", 1.5, myFont);
		myTileObjectInfo3 = new Text(900, 410, " ", 1.5, myFont);
		myTileObjectInfo4 = new Text(900, 380, " ", 1.5, myFont);
		myTileObjectInfo5 = new Text(900, 350, " ", 1.5, myFont);
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);
		myObjects.add(myTileObjectInfo4);
		myObjects.add(myTileObjectInfo5);
	}

	public void manageInfo() {
		myObjects.clear();
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		myTileObjectInfo5 = new Text(900, 350, " ", 2, myFont);
		myTileObjectInfo4 = new Text(900, 380, " ", 2, myFont);
		myTileObjectInfo3 = new Text(900, 410, " ", 2, myFont);
		myTileObjectInfo2 = new Text(900, 440, " ", 2, myFont);
		myTileObjectInfo1 = new Text(900, 470, " ", 2, myFont);
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			myTileObjectInfo2 = new Text(900, 470, " ", 2, myFont);
			myTileObjectInfo1 = new Text(900, 500, " ", 2, myFont);
		} else if(myCells.get(xLoc).get(yLoc).getEnemy()!=null){
			Enemy e = myCells.get(xLoc).get(yLoc).getEnemy();
			myTileObjectInfo5 = new Text(900, 440, e.getName().toUpperCase(), 1.5, myFont);
			myTileObjectInfo4 = new Text(900, 470, "POWER: " + e.getPower(), 1.5, myFont);
			myTileObjectInfo3 = new Text(1050, 470, "HEALTH: " + e.getHealth(), 1.5, myFont);
			myTileObjectInfo2 = new Text(900, 500, "VISION: " + e.getSightRange(), 1.5, myFont);
			myTileObjectInfo1 = new Text(1050, 500, "RANGE: " + e.getAttackRange(), 1.5, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCollectible()!=null){
			Collectible c = myCells.get(xLoc).get(yLoc).getCollectible();
			myTileObjectInfo2 = new Text(900, 470, "COLLECTIBLE:", 1.5, myFont);
			if(c instanceof CollectibleSkill){
				myTileObjectInfo1 = new Text(900,500, "NEW SKILL: " +  ((CollectibleSkill) c).getSkill().toUpperCase(), 1.5, myFont);
			}
			else{
				myTileObjectInfo1 = new Text(900, 500, c.getAmount() + " " + c.getType().toUpperCase() + " ENERGY", 1.5, myFont);
				if(c.getType().equals("health")){
					myTileObjectInfo1 = new Text(900, 500, c.getAmount() + " " + c.getType().toUpperCase(), 1.5, myFont);
				}
			}
		} else if(myCells.get(xLoc).get(yLoc).getPlayer()!=null){
//			Player p = myCells.get(xLoc).get(yLoc).getPlayer();
//			myTileObjectInfo5 = new Text(900, 500, "HP: " + p.getHealth() + "/" + p.getMaxHealth(), 1.5, myFont);
//			myTileObjectInfo4 = new Text(900, 380, "POWER: " + e.getPower(), 1.5, myFont);
//			myTileObjectInfo3 = new Text(900, 410, "HEALTH: " + e.getHealth(), 1.5, myFont);
//			myTileObjectInfo2 = new Text(900, 440, "SIGHT RANGE: " + e.getSightRange(), 1.5, myFont);
//			myTileObjectInfo1 = new Text(900, 470, "ATTACK RANGE: " + e.getAttackRange(), 1.5, myFont);
		}
		
		
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);
		myObjects.add(myTileObjectInfo4);
		myObjects.add(myTileObjectInfo5);
	}
}
