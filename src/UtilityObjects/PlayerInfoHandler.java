package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class PlayerInfoHandler extends SubMenu{
	
	private Text myHealth;
	
	public PlayerInfoHandler(Stage stage, State state) {
		super(stage, state);
		myHealth = new Text(974, 230, "HP: " + myPlayer.getHealth(), 3, myFont);
		
		myObjects.add(myHealth);
	}
	public void manageInfo() {
		myObjects.remove(myHealth);
		myHealth = new Text(974, 230, "HP: " + myPlayer.getHealth(), 3, myFont);
		myObjects.add(myHealth);
	}
}