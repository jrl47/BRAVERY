package GameObjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
		if(!(roomX == myStage.getRoomX() && roomY == myStage.getRoomY())){
			moveRooms();
		}
		else{
			if(!myStage.getCell(myX, myY).isPassable()){
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
		int rand = myRand.nextInt(1);
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
		if(roomX==myStage.getRoomX() && roomY==myStage.getRoomY())
		myStage.addBossToMap(this, possibleMoves.get(rand));
	}
	
	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

}
