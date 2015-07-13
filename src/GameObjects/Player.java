package GameObjects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import UtilityObjects.Action;
import UtilityObjects.Inventory;

public class Player extends GameObject{

	private int myX;
	private int myY;

	private boolean preparedMove;
	private boolean preparedAttack;
	private boolean preparedPlane;
	
	private int targetX;
	private int targetY;
	
	private int myHealth;
	
	private int damageTaken;
	
	private Stage myStage;
	private List<List<MapCell>> myCells;
	private boolean isPaused;
	private int planeCounter;
	
	private boolean tookAction;
	private Action myAction;
	
	private Inventory myInventory;
	
	public Player(Stage stage){
		myX = 23;
		myY = 2;
		
		myHealth = 16;
		
		
		myAction = new Action("wait");
		
		myStage = stage;
		myCells = myStage.getCells();
		
		myCells.get(myX).get(myY).addPlayer(this);
		myInventory = new Inventory();
	}
	
	public boolean isDead(){
		return myHealth<=0;
	}
	
	public void doDamage(Action action){
		myHealth-=action.getPower();
		damageTaken += action.getPower();
		if(myHealth<=0){
			myCells.get(myX).get(myY).removeEnemy();
		}
	}
	
	public int getDamageTaken(){
		return damageTaken;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		
	}

	@Override
	public void step() {

	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(myStage.getManager().getImage(this),(myStage.getRelativeX(myX))*Stage.BLOCK_SIZE, 1+((myStage.getRelativeY(myY))*Stage.BLOCK_SIZE), null);
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
	public int getTargetX() {
		return targetX;
	}
	public int getTargetY() {
		return targetY;
	}
	
	public void setTargetX(int x){
		targetX = x;
	}
	public void setTargetY(int y){
		targetY = y;
	}
	
	public Inventory getInventory(){
		return myInventory;
	}
	
	public void useKeyPress(KeyEvent k) {
		if(k==null){
			return;
		}
		if(k.getKeyCode()==KeyEvent.VK_W || k.getKeyCode()==KeyEvent.VK_S || 
				k.getKeyCode()==KeyEvent.VK_A || k.getKeyCode()==KeyEvent.VK_D){
			myAction = new Action("wait");
		}
		
		if(k.getKeyCode()==KeyEvent.VK_W)
			myAction = new Action("up");
		if(k.getKeyCode()==KeyEvent.VK_S)
			myAction = new Action("down");
		if(k.getKeyCode()==KeyEvent.VK_A)
			myAction = new Action("left");
		if(k.getKeyCode()==KeyEvent.VK_D)
			myAction = new Action("right");
	}

	public void stopAction() {
		preparedMove = false;
		preparedAttack = false;
		preparedPlane = false;
		damageTaken = 0;
		myAction = new Action("wait");
	}
	public boolean actionPrepared() {
		return preparedMove || preparedAttack || preparedPlane;
	}
	
	public void prepareMove(){
		preparedMove = true;
	}
	public boolean movePrepared(){
		return preparedMove;
	}

	public void prepareAttack() {
		preparedAttack = true;
	}
	public boolean attackPrepared(){
		return preparedAttack;
	}
	
	public void preparePlane() {
		preparedPlane = true;
	}
	public boolean planePrepared(){
		return preparedPlane;
	}
	
	public void move(){
		if(!isPaused){
			myCells.get(myX).get(myY).removePlayer();
			myX += targetX;
			myY += targetY;
			targetX = 0;
			targetY = 0;
			tookAction = true;
			MapCell cell = myCells.get(myX).get(myY);
			chargeForAction();
			getCollectible(cell);
			cell.addPlayer(this);
			stopAction();
		}
	}
	
	public void attack(Enemy enemy) {
		if(!isPaused){
			tookAction = true;
			enemy.doDamage(myAction);
			chargeForAction();
			stopAction();
		}
	}
	
	public void planeShift(){
		if(!isPaused){
			tookAction = true;
			myStage.planeShift(myAction.getName());
			chargeForAction();
			stopAction();
		}
	}
	
	public void resetLocation(int x, int y){
		if(!isPaused){
			myCells.get(myX).get(myY).removePlayer();
			myX = x;
			myY = y;
			targetX = 0;
			targetY = 0;
			tookAction = true;
			MapCell cell = myCells.get(myX).get(myY);
			chargeForAction();
			getCollectible(cell);
			cell.addPlayer(this);
			stopAction();
		}
	}

	public void chargeForAction() {
		if(myAction.getType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()-myAction.getCost());
		if(myAction.getType().equals("air"))
			myInventory.setAir(myInventory.getAir()-myAction.getCost());
		if(myAction.getType().equals("water"))
			myInventory.setWater(myInventory.getWater()-myAction.getCost());
		if(myAction.getType().equals("fire"))
			myInventory.setFire(myInventory.getFire()-myAction.getCost());
		if(myAction.getType().equals("all")){
			myInventory.setEarth(myInventory.getEarth()-myAction.getCost());
			myInventory.setAir(myInventory.getAir()-myAction.getCost());
			myInventory.setWater(myInventory.getWater()-myAction.getCost());
			myInventory.setFire(myInventory.getFire()-myAction.getCost());
		}
	}

	public void getCollectible(MapCell mapCell) {
		if(mapCell.getCollectible()==null){
			return;
		}
		
		if(mapCell.getCollectible().getType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("air"))
			myInventory.setAir(myInventory.getAir()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("water"))
			myInventory.setWater(myInventory.getWater()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("fire"))
			myInventory.setFire(myInventory.getFire()+mapCell.getCollectible().getAmount());
		
		mapCell.removeCollectible();
	}
	
	public boolean checkForEnemyTurn(){
		if(tookAction){
			tookAction = false;
			return true;
		}
		return tookAction;
	}

	public int getHealth() {
		return myHealth;
	}

	public Action getAction() {
		return myAction;
	}
	public void setAction(Action a) {
		myAction = a;
	}
	public void clearAction() {
		myAction = new Action("wait");
	}
	
	public void pause(){
		isPaused = true;
	}
	public void unpause(){
		isPaused = false;
	}
	public boolean isPaused(){
		return isPaused;
	}
	
	public int getPlaneCounter(){
		return planeCounter;
	}
}
