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
import Main.World;
import Utilities.State;

public class SubMenu {

	protected BufferedImage myFont;
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
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<GameObject> getObjects() {
		return myObjects;
	}
}
