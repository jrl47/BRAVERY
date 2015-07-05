package UtilityObjects;

import java.io.IOException;

import javax.imageio.ImageIO;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import Main.World;
import Utilities.State;

public class AttackMenu extends SubMenu{

	StateChangeButton back;
	public AttackMenu(Stage stage, State state) {
		super(stage, state);
		try {
			back = new StateChangeButton(984, 550, "BACK", 3, myFont,
					ImageIO.read(World.class.getResource("/bluefonts.png")),
					ImageIO.read(World.class.getResource("/textbackground.png")),
					ImageIO.read(World.class.getResource("/textbackgroundhover.png")), myState, "main");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		myObjects.add(back);
	}

}
