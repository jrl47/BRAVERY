package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import UtilityObjects.Action;
import GameObjects.MapCell;
import GameObjects.Player;
import GameObjects.Stage;

public class AttackDrawer {

	public static void drawAttacks(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE,
			Stage myStage, Graphics g) {
		List<List<MapCell>> myMap = myStage.getCells();
		Player myPlayer = myStage.getPlayer();
		for(int i=0; i<myMap.size(); i++){
			for(int j=0; j<myMap.size(); j++){
				myMap.get(i).get(j).setValidMove(false);
			}
		}
		if(myPlayer.getCommand()!=null){
			if(myPlayer.getCommand().equals("boulderfall")){
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 0, 1, new Color(.6f, .4f, .3f, .6f), 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 0, new Color(.6f, .4f, .3f, .6f), 1, 1, true);
			}
			if(myPlayer.getCommand().equals("skytoss")){
				Color c = Color.lightGray;
				c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 0, 0, c, 0, 0, false);
			}
			if(myPlayer.getCommand().equals("cascade")){
				Color c = Color.BLUE;
				c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 0, 1, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 0, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 1, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, -1, c, 1, 1, true);
			}
			if(myPlayer.getCommand().equals("detonate")){
				Color c = Color.RED;
				c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 0, 1, c, 1, 3, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 0, c, 1, 3, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 1, c, 1, 2, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, -1, c, 1, 2, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, 2, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 1, -2, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 2, 1, c, 1, 1, true);
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, 2, -1, c, 1, 1, true);
			}
			
		}
	}
	

	private static void drawLine(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE, Stage myStage,
			Graphics g, int xScale, int yScale, Color c, int start, int end, boolean first){
		List<List<MapCell>> myCells = myStage.getCells();
		Player myPlayer = myStage.getPlayer();
		
		for(int i=start; i<=end; i++){
			if(myPlayer.getX() + xScale*i > 0 &&
				myPlayer.getX() + xScale*i <= myCells.size() &&
				myPlayer.getY() - yScale*i > 0 &&
				myPlayer.getY() - yScale*i <= myCells.get(0).size() &&
				(myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).isPassable() ||
				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).getEnemy()!=null ||
				(xScale==0 && yScale==0))){

				if(myPlayer.getCommand().equals("boulderfall")){
					if(myPlayer.getInventory().getEarth()<2000)
						break;
					Action a = new Action(2000, "earth", 5, 1, true);
					myPlayer.setAction(a);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setValidMove(true);
				}
				if(myPlayer.getCommand().equals("skytoss")){
					if(myPlayer.getInventory().getAir()<2000)
						break;
					Action a = new Action(2000, "air", 5, 4, true);
					myPlayer.setAction(a);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setValidMove(true);
				}
				if(myPlayer.getCommand().equals("cascade")){
					if(myPlayer.getInventory().getWater()<2000)
						break;
					Action a = new Action(2000, "water", 5, 6, false);
					myPlayer.setAction(a);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setValidMove(true);
				}
				if(myPlayer.getCommand().equals("detonate")){
					if(myPlayer.getInventory().getFire()<2000)
						break;
					Action a = new Action(2000, "fire", 5, 2, true);
					myPlayer.setAction(a);
					myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setValidMove(true);
				}
					
				g.setColor(c);
				g.fillRect((myStage.getRelativeX(myPlayer.getX()) + xScale*i)*BLOCK_SIZE,
						(myStage.getRelativeY(myPlayer.getY()) - yScale*i)*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAvailable(true);
			}
			
			else{
				break;
			}
		}
		
		if(first)
			drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, -xScale, -yScale, c, start, end, false);
	}

}
