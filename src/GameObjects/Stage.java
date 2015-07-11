package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Main.World;
import Utilities.AttackDrawer;
import Utilities.Camera;
import Utilities.DeciduousTileManager;
import Utilities.MoveDrawer;
import Utilities.ValidAttackChecker;
import UtilityObjects.Action;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	public static final int MAP_WIDTH = 27;
	public static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	private BufferedImage myMap;
	
	private List<List<MapCell>> myCells;
	private List<Enemy> myEnemies;
	private DeciduousTileManager manager;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	private Camera myCamera;
	
	private int enemyAnimationCounter;
	private static final int ENEMY_ANIMATION_START = 10;
	
	private MapCell outsideBorder;
	
	public Stage() {
		super();
		myCamera = new Camera();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myEnemies = new ArrayList<Enemy>();
		myCells = new ArrayList<List<MapCell>>();
		try {
			manager = new DeciduousTileManager(this);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			myMap = ImageIO.read(World.class.getResource("/mapData.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0; i<myMap.getWidth(); i++){
			myCells.add(new ArrayList<MapCell>());
		}
		
		for(int i=0; i<myMap.getWidth(); i++){
			for(int j=0; j<myMap.getHeight(); j++){
				myCells.get(i).add(new MapCell(i, j, this));
				if(myMap.getRGB(i,j)!=-16777216){
					myCells.get(i).get(j).setPassable(true);
					if(i==4 && j == 3){
						myCells.get(i).get(j).setCollectible(new Collectible(2000, "earth"));
					}
					if(i==3 && j == 9){
						Enemy e = new Enemy(3,9, 6, 3, 10, 5, this);
						myEnemies.add(e);
						myCells.get(i).get(j).setEnemy(e);
					}
					if(i==12 && j == 3){
						Enemy e = new Enemy(12,3, 6, 3, 10, 5, this);
						myEnemies.add(e);
						myCells.get(i).get(j).setEnemy(e);
					}
				}
				else{
					myCells.get(i).get(j).setID(MapCell.WATER);
				}
			}
		}
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
			enemyAnimationCounter = ENEMY_ANIMATION_START;
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
		if(myPlayer.getCommand().equals("Up")){
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()-1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(-1);
				movePlayer();
				myPlayer.clearCommand();
			}
		}
		else if(myPlayer.getCommand().equals("Down")){
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()+1).isPassable()){
				myPlayer.setTargetX(0);
				myPlayer.setTargetY(1);
				movePlayer();
				myPlayer.clearCommand();
			}
		}
		else if(myPlayer.getCommand().equals("Left")){
			if(myCells.get(myPlayer.getX()-1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(-1);
				myPlayer.setTargetY(0);
				movePlayer();
				myPlayer.clearCommand();
			}
		}
		else if(myPlayer.getCommand().equals("Right")){
			if(myCells.get(myPlayer.getX()+1).get(myPlayer.getY()).isPassable()){
				myPlayer.setTargetX(1);
				myPlayer.setTargetY(0);
				movePlayer();
				myPlayer.clearCommand();
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
}
