package GameObjects;

import java.awt.image.BufferedImage;

import Utilities.State;

public class GameStartButton extends BorderedButton{

	State myState;
	public GameStartButton(int xx, int yy, String s, int size,
			BufferedImage font, BufferedImage hoverFont, BufferedImage backing,
			BufferedImage hoverBacking, State state) {
		super(xx, yy, s, size, font, hoverFont, backing, hoverBacking);
		myState = state;
	}

	@Override
	public void useInput(int i, int j, boolean b) {
		super.useInput(i,j,b);
		if(b){
			myState.setState("game");
		}
	}
	
}
