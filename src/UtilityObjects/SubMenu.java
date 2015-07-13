package UtilityObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import GameObjects.GameObject;
import GameObjects.MapCell;
import GameObjects.Player;
import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Main.World;
import Utilities.State;

public abstract class SubMenu {

	protected BufferedImage myFont;
	protected BufferedImage myBlueFont;
	protected BufferedImage myBackground;
	protected BufferedImage myHoverBackground;
	protected Stage myStage;
	protected State myState;
	protected Player myPlayer;
	protected List<GameObject> myObjects;
	protected List<List<MapCell>> myCells;
	
	public SubMenu(Stage stage, State state){
		myStage = stage;
		myState = state;
		myPlayer = myStage.getPlayer();
		myObjects = new ArrayList<GameObject>();
		myCells = myStage.getCells();
		try {
			myFont = ImageIO.read(World.class.getResource("/fonts.png"));
			myBlueFont = ImageIO.read(World.class.getResource("/bluefonts.png"));
			myBackground = ImageIO.read(World.class.getResource("/textbackground.png"));
			myHoverBackground = ImageIO.read(World.class.getResource("/textbackgroundhover.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<GameObject> getObjects() {
		return myObjects;
	}
	
	public abstract void manageInfo();
}
