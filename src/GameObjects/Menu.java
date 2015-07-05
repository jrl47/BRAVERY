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

public class Menu extends GameObject{
	private State myState;
	private String oldState;
	private Player myPlayer;
	private Stage myStage;
	private List<List<MapCell>> myCells;
	
	private HashMap<String, List<GameObject>> mySubMenus;

	
	private Text myTileObjectInfo1;
	private Text myTileObjectInfo2;
	
	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text myCostDialog;
	
	private Text myAvailableDialog;
	private Text myEarthDialog;
	private Text myAirDialog;
	private Text myWaterDialog;
	private Text myFireDialog;
	
	private BufferedImage myFont;
	
	public Menu(Stage s){
		myBounds = new Rectangle(928, 0, 272, 675);
		myStage = s;
		myCells = myStage.getCells();
		myPlayer = myStage.getPlayer();
		myState = new State("main");
		oldState = "main";
		mySubMenus = new HashMap<String, List<GameObject>>();
		try {
			myFont = ImageIO.read(World.class.getResource("/fonts.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		StateChangeButton moveMenuOpen = null;
		
		StateChangeButton back = null;
		try {
			moveMenuOpen = new StateChangeButton(984, 50, "MOVE", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "move");
			back = new StateChangeButton(984, 550, "BACK", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "main");
		} catch (IOException e) {
			e.printStackTrace();
		}
		myTileObjectInfo1 = new Text(900, 400, " ", 2, myFont);
		myTileObjectInfo2 = new Text(900, 440, " ", 2, myFont);
		
		noSelectionDialog = new Text(900, 50, "SELECT A VALID MOVE", 2, myFont);
		myAvailableDialog = new Text(900, 300, "AVAILABLE ENERGY:", 2, myFont);
		myEarthDialog = new Text(900, 340, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 2, myFont);
		myAirDialog = new Text(900, 380, myPlayer.getInventory().getAir() + " AIR ENERGY", 2, myFont);
		myWaterDialog = new Text(900, 420, myPlayer.getInventory().getWater() + " WATER ENERGY", 2, myFont);
		myFireDialog = new Text(900, 460, myPlayer.getInventory().getFire() + " FIRE ENERGY", 2, myFont);
		
		mySubMenus.put("main", new ArrayList<GameObject>());
		mySubMenus.get("main").add(moveMenuOpen);
		mySubMenus.get("main").add(myTileObjectInfo1);
		mySubMenus.get("main").add(myTileObjectInfo2);
		
		mySubMenus.put("move", new ArrayList<GameObject>());
		mySubMenus.get("move").add(back);
		mySubMenus.get("move").add(myAvailableDialog);
		mySubMenus.get("move").add(myEarthDialog);
		mySubMenus.get("move").add(myAirDialog);
		mySubMenus.get("move").add(myWaterDialog);
		mySubMenus.get("move").add(myFireDialog);
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		manageState();
		manageTileInfo();
		manageInventory();
		managePlayerMovement();
	}

	private void manageState() {
		if(!oldState.equals(myState.getState())){
			if(myState.getState().equals("main")){
				myPlayer.stopMove();
			}
			if(myState.getState().equals("move")){
				myPlayer.prepareMove();
			}
			oldState = myState.getState();
		}
		
		myActiveObjects = mySubMenus.get(myState.getState());
	}
	
	private void manageTileInfo() {
		mySubMenus.get("main").remove(myTileObjectInfo1);
		mySubMenus.get("main").remove(myTileObjectInfo2);
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			myTileObjectInfo1 = new Text(900, 400, " ", 2, myFont);
			myTileObjectInfo2 = new Text(900, 440, " ", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCollectible()!=null){
			Collectible c = myCells.get(xLoc).get(yLoc).getCollectible();
			myTileObjectInfo1 = new Text(900, 400, "COLLECTIBLE:", 2, myFont);
			myTileObjectInfo2 = new Text(900, 440, c.getAmount() + " " + c.getType().toUpperCase() + " ENERGY", 2, myFont);
		} else{
			myTileObjectInfo1 = new Text(900, 400, " ", 2, myFont);
			myTileObjectInfo2 = new Text(900, 440, " ", 2, myFont);
		}
		mySubMenus.get("main").add(myTileObjectInfo1);
		mySubMenus.get("main").add(myTileObjectInfo2);

	}
	
	
	private void manageInventory() {
		mySubMenus.get("move").remove(myEarthDialog);
		mySubMenus.get("move").remove(myAirDialog);
		mySubMenus.get("move").remove(myWaterDialog);
		mySubMenus.get("move").remove(myFireDialog);
		myEarthDialog = new Text(900, 340, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 2, myFont);
		myAirDialog = new Text(900, 380, myPlayer.getInventory().getAir() + " AIR ENERGY", 2, myFont);
		myWaterDialog = new Text(900, 420, myPlayer.getInventory().getWater() + " WATER ENERGY", 2, myFont);
		myFireDialog = new Text(900, 460, myPlayer.getInventory().getFire() + " FIRE ENERGY", 2, myFont);
		mySubMenus.get("move").add(myEarthDialog);
		mySubMenus.get("move").add(myAirDialog);
		mySubMenus.get("move").add(myWaterDialog);
		mySubMenus.get("move").add(myFireDialog);
	}

	
	private void managePlayerMovement() {
		myActiveObjects.remove(mySelectionDialog);
		myActiveObjects.remove(myCostDialog);
		if(!myPlayer.movePrepared() && myState.getState().equals("move")){
			myState.setState("main");
			oldState = "main";
		}

		if(!myState.getState().equals("move"))
			return;

		int x = Math.abs(myPlayer.getTargetX());
		int y = Math.abs(myPlayer.getTargetY());
		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
		else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("earth")){
			mySelectionDialog = new Text(900, 50, "ORTHOGONAL MOVE:", 2, myFont);
			if(Math.max(x, y)==1){
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
			else{
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY", 2, myFont);
			}
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("air")){
				mySelectionDialog = new Text(900, 50, "DIAGONAL MOVE:", 2, myFont);
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " AIR ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("water")){
				mySelectionDialog = new Text(900, 50, "SKEW MOVE:", 2, myFont);
				myCostDialog = new Text(900, 90, 
				myCells.get(xLoc).get(yLoc).getCost() + " WATER ENERGY", 2, myFont);
		}
		else if(myCells.get(xLoc).get(yLoc).getCostType().equals("fire")){
			mySelectionDialog = new Text(900, 50, "STRAY MOVE:", 2, myFont);
			myCostDialog = new Text(900, 90, 
			myCells.get(xLoc).get(yLoc).getCost() + " FIRE ENERGY",2, myFont);
		}
		else{
			mySelectionDialog = noSelectionDialog;
			myCostDialog = new Text(900, 90, " ", 2, myFont);
		}
		myActiveObjects.add(mySelectionDialog);
		myActiveObjects.add(myCostDialog);
	}
	
}
