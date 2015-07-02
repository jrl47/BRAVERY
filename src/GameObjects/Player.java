package GameObjects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Player extends GameObject{

	private int myX;
	private int myY;
	
	private String myCommand;
	
	public Player(){
		myX = 2;
		myY = 2;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
	public void setX(int x){
		myX = x;
	}
	public void setY(int y){
		myY = y;
	}
	
	public String getCommand(){
		return myCommand;
	}

	public void useKeyPress(KeyEvent k) {
		if(k==null){
			myCommand = null;
			return;
		}
		if(k.getKeyCode()==KeyEvent.VK_UP)
			myCommand = "Up";
		if(k.getKeyCode()==KeyEvent.VK_DOWN)
			myCommand = "Down";
		if(k.getKeyCode()==KeyEvent.VK_LEFT)
			myCommand = "Left";
		if(k.getKeyCode()==KeyEvent.VK_RIGHT)
			myCommand = "Right";
	}
}
