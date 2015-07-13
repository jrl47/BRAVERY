package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.State;

public class PlaneMenu extends SubMenu{

	private StateChangeButton back;
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
	
	private State myDesiredPlane;
	private State myStagePlane;
	
	public PlaneMenu(Stage stage, State state, State plane) {
		super(stage, state);
		myStagePlane = plane;
		myDesiredPlane = new State("main");
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "main");
		subBack = new StateChangeButton(984, 600, "BACK", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "main");
		one = new StateChangeButton(944, 20, "ONE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "one");
		two = new StateChangeButton(1034, 20, "TWO", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "two");
		three = new StateChangeButton(904, 90, "THREE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "three");
		four = new StateChangeButton(1034, 90, "FOUR", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "four");
		five = new StateChangeButton(924, 160, "FIVE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "five");
		six = new StateChangeButton(1034, 160, "SIX", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "six");
		seven = new StateChangeButton(904, 230, "SEVEN", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "seven");
		eight = new StateChangeButton(1034, 230, "EIGHT", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "eight");
		shift = new StateChangeButton(964, 20, "SHIFT", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "one");
		myPlaneCounter = new Text(886, 300, " ", 2, myFont);
		myCost = new Text(886, 300, " ", 2, myFont);
		cantPay = new Text(914, 30, "NOT ENOUGH ENERGY", 2, myFont);
		
		myObjects.add(back);
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
			myObjects.add(back);
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
			boolean canPay = true;
			if(myDesiredPlane.getState().equals("one")){
				myCost = new Text(874, 90, "0 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("two")){
				if(!(myPlayer.getInventory().getMin() >= 10)){
					canPay = false;
				}
				myCost = new Text(874, 90, "10 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("three")){
				if(!(myPlayer.getInventory().getMin() >= 100)){
					canPay = false;
				}
				myCost = new Text(874, 90, "100 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("four")){
				if(!(myPlayer.getInventory().getMin() >= 1000)){
					canPay = false;
				}
				myCost = new Text(874, 90, "1000 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("five")){
				if(!(myPlayer.getInventory().getMin() >= 10000)){
					canPay = false;
				}
				myCost = new Text(874, 90, "10000 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("six")){
				if(!(myPlayer.getInventory().getMin() >= 100000)){
					canPay = false;
				}
				myCost = new Text(874, 90, "100000 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("seven")){
				if(!(myPlayer.getInventory().getMin() >= 1000000)){
					canPay = false;
				}
				myCost = new Text(874, 90, "1000000 OF EACH ENERGY", 2, myFont);
			}
			if(myDesiredPlane.getState().equals("eight")){
				if(!(myPlayer.getInventory().getMin() >= 10000000)){
					canPay = false;
				}
				myCost = new Text(874, 90, "10000000 OF EACH ENERGY", 2, myFont);
			}
			
			if(canPay){
				myObjects.add(shift);
			}
			else{
				myObjects.add(cantPay);
			}
			myObjects.add(myCost);
		}
		
		myObjects.add(myPlaneCounter);
	}

}
