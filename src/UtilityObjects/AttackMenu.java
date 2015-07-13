package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;
import Utilities.ValidAttackChecker;

public class AttackMenu extends SubMenu{

	private Text mySelectionDialog;
	private Text myNameDialog;
	private Text noSelectionDialog;
	private Text emptySelectionDialog;
	private Text myCostDialog;
	private Text myPowerDialog;
	private Text emptyDialog;
	
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
	private StateChangeButton skyToss;
	private StateChangeButton cascade;
	private StateChangeButton detonate;
	public AttackMenu(Stage stage, State state) {
		super(stage, state);
		myAttackType = new State("main");
		myAttack = new State("main");
		
		noSelectionDialog = new Text(886, 140, "SELECT A VALID TARGET", 2, myFont);
		emptySelectionDialog = new Text(886, 140, "NO ENEMIES TARGETED", 2, myFont);
		myNameDialog = new Text(886, 20, " ", 2, myFont);
		emptyDialog = new Text(900, 20, " ", 2, myFont);
		mySelectionDialog = emptyDialog;
		myCostDialog = emptyDialog;
		myPowerDialog = emptyDialog;
		
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont, myBlueFont, myBackground, myHoverBackground, myState, "main");
		earth = new StateChangeButton(924, 20, "EARTH", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "earth");
		air = new StateChangeButton(1054, 20, "AIR", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "air");
		water = new StateChangeButton(914, 90, "WATER", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "water");
		fire = new StateChangeButton(1044, 90, "FIRE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "fire");
		subBack = new StateChangeButton(984, 600, "BACK", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "main");
		cancel = new StateChangeButton(964, 600, "CANCEL", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "main");
		boulderFall = new StateChangeButton(912, 20, "BOULDERFALL", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "boulderfall");
		skyToss = new StateChangeButton(952, 20, "SKYTOSS", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "skytoss");
		cascade = new StateChangeButton(952, 20, "CASCADE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "cascade");
		detonate = new StateChangeButton(942, 20, "DETONATE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, "detonate");
		
		myObjects.add(back);
		myObjects.add(earth);
		myObjects.add(air);
		myObjects.add(water);
		myObjects.add(fire);
	}
	public void manageInfo() {
		myObjects.clear();
		if(!myAttack.getState().equals("main")){
			myPlayer.setCommand(myAttack.getState());
			myObjects.add(cancel);
			myObjects.add(myNameDialog);
			myObjects.add(mySelectionDialog);
			myObjects.add(myCostDialog);
			myObjects.add(myPowerDialog);

			int xLoc = myPlayer.getX() + myPlayer.getTargetX();
			int yLoc = myPlayer.getY() + myPlayer.getTargetY();
			
			myNameDialog = new Text(900, 20, myAttack.getState().toUpperCase() + ":", 2, myFont);
			if(!myPlayer.getAction().getType().toUpperCase().equals("WAIT")){
				myCostDialog = new Text(900, 60, 
					myPlayer.getAction().getCost() + " " + myPlayer.getAction().getType().toUpperCase() + " ENERGY", 2, myFont);
			}
			else{
				myCostDialog = new Text(900, 60, 
						"NOT ENOUGH ENERGY", 2, myFont);
			}
			myPowerDialog = new Text(900, 100, 
					myPlayer.getAction().getPower() +  " POWER", 2, myFont);
			
			if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
				mySelectionDialog = noSelectionDialog;
			}
			else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
				mySelectionDialog = noSelectionDialog;
			}
			else if(!ValidAttackChecker.detectAttackTargets(xLoc, yLoc, myPlayer, myCells)){
				mySelectionDialog = emptySelectionDialog;
			}
			else if(myPlayer.getAction().getCost()!=0){
				mySelectionDialog = new Text(900, 140, " ", 2, myFont);
			}
			else{
				mySelectionDialog = noSelectionDialog;
				myCostDialog = emptyDialog;
				myPowerDialog = emptyDialog;
			}
			
			if(myPlayer.getCommand().equals("boulderfall")){
				if(!(myPlayer.getInventory().getEarth()<5)){
					Action a = new Action(5, "earth", 3, 1, true);
					myPlayer.setAction(a);
				}
			}
			if(myPlayer.getCommand().equals("skytoss")){
				if(!(myPlayer.getInventory().getAir()<5)){
					Action a = new Action(5, "air", 2, 2, true);
					myPlayer.setAction(a);
				}
			}
			if(myPlayer.getCommand().equals("cascade")){
				if(!(myPlayer.getInventory().getWater()<5)){
					Action a = new Action(5, "water", 1, 2, false);
					myPlayer.setAction(a);
				}
			}
			if(myPlayer.getCommand().equals("detonate")){
				if(!(myPlayer.getInventory().getFire()<5)){
					Action a = new Action(5, "fire", 1, 1, true);
					myPlayer.setAction(a);
				}
			}
			
			return;
		}
		else{
			myPlayer.setCommand(null);
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
			if(myAttackType.getState().equals("air")){
				myObjects.add(skyToss);
			}
			if(myAttackType.getState().equals("water")){
				myObjects.add(cascade);
			}
			if(myAttackType.getState().equals("fire")){
				myObjects.add(detonate);
			}
		}
	}
	public void manageState() {
		if(!myPlayer.actionPrepared() && (!myAttack.getState().equals("main")|| !myAttackType.getState().equals("main"))){
			myAttack.setState("main");
			myAttackType.setState("main");
		}
		if(myPlayer.getCommand()==null){
			myAttack.setState("main");
		}
	}
}