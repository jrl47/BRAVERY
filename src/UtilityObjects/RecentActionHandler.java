package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class RecentActionHandler extends SubMenu{

	private Text myHit;
	
	public RecentActionHandler(Stage stage, State state) {
		super(stage, state);
		
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(740, 505, " ", 1.5, myFont);
		}
		else{
			myHit = new Text(740, 505, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 2, myFont);
		}
		
		myObjects.add(myHit);
	}
	public void manageInfo() {
		System.out.println("hapen");
		myObjects.remove(myHit);
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(740, 505, " ", 2, myFont);
		}
		else{
			myHit = new Text(740, 505, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 1.5, myFont);
		}
		myObjects.add(myHit);
	}
}
