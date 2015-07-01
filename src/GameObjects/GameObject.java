package GameObjects;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public abstract class GameObject {

	private List<GameObject> myComponents;
	protected Shape myBounds;
	private boolean on;
	protected boolean isHover;
	protected BufferedImage myImage; 
	
	public GameObject(){
		myComponents = new ArrayList<GameObject>();
	}
	
	public void turnOn(){
		on = true;
	}
	
	public void turnOff(){
		on = false;
	}
	
	public boolean isOn(){
		return on;
	}
	
	public List<GameObject> getComponents() {
		return myComponents;
	}

	public Shape getBounds() {
		return myBounds;
	}

	public abstract void useInput(int i, int j, boolean b);
	
	public abstract void step();
	
	public abstract void draw(Graphics g);

	public void stopHover() {
		isHover = false;
		for(GameObject g: myComponents){
			g.stopHover();
		}
	}

}
