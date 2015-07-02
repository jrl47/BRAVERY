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
	
	public Menu(Player p){
		myBounds = new Rectangle(928, 0, 272, 675);
		myPlayer = p;
		myState = new State("main");
		mySubMenus = new HashMap<String, List<GameObject>>();
		StateChangeButton moveMenuOpen = null;
		try {
			moveMenuOpen = new StateChangeButton(1000, 50, "MOVE", 3, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "move");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mySubMenus.put("main", new ArrayList<GameObject>());
		mySubMenus.get("main").add(moveMenuOpen);
		
		mySubMenus.put("move", new ArrayList<GameObject>());
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		manageState();
	}

	private void manageState() {
		myActiveObjects = mySubMenus.get(myState.getState());
	}

}
