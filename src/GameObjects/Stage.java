package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.World;
import Utilities.MoveDrawer;

public class Stage extends GameObject{

	private static final int BLOCK_SIZE = 32;
	private static final int MAP_WIDTH = 27;
	private static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	private BufferedImage myMap;
	
	private boolean wasInput;
	private int hoverX = -1;
	private int hoverY = -1;
	
	public Stage(Player p) {
		super();
		myBounds = new Rectangle(0, 0, MAP_WIDTH * 32, 675);
		myPlayer = p;
		try {
			myMap = ImageIO.read(World.class.getResource("/mapData.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if(b && myPlayer.movePrepared() && 
				(x==0 || y==0 || x==y || 2*x==y || x==2*y || 3*x==y || x==3*y || 3*x==2*y || 2*x==3*y) 
				&& !(locX < 0 || locY < 0 || locX >= myMap.getWidth() || locY >= myMap.getHeight())
				&& myMap.getRGB(locX, locY)!=-16777216){
			myPlayer.move(); // BUG! THIS DOES NOT ACCOUNT FOR GOING THROUGH WALLS WHERE HIGHLIGHTING DOES! FIX!
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
			if(myMap.getRGB(myPlayer.getX(), myPlayer.getY()-1)!=-16777216){
				myPlayer.setY(myPlayer.getY() - 1);
			}
		}
		if(myPlayer.getCommand().equals("Down")){
			if(myMap.getRGB(myPlayer.getX(), myPlayer.getY()+1)!=-16777216){
				myPlayer.setY(myPlayer.getY() + 1);
			}
		}
		if(myPlayer.getCommand().equals("Left")){
			if(myMap.getRGB(myPlayer.getX()-1, myPlayer.getY())!=-16777216){
				myPlayer.setX(myPlayer.getX() - 1);
			}
		}
		if(myPlayer.getCommand().equals("Right")){
			if(myMap.getRGB(myPlayer.getX()+1, myPlayer.getY())!=-16777216){
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
				if(i < 0 || j < 0 || i >= myMap.getWidth() || j >= myMap.getHeight()){
					g.setColor(Color.BLACK);
				}
				else{
					if(myMap.getRGB(i,j)==-1){
						g.setColor(Color.WHITE);
					}
					else if(myMap.getRGB(i,j)==-16777216){
						g.setColor(Color.BLACK);
					}
				}
				g.fillRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect(xcounter*BLOCK_SIZE, 1+(ycounter*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
				ycounter++;
			}
			xcounter++;
			ycounter=0;
		}
		
		if(myPlayer.movePrepared()){
			MoveDrawer.drawMoves(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g);
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
						|| myPlayer.getX() + hoverX - MAP_WIDTH/2 >= myMap.getWidth() || myPlayer.getY() + hoverY - MAP_HEIGHT/2 >= myMap.getHeight())
						&& myMap.getRGB(myPlayer.getX() + hoverX - MAP_WIDTH/2, myPlayer.getY() + hoverY - MAP_HEIGHT/2)!=-16777216){
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

}
