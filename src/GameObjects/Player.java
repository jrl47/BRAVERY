package GameObjects;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import UtilitiesData.SkillBuilder;
import UtilitiesData.SkillData;
import UtilityObjects.Action;
import UtilityObjects.Inventory;

public class Player extends GameObject{

	private int myX;
	private int myY;

	private boolean preparedMove;
	private boolean preparedAttack;
	private boolean preparedPlane;
	
	private int targetX;
	private int targetY;
	
	private int myHealth;
	private int myMaxHealth;
	
	private int damageTaken;
	
	private Stage myStage;
	private List<List<MapCell>> myCells;
	private boolean isPaused;
	private int planeCounter;
	
	private boolean tookAction;
	private Action myAction;
	
	private Inventory myInventory;
	private Set<String> myGenericSkills;
	
	private List<SkillData> myEarthSkills;
	private List<SkillData> myAirSkills;
	private List<SkillData> myWaterSkills;
	private List<SkillData> myFireSkills;
	
	public Player(Stage stage){
		myStage = stage;
		int roomX = myStage.getRoomX();
		int roomY = myStage.getRoomY();
		myX = 10 + Stage.ROOM_SIZE*-(myStage.getRooms().getX(roomX, roomY) - roomX);
		myY = 9  + Stage.ROOM_SIZE*-(myStage.getRooms().getY(roomX, roomY) - roomY);
		
		myEarthSkills = new ArrayList<SkillData>();
		myAirSkills = new ArrayList<SkillData>();
		myWaterSkills = new ArrayList<SkillData>();
		myFireSkills = new ArrayList<SkillData>();
		
		myHealth = 20;
		myMaxHealth =20;
		myGenericSkills = new HashSet<String>();
		
		
		myEarthSkills.add(SkillBuilder.getSkill(0, "earth"));
		myAirSkills.add(SkillBuilder.getSkill(0, "air"));
		myWaterSkills.add(SkillBuilder.getSkill(0, "water"));
		myFireSkills.add(SkillBuilder.getSkill(0, "fire"));

		myGenericSkills.add("planeshift");
		
		
		myAction = new Action("wait");
		
		myCells = myStage.getCells();
		
		myCells.get(myX).get(myY).addPlayer(this);
		myInventory = new Inventory();
	}
	
	public boolean isDead(){
		return myHealth<=0;
	}
	
	public void doDamage(Action action){
		myHealth-=action.getPower();
		damageTaken += action.getPower();
		if(myHealth<=0){
			myCells.get(myX).get(myY).removeEnemy();
		}
	}
	
	public int getDamageTaken(){
		return damageTaken;
	}
	
	@Override
	public void useInput(int i, int j, boolean b) {
		
	}

	@Override
	public void step() {

	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(myStage.getManager().getImage(this),(myStage.getRelativeX(myX))*Stage.BLOCK_SIZE, 1+((myStage.getRelativeY(myY))*Stage.BLOCK_SIZE), null);
	}

	public int getX() {
		return myX;
	}
	public int getY() {
		return myY;
	}
	
	public int getTargetX() {
		return targetX;
	}
	public int getTargetY() {
		return targetY;
	}
	
	public void setTargetX(int x){
		targetX = x;
	}
	public void setTargetY(int y){
		targetY = y;
	}
	
	public Inventory getInventory(){
		return myInventory;
	}
	
	public void useKeyPress(KeyEvent k) {
		if(k==null){
			return;
		}
		if(k.getKeyCode()==KeyEvent.VK_W || k.getKeyCode()==KeyEvent.VK_S || 
				k.getKeyCode()==KeyEvent.VK_A || k.getKeyCode()==KeyEvent.VK_D){
			myAction = new Action("wait");
		}
		
		if(k.getKeyCode()==KeyEvent.VK_W)
			myAction = new Action("up");
		if(k.getKeyCode()==KeyEvent.VK_S)
			myAction = new Action("down");
		if(k.getKeyCode()==KeyEvent.VK_A)
			myAction = new Action("left");
		if(k.getKeyCode()==KeyEvent.VK_D)
			myAction = new Action("right");
	}

	public void stopAction() {
		preparedMove = false;
		preparedAttack = false;
		preparedPlane = false;
		damageTaken = 0;
		myAction = new Action("wait");
	}
	public boolean actionPrepared() {
		return preparedMove || preparedAttack || preparedPlane;
	}
	
	public void prepareMove(){
		preparedMove = true;
	}
	public boolean movePrepared(){
		return preparedMove;
	}

	public void prepareAttack() {
		preparedAttack = true;
	}
	public boolean attackPrepared(){
		return preparedAttack;
	}
	
	public void preparePlane() {
		preparedPlane = true;
	}
	public boolean planePrepared(){
		return preparedPlane;
	}
	
	public void move(){
		if(!isPaused){
			myCells.get(myX).get(myY).removePlayer();
			myX += targetX;
			myY += targetY;
			targetX = 0;
			targetY = 0;
			tookAction = true;
			MapCell cell = myCells.get(myX).get(myY);
			chargeForAction();
			getCollectible(cell);
			cell.addPlayer(this);
			stopAction();
		}
	}
	
	public void attack(Enemy enemy) {
		if(!isPaused){
			tookAction = true;
			enemy.doDamage(myAction);
		}
	}
	
	public void planeShift(){
		if(!isPaused){
			tookAction = true;
			planeCounter = 11;
			myStage.planeShift(myAction.getName());
			chargeForAction();
			stopAction();
		}
	}
	
