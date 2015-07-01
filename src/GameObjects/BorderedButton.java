package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Utilities.BorderedFixedFont;

public class BorderedButton extends Button{

	
	protected BufferedImage myFontBacking;
	protected BufferedImage myHoverFontBacking;
	protected BufferedImage mySelectedFontBacking;
	public BorderedButton(int xx, int yy, String s, int size, BufferedImage font, BufferedImage hoverFont,
			BufferedImage backing, BufferedImage hoverBacking) {
		super(xx, yy, s, size, font, hoverFont);
		fHover = new BorderedFixedFont(hoverFont, 6, hoverBacking);
		fStd = new BorderedFixedFont(myFont, 6, backing);
	}

	public void setSelectedFont(BufferedImage b, BufferedImage backing){
		mySelectedFont = b;
		mySelectedFontBacking = backing;
		fSelect = new BorderedFixedFont(mySelectedFont, 6, backing);
	}
	public void select(){
		selected = true;
	}
	public void deselect(){
		selected = false;
	}

	@Override
	public void useInput(int i, int j, boolean b) {
		super.useInput(i,j,b);
	}

	@Override
	public void step() {
		
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
	}
}
