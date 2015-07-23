package UtilitiesData;

import java.util.HashSet;
import java.util.Set;

public class RoomData {
	
	private int myPlane;
	private Set<Character> mySides;
	public RoomData(int i, int j){
		String dataLine = RoomDataBuilder.roomData[i][j];
		if(dataLine==null) {
			myPlane = 10;
			return;
		}
		
		mySides = new HashSet<Character>();
		String[] data = dataLine.split(":");
		if(data[0].equals("x")){
			myPlane = 100;
		}else{
		myPlane = Integer.parseInt(data[0]);
		}
		for(int x=1; x<data.length; x++){
			if(data[x].charAt(0)=='u' || data[x].charAt(0)=='d' || data[x].charAt(0)=='l' || data[x].charAt(0)=='r')
			mySides.add(data[x].charAt(0));
		}
	}
	public int getPlane() {
		return myPlane;
	}
	public Set<Character> getSides(){
		return mySides;
	}
}
