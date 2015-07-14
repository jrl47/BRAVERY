package Utilities;

public class RoomData {
	
	private int myPlane;
	public RoomData(int i, int j){
		String dataLine = RoomDataBuilder.roomData[i][j];
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myPlane = Integer.parseInt(data[0]);
	}
	public int getPlane() {
		return myPlane;
	}
	
}
