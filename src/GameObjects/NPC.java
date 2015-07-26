package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import UtilitiesData.EnemyBuilder;
import UtilitiesData.EnemyData;

public class NPC extends GameObject{

	protected int myX;
	protected int myY;
	
	
	private int myIndex;
	private boolean isRemoved;
	
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
		data = EnemyBuilder.getEnemyObject(myIndex);
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
			myPlayer.addNPC(this);
			isRemoved = true;
		}
	}

	public boolean isRemoved(){
		return isRemoved;
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
