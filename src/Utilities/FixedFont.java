package Utilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FixedFont {

	protected List<BufferedImage> myImages;
	protected int myWidth;
	protected int myHeight;
	public FixedFont(BufferedImage font, int width){
		myWidth = width;
		myHeight = font.getHeight();
		myImages = new ArrayList<BufferedImage>();
		for(int i=0; i<60; i++){
			if(i*(width+1)<font.getWidth()){
			BufferedImage bb = font.getSubimage((width+1)*i, 0, width+1, font.getHeight());
			myImages.add(bb);
			}
		}
	}
	public BufferedImage getStringImage(String s, double scale){
		BufferedImage bb = new BufferedImage((myWidth+1)*s.length()-1, myHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		for(int i=0; i<s.length(); i++){
				g.drawImage(myImages.get((int)s.charAt(i)-32), (myWidth+1)*i, 0, null);
		}
		BufferedImage sc = new BufferedImage((int)(bb.getWidth()*scale), (int)(bb.getHeight()*scale),BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = sc.createGraphics();
		gg.drawImage(bb,  0,  0, (int)(bb.getWidth()*scale), (int)(bb.getHeight()*scale), null);
		gg.dispose();
		return sc;
	}
}
