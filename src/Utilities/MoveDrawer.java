package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import UtilityObjects.Action;
import GameObjects.MapCell;
import GameObjects.Player;

public class MoveDrawer {

	public static void drawMoves(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE, List<List<MapCell>> myMap, Player myPlayer, Graphics g) {		
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 0, 1, new Color(.6f, .4f, .3f, .6f), true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 0, new Color(.6f, .4f, .3f, .6f), true);
		
		Color c = Color.lightGray;
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 1, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 1, c, true);
		
		c = Color.BLUE;
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 2, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 2, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -2, 1, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 2, 1, c, true);
		
		c = Color.RED;
		c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -1, 3, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 3, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -3, 1, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 3, 1, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -2, 3, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 2, 3, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, -3, 2, c, true);
		drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 3, 2, c, true);	
	}

	private static void drawLine(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE,
			List<List<MapCell>> myCells, Player myPlayer, Graphics g, int xScale, int yScale, Color c, boolean first) {
		Color original = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		int max = Math.max(Math.abs(xScale), Math.abs(yScale));
		
		for(int i=1; i<=MAP_WIDTH/(2*max); i++){
			c = new Color(Math.min((int)(c.getRed()+10*max), 255), Math.min((int)(c.getGreen()+10*max), 255), Math.min((int)(c.getBlue()+10*max), 255), c.getAlpha());
			if(myPlayer.getX() + xScale*i > 0 &&
					myPlayer.getX() + xScale*i <= myCells.size() &&
					myPlayer.getY() - yScale*i > 0 &&
					myPlayer.getY() - yScale*i <= myCells.get(0).size() &&
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).isPassable()){
				if((xScale==0 || yScale==0) && i==1){
					Action a = new Action(0, "earth", 0);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
				}
				else if((xScale==0 || yScale==0)){
					if(myPlayer.getInventory().getEarth()<Math.pow(i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6))
						break;
					Action a = new Action((int) Math.pow(i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6), "earth", 0);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
				}
				else{
					if(Math.abs(xScale)==Math.abs(yScale)){
						if(myPlayer.getInventory().getAir()<Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6))
							break;
						Action a = new Action((int) Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6), "air", 0);
						myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
					}
					else if(Math.abs(xScale)==2*Math.abs(yScale) || 2*Math.abs(xScale)==Math.abs(yScale)){
						if(myPlayer.getInventory().getWater()<Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6))
							break;
						Action a = new Action((int) Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6), "water", 0);
						myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
					}
					else{
						if(myPlayer.getInventory().getFire()<Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6))
							break;
						Action a = new Action((int) Math.pow(1+i*Math.max(Math.abs(xScale), Math.abs(yScale)), 6), "fire", 0);
						myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
					}
				}
				
				g.setColor(c);
				g.fillRect((MAP_WIDTH/2 + xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAvailable(true);
			}
			else{
				break;
			}
		}
		
		if(first)
			drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myCells, myPlayer, g, -xScale, -yScale, original, false);
	}
	
}
