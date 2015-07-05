package GameObjects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import Main.World;
import Utilities.State;
import UtilityObjects.AttackMenu;
import UtilityObjects.InventoryHandler;
import UtilityObjects.MainMenu;
import UtilityObjects.MoveMenu;
import UtilityObjects.SubMenu;
import UtilityObjects.TileObjectInfoHandler;

public class Menu extends GameObject{
	private State myState;
	private State oldState;
	private Player myPlayer;
	private Stage myStage;
	private List<List<MapCell>> myCells;
	
	private HashMap<String, SubMenu> mySubMenus;
	
	private TileObjectInfoHandler myTileHandler;
	private InventoryHandler myInventoryHandler;
	
	private BufferedImage myFont;
	
	public Menu(Stage s){
		myBounds = new Rectangle(928, 0, 272, 675);
		myStage = s;
		myCells = myStage.getCells();
		myPlayer = myStage.getPlayer();
		myState = new State("main");
		oldState = new State("main");
		myTileHandler = new TileObjectInfoHandler(myStage, myState);
		myInventoryHandler = new InventoryHandler(myStage, myState);
		mySubMenus = new HashMap<String, SubMenu>();
		try {
			myFont = ImageIO.read(World.class.getResource("/fonts.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		mySubMenus.put("main", new MainMenu(myStage, myState, myTileHandler));
		mySubMenus.put("move", new MoveMenu(myStage, myState));
		mySubMenus.put("attack", new AttackMenu(myStage, myState));
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		manageState();
		manageActiveObjects();
		myTileHandler.manageTileInfo();
		myInventoryHandler.manageInventory();
		((MoveMenu)mySubMenus.get("move")).managePlayerMovement(oldState);
	}

	private void manageActiveObjects() {
		myActiveObjects = new ArrayList<GameObject>();
		myActiveObjects.addAll(mySubMenus.get(myState.getState()).getObjects());
		if(myState.getState().equals("main")){
			myActiveObjects.addAll(myTileHandler.getObjects());
		}
		if(myState.getState().equals("move") || myState.getState().equals("attack")){
			myActiveObjects.addAll(myInventoryHandler.getObjects());
		}
	}

	private void manageState() {
		if(!oldState.equals(myState.getState())){
			if(myState.getState().equals("main")){
				myPlayer.stopAction();
			}
			if(myState.getState().equals("move")){
				myPlayer.prepareMove();
			}
			if(myState.getState().equals("attack")){
				myPlayer.prepareAttack();
			}
			oldState = new State(myState.getState());
		}
	}
	
}
