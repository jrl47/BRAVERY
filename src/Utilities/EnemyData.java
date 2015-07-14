package Utilities;

public class EnemyData {
	
	private String myName;
	private int myHealth;
	private int myPower;
	private int sightRange;
	private int attackRange;
	public EnemyData(int i){
		String dataLine = EnemyBuilder.enemyData[i];
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myName = data[0];
		myHealth = Integer.parseInt(data[1]);
		myPower = Integer.parseInt(data[2]);
		sightRange = Integer.parseInt(data[3]);
		attackRange = Integer.parseInt(data[4]);
	}
	public String getMyName() {
		return myName;
	}
	public void setMyName(String myName) {
		this.myName = myName;
	}
	public int getMyHealth() {
		return myHealth;
	}
	public void setMyHealth(int myHealth) {
		this.myHealth = myHealth;
	}
	public int getMyPower() {
		return myPower;
	}
	public void setMyPower(int myPower) {
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
