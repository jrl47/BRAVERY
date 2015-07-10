package Utilities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import GameObjects.MapCell;
import GameObjects.Player;
import GameObjects.Stage;

public class ValidAttackChecker {

	public static void drawHoverInfo(Graphics g, Player myPlayer, List<List<MapCell>> myCells, int hoverX, int hoverY,
			DeciduousTileManager manager) {
		int x = getCellFromHoverX(hoverX, myPlayer);
		int y = getCellFromHoverY(hoverY, myPlayer);
		if(myPlayer.getAction().getPower()!=0&&
				!(x < 0 || y < 0 || x >= myCells.size() || y >=myCells.get(0).size()) &&
				myCells.get(x).get(y).isValidMove()){
			Color c = new Color(1f, .5f, .3f, .6f);
			g.setColor(c);
			g.fillRect((hoverX)*Stage.BLOCK_SIZE, 1+((hoverY)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
			int lim = myPlayer.getAction().getRange();
			if(myPlayer.getAction().isRoundSplash()){
				for(int i=-lim; i<=lim; i++){
					for(int j=-lim; j<=lim; j++){
						if(Math.abs(i) + Math.abs(j) < lim)
							g.fillRect((hoverX + i)*Stage.BLOCK_SIZE, 1+((hoverY + j)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
					}
				}
			}
			else{
				int xDif = -(myPlayer.getX() - x);
				int yDif = -(myPlayer.getY() - y);
				if(Math.abs(xDif) > Math.abs(yDif)){
					for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
						g.fillRect((hoverX + i)*Stage.BLOCK_SIZE, 1+((hoverY)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
					}
				}
				else if(Math.abs(xDif) < Math.abs(yDif)){
					for(int i = Integer.signum(yDif); Math.abs(i) < lim; i+=Integer.signum(yDif)){
						g.fillRect((hoverX)*Stage.BLOCK_SIZE, 1+((hoverY + i)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
					}
				}
				else if(yDif==0 && xDif ==0){
					
				}
				else if(xDif==yDif){
					for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
						g.fillRect((hoverX + i)*Stage.BLOCK_SIZE, 1+((hoverY + i)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
					}
				}
				else{
					for(int i = Integer.signum(xDif); Math.abs(i) < lim; i+=Integer.signum(xDif)){
						g.fillRect((hoverX + i)*Stage.BLOCK_SIZE, 1+((hoverY - i)*Stage.BLOCK_SIZE), Stage.BLOCK_SIZE, Stage.BLOCK_SIZE);
					}
				}
			}
		}
		else{
			g.drawImage(manager.getHoverTransparency(), (hoverX)*Stage.BLOCK_SIZE, 1+((hoverY)*Stage.BLOCK_SIZE), null, null);
		} 
	}
	
	public static void doPlayerAttack(Player myPlayer, List<List<MapCell>> myCells) {
		int locX = myPlayer.getX()+myPlayer.getTargetX();
		int locY = myPlayer.getY()+myPlayer.getTargetY();
		int lim = myPlayer.getAction().getRange();
		if(myPlayer.getAction().isRoundSplash()){
			for(int i=-lim; i<=lim; i++){
				for(int j=-lim; j<=lim; j++){
					if(!(locX+i < 0 || locY+j < 0 || locX+i >= myCells.size() || locY+j >=myCells.get(0).size())){
						if(Math.abs(i) + Math.abs(j) < lim && myCells.get(locX+i).get(locY+j).getEnemy()!=null)
							myPlayer.attack(myCells.get(locX+i).get(locY+j).getEnemy());
					}
				}
			}
		}
		else{
			int xDif = -(myPlayer.getX() - locX);
			int yDif = -(myPlayer.getY() - locY);
			if(Math.abs(xDif) > Math.abs(yDif)){
				for(int i = 0; Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+i, locY, myCells))
						myPlayer.attack(myCells.get(locX+i).get(locY).getEnemy());
				}
			}
			else if(Math.abs(xDif) < Math.abs(yDif)){
				for(int i = 0; Math.abs(i) < lim; i+=Integer.signum(yDif)){
					if(checkCellForEnemy(locX, locY+i, myCells))
						myPlayer.attack(myCells.get(locX).get(locY+i).getEnemy());
				}
			}
			else if(yDif==0 && xDif ==0){
				
			}
			else if(xDif==yDif){
				for(int i = 0; Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+i, locY+i, myCells))
						myPlayer.attack(myCells.get(locX+i).get(locY+i).getEnemy());
				}
			}
			else{
				for(int i = 0; Math.abs(i) < lim; i+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+i, locY-i, myCells))
						myPlayer.attack(myCells.get(locX+i).get(locY-i).getEnemy());
				}
			}
		}
	}
	
	public static boolean detectAttackTargets(int locX, int locY, Player myPlayer, List<List<MapCell>> myCells) {
		boolean enemyInRange = false;
		int lim = myPlayer.getAction().getRange();
		if(myPlayer.getAction().isRoundSplash()){
			for(int x=-lim; x<=lim; x++){
				for(int y=-lim; y<=lim; y++){
					if(!(locX+x < 0 || locY+y < 0 || locX+x >= myCells.size() || locY+y >=myCells.get(0).size())){
						if(Math.abs(x) + Math.abs(y) < lim && myCells.get(locX+x).get(locY+y).getEnemy()!=null)
							enemyInRange = true;
					}
				}
			}
		}
		else{
			int xDif = -(myPlayer.getX() - locX);
			int yDif = -(myPlayer.getY() - locY);
			if(Math.abs(xDif) > Math.abs(yDif)){
				for(int x = 0; Math.abs(x) < lim; x+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+x, locY, myCells))
						enemyInRange = true;
				}
			}
			else if(Math.abs(xDif) < Math.abs(yDif)){
				for(int x = 0; Math.abs(x) < lim; x+=Integer.signum(yDif)){
					if(checkCellForEnemy(locX, locY+x, myCells))
						enemyInRange = true;
				}
			}
			else if(yDif==0 && xDif ==0){
				
			}
			else if(xDif==yDif){
				for(int x = 0; Math.abs(x) < lim; x+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+x, locY+x, myCells))
						enemyInRange = true;
				}
			}
			else{
				for(int x = 0; Math.abs(x) < lim; x+=Integer.signum(xDif)){
					if(checkCellForEnemy(locX+x, locY-x, myCells))
						enemyInRange = true;
				}
			}
		}
		return enemyInRange;
	}

	private static boolean checkCellForEnemy(int x, int y,
			List<List<MapCell>> myCells) {
		if((x < 0 || y < 0 || x >= myCells.size() || y >=myCells.get(0).size())){
			return false;
		}
		return myCells.get(x).get(y).getEnemy()!=null;
	}
	
	private static int getCellFromHoverX(int hoverx, Player myPlayer){
		int shift = hoverx-(Stage.MAP_WIDTH/2);
		return myPlayer.getX() + shift;
		
	}
	private static int getCellFromHoverY(int hovery, Player myPlayer){
		int shift = hovery-(Stage.MAP_HEIGHT/2);
		return myPlayer.getY() + shift;
	}
	
}
