package Main;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import Utilities.SongPlayer;
import Utilities.State;
import UtilitiesData.CollectibleBuilder;
import UtilitiesData.CollectibleSkillBuilder;
import UtilitiesData.EnemyBuilder;
import UtilitiesData.RoomDataBuilder;
import UtilitiesData.SkillBuilder;
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
	private State mySoundState;
	private String oldSoundState;
	private boolean soundOn;
	private StateChangeButton soundToggle;
	
	public World() {
		EnemyBuilder.init();
		CollectibleBuilder.init();
		RoomDataBuilder.init();
		SkillBuilder.init();
		CollectibleSkillBuilder.init();
		myState = new State("main");
		mySoundState = new State("false");
		oldSoundState = "false";
		oldState = "main";
		myStage = new Stage();
		myPlayer = new Player(myStage);
		myStage.addPlayer(myPlayer);
		myMusic = new SongPlayer();
		Background b = null;
		BorderedButton t = null;
		soundToggle = null;
		try {
			b = new Background(ImageIO.read(World.class.getResource("/titleBackground.png")));
			t = new StateChangeButton(384, 284, "BRAVERY", 6, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "game");
			soundToggle = new StateChangeButton(928, 660, "SOUND ON", 2, ImageIO.read(World.class.getResource("/fonts.png")),
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), mySoundState, "true");
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
		soundOn = Boolean.parseBoolean(mySoundState.getState());
		if(soundOn){
			myMusic.playTheme(myState.getState());
		} else{
			myMusic.stop();
		}
	}

	private void manageState() {
		if(myState.getState().equals("main")){
			try {
				myActiveObjects.remove(soundToggle);
				if(mySoundState.getState().equals("false") && !oldSoundState.equals("false")){
					oldSoundState = "false";
				soundToggle = new StateChangeButton(928, 660, "SOUND ON", 2, ImageIO.read(World.class.getResource("/fonts.png")),
						ImageIO.read(World.class.getResource("/bluefonts.png")),
						ImageIO.read(World.class.getResource("/textbackground.png")),
						ImageIO.read(World.class.getResource("/textbackgroundhover.png")), mySoundState, "true");
				}
				else if(mySoundState.getState().equals("true") && !oldSoundState.equals("true")){
					oldSoundState = "true";
					soundToggle = new StateChangeButton(914, 660, "SOUND OFF", 2, ImageIO.read(World.class.getResource("/fonts.png")),
							ImageIO.read(World.class.getResource("/bluefonts.png")),
							ImageIO.read(World.class.getResource("/textbackground.png")),
							ImageIO.read(World.class.getResource("/textbackgroundhover.png")), mySoundState, "false");
				}
				myActiveObjects.add(soundToggle);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
