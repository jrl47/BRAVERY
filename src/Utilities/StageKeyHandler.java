package Utilities;

import java.util.List;

import GameObjects.MapCell;
import GameObjects.Player;
import GameObjects.Stage;

public class StageKeyHandler {

	public static void handleKeyInput(Player myPlayer, List<List<MapCell>> myCells, Stage myStage) {
		if(myPlayer.isPaused() || myPlayer.getAction().getName().equals("wait"))
			return;
		if(myPlayer.getAction().getName().equals("up")){
			myPlayer.clearAction();
			if(myPlayer.getY()-1 < 0){
				myStage.setRoomY(myStage.getRoomY()-1);
				myPlayer.resetLocation(myPlayer.getX(), 31);
				myStage.changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()-1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(-1);
				myStage.movePlayer();
				myStage.setQuickMove(true);
			}
		}
		else if(myPlayer.getAction().getName().equals("down")){
			myPlayer.clearAction();
			if(myPlayer.getY()+1 >= myCells.get(0).size()){
				myStage.setRoomY(myStage.getRoomY()+1);
				myPlayer.resetLocation(myPlayer.getX(), 0);
				myStage.changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()+1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(1);
				myStage.movePlayer();
				myStage.setQuickMove(true);
			}
		}
		else if(myPlayer.getAction().getName().equals("left")){
			myPlayer.clearAction();
			if(myPlayer.getX()-1 < 0){
				myStage.setRoomX(myStage.getRoomX()-1);
				myPlayer.resetLocation(31, myPlayer.getY());
				myStage.changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()-1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(-1);
				myPlayer.setTargetY(0);
				myStage.movePlayer();
				myStage.setQuickMove(true);
			}
		}
		else if(myPlayer.getAction().getName().equals("right")){
			myPlayer.clearAction();
			if(myPlayer.getX()+1 >= myCells.size()){
				myStage.setRoomX(myStage.getRoomX()+1);
				myPlayer.resetLocation(0, myPlayer.getY());
				myStage.changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()+1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(1);
				myPlayer.setTargetY(0);
				myStage.movePlayer();
				myStage.setQuickMove(true);
			}
		}
	}
	
}
