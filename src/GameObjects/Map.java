package GameObjects;

import java.awt.Color;
import java.awt.Graphics;

import Utilities.RoomNetwork;

public class Map extends GameObject{

	
	Stage myStage;
	RoomNetwork myNetwork;
	
	public Map(Stage stage){
		myStage = stage;
		myNetwork = myStage.getRooms();
	}
	
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i=0; i<RoomNetwork.WORLD_WIDTH; i++){
			for(int j=0; j<RoomNetwork.WORLD_HEIGHT; j++){
				if(i==myNetwork.getX(i, j) && j==myNetwork.getY(i, j)){
					g.drawRect(i*32, j*32, myNetwork.getWidth(i, j) * 32, myNetwork.getHeight(i, j) * 32);
				}
			}
		}
		g.drawRect(myStage.getRoomX()*32 + 8, myStage.getRoomY()*32 + 8, 16, 16);
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
