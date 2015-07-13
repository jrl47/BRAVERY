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
	
	private Text myPlaneCounter;
	
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
		
		myPlaneCounter = new Text(886, 300, " ", 2, myFont);
		
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
			if(myDesiredPlane.getState().equals("one")){
				
			}
			if(myDesiredPlane.getState().equals("two")){
				
			}
			if(myDesiredPlane.getState().equals("three")){
				
			}
			if(myDesiredPlane.getState().equals("four")){
				
			}
			if(myDesiredPlane.getState().equals("five")){
				
			}
			if(myDesiredPlane.getState().equals("six")){
				
			}
			if(myDesiredPlane.getState().equals("seven")){
				
			}
			if(myDesiredPlane.getState().equals("eight")){
				
			}
		}
		
		myObjects.add(myPlaneCounter);
	}

}
