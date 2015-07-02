package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Utilities.FixedFont;

public class Button extends GameObject{

	protected String myString;
	protected int mySize;
	protected BufferedImage myFont;
	protected BufferedImage myHoverFont;
	protected BufferedImage mySelectedFont;
	protected boolean selected;
	protected FixedFont fSelect;
	protected FixedFont fStd;
	protected FixedFont fHover;
	protected int x;
	protected int y;
	public Button(int xx, int yy, String s, int size, BufferedImage font, BufferedImage hoverFont) {
		x = xx;
		y = yy;
		myString = s;
		mySize = size;
		myFont = font;
		fStd = new FixedFont(myFont, 6);
		fHover = new FixedFont(hoverFont, 6);
	}

	public void setSelectedFont(BufferedImage b){
		mySelectedFont = b;
		fSelect = new FixedFont(mySelectedFont, 6);
	}
	public void select(){
		selected = true;
	}
	public void deselect(){
		selected = false;
	}

	@Override
	public void useInput(int i, int j, boolean b) {
		if(!b){
			isHover = true;
			return;
		}
	}

	@Override
	public void step() {
		
	}

	@Override
	public void draw(Graphics g) {
		if(myBounds==null){
			BufferedImage image = fStd.getStringImage(myString, mySize);
			myBounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
		}
		
		if(selected&&mySelectedFont!=null){
			g.drawImage(fSelect.getStringImage(myString, mySize), x, y, null);
			return;
		}
		
//		System.out.println(isHover);
		if(!isHover){
			g.drawImage(fStd.getStringImage(myString, mySize), x, y, null);
		} else{
//			System.out.println("hapen");
			g.drawImage(fHover.getStringImage(myString, mySize), x, y, null);
		}
	}

}
