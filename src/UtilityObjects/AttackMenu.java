package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Utilities.State;

public class AttackMenu extends SubMenu{

	StateChangeButton back;
	StateChangeButton earth;
	StateChangeButton air;
	StateChangeButton water;
	StateChangeButton fire;
	State myAttackType;
	public AttackMenu(Stage stage, State state) {
		super(stage, state);
		myAttackType = new State("main");
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont, myBlueFont, myBackground, myHoverBackground, myState, "main");
		earth = new StateChangeButton(924, 20, "EARTH", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "earth");
		air = new StateChangeButton(1054, 20, "AIR", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "air");
		water = new StateChangeButton(914, 90, "WATER", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "water");
		fire = new StateChangeButton(1044, 90, "FIRE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myAttackType, "fire");
		
		myObjects.add(back);
		myObjects.add(earth);
		myObjects.add(air);
		myObjects.add(water);
		myObjects.add(fire);
	}
}