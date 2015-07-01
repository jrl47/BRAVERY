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
import GameObjects.Text;


public class World {
	
	public static final int TICK_SCALAR = 30;
	private List<GameObject> myObjects;
	private State myState;
	
	public World() {
		myObjects = new ArrayList<GameObject>();
		myState = new State("main");
		Background b = null;
		BorderedButton t = null;
		try {
			b = new Background(ImageIO.read(World.class.getResource("/titleBackground.png")));
			t = new GameStartButton(298, 188, "BRAVERY", 4, ImageIO.read(World.class.getResource("/fonts.png")),
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
				myObjects.remove(g);
			}
			
			Background b = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			b.turnOn();
			myObjects.add(b);
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
