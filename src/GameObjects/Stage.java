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
import Utilities.MoveDrawer;
import UtilityObjects.Action;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	public static final int MAP_WIDTH = 27;
	public static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	private BufferedImage myMap;
	
	private List<List<MapCell>> myCells;
	private List<Enemy> myEnemies;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	
	public Stage() {
		super();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myEnemies = new ArrayList<Enemy>();
		myCells = new ArrayList<List<MapCell>>();
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
				myCells.get(i).add(new MapCell(i, j));
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
			}
		}
		
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
			if(b && myPlayer.movePrepared() && myCells.get(locX).get(locY).isAvailable()){
				movePlayer();
			}
			if(b && myPlayer.attackPrepared() && myCells.get(locX).get(locY).getEnemy()!=null){
				doPlayerAttack();
				myPlayer.clearCommand();
			}
		}
	}

	private void movePlayer() {
		myPlayer.move();
	}
	
	private void doPlayerAttack() {
		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		myPlayer.attack(myCells.get(locX).get(locY).getEnemy());
	}

	@Override
	public void step() {
		if(myPlayer.isDead()){
			
		}
		
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
					g.setColor(Color.BLACK);
					g.fillRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
					g.setColor(Color.GRAY);
					g.drawRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
				}
				else{
					myCells.get(i).get(j).draw(g,xcounter,ycounter);
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
			g.setColor(Color.lightGray);
			g.fillRect((hoverX)*BLOCK_SIZE, 1+((hoverY)*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
			g.setColor(Color.GRAY);
			g.drawRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
			
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
				myCells.get(i).get(j).setAction(new Action(0, "null", 0));
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

}
