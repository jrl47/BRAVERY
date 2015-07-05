package GameObjects;

import java.awt.Rectangle;
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

	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text myCostDialog;
	
	public Menu(Stage s){
		myBounds = new Rectangle(928, 0, 272, 675);
		myStage = s;
		myCells = myStage.getCells();
		myPlayer = myStage.getPlayer();
		myState = new State("main");
		oldState = "main";
		mySubMenus = new HashMap<String, List<GameObject>>();
		
		StateChangeButton moveMenuOpen = null;
		
		StateChangeButton back = null;
		try {
			moveMenuOpen = new StateChangeButton(984, 50, "MOVE", 3, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "move");
			back = new StateChangeButton(984, 550, "BACK", 3, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "main");
			
			noSelectionDialog = new Text(900, 50, "SELECT A VALID MOVE", 2, ImageIO.read(World.class.getResource("/fonts.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mySubMenus.put("main", new ArrayList<GameObject>());
		mySubMenus.get("main").add(moveMenuOpen);
		
		mySubMenus.put("move", new ArrayList<GameObject>());
		mySubMenus.get("move").add(back);
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		manageState();
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

	
	private void managePlayerMovement() {
		myActiveObjects.remove(mySelectionDialog);
		myActiveObjects.remove(myCostDialog);
		if(!myPlayer.movePrepared() && myState.getState().equals("move")){
			myState.setState("main");
			oldState = "main";
		}

		if(myState.getState().equals("move")){
			int x = Math.abs(myPlayer.getTargetX());
			int y = Math.abs(myPlayer.getTargetY());
			int xLoc = myPlayer.getX() + myPlayer.getTargetX();
			int yLoc = myPlayer.getY() + myPlayer.getTargetY();
			
			if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
				mySelectionDialog = noSelectionDialog;
				try {
					myCostDialog = new Text(900, 90, " ", 2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
				mySelectionDialog = noSelectionDialog;
				try {
					myCostDialog = new Text(900, 90, " ", 2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else if(x==0 || y==0){
				try {
					mySelectionDialog = new Text(900, 50, "ORTHOGONAL MOVE:", 2, ImageIO.read(World.class.getResource("/fonts.png")));
					if(Math.max(x, y)==1){
						myCostDialog = new Text(900, 90, 
								myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY",
								2, ImageIO.read(World.class.getResource("/fonts.png")));
					}
					else{
						myCostDialog = new Text(900, 90, 
								myCells.get(xLoc).get(yLoc).getCost() + " EARTH ENERGY",
								2, ImageIO.read(World.class.getResource("/fonts.png")));
					}
				} catch (IOException e) {}
			}
			else if(x==y){
				try {
					mySelectionDialog = new Text(900, 50, "DIAGONAL MOVE:", 2, ImageIO.read(World.class.getResource("/fonts.png")));
					myCostDialog = new Text(900, 90, 
							myCells.get(xLoc).get(yLoc).getCost() + " AIR ENERGY",
							2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else if(x==2*y || 2*x==y){
				try {
					mySelectionDialog = new Text(900, 50, "SKEW MOVE:", 2, ImageIO.read(World.class.getResource("/fonts.png")));
					myCostDialog = new Text(900, 90, 
							myCells.get(xLoc).get(yLoc).getCost() + " WATER ENERGY",
							2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else if(x==3*y || 3*x==y
					|| 2*x==3*y || 3*x==2*y){
				try {
					mySelectionDialog = new Text(900, 50, "STRAY MOVE:", 2, ImageIO.read(World.class.getResource("/fonts.png")));
					myCostDialog = new Text(900, 90, 
							myCells.get(xLoc).get(yLoc).getCost() + " FIRE ENERGY",
							2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else{
				mySelectionDialog = noSelectionDialog;
				try {
					myCostDialog = new Text(900, 90, " ", 2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			myActiveObjects.add(mySelectionDialog);
			myActiveObjects.add(myCostDialog);
		}
	}
	
}
