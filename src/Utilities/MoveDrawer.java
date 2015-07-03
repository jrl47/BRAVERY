package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameObjects.Player;

public class MoveDrawer {

	public static void drawMoves(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE, BufferedImage myMap, Player myPlayer, Graphics g) {		
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 0, 1, new Color(.6f, .4f, .3f));
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 0, new Color(.6f, .4f, .3f));
		
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 1, Color.lightGray);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 1, Color.lightGray);
		
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 2, Color.BLUE);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 2, Color.BLUE);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -2, 1, Color.BLUE);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 2, 1, Color.BLUE);
		
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 3, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 3, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -3, 1, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 3, 1, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -2, 3, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 2, 3, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -3, 2, Color.RED);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 3, 2, Color.RED);	
	}

	private static void drawLine(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE,
			BufferedImage myMap, Player myPlayer, Graphics g, int xScale, int yScale, Color c) {
		Color original = new Color(c.getRed(), c.getGreen(), c.getBlue());
		int max = Math.max(Math.abs(xScale), Math.abs(yScale));
		
		for(int i=1; i<=MAP_WIDTH/(2*max); i++){
			c = new Color(Math.min((int)(c.getRed()+10*max), 255), Math.min((int)(c.getGreen()+10*max), 255), Math.min((int)(c.getBlue()+10*max), 255));
			if(myPlayer.getX() + xScale*i > 0 &&
					myPlayer.getX() + xScale*i <= myMap.getWidth() &&
					myPlayer.getY() - yScale*i > 0 &&
					myPlayer.getY() - yScale*i <= myMap.getHeight() &&
					myMap.getRGB(myPlayer.getX() + xScale*i, myPlayer.getY() - yScale*i)!=-16777216){
				g.setColor(c);
				g.fillRect((MAP_WIDTH/2 + xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect((MAP_WIDTH/2 + xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
			else{
				break;
			}
		}
		c = original;
		for(int i=1; i<=MAP_HEIGHT/(2*max); i++){
			c = new Color(Math.min((int)(c.getRed()+10*max), 255), Math.min((int)(c.getGreen()+10*max), 255), Math.min((int)(c.getBlue()+10*max), 255));
			if(myPlayer.getX() - xScale*i> 0 &&
					myPlayer.getX() - xScale*i<= myMap.getWidth() &&
					myPlayer.getY() + yScale*i > 0 &&
					myPlayer.getY() + yScale*i <= myMap.getHeight() &&
					myMap.getRGB(myPlayer.getX() - xScale*i, myPlayer.getY() + yScale*i)!=-16777216){
				g.setColor(c);
				g.fillRect((MAP_WIDTH/2 - xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) + yScale*i)*BLOCK_SIZE+1, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect((MAP_WIDTH/2 - xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) + yScale*i)*BLOCK_SIZE+1, BLOCK_SIZE-1, BLOCK_SIZE-1);
			}
			else{
				break;
			}
		}
	}
	
}
