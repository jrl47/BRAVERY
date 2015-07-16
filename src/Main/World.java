package Main;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Utilities.CollectibleBuilder;
import Utilities.EnemyBuilder;
import Utilities.RoomDataBuilder;
import Utilities.SongPlayer;
import Utilities.State;
import GameObjects.Background;
import GameObjects.BorderedButton;
import GameObjects.GameObject;
import GameObjects.Map;
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
	private Map myMap;
	private SongPlayer myMusic;
	private boolean gameLoaded;
	private boolean mapLoaded;
	
	public World() {
		EnemyBuilder.init();
		CollectibleBuilder.init();
		RoomDataBuilder.init();
		myState = new State("main");
		oldState = "main";
		myStage = new Stage();
		myPlayer = new Player(myStage);
		myStage.addPlayer(myPlayer);
		myMusic = new SongPlayer();
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
		myMusic.playWorldTheme();
	}

	private void manageState() {
		if(myState.getState().equals("game") && !oldState.equals("game")){
			oldState = "game";
			myActiveObjects.clear();
			
			Background b = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!gameLoaded){
				myMenu = new Menu(myStage, myState);
				gameLoaded = true;
			}
			myActiveObjects.add(b);
			myActiveObjects.add(myStage);
			myActiveObjects.add(myMenu);
		}
		
		if(myState.getState().equals("map") && !oldState.equals("map")){
			oldState = "map";
			myActiveObjects.clear();
			
			Background b = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/mapBackground.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!mapLoaded){
				myMap = new Map(myStage);
				mapLoaded = true;	
			}
			myActiveObjects.add(b);
			myActiveObjects.add(myMap);
			myActiveObjects.add(myMenu);
		}
		
		if(myState.getState().equals("end") && !oldState.equals("end")){
			oldState = "end";
			myActiveObjects.clear();
			Background b = null;
			try {
				b = new Background(ImageIO.read(World.class.getResource("/gameOverBackground.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			myActiveObjects.add(b);
		}
		if(myStage.isGameOver()){
			myState.setState("end");
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
