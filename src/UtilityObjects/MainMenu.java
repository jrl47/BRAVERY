package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;

public class MainMenu extends SubMenu{

	private StateChangeButton moveMenuOpen;
	private StateChangeButton attackMenuOpen;
	
	public MainMenu(Stage stage, State state, TileObjectInfoHandler handler) {
		super(stage, state);
		moveMenuOpen = new StateChangeButton(984, 20, "MOVE", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "move");
		attackMenuOpen = new StateChangeButton(964, 90, "ATTACK", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "attack");
		
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
	}

	@Override
	public void manageInfo() {
	}

}