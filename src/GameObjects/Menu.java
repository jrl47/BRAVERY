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
	
	private HashMap<String, List<GameObject>> mySubMenus;

	private Text mySelectionDialog;
	private Text noSelectionDialog;
	
	
	public Menu(Player p){
		myBounds = new Rectangle(928, 0, 272, 675);
		myPlayer = p;
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
		if(!myPlayer.movePrepared() && myState.getState().equals("move")){
			myState.setState("main");
			oldState = "main";
		}

		if(myState.getState().equals("move")){
			if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
				mySelectionDialog = noSelectionDialog;
			}
			else if(myPlayer.getTargetX()==0 || myPlayer.getTargetY()==0){
				try {
					mySelectionDialog = new Text(900, 50, "ORTHOGONAL MOVE", 2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else if(myPlayer.getTargetX()==myPlayer.getTargetY() || myPlayer.getTargetX()==-myPlayer.getTargetY()){
				try {
					mySelectionDialog = new Text(900, 50, "DIAGONAL MOVE", 2, ImageIO.read(World.class.getResource("/fonts.png")));
				} catch (IOException e) {}
			}
			else{
				mySelectionDialog = noSelectionDialog;
			}
			myActiveObjects.add(mySelectionDialog);
		}
	}
	
}
