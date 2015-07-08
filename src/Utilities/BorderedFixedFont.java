package Utilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BorderedFixedFont extends FixedFont{

	private List<BufferedImage> myBorderImages;
	public BorderedFixedFont(BufferedImage font, int width, BufferedImage border) {
		super(font, width);
		myBorderImages = new ArrayList<BufferedImage>();
		myHeight = border.getHeight();
		for(int i=0; i<3; i++){
			if(i*(width+1)<border.getWidth()){
			BufferedImage bb = border.getSubimage((width+1)*i, 0, width+1, border.getHeight());
			myBorderImages.add(bb);
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
		g = sc.createGraphics();
		g.drawImage(bb,  0,  0, (int)(bb.getWidth()*scale), (int)(bb.getHeight()*scale), null);
		g.dispose();
		BufferedImage result = prepareBackground(s, scale);
		g = result.createGraphics();
		g.drawImage(sc, (int)(3*scale), (int)(3*scale), null);
		return result;
	}
	private BufferedImage prepareBackground(String ss, double scale) {
		String s = "";
		s += "A";
		for(int i=0; i<ss.length()-1; i++){
			s+="B";
		}
		s += "C";
		BufferedImage bb = new BufferedImage((myWidth+1)*s.length()-1, myHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bb.getGraphics();
		for(int i=0; i<s.length(); i++){
			g.drawImage(myBorderImages.get((int)s.charAt(i)-65), (myWidth+1)*i, 0, null);
		}
		BufferedImage sc = new BufferedImage((int)(bb.getWidth()*scale), (int)(bb.getHeight()*scale),BufferedImage.TYPE_INT_ARGB);
		g = sc.createGraphics();
		g.drawImage(bb,  0,  0, (int)(bb.getWidth()*scale), (int)(bb.getHeight()*scale), null);
		g.dispose();
		return sc;
	}
}
