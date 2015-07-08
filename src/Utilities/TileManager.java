package Utilities;

import java.awt.image.BufferedImage;

import GameObjects.MapCell;
import GameObjects.Stage;

public abstract class TileManager {

	protected BufferedImage myImage;
	protected Stage myMap;
	public TileManager(BufferedImage b, Stage m){
		myImage = b;
		myMap = m;
	}
	public abstract BufferedImage getImage(MapCell m);
}
