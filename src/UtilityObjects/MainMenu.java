package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Utilities.State;

public class MainMenu extends SubMenu{

	StateChangeButton moveMenuOpen;
	StateChangeButton attackMenuOpen;
	public MainMenu(Stage stage, State state, TileObjectInfoHandler handler) {
		super(stage, state);
		moveMenuOpen = new StateChangeButton(984, 20, "MOVE", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "move");
		attackMenuOpen = new StateChangeButton(964, 90, "ATTACK", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "attack");
		
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
	}

}
