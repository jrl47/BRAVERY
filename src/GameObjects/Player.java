package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;

import UtilityObjects.Action;
import UtilityObjects.Inventory;

public class Player extends GameObject{

	private int myX;
	private int myY;
	
	private String myCommand;
	private boolean preparedMove;
	private boolean preparedAttack;
	
	private int targetX;
	private int targetY;
	
	private int myHealth;
	
	private int damageTaken;
	
	private Stage myStage;
	private List<List<MapCell>> myCells;
	
	private boolean tookAction;
	
	private Inventory myInventory;
	
	public Player(Stage stage){
		myX = 2;
		myY = 2;
		
		myHealth = 12;
		
		myStage = stage;
		myCells = myStage.getCells();
		
		myCells.get(myX).get(myY).addPlayer(this);
		
		targetX = Integer.MIN_VALUE;
		targetY = Integer.MIN_VALUE;
		
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
		g.setColor(Color.GREEN);
		g.fillRect(((Stage.MAP_WIDTH/2))*Stage.BLOCK_SIZE, 1+(((Stage.MAP_HEIGHT/2))*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(((Stage.MAP_WIDTH/2))*Stage.BLOCK_SIZE, 1+(((Stage.MAP_HEIGHT/2))*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE-1, Stage.BLOCK_SIZE-1);
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
	
	public String getCommand(){
		return myCommand;
	}
	public void setCommand(String s){
		myCommand = s;
	}
	public void clearCommand(){
		myCommand = null;
	}
	
	public Inventory getInventory(){
		return myInventory;
	}
	
	public void useKeyPress(KeyEvent k) {
		if(k==null){
			return;
		}
		if(k.getKeyCode()==KeyEvent.VK_W)
			myCommand = "Up";
		if(k.getKeyCode()==KeyEvent.VK_S)
			myCommand = "Down";
		if(k.getKeyCode()==KeyEvent.VK_A)
			myCommand = "Left";
		if(k.getKeyCode()==KeyEvent.VK_D)
			myCommand = "Right";
	}

	public void stopAction() {
		preparedMove = false;
		preparedAttack = false;
		damageTaken = 0;
	}
	public boolean actionPrepared() {
		return preparedMove || preparedAttack;
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
	
	public void move(){
		myCells.get(myX).get(myY).removePlayer();
		myX += targetX;
		myY += targetY;
		targetX = 0;
		targetY = 0;
		stopAction();
		tookAction = true;
		MapCell cell = myCells.get(myX).get(myY);
		chargeForAction(cell);
		getCollectible(cell);
		cell.addPlayer(this);
	}
	
	public void attack(Enemy enemy) {
		stopAction();
		tookAction = true;
		MapCell cell = myCells.get(myX+targetX).get(myY+targetY);
		Enemy e = cell.getEnemy();
		e.doDamage(cell.getAction());
		chargeForAction(cell);
	}

	public void chargeForAction(MapCell mapCell) {
		if(mapCell.getAction().getType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()-mapCell.getAction().getCost());
		if(mapCell.getAction().getType().equals("air"))
			myInventory.setAir(myInventory.getAir()-mapCell.getAction().getCost());
		if(mapCell.getAction().getType().equals("water"))
			myInventory.setWater(myInventory.getWater()-mapCell.getAction().getCost());
		if(mapCell.getAction().getType().equals("fire"))
			myInventory.setFire(myInventory.getFire()-mapCell.getAction().getCost());
	}

	public void getCollectible(MapCell mapCell) {
		if(mapCell.getCollectible()==null)
			return;
		
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
}
