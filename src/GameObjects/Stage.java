package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.World;
import Utilities.AttackDrawer;
import Utilities.Camera;
import Utilities.DeciduousTileManager;
import Utilities.MoveDrawer;
import Utilities.RoomNetwork;
import Utilities.ValidAttackChecker;
import UtilityObjects.Action;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	public static final int MAP_WIDTH = 27;
	public static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	
	private int roomX;
	private int roomY;
	private RoomNetwork myRooms;
	
	private List<List<MapCell>> myCells;
	private List<Enemy> myEnemies;
	private DeciduousTileManager manager;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	private Camera myCamera;
	
	private int enemyAnimationCounter;
	private static final int ENEMY_ANIMATION_START = 6;
	private boolean quickMove;
	
	private MapCell outsideBorder;
	
	public Stage() {
		super();
		roomX = 0;
		roomY = 1;
		myCamera = new Camera();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myEnemies = new ArrayList<Enemy>();
		myCells = new ArrayList<List<MapCell>>();
		myRooms = new RoomNetwork(this);
		try {
			manager = new DeciduousTileManager(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		myRooms.buildRoom(myCells, roomX, roomY);
		
		setEnemiesAndCollectibles();
		
//		myCells.get(4).get(3).setCollectible(new Collectible(2000, "earth"));
//		Enemy e = new Enemy(3,9, 6, 3, 10, 5, this);
//		myEnemies.add(e);
//		myCells.get(3).get(9).setEnemy(e);
//		e = new Enemy(26,7, 6, 3, 10, 5, this);
//		myEnemies.add(e);
//		myCells.get(10).get(3).setEnemy(e);
		
		outsideBorder = new MapCell(-1, -1, this);
		outsideBorder.setID(MapCell.WATER);
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		wasInput = true;
		if(j>0 && j < 674 && i < MAP_WIDTH * 32){
			hoverX = i/32;
			hoverY = (j+1)/32;
		}
		else{
			hoverX = -1;
			hoverY = -1;
		}

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
			hoverX = -1;
			hoverY = -1;
			myPlayer.setTargetX(Integer.MIN_VALUE);
			myPlayer.setTargetY(Integer.MIN_VALUE);
		}
		
		removeDeadEnemies();
		
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
		
		if(myPlayer.getCommand()==null)
			return;
		
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
		wasInput = false;
	}

	private void drawCells(Graphics g) {
		int xcounter = 0;
		int ycounter = 0;
		for(int i=myCamera.getX() - (MAP_WIDTH/2); i<=(MAP_WIDTH/2) + myCamera.getX(); i++){
			for(int j=myCamera.getY() - (MAP_HEIGHT/2); j<(MAP_HEIGHT/2) + 1 + myCamera.getY(); j++){
				if(i < 0 || j < 0 || i >= myCells.size() || j >=myCells.get(0).size()){
					g.drawImage(manager.getImage(outsideBorder), xcounter*Stage.BLOCK_SIZE, 1+(ycounter*Stage.BLOCK_SIZE), null);
				}
				else{
					myCells.get(i).get(j).draw(g, manager, xcounter,ycounter);
				}
				ycounter++;
			}
			xcounter++;
			ycounter=0;
		}
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
		if(myPlayer.isPaused())
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
	}

	private void setEnemiesAndCollectibles() {
		myEnemies.clear();
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
						if(type==0)
							myCells.get(i).get(j).setCollectible(new Collectible(2000, "earth"));
						if(type==1)
							myCells.get(i).get(j).setCollectible(new Collectible(2000, "air"));
						if(type==2)
							myCells.get(i).get(j).setCollectible(new Collectible(2000, "water"));
						if(type==3)
							myCells.get(i).get(j).setCollectible(new Collectible(2000, "fire"));
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
}
