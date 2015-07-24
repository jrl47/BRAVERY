package UtilitiesData;

public class CollectibleSkillData {
	private String myName;
	private String myType;
	private int myIndex;
	private int myX;
	private int myY;
	private int roomX;
	private int roomY;
	public CollectibleSkillData(int i){
		String dataLine = CollectibleSkillBuilder.skillData[i];
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myName = data[0];
		myType = data[1];
		myX = Integer.parseInt(data[3]);
		myY = Integer.parseInt(data[4]);
		roomX = Integer.parseInt(data[5]);
		roomY = Integer.parseInt(data[6]);
		myIndex = Integer.parseInt(data[2]);
	}
	public String getName() {
		return myName;
	}
	public String getType(){
		return myType;
	}
	public int getIndex(){
		return myIndex;
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
