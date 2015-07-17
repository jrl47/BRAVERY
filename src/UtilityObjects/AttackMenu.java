package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;
import Utilities.ValidAttackChecker;
import UtilitiesData.SkillBuilder;
import UtilitiesData.SkillData;

public class AttackMenu extends SubMenu{

	private Text mySelectionDialog;
	private Text myNameDialog;
	private Text noSelectionDialog;
	private Text emptySelectionDialog;
	private Text myCostDialog;
	private Text myPowerDialog;
	private Text emptyDialog;

	private StateChangeButton earth;
	private StateChangeButton air;
	private StateChangeButton water;
	private StateChangeButton fire;
	
	private StateChangeButton subBack;
	private StateChangeButton cancel;
	private State myAttackType;
	private State myAttack;
	
	private StateChangeButton earthSkill0;
	private StateChangeButton airSkill0;
	private StateChangeButton waterSkill0;
	private StateChangeButton fireSkill0;
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
		
		earth = new StateChangeButton(874, 10, "EARTH", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "earth");
		air = new StateChangeButton(966, 10, "AIR", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "air");
		water = new StateChangeButton(1026, 10, "WATER", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "water");
		fire = new StateChangeButton(1118, 10, "FIRE", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "fire");
		subBack = new StateChangeButton(1130, 400, "BACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, myAttackType, "main");
		cancel = new StateChangeButton(1102, 400, "CANCEL", 2, myFont,myBlueFont, myBackground, myHoverBackground, myAttack, "main");
		earthSkill0 = new StateChangeButton(892, 20, SkillBuilder.getSkill(0, "earth").getName().toUpperCase(),
				3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, SkillBuilder.getSkill(0, "earth").getName().toLowerCase());
		airSkill0 = new StateChangeButton(892, 20, SkillBuilder.getSkill(0, "air").getName().toUpperCase(),
				3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, SkillBuilder.getSkill(0, "air").getName().toLowerCase());
		waterSkill0 = new StateChangeButton(892, 20, SkillBuilder.getSkill(0, "water").getName().toUpperCase(),
				3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, SkillBuilder.getSkill(0, "water").getName().toLowerCase());
		fireSkill0 = new StateChangeButton(892, 20, SkillBuilder.getSkill(0, "fire").getName().toUpperCase(),
				3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, SkillBuilder.getSkill(0, "fire").getName().toLowerCase());

		myObjects.add(earth);
		myObjects.add(air);
		myObjects.add(water);
		myObjects.add(fire);
	}
	public void manageInfo() {
		myObjects.clear();
		if(!myAttack.getState().equals("main")){
			myObjects.add(cancel);
			myObjects.add(myNameDialog);
			myObjects.add(mySelectionDialog);
			myObjects.add(myCostDialog);
			myObjects.add(myPowerDialog);

			int xLoc = myPlayer.getX() + myPlayer.getTargetX();
			int yLoc = myPlayer.getY() + myPlayer.getTargetY();
			
			myNameDialog = new Text(900, 20, myAttack.getState().toUpperCase() + ":", 2, myFont);
			if(!myPlayer.getAction().getName().toUpperCase().equals("WAIT")){
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
			
			SkillData data = SkillBuilder.getSkill(0, myAttackType.getState());
			Action a = new Action(data);
			myPlayer.setAction(a);
			return;
		}
		else{
			myPlayer.setAction(new Action("wait"));
		}
		
		if(myAttackType.getState().equals("main")){
			myObjects.add(earth);
			myObjects.add(air);
			myObjects.add(water);
			myObjects.add(fire);
		}
		else{
			myObjects.add(subBack);
			if(myAttackType.getState().equals("earth")){
				if(myPlayer.getSkills().contains(earthSkill0.getState()))
					myObjects.add(earthSkill0);
			}
			if(myAttackType.getState().equals("air")){
				if(myPlayer.getSkills().contains(airSkill0.getState()))
					myObjects.add(airSkill0);
			}
			if(myAttackType.getState().equals("water")){
				if(myPlayer.getSkills().contains(waterSkill0.getState()))
					myObjects.add(waterSkill0);
			}
			if(myAttackType.getState().equals("fire")){
				if(myPlayer.getSkills().contains(fireSkill0.getState()))
					myObjects.add(fireSkill0);
			}
		}
	}
	public void manageState() {
		if(!myPlayer.actionPrepared() && (!myAttack.getState().equals("main")|| !myAttackType.getState().equals("main"))){
			myAttack.setState("main");
			myAttackType.setState("main");
		}
		if(myPlayer.getAction().getName().equals("wait")){
			myAttack.setState("main");
		}
	}
}