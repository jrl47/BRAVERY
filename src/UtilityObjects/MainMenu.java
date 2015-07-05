package UtilityObjects;

import java.io.IOException;

import javax.imageio.ImageIO;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Main.World;
import Utilities.State;

public class MainMenu extends SubMenu{

	StateChangeButton moveMenuOpen;
	StateChangeButton attackMenuOpen;
	public MainMenu(Stage stage, State state, TileObjectInfoHandler handler) {
		super(stage, state);
		try {
			moveMenuOpen = new StateChangeButton(984, 50, "MOVE", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "move");
			attackMenuOpen = new StateChangeButton(964, 120, "ATTACK", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "attack");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		myObjects.add(moveMenuOpen);
		myObjects.add(attackMenuOpen);
	}

}
