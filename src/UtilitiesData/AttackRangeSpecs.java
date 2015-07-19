package UtilitiesData;

public class AttackRangeSpecs {

	int scale1;
	int scale2;
	int start;
	int end;
	
	public AttackRangeSpecs(String s) {
		String[] data = s.split("/");
		scale1 = Integer.parseInt(data[0]);
		scale2 = Integer.parseInt(data[1]);
		start = Integer.parseInt(data[2]);
		end = Integer.parseInt(data[3]);
	}
	
	public int getScale1(){
		return scale1;
	}
	public int getScale2(){
		return scale2;
	}
	public int getStart(){
		return start;
	}
	public int getEnd(){
		return end;
	}
	
	

}
