package UtilityObjects;

import GameObjects.Stage;
import GameObjects.Text;
import Utilities.State;

public class RecentActionHandler extends SubMenu{

	private Text myHit;
	
	public RecentActionHandler(Stage stage, State state) {
		super(stage, state);
		
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(904, 300, " ", 2, myFont);
		}
		else{
			myHit = new Text(904, 300, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 2, myFont);
		}
		
		myObjects.add(myHit);
	}
	public void manageInfo() {
		myObjects.remove(myHit);
		if(myPlayer.getDamageTaken()==0){
			myHit = new Text(904, 300, " ", 2, myFont);
		}
		else{
			myHit = new Text(904, 300, "TOOK " + myPlayer.getDamageTaken() + " DAMAGE", 2, myFont);
		}
		myObjects.add(myHit);
	}
}
