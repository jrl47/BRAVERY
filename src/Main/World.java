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
import GameObjects.GameStartButton;
import GameObjects.Player;
import GameObjects.Stage;
import GameObjects.Text;


public class World {
	
	public static final int TICK_SCALAR = 30;
	private List<GameObject> myObjects;
	private State myState;
	private State menuState;
	private Stage myStage;
	private Player myPlayer;
	
	public World() {
		myObjects = new ArrayList<GameObject>();
		myState = new State("main");
		menuState = new State("main");
		Background b = null;
		BorderedButton t = null;
		try {
			b = new Background(ImageIO.read(World.class.getResource("/titleBackground.png")));
			t = new GameStartButton(464, 268, "BRAVERY", 6, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState);
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
		if(myState.getState().equals("game")){
			for(GameObject g: myObjects){
				g.turnOff();
			}
			myObjects.clear();
			
			myPlayer = new Player();
			Background b = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
				myStage = new Stage(myPlayer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			b.turnOn();
			myStage.turnOn();
			myObjects.add(b);
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
