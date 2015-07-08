package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class InventoryHandler extends SubMenu {
	private Text myAvailableDialog;
	private Text myEarthDialog;
	private Text myAirDialog;
	private Text myWaterDialog;
	private Text myFireDialog;
	
	public InventoryHandler(Stage stage, State state) {
		super(stage, state);
		myAvailableDialog = new Text(900, 440, "AVAILABLE ENERGY:", 1.5, myFont);
		myEarthDialog = new Text(900, 470, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 1.5, myFont);
		myAirDialog = new Text(900, 500, myPlayer.getInventory().getAir() + " AIR ENERGY", 1.5, myFont);
		myWaterDialog = new Text(900, 530, myPlayer.getInventory().getWater() + " WATER ENERGY", 1.5, myFont);
		myFireDialog = new Text(900, 560, myPlayer.getInventory().getFire() + " FIRE ENERGY", 1.5, myFont);
		
		myObjects.add(myAvailableDialog);
		myObjects.add(myEarthDialog);
		myObjects.add(myAirDialog);
		myObjects.add(myWaterDialog);
		myObjects.add(myFireDialog);
	}
	public void manageInfo() {
		myObjects.remove(myAvailableDialog);
		myObjects.remove(myEarthDialog);
		myObjects.remove(myAirDialog);
		myObjects.remove(myWaterDialog);
		myObjects.remove(myFireDialog);
		myEarthDialog = new Text(900, 470, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 1.5, myFont);
		myAirDialog = new Text(900, 500, myPlayer.getInventory().getAir() + " AIR ENERGY", 1.5, myFont);
		myWaterDialog = new Text(900, 530, myPlayer.getInventory().getWater() + " WATER ENERGY", 1.5, myFont);
		myFireDialog = new Text(900, 560, myPlayer.getInventory().getFire() + " FIRE ENERGY", 1.5, myFont);
		myObjects.add(myAvailableDialog);
		myObjects.add(myEarthDialog);
		myObjects.add(myAirDialog);
		myObjects.add(myWaterDialog);
		myObjects.add(myFireDialog);
	}

}
