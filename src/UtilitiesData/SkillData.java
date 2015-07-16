package UtilitiesData;

public class SkillData {
	private String myName;
	private int myHealth;
	private int myPower;
	private int sightRange;
	private int attackRange;
	public SkillData(int i, String type){
		if(type.equals("earth")){
			String dataLine = SkillBuilder.earthData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
		}
		if(type.equals("air")){
			String dataLine = SkillBuilder.airData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
		}
		if(type.equals("water")){
			String dataLine = SkillBuilder.waterData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
		}
		if(type.equals("fire")){
			String dataLine = SkillBuilder.fireData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
		}
	}
	public String getName() {
		return myName;
	}
	public void setName(String myName) {
		this.myName = myName;
	}
	public int getHealth() {
		return myHealth;
	}
	public void setHealth(int myHealth) {
		this.myHealth = myHealth;
	}
	public int getPower() {
		return myPower;
	}
	public void setPower(int myPower) {
		this.myPower = myPower;
	}
	public int getSightRange() {
		return sightRange;
	}
	public void setSightRange(int sightRange) {
		this.sightRange = sightRange;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}
}
