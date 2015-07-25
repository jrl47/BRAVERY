package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UtilitiesData.EnemyBuilder;
import UtilitiesData.EnemyData;
import UtilityObjects.Action;

public class NPC extends GameObject{

	protected int myX;
	protected int myY;
	
	
	private int myIndex;
	
	protected String myName;
	
	protected Stage myStage;
	protected List<List<MapCell>> myCells;
	protected EnemyData data;
	
	public NPC(int x, int y, Stage stage, int index){
		myStage = stage;
		myCells = myStage.getCells();
		myX = x;
		myY = y;
		myIndex = index;
		data = null;
		if(myIndex < 100){
			data = EnemyBuilder.getEnemyObject(myIndex);
		} else{
			data = EnemyBuilder.getBossObject(myIndex-100);
		}
		myName = data.getName();
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
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

	public void doTurn(Player myPlayer) {
		int dist = Math.abs(myPlayer.getX() - myX) + Math.abs(myPlayer.getY() - myY);
		if(dist <= 1){
//			attack(myPlayer);
		}
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
