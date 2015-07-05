package GameObjects;

import java.awt.Color;
import java.awt.Graphics;

public class MapCell extends GameObject{

	private boolean isPassable;
	private boolean isAvailable; // referring to immediate access for movement, attack, etc. "highlightability"
	private int myX;
	private int myY;
	private int myCost;
	private String myCostType;
	
	private Collectible myCollectible;
	private Enemy myEnemy;
	
	public MapCell(int x, int y){
		myX = x;
		myY = y;
		myCostType = "";
	}
	public void setPassable(boolean b){
		isPassable = b;
	}
	public boolean isPassable(){
		return isPassable;
	}
	
	public void setAvailable(boolean b){
		isAvailable = b;
	}
	public boolean isAvailable(){
		return isAvailable;
	}
	
	public void setCost(int c){
		myCost = c;
	}
	public int getCost(){
		return myCost;
	}
	
	public void setCostType(String t){
		myCostType = t;
	}
	public String getCostType(){
		return myCostType;
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
	
	public void draw(Graphics g, int x, int y){
		if(isPassable){
			g.setColor(Color.WHITE);
			if(myCollectible!=null)
				g.setColor(Color.MAGENTA);
			if(myEnemy!=null){
				myEnemy.draw(g, x, y);
				return;
			}
		}
		else{
			g.setColor(Color.BLACK);
		}
		g.fillRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE-1, Stage.BLOCK_SIZE-1);
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

}
