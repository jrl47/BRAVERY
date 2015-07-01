package GameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Utilities.BorderedFixedFont;

public class BorderedText extends Text{
	
	public BorderedText(int xx, int yy, String s, int size, BufferedImage font, BufferedImage backing) {
		super(xx, yy, s, size, font);
		x = xx;
		y = yy;
		myString = s;
		mySize = size;
		myFont = font;
		f = new BorderedFixedFont(myFont, 6, backing);
	}


	@Override
	public void useInput(int i, int j, boolean b) {

	}

	@Override
	public void step() {

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(f.getStringImage(myString, mySize), x, y, null);
	}
}
