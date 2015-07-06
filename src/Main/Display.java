package Main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Display {
	public static int width = 1200;
	public static int height = (width / 16) * 9;
	
	private JFrame frame;
	private Canvas canvas;
	private BufferStrategy strategy;
	private Graphics g;
	public Display(InputListener l){
		Dimension size = new Dimension((int)(width), (int)(height));
		canvas = new Canvas();
		canvas.setPreferredSize(size);
		canvas.addMouseListener(l);
		canvas.addMouseMotionListener(l);
		canvas.addKeyListener(l);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("BRAVERY");
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
		frame.setFocusable(true);
//		GraphicsConfiguration config =
//	    		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
//		image = config.createCompatibleImage(width, height, Transparency.OPAQUE);
	}
	public void render(World w) {
    	if(strategy==null){
    		strategy = canvas.getBufferStrategy();
    		if(strategy==null){
        		canvas.createBufferStrategy(2);
        		strategy = canvas.getBufferStrategy();
    		}
    	}
    	if(g==null){
    		g = strategy.getDrawGraphics();
    	}
    	w.draw(g);
		strategy.show();
	}
}
