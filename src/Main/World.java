package Main;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Utilities.State;
import GameObjects.Background;
import GameObjects.BorderedButton;
import GameObjects.GameObject;
import GameObjects.Menu;
import GameObjects.Player;
import GameObjects.Stage;
import GameObjects.StateChangeButton;


public class World extends GameObject{
	
	public static final int TICK_SCALAR = 30;
	private State myState;
	private String oldState;
	private Stage myStage;
	private Player myPlayer;
	private Menu myMenu;
	
	public World() {
		myState = new State("main");
		oldState = "main";
		myPlayer = new Player();
		Background b = null;
		BorderedButton t = null;
		try {
			b = new Background(ImageIO.read(World.class.getResource("/titleBackground.png")));
			t = new StateChangeButton(464, 268, "BRAVERY", 6, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "game");
		} catch (IOException e) {
			e.printStackTrace();
		}
		myActiveObjects.add(b);
		myActiveObjects.add(t);
	}
	
	public void step() {
		manageState();
		for(GameObject g: myActiveObjects){
			g.step();
		}
	}

	private void manageState() {
		if(myState.getState().equals("game") && !oldState.equals("game")){
			oldState = "game";
			myActiveObjects.clear();
			
			Background b = null;
			StateChangeButton moveMenuOpen = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
				myStage = new Stage(myPlayer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			myMenu = new Menu(myStage);
			myActiveObjects.add(b);
			myActiveObjects.add(myStage);
			myActiveObjects.add(myMenu);
		}
		
	}
	
	public List<GameObject> getComponents() {
		return myActiveObjects;
	}

	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	public Player getPlayer() {
		return myPlayer;
	}
}
