package GameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Utilities.RoomNetwork;
import UtilitiesData.RoomDataBuilder;

public class Boss extends Enemy{

	private int roomX;
	private int roomY;
	
	private Random myRand;
	private RoomNetwork myRooms;
	
	public Boss(int x, int y, Stage stage, int index) {
		super(x, y, stage, index);
		roomX = data.getRoomX();
		roomY = data.getRoomY();
		myRand = new Random();
		myRooms = myStage.getRooms();
	}
	public void doTurn(Player myPlayer) {
		if(!isOnStage()){
			myX = 0;
			myY = 0;
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
		roomX = myRooms.getX(roomX, roomY) + myX/Stage.ROOM_SIZE;
		roomY = myRooms.getY(roomX, roomY) + myY/Stage.ROOM_SIZE;
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
		return myRooms.getX(roomX, roomY) == myRooms.getX(myStage.getRoomX(), myStage.getRoomY()) 
				&& myRooms.getY(roomX, roomY) == myRooms.getY(myStage.getRoomX(), myStage.getRoomY());
	}

}
