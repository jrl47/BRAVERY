package UtilityObjects;

import GameObjects.Stage;
import GameObjects.StateChangeButton;
import GameObjects.Text;
import Utilities.MovementCostCalculator;
import Utilities.State;

public class MoveMenu extends SubMenu{
	private Text mySelectionDialog;
	private Text noSelectionDialog;
	private Text myCostDialog;

	public MoveMenu(Stage stage, State state){
		super(stage, state);

		noSelectionDialog = new Text(900, 20, "SELECT A VALID MOVE", 2, myFont);
		mySelectionDialog = new Text(900, 20, " ", 2, myFont);
		myCostDialog = new Text(900, 20, " ", 2, myFont);

		myObjects.add(mySelectionDialog);
		myObjects.add(myCostDialog);
	}

	public void manageInfo() {
		myObjects.remove(mySelectionDialog);
		myObjects.remove(myCostDialog);

		int xLoc = myPlayer.getX() + myPlayer.getTargetX();
		int yLoc = myPlayer.getY() + myPlayer.getTargetY();
		int xDif = Math.abs(myPlayer.getTargetX());
		int yDif = Math.abs(myPlayer.getTargetY());
		
		myCostDialog = new Text(900, 60, 
				myPlayer.getAction().getCost() + " " + myPlayer.getAction().getType().toUpperCase() + " ENERGY", 2, myFont);
		
		if(myPlayer.getTargetX()==Integer.MIN_VALUE || myPlayer.getTargetY()==Integer.MIN_VALUE){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		else if(!myCells.get(xLoc).get(yLoc).isAvailable()){
			mySelectionDialog = noSelectionDialog;
				myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		else if(xDif==0 && yDif==0){
			
		}
		else if(xDif==0 || yDif==0){
			Action a = new Action("move");
			a.setCost(MovementCostCalculator.getCost(xDif, yDif));
			a.setType("earth");
			myPlayer.setAction(a);
		}
		else if(xDif==yDif){
			Action a = new Action("move");
			a.setCost(MovementCostCalculator.getCost(xDif, yDif));
			a.setType("air");
			myPlayer.setAction(a);
		}
		else if(xDif==2*yDif || 2*xDif==yDif){
			Action a = new Action("move");
			a.setCost(MovementCostCalculator.getCost(xDif, yDif));
			a.setType("water");
			myPlayer.setAction(a);
		}
		else if(xDif==3*yDif || 3*xDif==yDif || 2*xDif==3*yDif || 3*xDif==2*yDif){
			Action a = new Action("move");
			a.setCost(MovementCostCalculator.getCost(xDif, yDif));
			a.setType("fire");
			myPlayer.setAction(a);
		}
		else{
			mySelectionDialog = noSelectionDialog;
			myCostDialog = new Text(900, 60, " ", 2, myFont);
		}
		
		
		if(myPlayer.getAction().getType().equals("earth")){
			mySelectionDialog = new Text(900, 20, "ORTHOGONAL MOVE:", 2, myFont);
		}
		else if(myPlayer.getAction().getType().equals("air")){
			mySelectionDialog = new Text(900, 20, "DIAGONAL MOVE:", 2, myFont);
		}
		else if(myPlayer.getAction().getType().equals("water")){
			mySelectionDialog = new Text(900, 20, "SKEW MOVE:", 2, myFont);
		}
		else if(myPlayer.getAction().getType().equals("fire")){
			mySelectionDialog = new Text(900, 20, "STRAY MOVE:", 2, myFont);
		}
		
		
		
		myObjects.add(mySelectionDialog);
		myObjects.add(myCostDialog);
	}
	
}
