package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;

public class AttackMenu extends SubMenu{

	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text emptySelectionDialog;
	private Text myCostDialog;
	
	private StateChangeButton back;
	private StateChangeButton earth;
	private StateChangeButton air;
	private StateChangeButton water;
	private StateChangeButton fire;
	
	private StateChangeButton subBack;
	private StateChangeButton cancel;
	private State myAttackType;
	private State myAttack;
	
	private StateChangeButton boulderFall;
	public AttackMenu(Stage stage, State state) {
		super(stage, state);
		myAttackType = new State("main");
		myAttack = new State("main");
		
		noSelectionDialog = new Text(886, 20, "SELECT A VALID ATTACK", 2, myFont);
		emptySelectionDialog = new Text(886, 20, "NO ENEMY ON THIS TILE", 2, myFont);
		mySelectionDialog = new Text(900, 20, " ", 2, myFont);
		myCostDialog = new Text(900, 20, " ", 2, myFont);
		
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont, myBlueFont, myBackground, myHoverBackground, myState, "main");
		earth = new StateChangeButton(924, 20, "EARTH", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "earth");
		air = new StateChangeButton(1054, 20, "AIR", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "air");
		water = new StateChangeButton(914, 90, "WATER", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "water");
		fire = new StateChangeButton(1044, 90, "FIRE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "fire");
		subBack = new StateChangeButton(984, 600, "BACK", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "main");
		cancel = new StateChangeButton(964, 600, "CANCEL", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "main");
		boulderFall = new StateChangeButton(912, 20, "BOULDERFALL", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "boulderfall");
		
		myObjects.add(back);
		myObjects.add(earth);
		myObjects.add(air);
		myObjects.add(water);
		myObjects.add(fire);
	}
	public void managePlayerAttack() {
		myObjects.clear();
		if(!myAttack.getState().equals("main")){
			myPlayer.setCommand("boulderfall");
			myObjects.add(cancel);
			myObjects.add(mySelectionDialog);
			myObjects.add(myCostDialog);

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
			else if(myCells.get(xLoc).get(yLoc).getEnemy()==null){
				mySelectionDialog = emptySelectionDialog;
					myCostDialog = new Text(900, 60, " ", 2, myFont);
			}
			else if(myCells.get(xLoc).get(yLoc).getCost()!=0){
				mySelectionDialog = new Text(900, 20, myAttack.getState().toUpperCase() + ":", 2, myFont);
				myCostDialog = new Text(900, 60, 
						myCells.get(xLoc).get(yLoc).getCost() + " " + myCells.get(xLoc).get(yLoc).getCostType().toUpperCase(), 2, myFont);
			}
			else{
				mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 60, " ", 2, myFont);
			}
			return;
		}
		
		if(myAttackType.getState().equals("main")){
			myObjects.add(back);
			myObjects.add(earth);
			myObjects.add(air);
			myObjects.add(water);
			myObjects.add(fire);
		}
		else{
			myObjects.add(subBack);
			if(myAttackType.getState().equals("earth")){
				myObjects.add(boulderFall);
			}
		}
	}
	public void manageState() {
		if(!myPlayer.actionPrepared() && !myState.getState().equals("main")){
			myAttack.setState("main");
			myAttackType.setState("main");
		}
	}
}