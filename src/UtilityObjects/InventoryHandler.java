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
		myAvailableDialog = new Text(900, 400, "AVAILABLE ENERGY:", 2, myFont);
		myEarthDialog = new Text(900, 440, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 2, myFont);
		myAirDialog = new Text(900, 480, myPlayer.getInventory().getAir() + " AIR ENERGY", 2, myFont);
		myWaterDialog = new Text(900, 520, myPlayer.getInventory().getWater() + " WATER ENERGY", 2, myFont);
		myFireDialog = new Text(900, 560, myPlayer.getInventory().getFire() + " FIRE ENERGY", 2, myFont);
		
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
		myEarthDialog = new Text(900, 440, myPlayer.getInventory().getEarth() + " EARTH ENERGY", 2, myFont);
		myAirDialog = new Text(900, 480, myPlayer.getInventory().getAir() + " AIR ENERGY", 2, myFont);
		myWaterDialog = new Text(900, 520, myPlayer.getInventory().getWater() + " WATER ENERGY", 2, myFont);
		myFireDialog = new Text(900, 560, myPlayer.getInventory().getFire() + " FIRE ENERGY", 2, myFont);
		myObjects.add(myAvailableDialog);
		myObjects.add(myEarthDialog);
		myObjects.add(myAirDialog);
		myObjects.add(myWaterDialog);
		myObjects.add(myFireDialog);
	}

}
