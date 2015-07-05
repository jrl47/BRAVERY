package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
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
		List<String> availableMoves = new ArrayList<String>();
		availableMoves.add("u");
		availableMoves.add("d");
		availableMoves.add("l");
		availableMoves.add("r");
		List<String> preferredMoves = new ArrayList<String>();
		if(dist > sightRange){
			Random r = new Random();
			for(int i=4; i>0; i--){
			int rand = r.nextInt(i);
			preferredMoves.add(availableMoves.get(rand));
			}
		} else{
			if(Math.abs(p.getX() - myX) > Math.abs(p.getY() - myY)){
				if(p.getX() > myX) {
					preferredMoves.add("r");
				} else{
					preferredMoves.add("l");
				}
				if(p.getY() > myY) {
					preferredMoves.add("d");
				} else{
					preferredMoves.add("u");
				}
			} else{
				if(p.getY() > myY) {
					preferredMoves.add("d");
				} else{
					preferredMoves.add("u");
				}
				if(p.getX() > myX) {
					preferredMoves.add("r");
				} else{
					preferredMoves.add("l");
				}
			}
		}
		
		if(!myCells.get(myX).get(myY-1).isPassable())
			preferredMoves.remove("u");
		if(!myCells.get(myX).get(myY+1).isPassable())
			preferredMoves.remove("d");
		if(!myCells.get(myX-1).get(myY).isPassable())
			preferredMoves.remove("l");
		if(!myCells.get(myX+1).get(myY).isPassable())
			preferredMoves.remove("r");
		
		if(preferredMoves.size()>0){
			myCells.get(myX).get(myY).removeEnemy();
			String dir = preferredMoves.get(0);
			if(dir.equals("u")){
				myY--;
			}
			if(dir.equals("d")){
				myY++;
			}
			if(dir.equals("l")){
				myX--;
			}
			if(dir.equals("r")){
				myX++;
			}
			myCells.get(myX).get(myY).setEnemy(this);
		}
	}

}
