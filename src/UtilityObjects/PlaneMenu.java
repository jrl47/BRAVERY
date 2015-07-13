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
	private State myPurchasedPlane;
	
	public PlaneMenu(Stage stage, State state) {
		super(stage, state);
		myPurchasedPlane = new State("main");
		myDesiredPlane = new State("main");
		back = new StateChangeButton(930, 600, "MAIN MENU", 3, myFont,myBlueFont, myBackground, myHoverBackground, myState, "main");
		subBack = new StateChangeButton(984, 600, "BACK", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "main");
		one = new StateChangeButton(944, 20, "ONE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "1");
		two = new StateChangeButton(1034, 20, "TWO", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "2");
		three = new StateChangeButton(904, 90, "THREE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "3");
		four = new StateChangeButton(1034, 90, "FOUR", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "4");
		five = new StateChangeButton(924, 160, "FIVE", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "5");
		six = new StateChangeButton(1034, 160, "SIX", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "6");
		seven = new StateChangeButton(904, 230, "SEVEN", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "7");
		eight = new StateChangeButton(1034, 230, "EIGHT", 3, myFont, myBlueFont, myBackground, myHoverBackground, myDesiredPlane, "8");
		shift = new StateChangeButton(964, 20, "SHIFT", 3, myFont, myBlueFont, myBackground, myHoverBackground, myPurchasedPlane, "main");
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
			int plane = Integer.parseInt(myDesiredPlane.getState());
			boolean canPay = true;
			if(myDesiredPlane.getState().equals("1")){
				myCost = new Text(874, 90, "0 OF EACH ENERGY", 2, myFont);
			}
			else{
				if(!(myPlayer.getInventory().getMin() >= Math.pow(10, plane - 1))){
					canPay = false;
				}
				myCost = new Text(874, 90, (int)Math.pow(10, plane - 1)  +  " OF EACH ENERGY", 2, myFont);
			}
			
			if(canPay){
				myObjects.add(shift);
			}
			else{
				myObjects.add(cantPay);
			}
			myObjects.add(myCost);
		}
		
		if(!myPurchasedPlane.getState().equals("main")){
			myPlayer.setCommand(myPurchasedPlane.getState());
		}
		
		myObjects.add(myPlaneCounter);
	}

}
