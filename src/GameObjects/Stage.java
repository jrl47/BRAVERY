package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.World;

public class Stage extends GameObject{

	private static final int BLOCK_SIZE = 32;
	private static final int MAP_WIDTH = 29;
	private static final int MAP_HEIGHT = 21;
	private Player myPlayer;
	private BufferedImage myMap;
	public Stage(Player p) {
		super();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		int xcounter = 0;
		int ycounter = 0;
		for(int i=myPlayer.getX() - (MAP_WIDTH/2); i<(MAP_WIDTH/2) + myPlayer.getX(); i++){
			for(int j=myPlayer.getY() - (MAP_HEIGHT/2); j<(MAP_HEIGHT/2) + 1 + myPlayer.getY(); j++){
				if(i < 0 || j < 0){
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
		g.setColor(Color.GREEN);
		g.fillRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
		g.setColor(Color.GRAY);
		g.drawRect(((MAP_WIDTH/2))*BLOCK_SIZE, 1+(((MAP_HEIGHT/2))*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE-1);
	}

}
