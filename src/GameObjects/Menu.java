package GameObjects;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import Utilities.State;
import UtilityObjects.AttackMenu;
import UtilityObjects.InventoryHandler;
import UtilityObjects.MainMenu;
import UtilityObjects.MoveMenu;
import UtilityObjects.PlaneMenu;
import UtilityObjects.PlayerInfoHandler;
import UtilityObjects.RecentActionHandler;
import UtilityObjects.SubMenu;
import UtilityObjects.TileObjectInfoHandler;

public class Menu extends GameObject{
	private State myState;
	private State oldState;
	private Player myPlayer;
	private Stage myStage;
	private State gameState;
	
	private HashMap<String, SubMenu> mySubMenus;
	
	private TileObjectInfoHandler myTileHandler;
	private InventoryHandler myInventoryHandler;
//	private PlayerInfoHandler myInfoHandler;
	private RecentActionHandler myActionHandler;
	
	public Menu(Stage stage, State gamestate){
		gameState = gamestate;
		myBounds = new Rectangle(928, 0, 272, 675);
		myStage = stage;
		myPlayer = myStage.getPlayer();
		myState = new State("main");
		oldState = new State("main");
		myTileHandler = new TileObjectInfoHandler(myStage, myState);
		myInventoryHandler = new InventoryHandler(myStage, myState);
//		myInfoHandler = new PlayerInfoHandler(myStage, myState);
		myActionHandler = new RecentActionHandler(myStage, myState);
		mySubMenus = new HashMap<String, SubMenu>();
		mySubMenus.put("main", new MainMenu(myStage, myState, myTileHandler, gameState));
		mySubMenus.put("move", new MoveMenu(myStage, myState));
		mySubMenus.put("attack", new AttackMenu(myStage, myState));
		mySubMenus.put("plane", new PlaneMenu(myStage, myState));
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		manageState();
		myTileHandler.manageInfo();
		myInventoryHandler.manageInfo();
//		myInfoHandler.manageInfo();
		myActionHandler.manageInfo();
		if(myState.getState().equals("main")){
			mySubMenus.get("main").manageInfo();
		}
		if(myState.getState().equals("move")){
			mySubMenus.get("move").manageInfo();
		}
		if(myState.getState().equals("attack")){
			mySubMenus.get("attack").manageInfo();
		}
		if(myState.getState().equals("plane")){
			mySubMenus.get("plane").manageInfo();
		}
		((AttackMenu)mySubMenus.get("attack")).manageState();
		manageActiveObjects();
	}

	private void manageActiveObjects() {
		myActiveObjects = new ArrayList<GameObject>();
		myActiveObjects.addAll(mySubMenus.get(myState.getState()).getObjects());
		if(gameState.getState().equals("game")){
			if(myState.getState().equals("main")){
				myActiveObjects.addAll(myTileHandler.getObjects());
//				myActiveObjects.addAll(myInfoHandler.getObjects());
				myActiveObjects.addAll(myActionHandler.getObjects());
				myActiveObjects.addAll(myInventoryHandler.getObjects());
			}
			if(myState.getState().equals("move") || myState.getState().equals("attack") || myState.getState().equals("plane")){
				myActiveObjects.addAll(myInventoryHandler.getObjects());
				myActiveObjects.addAll(myTileHandler.getObjects());
			}
		}
	}

	private void manageState() {
		if(!oldState.getState().equals(myState.getState())){
			if(myState.getState().equals("main")){
				myPlayer.stopAction();
			}
			if(myState.getState().equals("move")){
				myPlayer.prepareMove();
			}
			if(myState.getState().equals("attack")){
				myPlayer.prepareAttack();
			}
			if(myState.getState().equals("plane")){
				myPlayer.preparePlane();
			}
			oldState = new State(myState.getState());
			return;
		}
		if(!myPlayer.actionPrepared() && !myState.getState().equals("main")){
			myState.setState("main");
			oldState = new State(myState.getState());
		}
	}
	
}
