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
		moveMenuOpen = new StateChangeButton(712, 660, "MOVE", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "move");
		attackMenuOpen = new StateChangeButton(790, 660, "ATTACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "attack");
		planeMenuOpen = new StateChangeButton(896, 660, "SHIFT", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "plane");
		
		mapOpen = new StateChangeButton(990, 660, "MAP", 2, myFont,myBlueFont, myBackground, myHoverBackground, gameState, "map");
		mapClose = new StateChangeButton(988, 660, "BACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, gameState, "game");
		
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
		if(myPlayer.getGenericSkills().contains("planeshift"))
			myObjects.add(planeMenuOpen);
		myObjects.add(mapOpen);
	}

	@Override
	public void manageInfo() {
		myObjects.clear();
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
		if(myPlayer.getGenericSkills().contains("planeshift"))
			myObjects.add(planeMenuOpen);
		if(gameState.getState().equals("game")){
			myObjects.add(mapOpen);
		}
		if(gameState.getState().equals("map")){
			myPlayer.stopAction();
			myObjects.add(mapClose);
		}
	}

}
