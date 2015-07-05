package UtilityObjects;

import java.io.IOException;

import javax.imageio.ImageIO;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Main.World;
import Utilities.State;

public class MoveMenu extends SubMenu{
	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text myCostDialog;
	
	
	StateChangeButton back;
	
	public MoveMenu(Stage stage, State state){
		super(stage, state);
		
		try {
			back = new StateChangeButton(984, 550, "BACK", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "main");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		noSelectionDialog = new Text(900, 50, "SELECT A VALID MOVE", 2, myFont);
		mySelectionDialog = new Text(900, 50, " ", 2, myFont);
		myCostDialog = new Text(900, 50, " ", 2, myFont);

		myObjects.add(mySelectionDialog);
		myObjects.add(noSelectionDialog);
		myObjects.add(myCostDialog);
		myObjects.add(back);
	}

	public void managePlayerMovement(State oldState) {
		int x = Math.abs(myPlayer.getTargetX());
		int y = Math.abs(myPlayer.getTargetY());
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
		else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("earth")){
			mySelectionDialog = new Text(900, 50, "ORTHOGONAL MOVE:", 2, myFont);
			if(Math.max(x, y)==1){
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
			else{
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("air")){
				mySelectionDialog = new Text(900, 50, "DIAGONAL MOVE:", 2, myFont);
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " AIR ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("water")){
				mySelectionDialog = new Text(900, 50, "SKEW MOVE:", 2, myFont);
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " WATER ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("fire")){
			mySelectionDialog = new Text(900, 50, "STRAY MOVE:", 2, myFont);
			myCostDialog = new Text(900, 90, 
			myCells.get(xLoc).get(yLoc).getCost() + " FIRE ENERGY",2, myFont);
		}
		else{
			mySelectionDialog = noSelectionDialog;
			myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
	}
	
}
