package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.World;

public class Stage extends GameObject{

	private static final int BLOCK_SIZE = 32;
	private BufferedImage myMap;
	public Stage() {
		super();
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
		for(int i=0; i<27; i++){
			for(int j=0; j<21; j++){
				if(myMap.getRGB(i,j)==-1){
					g.setColor(Color.WHITE);
				}
				else if(myMap.getRGB(i,j)==-16777216){
					g.setColor(Color.BLACK);
				}
				g.fillRect(i*BLOCK_SIZE, 1+(j*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
				g.setColor(Color.GRAY);
				g.drawRect(i*BLOCK_SIZE, 1+(j*BLOCK_SIZE), BLOCK_SIZE-1, BLOCK_SIZE);
			}
		}
	}

}
