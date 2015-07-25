package UtilityObjects;

import java.util.HashSet;
import java.util.Set;

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
	private Text notEnoughDialog;
	private Text myCostDialog;
	private Text costWarningDialog;
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
	
	private Set<StateChangeButton> myAttackButtons;
	
	public AttackMenu(Stage stage, State state) {
		super(stage, state);
		myAttackType = new State("main");
		myAttack = new State("main");
		
		myAttackButtons = new HashSet<StateChangeButton>();
		
		noSelectionDialog = new Text(730, 80, "SELECT A VALID TARGET", 1.5, myFont);
		emptySelectionDialog = new Text(730, 80, "NO ENEMIES TARGETED", 1.5, myFont);
		notEnoughDialog = new Text(850, 50, "NOT ENOUGH ENERGY", 1.5, myFont);
		myNameDialog = new Text(736, 20, " ", 1.5, myFont);
		emptyDialog = new Text(750, 20, " ", 1.5, myFont);
		mySelectionDialog = emptyDialog;
		myCostDialog = emptyDialog;
		myPowerDialog = emptyDialog;
		costWarningDialog = emptyDialog;
		
		earth = new StateChangeButton(724, 10, "EARTH", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "earth");
		air = new StateChangeButton(816, 10, "AIR", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "air");
		water = new StateChangeButton(876, 10, "WATER", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "water");
		fire = new StateChangeButton(968, 10, "FIRE", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "fire");
		subBack = new StateChangeButton(990, 455, "BACK", 1.5, myFont,myBlueFont, myBackground, myHoverBackground, myAttackType, "main");
		cancel = new StateChangeButton(980, 455, "CANCEL", 1.5, myFont,myBlueFont, myBackground, myHoverBackground, myAttack, "main");

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
			myObjects.add(costWarningDialog);
			
			int xLoc = myPlayer.getX() + myPlayer.getTargetX();
			int yLoc = myPlayer.getY() + myPlayer.getTargetY();
			
			SkillData data = SkillBuilder.getSkill(Integer.parseInt(myAttack.getState()), myAttackType.getState());
			myNameDialog = new Text(730, 20, data.getName().toUpperCase() + ":", 1.5, myFont);
			myCostDialog = new Text(860, 20, 
					myPlayer.getAction().getCost() + " " + myPlayer.getAction().getType().toUpperCase() + " ENERGY", 1.5, myFont);
			if(!myPlayer.canAfford(data.getCost(), data.getType().toLowerCase())){
				costWarningDialog = notEnoughDialog;
			}else{
				costWarningDialog = emptyDialog;
			}
			
			myPowerDialog = new Text(730, 50, 
					myPlayer.getAction().getPower() +  " POWER", 1.5, myFont);
			
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
				mySelectionDialog = new Text(750, 80, " ", 2, myFont);
			}
			else{
				mySelectionDialog = noSelectionDialog;
				myCostDialog = emptyDialog;
				myPowerDialog = emptyDialog;
			}
			
			Action a = new Action(data);
			myPlayer.setAction(a);
			return;
		}
		myPlayer.setAction(new Action("wait"));
		
		if(myAttackType.getState().equals("main")){
			myObjects.add(earth);
			myObjects.add(air);
			myObjects.add(water);
			myObjects.add(fire);
			myAttackButtons.clear();
		}
		else{
			myObjects.add(subBack);
			for(SkillData data: myPlayer.getEarthSkills()){
				addAttackButton(data);
			}
			for(SkillData data: myPlayer.getAirSkills()){
				addAttackButton(data);
			}
			for(SkillData data: myPlayer.getWaterSkills()){
				addAttackButton(data);
			}
			for(SkillData data: myPlayer.getFireSkills()){
				addAttackButton(data);
			}
			myObjects.addAll(myAttackButtons);
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
	public void addAttackButton(SkillData data){
		if(data.getType().equals(myAttackType.getState())){
			myAttackButtons.add(new StateChangeButton(726, 10 + (data.getIndex() * 65), data.getName().toUpperCase(),
					3, myFont, myBlueFont, myBackground, myHoverBackground, myAttack, ""+data.getAbsoluteIndex()));
		}
	}
}