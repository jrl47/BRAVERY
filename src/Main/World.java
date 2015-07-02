package Main;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Utilities.State;
import GameObjects.Background;
import GameObjects.BorderedButton;
import GameObjects.BorderedText;
import GameObjects.Button;
import GameObjects.GameObject;
import GameObjects.Player;
import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;


public class World {
	
	public static final int TICK_SCALAR = 30;
	private List<GameObject> myObjects;
	private State myState;
	private String oldState;
	private State menuState;
	private Stage myStage;
	private Player myPlayer;
	
	public World() {
		myObjects = new ArrayList<GameObject>();
		myState = new State("main");
		oldState = "main";
		menuState = new State("main");
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
		b.turnOn();
		t.turnOn();
		myObjects.add(b);
		myObjects.add(t);
	}
	
	public void step() {
		manageState();
		for(GameObject g: myObjects){
			if(g.isOn()){
				g.step();
			}
		}
	}
	private void manageState() {
		if(myState.getState().equals("game") && !oldState.equals("game")){
			oldState = "game";
			for(GameObject g: myObjects){
				g.turnOff();
			}
			myObjects.clear();
			
			myPlayer = new Player();
			Background b = null;
			StateChangeButton moveMenuOpen = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
				moveMenuOpen = new StateChangeButton(1000, 50, "MOVE", 3, ImageIO.read(World.class.getResource("/fonts.png")),
						ImageIO.read(World.class.getResource("/bluefonts.png")),
						ImageIO.read(World.class.getResource("/textbackground.png")),
						ImageIO.read(World.class.getResource("/textbackgroundhover.png")), menuState, "move");
				myStage = new Stage(myPlayer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			b.turnOn();
			moveMenuOpen.turnOn();
			myStage.turnOn();
			myObjects.add(b);
			myObjects.add(moveMenuOpen);
			myObjects.add(myStage);
		}
		
	}

	public void draw(Graphics g) {
		for(GameObject go: myObjects){
			if(go.isOn())
				go.draw(g);
		}
	}
	public List<GameObject> getComponents() {
		return myObjects;
	}
}
