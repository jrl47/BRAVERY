package GameObjects;

public class MapCell extends GameObject{

	private boolean isPassable;
	private boolean isAvailable; // referring to immediate access for movement, attack, etc. "highlightability"
	
	public void setPassable(boolean b){
		isPassable = b;
	}
	public boolean isPassable(){
		return isPassable;
	}
	
	public void setAvailable(boolean b){
		isAvailable = b;
	}
	public boolean isAvailable(){
		return isAvailable;
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
