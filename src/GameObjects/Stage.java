package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Main.World;
import Utilities.MoveDrawer;

public class Stage extends GameObject{

	public static final int BLOCK_SIZE = 32;
	private static final int MAP_WIDTH = 27;
	private static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	private BufferedImage myMap;
	
	private List<List<MapCell>> myCells;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	
	public Stage(Player p) {
		super();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myPlayer = p;
		myCells = new ArrayList<List<MapCell>>();
		try {
			myMap = ImageIO.read(World.class.getResource("/mapData.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0; i<myMap.getWidth(); i++){
			myCells.add(new ArrayList<MapCell>());
		}
		
		for(int i=0; i<myMap.getWidth(); i++){
			for(int j=0; j<myMap.getHeight(); j++){
				myCells.get(i).add(new MapCell(i, j));
				if(myMap.getRGB(i,j)!=-16777216)
					myCells.get(i).get(j).setPassable(true);
			}
		}
		
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		wasInput = true;
		if(j>0 && j < 674 && i < MAP_WIDTH * 32){
			hoverX = i/32;
			hoverY = (j+1)/32;
		}
		else{
			hoverX = -1;
			hoverY = -1;
		}
		
		int x = Math.abs(myPlayer.getTargetX());
		int y = Math.abs(myPlayer.getTargetY());
		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		if(b && myPlayer.movePrepared() && myCells.get(locX).get(locY).isAvailable()){
			myPlayer.move();
		}
	}

	@Override
	public void step() {
		if(!wasInput){
			hoverX = -1;
			hoverY = -1;
			myPlayer.setTargetX(Integer.MIN_VALUE);
			myPlayer.setTargetY(Integer.MIN_VALUE);
		}
		
		if(myPlayer.getCommand()==null)
			return;
		
		
		if(myPlayer.getCommand().equals("Up")){
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()-1).isPassable()){
				myPlayer.setY(myPlayer.getY() - 1);
			}
		}
		if(myPlayer.getCommand().equals("Down")){
			if(myCells.get(myPlayer.getX()).get(myPlayer.getY()+1).isPassable()){
				myPlayer.setY(myPlayer.getY() + 1);
			}
		}
		if(myPlayer.getCommand().equals("Left")){
			if(myCells.get(myPlayer.getX()-1).get(myPlayer.getY()).isPassable()){
				myPlayer.setX(myPlayer.getX() - 1);
			}
		}
		if(myPlayer.getCommand().equals("Right")){
			if(myCells.get(myPlayer.getX()+1).get(myPlayer.getY()).isPassable()){
				myPlayer.setX(myPlayer.getX() + 1);
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		int xcounter = 0;
		int ycounter = 0;
		for(int i=myPlayer.getX() - (MAP_WIDTH/2); i<=(MAP_WIDTH/2) + myPlayer.getX(); i++){
			for(int j=myPlayer.getY() - (MAP_HEIGHT/2); j<(MAP_HEIGHT/2) + 1 + myPlayer.getY(); j++){
				if(i < 0 || j < 0 || i >= myCells.size() || j >=myCells.get(0).size()){
					g.setColor(Color.BLACK);
					g.fillRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
					g.setColor(Color.GRAY);
					g.drawRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
				}
				else{
					myCells.get(i).get(j).draw(g,xcounter,ycounter);
				}
				ycounter++;
			}
			xcounter++;
			ycounter=0;
		}
		
		if(myPlayer.movePrepared()){
			MoveDrawer.drawMoves(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myCells, myPlayer, g);
		} else{
			clearAvailability(myCells);
		}
		
		
		g.setColor(Color.GREEN);
		g.fillRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
		
		if(hoverX!=-1 || hoverY!=-1){
			g.setColor(Color.lightGray);
			g.fillRect((hoverX)*BLOCK_SIZE, 1+((hoverY)*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
			g.setColor(Color.GRAY);
			g.drawRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
			
			if(myPlayer.movePrepared()){
				if(!(myPlayer.getX() + hoverX - MAP_WIDTH/2 < 0 || myPlayer.getY() + hoverY - MAP_HEIGHT/2 < 0 
						|| myPlayer.getX() + hoverX - MAP_WIDTH/2 >= myCells.size() || myPlayer.getY() + hoverY - MAP_HEIGHT/2 >= myCells.get(0).size())
						&& myCells.get(myPlayer.getX() + hoverX - MAP_WIDTH/2).get(myPlayer.getY() + hoverY - MAP_HEIGHT/2).isPassable()){
					myPlayer.setTargetX(hoverX - MAP_WIDTH/2);
					myPlayer.setTargetY(hoverY - MAP_HEIGHT/2);
				}
				else{
					myPlayer.setTargetX(Integer.MIN_VALUE);
					myPlayer.setTargetY(Integer.MIN_VALUE);
				}
			}
		}
		
		wasInput = false;
	}

	private void clearAvailability(List<List<MapCell>> myCells) {
		for(int i=0; i<myCells.size(); i++){
			for(int j=0; j<myCells.get(0).size(); j++){
				myCells.get(i).get(j).setAvailable(false);
			}
		}
	}

}
