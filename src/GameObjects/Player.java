package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import UtilityObjects.Inventory;

public class Player extends GameObject{

	private int myX;
	private int myY;
	
	private String myCommand;
	private boolean preparedMove;
	private boolean preparedAttack;
	
	private int targetX;
	private int targetY;
	
	private boolean tookAction;
	
	private Inventory myInventory;
	
	public Player(){
		myX = 2;
		myY = 2;
		
		targetX = Integer.MIN_VALUE;
		targetY = Integer.MIN_VALUE;
		
		myInventory = new Inventory();
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
	
	public void setX(int x){
		myX = x;
		stopAction();
	}
	public void setY(int y){
		myY = y;
		stopAction();
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
	
	public Inventory getInventory(){
		return myInventory;
	}
	
	public void useKeyPress(KeyEvent k) {
		if(k==null){
			myCommand = null;
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
		myX += targetX;
		myY += targetY;
		targetX = 0;
		targetY = 0;
		stopAction();
		tookAction = true;
	}

	public void chargeForMove(MapCell mapCell) {
		if(mapCell.getCostType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()-mapCell.getCost());
		if(mapCell.getCostType().equals("air"))
			myInventory.setAir(myInventory.getAir()-mapCell.getCost());
		if(mapCell.getCostType().equals("water"))
			myInventory.setWater(myInventory.getWater()-mapCell.getCost());
		if(mapCell.getCostType().equals("fire"))
			myInventory.setFire(myInventory.getFire()-mapCell.getCost());
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
}
