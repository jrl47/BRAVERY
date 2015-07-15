package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Set;

import Main.Display;
import Utilities.RoomData;
import Utilities.RoomDataBuilder;
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
		int width = Stage.MAP_WIDTH * 32;
		int height = Display.height;
		int xshift = width / 2 - RoomNetwork.WORLD_WIDTH*32 / 2;
		int yshift = height / 2 - RoomNetwork.WORLD_HEIGHT*32 / 2;
		
		for(int i=0; i<RoomNetwork.WORLD_WIDTH; i++){
			for(int j=0; j<RoomNetwork.WORLD_HEIGHT; j++){
				RoomData data = RoomDataBuilder.getRoomData(i, j);
				if(i==myNetwork.getX(i, j) && j==myNetwork.getY(i, j)){
					if(myStage.getPlane() >= data.getPlane()){
						g.setColor(Color.PINK);
						g.fillRect(xshift + i*32, yshift + j*32, myNetwork.getWidth(i, j) * 32, myNetwork.getHeight(i, j) * 32);
						g.setColor(Color.BLACK);
						g.drawRect(xshift + i*32, yshift + j*32, myNetwork.getWidth(i, j) * 32, myNetwork.getHeight(i, j) * 32);
					}
				}
				Set<Character> sides = data.getSides();
				if(sides==null) continue;
				if(sides.contains('u')){
					g.setColor(Color.WHITE);
					g.fillRect(xshift + i*32 + 8, yshift + j*32 - 1, 16, 3);
				}
				if(sides.contains('d')){
					g.setColor(Color.WHITE);
					g.fillRect(xshift + i*32 + 8, yshift + j*32 + 31, 16, 3);
				}
				if(sides.contains('l')){
					g.setColor(Color.WHITE);
					g.fillRect(xshift + i*32 + - 1, yshift + j*32 + 8, 3, 16);
				}
				if(sides.contains('r')){
					g.setColor(Color.WHITE);
					g.fillRect(xshift + i*32 + + 15, yshift + j*32 + 31, 3, 16);
				}
			}
		}
		
		for(Boss b: myStage.getBosses()){
			g.setColor(Color.RED);
			g.fillRect(b.getRoomX()*32 + 6 + xshift, b.getRoomY()*32 + 6 + yshift, 20, 20);
			g.setColor(Color.BLACK);
			g.drawRect(b.getRoomX()*32 + 6 + xshift, b.getRoomY()*32 + 6 + yshift, 20, 20);
		}
		
		
		g.setColor(Color.GREEN);
		g.fillRect((myStage.getRoomX() + myStage.getPlayer().getX()/32)*32 + 8 + xshift,
				(myStage.getRoomY() + myStage.getPlayer().getY()/32)*32 + 8 + yshift, 16, 16);
		g.setColor(Color.BLACK);
		g.drawRect((myStage.getRoomX() + myStage.getPlayer().getX()/32)*32 + 8 + xshift,
				(myStage.getRoomY() + myStage.getPlayer().getY()/32)*32 + 8 + yshift, 16, 16);
		
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
