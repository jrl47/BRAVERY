package GameObjects;

import java.awt.image.BufferedImage;

import Utilities.State;

public class StateChangeButton extends BorderedButton{

	State myState;
	String myNewState;
	public StateChangeButton(int xx, int yy, String s, double d,
			BufferedImage font, BufferedImage hoverFont, BufferedImage backing,
			BufferedImage hoverBacking, State state, String newState) {
		super(xx, yy, s, d, font, hoverFont, backing, hoverBacking);
		myState = state;
		myNewState = newState;
	}

	@Override
	public void useInput(int i, int j, boolean b) {
		super.useInput(i,j,b);
		if(b){
			myState.setState(myNewState);
		}
	}
	
	public void setNewState(String s){
		myNewState = s;
	}

	public String getState() {
		return myNewState;
	}
}
