package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Utilities.AttackDrawer;
import Utilities.Camera;
import Utilities.DeciduousTileManager;
import Utilities.MoveDrawer;
import Utilities.RoomNetwork;
import Utilities.State;
import Utilities.ValidAttackChecker;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	public static final int MAP_WIDTH = 27;
	public static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	
	private int roomX;
	private int roomY;
	private BufferedImage myRoom;
	private RoomNetwork myRooms;
	
	private List<List<MapCell>> myCells;
	private List<Enemy> myEnemies;
	private List<Collectible> myCollectibles;
	private DeciduousTileManager manager;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	private Camera myCamera;
	
	private int enemyAnimationCounter;
	private static final int ENEMY_ANIMATION_START = 6;
	private boolean quickMove;
	private State myPlane;
	
	public Stage() {
		super();
		roomX = 0;
		roomY = 1;
		myCamera = new Camera();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myPlane = new State("one");
		myEnemies = new ArrayList<Enemy>();
		myCollectibles = new ArrayList<Collectible>();
		myCells = new ArrayList<List<MapCell>>();
		myRooms = new RoomNetwork(this);
		try {
			manager = new DeciduousTileManager(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		myRooms.buildRoom(myCells, roomX, roomY);
		
		setEnemiesAndCollectibles();
		drawRoom();
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		if(!(j>0 && j < 674 && i < MAP_WIDTH * 32))
			return;
		
		wasInput = true;

		hoverX = i/32;
		hoverY = (j+1)/32;

		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		
		if(myPlayer.getTargetX()!=Integer.MIN_VALUE && myPlayer.getTargetY()!=Integer.MIN_VALUE){
			if(b){
				if(myPlayer.movePrepared() && myCells.get(locX).get(locY).isAvailable()){
					movePlayer();
				}
				if(myPlayer.attackPrepared()){
					if(ValidAttackChecker.detectAttackTargets(locX, locY, myPlayer, myCells)){
						ValidAttackChecker.doPlayerAttack(myPlayer, myCells);
						myPlayer.clearCommand();
					}
				}
			}
		}
	}

	@Override
	public void step() {
		if(!wasInput){
			hoverX = -200;
			hoverY = -200;
			myPlayer.setTargetX(Integer.MIN_VALUE);
			myPlayer.setTargetY(Integer.MIN_VALUE);
		}
		
		removeDeadEnemies();
		removeDeadCollectibles();
		
		if(myPlayer.checkForEnemyTurn()){
			myPlayer.pause();
			executeEnemyTurns();
			if(!quickMove){
			enemyAnimationCounter = ENEMY_ANIMATION_START;
			}
			else{
				quickMove = false;
				enemyAnimationCounter = 2;
			}
		}
		
		if(enemyAnimationCounter>0){
			enemyAnimationCounter--;
		}
		else{
			myPlayer.unpause();
		}
		
//		if(myPlayer.getCommand()==null)
//			return;
		
		handleKeyInput();
		setPlayerTarget();
		setCamera();
		
	}

	private void setCamera() {
		int camX = myPlayer.getX();
		int camY = myPlayer.getY();
		if(camX - MAP_WIDTH/2 < 0){
			camX = MAP_WIDTH/2;
		}
		if(camY - MAP_HEIGHT/2 < 0){
			camY = MAP_HEIGHT/2;
		}
		if(camX + MAP_WIDTH/2 >= myCells.size()){
			camX = myCells.size() - MAP_WIDTH/2 - 1;
		}
		if(camY + MAP_HEIGHT/2 >= myCells.get(0).size()){
			camY = myCells.get(0).size() - MAP_HEIGHT/2 - 1;
		}
		myCamera.setX(camX);
		myCamera.setY(camY);
	}
	
	@Override
	public void draw(Graphics g) {
		drawCells(g);
		drawPlayer(g);
		drawEnemies(g);
		drawCollectibles(g);
		wasInput = false;
	}

	private void drawCollectibles(Graphics g) {
		for(Collectible c: myCollectibles){
			c.draw(g);
		}
	}

	private void drawCells(Graphics g) {
		g.drawImage(myRoom.getSubimage(32*(myCamera.getX() - MAP_WIDTH/2), 32*(myCamera.getY() - MAP_HEIGHT/2), 32*MAP_WIDTH, 32*MAP_HEIGHT),
				0, 0, null);
	}
	
	private void drawEnemies(Graphics g) {
		for(Enemy e: myEnemies){
			if(enemyAnimationCounter > 0){
				e.drawOld(g);
				e.drawHover(g);
			}
			else{
			e.draw(g);
			}
		}
	}
	
	private void drawPlayer(Graphics g) {
		myPlayer.draw(g);
		if(myPlayer.movePrepared()){
			MoveDrawer.drawMoves(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, this, g);
		} else if(myPlayer.actionPrepared()){
			AttackDrawer.drawAttacks(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, this, g);
		}
		else{
			clearAvailability(myCells);
		}
		
		if(hoverX!=-1 || hoverY!=-1){
			ValidAttackChecker.drawHoverInfo(g, this, hoverX, hoverY, manager);
		}
	}
	
	private void handleKeyInput() {
		if(myPlayer.isPaused() || myPlayer.getCommand()==null)
			return;
		if(myPlayer.getCommand().equals("Up")){
			myPlayer.clearCommand();
			if(myPlayer.getY()-1 < 0){
				roomY -= 1;
				myPlayer.resetLocation(myPlayer.getX(), 31);
				changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()-1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(-1);
				movePlayer();
				quickMove = true;
			}
		}
		else if(myPlayer.getCommand().equals("Down")){
			myPlayer.clearCommand();
			if(myPlayer.getY()+1 >= myCells.get(0).size()){
				roomY += 1;
				myPlayer.resetLocation(myPlayer.getX(), 0);
				changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()+1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(1);
				movePlayer();
				quickMove = true;
			}
		}
		else if(myPlayer.getCommand().equals("Left")){
			myPlayer.clearCommand();
			if(myPlayer.getX()-1 < 0){
				roomX -= 1;
				myPlayer.resetLocation(31, myPlayer.getY());
				changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()-1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(-1);
				myPlayer.setTargetY(0);
				movePlayer();
				quickMove = true;
			}
		}
		else if(myPlayer.getCommand().equals("Right")){
			myPlayer.clearCommand();
			if(myPlayer.getX()+1 >= myCells.size()){
				roomX += 1;
				myPlayer.resetLocation(0, myPlayer.getY());
				changeRoom();
				return;
			}
			if(myCells.get(myPlayer.getX()+1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(1);
				myPlayer.setTargetY(0);
				movePlayer();
				quickMove = true;
			}
		}
	}

	private void changeRoom() {
		int specificRoomX = roomX;
		int specificRoomY = roomY;
		roomX = myRooms.getX(specificRoomX, specificRoomY);
		roomY = myRooms.getY(specificRoomX, specificRoomY);
		myRooms.buildRoom(myCells, roomX, roomY);
		
		int newX = myPlayer.getX() % 32;
		int newY = myPlayer.getY() % 32;
		newX += 32*(specificRoomX - roomX);
		newY += 32*(specificRoomY - roomY);
		myPlayer.resetLocation(newX, newY);
		
		setEnemiesAndCollectibles();
		drawRoom();
	}

	private void drawRoom() {
		int roomWidth = myRooms.getWidth(roomX, roomY);
		int roomHeight = myRooms.getHeight(roomX, roomY);
		myRoom = new BufferedImage(32*32*roomWidth, 32*32*roomHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = myRoom.getGraphics();
		for(int i=0; i<roomWidth*32; i++){
			for(int j=0; j<roomHeight*32; j++){
				myCells.get(i).get(j).draw(g, manager, i, j);
			}
		}
		g.dispose();
	}

	private void setEnemiesAndCollectibles() {
		myEnemies.clear();
		myCollectibles.clear();
		Random r = new Random();
		for(int i=0; i<myCells.size(); i++){
			for(int j=0; j<myCells.get(0).size(); j++){
				if(myCells.get(i).get(j).isPassable()){
					int rand = r.nextInt(500);
					if(rand < 5){
						Enemy e = new Enemy(i,j, 6, 3, 10, 5, this);
						myEnemies.add(e);
						myCells.get(i).get(j).setEnemy(e);
					}
					if(rand == 6 || rand==7){
						int type = r.nextInt(4);
						Collectible c = null;
						if(type==0)
							c = new Collectible(2000, "earth", i, j, this);
						if(type==1)
							c = new Collectible(2000, "air", i, j, this);
						if(type==2)
							c = new Collectible(2000, "water", i, j, this);
						if(type==3)
							c = new Collectible(2000, "fire", i, j, this);
						
						myCollectibles.add(c);
						myCells.get(i).get(j).setCollectible(c);
					}
				}
			}
		}
	}
	
	private void setPlayerTarget() {
		int targetX = myCamera.getX() - myPlayer.getX() + hoverX - MAP_WIDTH/2;
		int targetY = myCamera.getY() - myPlayer.getY() + hoverY - MAP_HEIGHT/2;
		
		int destX = myPlayer.getX() + targetX;
		int destY = myPlayer.getY() + targetY;
		
		if(!(destX < 0 || destY < 0 
				|| destX >= myCells.size() || destY >= myCells.get(0).size())){
			myPlayer.setTargetX(targetX);
			myPlayer.setTargetY(targetY);
		}
		else{
			myPlayer.setTargetX(Integer.MIN_VALUE);
			myPlayer.setTargetY(Integer.MIN_VALUE);
		}
	}

	private void executeEnemyTurns() {
		for(Enemy e: myEnemies){
			e.doTurn(myPlayer);
		}
	}

	private void clearAvailability(List<List<MapCell>> myCells) {
		for(int i=0; i<myCells.size(); i++){
			for(int j=0; j<myCells.get(0).size(); j++){
				myCells.get(i).get(j).setAvailable(false);
			}
		}
	}

	public Player getPlayer() {
		return myPlayer;
	}
	public List<List<MapCell>> getCells(){
		return myCells;
	}

	public void addPlayer(Player player) {
		myPlayer = player;
		setCamera();
	}

	public boolean isGameOver() {
		return myPlayer.isDead();
	}

	public MapCell getCell(int x, int y) {
		return myCells.get(x).get(y);
	}

	public int getWidth() {
		return myCells.size();
	}
	public int getHeight() {
		return myCells.get(0).size();
	}

	public DeciduousTileManager getManager() {
		return manager;
	}
	
	private void movePlayer() {
		myPlayer.move();
	}

	private void removeDeadEnemies() {
		for(int i=0; i<myEnemies.size(); i++){
			Enemy e = myEnemies.get(i);
			if(e.isDead()){
				myEnemies.remove(e);
				i--;
			}
		}
	}
	
	private void removeDeadCollectibles() {
		for(int i=0; i<myCollectibles.size(); i++){
			Collectible c = myCollectibles.get(i);
			if(c.isDestroyed()){
				myCollectibles.remove(c);
				i--;
			}
		}
	}

	public int getRelativeX(int myX) {
		if(Stage.MAP_WIDTH/2 + (myX - myCamera.getX()) < 0 || Stage.MAP_WIDTH/2 + (myX - myCamera.getX()) >= MAP_WIDTH)
			return -1;
		return Stage.MAP_WIDTH/2 + (myX - myCamera.getX());
	}
	public int getRelativeY(int myY) {
		if(Stage.MAP_HEIGHT/2 + (myY - myCamera.getY()) < 0 || Stage.MAP_HEIGHT/2 + (myY - myCamera.getY()) >= MAP_HEIGHT)
			return -1;
		return Stage.MAP_HEIGHT/2 + (myY - myCamera.getY());
	}

	public Camera getCamera() {
		return myCamera;
	}

	public List<Enemy> getEnemies() {
		return myEnemies;
	}

	public RoomNetwork getRooms() {
		return myRooms;
	}

	public int getRoomX() {
		return roomX;
	}
	public int getRoomY() {
		return roomY;
	}

	public State getPlane() {
		return myPlane;
	}
}
