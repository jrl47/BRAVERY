package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class RecentActionHandler extends SubMenu{

	private Text myHit;
	
	public RecentActionHandler(Stage stage, State state) {
		super(stage, state);
		
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(924, 230, " ", 3, myFont);
		}
		else{
			myHit = new Text(924, 230, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 3, myFont);
		}
		
		myObjects.add(myHit);
	}
	public void manageInfo() {
		myObjects.remove(myHit);
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(924, 230, " ", 3, myFont);
		}
		else{
			myHit = new Text(924, 230, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 3, myFont);
		}
		myObjects.add(myHit);
	}
}
