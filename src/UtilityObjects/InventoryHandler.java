package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class InventoryHandler extends SubMenu {
	private Text myTitle;
	private Text myHealth;
	private Text myEarthDialog;
	private Text myAirDialog;
	private Text myWaterDialog;
	private Text myFireDialog;
	
	public InventoryHandler(Stage stage, State state) {
		super(stage, state);
		myTitle = new Text(900, 540, "ENERGY MENU", 1.5, myFont);
		myHealth = new Text(1050, 540, myPlayer.getHealth() + "/" + myPlayer.getMaxHealth() + " HEALTH", 1.5, myFont);
		myEarthDialog = new Text(900, 570, myPlayer.getInventory().getEarth() + " EARTH", 1.5, myFont);
		myAirDialog = new Text(900, 600, myPlayer.getInventory().getAir() + " AIR", 1.5, myFont);
		myWaterDialog = new Text(1050, 570, myPlayer.getInventory().getWater() + " WATER", 1.5, myFont);
		myFireDialog = new Text(1050, 600, myPlayer.getInventory().getFire() + " FIRE", 1.5, myFont);
		myObjects.add(myTitle);
		myObjects.add(myHealth);
		myObjects.add(myEarthDialog);
		myObjects.add(myAirDialog);
		myObjects.add(myWaterDialog);
		myObjects.add(myFireDialog);
	}
	public void manageInfo() {
		myObjects.clear();
		myEarthDialog = new Text(900, 570, myPlayer.getInventory().getEarth() + " EARTH", 1.5, myFont);
		myAirDialog = new Text(900, 600, myPlayer.getInventory().getAir() + " AIR", 1.5, myFont);
		myWaterDialog = new Text(1050, 570, myPlayer.getInventory().getWater() + " WATER", 1.5, myFont);
		myFireDialog = new Text(1050, 600, myPlayer.getInventory().getFire() + " FIRE", 1.5, myFont);
		myObjects.add(myTitle);
		myObjects.add(myHealth);
		myObjects.add(myEarthDialog);
		myObjects.add(myAirDialog);
		myObjects.add(myWaterDialog);
		myObjects.add(myFireDialog);
	}

}
