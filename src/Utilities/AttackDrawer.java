package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import UtilitiesData.AttackRangeSpecs;
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
			for(int j=0; j<myMap.get(0).size(); j++){
				myMap.get(i).get(j).setAvailable(false);
			}
		}
		if(myPlayer.getAction()!=null){
			if(myPlayer.getAction().getName().equals("wait")) return;
			
			Color c = null;
			if(myPlayer.getAction().getType().equals("earth")){
				c = new Color(.6f, .4f, .3f, .6f);
			}
			if(myPlayer.getAction().getType().equals("air")){
				c = Color.lightGray;
			}
			if(myPlayer.getAction().getType().equals("water")){
				c = Color.BLUE;
			}
			if(myPlayer.getAction().getType().equals("fire")){
				c = Color.RED;
			}
			c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 153);
			List<AttackRangeSpecs> mySpecs = myPlayer.getAction().getHitRange();
			for(AttackRangeSpecs s : mySpecs){
				drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, s.getScale1(), s.getScale2(), c, s.getStart(), s.getEnd(), true);
				if(s.getScale2()!=s.getScale1())
					drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, s.getScale2(), s.getScale1(), c, s.getStart(), s.getEnd(), true);
				if(s.getScale1()!=0 && s.getScale2()!=0 && s.getScale2()!=-s.getScale1()){
					drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, s.getScale1(), -s.getScale2(), c, s.getStart(), s.getEnd(), true);
					if(s.getScale2()!=s.getScale1())
						drawLine(MAP_WIDTH, MAP_HEIGHT, BLOCK_SIZE, myStage, g, s.getScale2(), -s.getScale1(), c, s.getStart(), s.getEnd(), true);
				}
			}
		}
	}
	

	private static void drawLine(int MAP_WIDTH, int MAP_HEIGHT, int BLOCK_SIZE, Stage myStage,
			Graphics g, int xScale, int yScale, Color c, int start, int end, boolean first){
		List<List<MapCell>> myCells = myStage.getCells();
		Player myPlayer = myStage.getPlayer();
		
		for(int i=start; i<=end; i++){
			if(myPlayer.getX() + xScale*i >= 0 &&
				myPlayer.getX() + xScale*i < myCells.size() &&
				myPlayer.getY() - yScale*i >= 0 &&
				myPlayer.getY() - yScale*i < myCells.get(0).size() &&
				(myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).isLand())){

				myCells.get(myPlayer.getX() + xScale*i).get(myPlayer.getY() - yScale*i).setAvailable(true);
					
				g.setColor(c);
				g.fillRect((myStage.getRelativeX(myPlayer.getX()) + xScale*i)*BLOCK_SIZE,
						(1+(myStage.getRelativeY(myPlayer.getY()) - yScale*i)*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
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
