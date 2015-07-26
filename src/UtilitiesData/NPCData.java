package UtilitiesData;

public class NPCData {
	private String myName;
	private int myX;
	private int myY;
	private int roomX;
	private int roomY;
	public NPCData(int i){
		String dataLine = NPCBuilder.npcData[i];
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myName = data[0];
		myX = Integer.parseInt(data[1]);
		myY = Integer.parseInt(data[2]);
		roomX = Integer.parseInt(data[3]);
		roomY = Integer.parseInt(data[4]);
	}
	public String getName() {
		return myName;
	}
	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	public int getRoomX() {
		return roomX;
	}
	public int getRoomY() {
		return roomY;
	}
}
