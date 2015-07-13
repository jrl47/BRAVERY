package Utilities;

public class MovementCostCalculator {
	
	public static int getCost(int xx, int yy){
		int x = Math.abs(xx);
		int y = Math.abs(yy);
		if((x==0 && y==1) || (x==1 && y==0)){
			return 0;
		}
		return (int) Math.pow(x + y, (int)Math.pow(x + y, .75));
	}
	
}
