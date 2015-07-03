package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameObjects.Player;

public class MoveDrawer {

	public static void drawMoves(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE, BufferedImage myMap, Player myPlayer, Graphics g) {
		for(int i=0; i<MAP_WIDTH; i++){
			if(myPlayer.getX() + i - MAP_WIDTH/2 > 0 &&
					myPlayer.getX() + i - MAP_WIDTH/2 <= myMap.getWidth() &&
					myMap.getRGB(myPlayer.getX() + i - MAP_WIDTH/2, myPlayer.getY())!=-16777216){
				g.setColor(new Color(.7f, .5f, .4f));
				g.fillRect(i*BLOCK_SIZE, ((MAP_HEIGHT/2))*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect(i*BLOCK_SIZE, ((MAP_HEIGHT/2))*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
		}
		for(int i=0; i<MAP_HEIGHT; i++){
			if(myPlayer.getY() + i - MAP_HEIGHT/2 > 0 &&
					myPlayer.getY() + i - MAP_HEIGHT/2 <= myMap.getHeight() &&
					myMap.getRGB(myPlayer.getX(), myPlayer.getY() + i - MAP_HEIGHT/2)!=-16777216){
				g.setColor(new Color(.7f, .5f, .4f));
				g.fillRect(((MAP_WIDTH/2))*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect(((MAP_WIDTH/2))*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
		}
		
		// don't make the map taller than it is wide! :P
		int shift = (MAP_WIDTH - MAP_HEIGHT)/2;
		for(int i=0; i<MAP_WIDTH; i++){
			if(myPlayer.getX() + i + shift - MAP_WIDTH/2 > 0 &&
					myPlayer.getX() + i + shift - MAP_WIDTH/2 <= myMap.getWidth() &&
					myPlayer.getY() + i - MAP_HEIGHT/2 > 0 &&
					myPlayer.getY() + i - MAP_HEIGHT/2 <= myMap.getHeight() &&
					myMap.getRGB(myPlayer.getX() + i + shift - MAP_WIDTH/2, myPlayer.getY() + i - MAP_HEIGHT/2)!=-16777216){
				g.setColor(Color.lightGray);
				g.fillRect((i+ shift)*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect((i + shift)*BLOCK_SIZE, i*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
		}
		for(int i=0; i<MAP_WIDTH; i++){
			if(myPlayer.getX() + i + shift - MAP_WIDTH/2 > 0 &&
					myPlayer.getX() + i + shift - MAP_WIDTH/2 <= myMap.getWidth() &&
					myPlayer.getY() + (MAP_HEIGHT - 1 - i) - MAP_HEIGHT/2 > 0 &&
					myPlayer.getY() + (MAP_HEIGHT - 1 - i) - MAP_HEIGHT/2 <= myMap.getHeight() &&
					myMap.getRGB(myPlayer.getX() + i + shift - MAP_WIDTH/2, myPlayer.getY() + (MAP_HEIGHT - 1 - i) - MAP_HEIGHT/2)!=-16777216){
				g.setColor(Color.lightGray);
				g.fillRect((i + shift)*BLOCK_SIZE, (MAP_HEIGHT - 1 - i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect((i + shift)*BLOCK_SIZE, (MAP_HEIGHT - 1 - i)*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
		}
	}
	
}
