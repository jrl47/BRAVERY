package UtilityObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import GameObjects.MapCell;
import GameObjects.Player;

public class AttackDrawer {

	public static void drawAttacks(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE,
			List<List<MapCell>> myMap, Player myPlayer, Graphics g) {
		if(myPlayer.getCommand()!=null){
			if(myPlayer.getCommand().equals("boulderfall")){
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 0, 1, new Color(.6f, .4f, .3f), 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myMap, myPlayer, g, 1, 0, new Color(.6f, .4f, .3f), 1, 1, true);
			}
		}
	}
	

	private static void drawLine(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE,
			List<List<MapCell>> myCells, Player myPlayer, Graphics g, int xScale, int yScale, Color c, int start, int end, boolean first) {
		int max = Math.max(Math.abs(xScale), Math.abs(yScale));
		
		for(int i=start; i<=end; i++){
			if(myPlayer.getX() + xScale*i > 0 &&
				myPlayer.getX() + xScale*i <= myCells.size() &&
				myPlayer.getY() - yScale*i > 0 &&
				myPlayer.getY() - yScale*i <= myCells.get(0).size() &&
				(myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).isPassable() ||
				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).getEnemy()!=null)){

				if(myPlayer.getCommand().equals("boulderfall")){
					Action a = new Action(2000, "earth", 5);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAction(a);
				}
					
				g.setColor(c);
				g.fillRect((MAP_WIDTH/2 + xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect((MAP_WIDTH/2 + xScale*i)*BLOCK_SIZE, ((MAP_HEIGHT/2) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE-1, BLOCK_SIZE-1);
				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAvailable(true);
			}
			
			else{
				break;
			}
		}
		
		if(first)
			drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myCells, myPlayer, g, -xScale, -yScale, c, start, end, false);
	}

}
