package Main;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Set;

import GameObjects.GameObject;
import GameObjects.Player;

/**
 * 
 * @author Jacob
 * 
 * Keeps track of all currently pressed keys.
 */
public class InputListener implements MouseListener, MouseMotionListener, KeyListener{
	
	private KeyEvent mostRecentKeyEvent = null;
	private MouseEvent mostRecentEvent = null;
	private boolean clicked = false;
	private boolean moved = false;
	private Player myPlayer;
	public void step(World model) {
		myPlayer = model.getPlayer();
		List<GameObject> components = model.getComponents();
		
		myPlayer.useKeyPress(mostRecentKeyEvent);
		
		if(clicked){
			for(GameObject g: components){
				doClick(g);
			}
		}
		if(moved){
			for(GameObject g: components){
				doHover(g);
			}
		}
		
		for(GameObject g: components){
			doIdle(g);
		}
		
//		mostRecentEvent = null;
		mostRecentKeyEvent = null;
		clicked = false;
		moved = false;
	}

	private void doClick(GameObject g) {
		Shape s = g.getBounds();
		if(s==null) return;
		if(s.contains(mostRecentEvent.getX(), mostRecentEvent.getY())){
			for(GameObject gg: g.getComponents()){
				doClick(gg);
			}
			g.useInput((int)(mostRecentEvent.getX())-(int)(s.getBounds().x),
					(int)(mostRecentEvent.getY())-(int)(s.getBounds().y), true);
		}
	}

	private void doHover(GameObject g) {
		Shape s = g.getBounds();
		if(s!=null&&s.contains(mostRecentEvent.getX(), mostRecentEvent.getY())){
			for(GameObject gg: g.getComponents()){
				doHover(gg);
			}
			g.useInput((int)(mostRecentEvent.getX())-(int)(s.getBounds().x),
				(int)(mostRecentEvent.getY())-(int)(s.getBounds().y), false);
		} else{
			g.stopHover();
		}
	}
	
	private void doIdle(GameObject g) {
		Shape s = g.getBounds();
		if(s!=null&&s.contains(mostRecentEvent.getX(), mostRecentEvent.getY())){
			for(GameObject gg: g.getComponents()){
				doIdle(gg);
			}
			g.useInput((int)(mostRecentEvent.getX())-(int)(s.getBounds().x),
				(int)(mostRecentEvent.getY())-(int)(s.getBounds().y), false);
		} else{
			g.stopHover();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mostRecentEvent = arg0;
		clicked = true;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mostRecentEvent = arg0;
		moved = true;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mostRecentEvent = arg0;
		moved = true;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		mostRecentKeyEvent = arg0;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
