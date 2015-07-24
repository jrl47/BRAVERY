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
import Utilities.StageKeyHandler;
import Utilities.State;
import Utilities.ValidAttackChecker;
import UtilitiesData.CollectibleSkillBuilder;
import UtilitiesData.CollectibleSkillData;
import UtilitiesData.RoomDataBuilder;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	public static final int MAP_WIDTH = 22;
	public static final int MAP_HEIGHT = 22;
	public static final int ROOM_SIZE = 22;
	private Player myPlayer;
	private int roomX;
	private int roomY;
	private BufferedImage myRoom;
	private RoomNetwork myRooms;
	private List<List<MapCell>> myCells;
	private List<Enemy> myEnemies;
	private List<Boss> myBosses;
	private List<Boss> myActiveBosses;
	private List<Collectible> myCollectibles;
	private List<CollectibleSkill> myCollectibleSkills;
	private DeciduousTileManager manager;
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	private int hoverRoomX = -1;
	private int hoverRoomY = -1;
	private Camera myCamera;
	private int enemyAnimationCounter;
	private static final int ENEMY_ANIMATION_START = 6;
	private boolean quickMove;
	private State myPlane;

	public Stage() {
		super();
		roomX = 5;
		roomY = 3;
		myCamera = new Camera();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32,705);
		myPlane = new State("1");
		myEnemies = new ArrayList<Enemy>();
		myBosses = new ArrayList<Boss>();
		myActiveBosses = new ArrayList<Boss>();
		myCollectibles = new ArrayList<Collectible>();
		myCollectibleSkills = new ArrayList<CollectibleSkill>();
		myCells = new ArrayList<List<MapCell>>();
		myRooms = new RoomNetwork(this);

		try {
			manager = new DeciduousTileManager(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		myRooms.buildRoom(myCells, roomX, roomY);
		
		setEnemiesAndCollectibles();
		createBosses();
		createCollectibleSkills();
		drawRoom();
	}
	@Override
	public void useInput(int i, int j, boolean b) {
		if(!(j>0 && j < 705 && i < MAP_WIDTH * 32))
			return;
		
		wasInput = true;

		hoverX = i/32;
		hoverY = (j+1)/32;

		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		
		if(myPlayer.getTargetX()!=Integer.MIN_VALUE && myPlayer.getTargetY()!=Integer.MIN_VALUE){
			if(b){
				if(myPlayer.movePrepared() && myCells.get(locX).get(locY).isAvailable() &&
						myCells.get(locX).get(locY).isPassable()){
					movePlayer();
				}
				if(myPlayer.attackPrepared()){
					if(ValidAttackChecker.detectAttackTargets(locX, locY, myPlayer, myCells)){
						ValidAttackChecker.doPlayerAttack(myPlayer, myCells);
						myPlayer.clearAction();
					}
				}
			}
		}
	}
	@Override
	public void step() {
		hoverRoomX = -1;
		if(!wasInput){
			hoverX = -1;
			hoverY = -1;
			myPlayer.setTargetX(Integer.MIN_VALUE);
			myPlayer.setTargetY(Integer.MIN_VALUE);
		}
		
		roomX = myRooms.getX(roomX, roomY) + myPlayer.getX()/ROOM_SIZE;
		roomY = myRooms.getY(roomX, roomY) + myPlayer.getY()/ROOM_SIZE;
		
		removeDeadEnemies();
		removeDeadCollectibles();
		
		for(Boss b : myBosses){
			if(b.isOnStage() && !myActiveBosses.contains(b))
				addBossToMap(b, 'n');
		}
		
		if(myPlayer.checkForEnemyTurn()){
			myPlayer.pause();
			executeEnemyTurns();
			if(!quickMove){
			enemyAnimationCounter = ENEMY_ANIMATION_START;
			}
			else{
				quickMove = false;
				enemyAnimationCounter = 1;
			}
		}
		
		if(enemyAnimationCounter>0){
			enemyAnimationCounter--;
		}
		else{
			myPlayer.unpause();
		}
		
		StageKeyHandler.handleKeyInput(myPlayer, myCells, this);
		setPlayerTarget();
		setCamera();
		
	}
	@Override
	public void draw(Graphics g) {
		drawCells(g);
		drawPlayer(g);
		drawEnemies(g);
		drawCollectibles(g);
		wasInput = false;
	}
	private void drawCells(Graphics g) {
		g.drawImage(myRoom.getSubimage(32*(myCamera.getX() - MAP_WIDTH/2), 32*(myCamera.getY() - MAP_HEIGHT/2), 
				Math.min(MAP_WIDTH, ROOM_SIZE*myRooms.getWidth(roomX, roomY))*32, Math.min(MAP_HEIGHT, ROOM_SIZE*myRooms.getHeight(roomX, roomY))*32),
				0, 0, null);
	}
	private void drawCollectibles(Graphics g) {
		for(Collectible c: myCollectibles){
			c.draw(g);
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
		for(Boss b: myBosses){
			if(b.isOnStage() && myActiveBosses.contains(b)){
				if(enemyAnimationCounter > 0){
					b.drawOld(g);
					b.drawHover(g);
				}
				else{
				b.draw(g);
				}
			}
		}
	}
	private void drawPlayer(Graphics g) {
		myPlayer.draw(g);
		if(myPlayer.movePrepared()){
			MoveDrawer.drawMoves(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, this, g);
		} else if(myPlayer.attackPrepared()){
			AttackDrawer.drawAttacks(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, this, g);
		} else if(myPlayer.planePrepared()){
			
		}
		else{
			clearAvailability(myCells);
		}
		
		if(hoverX!=-1 || hoverY!=-1){
			if(!ValidAttackChecker.drawHoverInfo(g, this, hoverX, hoverY, manager))
				g.drawImage(manager.getHoverTransparency(), (hoverX)*Stage.BLOCK_SIZE, 1+((hoverY)*Stage.BLOCK_SIZE), null, null);
		}
	}
	public void changeRoom() {
		int specificRoomX = roomX;
		int specificRoomY = roomY;
		roomX = myRooms.getX(specificRoomX, specificRoomY);
		roomY = myRooms.getY(specificRoomX, specificRoomY);
		myRooms.buildRoom(myCells, roomX, roomY);
		
		int newX = myPlayer.getX() % ROOM_SIZE;
		int newY = myPlayer.getY() % ROOM_SIZE;
		newX += ROOM_SIZE*(specificRoomX - roomX);
		newY += ROOM_SIZE*(specificRoomY - roomY);
		
		setEnemiesAndCollectibles();
		myPlayer.resetLocation(newX, newY);
		addCollectibleSkillsToMap();
		drawRoom();
	}
	private void addCollectibleSkillsToMap() {
		for(CollectibleSkill c: myCollectibleSkills){
			if(!c.isDestroyed() && roomX==myRooms.getX(c.getRoomX(), c.getRoomY()) && roomY==myRooms.getY(c.getRoomX(), c.getRoomY()) && !myCollectibles.contains(c)){
				myCollectibles.add(c);
				int difX = c.getRoomX() - myRooms.getX(c.getRoomX(), c.getRoomY());
				int difY = c.getRoomY() - myRooms.getY(c.getRoomX(), c.getRoomY());
				System.out.println((c.getX() + ROOM_SIZE*difX) + " " + (c.getY() + ROOM_SIZE*difY));
				myCells.get(c.getX() + ROOM_SIZE*difX).get(c.getY() + ROOM_SIZE*difY).setCollectible(c);
			}
		}
	}
	private void drawRoom() {
		int roomWidth = myRooms.getWidth(roomX, roomY);
		int roomHeight = myRooms.getHeight(roomX, roomY);
		myRoom = new BufferedImage(ROOM_SIZE*BLOCK_SIZE*roomWidth, ROOM_SIZE*BLOCK_SIZE*roomHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = myRoom.getGraphics();
		for(int i=0; i<roomWidth*ROOM_SIZE; i++){
			for(int j=0; j<roomHeight*ROOM_SIZE; j++){
				((MapCell)myCells.get(i).get(j)).draw(g, manager, i, j);
			}
		}
		g.dispose();
	}
	private void setEnemiesAndCollectibles() {
		myEnemies.clear();
		myCollectibles.clear();
		myActiveBosses.clear();
		Random r = new Random();
		for(int i=0; i<myCells.size(); i++){
			for(int j=0; j<myCells.get(0).size(); j++){
				if(myCells.get(i).get(j).isPassable()){
					int rand = r.nextInt(700);
					if(rand < 5){
						Enemy e = new Enemy(i,j,this, 0);
						myEnemies.add(e);
						myCells.get(i).get(j).setEnemy(e);
					}
					else if(rand < 8){
						Collectible c = new Collectible(i, j, this, 0);
						myCollectibles.add(c);
						myCells.get(i).get(j).setCollectible(c);
					}
				}
			}
		}
	}
	public void addBossToMap(Boss b, Character direction){
		Random r = new Random();
		int x = 0;
		int y = 0;
		boolean done = false;
		while(!done){
			x = r.nextInt(myCells.size());
			y = r.nextInt(myCells.get(0).size());
			if(direction == 'u') y = myCells.get(0).size() - 1;
			if(direction == 'd') y = 0;
			if(direction == 'l') x = myCells.size() - 1;
			if(direction == 'r') x =0;
			if(myCells.get(x).get(y).isPassable()){
				done = true;
			}
		}
		myActiveBosses.add(b);
		b.setLocation(x, y);
	}
	private void createBosses() {
		for(int i=0; i<1; i++){
			myBosses.add(new Boss(1, 1, this, 100));
		}
	}
	private void createCollectibleSkills() {
		for(int i=0; i<2; i++){
			myCollectibleSkills.add(new CollectibleSkill(i, this));
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
		for(Boss b : myBosses){
			b.doTurn(myPlayer);
		}
	}
	private void clearAvailability(List<List<MapCell>> myCells) {
		for(int i=0; i<myCells.size(); i++){
			for(int j=0; j<myCells.get(0).size(); j++){
				myCells.get(i).get(j).setAvailable(false);
			}
		}
	}
	public void movePlayer() {
		myPlayer.move();
	}
	private void removeDeadEnemies() {
		for(int i=0; i<myEnemies.size(); i++){
			Enemy e = myEnemies.get(i);
			if(e.isDead()){
				Collectible c = new Collectible(e.getX(), e.getY(), this, e.getIndex());
				myEnemies.remove(e);
				myCollectibles.add(c);
				myCells.get(c.getX()).get(c.getY()).setCollectible(c);
				i--;
			}
		}
		for(int i=0; i<myBosses.size(); i++){
			Boss b = myBosses.get(i);
			if(b.isDead()){
				myEnemies.remove(b);
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
	private void setCamera() {
		int camX = myPlayer.getX();
		int camY = myPlayer.getY();
		if(camX + MAP_WIDTH/2 >= myCells.size()){
			camX = myCells.size() - MAP_WIDTH/2;
		}
		if(camX - MAP_WIDTH/2 < 0){
			camX = MAP_WIDTH/2;
		}
		if(camY + MAP_HEIGHT/2 >= myCells.get(0).size()){
			camY = myCells.get(0).size() - MAP_HEIGHT/2;
		}
		if(camY - MAP_HEIGHT/2 < 0){
			camY = MAP_HEIGHT/2;
		}
		myCamera.setX(camX);
		myCamera.setY(camY);
	}
	public void planeShift(String name) {
		myPlane.setState(name);
		drawRoom();
	}
	public void setQuickMove(boolean quick){
		quickMove = quick;
	}
	public void addPlayer(Player player) {
		myPlayer = player;
		setCamera();
	}
	public boolean isGameOver() {
		return myPlayer.isDead();
	}
	public List<List<MapCell>> getCells(){
		return myCells;
	}
	public Player getPlayer() {
		return myPlayer;
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
	public int getPlane() {
		return Integer.parseInt(myPlane.getState());
	}
	public void setRoomX(int x){
		roomX = x;
	}
	public void setRoomY(int y) {
		roomY = y;
	}
	public List<Boss> getBosses(){
		return myBosses;
	}
	public void setHoverRoomX(int hoverX) {
		hoverRoomX = hoverX;
	}
	public void setHoverRoomY(int hoverY) {
		hoverRoomY = hoverY;
	}
	public int getHoverRoomX(){
		return hoverRoomX;
	}
	public int getHoverRoomY(){
		return hoverRoomY;
	}
	public boolean wasInput() {
		return wasInput;
	}
	public List<CollectibleSkill> getCollectibleSkills() {
		return myCollectibleSkills;
	}
}
