package GameObjects;

import java.awt.event.KeyEvent;

public class Player extends GameObject{

	private int myX;
	private int myY;
	
	private String myCommand;
	private boolean preparedMove;
	
	private int targetX;
	private int targetY;
	
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

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
	public void setX(int x){
		myX = x;
		stopMove();
	}
	public void setY(int y){
		myY = y;
		stopMove();
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

	public void stopMove() {
		preparedMove = false;
	}
	public void prepareMove(){
		preparedMove = true;
	}
	public boolean movePrepared(){
		return preparedMove;
	}
	public void move(){
		myX += targetX;
		myY += targetY;
		targetX = 0;
		targetY = 0;
		stopMove();
	}
}
