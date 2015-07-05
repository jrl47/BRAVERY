package GameObjects;

import java.awt.Color;
import java.awt.Graphics;

public class MapCell extends GameObject{

	private boolean isPassable;
	private boolean isAvailable; // referring to immediate access for movement, attack, etc. "highlightability"
	private int myX;
	private int myY;
	private int myCost;
	
	public MapCell(int x, int y){
		myX = x;
		myY = y;
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
		}
		else{
			g.setColor(Color.BLACK);
		}
		g.fillRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE-1, Stage.BLOCK_SIZE-1);
	}

}
