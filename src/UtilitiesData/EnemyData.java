package UtilitiesData;


public class EnemyData {
	
	private String myName;
	private int myHealth;
	private int myPower;
	private int sightRange;
	private int attackRange;
	private int roomX;
	private int roomY;
	public EnemyData(int i){
		if(i < EnemyBuilder.NUMBER_OF_ENEMIES){
			String dataLine = EnemyBuilder.enemyData[i];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
		} else{
			String dataLine = EnemyBuilder.bossData[i-EnemyBuilder.NUMBER_OF_ENEMIES];
			// Breaks up at colons
			String[] data = dataLine.split(":");
			myName = data[0];
			myHealth = Integer.parseInt(data[1]);
			myPower = Integer.parseInt(data[2]);
			sightRange = Integer.parseInt(data[3]);
			attackRange = Integer.parseInt(data[4]);
			roomX = Integer.parseInt(data[5]);
			roomY = Integer.parseInt(data[6]);
		}
	}
	public String getName() {
		return myName;
	}
	public int getHealth() {
		return myHealth;
	}
	public int getPower() {
		return myPower;
	}
	public int getSightRange() {
		return sightRange;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public int getRoomX() {
		return roomX;
	}
	public int getRoomY() {
		return roomY;
	}
}
