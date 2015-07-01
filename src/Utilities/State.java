package Utilities;

public class State {
	private String myString;
	private String myPrev;
	public State(String string){
		myString = string;
	}
	public void setState(String string){
		myPrev = myString;
		myString = string;
	}
	public void setState(State state){
		myPrev = myString;
		myString = state.getState();
	}
	public String getState(){
		return myString;
	}
	public String getPreviousState(){
		return myPrev;
	}
}
