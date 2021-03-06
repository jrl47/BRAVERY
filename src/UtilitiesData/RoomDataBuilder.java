package UtilitiesData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import Utilities.RoomNetwork;

public class RoomDataBuilder {

	public static String[][] roomData;
	public static RoomData[][] myRoomDatas;
	public static void init(){
    	// General file-reading stuff, nothing to see here
		roomData = new String[RoomNetwork.WORLD_WIDTH][RoomNetwork.WORLD_HEIGHT];
    	URL myFile = EnemyBuilder.class.getResource("/RoomData");
    	BufferedReader b = null;
		try {
			b = new BufferedReader(new InputStreamReader(myFile.openStream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	String currentLine = null;
		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
		int counter = 0;
    	while(currentLine!=null){
    		// Breaks up at colons
    		String[] s = currentLine.split("-");
    		for(int i=0; i<s.length; i++){
    			roomData[i][counter] = s[i];
    		}
    		counter++;
    		try {currentLine = b.readLine();} catch (IOException e) {e.printStackTrace();}
    	}
		myRoomDatas = new RoomData[RoomNetwork.WORLD_WIDTH][RoomNetwork.WORLD_HEIGHT];
		for(int i=0; i<RoomNetwork.WORLD_WIDTH; i++){
			for(int j=0; j<RoomNetwork.WORLD_HEIGHT; j++){
				RoomData r = new RoomData(i, j);
				myRoomDatas[i][j] = r;
			}
		}
	}
	public static RoomData getRoomData(int i, int j){
		if(i>=0 && j>=0 && i<myRoomDatas.length && j < myRoomDatas[i].length)
			return myRoomDatas[i][j];
		return null;
	}
	
}
