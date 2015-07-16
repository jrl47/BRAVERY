package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;

public class MainMenu extends SubMenu{

	private StateChangeButton moveMenuOpen;
	private StateChangeButton attackMenuOpen;
	private StateChangeButton planeMenuOpen;
	private StateChangeButton mapOpen;
	private StateChangeButton mapClose;
	private State gameState;
	
	public MainMenu(Stage stage, State state, TileObjectInfoHandler handler, State gamestate) {
		super(stage, state);
		gameState = gamestate;
		moveMenuOpen = new StateChangeButton(908, 20, "MOVE", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "move");
		attackMenuOpen = new StateChangeButton(1018, 20, "ATTACK", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "attack");
		planeMenuOpen = new StateChangeButton(900, 90, "SHIFT PLANES", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "plane");
		
		mapOpen = new StateChangeButton(1142, 630, "MAP", 2, myFont,myBlueFont, myBackground, myHoverBackground, gameState, "map");
		mapClose = new StateChangeButton(1130, 630, "BACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, gameState, "game");
		
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
		if(myPlayer.getSkills().contains("planeshift"))
			myObjects.add(planeMenuOpen);
		myObjects.add(mapOpen);
	}

	@Override
	public void manageInfo() {
		myObjects.clear();
		if(gameState.getState().equals("game")){
			myObjects.add(moveMenuOpen);
			myObjects.add(attackMenuOpen);
			if(myPlayer.getSkills().contains("planeshift"))
				myObjects.add(planeMenuOpen);
			myObjects.add(mapOpen);
		}
		if(gameState.getState().equals("map")){
			myObjects.add(mapClose);
		}
	}

}
