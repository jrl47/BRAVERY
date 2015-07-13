package GameObjects;

import java.awt.Color;
import java.awt.Graphics;

import Utilities.DeciduousTileManager;
import UtilityObjects.Action;

public class MapCell extends GameObject{

	public static final String GRASS = "grass";
	public static final String DIRT = "dirt";
	public static final String SMALL_ROCKS = "srocks";
	public static final String LARGE_ROCKS = "lrocks";
	public static final String WATER = "water";
	public static final String FLOWERS = "flowers";
	public static final String BRICKS = "bricks";
	public static final String SHOALS = "shoals";
	public static final String FOREST = "forest";
	
	private boolean isPassable;
	private boolean isAvailable; // referring to immediate access for movement, attack, etc. "highlightability"
	private int myX;
	private int myY;
	
	private String myID;
	
	private Collectible myCollectible;
	private Enemy myEnemy;
	private Player myPlayer;
	
	public MapCell(int x, int y){
		myX = x;
		myY = y;
		myID = GRASS;
	}
	public String getID(){
		return myID;
	}
	public void setID(String s){
		myID = s;
	}
	public void setPassable(boolean b){
		isPassable = b;
	}
	public boolean isPassable(){
		return isPassable && myPlayer==null && myEnemy==null;
	}
	
	public void setAvailable(boolean b){
		isAvailable = b;
	}
	public boolean isAvailable(){
		return isAvailable;
	}
	
	public void setCollectible(Collectible c){
		myCollectible = c;
	}
	public Collectible getCollectible(){
		return myCollectible;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics g, DeciduousTileManager manager, int x, int y){
		g.drawImage(manager.getImage(this),x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), null);
	}
	public void removeCollectible() {
		myCollectible.destroy();
		myCollectible = null;
	}
	public void setEnemy(Enemy enemy) {
		myEnemy = enemy;
	}
	public void removeEnemy() {
		myEnemy = null;
	}
	public Enemy getEnemy() {
		return myEnemy;
	}
	public void addPlayer(Player player) {
		myPlayer = player;
	}
	public void removePlayer() {
		myPlayer = null;
	}
	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	public Player getPlayer() {
		return myPlayer;
	}

}
