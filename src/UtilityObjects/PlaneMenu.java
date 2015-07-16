package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.MovementCostCalculator;
import Utilities.State;

public class PlaneMenu extends SubMenu{

	private StateChangeButton subBack;
	private StateChangeButton one;
	private StateChangeButton two;
	private StateChangeButton three;
	private StateChangeButton four;
	private StateChangeButton five;
	private StateChangeButton six;
	private StateChangeButton seven;
	private StateChangeButton eight;
	private StateChangeButton shift;
	
	private Text myPlaneCounter;
	private Text myCost;
	private Text cantPay;
	private Text cantWalk;
	private Text tooSoon;
	
	private State myDesiredPlane;
	private State myPurchasedPlane;
	
	public PlaneMenu(Stage stage, State state) {
		super(stage, state);
		myPurchasedPlane = new State("main");
		myDesiredPlane = new State("main");
		subBack = new StateChangeButton(1130, 400, "BACK", 2, myFont,myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "main");
		one = new StateChangeButton(874, 10, "ONE", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "1");
		two = new StateChangeButton(946, 10, "TWO", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "2");
		three = new StateChangeButton(1016, 10,"THREE", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "3");
		four = new StateChangeButton(1118, 10,"FOUR", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "4");
		five = new StateChangeButton(874, 60, "FIVE", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "5");
		six = new StateChangeButton(950, 60, "SIX", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "6");
		seven = new StateChangeButton(1010, 60, "SEVEN", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "7");
		eight = new StateChangeButton(1102, 60, "EIGHT", 2.25, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "8");
		shift = new StateChangeButton(964, 20, "SHIFT", 3, myFont, myBlueFont, myBackground, myHoverBackground, myPurchasedPlane, "main");
		myPlaneCounter = new Text(886, 300, " ", 2, myFont);
		myCost = new Text(886, 210, " ", 2, myFont);
		cantPay = new Text(884, 30, "CAN'T AFFORD", 1.5, myFont);
		cantWalk = new Text(1070 , 30, "IMPASSABLE", 1.5, myFont);
		tooSoon = new Text(884 , 60, myPlayer.getPlaneCounter() + " TURN COOLDOWN", 1.5, myFont);

		myObjects.add(one);
		myObjects.add(two);
		myObjects.add(three);
		myObjects.add(four);
		myObjects.add(five);
		myObjects.add(six);
		myObjects.add(seven);
		myObjects.add(eight);
	}

	@Override
	public void manageInfo() {
		myObjects.clear();
		
		if(myDesiredPlane.getState().equals("main")){
			myObjects.add(one);
			myObjects.add(two);
			myObjects.add(three);
			myObjects.add(four);
			myObjects.add(five);
			myObjects.add(six);
			myObjects.add(seven);
			myObjects.add(eight);
		}
		else{
			myObjects.add(subBack);
			shift.setNewState(myDesiredPlane.getState());
			int plane = Integer.parseInt(myDesiredPlane.getState());
			boolean canPay = true;
			boolean canWalk = true;
			if(!myStage.getCell(myPlayer.getX(), myPlayer.getY()).isPassable(Integer.parseInt(myDesiredPlane.getState())))
				canWalk = false;
			int cost = 0;
			if(!myDesiredPlane.getState().equals("1")){
				cost = (int) Math.pow(10, plane - 1);
				if(myPlayer.getInventory().getMin() < cost){
					canPay = false;
				}
			}
			myCost = new Text(884, 90, cost +  " OF EACH ENERGY", 1.5, myFont);
			if(canPay && canWalk && myPlayer.getPlaneCounter()==0){
				myObjects.add(shift);
			}
			if(!canPay){
				myObjects.add(cantPay);
			}
			if(!canWalk){
				myObjects.add(cantWalk);
			}
			if(myPlayer.getPlaneCounter()!=0){
				tooSoon = new Text(884 , 60, myPlayer.getPlaneCounter() + " TURN COOLDOWN", 1.5, myFont);
				myObjects.add(tooSoon);
			}
			myObjects.add(myCost);
			
			if(!myPurchasedPlane.getState().equals("main") && canPay){
				Action a = new Action(myPurchasedPlane.getState());
				a.setType("all");
				a.setCost(cost);
				myPlayer.setAction(a);
				myPlayer.planeShift();
				myPurchasedPlane.setState("main");
				myDesiredPlane.setState("main");
			}
		}
		
		myObjects.add(myPlaneCounter);
	}

}
