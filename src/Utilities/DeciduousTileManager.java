package Utilities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import GameObjects.Collectible;
import GameObjects.CollectibleSkill;
import GameObjects.Enemy;
import GameObjects.MapCell;
import GameObjects.Player;
import GameObjects.Stage;

public class DeciduousTileManager extends TileManager{

	public DeciduousTileManager(Stage m) throws IOException {
		super(ImageIO.read(DeciduousTileManager.class.getResource("/deciduousspritesheet.png")), m);
	}

	@Override
	public BufferedImage getImage(MapCell m) {
		BufferedImage bb = doBackground(m);
		return bb;
	}
	
	
	public BufferedImage doBackground(MapCell m){
//		if(m.isPassable()){
//			return generateGrass();
//		}
//		else{
//			return generateWater(m);
//		}
//		
		if(m.getID().equals(MapCell.GRASS))
			return generateGrass();
		if(m.getID().equals(MapCell.DIRT))
			return generateDirt(m);
		if(m.getID().equals(MapCell.SMALL_ROCKS))
			return generateSmallRocks(m);
		if(m.getID().equals(MapCell.LARGE_ROCKS))
			return generateLargeRocks(m);
		if(m.getID().equals(MapCell.WATER))
			return generateWater(m);
		if(m.getID().equals(MapCell.FLOWERS))
			return generateFlowers(m);
		if(m.getID().equals(MapCell.BRICKS))
			return generateBricks(m);
		if(m.getID().equals(MapCell.SHOALS))
			return generateShoals();
		if(m.getID().equals(MapCell.FOREST))
			return generateForest(m);
		return null;
	}

	private BufferedImage generateFlowers(MapCell m) {
		BufferedImage bb = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		g.drawImage(myImage.getSubimage(0*32, 1*32, 32, 32), 0, 0, null);
		fuzzBorderWithGrass(m, g);
		g.dispose();
		return bb;
	}
	
	private BufferedImage generateForest(MapCell m) {
		BufferedImage bb = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		g.drawImage(myImage.getSubimage(3*32, 1*32, 32, 32), 0, 0, null);
		fuzzBorderWithForest(m, g);
		g.dispose();
		return bb;
	}
	
	private BufferedImage generateBricks(MapCell m) {
		BufferedImage bb = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		g.drawImage(myImage.getSubimage(1*32, 1*32, 32, 32), 0, 0, null);
		fuzzBorderWithGrass(m, g);
		g.dispose();
		return bb;
	}
	
	private BufferedImage generateWater(MapCell m) {
		BufferedImage bb = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		g.drawImage(myImage.getSubimage(9*32, 0, 32, 32), 0, 0, null);
		fuzzBorderWithGrass(m, g);
		g.dispose();
		return bb;
	}

	public BufferedImage getHoverTransparency(){
		return myImage.getSubimage(8*32, 1*32, 32, 32);
	}
	public BufferedImage getHighlightTransparency(){
		return myImage.getSubimage(7*32, 0*32, 32, 32);
	}

	private BufferedImage generateLargeRocks(MapCell m) {
		return myImage.getSubimage(2*32, 0, 32, 32);
	}

	private BufferedImage generateSmallRocks(MapCell m) {
		return myImage.getSubimage(1*32, 0, 32, 32);
	}

	private BufferedImage generateDirt(MapCell m) {
		BufferedImage bb = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		g.drawImage(myImage.getSubimage(8*32, 0, 32, 32), 0, 0, null);
		fuzzBorderWithGrass(m, g);
		g.dispose();
		return bb;
	}

