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
	
	private static int getCellFromHoverX(int hoverx, Player myPlayer){
		int shift = hoverx-(Stage.MAP_WIDTH/2);
		return myPlayer.getX() + shift;
		
	}
	private static int getCellFromHoverY(int hovery, Player myPlayer){
		int shift = hovery-(Stage.MAP_HEIGHT/2);
		return myPlayer.getY() + shift;
	}
	
}
