package GameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Background extends GameObject{
	
	public Background(BufferedImage b) {
		super();
		myImage = b;
	}
	@Override
	public void draw(Graphics g) {
		g.drawImage(myImage, 0, 0, null);
	}

	@Override
	public void useInput(int i, int j, boolean b) {

	}

	@Override
	public void step() {

	}
}