	private void fuzzBorderWithGrass(MapCell m, Graphics g) {
		if(m.getX()<0 || m.getY()<0)
			return;
		
		String up = "";
		String down = "";
		String left = "";
		String right = "";
		if(m.getY()-1 >= 0)
			up = myMap.getCell(m.getX(), m.getY()-1).getID();
		if(m.getY()+1 < myMap.getHeight())
			down = myMap.getCell(m.getX(), m.getY()+1).getID();
		if(m.getX()-1 >= 0)
			left = myMap.getCell(m.getX()-1, m.getY()).getID();
		if(m.getX()+1 < myMap.getWidth())
			right = myMap.getCell(m.getX()+1, m.getY()).getID();
		boolean u = up.equals(MapCell.GRASS)||up.equals(MapCell.SMALL_ROCKS)||up.equals(MapCell.LARGE_ROCKS);
		boolean d = down.equals(MapCell.GRASS)||down.equals(MapCell.SMALL_ROCKS)||down.equals(MapCell.LARGE_ROCKS);
		boolean l = left.equals(MapCell.GRASS)||left.equals(MapCell.SMALL_ROCKS)||left.equals(MapCell.LARGE_ROCKS);
		boolean r = right.equals(MapCell.GRASS)||right.equals(MapCell.SMALL_ROCKS)||right.equals(MapCell.LARGE_ROCKS);
		if(u){
			g.drawImage(myImage.getSubimage(3*32, 0, 32, 32), 0, 0, null);
		}
		if(d){
			g.drawImage(myImage.getSubimage(5*32, 0, 32, 32), 0, 0, null);
		}
		if(l){
			g.drawImage(myImage.getSubimage(4*32, 0, 32, 32), 0, 0, null);
		}
		if(r){
			g.drawImage(myImage.getSubimage(6*32, 0, 32, 32), 0, 0, null);
		}
	}
	
	private void fuzzBorderWithForest(MapCell m, Graphics g) {
		String up = "";
		String down = "";
		String left = "";
		String right = "";
		if(m.getY()-1 >= 0)
			up = myMap.getCell(m.getX(), m.getY()-1).getID();
		if(m.getY()+1 < myMap.getWidth())
			down = myMap.getCell(m.getX(), m.getY()+1).getID();
		if(m.getX()-1 >= 0)
			left = myMap.getCell(m.getX()-1, m.getY()).getID();
		if(m.getX()+1 < myMap.getWidth())
			right = myMap.getCell(m.getX()+1, m.getY()).getID();
		boolean u = up.equals(MapCell.GRASS)||up.equals(MapCell.SMALL_ROCKS)||up.equals(MapCell.LARGE_ROCKS);
		boolean d = down.equals(MapCell.GRASS)||down.equals(MapCell.SMALL_ROCKS)||down.equals(MapCell.LARGE_ROCKS);
		boolean l = left.equals(MapCell.GRASS)||left.equals(MapCell.SMALL_ROCKS)||left.equals(MapCell.LARGE_ROCKS);
		boolean r = right.equals(MapCell.GRASS)||right.equals(MapCell.SMALL_ROCKS)||right.equals(MapCell.LARGE_ROCKS);
		if(u){
			g.drawImage(myImage.getSubimage(4*32, 1*32, 32, 32), 0, 0, null);
		}
		if(d){
			g.drawImage(myImage.getSubimage(6*32, 1*32, 32, 32), 0, 0, null);
		}
		if(l){
			g.drawImage(myImage.getSubimage(5*32, 1*32, 32, 32), 0, 0, null);
		}
		if(r){
			g.drawImage(myImage.getSubimage(7*32, 1*32, 32, 32), 0, 0, null);
		}
		g.dispose();
	}

	private BufferedImage generateGrass() {
		return myImage.getSubimage(0*32, 0, 32, 32);
	}
	private BufferedImage generateShoals() {
		return myImage.getSubimage(2*32, 1*32, 32, 32);
	}

	public BufferedImage getImage(Player player) {
		return myImage.getSubimage(9*32, 1*32, 32, 32);
	}
	
	public BufferedImage getImage(Collectible c) {
		if(c instanceof CollectibleSkill){
			return myImage.getSubimage(2*32, 2*32, 32, 32);
		}
		return myImage.getSubimage(0*32, 2*32, 32, 32);
	}
	
	public BufferedImage getImage(Enemy enemy) {
		if(enemy.getIndex()==0)
			return myImage.getSubimage(1*32, 2*32, 32, 32);
		if(enemy.getIndex()==100)
			return myImage.getSubimage(3*32, 2*32, 32, 32);
		return null;
	}

}
