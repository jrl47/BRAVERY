package Utilities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import GameObjects.MapCell;
import GameObjects.Stage;

public class RoomNetwork {
	public static final int WORLD_WIDTH = 3;
	public static final int WORLD_HEIGHT = 4;
	private List<List<BufferedImage>> myRooms;
	private List<List<Integer>> myRoomWidths;
	private List<List<Integer>> myRoomHeights;
	private List<List<Integer>> myRoomXs;
	private List<List<Integer>> myRoomYs;
	private Stage myStage;
	
	public RoomNetwork(Stage stage){
		myStage = stage;
		myRooms = new ArrayList<List<BufferedImage>>();
		myRoomWidths = new ArrayList<List<Integer>>();
		myRoomHeights = new ArrayList<List<Integer>>();
		myRoomXs = new ArrayList<List<Integer>>();
		myRoomYs = new ArrayList<List<Integer>>();
		
		for(int i=0; i<WORLD_WIDTH; i++){
			myRooms.add(new ArrayList<BufferedImage>());
			myRoomWidths.add(new ArrayList<Integer>());
			myRoomHeights.add(new ArrayList<Integer>());
			myRoomXs.add(new ArrayList<Integer>());
			myRoomYs.add(new ArrayList<Integer>());
			for(int j=0; j<WORLD_HEIGHT; j++){
				myRooms.get(i).add(null);
				myRoomWidths.get(i).add(-1);
				myRoomHeights.get(i).add(-1);
				myRoomXs.get(i).add(-1);
				myRoomYs.get(i).add(-1);
			}
		}
		for(int i=0; i<WORLD_WIDTH; i++){
			for(int j=0; j<WORLD_HEIGHT; j++){
				BufferedImage room = null;
				if(RoomNetwork.class.getResource("/mapData-" + i + "-" + j + ".png")!=null){
					try {
						room = ImageIO.read(RoomNetwork.class.getResource("/mapData-" + i + "-" + j + ".png"));
					} catch (IOException e) { }
				}
				if(room!=null){
					for(int x=0; x<room.getWidth(); x+=32){
						for(int y=0; y<room.getHeight(); y+=32){
							myRooms.get(i + (x/32)).set(j + (y/32), room);
							myRoomWidths.get(i + (x/32)).set(j + (y/32), room.getWidth()/32);
							myRoomHeights.get(i + (x/32)).set(j + (y/32), room.getHeight()/32);
							myRoomXs.get(i + (x/32)).set(j + (y/32), i);
							myRoomYs.get(i + (x/32)).set(j + (y/32), j);
						}
					}
				}
				
			}
		}
	}
	public void buildRoom(List<List<MapCell>> myCells, int roomX, int roomY) {
		myCells.clear();
		
		BufferedImage myMap = myRooms.get(roomX).get(roomY);
		
		for(int i=0; i<myMap.getWidth(); i++){
			myCells.add(new ArrayList<MapCell>());
		}
		
		for(int i=0; i<myMap.getWidth(); i++){
			for(int j=0; j<myMap.getHeight(); j++){
				myCells.get(i).add(new MapCell(i, j, myStage));
				if(myMap.getRGB(i,j)==-1){
					myCells.get(i).get(j).setPassable(1);
				}
				else if(myMap.getRGB(i,j)==-8421505){
					myCells.get(i).get(j).setPassable(2);
				}
				else{
					myCells.get(i).get(j).setID(MapCell.WATER);
				}
			}
		}
	}
	
	public int getX(int x, int y){
		return myRoomXs.get(x).get(y);
	}
	public int getY(int x, int y){
		return myRoomYs.get(x).get(y);
	}
	public int getWidth(int roomX, int roomY) {
		return myRoomWidths.get(roomX).get(roomY);
	}
	public int getHeight(int roomX, int roomY) {
		return myRoomHeights.get(roomX).get(roomY);
	}
}
