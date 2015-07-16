package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UtilitiesData.EnemyBuilder;
import UtilitiesData.EnemyData;
import UtilityObjects.Action;

public class Enemy extends GameObject{

	protected int myX;
	protected int myY;
	
	protected int oldX;
	protected int oldY;
	protected boolean attacked;
	
	private int myIndex;
	
	protected int myHealth;
	protected int myPower;
	
	protected String myName;
	
	protected int sightRange;
	protected int attackRange;
	
	protected Stage myStage;
	protected List<List<MapCell>> myCells;
	
	public Enemy(int x, int y, Stage stage, int index){
		myStage = stage;
		myCells = myStage.getCells();
		myX = x;
		myY = y;
		oldX = myX;
		oldY = myY;
		myIndex = index;
		EnemyData data = null;
		if(myIndex < 100){
			data = EnemyBuilder.getEnemyObject(myIndex);
		} else{
			data = EnemyBuilder.getBossObject(myIndex-100);
		}
		myName = data.getName();
		sightRange = data.getSightRange();
		attackRange = data.getAttackRange();
		myPower = data.getPower();
		myHealth = data.getHealth();
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

	public void draw(Graphics g){
		if(myStage.getRelativeX(myX)*Stage.BLOCK_SIZE <0 || 
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) < 0 ||
				myStage.getRelativeX(myX)*Stage.BLOCK_SIZE >= myStage.getWidth()*Stage.BLOCK_SIZE ||
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) >= myStage.getHeight()*Stage.BLOCK_SIZE)
			return;
		g.drawImage(myStage.getManager().getImage(this),myStage.getRelativeX(myX)*Stage.BLOCK_SIZE,
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE), null);
	}
	
	public void drawOld(Graphics g) {
		if(myStage.getRelativeX(myX)*Stage.BLOCK_SIZE <0 || 
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) < 0 ||
				myStage.getRelativeX(myX)*Stage.BLOCK_SIZE >= myStage.getWidth()*Stage.BLOCK_SIZE ||
				1+(myStage.getRelativeY(myY)*Stage.BLOCK_SIZE) >= myStage.getHeight()*Stage.BLOCK_SIZE)
			return;
		g.drawImage(myStage.getManager().getImage(this),myStage.getRelativeX(oldX)*Stage.BLOCK_SIZE,
				1+(myStage.getRelativeY(oldY)*Stage.BLOCK_SIZE), null);
	}
	
	public void drawHover(Graphics g) {
		Color c = new Color(1f, .5f, .3f, .6f);
		g.setColor(c);
		if(!attacked){
		g.fillRect((myStage.getRelativeX(myX))*Stage.BLOCK_SIZE,
				1+((myStage.getRelativeY(myY))*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		}
		else{
			g.fillRect(myStage.getRelativeX(myStage.getPlayer().getX())*Stage.BLOCK_SIZE,
					1+((myStage.getRelativeY(myStage.getPlayer().getY()))*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		}
	}
	
	public void attack(Player p){
		oldX = myX;
		oldY = myY;
		attacked = true;
		Action a = new Action("enemyattack");
		a.setPower(myPower);
		p.doDamage(a);
	}
	
	public void move(Player p){
		attacked = false;
		oldX = myX;
		oldY = myY;
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

		if(myY - 1 < 0 || !myCells.get(myX).get(myY-1).isPassable())
			preferredMoves.remove("u");
		if(myY + 1 >= myCells.get(myX).size() || !myCells.get(myX).get(myY+1).isPassable())
			preferredMoves.remove("d");
		if(myX - 1 < 0 || !myCells.get(myX-1).get(myY).isPassable())
			preferredMoves.remove("l");
		if(myX + 1 >= myCells.size() || !myCells.get(myX+1).get(myY).isPassable())
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
		if(!myStage.getCell(myX, myY).isLand()){
			myHealth = -1;
			return;
		}
		
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
	public int getPower() {
		return myPower;
	}
	public int getHealth() {
		return myHealth;
	}

	public int getIndex() {
		return myIndex;
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}

	public String getName() {
		return myName;
	}
}
