package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UtilityObjects.Action;

public class Enemy extends GameObject{

	private int myX;
	private int myY;
	
	private int myHealth;
	private int myPower;
	
	private int sightRange;
	private int attackRange;
	
	private Stage myStage;
	private List<List<MapCell>> myCells;
	
	public Enemy(int x, int y, int sightrange, int attackrange, int health, int power, Stage stage){
		myStage = stage;
		myCells = myStage.getCells();
		myX = 3;
		myY = 9;
		sightRange = sightrange;
		attackRange = attackrange;
		myPower = power;
		myHealth = health;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isDead(){
		return myHealth<=0;
	}
	
	public void doDamage(Action action){
		myHealth-=action.getPower();
		if(myHealth<=0){
			myCells.get(myX).get(myY).removeEnemy();
		}
	}

	public void draw(Graphics g, int x, int y){
		g.drawImage(myStage.getManager().getImage(this),x*Stage.BLOCK_SIZE, 1+(y*Stage.BLOCK_SIZE), null);
	}
	
	public void attack(Player p){
		p.doDamage(new Action(0, "earth", myPower, 0, true));
	}
	
	public void move(Player p){
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
			availableMoves.remove(availableMoves.get(rand));
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
	
	public int getSightRange(){
		return sightRange;
	}

	public void doTurn(Player myPlayer) {
		int dist = Math.abs(myPlayer.getX() - myX) + Math.abs(myPlayer.getY() - myY);
		if(dist <= attackRange){
			attack(myPlayer);
		}
		else{
			move(myPlayer);
		}
	}

	public int getAttackRange() {
		return attackRange;
	}

}
