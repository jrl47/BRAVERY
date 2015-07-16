package UtilitiesData;


public class CollectibleData {
	
	private String myName;
	private int earthAmount;
	private int earthChance;
	private int airAmount;
	private int airChance;
	private int waterAmount;
	private int waterChance;
	private int fireAmount;
	private int fireChance;
	private int healthAmount;
	private int healthChance;
	public CollectibleData(int i){
		String dataLine = CollectibleBuilder.collectibleData[i];
		// Breaks up at colons
		String[] data = dataLine.split(":");
		myName = data[0];
		earthAmount = Integer.parseInt(data[1]);
		earthChance = Integer.parseInt(data[2]);
		airAmount = Integer.parseInt(data[3]);
		airChance = Integer.parseInt(data[4]);
		waterAmount = Integer.parseInt(data[5]);
		waterChance = Integer.parseInt(data[6]);
		fireAmount = Integer.parseInt(data[7]);
		fireChance = Integer.parseInt(data[8]);
		healthAmount = Integer.parseInt(data[9]);
		healthChance = Integer.parseInt(data[10]);
	}
	public String getName() {
		return myName;
	}
	public int getEarthAmount() {
		return earthAmount;
	}
	public int getEarthChance() {
		return earthChance;
	}
	public int getAirAmount() {
		return airAmount;
	}
	public int getAirChance() {
		return airChance;
	}
	public int getWaterAmount() {
		return waterAmount;
	}
	public int getWaterChance() {
		return waterChance;
	}
	public int getFireAmount() {
		return fireAmount;
	}
	public int getFireChance() {
		return fireChance;
	}
	public int getHealthAmount() {
		return healthAmount;
	}
	public int getHealthChance() {
		return healthChance;
	}
	public int getTotalChance(){
		return earthChance + airChance + waterChance + fireChance + healthChance;
	}
	
}
