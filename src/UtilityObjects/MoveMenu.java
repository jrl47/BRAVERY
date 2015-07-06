package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;

public class MoveMenu extends SubMenu{
	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text myCostDialog;
	
	
	private StateChangeButton back;
	
	public MoveMenu(Stage stage, State state){
		super(stage, state);
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "main");
		
		noSelectionDialog = new Text(900, 20, "SELECT A VALID MOVE", 2, myFont);
		mySelectionDialog = new Text(900, 20, " ", 2, myFont);
		myCostDialog = new Text(900, 20, " ", 2, myFont);

		myObjects.add(mySelectionDialog);
		myObjects.add(myCostDialog);
		myObjects.add(back);
	}

	public void managePlayerMovement() {
		myObjects.remove(mySelectionDialog);
		myObjects.remove(myCostDialog);
		myObjects.remove(back);
		
		int x = Math.abs(myPlayer.getTargetX());
		int y = Math.abs(myPlayer.getTargetY());
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("earth")){
			mySelectionDialog = new Text(900, 20, "ORTHOGONAL MOVE:", 2, myFont);
			if(Math.max(x, y)==1){
				myCostDialog = new Text(900, 60, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
			else{
				myCostDialog = new Text(900, 60, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("air")){
				mySelectionDialog = new Text(900, 20, "DIAGONAL MOVE:", 2, myFont);
				myCostDialog = new Text(900, 60, 
				myCells.get(xLoc).get(yLoc).getCost() + " AIR ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("water")){
				mySelectionDialog = new Text(900, 20, "SKEW MOVE:", 2, myFont);
				myCostDialog = new Text(900, 60, 
				myCells.get(xLoc).get(yLoc).getCost() + " WATER ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("fire")){
			mySelectionDialog = new Text(900, 20, "STRAY MOVE:", 2, myFont);
			myCostDialog = new Text(900, 60, 
			myCells.get(xLoc).get(yLoc).getCost() + " FIRE ENERGY",2, myFont);
		}
		else{
			mySelectionDialog = noSelectionDialog;
			myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		
		myObjects.add(mySelectionDialog);
		myObjects.add(myCostDialog);
		myObjects.add(back);
	}
	
}
