package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Utilities.State;

public class PlaneMenu extends SubMenu{

	private StateChangeButton back;
	
	public PlaneMenu(Stage stage, State state) {
		super(stage, state);
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "main");
		
		myObjects.add(back);
	}

	@Override
	public void manageInfo() {
		// TODO Auto-generated method stub
		
	}

}
