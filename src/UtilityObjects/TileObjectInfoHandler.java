package UtilityObjects;

import Utilities.State;
import GameObjects.Collectible;
import GameObjects.Enemy;
import GameObjects.Stage;
import GameObjects.Text;

public class TileObjectInfoHandler extends SubMenu{
	private Text myTileObjectInfo1;
	private Text myTileObjectInfo2;
	
	public TileObjectInfoHandler(Stage stage, State state) {
		super(stage, state);
		myTileObjectInfo1 = new Text(900, 300, " ", 2, myFont);
		myTileObjectInfo2 = new Text(900, 340, " ", 2, myFont);
		
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);

	}

	public void manageInfo() {
		myObjects.remove(myTileObjectInfo1);
		myObjects.remove(myTileObjectInfo2);
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			myTileObjectInfo1 = new Text(900, 300, " ", 2, myFont);
			myTileObjectInfo2 = new Text(900, 340, " ", 2, myFont);
		} else if(myCells.get(xLoc).get(yLoc).getEnemy()!=null){
			Enemy e = myCells.get(xLoc).get(yLoc).getEnemy();
			myTileObjectInfo1 = new Text(900, 300, "ENEMY:", 2, myFont);
			myTileObjectInfo2 = new Text(900, 340, "SIGHT RANGE: " + e.getSightRange(), 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCollectible()!=null){
			Collectible c = myCells.get(xLoc).get(yLoc).getCollectible();
			myTileObjectInfo1 = new Text(900, 300, "COLLECTIBLE:", 2, myFont);
			myTileObjectInfo2 = new Text(900, 340, c.getAmount() + " " + c.getType().toUpperCase() + " ENERGY", 2, myFont);
		} else{
			myTileObjectInfo1 = new Text(900, 300, " ", 2, myFont);
			myTileObjectInfo2 = new Text(900, 340, " ", 2, myFont);
		}
		myObjects.add(myTileObjectInfo1);
		myObjects.add(myTileObjectInfo2);
	}
}
