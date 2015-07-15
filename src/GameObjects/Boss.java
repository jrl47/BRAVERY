package GameObjects;

public class Boss extends Enemy{

	private int roomX;
	private int roomY;
	
	public Boss(int x, int y, Stage stage, int index, int roomx, int roomy) {
		super(x, y, stage, index);
		roomX = roomx;
		roomY = roomy;
	}
	
	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

}
