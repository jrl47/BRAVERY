package UtilityObjects;

import Utilities.State;
import GameObjects.Collectible;
import GameObjects.Enemy;
import GameObjects.Stage;
import GameObjects.Text;

public class TileObjectInfoHandler extends SubMenu{
	private Text myTileObjectInfo1;
	private Text myTileObjectInfo2;
	private Text myTileObjectInfo3;
	
	public TileObjectInfoHandler(Stage stage, State state) {
		super(stage, state);
		myTileObjectInfo1 = new Text(900, 400, " ", 1.5, myFont);
		myTileObjectInfo2 = new Text(900, 370, " ", 1.5, myFont);
		myTileObjectInfo3 = new Text(900, 340, " ", 1.5, myFont);
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);

	}

	public void manageInfo() {
		myObjects.remove(myTileObjectInfo1);
		myObjects.remove(myTileObjectInfo2);
		myObjects.remove(myTileObjectInfo3);
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			myTileObjectInfo2 = new Text(900, 370, " ", 2, myFont);
			myTileObjectInfo1 = new Text(900, 400, " ", 2, myFont);
		} else if(myCells.get(xLoc).get(yLoc).getEnemy()!=null){
			Enemy e = myCells.get(xLoc).get(yLoc).getEnemy();
			myTileObjectInfo3 = new Text(900, 340, "ENEMY:", 1.5, myFont);
			myTileObjectInfo2 = new Text(900, 370, "SIGHT RANGE: " + e.getSightRange(), 1.5, myFont);
			myTileObjectInfo1 = new Text(900, 400, "ATTACK RANGE: " + e.getAttackRange(), 1.5, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCollectible()!=null){
			Collectible c = myCells.get(xLoc).get(yLoc).getCollectible();
			myTileObjectInfo2 = new Text(900, 370, "COLLECTIBLE:", 1.5, myFont);
			myTileObjectInfo1 = new Text(900, 400, c.getAmount() + " " + c.getType().toUpperCase() + " ENERGY", 1.5, myFont);
		} else{
			myTileObjectInfo3 = new Text(900, 340, " ", 2, myFont);
			myTileObjectInfo2 = new Text(900, 370, " ", 2, myFont);
			myTileObjectInfo1 = new Text(900, 400, " ", 2, myFont);
		}
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
		myObjects.add(myTileObjectInfo3);
	}
}
