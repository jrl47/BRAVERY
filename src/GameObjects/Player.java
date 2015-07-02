package GameObjects;

import java.awt.Graphics;

public class Player extends GameObject{

	private int myX;
	private int myY;
	
	public Player(){
		myX = 2;
		myY = 2;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
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
}
