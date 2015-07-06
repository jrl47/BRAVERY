package GameObjects;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public abstract class GameObject {

	protected List<GameObject> myActiveObjects;
	protected Shape myBounds;
	protected boolean isHover;
	protected BufferedImage myImage; 
	
	public GameObject(){
		myActiveObjects = new ArrayList<GameObject>();
	}
	
	public List<GameObject> getComponents() {
		return myActiveObjects;
	}

	public Shape getBounds() {
		return myBounds;
	}

	public abstract void useInput(int i, int j, boolean b);
	
	public abstract void step();
	
	public void draw(Graphics g) {
		for(GameObject go: myActiveObjects){
			if(go!=null)
				go.draw(g);
		}
	}

	public void stopHover() {
		isHover = false;
		for(GameObject g: myActiveObjects){
			g.stopHover();
		}
	}

}
