package GameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import Utilities.FixedFont;

public class Text extends GameObject {

	protected String myString;
	protected BufferedImage myFont;
	protected double mySize;
	protected int x;
	protected int y;
	protected FixedFont f;
	public Text(int xx, int yy, String s, double d, BufferedImage font) {
		x = xx;
		y = yy;
		myString = s;
		mySize = d;
		myFont = font;
		f = new FixedFont(myFont, 6);
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
