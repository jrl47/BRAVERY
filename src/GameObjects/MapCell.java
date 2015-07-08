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
	private int myCost;
	private String myCostType;
	
	private String myID;
	
	private Collectible myCollectible;
	private Enemy myEnemy;
	private Player myPlayer;
	private Action myAction;
	private Stage myStage;
	
	public MapCell(int x, int y, Stage s){
		myX = x;
		myY = y;
		myID = GRASS;
		myStage = s;
		myCostType = "";
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
	
	public void setAction(Action a){
		myAction = a;
	}
	public Action getAction(){
		return myAction;
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
		if(myCollectible!=null){
			g.drawImage(myStage.getManager().getImage(myCollectible),x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), null);
			return;
		}
		if(myEnemy!=null){
			myEnemy.draw(g, x, y);
			return;
		} else{
			g.drawImage(manager.getImage(this),x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), null);
			return;
		}
	}
	public void removeCollectible() {
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

}
