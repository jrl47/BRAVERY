package GameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Utilities.RoomDataBuilder;
import Utilities.RoomNetwork;

public class Boss extends Enemy{

	private int roomX;
	private int roomY;
	
	private Random myRand;
	private RoomNetwork myRooms;
	
	public Boss(int x, int y, Stage stage, int index, int roomx, int roomy) {
		super(x, y, stage, index);
		roomX = roomx;
		roomY = roomy;
		myRand = new Random();
		myRooms = myStage.getRooms();
	}
	
	public void doTurn(Player myPlayer) {
		if(!(myRooms.getX(roomX, roomY) == myStage.getRoomX() && myRooms.getY(roomX, roomY) == myStage.getRoomY())){
			moveRooms();
		}
		else{
			if(!myStage.getCell(myX, myY).isLand()){
				myHealth = -1;
				return;
			}
			
			int dist = Math.abs(myPlayer.getX() - myX) + Math.abs(myPlayer.getY() - myY);
			if(dist <= attackRange){
				attack(myPlayer);
			}
			else{
				move(myPlayer);
			}
		}
	}

	private void moveRooms() {
		int rand = myRand.nextInt(12);
		if(rand!=0) return;
		List<Character> possibleMoves = new ArrayList<Character>();
		possibleMoves.addAll(RoomDataBuilder.getRoomData(roomX, roomY).getSides());
		if(roomY - 1 - myRooms.getY(roomX, roomY) >=0 && !possibleMoves.contains('u')){
			possibleMoves.add('u');
		}
		if(roomY + 1 - myRooms.getY(roomX, roomY) < myRooms.getHeight(roomX, roomY) && !possibleMoves.contains('d')){
			possibleMoves.add('d');
		}
		if(roomX - 1 - myRooms.getX(roomX, roomY) >=0 && !possibleMoves.contains('l')){
			possibleMoves.add('l');
		}
		if(roomX + 1 - myRooms.getX(roomX, roomY) < myRooms.getWidth(roomX, roomY) && !possibleMoves.contains('r')){
			possibleMoves.add('r');
		}
		
		rand = myRand.nextInt(possibleMoves.size());
		if(possibleMoves.get(rand)=='u'){
			roomY--;
		}
		if(possibleMoves.get(rand)=='d'){
			roomY++;
		}
		if(possibleMoves.get(rand)=='l'){
			roomX--;
		}
		if(possibleMoves.get(rand)=='r'){
			roomX++;
		}
		if(isOnStage())
			myStage.addBossToMap(this, possibleMoves.get(rand));
	}
	
	public void setLocation(int i, int j){
		attacked = false;
		oldX = myX;
		oldY = myY;
		
		myCells.get(myX).get(myY).removeEnemy();
		
		myX = i;
		myY = j;
		
		myCells.get(myX).get(myY).setEnemy(this);
	}
	
	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

	public boolean isOnStage() {
		return myRooms.getX(roomX, roomY)==myStage.getRoomX() && myRooms.getY(roomX, roomY)==myStage.getRoomY();
	}

}
