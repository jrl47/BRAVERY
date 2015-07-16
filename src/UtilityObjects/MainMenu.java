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
		moveMenuOpen = new StateChangeButton(872, 630, "MOVE", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "move");
		attackMenuOpen = new StateChangeButton(944, 630, "ATTACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "attack");
		planeMenuOpen = new StateChangeButton(1044, 630, "SHIFT", 2, myFont,myBlueFont, myBackground, myHoverBackground, myState, "plane");
		
		mapOpen = new StateChangeButton(1130, 630, "MAP", 2, myFont,myBlueFont, myBackground, myHoverBackground, gameState, "map");
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
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
		if(myPlayer.getSkills().contains("planeshift"))
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
