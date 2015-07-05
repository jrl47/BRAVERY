package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

public class Enemy extends GameObject{

	private int myX;
	private int myY;
	
	private int sightRange;
	
	public Enemy(int x, int y, int range){
		myX = 3;
		myY = 9;
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
		g.setColor(Color.PINK);
		g.fillRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE-1, Stage.BLOCK_SIZE-1);
	}
	
	public void move(Player p, List<List<MapCell>> myCells){
		int dist = Math.abs(p.getX() - myX) + Math.abs(p.getY() - myY);
		String direction;
		if(dist > sightRange){
			Random r = new Random();
			
		} else{
			
		}
	}

}
