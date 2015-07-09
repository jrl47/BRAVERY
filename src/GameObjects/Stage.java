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
	
	private MapCell outsideBorder;
	
	public Stage() {
		super();
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
					boolean enemyInRange = false;
					int lim = myPlayer.getAction().getRange();
					if(myPlayer.getAction().isRoundSplash()){
						for(int x=-lim; x<=lim; x++){
							for(int y=-lim; y<=lim; y++){
								if(Math.abs(x) + Math.abs(y) < lim && myCells.get(locX+x).get(locY+y).getEnemy()!=null)
									enemyInRange = true;
							}
						}
					}
					else{
						int xDif = -(myPlayer.getX() - locX);
						int yDif = -(myPlayer.getY() - locY);
						if(xDif > yDif){
							for(int x = Integer.signum(xDif); Math.abs(x) < lim; x+=Integer.signum(xDif)){
								if(myCells.get(locX+x).get(locY).getEnemy()!=null)
									enemyInRange = true;
							}
						}
						else if(yDif > xDif){
							for(int x = Integer.signum(yDif); Math.abs(x) < lim; x+=Integer.signum(yDif)){
								if(myCells.get(locX).get(locY+x).getEnemy()!=null)
									enemyInRange = true;
							}
						}
						else if(yDif==0 && xDif ==0){
							
						}
						else if(xDif==yDif){
							for(int x = Integer.signum(xDif); Math.abs(x) < lim; x+=Integer.signum(xDif)){
								if(myCells.get(locX+x).get(locY+x).getEnemy()!=null)
									enemyInRange = true;
							}
						}
						else{
							for(int x = Integer.signum(xDif); Math.abs(x) < lim; x+=Integer.signum(xDif)){
								if(myCells.get(locX+x).get(locY-x).getEnemy()!=null)
									enemyInRange = true;
							}
						}
					}
					if(enemyInRange){
						doPlayerAttack();
						myPlayer.clearCommand();
					}
				}
			}
		}
	}

	private void movePlayer() {
		myPlayer.move();
	}
	
	private void doPlayerAttack() {
		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		int lim = myPlayer.getAction().getRange();
		if(myPlayer.getAction().isRoundSplash()){
			for(int i=-lim; i<=lim; i++){
				for(int j=-lim; j<=lim; j++){
					if(Math.abs(i) + Math.abs(j) < lim && myCells.get(locX+i).get(locY+j).getEnemy()!=null)
						myPlayer.attack(myCells.get(locX+i).get(locY+j).getEnemy());
				}
			}
		}
		else{
			int xDif = -(myPlayer.getX() - locX);
			int yDif = -(myPlayer.getY() - locY);
			if(xDif > yDif){
				for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(myCells.get(locX+i).get(locY).getEnemy()!=null)
						myPlayer.attack(myCells.get(locX+i).get(locY).getEnemy());
				}
			}
			else if(yDif > xDif){
				for(int i = Integer.signum(yDif); Math.abs(i) < lim; i+=Integer.signum(yDif)){
					if(myCells.get(locX).get(locY+i).getEnemy()!=null)
						myPlayer.attack(myCells.get(locX).get(locY+i).getEnemy());
				}
			}
			else if(yDif==0 && xDif ==0){
				
			}
			else if(xDif==yDif){
				for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(myCells.get(locX+i).get(locY+i).getEnemy()!=null)
						myPlayer.attack(myCells.get(locX+i).get(locY+i).getEnemy());
				}
			}
			else{
				for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(myCells.get(locX+i).get(locY-i).getEnemy()!=null)
						myPlayer.attack(myCells.get(locX+i).get(locY-i).getEnemy());
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
		
		if(myPlayer.checkForEnemyTurn()){
			executeEnemyTurns();
		}
		
		if(myPlayer.getCommand()==null)
			return;
		
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

	private void executeEnemyTurns() {
		for(int i=0; i<myEnemies.size(); i++){
			Enemy e = myEnemies.get(i);
			if(e.isDead()){
				myEnemies.remove(e);
				i--;
			}
		}
		for(Enemy e: myEnemies){
			e.doTurn(myPlayer);
		}
	}

	@Override
	public void draw(Graphics g) {
		int xcounter = 0;
		int ycounter = 0;
		for(int i=myPlayer.getX() - (MAP_WIDTH/2); i<=(MAP_WIDTH/2) + myPlayer.getX(); i++){
			for(int j=myPlayer.getY() - (MAP_HEIGHT/2); j<(MAP_HEIGHT/2) + 1 + myPlayer.getY(); j++){
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
		
		if(myPlayer.movePrepared()){
			MoveDrawer.drawMoves(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myCells, myPlayer, g);
		} else if(myPlayer.actionPrepared()){
			AttackDrawer.drawAttacks(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myCells, myPlayer, g);
		}
		else{
			clearAvailability(myCells);
		}
		
		myPlayer.draw(g);
		
		if(hoverX!=-1 || hoverY!=-1){
			ValidAttackChecker.drawHoverInfo(g, myPlayer, myCells, hoverX, hoverY, manager);
			
			if(!(myPlayer.getX() + hoverX - MAP_WIDTH/2 < 0 || myPlayer.getY() + hoverY - MAP_HEIGHT/2 < 0 
					|| myPlayer.getX() + hoverX - MAP_WIDTH/2 >= myCells.size() || myPlayer.getY() + hoverY - MAP_HEIGHT/2 >= myCells.get(0).size())){
				myPlayer.setTargetX(hoverX - MAP_WIDTH/2);
				myPlayer.setTargetY(hoverY - MAP_HEIGHT/2);
			}
			else{
				myPlayer.setTargetX(Integer.MIN_VALUE);
				myPlayer.setTargetY(Integer.MIN_VALUE);
			}
		}
		
		wasInput = false;
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

}