	public void resetLocation(int x, int y){
		if(!isPaused){
			myCells.get(myX).get(myY).removePlayer();
			myX = x;
			myY = y;
			targetX = 0;
			targetY = 0;
			tookAction = true;
			MapCell cell = myCells.get(myX).get(myY);
			chargeForAction();
			getCollectible(cell);
			cell.addPlayer(this);
			stopAction();
		}
	}

	public void chargeForAction() {
		planeCounter--;
		if(planeCounter<0) planeCounter = 0;
		if(myAction.getType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()-myAction.getCost());
		if(myAction.getType().equals("air"))
			myInventory.setAir(myInventory.getAir()-myAction.getCost());
		if(myAction.getType().equals("water"))
			myInventory.setWater(myInventory.getWater()-myAction.getCost());
		if(myAction.getType().equals("fire"))
			myInventory.setFire(myInventory.getFire()-myAction.getCost());
		if(myAction.getType().equals("all")){
			myInventory.setEarth(myInventory.getEarth()-myAction.getCost());
			myInventory.setAir(myInventory.getAir()-myAction.getCost());
			myInventory.setWater(myInventory.getWater()-myAction.getCost());
			myInventory.setFire(myInventory.getFire()-myAction.getCost());
		}
	}
	
	public boolean canAfford(int i, String type){
		if(type.equals("earth"))
			return myInventory.getEarth()>=i;
		if(type.equals("air"))
			return myInventory.getAir()>=i;
		if(type.equals("water"))
			return myInventory.getWater()>=i;
		if(type.equals("fire"))
			return myInventory.getFire()>=i;
		return false;
	}

	public void getCollectible(MapCell mapCell) {
		if(mapCell.getCollectible()==null){
			return;
		}
		
		if(mapCell.getCollectible() instanceof CollectibleSkill){
			if(!((CollectibleSkill) mapCell.getCollectible()).getGenericSkill().equals("")){
			myGenericSkills.add(((CollectibleSkill) mapCell.getCollectible()).getGenericSkill());
			mapCell.removeCollectible();
			return;
			}
			else{
				SkillData data = ((CollectibleSkill) mapCell.getCollectible()).getSkill();
				
				if(data.getType().equals("earth")){
					if(myEarthSkills.size()>data.getIndex()){
						myEarthSkills.set(data.getIndex(), data);
					} else if (myEarthSkills.size()==data.getIndex()){
						myEarthSkills.add(data);
					}
				}
				if(data.getType().equals("air")){
					if(myAirSkills.size()>data.getIndex()){
						myAirSkills.set(data.getIndex(), data);
					} else if (myAirSkills.size()==data.getIndex()){
						myAirSkills.add(data);
					}
				}
				if(data.getType().equals("water")){
					if(myWaterSkills.size()>data.getIndex()){
						myWaterSkills.set(data.getIndex(), data);
					} else if (myWaterSkills.size()==data.getIndex()){
						myWaterSkills.add(data);
					}
				}
				if(data.getType().equals("fire")){
					if(myFireSkills.size()>data.getIndex()){
						myFireSkills.set(data.getIndex(), data);
					} else if (myFireSkills.size()==data.getIndex()){
						myFireSkills.add(data);
					}
				}
				
				mapCell.removeCollectible();
				return;
			}
		}
		
		if(mapCell.getCollectible().getType().equals("earth"))
			myInventory.setEarth(myInventory.getEarth()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("air"))
			myInventory.setAir(myInventory.getAir()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("water"))
			myInventory.setWater(myInventory.getWater()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("fire"))
			myInventory.setFire(myInventory.getFire()+mapCell.getCollectible().getAmount());
		if(mapCell.getCollectible().getType().equals("health"))
			addHealth(mapCell.getCollectible().getAmount());
		
		mapCell.removeCollectible();
	}
	
	public boolean checkForEnemyTurn(){
		if(tookAction){
			tookAction = false;
			return true;
		}
		return tookAction;
	}

	public int getHealth() {
		return myHealth;
	}
	public int getMaxHealth() {
		return myMaxHealth;
	}

	public Action getAction() {
		return myAction;
	}
	public void setAction(Action a) {
		myAction = a;
	}
	public void clearAction() {
		myAction = new Action("wait");
	}
	
	public void pause(){
		isPaused = true;
	}
	public void unpause(){
		isPaused = false;
	}
	public boolean isPaused(){
		return isPaused;
	}
	
	public int getPlaneCounter(){
		return planeCounter;
	}
	public void addHealth(int amount) {
		myHealth+=amount;
		if(myHealth > myMaxHealth){
			myHealth = myMaxHealth;
		}
	}
	public Set<String> getGenericSkills(){
		return myGenericSkills;
	}

	public List<SkillData> getEarthSkills() {
		return myEarthSkills;
	}
	public List<SkillData> getAirSkills() {
		return myAirSkills;
	}
	public List<SkillData> getWaterSkills() {
		return myWaterSkills;
	}
	public List<SkillData> getFireSkills() {
		return myFireSkills;
	}
	public SkillData getSkill(int myIndex, String type){
		if(type.equals("earth")){
			return myEarthSkills.get(myIndex);
		}
		if(type.equals("air")){
			return myAirSkills.get(myIndex);
		}
		if(type.equals("water")){
			return myWaterSkills.get(myIndex);
		}
		if(type.equals("fire")){
			return myFireSkills.get(myIndex);
		}
		return null;
	}
}
